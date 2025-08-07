import React, { useState } from "react";
import BookCard from "./BookCard";
import Pagination from "./Pagination";

const AllProducts = ({ products = [] }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const productsPerPage = 10;

  const totalPages = Math.ceil(products.length / productsPerPage);
  const indexOfLast = currentPage * productsPerPage;
  const indexOfFirst = indexOfLast - productsPerPage;
  const currentProducts = products.slice(indexOfFirst, indexOfLast);

  return (
    <div className="px-6 py-12 bg-gray-900 text-white">
      <h2 className="text-2xl font-bold text-white mb-6 text-center">
        📘 All Products
      </h2>

      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-6">
        {currentProducts.map((product) => (
          <BookCard key={product.id} book={product} />
        ))}
      </div>

      {/* Pagination Component */}
      {totalPages > 1 && (
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={setCurrentPage}
        />
      )}
    </div>
  );
};

export default AllProducts;
