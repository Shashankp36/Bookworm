import Banner from "./Banner";
import bgimg from "../assets/carousalBanner.jpg";

const HomeCarousal = () => {
  return (
    <div className="w-full">
      {/* Hero + Banner Section sharing same background */}
      <div className="w-full relative overflow-hidden">
        
        {/* Blurred Background Image */}
        <div
          className="absolute inset-0 bg-cover bg-center"
          style={{
            backgroundImage: `url(${bgimg})`,
            backgroundSize: "cover",
            backgroundPosition: "center",
            filter: "blur(6px)", // adjust blur intensity
            transform: "scale(1.05)", // prevents edges from showing after blur
          }}
        ></div>

        {/* Dark Overlay on top of blurred image */}
        <div className="absolute inset-0 bg-black/60"></div>

        {/* Hero Section */}
        <div className="relative z-10 h-[500px] flex flex-col items-center justify-center px-6 text-center">
          <h2 className="text-4xl md:text-5xl font-extrabold text-white mb-4 drop-shadow-lg">
            Discover Your Next Favorite Book
          </h2>
          <p className="max-w-2xl text-gray-200 text-lg md:text-xl leading-relaxed drop-shadow-md">
            Explore a world of eBooks and Audiobooks.
            <br />
            <span className="text-gray-300 text-base">
              "Discover handpicked books to spark your imagination. Whether
              you're traveling, relaxing, or studying — there's a story
              waiting."
            </span>
          </p>
        </div>

        {/* Banner below hero text */}
        <div className="relative z-10">
          <Banner />
        </div>
      </div>
    </div>
  );
};

export default HomeCarousal;
