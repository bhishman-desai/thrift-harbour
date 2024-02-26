import styled from "styled-components";

export const Container = styled.div`
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  // background-color: #091c7a;
  background-color: #e8e8e8;
`;

export const InputCard = styled.div`
  display: flex;
  flex-direction: column;
  border: 1px solid red;
  box-sizing: border-box;
  background-color: #fff;
  border: 1px solid #edf1f7;
  border-radius: 8px;
  padding: 12px 18px 12px 18px;
`;

export const Title = styled.h2`
  font-size: 2rem;
  text-align: center;
`;

export const Label = styled.label`
  font-family: Inter, sans-serif;
  font-weight: 400;
  font-weight: 500;
  margin-bottom: 5px;
`;

export const Input = styled.input`
  border: 2px solid #212121;
  border-radius: 4px;
  padding: 8px;
  &:focus {
    outline: none; /* Remove default focus outline */
    border-color: #731dcf; /* Set border color when focused (same as default color) */
  }
  height: 1.5rem;
`;

export const Field = styled.div`
  display: flex;
  flex-direction: column;
  bprder: 1px solid red;
  margin-bottom: 12px;
`;

export const Form = styled.form`
  width: 100%;
`;

export const Button = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  margin-top: 14px;
`;

export const RegisterButton = styled.button`
  width: 100%;
  padding: 0.75rem;
  margin-bottom: 1rem;
  border-radius: 4px;
  background-color: #1450a3;
  color: #fff;
  border: none;
  &:hover {
    background-color: #191d88;
  }
`;

export const LoginLink = styled.div`
  text-align-center;
  display:flex;
  justify-content:center;
  align-items:center;
`;

export const Message = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const Error = styled.div`
  margin-top: 8px;
  font-family: inter;
  font-weight: 400;
  color: "red";
`;

export const InfoContainer = styled.div`
  display: flex;
  margin-left: 4px;
`;

export const PasswordContainer = styled.div`
  display: flex;
  flex-direction: row;
`;
