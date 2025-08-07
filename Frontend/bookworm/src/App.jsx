import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import WelcomePage from "./pages/WelcomePage";
import HomePage from "./pages/HomePage";
import Login from "./pages/Login";
import About_us from "./pages/About_us";
import Footer from "./pages/Footer";
import Ebook from "./pages/Ebook";
import Audiobook from "./pages/Audiobook";
import ProductDetail from './pages/ProductDetail'; // ✅ Product detail page
import Header from "./components/Header";
import Orders from "./components/Orders"; // ✅ My Orders page
import OrderDetails from "./components/OrderDetails"; // ✅ Order Details page
import Shelf from "./pages/Shelf"; // ✅ Shelf page

import { AuthProvider, useAuth } from "./contexts/AuthContext";
import PdfViewer from "./components/PdfViewer";

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
          element={isLoggedIn ? <HomePage /> : <Navigate to="/" replace />}
        />

        {/* Login Route - Only when NOT logged in */}
        <Route
          path="/login"
          element={isLoggedIn ? <Navigate to="/home" replace /> : <Login />}
        />

        {/* ✅ Public About Us page */}
        <Route path="/about" element={<About_us />} />
        <Route path="/shelf" element={<Shelf />} />

        {/* ✅ eBooks page */}
        <Route path="/ebooks" element={<Ebook />} />

        {/* ✅ AudioBooks page */}
        <Route path="/audiobooks" element={<Audiobook />} />

        {/* ✅ Product Detail route (view book details) */}
        <Route
          path="/product/:id"
          element={
            isLoggedIn ? <ProductDetail /> : <Navigate to="/login" replace />
          }
        />

        {/* ✅ My Orders route */}
        <Route
          path="/orders"
          element={isLoggedIn ? <Orders /> : <Navigate to="/login" replace />}
        />

        {/* ✅ Order Details route */}
        <Route
          path="/orders/:orderId"
          element={
            isLoggedIn ? <OrderDetails /> : <Navigate to="/login" replace />
          }
        />

        {/* Fallback route */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>

      {/* ✅ Footer is always visible */}
      <Footer />
    </>
  );
}

function App() {
  return (
    <AuthProvider>
      <Router>
        <ConditionalRoutes />
        <PdfViewer/>
      </Router>
    </AuthProvider>
  );
}

export default App;
