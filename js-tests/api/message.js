function routes(router, client) {
    router.get('/message', (req, res) => {
        res.render('chat', { bundle: '../js/message.js' });
    })

    router.post('/message', (req, res) => {
        const { content, timestamp, senderId, chatId } = req.body;
        const message = {
            content,
            timestamp,
            senderId,
            chatId
        }
        client.send('/app/sendMessage', {}, JSON.stringify(message))
        return res.json({
            success: true
        })
    })
}

module.exports = routes