import React, { createContext, useState, useContext, useEffect } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // Check token validity on app load
  useEffect(() => {
    const accessToken = localStorage.getItem("accessToken");

    if (accessToken) {
      const payload = JSON.parse(atob(accessToken.split(".")[1])); // decode JWT payload
      const expiry = payload.exp * 1000; // convert to ms
      const now = Date.now();

      if (now < expiry) {
        setIsLoggedIn(true);

        // Optional: Auto logout when token expires
        const timeout = expiry - now;
        setTimeout(() => logoutUser(), timeout);
      } else {
        logoutUser();
      }
    }
  }, []);

  const loginUser = () => {
    setIsLoggedIn(true);

    // Auto logout after token expires
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
      const payload = JSON.parse(atob(accessToken.split(".")[1]));
      const expiry = payload.exp * 1000;
      const now = Date.now();

      const timeout = expiry - now;
      setTimeout(() => logoutUser(), timeout);
    }
  };

  const logoutUser = () => {
    setIsLoggedIn(false);
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("role");
    localStorage.removeItem("loginTime");
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, loginUser, logoutUser }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
