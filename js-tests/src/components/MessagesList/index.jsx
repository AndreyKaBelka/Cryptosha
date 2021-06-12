import React, {Component} from 'react';
import ChatTitle from "../ChatTitle";

export default class MessagesList extends Component {
    render() {
        const {title} = this.props;
        return (<div className='rightItems'>
            <ChatTitle title={title}/>
        </div>)
    }

}
