<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { HandleLogin, HandleRegister } from '@/api/rl';

const router = useRouter();
const islogin = ref(true);
const LoginForm = reactive({
    username: '',
    password: '',
});
const RegisterForm = reactive({
    username: '',
    email: '',
    password: '',
});

const SubmitLogin = async () => {
    if (!LoginForm.username || !LoginForm.password) {
        alert('请输入用户名和密码！');
        return;
    }
    try {
        const response = await HandleLogin(LoginForm);
        if (response.code == 200)
        {
            alert(response.message || '登录成功！');
            router.push({ name: 'Home' });
        }
        else if(response.code == 401){
            alert(response.message)
        }
    }
    catch (error) {
        console.error('登录请求失败:', error);
    }
};

const SubmitRegister = async () => {
    if (!RegisterForm.username || !RegisterForm.email || !RegisterForm.password) {
        alert('请填写所有注册信息！');
        return;
    }
    try {
        const response = await HandleRegister(RegisterForm);
        if (response && response.success) {
            alert(response.message || '注册成功！现在可以登录了。');
            // 注册成功后，清空表单并切换到登录页
            router.push({ name: "Home" })

            Object.assign(RegisterForm, { username: '', email: '', password: '' });
        } else {
            alert(response.message || '注册失败，请稍后再试！');
        }
    } catch (error) {
        console.error('注册请求失败:', error);
        alert('服务器开小差了，请稍后再试！');
    }
};

</script>

<template>
    <div class="login-register-container">
        <div class="background-animation"></div>
        <div class="form-box">
            <transition name="form-fade" mode="out-in">
                <div v-if="islogin" key="login" class="form-content">
                    <h2>欢迎回来</h2>
                    <form @submit.prevent="SubmitLogin">
                        <div class="input-group">
                            <input type="text" v-model="LoginForm.username" required>
                            <label>用户名 / 邮箱</label>
                        </div>
                        <div class="input-group">
                            <input type="password" v-model="LoginForm.password" required>
                            <label>密码</label>
                        </div>
                        <button type="submit">登 录</button>
                    </form>
                    <p class="switch-form">
                        还没有账户？<span @click="islogin = false">立即注册</span>
                    </p>
                </div>



                <div v-else key="register" class="form-content">
                    <h2>创建新账户</h2>
                    <form @submit.prevent="SubmitRegister">
                        <div class="input-group">
                            <input type="text" v-model="RegisterForm.username" required>
                            <label>用户名</label>
                        </div>
                        <div class="input-group">
                            <input type="email" v-model="RegisterForm.email" required>
                            <label>邮箱地址</label>
                        </div>
                        <div class="input-group">
                            <input type="password" v-model="RegisterForm.password" required>
                            <label>设置密码</label>
                        </div>
                        <button type="submit">注 册</button>
                    </form>
                    <p class="switch-form">
                        已有账户？<span @click="islogin = true">返回登录</span>
                    </p>
                </div>
            </transition>
        </div>
    </div>
</template>

<style scoped>
.login-register-container {
    --soft-pink: #e8c2ca;
    --soft-blue: #b2c7e3;
    --accent-pink: #d89aab;
    --text-dark: #3a4b60;
    --text-light: #6e7d8d;
    --glass-bg: rgba(255, 255, 255, 0.25);
    --glass-border: rgba(255, 255, 255, 0.4);

    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    overflow: hidden;
    position: relative;
}

.background-animation {
    position: absolute;
    top: -50%;
    left: -50%;
    width: 200%;
    height: 200%;
    /* 应用新的渐变色 */
    background: linear-gradient(45deg, var(--soft-pink), var(--soft-blue), var(--soft-pink), var(--soft-blue));
    background-size: 400% 400%;
    animation: gradient-move 20s ease infinite;
    /* 动画时间稍长，更舒缓 */
}

@keyframes gradient-move {
    0% {
        background-position: 0% 50%;
    }

    50% {
        background-position: 100% 50%;
    }

    100% {
        background-position: 0% 50%;
    }
}

/* 🎨 优化点 3: 玻璃拟态质感提升 */
.form-box {
    width: 400px;
    padding: 40px;
    background: var(--glass-bg);
    backdrop-filter: blur(12px);
    /* 模糊效果更强一点 */
    border: 1px solid var(--glass-border);
    border-radius: 20px;
    box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.2);
    /* 阴影更柔和 */
    text-align: center;
}

.form-content h2 {
    margin-bottom: 30px;
    font-weight: 300;
    font-size: 28px;
    color: var(--text-dark);
    /* 应用新的深色文本颜色 */
}

.input-group {
    position: relative;
    margin-bottom: 35px;
}

.input-group input {
    width: 100%;
    padding: 10px 0;
    font-size: 16px;
    color: var(--text-dark);
    border: none;
    border-bottom: 2px solid rgba(0, 0, 0, 0.15);
    /* 底部边框颜色更淡 */
    outline: none;
    background: transparent;
}

.input-group label {
    position: absolute;
    top: 10px;
    left: 0;
    padding: 0;
    font-size: 16px;
    color: var(--text-light);
    /* 应用新的浅色文本颜色 */
    pointer-events: none;
    transition: .5s;
}

/* 🎨 优化点 4: 交互颜色同步更新 */
.input-group input:focus~label,
.input-group input:valid~label {
    top: -20px;
    left: 0;
    color: var(--soft-blue);
    /* 聚焦时标签颜色变为柔和蓝 */
    font-size: 12px;
}

.input-group input:focus {
    border-bottom-color: var(--soft-blue);
    /* 聚焦时下划线颜色 */
}

button {
    width: 100%;
    padding: 12px 20px;
    font-size: 18px;
    font-weight: bold;
    color: white;
    background: linear-gradient(90deg, var(--accent-pink), var(--soft-blue));
    /* 按钮使用新的渐变色 */
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
    /* 按钮阴影更淡 */
}

button:hover {
    transform: translateY(-3px);
    box-shadow: 0 6px 20px rgba(178, 199, 227, 0.4);
    /* 悬停时阴影颜色与主题匹配 */
}

.switch-form {
    margin-top: 25px;
    font-size: 14px;
    color: var(--text-light);
}

.switch-form span {
    color: var(--accent-pink);
    cursor: pointer;
    font-weight: bold;
    transition: color 0.3s;
}

.switch-form span:hover {
    text-decoration: underline;
    color: var(--soft-blue);
}

.form-fade-enter-active,
.form-fade-leave-active {
    transition: opacity 0.4s ease, transform 0.4s ease;
}

.form-fade-enter-from,
.form-fade-leave-to {
    opacity: 0;
    transform: translateY(20px);
}
</style>
