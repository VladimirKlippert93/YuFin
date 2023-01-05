import React from 'react';
import './App.css';
import OfferApp from "./offergallery/OfferApp";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import OfferDetails from "./offergallery/components/offers/OfferDetails";

export default function App() {

  return (
    <div className="App">

        <BrowserRouter>
        <Routes>
            <Route path={"/offers/:id"} element={<OfferDetails/>}></Route>
            <Route path={"/"} element={<OfferApp/>}></Route>
        </Routes>
        </BrowserRouter>
    </div>
  );
}
