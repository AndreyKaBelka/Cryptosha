const userRoutes = require('./user')
const messageRoutes = require('./message')
const Stomp = require('stompjs');
let sockjs = require('sockjs-client');

function routes (router) {
    sockjs = new sockjs(`${process.env.BASE_URL}/ws`)
    const client = Stomp.over(sockjs);
    const userId = 1;

    client.connect({},
        () => {
            console.log('Connect to messenger server');
            client.subscribe(`/user/${userId}/queue/messages`, onMessageReceived)
        },
        (err) => {
            console.log('Something went wrong!');
            console.log(err);
        }
    )

    userRoutes(router)
    messageRoutes(router, client)
}

function onMessageReceived(message) {
    console.log(message);
}

module.exports = routes