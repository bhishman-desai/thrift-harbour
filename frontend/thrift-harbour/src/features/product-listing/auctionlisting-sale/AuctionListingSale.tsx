import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react"
import { Auth } from "../../../services/Auth";
import useAuth from "../../../hooks/useAuth";
import { ListingService } from "../../../services/Listing";
import { AuctionSaleProductDetail, UserDetails } from "../../../types/ProductSaleDetails";

import { Card, CardContent, Toolbar, Typography, Button, Box } from "@mui/material";
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorderRounded';
import FavoriteIcon from '@mui/icons-material/Favorite';
import { blue, red } from "@mui/material/colors";
import Rating from '@mui/material/Rating';

import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';

import Carousel from "../../../components/ui-components/carousel/Carousel";
import Navbar from "../../../components/ui-components/navbar/Navbar";
import Footer from "../../../components/ui-components/footer/Footer";
import AboutImmediateSale from "../../../components/ui-components/thrift-harbour-immediatesale-about/AboutImmediateSale";
import AboutAuctionSale from "../../../components/ui-components/thrift-harbour-auctionsale-about/AboutAuctionSale";


const AuctionListingSale = () => {

    const loren = " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend malesuada semper. Mauris aliquam tempus ultrices. Sed scelerisque nunc vitae justo rutrum, vel lobortis nibh molestie. Vivamus arcu ante, maximus dapibus elementum non, volutpat non arcu. Ut at lacinia nisl, nec dictum lectus. Sed sed vestibulum metus. Morbi aliquam lorem lectus, eu sodales massa viverra nec. Aenean vehicula lectus id ex viverra, in aliquam orci commodo. Cras quis elit et lacus congue blandit viverra placerat eros. Proin sit amet neque bibendum, dapibus tellus a, maximus nisi. Sed facilisis massa justo, at dapibus risus malesuada et. Proin et odio sed ligula euismod interdum ac ut sem. Suspendisse vitae tellus tellus. Proin pellentesque nisi vel sapien dictum tincidunt. Aenean semper risus sed nisl pellentesque consectetur.Vivamus quis laoreet massa. Aenean tincidunt mi eu enim faucibus, et hendrerit lorem fermentum. Praesent quis mollis dolor. Aliquam ut nulla vestibulum, porta dui tempus, bibendum justo. Nulla lacinia id risus in sollicitudin. Curabitur varius felis elit, condimentum pharetra nisl cursus sed. Sed venenatis tristique hendrerit. Nulla aliquam lacus augue, a ullamcorper tellus posuere id.Phasellus sed diam et magna placerat porta. Quisque mattis pulvinar velit, non venenatis lectus pharetra eget. Vestibulum eu purus gravida dolor euismod pretium. Ut lacus tellus, mattis at magna tristique, commodo tempus lorem. Sed commodo, elit et feugiat placerat, neque mauris iaculis mi, et egestas neque tortor at ex. In mollis suscipit gravida. Quisque ac efficitur lacus, condimentum vestibulum lectus. Quisque placerat tempus nunc, et blandit diam consectetur id. Nam at turpis quis lorem porttitor sagittis. Proin orci dolor, mattis sed pellentesque nec, volutpat vel tellus. Donec augue arcu, feugiat vel rhoncus et, vestibulum non magna. Nulla fermentum nulla nulla, nec laoreet eros dignissim quisProin quis lorem sit amet nibh maximus tempus nec id urna. Cras eget diam semper, vehicula massa eget, tristique erat. Donec at mattis lorem. Nam viverra pulvinar turpis, sed feugiat odio elementum in. Maecenas augue leo, semper lobortis enim eget, pharetra cursus massa. Donec imperdiet sapien et nulla egestas congue. Morbi nec justo ultrices, egestas enim in, convallis eros. Maecenas accumsan fringilla massa at tincidunt";
    const listing = new ListingService();
    const auth = new Auth();

    const navigate = useNavigate();

    const [value, setValue] = useState("1");

    const { token, handleLogout } = useAuth();
    const [authorized, setAuthorized] = useState(false);
    const [loginType, setLogintype] = useState<string | null>();

    const { id } = useParams();
    let [isFavorite, setIsFavorite] = useState(false);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    const [auctionSaleProductDetail, setAuctionSaleProductDetail] = useState<AuctionSaleProductDetail>();

    let arkSeller: UserDetails = {
        userID: 5,
        firstName: "Mithun",
        lastName: "Khanna",
        email: "Khanna@mail.com",
        avgBuyerRatings: 4.2,
        avgSellerRatings: 4.5,
        username: "98only99-1",
    }

    let bowImg: string[] = ["https://picsum.photos/220", "https://picsum.photos/200", "https://picsum.photos/300"];

    let a: AuctionSaleProductDetail = {
        auctionSaleListingID: "random-uuid-here",
        productName: "Antique Ark of the Covenant",
        productDescription: loren,
        startingBid: 100,
        highestBid: 220,
        category: "Lifestyle",
        sellerEmail: "mithun@mithun.com",
        active: true,
        approverEmail: "admin@dal.ca",
        messageFromApprover: "Good product",
        approved: true,
        rejected: false,
        sold: false,
        seller: arkSeller,
        imageUrl: bowImg,

    };

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
            key: "Buy Products",
            value: "Buy Products",
            isSelected: false,
        },
    ];

    const handleFav = () => {
        setIsFavorite((prevIsFav) => !prevIsFav);
    }

    const handleChange = (event: React.SyntheticEvent, newValue: string) => {
        setValue(newValue);
    };

    useEffect(() => {
        const token = localStorage.getItem("token");

        (async () => {
            if (!token) {
                navigate("/login");
            } else {
                try {
                    const [data, error] = await auth.getUser(token);
                    if (data?.status === 200) {
                        console.log("in user");
                        setAuthorized(true);
                        setLogintype("USER");
                        setLoading(false);
                        return;
                    } else if (error) {
                        setError(true);
                        setAuthorized(false);
                    } else {
                        setError(true);
                    }
                } catch (error) {
                    setError(true);
                }

                try {
                    const [data, error] = await auth.getAdmin(token);
                    if (data?.status === 200) {
                        console.log("in admin");

                        setAuthorized(true);
                        setLogintype("ADMIN");
                    } else if (error) {
                        setError(true);
                        setAuthorized(false);
                    } else {
                        setError(true);
                    }
                } catch (error) {
                    setError(true);
                }
            }
        })();
    }, [token]);

    return (
        <>
            {loading ? ("Loading...") :
                <>
                    {authorized && loginType === "USER" ? (
                        <Navbar navOptions={navOptionsUsers} loginType={loginType} />
                    ) : (
                        <></>
                    )}
                    <div style={{ height: '100%', padding: 0, borderRadius: 10 }}>
                        <Card style={{ backgroundColor: "whitesmoke" }}>
                            <Typography
                                sx={{ textAlign: "left", marginLeft: "1%", marginTop: "1%", fontSize: 24 }}>
                                <b>{a?.productName}</b>
                            </Typography>
                            <Typography sx={{ textAlign: "left", marginLeft: "1%", fontSize: 14 }}>
                                by <b>{a?.category}</b> starting at &#36;{a.startingBid}
                            </Typography>
                            <CardContent>
                                <Carousel imageUrls={a?.imageUrl}></Carousel>
                            </CardContent>
                            <CardContent>
                            <Toolbar sx={{ fontSize: 20 }}>
                                    <span>Highest Bid: <span>&#36; </span>{a!.highestBid}</span>
                                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                                        <Button onClick={handleFav}>
                                            {isFavorite ? <FavoriteIcon sx={{ color: red[500] }} /> : <FavoriteBorderIcon />}
                                        </Button>
                                    </Typography>
                                    <Button className="chat-button" style={{ background: blue[400], color: "white" }}>Place Bid</Button>
                                </Toolbar>
                            </CardContent>
                            <CardContent>
                                <Box sx={{ width: '100%', borderRadius: 2, borderColor: 'divider' }}>
                                    <TabContext value={value}>
                                        <Box sx={{ borderBottom: 1, borderRadius: 1, borderColor: 'divider' }}>
                                            <TabList onChange={handleChange} aria-label="lab API tabs example">
                                                <Tab label="Description" value="1" />
                                                <Tab label="Seller Details" value="2" />
                                                <Tab label="How Auction works in Thrift Harbour" value="3" />
                                            </TabList>
                                        </Box>
                                        <TabPanel value="1">
                                            <Card sx={{ bgcolor: "whitesmoke", borderColor: "divided", boxShadow: 3 }}>
                                                <CardContent>
                                                    <Typography>
                                                        <h3>Product Description:</h3>
                                                        {a?.productDescription}
                                                    </Typography>
                                                </CardContent>
                                            </Card>
                                        </TabPanel>
                                        <TabPanel value="2">
                                            <Card sx={{ bgcolor: "whitesmoke", borderColor: "divided", boxShadow: 3 }}>
                                                <CardContent>
                                                    <Typography variant="h5" component="div">
                                                        {a?.seller.firstName} {a?.seller.lastName}
                                                    </Typography>
                                                    <div>
                                                        <Rating name="read-only" value={a?.seller.avgSellerRatings} precision={0.5} readOnly />
                                                    </div>
                                                    <Typography sx={{ paddingLeft: "2px" }}>
                                                        ({a?.seller.avgSellerRatings} of 5 stars)
                                                        {a?.seller.avgSellerRatings === 0
                                                            ?
                                                            <>
                                                                <span> No Reviews</span><br />
                                                            </>
                                                            :
                                                            <>
                                                            </>}
                                                    </Typography>
                                                </CardContent>
                                            </Card>
                                        </TabPanel>
                                        <TabPanel value="3">
                                            <AboutAuctionSale />
                                        </TabPanel>
                                    </TabContext>
                                </Box>
                                <br />
                            </CardContent>
                        </Card>

                    </div>
                    <Footer />
                </>
            }
        </>
    );
}

export default AuctionListingSale;