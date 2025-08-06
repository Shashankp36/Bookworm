// components/Logout.jsx

import React from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

const Logout = () => {
  const { logoutUser } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/auth/logout", {
        method: "POST",
        credentials: "include",
      });

      if (res.ok) {
        // Clear tokens or session data from localStorage
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");

        logoutUser(); // Clear auth context state
        navigate("/auth");
      } else {
        console.error("Logout failed");
      }
    } catch (error) {
      console.error("Logout error:", error);
    }
  };

  return (
    <button
      onClick={handleLogout}
      className="w-full flex items-center gap-2 px-4 py-2 text-red-600 hover:bg-gray-100"
    >
      <span>🚪</span> Logout
    </button>
  );
};

export default Logout;
