import Banner from "./Banner";
import bgimg from "../assets/carousalBanner.jpg";

const HomeCarousal = () => {
  return (
    <div className="w-full">
      {/* Hero Section */}
      <div
        className="w-full h-[500px] bg-cover bg-center relative"
        style={{
          backgroundImage: `url(${bgimg})`,
          backgroundSize: 'contain',
          backgroundPosition: 'center',
        }}
      >
        {/* Overlay with blur and dark tint */}
        <div className="absolute inset-0 bg-black/60 backdrop-blur-sm flex flex-col items-center justify-center px-6 text-center">
          <h2 className="text-4xl md:text-5xl font-extrabold text-white mb-4 drop-shadow-lg">
            Discover Your Next Favorite Book
          </h2>
          <p className="max-w-2xl text-gray-200 text-lg md:text-xl leading-relaxed drop-shadow-md">
            Explore a world of eBooks and Audiobooks.
            <br />
            <span className="text-gray-300 text-base">
              "Discover handpicked books to spark your imagination. Whether you're traveling, relaxing, or studying — there's a story waiting."
            </span>
          </p>
        </div>
      </div>

      {/* Banner Carousel */}
      <Banner />
    </div>
  );
};

export default HomeCarousal;
