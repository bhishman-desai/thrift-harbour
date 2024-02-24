import { useState } from "react";
import Navbar from "../../components/ui-components/navbar/Navbar";
import { Tab, TabsContainer } from "./AdminDashboardStyles";

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
    </>
  );
};
export default AdminDashboard;
