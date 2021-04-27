import React from "react";
import './Chat.css';

export default function Chat(props) {
    const {chatTitle, chatMessage} = props;
    return (<div className="chatBox">
        <h3 className="chatTitle">{chatTitle}</h3>
        <div className="chatMessage">{chatMessage}</div>
    </div>)
}