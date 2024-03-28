import React, { ChangeEvent, useEffect, useState } from "react";
import { List, TextField } from "@material-ui/core";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { GetSellersResponse } from "../../types/ListingTypes";
import { Dialog } from "@mui/material";
import {
  DialogActions,
  DialogContent,
  DialogTitle,
  IconButton,
} from "@mui/joy";
import Button from "@mui/joy/Button";
import { useStylesChatWindow } from "./ChatWindowStyles";
import CloseIcon from "@material-ui/icons/Close";
import { ChatService } from "../../services/ChatService";
import { ChatMessageType } from "../../types/ChatTypes";

interface ChatWindowProps {
  open: boolean;
  sender: any;
  recipient: GetSellersResponse;
  onClose: () => void;
}

const ChatWindow: React.FC<ChatWindowProps> = ({
  open,
  sender,
  recipient,
  onClose,
}) => {
  const [messages, setMessages] = useState<ChatMessageType[]>([]);
  const [message, setMessage] = useState("");
  const [stompClient, setStompClient] = useState<Stomp.Client>(
    {} as Stomp.Client
  );
  const [scrollContainer, setScrollContainer] = useState<HTMLDivElement | null>(
    null
  );
  const chatService = new ChatService();

  const scrollToBottom = () => {
    if (scrollContainer) {
      scrollContainer.scrollTop = scrollContainer.scrollHeight;
    }
  };
  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      handleSendMessage();
    }
  };

  useEffect(() => {
    if (open) {
      fetchChatHistory().then(() => {
        scrollToBottom();
      });
    }
  }, [open]);

  useEffect(() => {
    if (open) {
      /* TODO: Change the URL */
      const socket = new SockJS("http://172.17.1.50:8080/ws");
      const stompClient = Stomp.over(socket);

      stompClient.connect({}, () => {
        stompClient.subscribe(
          `/user/${sender.userID}/queue/messages`,
          onMessageReceived
        );
      });

      setStompClient(stompClient);

      return () => {
        if (stompClient.connected) {
          stompClient.disconnect(() => {});
        }
      };
    }
  }, [open]);

  const onMessageReceived = (payload: any) => {
    const newMessage = JSON.parse(payload.body) as ChatMessageType;

    setMessages((prevMessages) => {
      return [...prevMessages, newMessage];
    });
  };

  const fetchChatHistory = async () => {
    const [chatHistory, error] = await chatService.fetchChatHistory(
      String(sender.userID),
      String(recipient.userID)
    );
    if (chatHistory) setMessages(chatHistory);
  };

  const handleSendMessage = () => {
    if (stompClient && message.trim()) {
      const chatMessage = {
        senderId: sender.userID,
        recipientId: recipient.userID,
        content: message,
        date: new Date().toISOString(),
      };
      stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
      setMessages((prevMessages) => [
        ...prevMessages,
        {
          senderId: String(sender.userID),
          recipientId: String(recipient.userID),
          content: message,
        } as ChatMessageType,
      ]);
      setMessage("");
    }
  };

  const classes = useStylesChatWindow();
  return (
    <Dialog
      open={open}
      onClose={onClose}
      fullWidth={true}
      classes={{ paper: classes.dialog }}
    >
      <DialogTitle className={classes.dialogTitle}>
        Chat with User {recipient.firstName}
        <IconButton
          aria-label="close"
          className={classes.closeButton}
          onClick={onClose}
        >
          <CloseIcon />
        </IconButton>
      </DialogTitle>
      <DialogContent className={classes.dialogContent}>
        <div ref={(ref) => setScrollContainer(ref)}>
          <List>
            {messages.map((msg, index) => {
              const isSender = msg.senderId === String(sender.userID);
              return (
                <div
                  key={index}
                  style={{
                    textAlign: isSender ? "right" : "left",
                  }}
                >
                  {msg.content}
                </div>
              );
            })}
          </List>
        </div>

        <TextField
          autoFocus
          margin="dense"
          id="message"
          label="Message"
          type="text"
          fullWidth
          value={message}
          onChange={(e: ChangeEvent<HTMLInputElement>) =>
            setMessage(e.target.value)
          }
          onKeyDown={handleKeyDown}
        />
      </DialogContent>
      <DialogActions className={classes.dialogAction}>
        <Button
          onClick={handleSendMessage}
          color="primary"
          className={classes.button}
        >
          Send
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ChatWindow;
