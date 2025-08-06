import HomeCarousal from '../components/HomeCarousal';
import PopularBooks from '../components/PopularBooks';

function HomePage() {
  return (
    <div className="bg-gray-900 min-h-screen text-white">
      <HomeCarousal />
      <PopularBooks/>
    </div>
  );
}

export default HomePage;
