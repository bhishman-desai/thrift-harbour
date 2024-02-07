import React, { FormEvent, useState } from "react";
import Modal from "../../components/ui-components/Modal/Modal";
import { Auth } from "../../services/Auth";
import { Credentials } from "../../types/AuthTypes";
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
  LoginLink,
  Message,
} from "./RegistrationStyles";
import { Link } from "react-router-dom";
import { ClipLoader } from "react-spinners";
import useAuth from "../../hooks/useAuth";
import Home from "../home/HomeScreen";

const Registration: React.FC = () => {
  const { token } = useAuth();
  const [credentials, setCredentials] = useState({} as Credentials);
  const [showCriteria, setShowCriteria] = useState(false);
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [nameError, setNameError] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [registerSuccess, setRegisterSuccess] = useState(false);
  const [alreadyExist, setAlreadyExist] = useState(false);
  const [error, setError] = useState(false);
  const auth = new Auth();

  const validatePassword = () => {
    const regex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[#@*]).{8,}$/;
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

  const onSubmitRegister = async (e: FormEvent) => {
    e.preventDefault();
    const validPassword = validatePassword();
    const validName = validateNames();
    if (!validName) {
      setNameError(true);
      return;
    }
    if (!validPassword) {
      setShowCriteria(true);
    } else {
      setIsLoading(true);
      setCredentials({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
      });
      try {
        const [data, error] = await auth.signUpUser(credentials as Credentials);
        if (data?.userID) {
          setIsLoading(false);
          setRegisterSuccess(true);
        } else if (error?.status === 409) {
          setIsLoading(false);
          setAlreadyExist(true);
        } else {
          setIsLoading(false);
          setError(true);
        }
      } catch (error) {
        setIsLoading(false);
        setError(true);
      }
    }
  };

  const toggleNameError = () => {
    setNameError(!nameError);
  };

  const toggleCriteria = () => {
    setShowCriteria(!showCriteria);
  };

  const toggoleRegisterSuccess = () => {
    setRegisterSuccess(!registerSuccess);
  };

  const toggoleAlreadyExist = () => {
    setAlreadyExist(!alreadyExist);
  };

  const toggleError = () => {
    setError(!error);
  };

  return (
    <>
      {token && token?.length > 0 && (
        <>
          <Home />
        </>
      )}
      {token?.length === 0 && (
        <Container>
          <InputCard>
            <Title>Sign up to Thrift Harbour</Title>
            <Form onSubmit={onSubmitRegister}>
              <Field>
                <Label>First Name</Label>
                <Input
                  value={credentials.firstName}
                  required={true}
                  id="firstName"
                  onFocus={() => setIsFocused(true)}
                  onBlur={() => setIsFocused(false)}
                  onChange={(e) =>
                    setCredentials({
                      ...credentials,
                      firstName: e.target.value,
                    })
                  }
                ></Input>
              </Field>
              <Field>
                <Label htmlFor="lastName">Last Name</Label>
                <Input
                  value={credentials.lastName}
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
                  value={credentials.email}
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
                  value={credentials.password}
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
                <RegisterButton type="submit">
                  {isLoading ? (
                    <ClipLoader color="#ffffff" loading={isLoading} size={20} />
                  ) : (
                    "Sign up"
                  )}
                </RegisterButton>
              </Button>
            </Form>
            <LoginLink>
              <Link to="/login">Already have an account ? Sign In</Link>
            </LoginLink>
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

          {registerSuccess && (
            <Modal onClose={toggoleRegisterSuccess}>
              <Message>
                <p style={{ color: "green" }}>Registered Successfully!</p>
                <Link to="/login">Click here to Login</Link>
              </Message>
            </Modal>
          )}

          {alreadyExist && (
            <Modal onClose={toggoleAlreadyExist}>
              <Message>
                <p style={{ color: "Red" }}>
                  User already registered with this email!
                </p>
                <Link to="/login">Click here to Login</Link>
              </Message>
            </Modal>
          )}

          {error && (
            <Modal onClose={toggleError}>
              <Message>
                <p style={{ color: "Red" }}>
                  Something went wrong, please try again!
                </p>
              </Message>
            </Modal>
          )}
        </Container>
      )}
    </>
  );
};

export default Registration;
