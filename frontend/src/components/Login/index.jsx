import React, {Component} from "react";

export default class Login extends Component {
    constructor(props) {
        super(props);
        this.onLoginBtnClick = this.onLoginBtnClick.bind(this);
    }

    onLoginBtnClick(event) {
        const username = $("#username").val();
        console.log(username);
    }

    render() {
        return (
            <div>
                <input type='text' id='username'/>
                <button onClick={this.onLoginBtnClick} id='Login'>Login</button>
            </div>
        )
    }
}