
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import Login from './pages/Login'; // move your login component here
import Navbar from './components/Navbar'; 


function App() {
  return (

    <Router>
       <Navbar/>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/auth" element={<Login />} />
      </Routes>
    </Router>
  );

}

export default App;
