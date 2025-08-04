import React from 'react';

const HeroSection = () => {
  return (
    <section className="py-12 px-6 bg-gray-900 text-white text-center">
      <h2 className="text-2xl md:text-3xl font-bold mb-4">Explore a world of eBooks and Audiobooks</h2>
      <p className="max-w-2xl mx-auto text-gray-400 mb-6">
        Handpicked collections to spark your imagination. Whether you're commuting, relaxing, or studying — there's a story waiting.
      </p>
      <div className="space-x-4">
        <button className="bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 px-6 rounded-full">
          Browse Books
        </button>
        <button className="bg-gray-700 hover:bg-gray-600 text-white font-semibold py-2 px-6 rounded-full">
          Listen to Samples
        </button>
      </div>
    </section>
  );
};

export default HeroSection;
