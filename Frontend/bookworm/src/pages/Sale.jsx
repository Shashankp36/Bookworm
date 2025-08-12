import React, { useEffect, useState } from 'react';
import Slider from 'react-slick';
import BookCard from '../components/BookCard';

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

// ✅ Corrected banner image imports (make sure these files exist at this path)
import banner1 from "../assets/Banner/Banner/Slider1.jpg";
import banner2 from "../assets/Banner/Banner/Slider2.jpg";
import banner3 from "../assets/Banner/Banner/Slider3.jpg";
import banner4 from "../assets/Banner/Banner/Slider4.jpg";


const Sale = () => {
    const [books, setBooks] = useState([]);

    useEffect(() => {
        const fetchBooks = async () => {
            try {
                const res = await fetch('http://localhost:8080/api/products/');
                if (!res.ok) throw new Error("Failed to fetch books");
                const data = await res.json();
                const discounted = data.filter(book => book.discountedPrice && book.discountedPrice < book.price);
                setBooks(discounted);
            } catch (error) {
                console.error("Error fetching discounted books:", error);
            }
        };

        fetchBooks();
    }, []);

    const settings = {
        dots: true,
        infinite: true,
        autoplay: true,
        autoplaySpeed: 2000,
        arrows: true,
        slidesToShow: 1,
        slidesToScroll: 1
    };

    return (
        <div className="px-4 py-10 bg-gray-900 min-h-screen">
            {/* ✅ Banner Slider with overlay text */}
            <div className="mb-10 rounded-md overflow-hidden">
                <Slider {...settings}>
                    {/* Slide 1 */}
                    <div className="relative w-full h-[350px] rounded-md overflow-hidden">
                        <img src={banner1} alt="Banner 1" className="w-full h-full object-cover filter brightness-75 blur-sm" />
                        <div className="absolute inset-0 flex flex-col items-center justify-center text-white px-4">
                            <h2 className="text-4xl font-extrabold">Sale is Live</h2>
                            <p className="text-lg font-semibold mt-2">Get up to 25% OFF on Bestsellers</p>
                        </div>
                    </div>

                    {/* Slide 2 */}
                    <div className="relative w-full h-[350px] rounded-md overflow-hidden">
                        <img src={banner2} alt="Banner 2" className="w-full h-full object-cover filter brightness-75 blur-sm" />
                        <div className="absolute inset-0 flex flex-col items-center justify-center text-white px-4">
                            <h2 className="text-4xl font-extrabold">Limited Time Deals on Your Favorite Reads!</h2>
                            <p className="text-lg font-semibold mt-2">Grab your favorite books now!</p>
                        </div>
                    </div>

                    {/* Slide 3 */}
                    <div className="relative w-full h-[350px] rounded-md overflow-hidden">
                        <img src={banner3} alt="Banner 3" className="w-full h-full object-cover filter brightness-75 blur-sm" />
                        <div className="absolute inset-0 flex flex-col items-center justify-center text-white px-4">
                            <h2 className="text-4xl font-extrabold">Festival Reads</h2>
                            <p className="text-lg font-semibold mt-2">Exclusive festive discounts — hurry, while stocks last!</p>
                        </div>
                    </div>

                    {/* Slide 4 */}
                    <div className="relative w-full h-[350px] rounded-md overflow-hidden">
                        <img src={banner4} alt="Banner 4" className="w-full h-full object-cover filter brightness-75 blur-sm" />
                        <div className="absolute inset-0 flex flex-col items-center justify-center text-white px-4">
                            <h2 className="text-4xl font-extrabold">End of Season Sale</h2>
                            <p className="text-lg font-semibold mt-2">Up to 40% off across categories</p>
                        </div>
                    </div>
                </Slider>
            </div>

            {/* ✅ Discounted Books Section */}
            <h2 className="text-2xl font-bold text-white mb-6 text-center">Discounted Books</h2>
            <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-6">
                {books.map((book) => (
                    <BookCard key={book.productId} book={book} />
                ))}
            </div>
        </div>
    );
};

export default Sale;
