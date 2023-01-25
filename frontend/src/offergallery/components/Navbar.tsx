import React from 'react';
import { Link } from 'react-router-dom';
import {User} from "./models/User";
import "../../styles/Navbar.css"

type NavbarProps = {
    user: User
    logout: ()=>void
}

export default function Navbar(props: NavbarProps) {

    return (
        <nav className="navbar">
            <ul className="navbar_list">
                <li className="navbar_item">
                    <Link to="/" className="navbar_link">Home</Link>
                </li>
                {props.user.username !== "" && <li className="navbar_item">
                    <Link to="/addoffer" className="navbar_link">AddOffer</Link>
                </li>}
                {props.user.username !== "" && <li className="navbar_item">
                    <Link to="/chatoverview" className="navbar_link">Chats</Link>
                </li>
                }{props.user.username === "" && <li className="navbar_item">
                <Link to="/login" className="navbar_link">Login/Register</Link>
            </li>}
                {props.user.username !== "" && <li className="navbar_item">
                    <Link to="#" className="navbar_link" onClick={() => props.logout()}>{props.user.username} Logout</Link>
                </li>}

            </ul>
        </nav>
    );
}


