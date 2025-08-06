// src/components/CustomNavLink.jsx
import React from "react";
import { NavLink } from "react-router-dom";

const CustomNavLink = ({ to, children }) => {
  return (
    <NavLink
      to={to}
      className={({ isActive }) =>
        `px-3 py-1 rounded-full transition-colors duration-200 no-underline ${
          isActive
            ? "bg-[#f3ede1] text-[#b7a680] font-semibold"
            : "text-black hover:bg-[#b7a680] hover:text-white"
        }`
      }
    >
      {children}
    </NavLink>
  );
};

export default CustomNavLink;
