import React from 'react';
import { useNavigate } from 'react-router-dom';

const fallbackCover = "https://strangerthansf.com/scans/asimov-foundation.jpg";

const BookCard = ({ book }) => {
  const navigate = useNavigate();

  const handleViewDetails = () => {
    navigate(`/product/${book.productId}`);
  };

  const handleAddToCart = () => {
    console.log(`Add to cart: ${book.title}`);
    // Add to cart logic here
  };

  const coverUrl = book.coverUrl?.trim() !== "" ? book.coverUrl : fallbackCover;

  const showDiscount = book.discountedPrice !== null && book.discountedPrice < book.price;

  return (
    <div className="rounded-2xl p-4 shadow-md bg-[#e5e3df] text-black w-54 flex flex-col justify-between h-full">
      <div className="flex flex-col items-center space-y-3 flex-grow">
        <img
          src={coverUrl}
          alt={book.title}
          className="h-42 w-28 object-cover rounded shadow-lg cursor-pointer"
          onClick={handleViewDetails}
        />

        <h3 className="text-lg font-bold text-center">{book.title}</h3>
        <p className="text-sm font-medium text-center">{book.author?.authorName}</p>
      </div>

      {/* Bottom Section: Price + Button */}
      <div className="mt-8 flex flex-col items-center">
        {/* Price Section */}
        {showDiscount ? (
          <div className="flex items-center justify-center space-x-2 mb-3">
            <span className="line-through text-sm text-red-500">₹{book.price}</span>
            <span className="bg-black px-4 py-0.5 rounded text-sm font-bold text-white">
              ₹{book.discountedPrice}
            </span>
          </div>
        ) : (
          <div className="flex justify-center mb-3">
            <span className="text-black px-2 py-1 rounded text-sm font-semibold">
              ₹{book.price}
            </span>
          </div>
        )}

        {/* Add to Cart Button */}
        <button
          onClick={handleAddToCart}
          className="bg-green-600 hover:bg-green-700 text-white px-6 py-2 text-sm rounded w-full"
        >
          Add to Cart
        </button>
      </div>
    </div>
  );
};

export default BookCard;
