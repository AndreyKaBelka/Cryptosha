const userRoutes = require('./user')
const messageRoutes = require('./message')

function routes (router) {
    userRoutes(router)
    messageRoutes(router)
}

module.exports = routes