import { AdminGetAllListingResponseType } from "../../types/ListingTypes";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import Carousel from "react-bootstrap/Carousel";
import ImageSlider from "../../components/ui-components/image-slider/ImageSlider";
import { Container, ImageSLiderConainer } from "./ViewProductsStyles";
// import ExampleCarouselImage from "components/ExampleCarouselImage";

interface ViewProductsProps {
  product: AdminGetAllListingResponseType;
}

const ViewProduct: React.FC<ViewProductsProps> = ({ product }) => {
  return (
    <>
      <h1>hello</h1>
    </>
  );
};

export default ViewProduct;
