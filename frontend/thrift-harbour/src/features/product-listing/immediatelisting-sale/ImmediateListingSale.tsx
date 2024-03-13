import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom";
import useAuth from "../../../hooks/useAuth";
import { Auth } from "../../../services/Auth";
import { useParams } from "react-router-dom";
import { ImmediateSaleProductDetail } from "../../../types/ProductSaleDetails";
import { ListingService } from "../../../services/Listing";
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';

import "./ImmediateListingSale.css"

import { Card, CardContent, Toolbar, Typography, Button, Box } from "@mui/material";
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorderRounded';
import FavoriteIcon from '@mui/icons-material/Favorite';
import IconButton from '@mui/material/IconButton';
import { blue, red } from "@mui/material/colors";
import Rating from '@mui/material/Rating';

import Carousel from "../../../components/ui-components/carousel/Carousel";
import Navbar from "../../../components/ui-components/navbar/Navbar";
import Footer from "../../../components/ui-components/footer/Footer";


const ImmediateListingSale = () => {

    const listing = new ListingService();
    const auth = new Auth();

    const navigate = useNavigate();

    const [value, setValue] = useState("1");

    const { token, handleLogout } = useAuth();
    const [authorized, setAuthorized] = useState(false);
    const [loginType, setLogintype] = useState<string | null>();

    const { id } = useParams();
    const [isFavorite, setIsFavorite] = useState(false);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    const [immediateSaleProductDetail, setImmediateSaleProductDetail] = useState<ImmediateSaleProductDetail>();

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
        (async function () {
            try {
                const response = await listing.immediateSaleProductDetail(id!, token);
                console.log("from useeffect", response);

                const imgresponse = await listing.getImmediateListedProductsImages(id!, token)
                console.log("from img useeffect", imgresponse[0]?.data.imageURLs);
                const imageUrls = imgresponse[0]?.data.imageURLs;

                const productWithImages = { ...response, imageUrl: imageUrls };

                setImmediateSaleProductDetail(productWithImages);
                setLoading(false);
            }
            catch (error) {
                console.log(error);
                throw error;
            }
        })();
    }, [])


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
                                <b>{immediateSaleProductDetail!.productName}</b>
                            </Typography>
                            <Typography sx={{ textAlign: "left", marginLeft: "1%", fontSize: 14}}>
                                by {immediateSaleProductDetail?.category}
                            </Typography>
                            <CardContent>
                                <Carousel imageUrls={immediateSaleProductDetail?.imageUrl}></Carousel>
                            </CardContent>
                            <CardContent>
                                <Toolbar sx ={{fontSize: 20}}>
                                    <span>Seller Quoted Price: &#36;{immediateSaleProductDetail!.price}</span>
                                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                                        <Button onClick={handleFav}>
                                            {isFavorite ? <FavoriteIcon sx={{ color: red[500] }} /> : <FavoriteBorderIcon />}
                                        </Button>
                                    </Typography>
                                    <Button className="chat-button" style={{ background: blue[400], color: "white" }}>Chat</Button>
                                </Toolbar>
                            </CardContent>
                            <CardContent >
                                <Box sx={{ width: '100%', borderRadius: 2, bgcolor: "whitesmoke"}}>
                                    <TabContext value={value} >
                                        <Box sx={{ borderBottom: 1, borderRadius: 1, borderColor: 'divider'}}>
                                            <TabList onChange={handleChange} aria-label="lab API tabs example">
                                                <Tab label="Description" value="1" />
                                                <Tab label="Seller Details" value="2" />
                                                <Tab label="How Buying works in Thrift Harbour" value="3" />
                                            </TabList>
                                        </Box>
                                        <TabPanel value="1">
                                            <Typography>
                                                {immediateSaleProductDetail?.productDescription}
                                            </Typography>
                                        </TabPanel>
                                        <TabPanel value="2">
                                            <Typography>
                                                Sold by:
                                                {immediateSaleProductDetail?.seller.firstName} {immediateSaleProductDetail?.seller.lastName} <br />
                                                <div>
                                                    <Typography component="legend">Rating as Seller</Typography>
                                                    <Rating name="read-only" value={immediateSaleProductDetail?.seller.avgSellerRatings} precision={0.5} readOnly />
                                                </div>
                                            </Typography>
                                        </TabPanel>
                                        <TabPanel value="3">We make it work in Thrifthrabour</TabPanel>
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

export default ImmediateListingSale