import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

const Navbar = () => {
  const { isLoggedIn, logoutUser } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/auth/logout", {
        method: "POST",
        credentials: "include"
      });

      if (res.ok) {
        logoutUser();
        navigate("/auth");
      } else {
        console.error("Logout failed");
      }
    } catch (error) {
      console.error("Logout error:", error);
    }
  };

  return (
    <nav className="bg-gray-800 px-6 py-4 flex justify-between items-center shadow-md">
      <h1 className="text-xl font-bold text-white">📚 BookWorm</h1>
      <div className="space-x-6 text-gray-300 flex items-center">
        <Link to="/" className="hover:text-white">Home</Link>
        <Link to="/books" className="hover:text-white">Books</Link>
        <Link to="/audiobooks" className="hover:text-white">Audiobooks</Link>
        <Link to="/contact" className="hover:text-white">Contact</Link>

        {isLoggedIn ? (
          <>
            <Link to="/shelf" className="hover:text-white">Shelf</Link>
            <Link to="/cart" className="hover:text-white">
              <i className="fas fa-shopping-cart"></i> Cart
            </Link>
            <button
              onClick={handleLogout}
              className="bg-red-600 px-3 py-1 rounded hover:bg-red-700 transition text-white"
            >
              Logout
            </button>
          </>
        ) : (
          <Link to="/auth" className="bg-indigo-600 px-3 py-1 rounded text-white hover:bg-indigo-700 transition">
            Login/Signup
          </Link>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
