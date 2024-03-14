import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useAuth from "../../../hooks/useAuth";
import { Auth } from "../../../services/Auth";
import { useParams } from "react-router-dom";
import {
  ImmediateSaleProductDetail,
  UserDetails,
} from "../../../types/ProductSaleDetails";
import { ListingService } from "../../../services/Listing";
import Tab from "@mui/material/Tab";
import TabContext from "@mui/lab/TabContext";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";

import {
  Card,
  CardContent,
  Toolbar,
  Typography,
  Button,
  Box,
  CardHeader,
} from "@mui/material";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorderRounded";
import FavoriteIcon from "@mui/icons-material/Favorite";
import IconButton from "@mui/material/IconButton";
import { blue, red } from "@mui/material/colors";
import Rating from "@mui/material/Rating";

import Carousel from "../../../components/ui-components/carousel/Carousel";
import Navbar from "../../../components/ui-components/navbar/Navbar";
import Footer from "../../../components/ui-components/footer/Footer";
import AboutImmediateSale from "../../../components/ui-components/thrift-harbour-immediatesale-about/AboutImmediateSale";
import { ProfileLink } from "./ImmediatesaleStyles";
import Modal from "../../../components/ui-components/Modal/Modal";
import UserProfile from "../../user-profile/UserProfile";
import ChatWindow from "../../chat/ChatWindow";
import { GetSellersResponse } from "../../../types/ListingTypes";
import { UsersService } from "../../../services/Users";

