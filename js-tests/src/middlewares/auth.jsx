export default function isAuthenticated() {
    return !!document.cookie.userId;
}
