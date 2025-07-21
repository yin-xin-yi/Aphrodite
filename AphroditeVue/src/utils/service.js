import axios from 'axios';
import { getToken, setToken, removeToken } from './token'; 

const service = axios.create({
  baseURL: '/api', 
  timeout: 5000 
});

/**
 * 请求 
 * 1. 封装 token
 * 2. 
 */
service.interceptors.request.use(
  config => {
    const token = getToken();
    
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token;
    }
    
    return config;
  },
  error => {
    console.error('请求错误', error);
    return Promise.reject(error);
  }
);

service.interceptors.response.use(
  response => {
    const res = response.data;
    if (res.code === 200 && res.data && res.data.token) {
      setToken(res.data.token);
      console.log('Token 已自动存储!');
    }
    return res; // 注意这里直接是data
  },
  error => {
    console.error('API 错误', error.response || error);
    if (error.response) {
      const { status, data } = error.response;
      const message = data.message || '网络请求失败，请稍后再试';
      if (status === 401) {
        alert('登录状态已过期，请重新登录');
        removeToken();
      } else {
        alert(message);
      }
    } else {
      alert('网络请求失败，请检查您的网络连接');
    }
    
    return Promise.reject(error);
  }
);

export default service;
