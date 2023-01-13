import React from 'react';
import './App.css';
import OfferApp from "./offergallery/OfferApp";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import OfferDetails from "./offergallery/components/offers/OfferDetails";
import useUser from "./offergallery/components/hooks/useUser";
import LoginRegisterPage from "./offergallery/components/userpage/LoginRegisterPage";
import AddOffer from "./offergallery/components/offers/AddOffer";

export default function App() {

    const {username, login, register} = useUser();

  return (
    <div className="App">

        <BrowserRouter>
        <Routes>
            <Route path={"/offers/:id"} element={<OfferDetails/>}></Route>
            <Route path={"/addoffer"} element={<AddOffer username={username}/>}></Route>
            <Route path={"/"} element={<OfferApp/>}></Route>
            <Route path={"/login"} element={<LoginRegisterPage login={login} register={register}/>}/>
        </Routes>
        </BrowserRouter>
    </div>
  );
}
