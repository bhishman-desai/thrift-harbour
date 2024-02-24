import { useEffect, useState } from "react";
import SuccessErrorModal from "../../../components/ui-components/SuccessErrorModal/SuccessErrorModal";
import { ListingService } from "../../../services/Listing";
import {
  AuctionListing,
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
  TabsContainer,
  Tab,
} from "./ListedProductsStyles";

const ListedProducts: React.FC = () => {
  const listing = new ListingService();
  const token = localStorage.getItem("token");

  const [immediateListedProducts, setImmediateListedProducts] = useState<
    ImmediateListing[]
  >([]);
  const [auctionListedProducts, setAuctionListedProducts] = useState<
    AuctionListing[]
  >([]);
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(true);
  const [noListedProducts, setNoListedProducts] = useState(false);

  const [filteredProducts, setFilteredProdducts] = useState([]);

  const [activeTab, setActiveTab] = useState("Immediate Listing");

  const tabs = [
    {
      key: "Immediate Listing",
      value: "Immediate Listing",
    },
    {
      key: "Auction Listing",
      value: "Auction Listing",
    },
  ];

  const handleOnClick = (key: string) => {
    setActiveTab(key);
  };

  useEffect(() => {
    (async function () {
      try {
        const response = await listing.getImmediateListedProducts(token);

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

  useEffect(() => {
    (async function () {
      try {
        const response = await listing.getAuctionListedProducts(token);
        if (response[0]?.status === 200) {
          setLoading(false);
          const data = response[0].data;
          if (data.length === 0) {
            setNoListedProducts(true);
          }
          data.map(async (product, index) => {
            const imagesResponse = await listing.getAuctionListedProductsImages(
              product.auctionSaleListingID,
              token
            );
            if (imagesResponse[0]?.status === 200) {
              product.productImages = imagesResponse[0].data.imageURLs;
            }
            setAuctionListedProducts([...data]);
          });

          //   console.log("data after if", data);
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
          <TabsContainer>
            {tabs.map((tab) => (
              <Tab
                selected={activeTab === tab.key}
                key={tab.key}
                onClick={() => handleOnClick(tab.key)}
                className={activeTab === tab.key ? "active" : ""}
                style={{
                  borderBottom: activeTab === tab.key ? "2px solid blue" : "",
                }}
              >
                {tab.value}
              </Tab>
            ))}
          </TabsContainer>
          <Grid>
            {activeTab === "Immediate Listing" && (
              <>
                {immediateListedProducts.length === 0 ? (
                  <NoListing>
                    You don't have any Immediate sale listing
                  </NoListing>
                ) : (
                  immediateListedProducts.map((product, index) => {
                    return (
                      <>
                        <ProductCard>
                          <ImageContainer>
                            <Image>
                              <img
                                src={
                                  product.productImages &&
                                  product.productImages[0]
                                }
                                height={"100%"}
                                width={"100%"}
                              />
                            </Image>
                          </ImageContainer>
                          <ProductName>{`Name: ${product.productName}`}</ProductName>
                          <ProductDescription>
                            {`Description: ${product.productDescription}`}
                          </ProductDescription>
                          <ProductPrice>{`Price: ${product.price}`}</ProductPrice>
                          <ApproveStatus>
                            Status :
                            {getListingStatus(
                              product.approved,
                              product.rejected
                            )}
                          </ApproveStatus>
                          <ViewButtonContainer>
                            <Button>View</Button>
                          </ViewButtonContainer>
                        </ProductCard>
                      </>
                    );
                  })
                )}
              </>
            )}
            {activeTab === "Auction Listing" && (
              <>
                {auctionListedProducts.length === 0 ? (
                  <NoListing>You don't have any Auction sale listing</NoListing>
                ) : (
                  auctionListedProducts.map((product, index) => {
                    return (
                      <>
                        <ProductCard>
                          <ImageContainer>
                            <Image>
                              <img
                                height={"100%"}
                                width={"100%"}
                                src={
                                  product.productImages &&
                                  product.productImages[0]
                                }
                              />
                            </Image>
                          </ImageContainer>
                          <ProductName>{`Name: ${product.productName}`}</ProductName>
                          <ProductDescription>
                            {`Description: ${product.productDescription}`}
                          </ProductDescription>
                          <ApproveStatus>
                            Status :
                            {getListingStatus(
                              product.approved,
                              product.rejected
                            )}
                          </ApproveStatus>
                          <ViewButtonContainer>
                            <Button>View</Button>
                          </ViewButtonContainer>
                        </ProductCard>
                      </>
                    );
                  })
                )}
              </>
            )}
          </Grid>
        </>
      )}
    </>
  );
};
export default ListedProducts;
