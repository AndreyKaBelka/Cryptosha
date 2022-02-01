import ChatsList from "../ChatsList";
import MessagesList from "../MessagesList";
import React, {Component} from "react";


function getChatTitle() {
    return "Mark";
}

export default class Home extends Component {
    render() {
        return (
            <>
                <ChatsList/>
                <MessagesList title={getChatTitle()}/>
            </>
        )
    }
}