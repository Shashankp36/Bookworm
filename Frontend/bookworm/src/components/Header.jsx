// src/components/Header.jsx
import React, { useEffect, useRef, useState } from "react";
import { Link, useNavigate, Outlet } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import Logout from "./Logout";
import LowerHeader from "./lowerHeader";
import { Phone, ShoppingCart } from "lucide-react";
import CustomNavLink from "../components/CustomNavlink";

export const ProductContext = React.createContext(); // ✅ Context to pass down data

const UserAvatar = ({ user }) => {
  const firstLetter = user?.name?.trim()?.charAt(0)?.toUpperCase() || "U";
  return (
    <div className="rounded-full w-9 h-9 flex items-center justify-center font-bold uppercase cursor-pointer 
                 bg-[#b7a680] text-white hover:bg-[#8a6c3a] transition-colors duration-300">
      {firstLetter}
    </div>
  );
};

const Header = () => {
  const { isLoggedIn, user } = useAuth();
  const navigate = useNavigate();
  const [showDropdown, setShowDropdown] = useState(false);
  const dropdownRef = useRef(null);

  const [allBooks, setAllBooks] = useState([]);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setShowDropdown(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  // ✅ Fetch books once
  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/products/");
        if (!res.ok) throw new Error("Failed to fetch books");
        const data = await res.json();
        setAllBooks(data);
      } catch (error) {
        console.error("Error fetching books:", error);
      }
    };
    fetchBooks();
  }, []);

  return (
    <ProductContext.Provider value={allBooks}>
      <header className="bg-[#f5f5f4] shadow-md w-full h-20 relative z-50">
        <div className="flex justify-between items-center px-8 py-3 h-full">
          <div
            className="text-2xl font-serif font-bold text-[#2d2c2a] cursor-pointer"
            onClick={() => navigate("/")}
          >
            Bookworm
          </div>

          <nav className="absolute left-1/2 transform -translate-x-1/2 flex text-lg font-medium h-full items-center space-x-6 font-serif tracking-wide">
            <CustomNavLink to="/">Home</CustomNavLink>
            <CustomNavLink to="/Ebooks">Ebooks</CustomNavLink>
            <CustomNavLink to="/audiobooks">Audiobooks</CustomNavLink>
            <CustomNavLink to="/sale">Sale</CustomNavLink>
            <CustomNavLink to="/shelf">MyShelf</CustomNavLink>
          </nav>

          <div className="flex items-center gap-4 relative">
            <Link
              to="/cart"
              className="relative ml-2 mr-2 p-2 rounded-md hover:bg-[#b7a680] transition-colors duration-200"
            >
              <ShoppingCart className="w-6 h-6 text-[#2d2c2a]" />
            </Link>

            {isLoggedIn && (
              <div className="relative ml-2 mr-2" ref={dropdownRef}>
                <div onClick={() => setShowDropdown((prev) => !prev)}>
                  <UserAvatar user={user} />
                </div>

                {showDropdown && (
                  <div className="absolute right-0 mt-2 w-44 bg-white shadow-lg border rounded-md overflow-hidden z-50">
                    <Link
                      to="/about"
                      className="flex items-center gap-2 px-4 py-2 hover:bg-gray-100 text-[#2d2c2a] transition-colors duration-200"
                    >
                      <Phone className="w-4 h-4" /> About Us
                    </Link>
                    {/* My Orders */}
                    <Link
                      to="/orders"
                      className="flex items-center gap-2 px-4 py-2 hover:bg-gray-100 text-[#2d2c2a] transition-colors duration-200"
                    >
                      📦 My Orders
                    </Link>
                    <Logout />
                  </div>
                )}
              </div>
            )}
          </div>
        </div>
        <LowerHeader />
      </header>
      <Outlet /> {/* ✅ All child routes can now use ProductContext */}
    </ProductContext.Provider>
  );
};

export default Header;
