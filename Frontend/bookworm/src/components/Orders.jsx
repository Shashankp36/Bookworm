import React, { useEffect, useState } from "react";
import OrderDetails from "./OrderDetails"; // ✅ Order Details component
import Pagination from "./Pagination";

const OrderHistory = () => {
  const [orders, setOrders] = useState([]);
  const [groupedOrders, setGroupedOrders] = useState({});
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const ordersPerPage = 4;

  useEffect(() => {
    fetch("http://localhost:8080/api/history/user", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        "Content-Type": "application/json",
      },
    })
      .then((res) => res.json())
      .then((data) => {
        setOrders(data);
        const grouped = data.reduce((acc, item) => {
          if (!acc[item.orderId]) acc[item.orderId] = [];
          acc[item.orderId].push(item);
          return acc;
        }, {});
        setGroupedOrders(grouped);
      })
      .catch((err) => console.error("Failed to fetch order history:", err));
  }, []);

  if (orders.length === 0) {
    return (
      <div className="text-center mt-20">
        <h2 className="text-xl font-semibold mb-4">No Orders Found</h2>
        <button
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          onClick={() => (window.location.href = "/")}
        >
          Continue Shopping
        </button>
      </div>
    );
  }

  const formatDate = (dateStr) => {
    const date = new Date(dateStr);
    return `${date.getDate().toString().padStart(2, "0")}/${(
      date.getMonth() + 1
    )
      .toString()
      .padStart(2, "0")}/${date.getFullYear()}`;
  };

  const orderGroups = Object.entries(groupedOrders);
  const totalPages = Math.ceil(orderGroups.length / ordersPerPage);
  const paginatedOrders = orderGroups.slice(
    (currentPage - 1) * ordersPerPage,
    currentPage * ordersPerPage
  );

  return (
    <div className="min-h-[75vh] max-w-7xl mx-auto mt-10 px-4">
      <h1 className="text-3xl font-bold mb-10 text-center text-gray-800">
        Your Orders
      </h1>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {paginatedOrders.map(([orderId, items]) => {
          const totalPrice = items.reduce(
            (acc, item) => acc + item.pricePaid,
            0
          );
          const orderDateRaw = items[0].purchaseDate || items[0].rentalStart;
          const orderDate = new Date(orderDateRaw);
          const formattedDate = `${String(orderDate.getDate()).padStart(
            2,
            "0"
          )}/${String(orderDate.getMonth() + 1).padStart(
            2,
            "0"
          )}/${orderDate.getFullYear()}`;

          return (
            <div
              key={orderId}
              className="bg-white border-2 border-gray-200 rounded-2xl shadow-md hover:shadow-2xl transition-all duration-300 p-6 flex flex-col min-h-[240px] hover:scale-[1.02]"
            >
              <div className="mb-4">
                <p className="text-lg font-bold text-gray-800">
                  Order ID: {orderId}
                </p>
                <p className="text-sm text-gray-600 font-medium mt-1">
                  Purchase Date: {formattedDate}
                </p>
              </div>
              <p className="text-gray-800 font-semibold">
                🛒 Total Items: {items.length}
              </p>
              <p className="text-gray-800 font-semibold">
                💰 Total Price: ₹{totalPrice.toFixed(2)}
              </p>

              <button
                onClick={() => setSelectedOrder({ orderId, items })}
                className="mt-auto bg-indigo-600 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-indigo-700 transition mt-5"
              >
                See Details
              </button>
            </div>
          );
        })}
      </div>

      <Pagination
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={(page) => setCurrentPage(page)}
      />

      {/* Modal for Order Details */}
      {selectedOrder && (
        <OrderDetails
          orderId={selectedOrder.orderId}
          items={selectedOrder.items}
          onClose={() => setSelectedOrder(null)}
        />
      )}
    </div>
  );
};

export default OrderHistory;
