import React, { useState, useEffect } from 'react';
import {ChatMessage} from "../models/ChatMessage";
import { v4 as uuidv4 } from 'uuid';
import {useNavigate} from "react-router-dom";

interface ChatPageProps {
    senderUsername: string;
    receiverUsername: string;
}

export default function ChatPage(props: ChatPageProps) {

    const navigate = useNavigate();
    const [messages, setMessages] = useState<ChatMessage[]>([]);
    const [message, setMessage] = useState('');
    const [ws, setWs] = useState<WebSocket | null>(null);

    useEffect(() => {
        navigate('/chat', {state: {senderUsername: props.senderUsername, receiverUsername: props.receiverUsername}});
        const ws = new WebSocket(`ws://localhost:8080/ws/${props.senderUsername}/${props.receiverUsername}`);
        setWs(ws);
        ws.onmessage = (event) => {
            const data = JSON.parse(event.data);
            setMessages((prevMessages) => [...prevMessages, data]);
        };
        return () => {
            ws.close();
        };
    }, [props.senderUsername, props.receiverUsername]);

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        const newMessage: ChatMessage = {
            id: uuidv4(),
            senderUsername: props.senderUsername,
            receiverUsername: props.receiverUsername,
            message,
            timestamp: new Date(),
        };
        setMessages((prevMessages) => [...prevMessages, newMessage]);
        if (ws) {
            ws.send(JSON.stringify(newMessage));
        }
        setMessage('');
    };

    return (
        <div className="chat-page">
            <div className="chat-page-header">Chatting with {props.receiverUsername}</div>
            <div className="chat-page-messages">
                {messages.map((chatMessage) => (
                    <div className="chat-message" key={chatMessage.id}>
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
};