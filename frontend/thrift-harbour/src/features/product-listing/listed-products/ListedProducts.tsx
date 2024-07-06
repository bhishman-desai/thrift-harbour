import { useEffect, useState } from "react";
import ProductList from "../../../components/ProductList/ProductList";
import SuccessErrorModal from "../../../components/ui-components/SuccessErrorModal/SuccessErrorModal";
import { ListingService } from "../../../services/Listing";
import {
  AuctionListing,
  ImmediateListing,
  ListingDataTypes,
} from "../../../types/ListingTypes";
import { TabsContainer } from "../../admin/AdminDashboardStyles";
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
          {activeTab === "Immediate Listing" && (
            <>
              {immediateListedProducts.length === 0 ? (
                <NoListing>You don't have any Immediate sale listing</NoListing>
              ) : (
                <>
                  <Grid>
                    {immediateListedProducts.map((product) => {
                      return (
                        <ProductList product={product} showViewButton={false} />
                      );
                    })}
                  </Grid>
                </>
              )}
            </>
          )}
          {activeTab === "Auction Listing" && (
            <>
              {auctionListedProducts.length === 0 ? (
                <NoListing>You don't have any Auction sale listing</NoListing>
              ) : (
                <>
                  <Grid>
                    {auctionListedProducts.map((product) => {
                      return (
                        <ProductList product={product} showViewButton={false} />
                      );
                    })}
                  </Grid>
                </>
              )}
            </>
          )}
        </>
      )}
    </>
  );
};
export default ListedProducts;
