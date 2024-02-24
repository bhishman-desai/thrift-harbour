import { useState } from "react";
import Navbar from "../../components/ui-components/navbar/Navbar";
import { Button } from "../product-listing/listed-products/ListedProductsStyles";
import {
  Grid,
  ImageContainer,
  ProductCard,
  Tab,
  TabsContainer,
  Image,
  ProductNameAndDescription,
  ProductDescription,
  ProductName,
  Rest,
  ViewButtonContainer,
} from "./AdminDashboardStyles";

const AdminDashboard: React.FC = () => {
  const [activeTab, setActiveTab] = useState("All Listed Products");

  const navOptions = [
    {
      key: "Dashboard",
      value: "Dashboard",
      isSelected: true,
    },
  ];
  const tabs = [
    {
      key: "All Listed Products",
      value: "All Listed Products",
    },
    {
      key: "Approved Products",
      value: "Approved Products",
    },
    {
      key: "Rejected Products",
      value: "Rejected Products",
    },
  ];

  const handleOnClick = (key: string) => {
    setActiveTab(key);
  };

  const data = [
    {
      key: "",
    },
    {
      key: "",
    },
    {
      key: "",
    },
    {
      key: "",
    },
    {
      key: "",
    },
    {
      key: "",
    },
    {
      key: "",
    },
    {
      key: "",
    },
    {
      key: "",
    },
    {
      key: "",
    },
  ];

  return (
    <>
      <Navbar navOptions={navOptions} />

      <TabsContainer>
        {tabs.map((tab) => (
          <Tab
            selected={activeTab === tab.key}
            key={tab.key}
            onClick={() => handleOnClick(tab.key)}
            className={activeTab === tab.key ? "active" : ""}
            style={{
              borderBottom: activeTab === tab.key ? "2px solid blue" : "",
            }}
          >
            {tab.value}
          </Tab>
        ))}
      </TabsContainer>
      <Grid>
        {data.map((data) => {
          return (
            <ProductCard>
              <ImageContainer>
                <Image></Image>
              </ImageContainer>
              <Rest>
                <ProductNameAndDescription>
                  <ProductName>Name: Krutik</ProductName>
                  <ProductDescription>
                    Lorem ipsum dolor sit amet consectetur, adipisicing elit.
                    Distinctio numquam ab suscipit amet est aperiam
                  </ProductDescription>
                </ProductNameAndDescription>
                <ViewButtonContainer>
                  <Button>View</Button>
                </ViewButtonContainer>
              </Rest>
            </ProductCard>
          );
        })}
      </Grid>
    </>
  );
};
export default AdminDashboard;
