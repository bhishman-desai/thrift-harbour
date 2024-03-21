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
import { BidService } from "../../../services/Bid";
import { Container, Header, Instruct } from "./PlaceBidStyled";

export interface PlaceBidProps {
  auctionSaleListingID: string;
  highestBid: number;
}
const PlaceBid: React.FC<PlaceBidProps> = ({
  auctionSaleListingID,
  highestBid,
}) => {
  const bid = new BidService();
  const token = localStorage.getItem("token");

  const [bidAmount, setBidAmount] = useState<number>();
  const [amountError, setAmountError] = useState(false);
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  const onPlaceBid = async () => {
    console.log("bid amount", bidAmount);
    console.log("highest bid", highestBid);
    if (bidAmount! <= highestBid) {
      console.log("in if");
      setAmountError(true);
    }

    if (bidAmount! > highestBid) {
      console.log("in else");
      setLoading(true);
      setAmountError(false);
      try {
        const response = await bid.placeBid(token!, {
          auctionSaleListingID: auctionSaleListingID,
          bidAmount: bidAmount,
        });
        console.log("response of place bid", response);
        if (response[0] === "Bid Placed successfully") {
          setSuccess(true);
          setLoading(false);
          setError(false);
        } else {
          setLoading(false);
          setError(true);
        }
      } catch (error) {
        setLoading(false);
        setError(true);
      }
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
      {success && <p style={{ color: "green" }}> Bid Placed Successfully! </p>}
      {error && (
        <p style={{ color: "red" }}>Error in placing bid, please try again!</p>
      )}
      <RegisterButton
        onClick={() => onPlaceBid()}
        type="submit"
        style={{ marginTop: "8px" }}
      >
        {loading ? (
          <ClipLoader color="#ffffff" loading={loading} size={20} />
        ) : (
          "Place Bid"
        )}
      </RegisterButton>
    </Container>
  );
};

export default PlaceBid;
