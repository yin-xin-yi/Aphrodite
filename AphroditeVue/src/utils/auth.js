const USERNAME_KEY = 'username';
const USERID_KEY = 'userid';
const EMAIL_KEY = 'email';
const TOKEN_KEY = 'Token';

/**
 * 将 Token 保存到 localStorage
 * 
 */
export function SetToken(token) {
  localStorage.setItem(TOKEN_KEY, token);
}

export function SetUserId(id)
{
    localStorage.setItem(USERID_KEY,id);
}

export function SetUserName(username)
{
    localStorage.setItem(USERNAME_KEY,username)
}

export function GetToken() {
  return localStorage.getItem(TOKEN_KEY);
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
