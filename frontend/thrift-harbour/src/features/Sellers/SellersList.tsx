import { useEffect, useState } from "react";
import { AdminServices } from "../../services/Admin";
import { GetSellersResponse } from "../../types/ListingTypes";
import {
  Avatar,
  AvatarContainer,
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

const Dashboard: React.FC = () => {
  const admin = new AdminServices();
  const token = localStorage.getItem("token");
  const [loading, setLoading] = useState(false);
  const [sellers, setSellers] = useState<GetSellersResponse[]>([]);
  const [error, setError] = useState(false);

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
              <Parent>
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
    </SellersContainer>
  );
};

export default Dashboard;
