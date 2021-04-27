const Stomp = require('stompjs');
let sockjs = require('sockjs-client');

const client = function (){};

function ClientCreate (userId) {
    sockjs = new sockjs(`${process.env.BASE_URL}/ws`)
    const client = Stomp.over(sockjs);

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
}

client.prototype.ClientCreate = ClientCreate;

function onMessageReceived(message) {
    console.log(message);
}

export default client;