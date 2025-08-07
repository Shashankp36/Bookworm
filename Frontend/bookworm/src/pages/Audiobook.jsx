import React, { useEffect, useState } from "react";
import BookCard from "../components/BookCard";

const Audiobook = () => {
  const [audiobooks, setAudiobooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchAudiobooks = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/products/");
        if (!res.ok) throw new Error("Failed to fetch products");

        const data = await res.json();

        const filtered = data.filter(
          (product) =>
            product.format &&
            product.format.formatName &&
            product.format.formatName.toLowerCase() === "audiobook"
        );

        const formatted = filtered.map((product) => ({
          id: product.productId,
          title: product.title,
          author: product.author?.authorName,
          image: product.coverUrl,
        }));

        setAudiobooks(formatted);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchAudiobooks();
  }, []);

  return (
    <div className="bg-white min-h-screen p-6">
      <h2 className="text-2xl font-bold text-gray-800 mb-6">🎧 All Audiobooks</h2>

      {loading && <p className="text-gray-500">Loading...</p>}
      {error && <p className="text-red-500">Error: {error}</p>}
      {!loading && !error && audiobooks.length === 0 && (
        <p className="text-gray-500">No audiobooks found.</p>
      )}

      <div className="grid grid-cols-2 md:grid-cols-4 gap-6">
        {audiobooks.map((book) => (
          <BookCard key={book.id} book={book} />
        ))}
      </div>
    </div>
  );
};

export default Audiobook;
