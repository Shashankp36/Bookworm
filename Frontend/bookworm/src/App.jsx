// App.js

import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import WelcomePage from "./pages/WelcomePage";
import HomePage from "./pages/HomePage";
import Login from "./pages/Login";
import Header from "./components/Header";
import { AuthProvider, useAuth } from "./contexts/AuthContext";

// Wrapper Component for Routes
function ConditionalRoutes() {
  const { isLoggedIn } = useAuth();

  return (
    <>
      {/* Show Header only if logged in */}
      {isLoggedIn && <Header />}

      <Routes>
        {/* Redirect root based on auth status */}
        <Route
          path="/"
          element={
            isLoggedIn ? <Navigate to="/home" replace /> : <WelcomePage />
          }
        />

        {/* Home Route - Only for Logged In */}
        <Route
          path="/home"
          element={
            isLoggedIn ? <HomePage /> : <Navigate to="/" replace />
          }
        />

        {/* Login Route - Only when NOT logged in */}
        <Route
          path="/login"
          element={
            isLoggedIn ? <Navigate to="/home" replace /> : <Login />
          }
        />

        {/* Optional fallback for unknown routes */}
        <Route
          path="*"
          element={<Navigate to="/" replace />}
        />
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
