const routes = require('./api/index')
const path = require('path')

const express = require('express')
const env = require('node-env-file')
env(__dirname + '/.env')

const app = express()
const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
const router = express.Router()
const port = process.env.PORT || 3000

const Stomp = require('stompjs');
let sockjs = require('sockjs-client');
sockjs = new sockjs(`${process.env.BASE_URL}/ws`)

const client = Stomp.over(sockjs);
const userId = 1;

function onMessageReceived(message) {
    console.log(message);
}

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

app.set('view engine', 'pug')
app.set('views', path.join(__dirname, 'static/views'))
app.use(express.static(path.join(__dirname, 'static')))


routes(router)

app.use('/', router)

app.listen(port, () => {
    console.log(`Server started at port ${port}`)
})
