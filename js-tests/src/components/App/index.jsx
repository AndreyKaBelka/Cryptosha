import React, {Component} from "react";
import './App.css';
import {Redirect, Route, Switch, withRouter} from 'react-router-dom';
import Home from "../Home";
import Login from "../Login";
import isAuthenticated from "../../middlewares/auth";

class App extends Component {
    render() {
        return (
            <div className="messenger">
                {isAuthenticated() ? (
                        <Switch>
                            <Route path='/home' component={Home}/>
                            <Redirect from='*' to='/home'/>
                        </Switch>
                    )
                    : (
                        <Switch>
                            <Route path='/login' component={Login}/>
                            <Redirect from='*' to='/login'/>
                        </Switch>
                    )}
            </div>
        );
    }
}

export default withRouter(App);