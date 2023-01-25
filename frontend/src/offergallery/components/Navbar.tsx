import React from 'react';
import { Link } from 'react-router-dom';
import {User} from "./models/User";

type NavbarProps = {
    user: User
}

export default function Navbar(props: NavbarProps) {

    return (
        <nav>
            <ul>
                <li>
                    <Link to="/">Home</Link>
                </li>
                {props.user.username !== "" && <li>
                    <Link to="/addoffer">Add Offer</Link>
                </li>}
                {props.user.username !== "" && <li>
                    <Link to="/chatoverview">ChatOverview</Link>
                </li>}
                <li>
                    <Link to="/login">Login/Register</Link>
                </li>
            </ul>
        </nav>
    );
}


