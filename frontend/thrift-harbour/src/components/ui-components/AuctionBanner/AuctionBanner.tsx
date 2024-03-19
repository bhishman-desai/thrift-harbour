import { Dates } from "../../../utils/Dates";
import {
  AuctionDate,
  AuctionDesc,
  BannerContainer,
  Container,
} from "./AuctionBannerStyles";

const AuctionBanner = () => {
  const dates = new Dates();

  return (
    <>
      <Container>
        <BannerContainer>
          <AuctionDate>
            Next auction will be Live on {dates.getNextThursday()}
          </AuctionDate>
          <AuctionDesc>
            See the products and bid for your favourite product in Auction!
          </AuctionDesc>
        </BannerContainer>
        <img
          src="https://mymitsandboxbucket.s3.ca-central-1.amazonaws.com/auction_icon.jpg"
          height={"100%"}
          width={"30%"}
        />
      </Container>
    </>
  );
};

export default AuctionBanner;
