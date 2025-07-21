<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();
const GoToLogin = () => {
    router.push('/RegisterAndLogin'); 
};
</script>

<template>
    <div class="index-container">
        <div class="background-animation"></div>
        
        <div class="content-box">
            <div class="content-wrapper">
                <h1 class="main-title">Aphrodite</h1>
                <p class="subtitle">xx校园墙 欢迎你~</p>
                
                <p class="description">
                    Aphrodite 致力于打造更和谐,更热闹,更便捷的校园墙
                </p>
                
                <div class="action-buttons">
                    <button class="btn-primary" @click="GoToLogin">开始使用</button>
                    <button class="btn-secondary">了解更多</button>
                </div>
            </div>
        </div>
        
        <footer class="page-footer">
            <p>&copy; 2025 LosAngelous Project. All Rights Reserved.</p>
        </footer>
    </div>
</template>

<style scoped>
.index-container {
    --soft-pink: #e8c2ca;
    --soft-blue: #b2c7e3;
    --accent-pink: #d89aab;
    --text-dark: #3a4b60;
    --text-light: #6e7d8d;
    --glass-bg: rgba(255, 255, 255, 0.25);
    --glass-border: rgba(255, 255, 255, 0.4);

    display: flex;
    flex-direction: column; /* 垂直布局，为页脚留出空间 */
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    overflow: hidden;
    position: relative;
    font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
}

.background-animation {
    position: absolute;
    top: -50%;
    left: -50%;
    width: 200%;
    height: 200%;
    background: linear-gradient(45deg, var(--soft-pink), var(--soft-blue), var(--soft-pink), var(--soft-blue));
    background-size: 400% 400%;
    animation: gradient-move 20s ease infinite;
    z-index: -1; 
}

@keyframes gradient-move {
    0% { background-position: 0% 50%; }
    50% { background-position: 100% 50%; }
    100% { background-position: 0% 50%; }
}

.content-box {
    max-width: 650px;
    padding: 50px 60px;
    background: var(--glass-bg);
    backdrop-filter: blur(12px);
    border: 1px solid var(--glass-border);
    border-radius: 20px;
    box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.2);
    text-align: center;
    animation: fade-in-up 1s ease-out;
}

@keyframes fade-in-up {
    from {
        opacity: 0;
        transform: translateY(30px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.main-title {
    font-size: 56px;
    font-weight: 300; 
    color: var(--text-dark);
    margin-bottom: 10px;
    letter-spacing: 2px; 
}

.subtitle {
    font-size: 20px;
    color: var(--text-light);
    margin-bottom: 30px;
    font-weight: 400;
}

.description {
    font-size: 16px;
    color: var(--text-dark);
    line-height: 1.7;
    margin-bottom: 40px;
}

.action-buttons {
    display: flex;
    justify-content: center;
    gap: 20px; 
}

.btn-primary {
    padding: 14px 35px;
    font-size: 18px;
    font-weight: bold;
    color: white;
    background: linear-gradient(90deg, var(--accent-pink), var(--soft-blue));
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
}

.btn-primary:hover {
    transform: translateY(-3px);
    box-shadow: 0 6px 20px rgba(178, 199, 227, 0.4);
}

.btn-secondary {
    padding: 14px 35px;
    font-size: 18px;
    font-weight: bold;
    color: var(--accent-pink); 
    background-color: transparent;
    border: 2px solid var(--accent-pink);
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.btn-secondary:hover {
    background-color: var(--accent-pink);
    color: white;
    transform: translateY(-3px);
    box-shadow: 0 6px 20px rgba(216, 154, 171, 0.4);
}


.page-footer {
    position: absolute;
    bottom: 20px;
    color: rgba(255, 255, 255, 0.7);
    font-size: 14px;
}
</style>
