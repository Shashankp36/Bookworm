import React from 'react';
import {
  FaFacebookF,
  FaInstagram,
  FaTwitter,
  FaEnvelope,
  FaPhoneAlt,
  FaMapMarkerAlt
} from 'react-icons/fa';

const Footer = () => {
  return (
    <footer className="bg-gray-800 text-gray-300 py-8 mt-0">
      <div className="max-w-6xl mx-auto px-4">
        {/* Top Section */}
        <div className="grid md:grid-cols-3 gap-8 text-sm">
          
          {/* Follow Us */}
          <div>
            <h4 className="text-2xl font-semibold text-indigo-400 mb-2">🌐 Follow Us</h4>
            <div className="flex gap-6 text-indigo-300 mt-2 text-3xl">
              <a href="#" className="hover:text-white"><FaFacebookF /></a>
              <a href="#" className="hover:text-white"><FaInstagram /></a>
              <a href="#" className="hover:text-white"><FaTwitter /></a>
            </div>
          </div>


          {/* About Bookworm */}
          <div className="text-center">
            <h4 className="text-2xl font-semibold text-indigo-400 mb-2">📚 Bookworm</h4>
            <p className="text-lg">
              Your digital companion for discovering and enjoying eBooks and audiobooks.
              Read or listen — anytime, anywhere.
            </p>
          </div>


          {/* Contact Info */}
          <div className="text-right">
            <h4 className="text-2xl font-semibold text-indigo-400 mb-2">📇 Contact Us</h4>
            <p className="flex items-center justify-end gap-2 text-lg"><FaEnvelope /> bookworm@gmail.com</p>
            <p className="flex items-center justify-end gap-2 text-lg"><FaPhoneAlt /> +91-9075275929</p>
            <p className="flex items-center justify-end gap-2 text-lg"><FaMapMarkerAlt /> Mumbai, Maharashtra</p>
          </div>
        </div>

        {/* Bottom Text */}
        <div className="border-t border-gray-700 mt-8 pt-4 text-center text-base text-gray-400">
          &copy; {new Date().getFullYear()} Bookworm. All rights reserved.
        </div>
      </div>
    </footer>
  );
};

export default Footer;
