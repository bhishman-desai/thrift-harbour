import { truncate } from "fs/promises";
import { useEffect, useState } from "react";
import ProductList from "../../components/ProductList/ProductList";
import ImageSlider from "../../components/ui-components/image-slider/ImageSlider";
import { Container } from "../../components/ui-components/image-slider/ImageSliderStyles";
import Modal from "../../components/ui-components/Modal/Modal";
import Navbar from "../../components/ui-components/navbar/Navbar";
import { AdminServices } from "../../services/Admin";
import { ListingService } from "../../services/Listing";
import {
  AdminGetAllListingResponse,
  AdminGetAllListingResponseType,
  ApprovedDeniedProducts,
} from "../../types/ListingTypes";
import { Button } from "../product-listing/listed-products/ListedProductsStyles";
import ViewProduct from "../product/ViewProduct";
import { UserInfo } from "../product/ViewProductsStyles";
import {
  Grid,
  ImageContainer,
  ProductCard,
  Tab,
  TabsContainer,
  Image,
  ProductNameAndDescription,
  ProductDescription,
  ProductInfo,
  Rest,
  ViewButtonContainer,
  ProductPrice,
  ApproveStatus,
  Title,
  Value,
} from "./AdminDashboardStyles";

const AdminDashboard: React.FC = () => {
  const admin = new AdminServices();
  const listing = new ListingService();
  const token = localStorage.getItem("token");
  const [activeTab, setActiveTab] = useState("All Listed Products");
  // const [allListedProducts, setAllListedProducts] = useState<
  //   AdminGetAllListingResponseType[]
  // >([]);
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(true);
  const [viewProduct, setViewProduct] = useState(false);
  const [currentProduct, setCurrentProduct] = useState({} as any);
  const [approvedListing, setApprovedListing] = useState<
    ApprovedDeniedProducts[]
  >([]);
  const [rejectedListing, setRejectedListing] = useState<any[]>([]);
  const [allListedProducts, setAllListedProducts] = useState([] as any);

  const tabs = [
    {
      key: "All Listed Products",
      value: "All Listed Products",
    },
    {
      key: "Approved Products",
      value: "Approved Products",
    },
    {
      key: "Rejected Products",
      value: "Rejected Products",
    },
  ];

  useEffect(() => {
    (async function () {
      try {
        const response = await admin.getAllProductListing(token!);
        if (response[0]?.status === 200) {
          setLoading(false);
          const data = response[0].data;
          data.map(async (product: any) => {
            if (product.immediateSaleListingID) {
              const imagesResponse =
                await listing.getImmediateListedProductsImages(
                  product.immediateSaleListingID,
                  token
                );
              if (imagesResponse[0]?.status === 200) {
                product.productImages = imagesResponse[0].data.imageURLs;
              }
              setAllListedProducts([...data]);
            }
          });
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

  // useEffect(() => {
  //   (async function () {
  //     try {
  //       const response = await admin.getImmediateListedProducts(token);

  //       if (response[0]?.status === 200) {
  //         setLoading(false);
  //         const data = response[0].data;

  //         data.map(async (product, index) => {
  //           const imagesResponse =
  //             await listing.getImmediateListedProductsImages(
  //               product.immediateSaleListingID,
  //               token
  //             );
  //           if (imagesResponse[0]?.status === 200) {
  //             product.productImages = imagesResponse[0].data.imageURLs;
  //           }
  //           setAllListedProducts([...data]);
  //         });
  //       } else {
  //         setError(true);
  //         setLoading(false);
  //       }
  //     } catch (error) {
  //       setLoading(false);
  //       setError(true);
  //     }
  //   })();
  // }, []);

  useEffect(() => {
    (async function () {
      try {
        const immediateApproved = await admin.getApprovedListing(token);
        const auctionApproved =
          await admin.getApprovedListingAuctionSale(token);

        if (
          immediateApproved[0]?.status === 200 &&
          auctionApproved[0]?.status
        ) {
          setLoading(false);
          const data = [
            ...immediateApproved[0].data,
            ...auctionApproved[0].data,
          ];

          data.map(async (product, index) => {
            if (product.immediateSaleListingID) {
              const imagesResponse =
                await listing.getImmediateListedProductsImages(
                  product.immediateSaleListingID,
                  token
                );
              if (imagesResponse[0]?.status === 200) {
                product.imageURLs = imagesResponse[0].data.imageURLs;
              }
            }

            setApprovedListing([...data]);
          });
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
        const rejectedListingImmediate = await admin.getRejectedListing(token);
        const rejectedListingAuction =
          await admin.getRejectedListingAuctionSale(token);

        if (
          rejectedListingImmediate[0]?.status === 200 &&
          rejectedListingAuction[0]?.status
        ) {
          setLoading(false);
          const data = [
            ...rejectedListingImmediate[0].data,
            ...rejectedListingAuction[0].data,
          ];

          data.map(async (product, index) => {
            if (product.immediateSaleListingID) {
              const imagesResponse =
                await listing.getImmediateListedProductsImages(
                  product.immediateSaleListingID,
                  token
                );
              if (imagesResponse[0]?.status === 200) {
                product.imageURLs = imagesResponse[0].data.imageURLs;
              }
            }
          });
          setRejectedListing([...data]);
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

  const toggleViewProduct = () => {
    setViewProduct(!viewProduct);
  };

  const handleViewClick = (product: AdminGetAllListingResponseType) => {
    setCurrentProduct(product);
    setViewProduct(true);
  };

  const newModalStyle: React.CSSProperties = {
    width: "80%",
    height: "80%",
    maxWidth: "600px",
    maxHeight: "600px",
  };

  const handleOnClick = (key: string) => {
    setActiveTab(key);
  };

  return (
    <>
      {/* <Navbar navOptions={navOptions} /> */}

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
      {activeTab === "All Listed Products" && (
        <>
          {allListedProducts.length === 0 ? (
            "Loading..."
          ) : (
            <Grid>
              {allListedProducts.map((product: any) => {
                return (
                  <ProductList
                    product={product}
                    handleViewClick={handleViewClick}
                    showViewButton={true}
                  />
                );
              })}
            </Grid>
          )}
        </>
      )}

      {activeTab === "Approved Products" && (
        <>
          {approvedListing.length === 0 ? (
            "Loading..."
          ) : (
            <Grid>
              {approvedListing.map((product) => {
                return (
                  <ProductList
                    product={product}
                    handleViewClick={handleViewClick}
                    showViewButton={false}
                  />
                );
              })}
            </Grid>
          )}
        </>
      )}

      {activeTab === "Rejected Products" && (
        <>
          {rejectedListing.length === 0 ? (
            "Loading..."
          ) : (
            <Grid>
              {rejectedListing.map((product) => {
                return (
                  <ProductList
                    product={product}
                    handleViewClick={handleViewClick}
                    showViewButton={false}
                  />
                );
              })}
            </Grid>
          )}
        </>
      )}

      {viewProduct && (
        <Modal style={newModalStyle} onClose={toggleViewProduct}>
          <ImageSlider
            images={currentProduct.productImages || currentProduct.imageURLs}
          />
          <ViewProduct product={currentProduct} />
        </Modal>
      )}
    </>
  );
};
export default AdminDashboard;
