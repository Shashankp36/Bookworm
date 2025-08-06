import Banner from '../components/Banner';
import PopularBooks from '../components/PopularBooks';

function HomePage() {
  return (
    <div className="bg-gray-900 min-h-screen text-white">
      <Banner />
      <PopularBooks/>
    </div>
  );
}

export default HomePage;
