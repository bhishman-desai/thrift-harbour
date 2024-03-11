import React, { Fragment, useEffect, useState } from "react";
import {
  Badge,
  IconButton,
  List,
  ListItem,
  ListItemText,
} from "@material-ui/core";
import ChatIcon from "@material-ui/icons/Chat";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { AdminServices } from "../../services/Admin";
import { UserChatType } from "../../types/ChatTypes";
import ChatWindow from "./ChatWindow";
import { ChatService } from "../../services/ChatService";

interface ChatPayload {
  id: string;
  senderId: string;
  recipientId: string;
  content: string;
}

const ChatScreen: React.FC = () => {
  const adminService = new AdminServices();

  const id = localStorage.getItem("uId");
  const token = localStorage.getItem("token");

  const [users, setUsers] = useState<UserChatType[]>([]);
  const [stompClient, setStompClient] = useState<Stomp.Client>(
    {} as Stomp.Client,
  );

  const [openChat, setOpenChat] = useState(false);
  const [sender, setSender] = useState<UserChatType>({} as UserChatType);
  const [recipient, setRecipient] = useState<UserChatType>({} as UserChatType);

  const chatService = new ChatService();

  useEffect(() => {
    fetchChatHistory().then(() => {});
  }, []);

  const fetchChatUsers = async (id: string) => {
    const [chatUsersResponse, error] = await chatService.fetchChatUser(id);

    if (chatUsersResponse && chatUsersResponse.status === 200) {
      const chatUsers = chatUsersResponse.message;
      setUsers(chatUsers);
    }
  };
  const fetchChatHistory = async () => {
    const [chatHistory, error] = await chatService.fetchChatHistory(
      String(sender.userID),
      String(recipient.userID),
    );
  };

  useEffect(() => {
    (async function () {
      try {
        const response =
          id === "1"
            ? await adminService.getUserbyIDAdmin(parseInt(id), token)
            : await adminService.getUserbyIDUser(parseInt(id || "1"), token);

        if (response && response[0]?.status === 200) {
          /* TODO: Change the URL */
          const socket = new SockJS("http://localhost:8080/ws");
          const stompClient = Stomp.over(socket);

          const user = response[0].data;

          fetchChatUsers(user.userID).then(() => {
            setSender({
              userID: user.userID,
              firstName: user.firstName,
              lastName: user.lastName,
              email: user.email,
              notification: false,
            });

            stompClient.connect({}, () => {
              stompClient.subscribe(
                `/user/${user.userID}/queue/messages`,
                async (payload) => {
                  const chatPayload: ChatPayload = JSON.parse(payload.body);
                  const senderUserResponse =
                    id === "1"
                      ? await adminService.getUserbyIDAdmin(
                          parseInt(chatPayload.senderId),
                          token,
                        )
                      : await adminService.getUserbyIDUser(
                          parseInt(chatPayload.senderId),
                          token,
                        );

                  if (senderUserResponse[0].status === 200) {
                    const senderUser = senderUserResponse[0].data;
                    /* TODO: Set notification for user */
                  }
                },
              );
            });
            setStompClient(stompClient);
          });

          return () => {
            if (stompClient.connected) {
              stompClient.disconnect(() => {});
            }
          };
        }
      } catch (error) {
        /* TODO: Handle Error */
      }
    })();
  }, []);

  return (
    <Fragment>
      <List>
        {users.map((user) => (
          <ListItem key={user.userID}>
            <ListItemText primary={user.firstName} />
            <IconButton
              color="primary"
              onClick={(e) => {
                e.stopPropagation(); // Prevent triggering the Parent onClick
                user.notification = false;
                setRecipient(user);
                setOpenChat(true);
              }}
            >
              <Badge
                color="secondary"
                variant="dot"
                invisible={!user.notification}
              >
                <ChatIcon />
              </Badge>
            </IconButton>
          </ListItem>
        ))}
      </List>

      <ChatWindow
        open={openChat}
        sender={sender}
        recipient={recipient}
        onClose={() => {
          sender.notification = false;
          setOpenChat(false);
        }}
      />
    </Fragment>
  );
};

export default ChatScreen;
