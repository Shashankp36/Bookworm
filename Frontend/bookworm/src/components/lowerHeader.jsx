import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

const LowerHeader = () => {
  const [query, setQuery] = useState("");
  const [suggestions, setSuggestions] = useState([]);
  const [showDropdown, setShowDropdown] = useState(false);
  const timeoutRef = useRef(null);

  const navigate = useNavigate();

  const fetchSuggestions = async (text) => {
    if (!text.trim()) {
      setSuggestions([]);
      return;
    }
    try {
      const res = await fetch(
        `http://localhost:8080/api/products/search?keyword=${encodeURIComponent(
          text
        )}`
      );
      if (!res.ok) throw new Error("Failed to fetch");

      const data = await res.json();
      setSuggestions(data);
    } catch (err) {
      console.error("Error fetching suggestions:", err);
      setSuggestions([]);
    }
  };

  // Debounce API call
  useEffect(() => {
    if (timeoutRef.current) clearTimeout(timeoutRef.current);
    timeoutRef.current = setTimeout(() => {
      if (query.length > 0) {
        fetchSuggestions(query);
        setShowDropdown(true);
      } else {
        setShowDropdown(false);
      }
    }, 300);
    return () => clearTimeout(timeoutRef.current);
  }, [query]);

  // When user clicks a suggestion
  const handleSelect = (productId) => {
    setShowDropdown(false);
    setQuery("");
    navigate(`/product/${productId}`);
  };

  return (
    <div className="w-100 bg-light border-bottom shadow-sm py-2 px-3">
      <div className="container-fluid d-flex flex-wrap justify-content-between align-items-center gap-2">
        <div className="flex-grow-1 d-flex justify-content-center position-relative">
          <div className="input-group w-75" style={{ maxHeight: "42px" }}>
            <input
              type="text"
              className="form-control border-end-0 rounded-start-5 py-1 px-3"
              placeholder="Search books or authors..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              onFocus={() => query && setShowDropdown(true)}
              onKeyDown={(e) => {
                if (e.key === "Enter" && suggestions.length > 0) {
                  handleSelect(suggestions[0].productId);
                }
              }}
            />
            <button
              className="btn btn-dark rounded-end-5 px-4"
              onClick={() => {
                if (suggestions.length > 0) {
                  handleSelect(suggestions[0].productId);
                }
              }}
            >
              Search
            </button>
          </div>

          {showDropdown && (
            <ul
              className="list-group position-absolute mt-1 shadow"
              style={{
                top: "42px",
                width: "75%",
                zIndex: 1000,
                maxHeight: "200px",
                overflowY: "auto",
              }}
            >
              {suggestions.length > 0 ? (
                suggestions.map((item, index) => (
                  <li
                    key={index}
                    className="list-group-item list-group-item-action"
                    onClick={() => handleSelect(item.productId)}
                    style={{ cursor: "pointer" }}
                  >
                    <strong>{item.title}</strong>{" "}
                    {item.author?.authorName && (
                      <span
                        className="text-muted"
                        style={{ fontSize: "0.85rem" }}
                      >
                        by {item.author.authorName}
                      </span>
                    )}
                  </li>
                ))
              ) : (
                <li className="list-group-item text-muted">
                  No results found
                </li>
              )}
            </ul>
          )}
        </div>
      </div>
    </div>
  );
};

export default LowerHeader;
