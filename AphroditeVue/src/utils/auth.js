const USERNAME_KEY = 'username';
const USERID_KEY = 'userid';
const EMAIL_KEY = 'email';

export function SetToken(token) {
    return localStorage.setItem(TOKEN_KEY, token);
}

export function GetUserName() {
    return localStorage.getItem(USERNAME_KEY);
}

export function GetUserId() {
    return localStorage.getItem(USERID_KEY);
}

export function GetEmail() {
    return localStorage.getItem(EMAIL_KEY);
}

export function RemoveAuthInfo() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USERNAME_KEY);
    localStorage.removeItem(USERID_KEY);
    localStorage.removeItem(EMAIL_KEY);
}
