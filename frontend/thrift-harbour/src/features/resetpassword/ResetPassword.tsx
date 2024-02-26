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
import { ClipLoader } from "react-spinners";
import Modal from "../../components/ui-components/Modal/Modal";
import SuccessErrorModal from "../../components/ui-components/SuccessErrorModal/SuccessErrorModal";

const ResetPassword: React.FC = () => {
  const navigate = useNavigate();
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const [resetPasswordFields, setReserPasswordFields] = useState(
    {} as ResetPasswordFields
  );
  // const [notMatched, setNotMatched] = useState(false);
  const [passwordError, setPasswordError] = useState("" as string);
  const [validPassword, setValidPassword] = useState(false);
  const [inValidToken, setInValidToken] = useState(false);
  const [error, setError] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [success, setSuccess] = useState(false);

  const auth = new Auth();

  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);

  // Get the token parameter value
  const token = urlParams.get("token");

  const validatePassword = () => {
    const password = resetPasswordFields.confirmPassword;

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
    } else if (
      resetPasswordFields.newPassword.trim() !==
      resetPasswordFields.confirmPassword.trim()
    ) {
      setPasswordError("New Password and Confirm Password not matched!");
      return false;
    } else {
      setValidPassword(true);
      setPasswordError("");
      return true;
    }
  };

  const onSubmitResetPassword = async (e: FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    const validPass = validatePassword();

    if (validPass) {
      try {
        const [data, error] = await auth.resetPassword({
          password: resetPasswordFields.confirmPassword,
          token: token, //change this
        } as ResetPasswordRequest);
        if (data?.status === 200) {
          setIsLoading(false);
          setSuccess(true);
        } else if (error?.status === 500) {
          setInValidToken(true);
          setIsLoading(false);
        } else {
          setError(true);
          setIsLoading(false);
        }
      } catch (error) {
        setError(true);
        setIsLoading(false);
      }
    } else {
      setIsLoading(false);
    }
  };

  const toggleError = () => {
    setError(!error);
  };

  const toggleSuccess = () => {
    setSuccess(!success);
  };

  const toggleInvalidToken = () => {
    setInValidToken(!inValidToken);
  };

  return (
    <Container>
      <InputCard>
        <Title>Change Password</Title>
        <Form onSubmit={onSubmitResetPassword}>
          <Field>
            <Label>New Password</Label>
            <Input
              value={resetPasswordFields.newPassword}
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
              value={resetPasswordFields.confirmPassword}
              type="password"
              required={true}
              id="Confirm Password"
              onFocus={() => setIsFocused(true)}
              onBlur={() => setIsFocused(false)}
              onChange={(e) => {
                setReserPasswordFields({
                  ...resetPasswordFields,
                  confirmPassword: e.target.value,
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
                "Change Password"
              )}
            </RegisterButton>
          </Button>
        </Form>
      </InputCard>
      {/* {error && (
        <Modal onClose={toggleError}>
          <div>
            <p style={{ color: "red" }}>
              Something went wrong, please try again!
            </p>
          </div>
        </Modal>
      )} */}
      <SuccessErrorModal
        type="ERROR"
        message={"Something went wrong, please try again!"}
        open={error}
        setOpen={toggleError}
        title={"Success"}
      />
      {inValidToken && (
        <Modal onClose={toggleInvalidToken}>
          <div>
            <p style={{ color: "red" }}>Please regenerates the link!</p>
          </div>
        </Modal>
      )}
      <SuccessErrorModal
        type="SUCCESS"
        message={"Password Changed Successfully!"}
        open={success}
        setOpen={setSuccess}
        title={"Success"}
      />
    </Container>
  );
};
export default ResetPassword;
