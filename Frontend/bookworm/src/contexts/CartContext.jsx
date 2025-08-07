import React, { createContext, useContext, useEffect, useState } from "react";

const CartContext = createContext();

export const CartProvider = ({ children }) => {
  const [cartItems, setCartItems] = useState([]);

  const fetchCartItems = async () => {
    try {
      const accessToken = localStorage.getItem("accessToken");
      const response = await fetch("http://localhost:8080/api/cart/items", {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      if (!response.ok) throw new Error("Failed to fetch cart items");
      const data = await response.json();
      setCartItems(data);
    } catch (err) {
      console.error("Fetch Cart Error:", err);
    }
  };

  const addToCart = (item) => {
    setCartItems((prev) => {
      const exists = prev.find(
        (i) =>
          i.product?.productId === item.product?.productId &&
          i.itemType === item.itemType &&
          (item.itemType === "PURCHASE" || i.days === item.days)
      );
      if (exists) {
        return prev.map((i) =>
          i === exists
            ? { ...i, quantity: (i.quantity || 1) + (item.quantity || 1) }
            : i
        );
      } else {
        return [...prev, item];
      }
    });
  };

  const removeFromCart = (cartItemId) => {
    setCartItems((prev) =>
      prev.filter((item) => item.product.productId !== cartItemId)
    );
  };

  const clearCart = () => {
    setCartItems([]);
  };

  const cartCount = cartItems.reduce(
    (sum, item) => sum + (item.quantity || 1),
    0
  );

  useEffect(() => {
    fetchCartItems();
  }, []);

  return (
    <CartContext.Provider
      value={{
        cartItems,
        setCartItems,
        addToCart,
        removeFromCart,
        clearCart,
        cartCount,
        fetchCartItems,
      }}
    >
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => useContext(CartContext);
