import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuctionBanner from "../../components/ui-components/AuctionBanner/AuctionBanner";
import Navbar from "../../components/ui-components/navbar/Navbar";
import { ListingService } from "../../services/Listing";
import { Dates } from "../../utils/Dates";
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

export interface AuctionListingProps {}
const AuctionListing: React.FC<AuctionListingProps> = ({}) => {
  const navigate = useNavigate();
  const listing = new ListingService();
  const token = localStorage.getItem("token");
  const dates = new Dates();
  const [auctionListedProducts, setAuctionListedProducts] = useState([] as any);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);
  const today = new Date();
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

  const handleOnProductClick = (id: string, highestBidUser: any) => {
    console.log("auctionListedProducts.highestBidUser", highestBidUser);
    navigate(`/auctionsale-product-detail/${id}`, {
      state: highestBidUser,
    });
  };

  const currentUrl = window.location.href;
  const auction = currentUrl.includes("auction");

  useEffect(() => {
    (async function () {
      try {
        const response = await listing.getAllAuctionListedProducts(token);

        if (response[0]?.status === 200) {
          const data = response[0].data;

          setAuctionListedProducts(data);
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

  const navOptionsUsers = [
    {
      key: "List Product",
      value: "List Product",
      isSelected: false,
    },

    {
      key: "My Listed Products",
      value: "My Listed Products",
      isSelected: false,
    },
    {
      key: "Chats",
      value: "Chats",
      isSelected: false,
    },

    {
      key: "Buy Products",
      value: "Buy Products",
      isSelected: false,
    },
  ];

  console.log("in auction list");

  return (
    <>
      {auctionListedProducts.length === 0 ? (
        "Loading"
      ) : (
        <>
          <Navbar navOptions={navOptionsUsers} loginType={"USER"} />

          {auction && (
            <>
              <Header>Products</Header>
              <Grid>
                {auctionListedProducts.map((product: any) => {
                  return (
                    <>
                      <Card>
                        <ImageContainer
                          onClick={() =>
                            handleOnProductClick(
                              product.auctionSaleListingID,
                              product.highestBidUser
                            )
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
                          <Price>
                            ${" "}
                            {product.highestBid
                              ? product.highestBid
                              : product.startingBid}
                          </Price>
                        </NamePrice>
                      </Card>
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

export default AuctionListing;
