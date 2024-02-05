import styled from "styled-components";

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
  min-height: 100vh;
`;

export const FormContainer = styled.div`
  text-align: center;
`;

export const Form = styled.form`
  width: 100%;
`;

export const Title = styled.h2`
  margin-bottom: 1rem;
`;

export const Input = styled.input`
  width: 65%;
  padding: 0.75rem;
  margin-bottom: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #d9d9d9;
`;

export const RegisterButton = styled.button`
  width: 70%;
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
