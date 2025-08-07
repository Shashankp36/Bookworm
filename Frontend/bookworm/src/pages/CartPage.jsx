import React, { useEffect, useState } from "react";
import { Button, Image, Card } from "react-bootstrap";
import { useCart } from "../contexts/CartContext";

const CartPage = () => {
  const { cartItems, removeFromCart, fetchCartItems } = useCart();

  const [showModal, setShowModal] = useState(false);
  const [loadingPayment, setLoadingPayment] = useState(false);

  const removeItem = async (cartItemId) => {
    try {
      const token = localStorage.getItem("accessToken");
      const response = await fetch(
        `http://localhost:8080/api/cart/items/product/${cartItemId}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) throw new Error("Failed to remove item");

      removeFromCart(cartItemId); // frontend se turant hata do
    } catch (error) {
      console.error("Remove error:", error);
    }
  };

  const subtotal = cartItems
    .reduce(
      (sum, item) =>
        sum + (item.product?.discountedPrice ?? item.product?.price ?? 0),
      0
    )
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

      // Show payment modal
      setShowModal(true);
      setLoadingPayment(true);

      // Step 1: Show loading for 2 seconds
      setTimeout(() => {
        setLoadingPayment(false); // Show success
        // Step 2: Close modal after another 2 seconds
        setTimeout(() => {
          setShowModal(false);
          fetchCartItems(); // Refresh cart
        }, 2000);
      }, 2000);
    } catch (error) {
      console.error("Checkout error:", error);
      alert("❌ Checkout failed. Please try again.");
    }
  };

  return (
    <div className="w-full mx-auto px-8 py-6 mt-32 max-w-6xl">
      {/* Yellow Bold Heading */}
      <h1 className="text-5xl font-extrabold text-center text-yellow-500 mb-8 uppercase tracking-wider drop-shadow">
        Cart
      </h1>

      {/* Cart Info */}
      <h6 className="mb-6 text-xl font-semibold text-center text-gray-800">
        You have <span className="text-yellow-600">{cartItems.length}</span> product{cartItems.length !== 1 && 's'} in your cart
      </h6>

      {cartItems.length === 0 ? (
        <p className="text-center text-muted text-lg">Nothing to see in the cart.</p>
      ) : (
        <>
          {/* Product Cards */}
          <div className="space-y-5">
            {cartItems.map((item) => {
              const product = item.product || {};
              const imageUrl = product.coverUrl || "/images/default.png";
              const title = product.title || "No Title";
              const price = product.discountedPrice ?? product.price ?? 0;
              const format = product.format?.formatName ?? "N/A";
              const genre = product.genre?.genreName ?? "N/A";
              const language = product.language?.languageName ?? "N/A";
              const author = product.author?.authorName ?? "Unknown";

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
                      <h5 className="text-xl font-bold mb-1 text-gray-900">{title}</h5>
                      <p className="text-sm mb-1 text-gray-700">
                        <strong className="text-gray-800">{language}</strong> by{" "}
                        <strong className="text-gray-800">{author}</strong>
                      </p>
                      <div className="text-sm text-gray-600 space-x-4">
                        <span>
                          <strong className="text-gray-700">Format:</strong> {format}
                        </span>
                        <span>
                          <strong className="text-gray-700">Genre:</strong> {genre}
                        </span>
                      </div>
                    </div>
                    <div className="flex flex-col items-end gap-2 min-w-[100px]">
                      <div className="text-lg font-semibold text-green-700">
                        ${price.toFixed(2)}
                      </div>
                      <Button
                        variant="danger"
                        size="sm"
                        className="uppercase tracking-wide font-semibold"
                        onClick={() => removeItem(item.cartItemId)}
                      >
                        Remove
                      </Button>
                    </div>
                  </div>
                </Card>
              );
            })}
          </div>

          {/* Subtotal Section */}
          <div className="flex justify-end mt-8">
            <div className="text-right">
              <div className="text-xl font-bold text-gray-900">
                Subtotal: <span className="text-green-700">${subtotal}</span>
              </div>
              <div className="text-sm text-gray-500">
                * Excludes taxes and delivery charges
              </div>
            </div>
          </div>

          {/* Action Buttons */}
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

      {/* Payment Popup Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white px-10 py-8 rounded-lg shadow-lg text-center">
            <h2 className="text-2xl font-bold text-gray-800 mb-4">Payment</h2>
            {loadingPayment ? (
              <div className="flex flex-col items-center">
                <div className="border-4 border-yellow-500 border-t-transparent rounded-full w-12 h-12 animate-spin mb-4"></div>
                <p className="text-lg font-semibold text-gray-700">Processing Payment...</p>
              </div>
            ) : (
              <div className="text-green-600 font-bold text-xl">✅ Payment Successful!</div>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default CartPage;