const ImmediateListingSale = () => {
  const listing = new ListingService();
  const auth = new Auth();

  const navigate = useNavigate();

  const [value, setValue] = useState("1");

  const token = localStorage.getItem("token");

  const [authorized, setAuthorized] = useState(false);
  const [loginType, setLogintype] = useState<string | null>();
  const [selectedUser, setSelectedUser] = useState<GetSellersResponse>(
    {} as GetSellersResponse
  );
  const [viewProfile, setViewProfile] = useState(false);
  const [openChat, setOpenChat] = useState(false);
  const [user, setUser] = useState({} as UserDetails);

  const { id } = useParams();
  let [isFavorite, setIsFavorite] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);
  const users = new UsersService();

  const [immediateSaleProductDetail, setImmediateSaleProductDetail] =
    useState<ImmediateSaleProductDetail>();

  const newModalStyle: React.CSSProperties = {
    width: "80%",
    height: "80%",
    maxWidth: "600px",
    maxHeight: "600px",
  };

  const handleFav = () => {
    setIsFavorite((prevIsFav) => !prevIsFav);
  };

  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setValue(newValue);
  };

  const toggleViewProfile = () => {
    setViewProfile(!viewProfile);
  };

  useEffect(() => {
    (async function () {
      try {
        const response = await listing.immediateSaleProductDetail(id!, token);
        console.log("response", response);
        const imgresponse = await listing.getImmediateListedProductsImages(
          id!,
          token
        );

        const imageUrls = imgresponse[0]?.data.imageURLs;

        const productWithImages = { ...response, imageUrl: imageUrls };

        setImmediateSaleProductDetail(productWithImages);
        setSelectedUser({
          userID: response?.seller.userID || 1,
          email: response?.sellerEmail || "",
          firstName: response?.seller.firstName || "",
          lastName: response?.seller.lastName || "",
        });
        setLoading(false);
      } catch (error) {
        console.log(error);
        throw error;
      }
      try {
        const response = await users.getUserData(
          Number(localStorage.getItem("uId")),
          token
        );

        if (response[0]?.status === 200) {
          const data = response[0].data;
          console.log("data of user", data);
          setUser(data);
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

  return (
    <>
      {loading ? (
        "Loading..."
      ) : (
        <>
          {" "}
          {immediateSaleProductDetail && (
            <>
              <div style={{ height: "100%", padding: 0, borderRadius: 10 }}>
                <Card style={{ backgroundColor: "whitesmoke" }}>
                  <Typography
                    sx={{
                      textAlign: "left",
                      marginLeft: "1%",
                      marginTop: "1%",
                      fontSize: 24,
                    }}
                  >
                    <b>{immediateSaleProductDetail!.productName}</b>
                  </Typography>
                  <Typography
                    sx={{ textAlign: "left", marginLeft: "1%", fontSize: 14 }}
                  >
                    by {immediateSaleProductDetail?.category}
                  </Typography>
                  <CardContent>
                    <Carousel
                      imageUrls={immediateSaleProductDetail?.imageUrl}
                    ></Carousel>
                  </CardContent>
                  <CardContent>
                    <Toolbar sx={{ fontSize: 20 }}>
                      <span>
                        Seller Quoted Price: <span>&#36; </span>
                        {immediateSaleProductDetail!.price}
                      </span>
                      <Typography
                        variant="h6"
                        component="div"
                        sx={{ flexGrow: 1 }}
                      >
                        <Button onClick={handleFav}>
                          {isFavorite ? (
                            <FavoriteIcon sx={{ color: red[500] }} />
                          ) : (
                            <FavoriteBorderIcon />
                          )}
                        </Button>
                      </Typography>
                      <Button
                        onClick={() => setOpenChat(true)}
                        className="chat-button"
                        style={{ background: blue[400], color: "white" }}
                      >
                        Chat
                      </Button>
                    </Toolbar>
                  </CardContent>
                  <CardContent>
                    <Box
                      sx={{
                        width: "100%",
                        borderRadius: 2,
                        bgcolor: "whitesmoke",
                      }}
                    >
                      <TabContext value={value}>
                        <Box
                          sx={{
                            borderBottom: 1,
                            borderRadius: 1,
                            borderColor: "divider",
                          }}
                        >
                          <TabList
                            onChange={handleChange}
                            aria-label="lab API tabs example"
                          >
                            <Tab label="Description" value="1" />
                            <Tab label="Seller Details" value="2" />
                            <Tab
                              label="How Buying works in Thrift Harbour"
                              value="3"
                            />
                          </TabList>
                        </Box>
                        <TabPanel value="1">
                          <Card
                            sx={{
                              bgcolor: "whitesmoke",
                              borderColor: "divided",
                              boxShadow: 3,
                            }}
                          >
                            <CardContent>
                              <Typography>
                                <h3>Product Description:</h3>
                                {immediateSaleProductDetail?.productDescription}
                              </Typography>
                            </CardContent>
                          </Card>
                        </TabPanel>
                        <TabPanel value="2">
                          <Card
                            sx={{
                              bgcolor: "whitesmoke",
                              borderColor: "divided",
                              boxShadow: 3,
                            }}
                          >
                            <CardContent>
                              <ProfileLink onClick={() => setViewProfile(true)}>
                                <Typography
                                  style={{ color: "#731DCF" }}
                                  variant="h5"
                                  component="div"
                                >
                                  {immediateSaleProductDetail?.seller.firstName}{" "}
                                  {immediateSaleProductDetail?.seller.lastName}
                                </Typography>
                              </ProfileLink>
                              <div>
                                <Rating
                                  name="read-only"
                                  value={
                                    immediateSaleProductDetail?.seller
                                      .avgSellerRatings
                                  }
                                  precision={0.5}
                                  readOnly
                                />
                              </div>
                              <Typography sx={{ paddingLeft: "2px" }}>
                                (
                                {
                                  immediateSaleProductDetail?.seller
                                    .avgSellerRatings
                                }{" "}
                                of 5 stars)
                                {immediateSaleProductDetail?.seller
                                  .avgSellerRatings === 0 ? (
                                  <>
                                    <span> No Reviews</span>
                                    <br />
                                  </>
                                ) : (
                                  <></>
                                )}
                              </Typography>
                            </CardContent>
                          </Card>
                        </TabPanel>
                        <TabPanel value="3">
                          <AboutImmediateSale />
                        </TabPanel>
                      </TabContext>
                    </Box>
                    <br />
                  </CardContent>
                </Card>
              </div>
              <Footer />

              <ChatWindow
                open={openChat}
                sender={user as any}
                recipient={selectedUser}
                onClose={() => setOpenChat(false)}
              />

              {viewProfile && (
                <Modal style={newModalStyle} onClose={toggleViewProfile}>
                  <UserProfile
                    id={
                      immediateSaleProductDetail
                        ? immediateSaleProductDetail?.seller.userID
                        : 0
                    }
                  />
                </Modal>
              )}
            </>
          )}
        </>
      )}
    </>
  );
};

export default ImmediateListingSale;
