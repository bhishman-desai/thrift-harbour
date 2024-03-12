import Rating from "@mui/material/Rating";
import Stack from "@mui/material/Stack";
import { useNavigate } from "react-router-dom";
import {
  Card,
  Grid,
  Header,
  Image,
  ImageContainer,
  Name,
  NamePrice,
  Price,
  Ratings,
} from "./BuyProductsStyles";

const BuyProducts: React.FC = () => {
  const navigate = useNavigate();

  const auctionListedProducts = [
    {
      productName: "chair",
      price: 50,
      approved: true,
      productImages: [
        "https://mymitsandboxbucket.s3.ca-central-1.amazonaws.com/DIRECT/fe516a57-e098-4fdc-a7ee-371e6f911ec2/productImage1.png",
      ],
      id: "1",
    },
    {
      productName: "chair",
      price: 50,
      approved: true,
      productImages: [
        "https://mymitsandboxbucket.s3.ca-central-1.amazonaws.com/DIRECT/fe516a57-e098-4fdc-a7ee-371e6f911ec2/productImage1.png",
      ],
      id: "2",
    },
    {
      productName: "chair",
      price: 50,
      approved: true,
      productImages: [
        "https://mymitsandboxbucket.s3.ca-central-1.amazonaws.com/DIRECT/fe516a57-e098-4fdc-a7ee-371e6f911ec2/productImage1.png",
      ],
      id: "3",
    },
    {
      productName: "chair",
      price: 50,
      approved: true,
      productImages: [
        "https://mymitsandboxbucket.s3.ca-central-1.amazonaws.com/DIRECT/fe516a57-e098-4fdc-a7ee-371e6f911ec2/productImage1.png",
      ],
      id: "4",
    },
  ];

  const handleOnProductClick = (id: string) => {
    navigate(`/immediatesal-product-detail/${id}`);
  };
  return (
    <>
      <Header>Products</Header>

      <Grid>
        {auctionListedProducts.map((product) => {
          return (
            <>
              <Card onClick={() => handleOnProductClick(product.id)}>
                <ImageContainer>
                  <Image>
                    <img
                      src={product.productImages[0]}
                      height={"100%"}
                      width={"100%"}
                    />
                  </Image>
                </ImageContainer>
                <Ratings>
                  <Stack spacing={1}>
                    <Rating
                      name="half-rating-read"
                      defaultValue={4.5}
                      precision={0.5}
                      readOnly
                    />
                  </Stack>
                </Ratings>
                <NamePrice>
                  <Name> {product.productName}</Name>
                  <Price>$ {product.price}</Price>
                </NamePrice>
              </Card>
            </>
          );
        })}
      </Grid>
    </>
  );
};

export default BuyProducts;
