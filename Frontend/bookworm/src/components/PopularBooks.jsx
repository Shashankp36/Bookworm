
import React from "react";
import BookCard from "./BookCard";

const PopularBooks = ({ books = [] }) => {
  return (
    <div className="px-6 py-12 bg-gray-900">
      <h2 className="text-2xl font-bold text-white mb-6 text-center">
        📚 Popular Reads
      </h2>
      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-6">
        {books.map((book) => (
          <BookCard key={book.productId} book={book} />
        ))}
      </div>
    </div>
  );
};

export default PopularBooks;
