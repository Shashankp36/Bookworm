import React, { useState, useEffect } from "react";
import BookCard from "./BookCard";
import Pagination from "./Pagination";

const AllProducts = ({ products = [] }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [languages, setLanguages] = useState([]);
  const [genres, setGenres] = useState([]);
  const [allProducts, setAllProducts] = useState(products);
  const [filteredProducts, setFilteredProducts] = useState(products);
  const [selectedLanguage, setSelectedLanguage] = useState("");
  const [selectedGenre, setSelectedGenre] = useState("");

  const productsPerPage = 10;
  const totalPages = Math.ceil(filteredProducts.length / productsPerPage);
  const indexOfLast = currentPage * productsPerPage;
  const indexOfFirst = indexOfLast - productsPerPage;
  const currentProducts = filteredProducts.slice(indexOfFirst, indexOfLast);

  // Fetch languages and genres
  useEffect(() => {
    const fetchMetaData = async () => {
      try {
        const [langRes, genreRes] = await Promise.all([
          fetch("http://localhost:8080/api/meta/languages"),
          fetch("http://localhost:8080/api/meta/genres"),
        ]);
        const langs = await langRes.json();
        const genres = await genreRes.json();
        setLanguages(langs);
        setGenres(genres);
      } catch (error) {
        console.error("Error fetching metadata:", error);
      }
    };
    fetchMetaData();
  }, []);

  // Initial product load
  useEffect(() => {
    setAllProducts(products);
    setFilteredProducts(products);
  }, [products]);

  // Trigger unified filter API
  const applyFilters = async (lang, genre) => {
    try {
      const url = new URL("http://localhost:8080/api/products/filter");
      if (lang) url.searchParams.append("language", lang);
      if (genre) url.searchParams.append("genre", genre);

      const res = await fetch(url.toString());
      const data = await res.json();
      setFilteredProducts(data);
    } catch (err) {
      console.error("Filtering failed:", err);
    }
  };

  // Handle dropdown changes
  const handleLanguageChange = (e) => {
    const lang = e.target.value;
    setSelectedLanguage(lang);
    setCurrentPage(1);
    applyFilters(lang, selectedGenre);
  };

  const handleGenreChange = (e) => {
    const genre = e.target.value;
    setSelectedGenre(genre);
    setCurrentPage(1);
    applyFilters(selectedLanguage, genre);
  };

  return (
    <div className="px-6 py-12 bg-gray-900 text-white">
      {/* Title and Dropdowns Row */}
      <div className="flex flex-col md:flex-row justify-between items-center mb-6 gap-4">
        <h2 className="text-2xl font-bold text-white">📘 All Products</h2>

        <div className="flex gap-4">
          {/* Language Dropdown */}
          <select
            className="rounded-full text-sm px-3 py-1 bg-white text-black border border-gray-300"
            value={selectedLanguage}
            onChange={handleLanguageChange}
          >
            <option value="">All Languages</option>
            {languages.map((lang) => (
              <option key={lang.languageId} value={lang.languageName}>
                {lang.languageName}
              </option>
            ))}
          </select>

          {/* Genre Dropdown */}
          <select
            className="rounded-full text-sm px-3 py-1 bg-white text-black border border-gray-300"
            value={selectedGenre}
            onChange={handleGenreChange}
          >
            <option value="">All Genres</option>
            {genres.map((genre) => (
              <option key={genre.genreId} value={genre.genreName}>
                {genre.genreName}
              </option>
            ))}
          </select>
        </div>
      </div>

      {/* Product Grid */}
      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-6">
        {currentProducts.map((product) => (
          <BookCard key={product.id} book={product} />
        ))}
      </div>

      {/* Pagination */}
      {totalPages > 1 && (
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={setCurrentPage}
        />
      )}
    </div>
  );
};

export default AllProducts;
