import Cookies from "universal-cookie/lib";

const cookies = new Cookies();

export default function isAuthenticated() {
    return !!cookies.get('userId');
}
