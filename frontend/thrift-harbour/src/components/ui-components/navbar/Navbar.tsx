import React, { useEffect, useState } from "react";
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
import ImmediateListingSale from "../../../features/product-listing/immediatelisting-sale/ImmediateListingSale";
import AuctionListing from "../../../features/auction/AuctionListing";

interface NavbarProps {
  navOptions: NavOptions[];
  loginType: string;
}

const Navbar: React.FC<NavbarProps> = ({ navOptions, loginType }) => {
  const currentState = window.history.state;
  const [currentSelected, setCurrentSelected] = useState(
    loginType === "ADMIN"
      ? "Dashboard"
      : currentState.currentSelectd
        ? currentState.currentSelectd
        : "Buy Products"
  );
  const [isProfileClicked, setIsProfileClicked] = useState(false);
  const onClickOption = (key: string) => {
    setCurrentSelected(key);
  };

  const currentUrl = window.location.href;
  const isImmediateSaleProductDetail = currentUrl.includes(
    "immediatesale-product-detail"
  );
  const auctionProductDetails = currentUrl.includes(
    "auctionsale-product-detail"
  );
  const auction = currentUrl.includes("auction");
  const home = currentUrl.includes("home");
  const handleUrlChange = (selectedTab: string) => {
    if (selectedTab !== "Buy Products") {
      window.history.pushState({}, "", "/home");
    }
  };

  // Call handleUrlChange when the tab is changed
  // useEffect(() => {
  //   handleUrlChange(currentSelected);
  // }, [currentSelected]);

  return (
    <>
      <NavContainer>
        <TabsOptionsContainer>
          <Tabs>
            {navOptions.map((option) => {
              return (
                <Option
                  style={{ color: "#ffffff" }}
                  currentSelectd={currentSelected === option.key}
                  onClick={() => {
                    onClickOption(option.key);
                    handleUrlChange(option.value);
                  }}
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

      {currentSelected === "Dashboard" && loginType === "ADMIN" && (
        <AdminDashboard />
      )}

      {isProfileClicked && <Profilepopup />}
      {currentSelected === "List Product" && <ProductListing />}
      {currentSelected === "My Listed Products" && <ListedProducts />}
      {currentSelected === "Buy Products" &&
        !isImmediateSaleProductDetail &&
        !auctionProductDetails &&
        !auction &&
        loginType === "USER" && <BuyProducts />}

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
