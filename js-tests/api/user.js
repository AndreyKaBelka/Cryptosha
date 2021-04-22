const serverRequest = require("../utils/serverRequest");
const URI = require('urijs')

function routes(router) {
    router.get('/createUser', async (req, res) => {
        const response = await serverRequest('/user', {
            method: 'POST',
            body: {
                username: 'Andrey',
                publicKey: '{0;0}'
            }
        }).then(data => data)
        console.log(response)
        return res.json({
            success: true
        });
    })

    router.get('/user', async (req, res) => {
        const userId = req.query.userId
        const response = await serverRequest(URI('/user').search({userId}).toString(), {
            method: 'GET'
        })
        console.log(response)
        return res.json({
            success: true
        });
    })
}

module.exports = routes