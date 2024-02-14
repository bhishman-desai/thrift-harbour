import { FormEvent, useEffect, useState } from "react";
import { Container, InputCard } from "../forgotpassword/ForgotPasswordStyles";
import {
  Button,
  Field,
  Form,
  Input,
  Label,
  RegisterButton,
  Title,
} from "../registration/RegistrationStyles";
import { ResetPasswordCredentials } from "../../types/AuthTypes";
import { useNavigate } from "react-router-dom";
import useAuth from "../../hooks/useAuth";
import { Auth } from "../../services/Auth";

const ResetPassword: React.FC = () => {
  const { token } = useAuth();
  const navigate = useNavigate();
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [ResetPasswordCredentials, setResetPasswordCredentials] = useState(
    {} as ResetPasswordCredentials
  );
  const auth = new Auth();

  useEffect(() => {
    if (token?.length) {
      navigate("/home");
    }
  }, [token]);

  const onSubmitResetPassword = async (e: FormEvent) => {
    e.preventDefault();
    navigate("/newpassword");
  };

  return (
    <Container>
      <InputCard>
        <Title>Change Password</Title>
        <Form onSubmit={onSubmitResetPassword}>
          <Field>
            <Label>New Password</Label>
            <Input
              required={true}
              id="New Password"
              onFocus={() => setIsFocused(true)}
              onBlur={() => setIsFocused(false)}
              onChange={(e) =>
                setResetPasswordCredentials({
                  ...ResetPasswordCredentials,
                  newpassword: e.target.value,
                })
              }
            ></Input>
          </Field>
          <Field>
            <Label>Confirm Password</Label>
            <Input
              required={true}
              id="Confirm Password"
              onFocus={() => setIsFocused(true)}
              onBlur={() => setIsFocused(false)}
              onChange={(e) =>
                setResetPasswordCredentials({
                  ...ResetPasswordCredentials,
                  confirmpassword: e.target.value,
                })
              }
            ></Input>
          </Field>
          <Button>
            <RegisterButton type="submit">Change password</RegisterButton>
          </Button>
        </Form>
      </InputCard>
    </Container>
  );
};
export default ResetPassword;
