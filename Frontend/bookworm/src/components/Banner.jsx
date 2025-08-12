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
    name: "Dr. Seuss",
    text: "The more that you read, the more things you will know. The more that you learn, the more places you'll go.",
    image:
      "https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/Theodor_Seuss_Geisel_%2801037v%29.jpg/1200px-Theodor_Seuss_Geisel_%2801037v%29.jpg",
  },
  {
    id: 2,
    name: "Harper Lee",
    text: "You never really understand a person until you consider things from his point of view… Until you climb inside of his skin and walk around in it.",
    image:
      "https://people.com/thmb/vRQID6JP1gHYmbbMOXxXTYY3UCM=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc():focal(216x0:218x2)/harper-lee-435-3-423906ad4cb246099cd05548f99e70b6.jpg",
  },
  {
    id: 3,
    name: "Ernest Hemingway",
    text: "The world breaks everyone, and afterward, many are strong at the broken places. But those that will not break it kills. It kills the very good and the very gentle and the very brave impartially.",
    image:
      "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSo40k4n_gJjYcEu3Xv8ERza6xwjjkvx_9bIQ&s",
  },
  {
    id: 4,
    name: "Rabindranath Tagore",
    text: "Where the mind is without fear and the head is held high… into that heaven of freedom, my Father, let my country awake.",
    image:
      "https://padhegaindia.in/wp-content/uploads/2024/09/Rabindranath-Tagore-1.png",
  },
  {
    id: 5,
    name: "Arundhati Roy",
    text: "Another world is not only possible, she is on her way. On a quiet day, I can hear her breathing.",
    image:
      "https://i.guim.co.uk/img/media/76a4da327ffb401328627cf93ef16cd366723de5/298_1399_3505_4381/master/3505.jpg?width=465&dpr=1&s=none&crop=none",
  },
];
const Banner = () => {
  return (
    <div className="w-full px-2 py-8 bg-transparent">
      <Swiper
        modules={[Navigation, Pagination, Autoplay]}
        spaceBetween={0}
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
              <div
                className="bg-white shadow rounded-2xl overflow-hidden w-full max-w-xs flex flex-col"
                style={{
                  height: "350px", // Fixed height
                }}
              >
                <div className="bg-gray-600 flex flex-col items-center pt-6 pb-4">
                  <img
                    src={book.image}
                    alt={book.name}
                    className="w-24 h-24 rounded-full border-4 border-white object-cover"
                  />
                </div>
                <div className="p-4 text-center flex flex-col flex-grow">
                  <h3 className="text-lg font-bold text-gray-700">{book.name}</h3> {/* Bold & Grey */}
                  <p className="text-sm text-gray-500 my-2 flex-grow">
                    {book.text}
                  </p>
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
