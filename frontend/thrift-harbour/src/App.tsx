import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Registration from "./features/registration/RegistrationScreen";
import Login from "./features/login/LoginScreen";
import AuthWrapper from "./context/AuthContext";
import Home from "./features/home/HomeScreen";
import ForgotPassword from "./features/forgotpassword/ForgotPassword";
import ResetPassword from "./features/resetpassword/ResetPassword";
import ProductListing from "./features/product-listing/add-listing/ProductListing";

function App() {
  return (
    <AuthWrapper>
      <Router>
        <Routes>
          <Route path="/" element={<Registration />} />
          <Route path="/login" element={<Login />} />
          <Route path="/home" element={<Home />} />
          <Route path="/forgot-password" element={<ForgotPassword />} />
          <Route path="/reset-password/*" element={<ResetPassword />} />
          <Route path="/listing" element={<ProductListing />} />
        </Routes>
      </Router>
    </AuthWrapper>
  );
}

export default App;
