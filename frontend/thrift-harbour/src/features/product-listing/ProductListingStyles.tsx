import style from "styled-components";

export const Container = style.div`
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #091c7a;
  background: rgb(0,212,255);
  background: linear-gradient(90deg, rgba(0,212,255,1) 0%, rgba(23,16,148,1) 100%);
`;

export const InputCard = style.div`
  display: flex;
  width:30%;
  flex-direction: column;
  border: 1px solid red;
  box-sizing: border-box;
  background-color:#fff;
  border: 1px solid #edf1f7;
  border-radius: 8px;
  padding: 12px 18px 12px 18px;
`;
