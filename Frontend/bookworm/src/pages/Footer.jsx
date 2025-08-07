import React from 'react';
import {
  FaFacebookF,
  FaInstagram,
  FaTwitter,
  FaWhatsapp,   // ✅ Imported WhatsApp icon
  FaEnvelope,
  FaPhoneAlt,
  FaMapMarkerAlt
} from 'react-icons/fa';

const Footer = () => {
  return (
    <footer className="bg-[#f5f5f4] text-gray-300 py-8 mt-0">
      <div className="max-w-6xl mx-auto px-4">
        {/* Top Section */}
        <div className="grid md:grid-cols-3 gap-8 text-sm">
          
          {/* 📇 Contact Info - now on LEFT */}
          <div className="text-left">
            <h4 className="text-2xl font-semibold text-black mb-2">📇 Contact Us</h4>
            <p className="flex items-center gap-2 text-lg text-black"><FaEnvelope /> bookworm@gmail.com</p>
            <p className="flex items-center gap-2 text-lg text-black"><FaPhoneAlt /> +91-9075275929</p>
            <p className="flex items-center gap-2 text-lg text-black"><FaMapMarkerAlt /> Mumbai, Maharashtra</p>
          </div>

          {/* 📚 About Bookworm - stays in center */}
          <div className="text-center">
            <h4 className="text-2xl font-semibold text-black mb-2">📚 Bookworm</h4>
            <p className="text-lg text-black">
              Your trusted gateway to explore and enjoy a world of eBooks and audiobooks.  
              <br/>Read or listen — wherever life takes you.
            </p>
          </div>

          {/* 🌐 Follow Us - now on RIGHT */}
          <div className="flex flex-col items-center text-center">
            <h4 className="text-2xl font-semibold text-black mb-3 ">🌐 Follow Us</h4>
            <div className="flex justify-end gap-6 text-black-300 mt-2 text-3xl">
              <a href="#" className="hover:text-white"><FaFacebookF /></a>
              <a href="#" className="hover:text-white"><FaInstagram /></a>
              <a href="#" className="hover:text-white"><FaTwitter /></a>
              <a href="#" className="hover:text-white"><FaWhatsapp /></a> {/* ✅ WhatsApp Icon */}
            </div>
          </div>
        </div>

        {/* Bottom Text */}
        <div className="border-t border-gray-800 mt-8 pt-4 text-center text-base text-gray-400">
          &copy; {new Date().getFullYear()} Bookworm. All rights reserved.
        </div>
      </div>
    </footer>
  );
};

export default Footer;
