import React, {Component} from "react";
import {withRouter} from "react-router-dom";
import Cookies from "universal-cookie/lib";
import * as bcryptjs from 'bcryptjs';

const cookies = new Cookies();

class Registration extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: ''
        }
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async handleSubmit(event) {
        event.preventDefault();
        const salt = await bcryptjs.genSalt(10);
        const passwordHash = await bcryptjs.hash(this.state.password, salt);
        const reqBody = {
            ...this.state,
            password: passwordHash
        };
        console.log(reqBody);
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <div>
                    <label htmlFor='username'>Username</label>
                    <input type='username' id='username' value={this.state.username} onChange={(e) => this.setState({username: e.target.value})} />
                </div>
                <div>
                    <label htmlFor='password'>Password</label>
                    <input type='password' id='password' value={this.state.password} onChange={(e) => this.setState({password: e.target.value})} />
                </div>
                <button>Register</button>
            </form>
        );
    }
}

export default withRouter(Registration);