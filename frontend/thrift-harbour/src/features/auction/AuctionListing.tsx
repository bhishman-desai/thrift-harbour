import { useNavigate } from "react-router-dom";
import {
  Card,
  Grid,
  Header,
  ImageContainer,
  Image,
  NamePrice,
  Name,
  Price,
} from "../buy-product/BuyProductsStyles";

const AuctionListing: React.FC = () => {
  const navigate = useNavigate();

  const productsList = [
    {
      auctionSaleListingID: "43f2c169-edcb-4c60-9d20-a8329e34492e",
      productName: "actuon",
      productDescription: "krutik",
      startingBid: 12.0,
      category: "Electronics",
      sellerEmail: "kulkarnikrutik2000@gmail.com",
      auctionSlot: null,
      imageURLs: [
        "https://mymitsandboxbucket.s3.ca-central-1.amazonaws.com/AUCTION/43f2c169-edcb-4c60-9d20-a8329e34492e/productImage1.png",
      ],
      active: true,
      createdDate: "2024-03-19T14:50:45.384+00:00",
      sellerID: 2,
      approved: true,
      rejected: false,
    },
    {
      auctionSaleListingID: "ca41b139-af30-4321-a44c-fb32457e843d",
      productName: "auction",
      productDescription: "Auciton sale exmpale",
      startingBid: 15.0,
      category: "Electronics",
      sellerEmail: "kulkarnikrutik2000@gmail.com",
      auctionSlot: null,
      imageURLs: [
        "https://mymitsandboxbucket.s3.ca-central-1.amazonaws.com/AUCTION/ca41b139-af30-4321-a44c-fb32457e843d/productImage1.png",
      ],
      active: true,
      createdDate: "2024-03-21T00:33:56.998+00:00",
      sellerID: 2,
      approved: true,
      rejected: false,
    },
  ];

  const handleOnProductClick = (id: string) => {
    navigate(`/auctionsale-product-detail/${id}`);
  };

  return (
    <>
      <Header>Products</Header>
      <Grid>
        {productsList.map((product) => {
          return (
            <>
              <Card>
                <ImageContainer
                  onClick={() =>
                    handleOnProductClick(product.auctionSaleListingID)
                  }
                >
                  <Image>
                    <img
                      src={product.imageURLs && product.imageURLs[0]}
                      height={"100%"}
                      width={"100%"}
                    />
                  </Image>
                </ImageContainer>

                <NamePrice>
                  <Name> {product.productName}</Name>
                  <Price>$ {product.startingBid}</Price>
                </NamePrice>
              </Card>
            </>
          );
        })}
      </Grid>
      t
    </>
  );
};

export default AuctionListing;
