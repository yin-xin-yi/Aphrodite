import axios from 'axios';
const service = axios.create({
  baseURL: '/api', 
  timeout: 5000 
});

service.interceptors.response.use(
  response => {
    return response.data; 
  },
  error => {
    console.error('API错误', error.response || error);
    alert(error.response?.data?.message || '网络请求失败，请稍后再试');
    return Promise.reject(error);
  }
);

export default service;

// 登录成功 应该 返回这个 
// {
//   "code": 200,
//   "message": "登录成功",
//   "data": {
//     "token": "a-very-long-and-secure-jwt-token",
//     "username": "foryxy"
//   }
// }
// 登录失败

