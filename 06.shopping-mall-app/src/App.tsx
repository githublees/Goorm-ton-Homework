import { Outlet, Route, Routes } from "react-router-dom";
import "./App.css";
import MainPage from "./pages/MainPage";
import DetailPage from "./pages/DetailPage";
import LoginPage from "./pages/LoginPage";
import CartPage from "./pages/CartPage";
import Header from "./components/Header";
import RegisterPage from "./pages/RegisterPage";

const Layout = () => {
  return (
    <div>
      <Header />

      <Outlet />
    </div>
  );
};

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<MainPage />} />
          <Route path="detail/:productId" element={<DetailPage />} />
          {/* detail/:productId */}
          <Route path="login" element={<LoginPage />} />
          <Route path="register" element={<RegisterPage />} />
          <Route path="cart" element={<CartPage />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
