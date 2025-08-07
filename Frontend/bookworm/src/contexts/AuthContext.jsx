import React, { createContext, useState, useContext, useEffect, useRef } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  const logoutTimerRef = useRef(null);

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
    setUser(null);
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
        setUser(payload);
        logoutTimerRef.current = setTimeout(logoutUser, timeout);
      } else {
        logoutUser();
      }
    } else {
      logoutUser();
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
          setUser(payload);
          logoutTimerRef.current = setTimeout(logoutUser, timeout);
        } else {
          logoutUser();
        }
      } else {
        logoutUser();
      }
    }

    return () => {
      if (logoutTimerRef.current) {
        clearTimeout(logoutTimerRef.current);
      }
    };
  }, []);

  return (
    <AuthContext.Provider value={{ isLoggedIn, user, loginUser, logoutUser }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
