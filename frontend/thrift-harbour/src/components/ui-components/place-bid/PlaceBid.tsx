import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ClipLoader } from "react-spinners";
import {
  Field,
  Input,
  Label,
  RegisterButton,
  Error,
} from "../../../features/registration/RegistrationStyles";
import { Container, Header, Instruct } from "./PlaceBidStyled";

const PlaceBid: React.FC = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [bidAmount, setBidAmount] = useState<number>();
  const [amountError, setAmountError] = useState(false);

  const navigate = useNavigate();

  const onPlaceBid = () => {
    if (bidAmount! < 1.23) {
      setAmountError(true);
      return;
    } else {
      setAmountError(false);
      console.log("bid amount", bidAmount);
    }
  };

  return (
    <Container>
      <Header>Highest bid is $1.23</Header>
      <Instruct>Place your bid below</Instruct>
      <Field style={{ marginTop: "4px" }}>
        <Input
          value={bidAmount}
          type="number"
          required={true}
          id="password"
          onChange={(e) => {
            setBidAmount(Number(e.target.value));
          }}
        ></Input>
        {amountError && (
          <Error style={{ color: "red" }}>
            Bid amount must be greater than current bid amount
          </Error>
        )}
      </Field>
      <RegisterButton
        onClick={() => onPlaceBid()}
        type="submit"
        style={{ marginTop: "8px" }}
      >
        {isLoading ? (
          <ClipLoader color="#ffffff" loading={isLoading} size={20} />
        ) : (
          "Place Bid"
        )}
      </RegisterButton>
    </Container>
  );
};

export default PlaceBid;
