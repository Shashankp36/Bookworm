// App.js

import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import WelcomePage from "./pages/WelcomePage";
import HomePage from "./pages/HomePage";
import Login from "./pages/Login";
import About_us from "./pages/About_us";
import Footer from "./pages/Footer";
import Navbar from "./components/Navbar";
import { AuthProvider, useAuth } from "./contexts/AuthContext";

// Wrapper Component to switch landing/home
function ConditionalRoutes() {
  const { isLoggedIn } = useAuth();

  return (
    <>
      {/* ✅ Navbar visible when logged in */}
      {isLoggedIn && <Navbar />}

      <Routes>
        {isLoggedIn ? (
          <Route path="/" element={<HomePage />} />
        ) : (
          <Route path="/" element={<WelcomePage />} />
        )}

        {/* Accessible routes for both logged-in and guest users */}
        <Route path="/login" element={<Login />} />
        <Route path="/about_us" element={<About_us />} />
      </Routes>

      {/* ✅ Footer visible always */}
      <Footer />
    </>
  );
}

function App() {
  return (
    <AuthProvider>
      <Router>
        <ConditionalRoutes />
      </Router>
    </AuthProvider>
  );
}

export default App;
