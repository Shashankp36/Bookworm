import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import WelcomePage from "./pages/WelcomePage";
import HomePage from "./pages/HomePage";
import Login from "./pages/Login";
import About_us from "./pages/About_us";
import Footer from "./pages/Footer";
import Ebook from "./pages/Ebook";
import Audiobook from "./pages/Audiobook";
import ProductDetail from "./pages/ProductDetail";
import Header from "./components/Header";
import CartPage from "./pages/CartPage";

import { AuthProvider, useAuth } from "./contexts/AuthContext";
import { CartProvider } from "./contexts/CartContext"; // ✅ Cart Context

function ConditionalRoutes() {
  const { isLoggedIn } = useAuth();

  return (
    <>
      {isLoggedIn && <Header />}

      <Routes>
        <Route
          path="/"
          element={isLoggedIn ? <Navigate to="/home" replace /> : <WelcomePage />}
        />
        <Route
          path="/home"
          element={isLoggedIn ? <HomePage /> : <Navigate to="/" replace />}
        />
        <Route
          path="/login"
          element={isLoggedIn ? <Navigate to="/home" replace /> : <Login />}
        />
        <Route path="/about" element={<About_us />} />
        <Route path="/ebooks" element={<Ebook />} />
        <Route path="/audiobooks" element={<Audiobook />} />
        <Route
          path="/product/:id"
          element={isLoggedIn ? <ProductDetail /> : <Navigate to="/login" replace />}
        />
        <Route
          path="/cart"
          element={isLoggedIn ? <CartPage /> : <Navigate to="/login" replace />}
        />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>

      <Footer />
    </>
  );
}

function App() {
  return (
    <AuthProvider>
      <CartProvider>
        <Router>
          <ConditionalRoutes />
        </Router>
      </CartProvider>
    </AuthProvider>
  );
}

export default App;
