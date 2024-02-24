import style from "styled-components";

export const Container = style.div`
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #091c7a;
`;

export const InputCard = style.div`
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  background-color: #fff;
  border: 1px solid #edf1f7;
  border-radius: 8px;
  padding: 16px;
`;

export const TabsContainer = style.div`
  margin-top: 10px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-around;
  background-color: #fff;
//   padding: 16px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

  @media screen and (max-width: 768px) {
    flex-direction: column;
  }
`;

interface TabProps {
  selected: boolean;
}

export const Tab = style.div<TabProps>`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 12px 24px;
  color: ${(props) => (props.selected ? " #731DCF" : "")};
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
  background-color: #fff;
  border-bottom: ${(props) => (props.selected ? "4px solid #731DCF" : "")};

`;
