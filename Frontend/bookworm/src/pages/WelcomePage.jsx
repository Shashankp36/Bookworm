import React from "react";
import { useNavigate } from "react-router-dom";
import imgbg from "../assets/bgimg.jpeg";

const BookwormLanding = () => {
  const navigate = useNavigate();

  return (
    <div className="relative min-h-screen bg-black overflow-hidden text-white">
      {/* Background Image */}
      <div
        className="absolute inset-0 z-0"
        style={{
          backgroundImage: `url(${imgbg})`,
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat",
          opacity: 0.2,
        }}
      />

      {/* Center Content */}
      <div className="relative z-10 flex items-center justify-center min-h-screen px-4">
        <div className="text-center max-w-2xl">
          <div className="text-6xl mb-6 animate-bounce">📚</div>

          <h1 className="text-5xl font-bold mb-4">Bookworm</h1>
          <p className="text-lg text-gray-300 mb-8">
            Your gateway to the world of literature
          </p>

          <h2 className="text-2xl font-semibold mb-3">
            Buy Books | Explore Authors | Discover Genres
          </h2>
          <p className="text-gray-400 mb-10 leading-relaxed">
            Dive into a curated collection of ebooks, audiobooks, and author
            profiles. Find your next favorite read and stay in touch with
            literature like never before.
          </p>

          {/* Action Buttons */}
          <div className="flex justify-center gap-6">
            <button
              onClick={() => navigate("/login")}
              className="px-6 py-2 bg-gray-700 text-white rounded-full hover:bg-white hover:text-black border border-white transition duration-300"
            >
              Login
            </button>
            <button
              onClick={() =>
                navigate("/login", { state: { formType: "register" } })
              }
              className="px-6 py-2 bg-gray-700 text-white rounded-full hover:bg-white hover:text-black border border-white transition duration-300"
            >
              Signup
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookwormLanding;
