import React, { FormEvent, useState } from "react";
import Modal from "../../components/ui-components/Modal/Modal";
import { Auth } from "../../services/Auth";
import { Credentials, LoginCredentials } from "../../types/AuthTypes";
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
  Error,
  InfoContainer,
  PasswordContainer,
} from "./RegistrationStyles";
import { Link, useNavigate } from "react-router-dom";
import { ClipLoader } from "react-spinners";
import useAuth from "../../hooks/useAuth";
import Home from "../home/HomeScreen";
import Info from "../../assets/icons/Info";

const Registration: React.FC = () => {
  const { token, handleLogin } = useAuth();
  const navigate = useNavigate();

  const [credentials, setCredentials] = useState({} as Credentials);
  const [showCriteria, setShowCriteria] = useState(false);
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState(false);
  const [registerSuccess, setRegisterSuccess] = useState(false);
  const [alreadyExist, setAlreadyExist] = useState(false);
  const [error, setError] = useState(false);
  const [firstNameError, setFirstNameError] = useState(false);
  const [lastNameError, setLastNameError] = useState(false);
  const [passwordError, setPasswordError] = useState("" as string);
  const [validPassword, setValidPassword] = useState(false);
  const auth = new Auth();

  const validatePassword = () => {
    const password = credentials.password;

    if (password.length < 8) {
      setPasswordError("Password length should be atleast 8");
      return false;
    } else if (!/[#@*]/.test(password)) {
      setPasswordError("Password must contain a special character");
      return false;
    } else if (!/\d/.test(password)) {
      setPasswordError("Password must contain atleast a number");
      return false;
    } else if (!/[a-zA-Z]/.test(password)) {
      setPasswordError("Password must contain atleast a alphabet");
      return false;
    } else {
      setValidPassword(true);
      setPasswordError("");
      return true;
    }
  };

  const validateNames = (name: string) => {
    return /^\w+$/.test(name);
  };

  const toggleCriteria = () => {
    setShowCriteria(!showCriteria);
  };

  const toggleRegisterSuccess = () => {
    setRegisterSuccess(!registerSuccess);
  };

  const toggoleAlreadyExist = () => {
    setAlreadyExist(!alreadyExist);
  };

  const toggleError = () => {
    setError(!error);
  };

  const onSubmitRegister = async (e: FormEvent) => {
    e.preventDefault();

    const validPass = validatePassword();
    const validFirstName = validateNames(credentials.firstName.trim());
    const validLastName = validateNames(credentials.lastName.trim());

    validFirstName ? setFirstNameError(false) : setFirstNameError(true);
    validLastName ? setLastNameError(false) : setLastNameError(true);

    if (validFirstName && validLastName && validPass) {
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
          const [data, error] = await auth.signInUser({
            email: credentials.email,
            password: credentials.password,
          } as LoginCredentials);
          if (data?.token) {
            handleLogin && handleLogin(data?.token);
            navigate("/home");
          }
          if (error) {
            setIsLoading(false);
            setError(true);
          }
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
                {firstNameError && (
                  <Error style={{ color: "red" }}>
                    First Name should be only of one word
                  </Error>
                )}
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
                {lastNameError && (
                  <Error style={{ color: "red" }}>
                    Last Name should be only of one word
                  </Error>
                )}
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
                <PasswordContainer>
                  <Label>Password</Label>
                  <InfoContainer onClick={toggleCriteria}>
                    <Info color="blue" />
                  </InfoContainer>
                </PasswordContainer>
                <Input
                  value={credentials.password}
                  required={true}
                  type="password"
                  id="password"
                  onFocus={() => setIsFocused(!isFocused)}
                  onBlur={() => setIsFocused(false)}
                  onChange={(e) => {
                    setCredentials({
                      ...credentials,
                      password: e.target.value,
                    });
                  }}
                ></Input>
                {!validPassword && (
                  <Error style={{ color: "red" }}>{passwordError}</Error>
                )}
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
            <Modal onClose={toggleRegisterSuccess}>
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
