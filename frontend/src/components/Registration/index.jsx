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
        this.props.history.push('/registration/success');
    }

    render() {
        return (
            <div>
                <div>
                    <input type='text' id='username' value="Username"/>
                    <input type='text' id='password' value="Password"/>
                    <button onClick={this.onRegisterBtnClick} id='Register'>Register</button>
                </div>
            </div>
        );
    }
}

export default withRouter(Registration);