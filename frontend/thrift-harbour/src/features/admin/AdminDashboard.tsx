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
  const [allListedProducts, setAllListedProducts] = useState<
    AdminGetAllListingResponseType[]
  >([]);
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(true);
  const [viewProduct, setViewProduct] = useState(false);
  const [currentProduct, setCurrentProduct] =
    useState<AdminGetAllListingResponseType>({
      immediateSaleListingID: "",
      productName: "",
      price: 0,
      active: false,
      approved: false,
      rejected: false,
      productImages: [],
    });
  const [approvedListing, setApprovedListing] = useState<
    ApprovedDeniedProducts[]
  >([]);
  const [rejectedListing, setRejectedListing] = useState<
    ApprovedDeniedProducts[]
  >([]);

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
        console.log("in use effect");
        console.log("active tab", activeTab);
        const response = await admin.getImmediateListedProducts(token);

        if (response[0]?.status === 200) {
          setLoading(false);
          const data = response[0].data;

          data.map(async (product, index) => {
            const imagesResponse =
              await listing.getImmediateListedProductsImages(
                product.immediateSaleListingID,
                token
              );
            if (imagesResponse[0]?.status === 200) {
              product.productImages = imagesResponse[0].data.imageURLs;
            }
            setAllListedProducts([...data]);
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
        const response = await admin.getApprovedListing(token);

        if (response[0]?.status === 200) {
          setLoading(false);
          const data = response[0].data;

          data.map(async (product, index) => {
            const imagesResponse =
              await listing.getImmediateListedProductsImages(
                product.immediateSaleListingID,
                token
              );
            if (imagesResponse[0]?.status === 200) {
              product.imageURLs = imagesResponse[0].data.imageURLs;
            }
            setApprovedListing([...data]);
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
        const response = await admin.getRejectedListing(token);

        if (response[0]?.status === 200) {
          setLoading(false);
          const data = response[0].data;

          data.map(async (product, index) => {
            const imagesResponse =
              await listing.getImmediateListedProductsImages(
                product.immediateSaleListingID,
                token
              );
            if (imagesResponse[0]?.status === 200) {
              product.imageURLs = imagesResponse[0].data.imageURLs;
            }
            setRejectedListing([...data]);
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
              {allListedProducts.map((product) => {
                return (
                  <ProductList
                    product={product}
                    handleViewClick={handleViewClick}
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
                  />
                );
              })}
            </Grid>
          )}
        </>
      )}

      {viewProduct && (
        <Modal style={newModalStyle} onClose={toggleViewProduct}>
          <ImageSlider images={currentProduct.productImages} />
          <ViewProduct product={currentProduct} />
        </Modal>
      )}
    </>
  );
};
export default AdminDashboard;
