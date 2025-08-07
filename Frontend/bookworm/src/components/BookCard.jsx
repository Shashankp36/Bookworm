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
   <div className="rounded-2xl p-4 shadow-md bg-[#e5e3df] text-black w-54 h-[360px] flex flex-col justify-between">
  <div className="flex flex-col items-center space-y-3">
    <img
      src={coverUrl}
      alt={book.title}
      className="h-40 w-28 object-cover rounded shadow-lg cursor-pointer"
      onClick={handleViewDetails}
    />

    <h3 className="text-sm font-bold text-center line-clamp-2">{book.title}</h3>
    <p className="text-xs font-medium text-center text-gray-700 line-clamp-1">
      {book.author?.authorName}
    </p>
  </div>

  {/* Bottom Section: Price + Button */}
  <div className="mt-4 flex flex-col items-center">
    {book.discountedPrice && book.discountedPrice < book.price ? (
      <div className="flex items-center justify-center space-x-2 mb-2">
        <span className="line-through text-xs text-red-500">₹{book.price}</span>
        <span className="bg-black px-2 py-0.5 rounded text-xs font-bold text-white">
          ₹{book.discountedPrice}
        </span>
      </div>
<<<<<<< HEAD

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
=======
    ) : (
      <div className="flex justify-center mb-2">
        <span className="bg-white text-black px-2 py-1 rounded text-sm font-semibold">
          ₹{book.price}
        </span>
>>>>>>> 6cfc14d8e5f555779244b85eb85421b9d2dad25b
      </div>
    )}

    <button
      onClick={handleAddToCart}
      className="bg-green-600 hover:bg-green-700 text-white px-4 py-1 text-sm rounded w-full"
    >
      Add to Cart
    </button>
  </div>
</div>
  );
};

export default BookCard;
