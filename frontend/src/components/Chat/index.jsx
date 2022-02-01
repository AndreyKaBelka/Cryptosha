import React, {Component} from "react";
import './Chat.css';

export default class Chat extends Component {
    render() {
        const {chatTitle, chatMessage} = this.props;
        return (<div className="chatBox">
            <h3 className="chatTitle">{chatTitle}</h3>
            <div className="chatMessage">{chatMessage}</div>
        </div>)
    }

}