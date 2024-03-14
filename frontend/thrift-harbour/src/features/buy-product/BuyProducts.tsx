import Rating from "@mui/material/Rating";
import Stack from "@mui/material/Stack";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "../../components/ui-components/Modal/Modal";
import { ListingService } from "../../services/Listing";
import { ProductsService } from "../../services/Products";
import { ViewButtonContainer } from "../admin/AdminDashboardStyles";
import { Button } from "../product-listing/listed-products/ListedProductsStyles";
import UserProfile from "../user-profile/UserProfile";
import {
  Card,
  Grid,
  Header,
  Image,
  ImageContainer,
  Main,
  Name,
  NamePrice,
  Price,
  Ratings,
} from "./BuyProductsStyles";

const BuyProducts: React.FC = () => {
  const navigate = useNavigate();
  const products = new ProductsService();
  const listing = new ListingService();

  const [viewProfile, setViewProfile] = useState(false);
  const [loading, setLoading] = useState(true);
  const [productsList, setProductsList] = useState<any[]>([]);
  const [error, setError] = useState(false);

  const token = localStorage.getItem("token");

  const newModalStyle: React.CSSProperties = {
    width: "80%",
    height: "80%",
    maxWidth: "600px",
    maxHeight: "600px",
  };

  const toggleViewProfile = () => {
    setViewProfile(!viewProfile);
  };

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

  useEffect(() => {
    (async function () {
      try {
        const response = await products.getAllListedProducts(token);

        if (response[0]?.status === 200) {
          const data = response[0].data;

          data.map(async (product: any) => {
            const imagesResponse =
              await listing.getImmediateListedProductsImages(
                product.immediateSaleListingID,
                token
              );
            if (imagesResponse[0]?.status === 200) {
              product.imageURLs = imagesResponse[0].data.imageURLs;
            }
            setProductsList([...data]);
          });
          console.log("data after if", data);
        } else {
          setError(true);
          setLoading(false);
        }
      } catch (error) {
        setLoading(false);
        setError(true);
      }
    })();
  }, []);

  const handleOnProductClick = (id: string) => {
    navigate(`/immediatesal-product-detail/${id}`);
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
                  onClick={() => handleOnProductClick(product.id)}
                >
                  <Image>
                    <img
                      src={product.imageURLs && product.imageURLs[0]}
                      height={"100%"}
                      width={"100%"}
                    />
                  </Image>
                </ImageContainer>
                <Ratings>
                  <Stack spacing={1}>
                    <Rating
                      name="half-rating-read"
                      defaultValue={product.seller.avgSellerRatings}
                      precision={0.5}
                      readOnly
                    />
                  </Stack>
                </Ratings>
                <NamePrice>
                  <Name> {product.productName}</Name>
                  <Price>$ {product.price}</Price>
                </NamePrice>
                <ViewButtonContainer>
                  <Button onClick={() => setViewProfile(true)}>View</Button>
                </ViewButtonContainer>
              </Card>

              {viewProfile && (
                <Modal style={newModalStyle} onClose={toggleViewProfile}>
                  <UserProfile />
                </Modal>
              )}
            </>
          );
        })}
      </Grid>
    </>
  );
};

export default BuyProducts;
