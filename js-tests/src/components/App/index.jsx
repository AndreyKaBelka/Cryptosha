import React, {Component} from "react";
import './App.css';
import {Redirect, Route, Switch, withRouter} from 'react-router-dom';
import Home from "../Home";
import Login from "../Login";
import isAuthenticated from "../../middlewares/auth";
import Registration from "../Registration";
import Registered from "../Registration/Registered";
import Unregistered from "../Registration/Unregistered";

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
                            <Route exact path='/login' component={Login}/>
                            <Route exact path='/registration' component={Registration}/>
                            <Route path='/registration/fail' component={Unregistered}/>
                            <Route path='/registration/success' component={Registered}/>
                            <Redirect from='/' to='/registration'/>
                        </Switch>
                    )}
            </div>
        );
    }
}

export default withRouter(App);