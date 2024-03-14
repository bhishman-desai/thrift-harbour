import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import ImageIcon from "../../assets/icons/ImageIcon";
import Modal from "../../components/ui-components/Modal/Modal";
import { Ratings } from "../buy-product/BuyProductsStyles";
import { ProductImage } from "../product-listing/add-listing/ProductListingStyles";
import {
  Header,
  InfoContainer,
  Profile,
  RatingContainer,
  Title,
  UserInfo,
  Value,
} from "./UserProfileStyles";
import Stack from "@mui/material/Stack";
import Rating from "@mui/material/Rating";
import ProfileIcon from "../../assets/icons/ProfileIcon";
import { UsersService } from "../../services/Users";
import { UserDetails } from "../../types/ProductSaleDetails";

interface UserProfileProps {
  id: number;
}
const BuyProducts: React.FC<UserProfileProps> = ({ id }) => {
  const navigate = useNavigate();
  const users = new UsersService();
  const token = localStorage.getItem("token");

  const [thumbnailUrl, setThumbnailUrl] = useState<string | null>(null);
  const [user, setUser] = useState({} as UserDetails);
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    (async function () {
      try {
        const response = await users.getUserData(id, token);

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
      <Profile>
        <label htmlFor="file-input">
          {thumbnailUrl ? (
            <ProductImage>
              <img
                style={{ borderRadius: "50%" }}
                height={"100%"}
                width={"100%"}
                src={thumbnailUrl}
                alt="Thumbnail"
              />
            </ProductImage>
          ) : (
            <ProductImage>
              <ProfileIcon height={32} width={32} />
            </ProductImage>
          )}
        </label>
      </Profile>
      <InfoContainer>
        <UserInfo>
          <Title>First Name:</Title>
          <Value style={{ marginLeft: "4px" }}>{user?.firstName}</Value>
        </UserInfo>
        <UserInfo>
          <Title>Last Name:</Title>
          <Value style={{ marginLeft: "4px" }}>{user?.lastName}</Value>
        </UserInfo>
        <UserInfo>
          <Title>Email:</Title>
          <Value style={{ marginLeft: "4px" }}>{user.email}</Value>
        </UserInfo>
        <RatingContainer>
          <Title>Ratings as buyer:</Title>
          <Value style={{ marginLeft: "4px" }}>
            <Stack spacing={1}>
              <Rating
                name="half-rating-read"
                defaultValue={4.5}
                precision={0.5}
                readOnly
              />
            </Stack>
          </Value>
        </RatingContainer>
        <RatingContainer>
          <Title>Ratings as seller:</Title>
          <Value style={{ marginLeft: "4px" }}>
            <Stack spacing={1}>
              <Rating
                name="half-rating-read"
                defaultValue={4.5}
                precision={0.5}
                readOnly
              />
            </Stack>
          </Value>
        </RatingContainer>
      </InfoContainer>
    </>
  );
};

export default BuyProducts;
