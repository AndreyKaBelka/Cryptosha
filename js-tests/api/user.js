const serverRequest = require("../utils/serverRequest");
const URI = require('urijs')

const user = function (){};

user.prototype.getUser = async () => {
    const userId = 1; //TODO: get userId
    const response = await serverRequest(URI('/user').search({userId}).toString(), {
        method: 'GET'
    })
    console.log(response)
    return response;
}

user.prototype.createUser = async (username) => {
    const publicKey = generatePublicKey();
    await serverRequest('/user', {
        method: 'POST',
        body: {
            username,
            publicKey
        }
    }).then(data => {
        if (data.id) {
           //TODO: save user id
        }
        else {
            return undefined;
        }
    })
}

function generatePublicKey() {
    return "{0;0}"
}

export default user;
