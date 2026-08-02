import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";

import Home from "./pages/Home";
import Contacts from "./pages/Contacts";
import Login from "./pages/Login";


function App() {
  return (
    <BrowserRouter>

      <Navbar />

      <Routes>

        <Route path="/" element={<Home />} />

        <Route path="/contacts" element={<Contacts />} />

        <Route path="/login" element={<Login />} />
      
      </Routes>

    </BrowserRouter>
  );
}

export default App;