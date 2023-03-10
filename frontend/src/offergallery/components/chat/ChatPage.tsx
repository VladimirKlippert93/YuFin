import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import {ChatMessage} from "../models/ChatMessage";
import useUser from "../hooks/useUser";
import axios from 'axios';
import "../../../styles/components/chat/ChatPage.css"


export default function ChatPage() {

    const [messages, setMessages] = useState<ChatMessage[]>([]);
    const [message, setMessage] = useState('');
    const [ws, setWs] = useState<WebSocket | null>(null);
    const { receiverUsername } = useParams<{receiverUsername: string }>();
    const {user} = useUser();
    const {username: senderUsername} = user;


    useEffect(() => {
        axios.get<ChatMessage[]>(`/api/chat/previous-messages?senderUsername=${senderUsername}&receiverUsername=${receiverUsername}`)
            .then((res) => {
                setMessages(res.data);
            })
            .catch((error) => console.log(error));
        const ws = new WebSocket(`ws://localhost:8080/api/ws/chat`);
        setWs(ws);

        ws.onmessage = (event) => {
            const data = JSON.parse(event.data);
            if (data.senderUsername ===receiverUsername && data.receiverUsername === senderUsername && data.senderUsername !== data.receiverUsername) {
                setMessages((prevMessages) => [...prevMessages, data]);
            }
        };
        ws.onclose = () => {
            setMessages([]);
        };

        return () => {
            if (ws) {
                ws.close();
            }
        };
    }, [senderUsername, receiverUsername]);

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        if (receiverUsername !== undefined && senderUsername !== undefined && ws && ws.readyState === WebSocket.OPEN) {

            const newChatMessage: ChatMessage = {
                senderUsername,
                receiverUsername,
                message,
                id: Date.now().toString(),
                timestamp: new Date()
            };
                ws.send(JSON.stringify(newChatMessage));
                setMessages((prevMessages)=>[...prevMessages, newChatMessage]);
            setMessage('');
        }
    };

    return (
        <div className="chat-page">
            <div className="chat-page-header">Chatting with {receiverUsername}</div>
            <div className="chat-page-messages">
                {messages.map((chatMessage) => (
                    <div className={`chat-message ${chatMessage.senderUsername === receiverUsername ? "chat-message-receiver" : "chat-message-sender"}`} key={chatMessage.id}>
                        <div className="chat-message-username">{chatMessage.senderUsername}</div>
                        <div className="chat-message-text">{chatMessage.message}</div>
                        <div className="chat-message-timestamp">{chatMessage.timestamp.toString()}</div>
                    </div>
                ))}
            </div>
            <form className="chat-page-form" onSubmit={handleSubmit}>
                <input
                    type="text"
                    value={message}
                    onChange={(event) => setMessage(event.target.value)}
                />
                <button type="submit">Send</button>
            </form>
        </div>
    );
}