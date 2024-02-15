import { FormEvent, useEffect, useState } from "react";
import { Container, InputCard } from "./LoginStyles";
import {
  Button,
  Field,
  Form,
  Input,
  Label,
  LoginLink,
  Message,
  RegisterButton,
  Title,
} from "../registration/RegistrationStyles";
import { LoginCredentials } from "../../types/AuthTypes";
import { Link, useNavigate } from "react-router-dom";
import useAuth from "../../hooks/useAuth";
import { Auth } from "../../services/Auth";
import Modal from "../../components/ui-components/Modal/Modal";
import { ClipLoader } from "react-spinners";

const Login: React.FC = () => {
  const { token, handleLogin } = useAuth();
  const navigate = useNavigate();
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [loginCredentials, setLoginCredentials] = useState(
    {} as LoginCredentials
  );
  const [errorInLogin, setErrorInLogin] = useState(false);
  const [badCredentials, setBadCredentials] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const auth = new Auth();
  useEffect(() => {
    if (token?.length) {
      navigate("/home");
    }
  }, [token]);

  const onSubmitLogin = async (e: FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      const [data, error] = await auth.signInUser(loginCredentials);
      if (data?.token) {
        setErrorInLogin(false);
        setIsLoading(false);
        handleLogin && handleLogin(data?.token);
        navigate("/home");
      } else if (error?.status === 401) {
        setIsLoading(false);
        setErrorInLogin(false);
        setBadCredentials(true);
      } else {
        setIsLoading(false);
        setErrorInLogin(true);
      }
    } catch (error) {
      setIsLoading(false);
      setErrorInLogin(true);
    }
  };

  const toggleErrorInLogin = () => {
    setErrorInLogin(!toggleErrorInLogin);
  };

  const togglebadCredentials = () => {
    setBadCredentials(!togglebadCredentials);
  };
  return (
    <Container>
      <InputCard>
        <Title>Login to Thrift Harbour</Title>
        <Form onSubmit={onSubmitLogin}>
          <Field>
            <Label>E-mail ID</Label>
            <Input
              required={true}
              id="email"
              onFocus={() => setIsFocused(true)}
              onBlur={() => setIsFocused(false)}
              onChange={(e) =>
                setLoginCredentials({
                  ...loginCredentials,
                  email: e.target.value,
                })
              }
            ></Input>
          </Field>
          <Field>
            <Label>Password</Label>
            <Input
              type="password"
              required={true}
              id="password"
              onFocus={() => setIsFocused(!isFocused)}
              onBlur={() => setIsFocused(false)}
              onChange={(e) =>
                setLoginCredentials({
                  ...loginCredentials,
                  password: e.target.value,
                })
              }
            ></Input>
          </Field>

          <Button>
            <RegisterButton type="submit" style={{ marginTop: "8px" }}>
              {isLoading ? (
                <ClipLoader color="#ffffff" loading={isLoading} size={20} />
              ) : (
                "Login"
              )}
            </RegisterButton>
            <LoginLink>
              <Link to="/forgot-password">Forgot password</Link>
            </LoginLink>
            <LoginLink>
              <Link to="/">Don't have an account ? Sign Up</Link>
            </LoginLink>
          </Button>
        </Form>
      </InputCard>
      {errorInLogin && (
        <Modal onClose={toggleErrorInLogin}>
          <Message>
            <p style={{ color: "Red" }}>
              Something went wrong, please try again!
            </p>
          </Message>
        </Modal>
      )}
      {badCredentials && (
        <Modal onClose={togglebadCredentials}>
          <Message>
            <p style={{ color: "Red" }}>Wrong Email or Password !</p>
          </Message>
        </Modal>
      )}
    </Container>
  );
};

export default Login;
