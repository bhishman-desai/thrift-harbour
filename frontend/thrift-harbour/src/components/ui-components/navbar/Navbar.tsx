import React, { useState } from "react";
import ProfileIcon from "../../../assets/icons/ProfileIcon";
import AdminDashboard from "../../../features/admin/AdminDashboard";
import BuyProducts from "../../../features/buy-product/BuyProducts";
import ProductListing from "../../../features/product-listing/add-listing/ProductListing";
import ListedProducts from "../../../features/product-listing/listed-products/ListedProducts";
import ListedBySeller from "../../../features/product-listing/seller/ListedBySeller";
import SellersList from "../../../features/Sellers/SellersList";
import { NavOptions } from "../../../types/AuthTypes";
import Profilepopup from "../Profilepopup/Profilepopup";

import {
  NavContainer,
  Option,
  Profile,
  ProfileIconBg,
  Tabs,
  TabsOptionsContainer,
} from "./NavbarStyles";
import ChatScreen from "../../../features/chat/ChatScreen";

interface NavbarProps {
  navOptions: NavOptions[];
  loginType: string;
}

const Navbar: React.FC<NavbarProps> = ({ navOptions, loginType }) => {
  const [currentSelected, setCurrentSelected] = useState(
    loginType === "ADMIN" ? "Dashboard" : "Buy Products"
  );
  const [isProfileClicked, setIsProfileClicked] = useState(false);

  const onClickOption = (key: string) => {
    setCurrentSelected(key);
  };

  return (
    <>
      <NavContainer>
        {/* <ReviewBoosterIcon height={"125"} width={"74"} /> */}
        <TabsOptionsContainer>
          <Tabs>
            {navOptions.map((option) => {
              return (
                <Option
                  style={{ color: "#ffffff" }}
                  currentSelectd={currentSelected === option.key}
                  onClick={() => onClickOption(option.key)}
                >
                  {option.value}
                </Option>
              );
            })}
          </Tabs>
          <Profile onClick={() => setIsProfileClicked(!isProfileClicked)}>
            <ProfileIconBg>
              <ProfileIcon />
            </ProfileIconBg>
          </Profile>
        </TabsOptionsContainer>
      </NavContainer>

      {isProfileClicked && <Profilepopup />}
      {currentSelected === "List Product" && <ProductListing />}
      {currentSelected === "My Listed Products" && <ListedProducts />}
      {currentSelected === "Buy Products" && loginType === "USER" && (
        <BuyProducts />
      )}
      {currentSelected === "Dashboard" && loginType === "ADMIN" && (
        <AdminDashboard />
      )}
      {currentSelected === "Sellers" && (
        <SellersList
          setCurrentSelected={setCurrentSelected}
          currentSelected={currentSelected}
        />
      )}
      {currentSelected === "List By Sellers" && <ListedBySeller />}
      {currentSelected === "Chats" && <ChatScreen />}
    </>
  );
};

export default Navbar;
