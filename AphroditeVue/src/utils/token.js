const TOKEN_KEY = 'Token';

/**
 * 从 localStorage 获取 Token
 * 
 */
export function GetToken() {
  return localStorage.getItem(TOKEN_KEY);
}

/**
 * 将 Token 保存到 localStorage
 * 
 */
export function SetToken(token) {
  localStorage.setItem(TOKEN_KEY, token);
}

/**
 * 从 localStorage 删除 Token
 */
export function RemoveToken() {
  localStorage.removeItem(TOKEN_KEY);
}
