import React, { useEffect, useState } from "react";
import { Button, Image, Card } from "react-bootstrap";
import { useCart } from "../contexts/CartContext";
import { useNavigate } from "react-router-dom"; // ✅ added

const CartPage = () => {
  const { cartItems, removeFromCart, fetchCartItems } = useCart();
  const navigate = useNavigate(); // ✅ initialize

  const [showModal, setShowModal] = useState(false);
  const [loadingPayment, setLoadingPayment] = useState(false);

  const removeItem = async (productId) => {
    try {
      const token = localStorage.getItem("accessToken");

      const response = await fetch(
        `http://localhost:8080/api/cart/items/product/${productId}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) throw new Error("Failed to remove item");

      removeFromCart(productId);
    } catch (error) {
      console.error("Remove error:", error);
    }
  };

  const subtotal = cartItems
    .reduce((sum, item) => {
      const product = item.product;
      if (!product) return sum;

      const isRent = item.itemType === "RENT";
      const price = isRent
        ? Number(product.rentPerDay ?? 0) * (item.days ?? 0)
        : Number(product.discountedPrice ?? product.price ?? 0);

      return sum + price;
    }, 0)
    .toFixed(2);

  useEffect(() => {
    fetchCartItems();
  }, []);

  const handleCheckout = async () => {
    try {
      const token = localStorage.getItem("accessToken");
      const amount = subtotal;
      const paymentMode = "CARD";

      const response = await fetch(
        `http://localhost:8080/api/order/pay?amount=${amount}&paymentMode=${paymentMode}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) throw new Error("Checkout failed");

      const result = await response.text();
      console.log(result);

      setShowModal(true);
      setLoadingPayment(true);

      // ✅ Redirect after payment
      setTimeout(() => {
        setLoadingPayment(false);
        setTimeout(() => {
          setShowModal(false);
          fetchCartItems();
          navigate("/shelf"); // ✅ Redirect to shelf page
        }, 2000);
      }, 2000);
    } catch (error) {
      console.error("Checkout error:", error);
      alert("❌ Checkout failed. Please try again.");
    }
  };

  return (
    <div className="w-full mx-auto px-8 py-6 mt-32 max-w-6xl">
      <h1 className="text-5xl font-extrabold text-center text-yellow-500 mb-8 uppercase tracking-wider drop-shadow">
        Cart
      </h1>

      <h6 className="mb-6 text-xl font-semibold text-center text-gray-800">
        You have{" "}
        <span className="text-yellow-600">{cartItems.length}</span> product
        {cartItems.length !== 1 && "s"} in your cart
      </h6>

      {cartItems.length === 0 ? (
        <p className="text-center text-muted text-lg">
          Nothing to see in the cart.
        </p>
      ) : (
        <>
          <div className="space-y-5">
            {cartItems.map((item) => {
              const product = item.product || {};
              const imageUrl = product.coverUrl || "/images/default.png";
              const title = product.title || "No Title";
              const format = product.format?.formatName ?? "N/A";
              const genre = product.genre?.genreName ?? "N/A";
              const language = product.language?.languageName ?? "N/A";
              const author = product.author?.authorName ?? "Unknown";

              const isRent = item.itemType === "RENT";
              const price = isRent
                ? Number(product.rentPerDay ?? 0) * (item.days ?? 0)
                : Number(product.discountedPrice ?? product.price ?? 0);

              return (
                <Card
                  key={item.cartItemId}
                  className="p-4 rounded-lg border border-gray-300 shadow-sm"
                >
                  <div className="flex flex-col md:flex-row items-center gap-6">
                    <Image
                      src={imageUrl}
                      alt={title}
                      rounded
                      className="w-28 h-28 object-cover border shadow"
                    />
                    <div className="flex-1 w-full">
                      <h5 className="text-xl font-bold mb-1 text-gray-900">
                        {title}
                      </h5>
                      <p className="text-sm mb-1 text-gray-700">
                        <strong className="text-gray-800">{language}</strong>{" "}
                        by <strong className="text-gray-800">{author}</strong>
                      </p>
                      <div className="text-sm text-gray-600 space-x-4">
                        <span>
                          <strong className="text-gray-700">Format:</strong>{" "}
                          {format}
                        </span>
                        <span>
                          <strong className="text-gray-700">Genre:</strong>{" "}
                          {genre}
                        </span>
                      </div>
                      <div className="mt-2 text-sm text-gray-700">
                        <strong>Type:</strong> {item.itemType}
                        {isRent && item.days && (
                          <span>
                            {" "}
                            • <strong>Days:</strong> {item.days}
                          </span>
                        )}
                      </div>
                    </div>
                    <div className="flex flex-col items-end gap-2 min-w-[100px]">
                      <div className="text-lg font-semibold text-green-700">
                        ₹{price.toFixed(2)}
                      </div>
                      <Button
                        variant="danger"
                        size="sm"
                        className="uppercase tracking-wide font-semibold"
                        onClick={() => removeItem(product.productId)}
                      >
                        Remove
                      </Button>
                    </div>
                  </div>
                </Card>
              );
            })}
          </div>

          <div className="flex justify-end mt-8">
            <div className="text-right">
              <div className="text-xl font-bold text-gray-900">
                Subtotal: <span className="text-green-700">₹{subtotal}</span>
              </div>
              <div className="text-sm text-gray-500">
                * Excludes taxes and delivery charges
              </div>
            </div>
          </div>

          <div className="flex justify-between mt-10">
            <Button
              variant="link"
              className="text-black uppercase tracking-wider font-semibold text-base"
              href="/"
            >
              ← Continue Shopping
            </Button>
            <Button
              variant="primary"
              className="uppercase tracking-wider px-4 py-2 text-base font-semibold shadow"
              onClick={handleCheckout}
            >
              Go to Checkout →
            </Button>
          </div>
        </>
      )}

      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white px-10 py-8 rounded-lg shadow-lg text-center">
            <h2 className="text-2xl font-bold text-gray-800 mb-4">Payment</h2>
            {loadingPayment ? (
              <div className="flex flex-col items-center">
                <div className="border-4 border-yellow-500 border-t-transparent rounded-full w-12 h-12 animate-spin mb-4"></div>
                <p className="text-lg font-semibold text-gray-700">
                  Processing Payment...
                </p>
              </div>
            ) : (
              <div className="text-green-600 font-bold text-xl">
                ✅ Payment Successful!
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default CartPage;
