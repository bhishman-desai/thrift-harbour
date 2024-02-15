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
