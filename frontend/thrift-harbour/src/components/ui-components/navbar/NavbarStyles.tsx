import styled from "styled-components";

export const NavContainer = styled.div`
  height: 8vh;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  justify-content: flex-end;
  background: #6c98da;
`;

export const TabsOptionsContainer = styled.div`
  display: flex;
  flex-direction: row;
  height: 100%;
  justify-content: space-between;
  padding-right: 12px;
`;

export const Tabs = styled.div`
  display: flex;
  flex-direction: row;
  height: 100%;
`;

export const Profile = styled.div`
  display: flex;
  flex-direction: row;
  height: 100%;
  justify-content: center;
  align-items: center;
  z-index: 1;
  margin-left: 16px;
`;
export const Option = styled.div<{ currentSelectd: boolean }>`
  display: flex;
  flex-direction: row;
  height: 100%;
  justify-content: center;
  align-items: center;
  padding: 0px 16px 0px 16px;
  background: ${(props) => (props.currentSelectd ? "#796BCD" : " #6c98da")};
  &:hover {
    background-color: #1d85e4;
  }
  cursor: pointer;
`;

export const ProfileIconBg = styled.div`
  display: flex;
  border-radius: 50%;
  height: 70%;
  justify-content: center;
  align-items: center;
  background-color: #ffffff;
`;

export const Icon = styled.div``;
