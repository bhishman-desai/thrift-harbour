import { useEffect, useState } from "react";
import SuccessErrorModal from "../../../components/ui-components/SuccessErrorModal/SuccessErrorModal";
import { ListingService } from "../../../services/Listing";
import {
  ImmediateListing,
  ListingDataTypes,
} from "../../../types/ListingTypes";
import {
  Grid,
  ImageContainer,
  ProductCard,
  Image,
  ProductDescription,
  ProductName,
  ProductPrice,
  ApproveStatus,
  ViewButtonContainer,
  Button,
  Header,
  NoListing,
} from "./ListedProductsStyles";

const ListedProducts: React.FC = () => {
  const listing = new ListingService();
  const token = localStorage.getItem("token");

  const [immediateListedProducts, setImmediateListedProducts] = useState<
    ImmediateListing[]
  >([]);
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(true);
  const [noListedProducts, setNoListedProducts] = useState(false);

  useEffect(() => {
    (async function () {
      const response = await listing.getImmediateListedProducts(token);
      try {
        if (response[0]?.status === 200) {
          setLoading(false);
          const data = response[0].data;
          if (data.length === 0) {
            setNoListedProducts(true);
          }
          data.map(async (product, index) => {
            const imagesResponse =
              await listing.getImmediateListedProductsImages(
                product.immediateSaleListingID,
                token
              );
            if (imagesResponse[0]?.status === 200) {
              product.productImages = imagesResponse[0].data.imageURLs;
            }
            setImmediateListedProducts([...data]);
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

  const getListingStatus = (approved: boolean, rejected: boolean) => {
    if (approved) {
      return "Approved";
    } else if (rejected) {
      return "Rejected";
    } else {
      return "Pending";
    }
  };

  return (
    <>
      {loading ? (
        "Loading..."
      ) : (
        <>
          {noListedProducts ? (
            <NoListing>You have not listed any products!</NoListing>
          ) : (
            <>
              <Header style={{ marginLeft: "16px" }}>Listed Products</Header>
              <Grid>
                {immediateListedProducts.map((product, index) => {
                  {
                    console.log("product in map", product);
                  }
                  return (
                    <>
                      <ProductCard>
                        <ImageContainer>
                          <Image>
                            <img src={product.productImages[0]} />
                          </Image>
                        </ImageContainer>
                        <ProductName>{`Name: ${product.productName}`}</ProductName>
                        <ProductDescription>
                          {`Description: ${product.productDescription}`}
                        </ProductDescription>
                        <ProductPrice>{`Price: ${product.price}`}</ProductPrice>
                        <ApproveStatus>
                          Status :{" "}
                          {getListingStatus(product.approved, product.rejected)}
                        </ApproveStatus>
                        <ViewButtonContainer>
                          <Button>View</Button>
                        </ViewButtonContainer>
                      </ProductCard>
                    </>
                  );
                })}
              </Grid>
            </>
          )}
        </>
      )}
    </>
  );
};
export default ListedProducts;
