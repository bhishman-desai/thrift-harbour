import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import ProfileIcon from "../../../assets/icons/ProfileIcon";
import AdminDashboard from "../../../features/admin/AdminDashboard";
import ProductListing from "../../../features/product-listing/add-listing/ProductListing";
import ListedProducts from "../../../features/product-listing/listed-products/ListedProducts";
import SellersList from "../../../features/Sellers/SellersList";
import { NavOptions } from "../../../types/AuthTypes";
import { HamburgerMenuProps } from "../../../types/ListingTypes";
import Profilepopup from "../Profilepopup/Profilepopup";

import {
  NavContainer,
  TabsOptionsContainer,
  Option,
  Tabs,
  Profile,
  ProfileIconBg,
} from "./NavbarStyles";

interface NavbarProps {
  navOptions: NavOptions[];
}

const Navbar: React.FC<NavbarProps> = ({ navOptions }) => {
  const [currentSelected, setCurrentSelected] = useState("Dashboard");
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
      {currentSelected === "Dashboard" && <AdminDashboard />}
      {currentSelected === "Sellers" && <SellersList />}
    </>
  );
};

export default Navbar;
