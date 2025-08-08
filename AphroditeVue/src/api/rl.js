import service from '@/utils/service.js';
import { GetUserId } from '../utils/auth';

const API_PATH = {
    LOGIN_PATH: "/auth/login",
    REGISTER_PATH: "/auth/register",
    ALTER_PASSWORD:"/auth/alterpd",
    FETCH_EMAIL:"/auth/fetchemail"
};

export const HandleLogin = async (logindata) => {
    return await service.post(API_PATH.LOGIN_PATH, logindata);
};

export const HandleRegister = async (register) => {
    return await service.post(API_PATH.REGISTER_PATH, register);
};


export const AlterPassword = async (alterform) => {
    // userid password newPassword 
    return await service.post(API_PATH.ALTER_PASSWORD, alterform);
};


export const FetchEmail = async () =>{
    return await service.post(API_PATH.FETCH_EMAIL,{
        "userId" : GetUserId()
    })
}