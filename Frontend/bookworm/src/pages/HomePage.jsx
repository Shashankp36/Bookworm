import React from 'react';
import Navbar from '../components/Navbar';
import Banner from '../components/Banner';
import HeroSection from '../components/HeroSection';
import BookGrid from '../components/BookGrid';

function HomePage() {
  return (
    <div className="bg-gray-900 min-h-screen text-white">
      <Banner />
      <HeroSection />
      <BookGrid />
    </div>
  );
}

export default HomePage;
