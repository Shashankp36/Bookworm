import React, { useEffect, useRef } from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import Logout from "./Logout";
import LowerHeader from "./lowerHeader";
import { Menu, LogOut, Phone, ShoppingCart } from "lucide-react"; // Lucide icons
import CustomNavLink from "../components/CustomNavlink";

// Avatar with first letter of name
const UserAvatar = ({ user }) => (
  <div
    className="rounded-full w-9 h-9 flex items-center justify-center font-bold uppercase cursor-pointer 
               bg-[#b7a680] text-white hover:bg-[#8a6c3a] transition-colors duration-300"
  >
    {user?.name ? user.name[0] : "U"}
  </div>
);

const Header = () => {
  const { isLoggedIn, user } = useAuth();
  const navigate = useNavigate();
  const [showDropdown, setShowDropdown] = React.useState(false);
  const dropdownRef = useRef(null);

  const toggleDropdown = () => setShowDropdown((prev) => !prev);

  // Close dropdown when clicking outside
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setShowDropdown(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <header className="bg-[#f5f5f4] shadow-md w-full h-20 relative z-50">
      <div className="flex justify-between items-center px-8 py-3 h-full">
        {/* Brand */}
        <div
          className="text-2xl font-serif font-bold text-[#2d2c2a] cursor-pointer"
          onClick={() => navigate("/")}
        >
          Bookworm
        </div>

        {/* Center Navigation Links */}
        <nav className="absolute left-1/2 transform -translate-x-1/2 flex text-lg font-medium h-full items-center space-x-6 font-serif tracking-wide">
          <CustomNavLink to="/">Home</CustomNavLink>
          <CustomNavLink to="/books">Ebooks</CustomNavLink>
          <CustomNavLink to="/audiobooks">Audiobooks</CustomNavLink>
          <CustomNavLink to="/sale">Sale</CustomNavLink>
          <CustomNavLink to="/shelf">MyShelf</CustomNavLink>
        </nav>

        {/* Right Side - Buttons and Avatar */}
        <div className="flex items-center gap-4 relative">
          <>
            {/* Cart Icon with hover */}
            <Link
              to="/cart"
              className="relative ml-2 mr-2 p-2 rounded-md hover:bg-[#b7a680] transition-colors duration-200"
            >
              <ShoppingCart className="w-6 h-6 text-[#2d2c2a]" />
            </Link>

            {/* Avatar + Dropdown */}
            <div className="relative ml-2 mr-2" ref={dropdownRef}>
              <div onClick={toggleDropdown}>
                <UserAvatar user={user} />
              </div>

              {showDropdown && (
                <div className="absolute right-0 mt-2 w-44 bg-white shadow-lg border rounded-md overflow-hidden z-50">
                  {/* About Us with hover */}
                  <Link
                    to="/contact"
                    className="flex items-center gap-2 px-4 py-2 hover:bg-gray-100 text-[#2d2c2a] transition-colors duration-200"
                  >
                    <Phone className="w-4 h-4" /> About Us
                  </Link>

                  {/* Logout button with hover (update inside component if needed) */}
                  <Logout />
                </div>
              )}
            </div>
          </>
        </div>
      </div>
      <LowerHeader />
    </header>
    
  );
};

export default Header;
