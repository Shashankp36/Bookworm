// ✅ src/pages/Ebook.jsx
import React, { useEffect, useState } from "react";
import BookCard from "../components/BookCard";

const Ebook = () => {
  const [ebooks, setEbooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchEbooks = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/products/");
        if (!res.ok) throw new Error("Failed to fetch products");

        const data = await res.json();
        const filtered = data.filter(
          (product) =>
            product.format &&
            product.format.formatName &&
            product.format.formatName.toLowerCase() === "ebook"
        );

        // const formatted = filtered.map((product) => ({
        //   id: product.productId,
        //   title: product.title,
        //   author: product.author?.authorName,
        //   image: product.coverUrl,
        // }));

        setEbooks(filtered);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchEbooks();
  }, []);

  return (
    <div className="bg-white min-h-screen p-3 mt-40">
      <h2 className="text-2xl font-bold text-gray-800 mb-6">📘 All eBooks</h2>

      {loading && <p className="text-gray-500">Loading...</p>}
      {error && <p className="text-red-500">Error: {error}</p>}
      {!loading && !error && ebooks.length === 0 && (
        <p className="text-gray-500">No eBooks found.</p>
      )}

      {/* ✅ Adjusted Grid Layout */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-6">
        {ebooks.map((book) => (
          <BookCard key={book.id} book={book} />
        ))}
      </div>
    </div>
  );
};

export default Ebook;
