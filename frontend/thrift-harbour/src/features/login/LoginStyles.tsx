import style from "styled-components";

export const Container = style.div`
  height:100vh;
  display:flex;
  flex-direction: row;
`;

export const HarbourImage = style.div`
  height:100%;
  width:60%;
  border:1px solid red;
  background-color:#091c7a;
`;

export const LoginSignup = style.div`
  height:100%;
  width:40%;
  border:1px solid red;
`;

export const Title = style.p`
  fontWeight:bold;
  font-size:50px;
`;
