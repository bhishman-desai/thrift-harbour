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
  const list = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
  {
  }
  return (
    <SellersContainer>
      <Header>Sellers</Header>
      {list.map((item) => {
        return (
          <Parent>
            <UserCard>
              <AvatarContainer>
                <Avatar>
                  <Initials>K</Initials>
                </Avatar>
              </AvatarContainer>
              <NameEmail>
                <Name>Krutik Kulkarni</Name>
                <Email>kulkarnikrutik@gmail.com</Email>
              </NameEmail>
            </UserCard>
            <Id>
              <UserId>UserId</UserId>
            </Id>
          </Parent>
        );
      })}
    </SellersContainer>
  );
};

export default Dashboard;
