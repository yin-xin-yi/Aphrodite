import service from '@/utils/service.js';

const API_PATH = {
    LOGIN_PATH: "/auth/login",
    REGISTER_PATH: "/auth/register",
};

export const HandleLogin = async (logindata) => {
    return await service.post(API_PATH.LOGIN_PATH, logindata);
};

export const HandleRegister = async (register) => {
    return await service.post(API_PATH.REGISTER_PATH, register);
};

