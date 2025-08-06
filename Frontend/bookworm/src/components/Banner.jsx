import React from 'react';

const demoBooks = [
  {
    title: 'The Great Gatsby',
    image: 'https://source.unsplash.com/200x300/?book,gatsby',
  },
  {
    title: '1984',
    image: 'https://source.unsplash.com/200x300/?book,1984',
  },
  {
    title: 'To Kill a Mockingbird',
    image: 'https://source.unsplash.com/200x300/?book,mockingbird',
  },
  {
    title: 'Pride and Prejudice',
    image: 'https://source.unsplash.com/200x300/?book,pride',
  },
  {
    title: 'Harry Potter',
    image: 'https://source.unsplash.com/200x300/?book,harrypotter',
  },
  {
    title: 'The Hobbit',
    image: 'https://source.unsplash.com/200x300/?book,hobbit',
  },
];

const Banner = () => {
  return (
    <>
      {/* Banner Section */}
      <div
        className="w-full h-64 bg-cover bg-center relative"
        style={{ backgroundImage: 'url(https://source.unsplash.com/1600x400/?books)' }}
      >
        <div className="absolute inset-0 bg-black bg-opacity-60 flex items-center justify-center">
          <h2 className="text-3xl md:text-4xl font-bold text-white text-center px-4">
            Discover Your Next Favorite Book
          </h2>
        </div>
      </div>

      {/* Scrollable Books Section */}
      <div className="mt-6 px-4">
        <h3 className="text-2xl font-semibold mb-4 text-center">Famous Books</h3>
        <div className="flex overflow-x-auto space-x-4 scrollbar-hide scroll-smooth">
          {demoBooks.map((book, index) => (
            <div
              key={index}
              className="min-w-[200px] transform hover:scale-105 transition duration-300 ease-in-out cursor-pointer"
            >
              <img
                src={book.image}
                alt={book.title}
                className="w-full h-72 object-cover rounded-lg shadow-md"
              />
              <p className="mt-2 text-center font-medium">{book.title}</p>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default Banner;
