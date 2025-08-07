import { useEffect, useState } from "react";
import HomeCarousal from "../components/HomeCarousal";
import PopularBooks from "../components/PopularBooks";

function HomePage() {
  const [allBooks, setAllBooks] = useState([]);

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/products/");
        if (!res.ok) throw new Error("Failed to fetch books");

        const data = await res.json();

        const formattedBooks = data.map((product) => ({
          id: product.productId,
          title: product.title,
          author: product.author?.authorName,
          image: product.coverUrl,
        }));

        setAllBooks(formattedBooks);
      } catch (error) {
        console.error("Error fetching products:", error);
      }
    };

    fetchBooks();
  }, []);

  return (
    <div className="bg-gray-900 min-h-screen text-white">
      <HomeCarousal />
      <PopularBooks books={allBooks.slice(0, 6)} />
    </div>
  );
}

export default HomePage;
