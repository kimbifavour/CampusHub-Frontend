import './App.css'
import { Routes, Route } from "react-router-dom";
import MarketPlace from './components/pages/MarketPlace';
import CourseExchange from './components/pages/CourseExchange';
import Housing from './components/pages/Housing';
import Tutoring from './components/pages/Tutoring';
import NavBar from './components/NavBar';

function App() {
  return (
    <>
      <NavBar/>

      <Routes>
        <Route path="/marketplace" element={<MarketPlace />} />
        <Route path="/tutoring" element={<Tutoring />} />
        <Route path="/housing" element={<Housing />} />
        <Route path="/courseexchange" element={<CourseExchange />} />
      </Routes>
    </>
  )
}

export default App
