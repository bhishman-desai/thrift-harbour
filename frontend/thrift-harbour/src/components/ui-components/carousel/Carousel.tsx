import React from "react"

import Slider from "react-slick"
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";


export interface productImageUrls {
    imageUrls: string[] | undefined
}


const Carousel: React.FC<productImageUrls> = ({imageUrls}) => {

    const images = imageUrls;

    const settings = {
        dots: true,
        fade: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        waitForAnimate: false
    };

    return (
        <div>
            <Slider {...settings}>
                {imageUrls!.map((image) => (
                    <div >
                        <img src={image} height={"30%"} width={"30%"} style={{ margin: "auto", display: "block" }} />
                    </div>
                ))}
            </Slider>
        </div>
    );
}

export default Carousel