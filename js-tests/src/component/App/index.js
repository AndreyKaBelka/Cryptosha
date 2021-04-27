import React from "react";
import ChatsList from "../ChatsList";
import MessagesList from "../MessagesList";
import './App.css';

function getChatTitle(){
    return "Mark";
}

export default function App(props) {
    return (<div className="messenger">
        <div className="left-items">
            <ChatsList/>
        </div>
        {props.showMessages &&
        <div className="right-items">
            <MessagesList title={getChatTitle()}/>
        </div>
        }
    </div>)
}