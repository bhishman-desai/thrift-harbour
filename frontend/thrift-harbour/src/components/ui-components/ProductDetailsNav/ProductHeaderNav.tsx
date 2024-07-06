import { useState } from "react";
import { useNavigate } from "react-router-dom";
import BackButton from "../../../assets/icons/BackButton";
import ProfileIcon from "../../../assets/icons/ProfileIcon";
import { Profile, ProfileIconBg } from "../navbar/NavbarStyles";
import Profilepopup from "../Profilepopup/Profilepopup";
import { Header, Items, NavContainer } from "./ProductHeaderNavStyles";

const ProductHeaderNav: React.FC = () => {
  const [isProfileClicked, setIsProfileClicked] = useState(false);
  const navigate = useNavigate();
  const handleOnBackButton = () => {
    navigate("/home");
  };
  return (
    <>
      <NavContainer>
        <Items onClick={() => handleOnBackButton()}>
          <BackButton height={16} width={16} />
          <Header>Product Details</Header>
        </Items>
        <Profile onClick={() => setIsProfileClicked(!isProfileClicked)}>
          <ProfileIconBg>
            <ProfileIcon />
          </ProfileIconBg>
        </Profile>
      </NavContainer>

      {isProfileClicked && <Profilepopup />}
    </>
  );
};

export default ProductHeaderNav;
