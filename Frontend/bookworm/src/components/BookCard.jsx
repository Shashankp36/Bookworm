import React from 'react';

const BookCard = ({ book }) => {
  return (
    <div className="bg-gray-800 rounded-lg overflow-hidden shadow-lg hover:shadow-xl transition duration-300">
      <img
        src={book.image}
        alt={book.title}
        className="h-48 w-full object-cover"
      />
      <div className="p-4">
        <h3 className="text-lg font-semibold text-white truncate">{book.title}</h3>
        <p className="text-sm text-gray-400">{book.author}</p>
        <button className="mt-4 w-full bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-1.5 rounded">
          View Details
        </button>
      </div>
    </div>
  );
};

export default BookCard;
