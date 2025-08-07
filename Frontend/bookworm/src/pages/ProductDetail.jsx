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

  const coverUrl =
    product.title === "Foundation" || product.productId === 1
      ? fallbackCover
      : product.coverUrl;

  return (
    <div className="bg-white min-h-screen p-3 mt-40">
    <div className="min-h-screen bg-white text-gray-900 p-10">
      <div className="flex flex-col lg:flex-row items-start gap-10">
        <img
          src={coverUrl}
          alt={product.title}
          className="w-96 h-[500px] object-cover rounded shadow-md"
        />

        {/* Right: Details */}
        <div className="flex flex-col gap-4 max-w-2xl">
          <h1 className="text-4xl font-bold">{product.title}</h1>
          <p className="text-lg text-gray-700">
            <strong>Author:</strong> {product.author?.authorName}
          </p>

          <p className="text-base text-gray-700">{product.description}</p>

          {/* Price Display */}
          <div className="flex items-center gap-4 text-lg mt-2">
            {product.discountedPrice < product.price ? (
              <>
                <span className="line-through text-red-600">
                  ₹{product.price}
                </span>
                <span className="text-black font-semibold">
                  ₹{product.discountedPrice}
                </span>
              </>
            ) : (
              <span className="text-black font-semibold">
                ₹{product.price}
              </span>
            )}
          </div>

          <div className="flex gap-4 mt-4">
            <button className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded">
              Buy
            </button>
            <button className="bg-green-600 hover:bg-green-700 text-white px-6 py-2 rounded">
              Rent
            </button>
          </div>
        </div>
      </div>
    </div>
    </div>
  );
};

export default ProductDetail;
