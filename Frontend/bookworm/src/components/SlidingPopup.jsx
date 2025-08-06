// components/SlidingPopup.jsx

import React, { useEffect } from "react";

const SlidingPopup = ({ message, type = "success", onClose, duration = 3000 }) => {
  useEffect(() => {
    const timer = setTimeout(() => {
      onClose();
    }, duration);

    return () => clearTimeout(timer);
  }, [onClose, duration]);

  const bgColor =
    type === "success"
      ? "bg-green-500"
      : type === "error"
      ? "bg-red-500"
      : "bg-blue-500";

  return (
    <div
      className={`fixed bottom-5 left-5 z-50 transition-transform duration-500 transform translate-y-0 ${bgColor} text-white px-6 py-4 rounded-2xl shadow-2xl`}
    >
      <div className="flex items-center justify-between space-x-4">
        <span className="font-medium">{message}</span>
        <button
          onClick={onClose}
          className="text-white text-xl font-bold leading-none hover:text-gray-200"
        >
          &times;
        </button>
      </div>
    </div>
  );
};

export default SlidingPopup;
