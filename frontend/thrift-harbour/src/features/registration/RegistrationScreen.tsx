import React, { FormEvent, useState } from "react";
import Modal from "../../components/ui-components/Modal/Modal";
import { Auth } from "../../services/Auth";
import { Credentials } from "../../types/types";
import {
  Container,
  Label,
  Field,
  Form,
  InputCard,
  Input,
  RegisterButton,
  Title,
  Button,
} from "./RegistrationStyles";

const Registration: React.FC = () => {
  const [credentials, setCredentials] = useState({} as Credentials);
  const [validPass, setValidPass] = useState(false);
  const [showCriteria, setShowCriteria] = useState(false);
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [inputValue, setInputValue] = useState("");
  const [nameError, setNameError] = useState(false);

  const auth = new Auth();

  const validatePassword = () => {
    const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    return regex.test(credentials.password);
  };

  const validateNames = () => {
    const validFirstName = /^\w+$/.test(credentials.firstName.trim());
    const validLastName = /^\w+$/.test(credentials.lastName.trim());

    if (validFirstName && validLastName) {
      return true;
    } else {
      return false;
    }
  };

  const onSubmitRegister = (e: FormEvent) => {
    e.preventDefault();
    const valid = validatePassword();
    const validName = validateNames();
    if (!validName) {
      setNameError(true);
      return;
    }
    if (!valid) {
      setShowCriteria(true);
      setValidPass(false);
    } else {
      setValidPass(true);
      const response = auth.signUpUser(credentials as Credentials);
    }
  };

  const toggleNameError = () => {
    setNameError(!nameError);
  };

  const toggleCriteria = () => {
    setShowCriteria(!showCriteria);
  };

  return (
    <Container>
      <InputCard>
        <Title>Sign up to Thrift Harbour</Title>
        <Form onSubmit={onSubmitRegister}>
          <Field>
            <Label>First Name</Label>
            <Input
              required={true}
              id="firstName"
              onFocus={() => setIsFocused(true)}
              onBlur={() => setIsFocused(false)}
              onChange={(e) =>
                setCredentials({ ...credentials, firstName: e.target.value })
              }
            ></Input>
          </Field>
          <Field>
            <Label htmlFor="lastName">Last Name</Label>
            <Input
              required={true}
              id="lastName"
              onFocus={() => setIsFocused(!isFocused)}
              onBlur={() => setIsFocused(false)}
              onChange={(e) =>
                setCredentials({ ...credentials, lastName: e.target.value })
              }
            ></Input>
          </Field>
          <Field>
            <Label>E-mail ID</Label>
            <Input
              required={true}
              type="email"
              id="email"
              onFocus={() => setIsFocused(!isFocused)}
              onBlur={() => setIsFocused(false)}
              onChange={(e) =>
                setCredentials({ ...credentials, email: e.target.value })
              }
            ></Input>
          </Field>
          <Field>
            <Label>Password</Label>
            <Input
              required={true}
              type="password"
              id="password"
              onFocus={() => setIsFocused(!isFocused)}
              onBlur={() => setIsFocused(false)}
              onChange={(e) =>
                setCredentials({ ...credentials, password: e.target.value })
              }
            ></Input>
          </Field>
          <Button>
            <RegisterButton type="submit">Sign up</RegisterButton>
          </Button>
        </Form>
      </InputCard>

      {nameError && (
        <Modal onClose={toggleNameError}>
          <div>
            <p style={{ color: "red" }}>
              First Name or Last Name should be one word only
            </p>
          </div>
        </Modal>
      )}

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
