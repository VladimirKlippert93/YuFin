import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import {User} from "../models/User";
import "../../../styles/components/chat/ChatOverview.css"

type ChatOverviewProps = {
    user:User
}
export default function ChatOverview(props: ChatOverviewProps) {
    const [users, setUsers] = useState<string[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get<User[]>('/api/users/all')
            .then((res) => {
                setUsers(res.data.map((user) => user.username))
            })
            .catch((error) => console.log(error));
    }, []);

    return (
        <div className="users-list">
            {users.map((username) => (
                props.user.username !== username &&
                 <div className="user" key={username} onClick={() => navigate(`/chat/${username}`)}>
                         {username}
                </div>
            ))}
        </div>
    );
}