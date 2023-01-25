import React from 'react';
import Navbar from './Navbar';
import {User} from "./models/User";
import "../../styles/Header.css";

type HeaderProps={
    user:User
    logout: ()=>void
}

export default function Header(props: HeaderProps){
    return (
        <header className="header">
            <h1 className="header_title">YuFin</h1>
            <p className="header_subtitle">You need it? You find it!</p>
            <Navbar  user={props.user} logout={props.logout}/>
        </header>
    );
}

