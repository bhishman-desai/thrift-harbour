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
import Modal from "../../components/ui-components/Modal/Modal";
import { ClipLoader } from "react-spinners";

const ForgotPassword: React.FC = () => {
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [email, setEmail] = useState("");
  const [emailSent, setEmailSent] = useState(false);
  const [invalidEmail, setInValidEmail] = useState(false);
  const [error, setError] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const auth = new Auth();

  const onSubmitForgotPassword = async (e: FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      const [data, error] = await auth.forgotPassword(email);
      if (data?.status === 200) {
        setEmail("");
        setEmailSent(true);
        setIsLoading(false);
      } else if (error?.status === 500) {
        setIsLoading(false);
        setInValidEmail(true);
      } else {
        setIsLoading(false);
        setError(true);
      }
    } catch (error) {
      setIsLoading(false);
    }
  };

  const toggleEmailSent = () => {
    setEmailSent(!emailSent);
  };

  const toggleInvalidEmail = () => {
    setInValidEmail(!invalidEmail);
  };

  const toggleError = () => {
    setError(!error);
  };

  return (
    <Container>
      <InputCard>
        <Title>Forgot Password</Title>
        <Form onSubmit={onSubmitForgotPassword}>
          <Field>
            <Label>E-mail ID</Label>
            <Input
              value={email}
              required={true}
              id="email"
              onFocus={() => setIsFocused(true)}
              onBlur={() => setIsFocused(false)}
              onChange={(e) => setEmail(e.target.value)}
            ></Input>
          </Field>

          <Button>
            <RegisterButton type="submit">
              {isLoading ? (
                <ClipLoader color="#ffffff" loading={isLoading} size={20} />
              ) : (
                "Reset password"
              )}
            </RegisterButton>
          </Button>
        </Form>
      </InputCard>

      {emailSent && (
        <Modal onClose={toggleEmailSent}>
          <div>
            <p style={{ color: "green" }}>Email sent successfully on {email}</p>
          </div>
        </Modal>
      )}
      {invalidEmail && (
        <Modal onClose={toggleInvalidEmail}>
          <div>
            <p style={{ color: "red" }}>Please enter valid Email</p>
          </div>
        </Modal>
      )}

      {error && (
        <Modal onClose={toggleError}>
          <div>
            <p style={{ color: "red" }}>
              Something went wrong, please try again!
            </p>
          </div>
        </Modal>
      )}
    </Container>
  );
};
export default ForgotPassword;
