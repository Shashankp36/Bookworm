import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  return (
    <nav className="bg-gray-800 px-6 py-4 flex justify-between items-center shadow-md">
      <h1 className="text-xl font-bold text-white">📚 BookWorm</h1>
      <div className="space-x-6 text-gray-300">
        <Link to="/" className="hover:text-white">Home</Link>
        <Link to="/books" className="hover:text-white">Books</Link>
        <Link to="/audiobooks" className="hover:text-white">Audiobooks</Link>
        <Link to="/contact" className="hover:text-white">Contact</Link>
        <Link to="/auth" className="bg-indigo-600 px-3 py-1 rounded text-white hover:bg-indigo-700 transition">Login/Signup</Link>
      </div>
    </nav>
  );
};

export default Navbar;
