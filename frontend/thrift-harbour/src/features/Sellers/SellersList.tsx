import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AdminServices } from "../../services/Admin";
import { GetSellersResponse } from "../../types/ListingTypes";
import {
  Avatar,
  AvatarContainer, ChatButton,
  Email,
  Header,
  Id,
  Initials,
  Name,
  NameEmail,
  Parent,
  SellersContainer,
  UserCard,
  UserId,
} from "./SellersListStyles";

import ChatWindow from "../chat/ChatWindow";

interface SllerListProps {
  setCurrentSelected: (key: string) => void;
  currentSelected: string;
}

const SellersList: React.FC<SllerListProps> = ({
  setCurrentSelected,
  currentSelected,
}) => {
  let navigate = useNavigate();
  const admin = new AdminServices();
  const token = localStorage.getItem("token");
  const [loading, setLoading] = useState(false);
  const [sellers, setSellers] = useState<GetSellersResponse[]>([]);
  const [error, setError] = useState(false);
  const [openChat, setOpenChat] = useState(false);
  const [selectedUser, setSelectedUser] = useState<GetSellersResponse>(
    {} as GetSellersResponse,
  );

  const list = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
  {
  }

  useEffect(() => {
    (async function () {
      try {
        console.log("in use effect");
        const response = await admin.getSellers(token);

        if (response[0]?.status === 200) {
          setLoading(false);
          const data = response[0].data;

          setSellers(data);
          console.log("data after if", data);
        } else {
          setError(true);
          setLoading(false);
        }
      } catch (error) {
        setLoading(false);
        setError(true);
      }
    })();
  }, []);

  return (
    <SellersContainer>
      <Header>Sellers</Header>
      {sellers.length ? (
        <>
          {sellers.map((seller) => {
            return (
              <Parent
                key={seller.userID}
                onClick={() => {
                  localStorage.setItem("uId", String(seller.userID));
                  setCurrentSelected("List By Sellers");
                }}
              >
                <UserCard>
                  <AvatarContainer>
                    <Avatar>
                      <Initials>{seller.firstName.charAt(0)}</Initials>
                    </Avatar>
                  </AvatarContainer>
                  <NameEmail>
                    <Name>
                      {seller.firstName} {seller.lastName}
                    </Name>
                    <Email>{seller.email}</Email>
                  </NameEmail>
                  <ChatButton
                    variant="contained"
                    color="primary"
                    onClick={(e) => {
                      e.stopPropagation(); // Prevent triggering the Parent onClick
                      setSelectedUser(seller);
                      setOpenChat(true);
                    }}
                  >
                    Chat
                  </ChatButton>
                </UserCard>
                <Id>
                  <UserId>User Id: {seller.userID}</UserId>
                </Id>
              </Parent>
            );
          })}
        </>
      ) : (
        <>"Loading</>
      )}

      <ChatWindow
        open={openChat}
        sender={
          {
            userID: 1,
            firstName: "admin",
            lastName: "",
            email: "admin@dal.ca",
          } as GetSellersResponse
        }
        recipient={selectedUser}
        onClose={() => setOpenChat(false)}
      />
    </SellersContainer>
  );
};

export default SellersList;
