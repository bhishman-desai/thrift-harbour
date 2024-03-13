import styled from "styled-components";

export const Header = styled.div`
  line-height: 28px;
  color: rgb(33, 33, 33);
  font-family: Inter;
  font-size: 20px;
  font-weight: 600;
`;

export const Profile = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

// export const Profile = styled.div`
//   display: flex;
//   flex-direction: row;
//   margin-top: 24px;
// `;
export const InfoContainer = styled.div`
  width: 50%;
`;

export const UserInfo = styled.div`
  display: flex;
  flex-direction: row;
  margin-top: 16px;
`;

export const Title = styled.div`
  color: #212121;
  font-family: inter;
  font-size: 16px;
  line-height: 20px;
  font-weight: 600;
`;

export const Value = styled.div`
  color: #616161;
  font-family: inter;
  line-height: 20px;
  font-size: 16px;
  font-weight: 400;
`;

export const RatingContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-top: 16px;
`;
