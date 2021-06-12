import React, {Component} from "react";
import {withRouter} from "react-router-dom";
import Cookies from "universal-cookie/lib";
const cookies = new Cookies();

class Registration extends Component {
    constructor(props) {
        super(props);
        this.onRegisterBtnClick = this.onRegisterBtnClick.bind(this);
    }

    onRegisterBtnClick(event) {
        const username = $("#username").val();
        console.log(username);
        cookies.set('userId', 12);
        this.props.history.push('/registration/success');
    }

    render() {
        return (
            <div>
                <div>
                    <input type='text' id='username'/>
                    <button onClick={this.onRegisterBtnClick} id='Register'>Register</button>
                </div>
            </div>
        );
    }
}

export default withRouter(Registration);