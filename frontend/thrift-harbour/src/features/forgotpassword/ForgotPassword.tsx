import { FormEvent, useEffect, useState } from "react";
import { Container, InputCard } from "./ForgotPasswordStyles";
import {
  Button,
  Field,
  Form,
  Input,
  Label,
  RegisterButton,
  Title,
} from "../registration/RegistrationStyles";
import { ForgotPasswordCredentials } from "../../types/AuthTypes";
import { useNavigate } from "react-router-dom";
import useAuth from "../../hooks/useAuth";
import { Auth } from "../../services/Auth";

const ForgotPassword: React.FC = () => {
  const { token } = useAuth();
  const navigate = useNavigate();
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [ForgotPasswordCredentials, setForgotPasswordCredentials] = useState(
    {} as ForgotPasswordCredentials
  );
  const auth = new Auth();

  const onSubmitForgotPassword = async (e: FormEvent) => {
    e.preventDefault();
    // navigate("/newpassword");
    try {
      const [data, error] = await auth.forgotPassword(
        ForgotPasswordCredentials.email
      );
      if (data?.status === 200) {
        navigate("/home");
      } else if (error?.status === 500) {
        console.log("message", data?.message);
      } else {
        console.log("error");
      }
    } catch (error) {
      console.log("somwthing wrong");
    }
  };

  return (
    <Container>
      <InputCard>
        <Title>Forgot Password</Title>
        <Form onSubmit={onSubmitForgotPassword}>
          <Field>
            <Label>E-mail ID</Label>
            <Input
              required={true}
              id="email"
              onFocus={() => setIsFocused(true)}
              onBlur={() => setIsFocused(false)}
              onChange={(e) =>
                setForgotPasswordCredentials({
                  ...ForgotPasswordCredentials,
                  email: e.target.value,
                })
              }
            ></Input>
          </Field>

          <Button>
            <RegisterButton type="submit">Reset password</RegisterButton>
          </Button>
        </Form>
      </InputCard>
    </Container>
  );
};
export default ForgotPassword;
