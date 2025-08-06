// BookCarousel.jsx
import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Pagination, Autoplay } from "swiper/modules";
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import "swiper/css/autoplay";

const books = [
  {
    id: 1,
    name: "David Dell",
    text: "The lorem text the section contain contains header having open and close functionality.",
    image: "https://randomuser.me/api/portraits/men/32.jpg",
  },
  {
    id: 2,
    name: "Rose Bush",
    text: "The lorem text the section contain contains header having open and close functionality.",
    image: "https://randomuser.me/api/portraits/women/44.jpg",
  },
  {
    id: 3,
    name: "Jones Gail",
    text: "The lorem text the section contain contains header having open and close functionality.",
    image: "https://randomuser.me/api/portraits/men/52.jpg",
  },
  {
    id: 4,
    name: "Sarah Stone",
    text: "The lorem text the section contain contains header having open and close functionality.",
    image: "https://randomuser.me/api/portraits/women/68.jpg",
  },
  {
    id: 5,
    name: "Peter Ray",
    text: "The lorem text the section contain contains header having open and close functionality.",
    image: "https://randomuser.me/api/portraits/men/73.jpg",
  },
];

const Banner = () => {
  return (
    <div className="w-full px-2 pt-24 pb-8">
      <Swiper
        modules={[Navigation, Pagination, Autoplay]}
        spaceBetween={0} // ⬅️ Reduced gap between slides
        slidesPerView={1}
        navigation
        pagination={{ clickable: true }}
        autoplay={{ delay: 3000, disableOnInteraction: false }}
        breakpoints={{
          640: { slidesPerView: 1 },
          768: { slidesPerView: 2 },
          1024: { slidesPerView: 3 },
        }}
      >
        {books.map((book) => (
          <SwiperSlide key={book.id}>
            <div className="flex justify-center">
              <div className="bg-white shadow rounded-2xl overflow-hidden w-full max-w-xs">
                <div className="bg-blue-500 flex flex-col items-center pt-6 pb-4">
                  <img
                    src={book.image}
                    alt={book.name}
                    className="w-24 h-24 rounded-full border-4 border-white object-cover"
                  />
                </div>
                <div className="p-4 text-center">
                  <h3 className="text-lg font-semibold">{book.name}</h3>
                  <p className="text-sm text-gray-500 my-2">{book.text}</p>
                  <button className="btn btn-primary mt-2">View More</button>
                </div>
              </div>
            </div>
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
};

export default Banner;
