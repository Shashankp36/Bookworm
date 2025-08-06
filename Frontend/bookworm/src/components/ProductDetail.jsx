import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const ProductDetail = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const res = await fetch(`http://localhost:8080/api/products/${id}`);
        if (!res.ok) throw new Error("Failed to fetch product");
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

  if (loading) return <div className="p-6 text-white">Loading...</div>;
  if (error) return <div className="p-6 text-red-400">Error: {error}</div>;
  if (!product) return <div className="p-6 text-white">Product not found.</div>;

  return (
    <div className="min-h-screen bg-gray-900 text-white p-6">
      <h1 className="text-3xl font-bold mb-4">{product.title}</h1>
      <img
        src={product.coverUrl}
        alt={product.title}
        className="w-60 h-auto mb-6 rounded shadow"
      />
      <p className="text-gray-300 mb-2"><strong>Author:</strong> {product.author?.authorName}</p>
      <p className="text-gray-300 mb-2"><strong>Genre:</strong> {product.genre?.genreName}</p>
      <p className="text-gray-300 mb-2"><strong>Format:</strong> {product.format?.formatName}</p>
      <p className="text-gray-300 mb-2"><strong>Price:</strong> ₹{product.discountedPrice}</p>
      <p className="text-gray-400 mt-4">{product.description}</p>
    </div>
  );
};

export default ProductDetail;
