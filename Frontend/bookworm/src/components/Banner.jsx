import React from 'react';

const Banner = () => {
  return (
    <div className="w-full h-64 bg-cover bg-center relative" style={{ backgroundImage: 'url(https://source.unsplash.com/1600x400/?books)' }}>
      <div className="absolute inset-0 bg-black bg-opacity-60 flex items-center justify-center">
        <h2 className="text-3xl md:text-4xl font-bold text-white text-center px-4">
          Discover Your Next Favorite Book
        </h2>
      </div>
    </div>
  );
};

export default Banner;
