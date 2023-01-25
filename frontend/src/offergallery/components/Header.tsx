import React from 'react';
import Navbar from './Navbar';
import {User} from "./models/User";

type HeaderProps={
    user:User
}

export default function Header(props: HeaderProps){
    return (
        <header>
            <h1>YuFin</h1>
            <p>You need it? You find it!</p>
            <Navbar  user={props.user}/>
        </header>
    );
}

