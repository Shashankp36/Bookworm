import React from 'react';
import BookCard from './BookCard';

const mockBooks = [
  {
    id: 1,
    title: 'The Midnight Library',
    author: 'Matt Haig',
    image: 'https://source.unsplash.com/300x400/?book,1',
  },
  {
    id: 2,
    title: 'Atomic Habits',
    author: 'James Clear',
    image: 'https://source.unsplash.com/300x400/?book,2',
  },
  {
    id: 3,
    title: 'Where the Crawdads Sing',
    author: 'Delia Owens',
    image: 'https://source.unsplash.com/300x400/?book,3',
  },
  {
    id: 4,
    title: 'Educated',
    author: 'Tara Westover',
    image: 'https://source.unsplash.com/300x400/?book,4',
  },
  {
    id: 5,
    title: 'Becoming',
    author: 'Michelle Obama',
    image: 'https://source.unsplash.com/300x400/?book,5',
  },
  {
    id: 6,
    title: 'Dune',
    author: 'Frank Herbert',
    image: 'https://source.unsplash.com/300x400/?book,6',
  },
];

const BookGrid = () => {
  return (
    <div className="px-6 py-12 bg-gray-900">
      <h2 className="text-2xl font-bold text-white mb-6 text-center">📚 Popular Reads</h2>
      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-6">
        {mockBooks.map((book) => (
          <BookCard key={book.id} book={book} />
        ))}
      </div>
    </div>
  );
};

export default BookGrid;
