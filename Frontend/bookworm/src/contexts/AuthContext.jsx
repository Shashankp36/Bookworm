import React, { createContext, useState, useContext, useEffect, useRef } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const logoutTimerRef = useRef(null); // to clear timeout on logout/login

  // Helper to decode JWT
  const decodeJWT = (token) => {
    try {
      const payload = JSON.parse(atob(token.split(".")[1]));
      return payload;
    } catch (error) {
      console.error("Invalid token:", error);
      return null;
    }
  };

  const logoutUser = () => {
    setIsLoggedIn(false);
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("role");
    localStorage.removeItem("loginTime");

    if (logoutTimerRef.current) {
      clearTimeout(logoutTimerRef.current);
      logoutTimerRef.current = null;
    }
  };

  const loginUser = () => {
    setIsLoggedIn(true);

    const accessToken = localStorage.getItem("accessToken");
    const payload = decodeJWT(accessToken);

    if (payload && payload.exp) {
      const expiry = payload.exp * 1000;
      const now = Date.now();
      const timeout = expiry - now;

      if (timeout > 0) {
        logoutTimerRef.current = setTimeout(logoutUser, timeout);
      } else {
        logoutUser();
      }
    } else {
      logoutUser(); // invalid token
    }
  };

  useEffect(() => {
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
      const payload = decodeJWT(accessToken);
      if (payload && payload.exp) {
        const expiry = payload.exp * 1000;
        const now = Date.now();
        const timeout = expiry - now;

        if (timeout > 0) {
          setIsLoggedIn(true);
          logoutTimerRef.current = setTimeout(logoutUser, timeout);
        } else {
          logoutUser(); // token expired
        }
      } else {
        logoutUser(); // invalid token
      }
    }
    // Cleanup on unmount
    return () => {
      if (logoutTimerRef.current) {
        clearTimeout(logoutTimerRef.current);
      }
    };
  }, []);

  return (
    <AuthContext.Provider value={{ isLoggedIn, loginUser, logoutUser }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);