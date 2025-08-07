import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const LowerHeader = () => {
  const [languages, setLanguages] = useState([]);
  const [genres, setGenres] = useState([]);

  // Fetch languages and genres on mount
  useEffect(() => {
    const fetchMetaData = async () => {
      try {
        const [langRes, genreRes] = await Promise.all([
          fetch('http://localhost:8080/api/meta/languages'),
          fetch('http://localhost:8080/api/meta/genres'),
        ]);

        const langs = await langRes.json();
        const genres = await genreRes.json();

        setLanguages(langs);
        setGenres(genres);
      } catch (error) {
        console.error('Error fetching metadata:', error);
      }
    };

    fetchMetaData();
  }, []);

  return (
    <div className="w-100 bg-light border-bottom shadow-sm py-2 px-3">
      <div className="container-fluid d-flex flex-wrap justify-content-between align-items-center gap-2">

        {/* Search Bar */}
        <div className="flex-grow-1 d-flex justify-content-center">
          <div className="input-group w-75" style={{ maxHeight: '42px' }}>
            <input
              type="text"
              className="form-control border-end-0 rounded-start-5 py-1 px-3"
              placeholder="Search books, authors, genres..."
              style={{ fontSize: '0.9rem' }}
            />
            <button
              className="btn btn-dark rounded-end-5 px-4"
              style={{ fontSize: '0.85rem', fontWeight: '500' }}
            >
              Search
            </button>
          </div>
        </div>

        {/* Dropdowns */}
        <div className="d-flex gap-2">

          {/* Language Dropdown */}
          <div className="position-relative">
            <select
              className="form-select rounded-pill px-3 py-1 shadow-sm border-secondary"
              style={{
                minWidth: '130px',
                fontSize: '0.85rem',
                backgroundColor: '#fff',
                cursor: 'pointer',
              }}
              defaultValue=""
            >
              <option disabled value="">Languages</option>
              {languages.map((lang) => (
                <option key={lang.languageId} value={lang.languageName}>
                  {lang.languageName}
                </option>
              ))}
            </select>
          </div>

          {/* Genre Dropdown */}
          <div className="position-relative">
            <select
              className="form-select rounded-pill px-3 py-1 shadow-sm border-secondary"
              style={{
                minWidth: '130px',
                fontSize: '0.85rem',
                backgroundColor: '#fff',
                cursor: 'pointer',
              }}
              defaultValue=""
            >
              <option disabled value="">Genres</option>
              {genres.map((genre) => (
                <option key={genre.genreId} value={genre.genreName}>
                  {genre.genreName}
                </option>
              ))}
            </select>
          </div>

        </div>
      </div>
    </div>
  );
};

export default LowerHeader;
