
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import WelcomePage from "./pages/WelcomePage";
import HomePage from "./pages/HomePage";
import Login from "./pages/Login";
import About_us from "./pages/About_us";
import Footer from "./pages/Footer";
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

        {/* ✅ NEW: Public About Us page */}
        <Route
          path="/about"
          element={<About_us />}
        />

        {/* Optional fallback for unknown routes */}
        <Route
          path="*"
          element={<Navigate to="/" replace />}
        />
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
