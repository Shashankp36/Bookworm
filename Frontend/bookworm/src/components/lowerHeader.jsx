import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

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

      </div>
    </div>
  );
};

export default LowerHeader;
