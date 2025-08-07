import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";
import { useCart } from "../contexts/CartContext";

const fallbackCover = "https://strangerthansf.com/scans/asimov-foundation.jpg";
const API_URL = "http://localhost:8080/api/cart/items";

const ProductDetail = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [rentModalOpen, setRentModalOpen] = useState(false);
  const { addToCart } = useCart();

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

  const handleBuy = async () => {
    const accessToken = localStorage.getItem("accessToken");
    try {
      const response = await fetch(API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
        body: JSON.stringify({
          productId: product.productId,
          itemType: "PURCHASE",
        }),
      });

      if (!response.ok) throw new Error("Network error");

      const data = await response.json();
      addToCart(data);
      toast.success(`${product.title} added to cart (Buy)!`);
    } catch (err) {
      toast.error("Failed to add product to cart.");
    }
  };

  const handleRentConfirm = async (days) => {
    const accessToken = localStorage.getItem("accessToken");
    if (isNaN(days) || days <= 0) {
      toast.warning("Please enter a valid number of days.");
      return;
    }

    try {
      const response = await fetch(API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
        body: JSON.stringify({
          productId: product.productId,
          itemType: "RENT",
          days,
        }),
      });

      if (!response.ok) throw new Error("Network error");

      const data = await response.json();
      addToCart(data);
      toast.success(`${product.title} rented for ${days} days!`);
      setRentModalOpen(false);
    } catch (err) {
      toast.error("Failed to rent the product.");
    }
  };

  if (loading) return <div className="p-6 text-gray-700">Loading...</div>;
  if (error) return <div className="p-6 text-red-500">Error: {error}</div>;
  if (!product) return <div className="p-6 text-gray-700">Product not found.</div>;

  const coverUrl =
    product.title === "Foundation" || product.productId === 1
      ? fallbackCover
      : product.coverUrl;

  return (
    <div className="bg-white min-h-screen px-6 md:px-20 py-24 mt-20">
      <div className="flex flex-col md:flex-row gap-10">
        {/* Cover Image */}
        <div className="flex-shrink-0">
          <img
            src={coverUrl}
            alt={product.title}
            onError={(e) => (e.target.src = fallbackCover)}
            className="w-80 h-[500px] object-cover rounded shadow-lg transition-transform hover:scale-105 duration-300"
          />
        </div>

        {/* Product Details */}
        <div className="flex-1 flex flex-col gap-5">
          <h1 className="text-4xl font-bold text-gray-900">{product.title}</h1>
          <p className="text-lg text-gray-700">
            <strong>Author:</strong> {product.author?.authorName}
          </p>

          {product.publisher?.publisherName && (
            <p className="text-md text-gray-600">
              <strong>Publisher:</strong> {product.publisher?.publisherName}
            </p>
          )}

          <p className="text-gray-800 text-base leading-relaxed">
            {product.description}
          </p>

          {/* Price Display */}
          <div className="text-2xl mt-4 flex gap-4 items-center">
            {product.discountedPrice < product.price ? (
              <>
                <span className="text-red-500 line-through">₹{product.price}</span>
                <span className="text-green-700 font-bold">₹{product.discountedPrice}</span>
                <span className="bg-yellow-200 text-yellow-900 text-sm font-medium px-2 py-1 rounded">
                  Discount
                </span>
              </>
            ) : (
              <span className="font-bold text-black">₹{product.price}</span>
            )}
          </div>

          {/* Actions */}
          <div className="mt-6 flex gap-4">
            <button
              onClick={handleBuy}
              className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-lg font-medium shadow"
            >
              Buy Now
            </button>
            <button
              onClick={() => setRentModalOpen(true)}
              className="bg-green-600 hover:bg-green-700 text-white px-6 py-2 rounded-lg font-medium shadow"
            >
              Rent
            </button>
          </div>
        </div>
      </div>

      {/* Rent Modal */}
      <RentModal
        isOpen={rentModalOpen}
        onClose={() => setRentModalOpen(false)}
        onConfirm={handleRentConfirm}
      />
    </div>
  );
};

export default ProductDetail;

const RentModal = ({ isOpen, onClose, onConfirm }) => {
  const [days, setDays] = useState("3");

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center animate-fade-in">
      <div className="bg-white rounded-lg p-6 w-80 shadow-2xl space-y-4 transform scale-100 transition-transform duration-200">
        <h2 className="text-xl font-semibold text-center text-gray-800">Rent Book</h2>
        <input
          type="number"
          value={days}
          onChange={(e) => setDays(e.target.value)}
          className="w-full border border-gray-300 px-3 py-2 rounded text-black focus:outline-none focus:ring-2 focus:ring-blue-400"
          placeholder="Enter number of days"
        />
        <div className="flex justify-end gap-2 pt-2">
          <button
            onClick={onClose}
            className="bg-gray-300 hover:bg-gray-400 text-black px-4 py-1 rounded"
          >
            Cancel
          </button>
          <button
            onClick={() => onConfirm(parseInt(days))}
            className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-1 rounded"
          >
            Confirm
          </button>
        </div>
      </div>
    </div>
  );
};
