import React, { useState } from "react";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { useCart } from "../contexts/CartContext";

const fallbackCover = "https://strangerthansf.com/scans/asimov-foundation.jpg";
const API_URL = "http://localhost:8080/api/cart/items";

const BookCard = ({ book }) => {
  const navigate = useNavigate();
  const { addToCart } = useCart();
  const [rentModalOpen, setRentModalOpen] = useState(false);

  const handleBuy = async () => {
    const accessToken = localStorage.getItem("accessToken");
    try {
      const response = await fetch(API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
        body: JSON.stringify({
          productId: book.productId,
          itemType: "PURCHASE",
          days: null,
        }),
      });

      if (!response.ok) throw new Error("Network error");

      const data = await response.json();
      addToCart(data);
      toast.success(`${book.title} added to cart (Buy)!`);
    } catch (err) {
      toast.error("Failed to add book to cart.");
    }
  };

  const handleRentConfirm = async (days) => {
    const accessToken = localStorage.getItem("accessToken");
    if (isNaN(days) || days <= 0) {
      toast.warning("Please enter a valid number of days.");
      return;
    }

    try {
      const response = await fetch(API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
        body: JSON.stringify({
          productId: book.productId,
          itemType: "RENT",
          days: days,
        }),
      });

      if (!response.ok) throw new Error("Network error");

      const data = await response.json();
      addToCart(data);
      toast.success(`${book.title} added for ${days} days (Rent)!`);
      setRentModalOpen(false);
    } catch (err) {
      toast.error("Failed to rent the book.");
    }
  };

  const coverUrl = book.coverUrl?.trim() !== "" ? book.coverUrl : fallbackCover;

  return (
    <>
      <div className="rounded-2xl p-4 shadow-md bg-[#e5e3df] text-black w-54 h-[360px] flex flex-col justify-between">
        <div className="flex flex-col items-center space-y-3">
          <img
            src={coverUrl}
            alt={book.title}
            className="h-40 w-28 object-cover rounded shadow-lg cursor-pointer"
            onClick={() => navigate(`/product/${book.productId}`)}
          />
          <h3 className="text-sm font-bold text-center line-clamp-2">{book.title}</h3>
          <p className="text-xs font-medium text-center text-gray-700 line-clamp-1">
            {book.author?.authorName || "Unknown Author"}
          </p>
        </div>

        <div className="mt-4 flex flex-col items-center">
          {book.discountedPrice && book.discountedPrice < book.price ? (
            <div className="flex items-center justify-center space-x-2 mb-2">
              <span className="line-through text-xs text-red-500">₹{book.price}</span>
              <span className="bg-black px-2 py-0.5 rounded text-xs font-bold text-white">
                ₹{book.discountedPrice}
              </span>
            </div>
          ) : (
            <div className="flex justify-center mb-2">
              <span className="bg-white text-black px-2 py-1 rounded text-sm font-semibold">
                ₹{book.price}
              </span>
            </div>
          )}

          <div className="flex space-x-2 w-full">
            <button
              onClick={handleBuy}
              className="bg-green-600 hover:bg-green-700 text-white px-3 py-1 text-sm rounded w-1/2"
            >
              Buy
            </button>
            <button
              onClick={() => setRentModalOpen(true)}
              className="bg-blue-600 hover:bg-blue-700 text-white px-3 py-1 text-sm rounded w-1/2"
            >
              Rent
            </button>
          </div>
        </div>
      </div>

      <RentModal
        isOpen={rentModalOpen}
        onClose={() => setRentModalOpen(false)}
        onConfirm={handleRentConfirm}
      />
    </>
  );
};

export default BookCard;

// Modal Component
const RentModal = ({ isOpen, onClose, onConfirm }) => {
  const [days, setDays] = useState("3");
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center">
      <div className="bg-black rounded-lg p-6 w-80 shadow-lg space-y-4">
        <h2 className="text-lg font-bold text-center">Rent Book</h2>
        <input
          type="number"
          value={days}
          onChange={(e) => setDays(e.target.value)}
          className="w-full border px-3 py-2 rounded"
          placeholder="Enter number of days"
          style={{ color: "black" }}
        />
        <div className="flex justify-end space-x-2">
          <button
            onClick={onClose}
            className="bg-gray-300 hover:bg-gray-400 text-black px-4 py-1 rounded"
          >
            Cancel
          </button>
          <button
            onClick={() => onConfirm(parseInt(days))}
            className="bg-blue-600 hover:bg-blue-700 text-black px-4 py-1 rounded"
          >
            Rent
          </button>
        </div>
      </div>
    </div>
  );
};
