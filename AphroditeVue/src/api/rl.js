const API_PATH = {
    LOGIN_PATH: "/auth/login",
    REGISTER_PATH: "/auth/register",
};

export const handleLogin = (logindata) => {
    return service.post(API_PATH.LOGIN_PATH, logindata);
};

export const HandleRegister = async (register) => {
    return service.post(API_PATH.REGISTER_PATH, register);
};

