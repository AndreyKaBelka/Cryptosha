import React from "react";
import ChatsList from "../ChatsList";
import MessagesList from "../MessagesList";
import './App.css';

function getChatTitle() {
    return "Mark";
}

export default function App(props) {
    return (<div className="messenger">
        <div className="leftItems">
            <ChatsList/>
        </div>
        {props.showMessages &&
        <div className="rightItems">
            <MessagesList title={getChatTitle()}/>
        </div>
        }
    </div>)
}