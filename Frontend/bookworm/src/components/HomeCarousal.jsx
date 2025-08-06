import Banner from "./Banner";


const HomeCarousal = () => {
  return (
    <div className="w-full">
      {/* Hero Section */}
      <div
        className="w-full h-[500px] bg-cover bg-center relative"
        style={{ backgroundImage: 'url(https://source.unsplash.com/1600x600/?books)' }}
      >
        <div className="absolute inset-0 bg-black bg-opacity-70 flex flex-col items-center justify-center px-6 text-center">
          <h2 className="text-4xl md:text-5xl font-bold text-white mb-4">
            Discover Your Next Favorite Book
          </h2>
          <p className="max-w-2xl text-gray-300 text-lg">
            Explore a world of eBooks and Audiobooks.<br />
            <span className="text-gray-400 text-base">
              "Discover handpicked books to spark your imagination, whether you're traveling, relaxing, or studying, there's a story waiting."
            </span>
          </p>
        </div>
      </div>

      {/* 👇 Banner Carousel shown just below the hero */}
      <Banner />
    </div>
  );
};

export default HomeCarousal;
