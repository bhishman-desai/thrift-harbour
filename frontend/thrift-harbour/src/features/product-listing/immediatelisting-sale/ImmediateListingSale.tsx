import React, { useEffect, useState } from "react"
import { useParams } from "react-router-dom";
import { ImmediateSaleProductDetail } from "../../../types/ProductSaleDetails";
import { ListingService } from "../../../services/Listing";

import "./ImmediateListingSale.css"

import { Card, CardContent, Toolbar, Typography, Button } from "@mui/material";
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorderRounded';
import FavoriteIcon from '@mui/icons-material/Favorite';
import IconButton from '@mui/material/IconButton';
import { blue, red } from "@mui/material/colors";

import Carousel from "../../../components/ui-components/carousel/Carousel";
import { Padding } from "@mui/icons-material";

const ImmediateListingSale = () => {

    const listing = new ListingService();

    const { id } = useParams();
    const token = localStorage.getItem("token");
    const [isFavorite, setIsFavorite] = useState(false);
    const [loading, setLoading] = useState(true);

    const handleFav = () => {
        setIsFavorite(!isFavorite);
    }

    const [immediateSaleProductDetail, setImmediateSaleProductDetail] = useState<ImmediateSaleProductDetail>();

    useEffect(() => {
        (async function () {
            try {
                const response = await listing.immediateSaleProductDetail(id!, token);
                console.log("from useeffect", response);

                const imgresponse = await listing.getImmediateListedProductsImages(id!, token)
                console.log("from img useeffect", imgresponse[0]?.data.imageURLs);
                const imageUrls = imgresponse[0]?.data.imageURLs;

                const productWithImages = {...response, imageUrl: imageUrls};

                setImmediateSaleProductDetail(productWithImages);
                setLoading(false);
            }
            catch (error) {
                console.log(error);
                throw error;
            }
        })();
    }, [])

    return (
        <>
            {loading ? ("Loading...") :
                <div style={{margin: "0.55%", padding: 5, borderRadius: 10}}>
                    <Card style={{ backgroundColor: "#FFFAFA" }}>
                        <Typography
                            sx={{ textAlign: "center", marginTop: "1%", fontSize: 24 }}>
                            {immediateSaleProductDetail!.productName}
                        </Typography>
                        <CardContent>
                            <Carousel imageUrls={immediateSaleProductDetail?.imageUrl}></Carousel>
                        </CardContent>
                        <CardContent>
                            <Toolbar>
                                <IconButton
                                    size="medium"
                                    edge="start"
                                    color="inherit"
                                    aria-label="menu"
                                    sx={{ mr: 2 }}
                                >
                                    <span>Price: {immediateSaleProductDetail!.price} &#36;</span>
                                </IconButton>
                                <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                                    <Button onClick={handleFav}>
                                        {isFavorite ? <FavoriteIcon sx={{ color: red[500] }} /> : <FavoriteBorderIcon />}
                                    </Button>
                                </Typography>
                                <Button className="chat-button" style={{ background: blue[400], color: "white" }}>Chat</Button>
                            </Toolbar>
                        </CardContent>
                        <CardContent>
                            <Typography>
                                Product Description: <br />
                            </Typography>
                            <Typography>
                                {immediateSaleProductDetail?.productDescription}
                            </Typography>
                        </CardContent>
                        <CardContent>
                            <Toolbar>
                                <Typography>
                                    Sold by: {immediateSaleProductDetail?.seller.firstName} {immediateSaleProductDetail?.seller.lastName}
                                    {/* Sold by: Mithun */}
                                </Typography>
                            </Toolbar>
                        </CardContent>
                    </Card>
                </div>
            }
        </>

    );
}

export default ImmediateListingSale