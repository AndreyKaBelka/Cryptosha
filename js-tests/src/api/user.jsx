import serverRequest from "../utils/serverRequest.jsx";
import URI from 'urijs';

export async function getUser(userId) {
    return await serverRequest(URI('/user').search({userId}).toString(), {
        method: 'GET'
    });
}

export async function createUser(username) {
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
        } else {
            return undefined;
        }
    })
}

function generatePublicKey() {
    return "{0;0}"
}
