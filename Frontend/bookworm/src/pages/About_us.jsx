import React from 'react';

const Contact = () => {
  return (
    <div className="bg-[#e5e3df] text-black-900 min-h-screen py-12 px-6 pt-28">
      <div className="max-w-6xl mx-auto">
        <h2 className="text-4xl md:text-5xl font-bold mb-4 text-center text-black">
          Let's Connect
        </h2>

        <p className="text-gray-700 text-lg text-center mb-12">
          Got questions? Feedback? We'd love to hear from you! At <span className="font-semibold text-black">BookWorm</span>,
          we're here to make your reading and listening experience better every day.
        </p>

        {/* About Bookworm Section */}
        <div className="bg-white p-8 rounded-lg shadow-lg mb-16">
          <h3 className="text-3xl font-bold text-black mb-4">About Bookworm</h3>
          <p className="text-black mb-4">
            <strong>Bookworm</strong> is a modern digital bookstore platform that brings the joy of reading and listening to your fingertips. 
            Built with the goal of making literature accessible to everyone, Bookworm offers a collection of <strong>eBooks</strong> and 
            <strong> audiobooks</strong> in various genres and languages — all through a user-friendly and intuitive interface.
          </p>

          <h4 className="text-2xl font-bold text-black mt-6 mb-2">What We Offer:</h4>
          <p className="text-black">
            ➤ <strong>Fiction, Non-fiction, Thrillers, Fantasy, Self-help</strong><br />
            ➤ <strong>Multilingual Support: English, Hindi, Marathi</strong><br />
            ➤ <strong>eBooks & Audiobooks for all types of readers</strong><br />
            ➤ <strong>Personal Shelf for Purchases & Rentals</strong><br />
            ➤ <strong>Secure Cart & Checkout System</strong><br />
            ➤ <strong>Modern UI (React + Tailwind)</strong><br />
            ➤ <strong>Spring Boot REST APIs for backend</strong>
          </p>

          <h4 className="text-2xl font-bold text-black mt-6 mb-2">Our Mission:</h4>
          <p className="text-black">
            To create an immersive and inclusive reading experience by combining the latest web technologies with a passion for books. 
            Whether you're a student, a casual reader, or an audiobook lover, <strong className="text-black">Bookworm</strong> is designed to cater to every need.
          </p>
        </div>

        {/* Info Section */}
        <div className="grid md:grid-cols-2 gap-12 mb-16">
          <div>
            <h1 className="text-2xl font-bold text-black mb-4">Genres We Offer</h1> 
            <p className="text-black text-lg leading-relaxed">
              ➤ <strong>Fiction & Non-fiction</strong><br />
              ➤ <strong>Mystery & Thriller</strong><br />
              ➤ <strong>Fantasy & Sci-fi</strong><br />
              ➤ <strong>Biographies</strong><br />
              ➤ <strong>Self-help & Motivation</strong>
            </p>
          </div>

          <div>
            <h1 className="text-2xl font-bold text-black mb-4">Supported Languages</h1> 
            <p className="text-black text-lg leading-relaxed">
              ➤ <strong>English</strong><br />
              ➤ <strong>Hindi</strong><br />
              ➤ <strong>Marathi</strong>
            </p>
          </div>
        </div>

        {/* Our Details */}
        <div className="bg-white p-6 rounded-lg shadow-lg mb-10 text-center">
          <h1 className="text-3xl font-bold text-black mb-4">Our Details</h1>
          <p className="text-gray-700 mb-2 text-lg"><strong>Name:</strong> Bookworm.com </p>
          <p className="text-gray-700 mb-2 text-lg"><strong>Email:</strong> bookworm@gmail.com</p>
          <p className="text-gray-700 mb-2 text-lg"><strong>Phone:</strong> +91-9075275929</p>
          <p className="text-gray-700 text-lg"><strong>Address:</strong> Mumbai, Maharashtra</p>
        </div>
      </div>
    </div>
  );
};

export default Contact;
