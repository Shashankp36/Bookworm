// App.js

import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import WelcomePage from "./pages/WelcomePage";
import HomePage from "./pages/HomePage";
import Login from "./pages/Login";
import Navbar from "./components/Navbar";
import { AuthProvider, useAuth } from "./contexts/AuthContext";

// Wrapper Component to switch landing/home
function ConditionalRoutes() {
  const { isLoggedIn } = useAuth();  // <-- "user" ki jagah "isLoggedIn"

  return (
    <>
      {/* ✅ Show Navbar only when logged in */}
      {isLoggedIn && <Navbar />}

      <Routes>
        {isLoggedIn ? (
          // If logged in
          <Route path="/" element={<HomePage />} />
        ) : (
          // If not logged in
          <Route path="/" element={<WelcomePage />} />
        )}

        {/* Login route accessible in both cases */}
        <Route path="/login" element={<Login />} />
      </Routes>
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
