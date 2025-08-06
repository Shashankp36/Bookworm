import React from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

// Helper to extract first letter of user name/email
const UserAvatar = ({ user }) => (
  <div
    className="rounded-full w-9 h-9 flex items-center justify-center font-bold uppercase"
    style={{
      backgroundColor: "#b7a680", // English gold-taupe
      color: "#fff"
    }}
  >
    {user?.name ? user.name[0] : "U"}
  </div>
);

const Navbar = () => {
  const { isLoggedIn, logoutUser, user } = useAuth();
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
    <header className="bg-[#f5f5f4] shadow-md w-full">
      {/* Upper Navbar */}
      <nav className="flex justify-between items-center px-8 py-3">
        <h1 className="text-2xl font-extrabold" style={{ color: "#2d2c2a" }}>
          📚 BookWorm
        </h1>
        <div className="flex items-center gap-4">
          {!isLoggedIn ? (
            <>
              <Link
                to="/auth?type=login"
                className="rounded-full px-5 py-2 font-semibold shadow"
                style={{
                  background: "#b7a680",
                  color: "#fff",
                  transition: "background 0.2s",
                }}
                onMouseOver={e => e.currentTarget.style.background = "#a1997b"}
                onMouseOut={e => e.currentTarget.style.background = "#b7a680"}
              >
                Login
              </Link>
              <Link
                to="/auth?type=signup"
                className="rounded-full border-2 px-5 py-2 font-semibold"
                style={{
                  borderColor: "#b7a680",
                  color: "#b7a680",
                  background: "#fff",
                  transition: "background 0.2s, color 0.2s",
                }}
                onMouseOver={e => {
                  e.currentTarget.style.background = "#f1eee6";
                  e.currentTarget.style.color = "#2d2c2a";
                }}
                onMouseOut={e => {
                  e.currentTarget.style.background = "#fff";
                  e.currentTarget.style.color = "#b7a680";
                }}
              >
                Signup
              </Link>
              <Link
                to="/contact"
                className="ml-2 underline font-semibold"
                style={{ color: "#2d2c2a" }}
                onMouseOver={e => e.currentTarget.style.color = "#b7a680"}
                onMouseOut={e => e.currentTarget.style.color = "#2d2c2a"}
              >
                Contact Us
              </Link>
            </>
          ) : (
            <>
              <button
                className="focus:outline-none"
                title={user?.name || "User"}
                style={{ minWidth: "36px" }}
              >
                <UserAvatar user={user} />
              </button>
              <Link
                to="/cart"
                className="rounded-full p-2 transition relative"
                style={{ color: "#2d2c2a" }}
                onMouseOver={e => e.currentTarget.style.background = "#ede9dd"}
                onMouseOut={e => e.currentTarget.style.background = "transparent"}
              >
                <i className="fas fa-shopping-cart text-xl"></i>
              </Link>
              <button
                onClick={handleLogout}
                className="rounded-full px-4 py-1.5 font-semibold shadow ml-1"
                style={{
                  background: "#ab3730", // Soft red (English brick red)
                  color: "#fff",
                  transition: "background 0.2s",
                }}
                onMouseOver={e => e.currentTarget.style.background = "#762520"}
                onMouseOut={e => e.currentTarget.style.background = "#ab3730"}
              >
                Logout
              </button>
            </>
          )}
        </div>
      </nav>
      {/* Secondary Navbar for logged-in users */}
      {isLoggedIn && (
        <nav className="border-t border-[#ede9dd] py-2 shadow-inner bg-[#f5f5f4]">
          <div className="flex justify-center gap-8">
            <NavLink
              to="/"
              className={({ isActive }) =>
                `px-3 py-1 rounded-full font-medium transition ${
                  isActive
                    ? "text-white"
                    : ""
                }`
              }
              style={({ isActive }) => ({
                background: isActive ? "#b7a680" : "transparent",
                color: isActive ? "#fff" : "#2d2c2a",
              })}
            >
              Home
            </NavLink>
            <NavLink
              to="/books"
              className={({ isActive }) =>
                `px-3 py-1 rounded-full font-medium transition ${
                  isActive
                    ? "text-white"
                    : ""
                }`
              }
              style={({ isActive }) => ({
                background: isActive ? "#b7a680" : "transparent",
                color: isActive ? "#fff" : "#2d2c2a",
              })}
            >
              Ebooks
            </NavLink>
            <NavLink
              to="/audiobooks"
              className={({ isActive }) =>
                `px-3 py-1 rounded-full font-medium transition ${
                  isActive
                    ? "text-white"
                    : ""
                }`
              }
              style={({ isActive }) => ({
                background: isActive ? "#b7a680" : "transparent",
                color: isActive ? "#fff" : "#2d2c2a",
              })}
            >
              Audiobooks
            </NavLink>
            <NavLink
              to="/sale"
              className={({ isActive }) =>
                `px-3 py-1 rounded-full font-medium transition ${
                  isActive
                    ? "text-white"
                    : ""
                }`
              }
              style={({ isActive }) => ({
                background: isActive ? "#b7a680" : "transparent",
                color: isActive ? "#fff" : "#2d2c2a",
              })}
            >
              Sale
            </NavLink>
            <NavLink
              to="/shelf"
              className={({ isActive }) =>
                `px-3 py-1 rounded-full font-medium transition ${
                  isActive
                    ? "text-white"
                    : ""
                }`
              }
              style={({ isActive }) => ({
                background: isActive ? "#b7a680" : "transparent",
                color: isActive ? "#fff" : "#2d2c2a",
              })}
            >
              Myself
            </NavLink>
          </div>
        </nav>
      )}
    </header>
  );
};

export default Navbar;
