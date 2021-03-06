import React, {Component} from "react";
import Chat from "../Chat";
import './ChatsList.css';

const tempChats = [
    {
        chatTitle: "Mark Cucerberg",
        chatMessage: "Hi there!"
    },
    {
        chatTitle: "Mark Cucerberg",
        chatMessage: "Hi buddy!"
    },
    {
        chatTitle: "Mark Cucerberg",
        chatMessage: "Hi man!"
    }
]

function renderChats() {
    const chats = []
    const apiChats = getChats();
    let i = 0;
    for (const chat of apiChats) {
        const {chatTitle, chatMessage} = chat;
        chats.push(<Chat
            key={i}
            chatTitle={chatTitle}
            chatMessage={chatMessage}
        />)
        i++;
    }
    return chats;
}

function getChats() {
    return tempChats;
}

export default class ChatsList extends Component {
    render() {
        return (
            <div className="chatsList leftItems">{renderChats()}</div>
        );
    }
}