import React, { FormEvent, useState } from "react";
import Modal from "../../components/ui-components/Modal/Modal";
import { Auth } from "../../services/Auth";
import { SignUpRequest } from "../../types/types";
import {
  Container,
  Form,
  FormContainer,
  Input,
  RegisterButton,
  Title,
} from "./RegistrationStyles";

export interface Credentials {
  name: string;
  email: string;
  password: string;
}

const Registration: React.FC = () => {
  const [credentials, setCredentials] = useState({} as Credentials);
  const [validPass, setValidPass] = useState(false);
  const [showCriteria, setShowCriteria] = useState(false);

  const auth = new Auth();

  console.log("environment", process.env.NODE_ENV);
  const validatePassword = () => {
    const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    return regex.test(credentials.password);
  };

  const onSubmitRegister = (e: FormEvent) => {
    e.preventDefault();
    const valid = validatePassword();
    if (!valid) {
      setShowCriteria(true);
      setValidPass(false);
    } else {
      setValidPass(true);
      const response = auth.signUpUser(credentials as SignUpRequest);
    }
  };

  const toggleCriteria = () => {
    setShowCriteria(!showCriteria);
  };

  return (
    <Container>
      <FormContainer>
        <Title>Sign up </Title>
        <Form onSubmit={onSubmitRegister}>
          <Input
            required={true}
            type="text"
            placeholder="Enter you name"
            onChange={(e) =>
              setCredentials({ ...credentials, name: e.target.value })
            }
            value={credentials.name}
          />
          <Input
            required={true}
            type="email"
            placeholder="Enter your email"
            onChange={(e) =>
              setCredentials({ ...credentials, email: e.target.value })
            }
            value={credentials.email}
          />
          <Input
            required={true}
            type="password"
            placeholder="Enter your password"
            onChange={(e) =>
              setCredentials({ ...credentials, password: e.target.value })
            }
            value={credentials.password}
          />
          <RegisterButton type="submit">Sign up</RegisterButton>
        </Form>
      </FormContainer>

      {showCriteria && (
        <Modal onClose={toggleCriteria}>
          <div>
            <p style={{ color: "red" }}>
              1. Must be at least 8 characters long{" "}
            </p>
            <p style={{ color: "red" }}>
              2. Must be combination of alphanumeric characters
            </p>
          </div>
        </Modal>
      )}
    </Container>
  );
};

export default Registration;
