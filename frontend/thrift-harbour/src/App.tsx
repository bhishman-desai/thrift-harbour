import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Registration from "./features/registration/RegistrationScreen";
import Login from "./features/login/LoginScreen";
import AuthWrapper from "./context/AuthContext";
import Home from "./features/home/HomeScreen";
import ForgotPassword from "./features/forgotpassword/ForgotPassword";
import ResetPassword from "./features/resetpassword/ResetPassword";

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
        </Routes>
      </Router>
    </AuthWrapper>
  );
}

export default App;
