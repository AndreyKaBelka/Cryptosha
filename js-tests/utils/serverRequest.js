const fetch = require('node-fetch')

async function serverRequest(url, opts) {
    opts.body = JSON.stringify(opts.body)
    return await fetch(`${process.env.BASE_URL}${url}`, {
        ...opts,
        headers: {
            'Content-Type': 'application/json',
            accept: 'application/json'
        }
    }).then(data => data.text()).catch((err) => console.log(err))
}

module.exports = serverRequest