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
  Error,
} from "../registration/RegistrationStyles";
import {
  ResetPasswordFields,
  ResetPasswordRequest,
} from "../../types/AuthTypes";
import { useNavigate } from "react-router-dom";
import useAuth from "../../hooks/useAuth";
import { Auth } from "../../services/Auth";

const ResetPassword: React.FC = () => {
  const navigate = useNavigate();
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [resetPasswordFields, setReserPasswordFields] = useState(
    {} as ResetPasswordFields
  );
  const [notMatched, setNotMatched] = useState(false);
  const auth = new Auth();

  const url = window.location.href;
  const token = url.substring(url.lastIndexOf("/") + 1);
  console.log("token", token);

  const onSubmitResetPassword = async (e: FormEvent) => {
    e.preventDefault();
    if (
      resetPasswordFields.newPassword.trim() !==
      resetPasswordFields.confirmPassword.trim()
    ) {
      setNotMatched(true);
    } else {
      try {
        const [data, error] = await auth.resetPassword({
          password: resetPasswordFields.confirmPassword,
          token: "f7d389ec-ece5-4627-85d2-fc5c3cec3281", //change this
        } as ResetPasswordRequest);
        if (data?.status === 200) {
          console.log("success");
        } else if (error?.status === 500) {
          console.log("message", data?.message);
        } else {
          console.log("error");
        }
      } catch (error) {
        console.log("somwthing wrong");
      }
    }
  };

  return (
    <Container>
      <InputCard>
        <Title>Change Password</Title>
        <Form onSubmit={onSubmitResetPassword}>
          <Field>
            <Label>New Password</Label>
            <Input
              type="password"
              required={true}
              id="New Password"
              onFocus={() => setIsFocused(true)}
              onBlur={() => setIsFocused(false)}
              onChange={(e) =>
                setReserPasswordFields({
                  ...resetPasswordFields,
                  newPassword: e.target.value,
                })
              }
            ></Input>
          </Field>
          <Field>
            <Label>Confirm Password</Label>
            <Input
              type="password"
              required={true}
              id="Confirm Password"
              onFocus={() => setIsFocused(true)}
              onBlur={() => setIsFocused(false)}
              onChange={(e) =>
                setReserPasswordFields({
                  ...resetPasswordFields,
                  confirmPassword: e.target.value,
                })
              }
            ></Input>
            {notMatched && (
              <Error style={{ color: "red" }}>Password did not match!</Error>
            )}
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
