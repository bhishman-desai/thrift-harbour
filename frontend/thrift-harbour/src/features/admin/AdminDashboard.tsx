import { useEffect, useState } from "react";
import Navbar from "../../components/ui-components/navbar/Navbar";
import { AdminServices } from "../../services/Admin";
import { ListingService } from "../../services/Listing";
import {
  AdminGetAllListingResponse,
  AdminGetAllListingResponseType,
} from "../../types/ListingTypes";
import { Button } from "../product-listing/listed-products/ListedProductsStyles";
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
  const navOptions = [
    {
      key: "Dashboard",
      value: "Dashboard",
      isSelected: true,
    },
  ];
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

  const handleOnClick = (key: string) => {
    setActiveTab(key);
  };

  useEffect(() => {
    (async function () {
      try {
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
      <Navbar navOptions={navOptions} />

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
      {allListedProducts.length === 0 ? (
        "Loading..."
      ) : (
        <Grid>
          {allListedProducts.map((product) => {
            return (
              <ProductCard>
                <ImageContainer>
                  <Image>
                    <img
                      height={"100%"}
                      width={"100%"}
                      src={product.productImages && product.productImages[0]}
                    />
                  </Image>
                </ImageContainer>
                <Rest>
                  <ProductNameAndDescription>
                    <ProductInfo>
                      <Title>Name: </Title>
                      <Value style={{ marginLeft: "4px" }}>
                        {product.productName}
                      </Value>
                    </ProductInfo>
                    <ProductInfo style={{ marginTop: "4px" }}>
                      <Title>Price: </Title>
                      <Value style={{ marginLeft: "4px" }}>
                        {"$" + product.price}
                      </Value>
                    </ProductInfo>
                    <ProductInfo style={{ marginTop: "4px" }}>
                      <Title>Status: </Title>
                      {getListingStatus(product.approved, product.rejected) ===
                      "Approved" ? (
                        <Value style={{ marginLeft: "4px", color: "green" }}>
                          {getListingStatus(product.approved, product.rejected)}
                        </Value>
                      ) : (
                        <Value style={{ marginLeft: "4px", color: "red" }}>
                          {getListingStatus(product.approved, product.rejected)}
                        </Value>
                      )}
                    </ProductInfo>
                  </ProductNameAndDescription>
                  <ViewButtonContainer>
                    <Button>View</Button>
                  </ViewButtonContainer>
                </Rest>
              </ProductCard>
            );
          })}
        </Grid>
      )}
    </>
  );
};
export default AdminDashboard;
