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
    console.log("response:",response);
    const res = response.data;
    console.log("res:",res);
    // if (res && res.data && res.data.token) {
    //   SetToken(res.data.token);
    //   console.log("Token 已自动存储!");
    // }
    // if (res && res.data && res.data.userid) {
    //   SetUserId(res.data.userid);
    //   console.log("userid 已自动存储!");
    // }
    // if (res && res.data && res.data.username) {
    //   SetUserName(res.data.username);
    //   console.log("username 已自动存储!");
    // }
    return res;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default service;
