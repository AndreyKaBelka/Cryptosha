import React, {Component} from "react";

export default class ChatTitle extends Component {
    render() {
        const {title} = this.props;
        return (
            <div>
                <h1>{title}</h1>
            </div>
        )
    }
}