import styled from "styled-components";

export const SellersContainer = styled.div`
  // height: 100vh;
  padding: 14px;
`;

export const Header = styled.div`
  color: rgb(33, 33, 33);
  line-height: 28px;
  font-family: Inter;
  font-size: 20px;
  font-weight: 600;
  margin-left: 10px;
`;

export const Parent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  height: 15%;
  padding: 14px;
  border-bottom: 1px solid #9e9e9e;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #fafafa;
  }
`;
export const UserCard = styled.div`
  width: 100%;
  height: 70%;
  display: flex;
  flex-direction: row;
  align-items: center;
`;

export const AvatarContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const Avatar = styled.div`
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #28034e;
  border: 0;
  border-radius: 50%;
`;

export const Initials = styled.div`
  font-family: Inter;
  color: #ffff;
  font-weight: 600;
  font-size: 14px;
  line-height: 20px;
  overflow: hidden;
`;

export const NameEmail = styled.div`
  margin-left: 12px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

export const Name = styled.div`
  font-family: Inter;
  color: #616161;
  font-weight: 600;
  font-size: 14px;
  line-height: 20px;
  overflow: hidden;
`;

export const Email = styled.div`
  color: #99631b;
  font-weight: 400;
  background-color: #fff7e5;
  font-family: Inter, sans-serif;
`;

export const Id = styled.div`
  display: flex;
  align-items: center;
`;
export const UserId = styled.div`
  margin-top: 12px;
  display: flex;
  font-family: Inter;
  color: #616161;
  font-weight: 400;
  font-size: 18px;
  line-height: 16px;
  overflow: hidden;
`;
