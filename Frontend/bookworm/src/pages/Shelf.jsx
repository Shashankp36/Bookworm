// import React, { useEffect, useState } from "react";
// import { differenceInDays, parseISO } from "date-fns";

// const Shelf = () => {
//   const [purchasedItems, setPurchasedItems] = useState([]);
//   const [rentedItems, setRentedItems] = useState([]);

//   useEffect(() => {
//     const fetchShelf = async () => {
//       try {
//         const accessToken = localStorage.getItem("accessToken"); // or sessionStorage.getItem()

//         const res = await fetch("http://localhost:8080/api/shelf/myshelf", {
//           method: "GET",
//           headers: {
//             "Content-Type": "application/json",
//             Authorization: `Bearer ${accessToken}`, // ✅ Include access token here
//           },
//         });

//         if (!res.ok) throw new Error("Failed to fetch shelf");

//         const data = await res.json();
//         setPurchasedItems(data.purchasedItems);
//         setRentedItems(data.rentedItems);
//       } catch (err) {
//         console.error("Error fetching shelf data:", err);
//       }
//     };

//     fetchShelf();
//   }, []);

//   const getDaysLeft = (endDate) => {
//     if (!endDate) return null;
//     const today = new Date();
//     const end = parseISO(endDate);
//     const diff = differenceInDays(end, today);
//     return diff >= 0 ? diff : 0;
//   };

//   return (
//     <div className="min-h-screen bg-gray-100 p-6">
//       <div className="max-w-7xl mx-auto bg-white rounded-xl shadow-lg p-6">
//         <h2 className="text-2xl font-semibold mb-6">Digital Shelf</h2>

//         {/* Purchased Items */}
//         <div>
//           <h3 className="text-xl font-semibold mb-3">Purchased Items</h3>
//           <div className="grid grid-cols-2 md:grid-cols-4 gap-5 mb-10">
//             {purchasedItems.map((item) => (
//               <div
//                 key={item.shelfItemId}
//                 className="bg-gray-50 rounded-lg p-4 shadow hover:shadow-md transition"
//               >
//                 <div className="h-36 bg-blue-200 rounded mb-2 flex items-center justify-center text-white font-bold text-sm text-center px-2">
//                   {item.productTitle}
//                 </div>
//                 <p className="text-sm text-gray-600 capitalize">
//                   {item.format}
//                 </p>
//               </div>
//             ))}
//           </div>
//         </div>

//         {/* Rented Items */}
//         <div>
//           <h3 className="text-xl font-semibold mb-3">Rented Items</h3>
//           <div className="grid grid-cols-2 md:grid-cols-4 gap-5">
//             {rentedItems.map((item) => (
//               <div
//                 key={item.shelfItemId}
//                 className="bg-gray-50 rounded-lg p-4 shadow hover:shadow-md transition relative"
//               >
//                 <div className="h-36 bg-green-200 rounded mb-2 flex items-center justify-center text-white font-bold text-sm text-center px-2">
//                   {item.productTitle}
//                 </div>
//                 <p className="text-sm text-gray-600 capitalize">
//                   {item.format}
//                 </p>
//                 <div className="mt-2 flex items-center gap-2 text-xs text-red-500">
//                   <span className="bg-red-100 px-2 py-1 rounded-full font-semibold">
//                     Rented
//                   </span>
//                   <span>{getDaysLeft(item.rentalEndDate)} days left</span>
//                 </div>
//               </div>
//             ))}
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default Shelf;

import React, { useEffect, useState } from "react";
import { differenceInDays, differenceInHours, parseISO } from "date-fns";

const Shelf = () => {
  const [purchasedItems, setPurchasedItems] = useState([]);
  const [rentedItems, setRentedItems] = useState([]);
  const [activeTab, setActiveTab] = useState("purchased");

  useEffect(() => {
    const fetchShelf = async () => {
      try {
        const accessToken = localStorage.getItem("accessToken");
        const res = await fetch("http://localhost:8080/api/shelf/myshelf", {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });
        const data = await res.json();
        setPurchasedItems(data.purchasedItems);
        setRentedItems(data.rentedItems);
      } catch (err) {
        console.error("Error fetching shelf data:", err);
      }
    };

    fetchShelf();
  }, []);

  const getTimeLeft = (endDate) => {
    if (!endDate) return null;
    const now = new Date();
    const end = parseISO(endDate);
    const totalHours = differenceInHours(end, now);
    const days = Math.floor(totalHours / 24);
    const hours = totalHours % 24;
    return totalHours <= 0 ? "Expired" : `${days}d ${hours}h left`;
  };

  const ShelfCard = ({ item, isRental = false }) => {
    const timeLeft = isRental ? getTimeLeft(item.rentalEndDate) : null;

    const cardContent = (
      <div className="bg-gray-50 rounded-lg p-4 shadow hover:shadow-xl hover:-translate-y-1 transition-transform duration-300">
        <div
          className={`h-36 ${isRental ? "bg-green-200" : "bg-blue-200"
            } rounded mb-2 flex items-center justify-center text-white font-bold text-sm text-center px-2`}
        >
          {item.productTitle}
        </div>
        <p className="text-sm text-gray-600 capitalize">{item.format}</p>
        {isRental && (
          <div className="mt-2 text-xs text-red-600 font-semibold">
            {timeLeft}
          </div>
        )}
      </div>
    );

    return item.productUrl ? (
      <a
        href={item.productUrl}
        target="_blank"
        rel="noopener noreferrer"
        className="block"
      >
        {cardContent}
      </a>
    ) : (
      <div>{cardContent}</div>
    );
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-6xl mx-auto bg-white rounded-xl shadow-lg p-6">
        <h2 className="text-2xl font-bold mb-6 text-gray-800">Shelf</h2>

        {/* Toggle Buttons */}
        <div className="flex space-x-4 mb-6">
          <button
            onClick={() => setActiveTab("purchased")}
            className={`px-4 py-2 rounded-full font-semibold transition ${activeTab === "purchased"
                ? "bg-blue-600 text-white"
                : "bg-gray-200 text-gray-700"
              }`}
          >
            Purchased
          </button>
          <button
            onClick={() => setActiveTab("rented")}
            className={`px-4 py-2 rounded-full font-semibold transition ${activeTab === "rented"
                ? "bg-green-600 text-white"
                : "bg-gray-200 text-gray-700"
              }`}
          >
            Rentals
          </button>
        </div>

        {/* Purchased Items Section */}
        {activeTab === "purchased" && (
          <div>
            {purchasedItems.length === 0 ? (
              <p className="text-gray-500">No purchased items found.</p>
            ) : (
              <div className="grid grid-cols-2 md:grid-cols-4 gap-5">
                {purchasedItems.map((item) => (
                  <ShelfCard key={item.shelfItemId} item={item} />
                ))}
              </div>
            )}
          </div>
        )}

        {/* Rented Items Section */}
        {activeTab === "rented" && (
          <div>
            {rentedItems.length === 0 ? (
              <p className="text-gray-500">No rental items found.</p>
            ) : (
              <div className="grid grid-cols-2 md:grid-cols-4 gap-5">
                {rentedItems.map((item) => (
                  <ShelfCard key={item.shelfItemId} item={item} isRental />
                ))}
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default Shelf;

