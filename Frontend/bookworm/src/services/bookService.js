export const getAllBooks = async () => {
  const response = await fetch('http://localhost:8080/api/books/change this to the actual API endpoint');
  if (!response.ok) throw new Error('Failed to fetch books');
  return await response.json();
};
