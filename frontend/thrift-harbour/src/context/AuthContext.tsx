import React, { useState, useEffect, ReactNode } from "react";

interface AuthContextProps {
  token: string;
  handleLogin: (token: string) => void;
  handleLogout: () => void;
}

interface AuthWrapperProps {
  children: ReactNode;
}

export const AuthContext = React.createContext<Partial<AuthContextProps>>({});

const AuthWrapper: React.FC<AuthWrapperProps> = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem("token") || "");

  useEffect(() => {
    const storedToken = localStorage.getItem("token");
    if (storedToken) {
      setToken(storedToken);
    }
  }, []);

  const handleLogin = async (token: string) => {
    localStorage.setItem("token", token);
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    setToken("");
  };

  return (
    <AuthContext.Provider value={{ token, handleLogin, handleLogout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthWrapper;
