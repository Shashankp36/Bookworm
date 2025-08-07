import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const fallbackCover = "https://strangerthansf.com/scans/asimov-foundation.jpg";

const ProductDetail = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const res = await fetch(`http://localhost:8080/api/products/${id}`);
        if (!res.ok) throw new Error("Failed to load product");
        const data = await res.json();
        setProduct(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [id]);

  if (loading) return <div className="p-6 text-gray-700">Loading...</div>;
  if (error) return <div className="p-6 text-red-500">Error: {error}</div>;
  if (!product) return <div className="p-6 text-gray-700">Product not found.</div>;

  // If this is Foundation book, override the image
  const coverUrl =
    product.title === "Foundation" || product.productId === 1
      ? fallbackCover
      : product.coverUrl;

  return (
    <div className="min-h-screen bg-white text-gray-900 p-6">
      <h1 className="text-3xl font-bold mb-4">{product.title}</h1>
      <img
        src={coverUrl}
        alt={product.title}
        className="w-60 h-auto mb-6 rounded shadow-md"
      />

      <p><strong>Author:</strong> {product.author?.authorName}</p>
      <p><strong>Genre:</strong> {product.genre?.genreName}</p>
      <p><strong>Format:</strong> {product.format?.formatName}</p>
      <p><strong>Price:</strong> ₹{product.discountedPrice}</p>
      <p className="mt-4 text-gray-700">{product.description}</p>
    </div>
  );
};

export default ProductDetail;
