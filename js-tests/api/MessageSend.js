
//TODO: fix message send
export default function MessageSend() {
    router.get('/message', (req, res) => {
        res.render('chat', { bundle: '../js/MessageSend.js' });
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