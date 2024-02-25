import {
  AdminGetAllListingResponseType,
  AdminGetImmediateSaleProductById,
} from "../../types/ListingTypes";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import Carousel from "react-bootstrap/Carousel";
import ImageSlider from "../../components/ui-components/image-slider/ImageSlider";
import {
  ChangeStatus,
  Container,
  DropDown,
  ImageSLiderConainer,
  ProductNameAndDescription,
  ProductShowcase,
  UserInfo,
} from "./ViewProductsStyles";
import { ProductInfo, Title, Value } from "../admin/AdminDashboardStyles";
import { Helper } from "../../utils/Helper";
import { FormEvent, useEffect, useState } from "react";
import { AdminServices } from "../../services/Admin";
import Select from "@mui/material/Select";
import {
  FormControl,
  FormHelperText,
  InputLabel,
  MenuItem,
  TextField,
} from "@mui/material";
import { Button, RegisterButton } from "../registration/RegistrationStyles";
import { ClipLoader } from "react-spinners";
// import ExampleCarouselImage from "components/ExampleCarouselImage";

interface ViewProductsProps {
  product: AdminGetAllListingResponseType;
}

const ViewProduct: React.FC<ViewProductsProps> = ({ product }) => {
  const helper = new Helper();
  const admin = new AdminServices();
  const token = localStorage.getItem("token");
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(true);
  const [data, setData] = useState<any>();
  const [changeStatus, setChangeStatus] = useState("");
  const [denyComment, setDenyComment] = useState("");
  const [loader, setLoader] = useState(false);

  useEffect(() => {
    (async function () {
      try {
        const response = await admin.getImmediateListedProductById(
          product.immediateSaleListingID,
          token
        );

        if (response[0]?.status === 200) {
          setLoading(false);
          const data = response[0].data;
          setData(data);
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

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setChangeStatus("");
    setDenyComment("");
    try {
      setLoader(true);
      const response = await admin.submitReview(
        {
          listingId: product.immediateSaleListingID,
          status: changeStatus,
          sellCategory: "DIRECT",
          message: denyComment ? denyComment : "",
        },
        token
      );

      if (response[0]?.status === 200) {
        setLoader(false);
        const data = response[0].data;
        setData(data);
        console.log("data after if", data);
      } else {
        setError(true);
        setLoader(false);
      }
    } catch (error) {
      setLoader(false);
      setError(true);
    }
  };

  return (
    <>
      {!data ? (
        "Loading..."
      ) : (
        <>
          <ProductShowcase>
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
                <Title>Description: </Title>
                <Value style={{ marginLeft: "4px" }}>
                  {data.productDescription}
                </Value>
              </ProductInfo>
              <ProductInfo style={{ marginTop: "4px" }}>
                <Title>Status: </Title>
                {helper.getListingStatus(product.approved, product.rejected) ===
                "Approved" ? (
                  <Value style={{ marginLeft: "4px", color: "green" }}>
                    {helper.getListingStatus(
                      product.approved,
                      product.rejected
                    )}
                  </Value>
                ) : (
                  <Value style={{ marginLeft: "4px", color: "red" }}>
                    {helper.getListingStatus(
                      product.approved,
                      product.rejected
                    )}
                  </Value>
                )}
              </ProductInfo>
            </ProductNameAndDescription>
            <UserInfo>
              <ProductInfo>
                <Title>Seller First Name: </Title>
                <Value style={{ marginLeft: "4px" }}>
                  {product.productName}
                </Value>
              </ProductInfo>
              <ProductInfo style={{ marginTop: "4px" }}>
                <Title>Seller Last Name: </Title>
                <Value style={{ marginLeft: "4px" }}>
                  {"$" + product.price}
                </Value>
              </ProductInfo>
              <ProductInfo style={{ marginTop: "4px" }}>
                <Title>Seller Email: </Title>
                <Value style={{ marginLeft: "4px" }}>
                  {"$" + product.price}
                </Value>
              </ProductInfo>
              <ProductInfo style={{ marginTop: "4px" }}>
                <Title>Sold: </Title>
                <Value style={{ marginLeft: "4px" }}>
                  {"$" + product.price}
                </Value>
              </ProductInfo>
            </UserInfo>
          </ProductShowcase>
          <ChangeStatus onSubmit={handleSubmit}>
            <DropDown>
              <Title>Change Status</Title>
              <FormControl sx={{ m: 1, width: "40%" }} error={false}>
                <Select
                  variant="outlined"
                  required={true}
                  labelId="demo-simple-select-disabled-label"
                  id="demo-simple-select-disabled"
                  value={changeStatus}
                  label="Product category"
                  onChange={(e) => {
                    setChangeStatus(e.target.value);
                  }}
                >
                  <MenuItem value={"APPROVED"}>APPROVE</MenuItem>
                  <MenuItem value={"REJECTED"}>DENY</MenuItem>
                </Select>
              </FormControl>
              {changeStatus === "REJECTED" && (
                <FormControl sx={{ m: 1, width: "48%" }}>
                  <TextField
                    required={true}
                    id="standard-error-helper-text"
                    label="Comment"
                    value={denyComment}
                    variant="outlined"
                    onChange={(e) => setDenyComment(e.target.value)}
                  />
                </FormControl>
              )}
            </DropDown>
            <Button>
              <RegisterButton type="submit">
                {loader ? (
                  <ClipLoader color="#ffffff" loading={loader} size={20} />
                ) : (
                  "Submit Review"
                )}
              </RegisterButton>
            </Button>
          </ChangeStatus>
        </>
      )}
    </>
  );
};

export default ViewProduct;
