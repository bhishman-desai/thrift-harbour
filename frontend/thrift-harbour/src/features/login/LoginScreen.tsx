import { FormEvent, useState } from "react";
import { Container, InputCard } from "./LoginStyles";
import {
  Button,
  Field,
  Form,
  Input,
  Label,
  RegisterButton,
  Title,
} from "../registration/RegistrationStyles";
import { LoginCredentials } from "../../types/types";

const Login: React.FC = () => {
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [loginCredentials, setLoginCredentials] = useState(
    {} as LoginCredentials
  );
  const onSubmitLogin = (e: FormEvent) => {
    e.preventDefault();
    console.log("loginCredentials", loginCredentials);
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
            <RegisterButton type="submit">Login</RegisterButton>
          </Button>
        </Form>
      </InputCard>
    </Container>
  );
};

export default Login;
