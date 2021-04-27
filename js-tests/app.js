const routes = require('./api/index')

const express = require('express')
const env = require('node-env-file')
env(__dirname + '/.env')

const app = express()
const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
const router = express.Router()
const port = process.env.PORT || 3000

routes(router)

app.use('/', router)

app.listen(port, () => {
    console.log(`Server started at port ${port}`)
})
