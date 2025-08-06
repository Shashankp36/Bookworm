import React from 'react';

const languages = ['English', 'Hindi', 'Spanish', 'French'];
const genres = ['Action', 'Comedy', 'Drama', 'Horror', 'Sci-Fi'];

const LowerHeader = () => {
  return (
    <div className="w-full bg-white shadow px-6 py-4 flex justify-center">
      {/* Wrapper */}
      <div className="flex items-center justify-between w-full max-w-7xl gap-4">
        
          {/* Centered Search Bar */}
     <div className="flex items-center bg-gray-300 rounded-full shadow-inner px-4 py-2 gap-2 w-full max-w-4xl">
          <input
            type="text"
            placeholder="Search anything..."
            className="bg-transparent flex-grow outline-none rounded-full px-4 py-2 text-gray-900 placeholder:text-gray-900 text-base"
          />
          <button className="bg-blue-500 text-white rounded-full px-6 py-2 hover:bg-[#8a6c3a] transition-colors duration-300 font-semibold shadow text-sm">
            Search
          </button>
        </div>

        {/* Left Dropdowns */}
        <div className="flex items-end gap-3">
          {/* Language Dropdown */}
          <select className="rounded-full px-5 py-3 bg-gray-200 text-gray-700 hover:bg-gray-300 focus:ring-2 focus:ring-blue-400 font-semibold text-sm shadow cursor-pointer transition-all duration-200">
            {languages.map((lang) => (
              <option key={lang} value={lang}>{lang}</option>
            ))}
          </select>

          {/* Genre Dropdown */}
          <select className="rounded-full px-5 py-3 bg-gray-200 text-gray-700 hover:bg-gray-300 focus:ring-2 focus:ring-blue-400 font-semibold text-sm shadow cursor-pointer transition-all duration-200">
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
