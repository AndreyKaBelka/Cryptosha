const serverRequest = require("../utils/serverRequest");
const URI = require('urijs')

function routes(router) {
    router.get('/message', (req, res) => {
        res.render('message', {bundle: '../js/message.js'});
    })

    router.post('/message', (req, res) => {
        console.log(req.body)
        return res.json({
            success: true
        })
    })
}

module.exports = routes