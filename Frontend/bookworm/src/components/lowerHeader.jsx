import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const languages = ['English', 'Hindi', 'Spanish', 'French'];
const genres = ['Action', 'Comedy', 'Drama', 'Horror', 'Sci-Fi'];

const LowerHeader = () => {
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
          <select
            className="form-select rounded-5 px-3 py-1 shadow-sm border-secondary"
            style={{ minWidth: '110px', fontSize: '0.85rem' }}
          >
            {languages.map((lang) => (
              <option key={lang} value={lang}>{lang}</option>
            ))}
          </select>

          {/* Genre Dropdown */}
          <select
            className="form-select rounded-5 px-3 py-1 shadow-sm border-secondary"
            style={{ minWidth: '110px', fontSize: '0.85rem' }}
          >
            {genres.map((genre) => (
              <option key={genre} value={genre}>{genre}</option>
            ))}
          </select>
        </div>

      </div>
    </div>
  );
};

export default LowerHeader;
