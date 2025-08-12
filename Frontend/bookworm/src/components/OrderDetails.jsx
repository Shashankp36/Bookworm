import React from "react";

const OrderDetails = ({ orderId, items, onClose }) => {
  const purchaseDateRaw = items[0].purchaseDate || items[0].rentalStart;
  const purchaseDate = new Date(purchaseDateRaw);
  const formattedDate = `${String(purchaseDate.getDate()).padStart(
    2,
    "0"
  )}/${String(purchaseDate.getMonth() + 1).padStart(
    2,
    "0"
  )}/${purchaseDate.getFullYear()}`;

  const handleDownloadInvoice = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/invoice/download/${orderId}`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
            "Content-Type": "application/json",
          },
        }
      );

      if (!response.ok) {
        throw new Error("Failed to download invoice");
      }

      const blob = await response.blob();
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download = `Invoice_Order_${orderId}.pdf`;
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(url);
    } catch (error) {
      console.error(error);
      alert("Could not download invoice. Please try again.");
    }
  };


  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-start justify-center pt-20 px-4">
      <div className="bg-white max-w-3xl w-full p-8 rounded-xl shadow-2xl relative border border-gray-200">
        <button
          className="absolute top-4 right-4 text-gray-600 hover:text-red-600 text-2xl font-bold"
          onClick={onClose}
        >
          ✖
        </button>

        <h2 className="text-2xl font-bold mb-1 text-gray-800">
          Order ID: {orderId}
        </h2>
        <p className="text-sm text-gray-500 mb-6">
          Initial Purchase Date: {formattedDate}
        </p>

        <div className="space-y-6 max-h-[60vh] overflow-y-auto pr-2 custom-scrollbar">
          {items.map((item, idx) => (
            <div
              key={idx}
              className="flex items-start gap-5 border-b border-gray-200 pb-4"
            >
              <img
                src={item.product?.coverUrl}
                alt={item.product?.title}
                className="w-24 h-32 object-cover rounded-lg shadow"
              />
              <div className="text-gray-800">
                <p className="font-semibold text-lg mb-1">
                  {item.product?.title}
                </p>
                <p className="text-sm">📚 Format: {item.product?.format}</p>
                <p className="text-sm">🔖 Type: {item.productType}</p>
                <p className="text-sm mb-1">
                  💵 Price Paid: ₹{item.pricePaid.toFixed(2)}
                </p>
                {item.productType === "rental" && (
                  <p className="text-sm">
                    🕒 Rented for{" "}
                    {Math.ceil(
                      (new Date(item.rentalEnd) - new Date(item.rentalStart)) /
                        (1000 * 60 * 60 * 24)
                    )}{" "}
                    days
                  </p>
                )}
              </div>
            </div>
          ))}
        </div>

        <div className="mt-6 text-right">
          <button
            onClick={handleDownloadInvoice}
            className="bg-indigo-600 hover:bg-indigo-700 text-white font-semibold px-5 py-2 rounded-lg transition"
          >
            Download Invoice
          </button>
        </div>
      </div>
    </div>
  );
};

export default OrderDetails;
