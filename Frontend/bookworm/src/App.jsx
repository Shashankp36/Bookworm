import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import Login from './pages/Login';
import Navbar from './components/Navbar';
import { AuthProvider } from './contexts/AuthContext';
import ProductPage from './pages/ProductPage';

function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/auth" element={<Login />} />
          <Route path="/products" element={<ProductPage />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
