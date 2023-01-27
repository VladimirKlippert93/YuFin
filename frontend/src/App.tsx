import React from 'react';
import OfferApp from "./offergallery/OfferApp";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import OfferDetails from "./offergallery/components/offers/OfferDetails";
import useUser from "./offergallery/components/hooks/useUser";
import LoginRegisterPage from "./offergallery/components/userpage/LoginRegisterPage";
import AddOffer from "./offergallery/components/offers/AddOffer";
import ChatPage from "./offergallery/components/chat/ChatPage";
import Header from "./offergallery/components/Header";
import ChatOverview from "./offergallery/components/chat/ChatOverview";


export default function App() {

    const {user, login, logout, register} = useUser();

    return (
        <div className="App">

            <BrowserRouter>
                <Header  user={user} logout={logout}/>
                <Routes>
                    <Route path={"/"} element={<OfferApp user={user}/>}></Route>
                    <Route path={"/addoffer"} element={<AddOffer user={user}/>}></Route>
                    <Route path={"/login"} element={<LoginRegisterPage login={login} register={register}/>}/>
                    <Route path={"/offers/:id"} element={<OfferDetails/>}></Route>
                    <Route path="/chat/:receiverUsername" element={<ChatPage/>}/>
                    <Route path={"/chatoverview"} element={<ChatOverview user={user}/>} />
                </Routes>
            </BrowserRouter>
        </div>
    );
}
