import React from 'react';
import ChatTitle from "../ChatTitle";

export default function MessagesList(props) {
    const {title} = props;
    return (<div>
        <ChatTitle title={title}/>
    </div>)
}
