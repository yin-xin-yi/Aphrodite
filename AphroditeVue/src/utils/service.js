import axios from "axios";
import {
  GetToken,
  SetToken,
  RemoveAuthInfo,
  SetUserId,
  SetUserName,
} from "./auth";

const service = axios.create({
  baseURL: "/api",
  timeout: 5000,
});

/**
 * 请求
 * 1. 封装 token
 * 2.
 */
service.interceptors.request.use(
  (config) => {
    const token = GetToken();

    if (token) {
      config.headers["Authorization"] = "Bearer " + token;
    }

    return config;
  },
  (error) => {
    console.error("请求错误", error);
    return Promise.reject(error);
  }
);

service.interceptors.response.use(
  (response) => {
    const res = response.data;
    if (res.data) {
      if (res.data.token) {
        SetToken(res.data.token);
        console.log("Token 已自动存储!");
      }
      if (res.data.userid) {
        SetUserId(res.data.userid);
        console.log("userid 已自动存储!");
      }
      if (res.data.username) {
        SetUserName(res.data.username);
        console.log("username 已自动存储!");
      }
    }

    return res; // 注意这里直接是data
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default service;
