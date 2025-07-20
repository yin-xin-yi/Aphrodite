<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';

// ------------------------------------------------------------------------------------------
/**
 * 参数设置
 *  
 */

const router = useRouter();
const activeview = ref('home');
const user = reactive({
    name: 'Yxy',
    avatar: 'Yxy'
});
// ------------------------------------------------------------------------------------------


// ------------------------------------------------------------------------------------------
/**
 * 业务代码
 */

//  帖子渲染
const posts = reactive([
    // 帖子 类似样式
    // {
    //     id: 1,
    //     author: { name: '111', avatar: 'https://api.multiavatar.com/学姐A.svg' },
    //     timestamp: '2小时前',
    //     content: '111111111111111111111111111111',
    //     likes: 11,
    //     comments: 11
    // },
]);

// ------------------------------------------------------------------------------------------
/**
 * 页面切换 区
 *
 */

// 切换视图的函数
const SetView = (viewName) => {
    // TODO: 处理登出 
};

// 登出
const HandleLogout = () => {

    // TODO: 清理token 之类的
    // 
    router.push('/RegisterAndLogin');
};
// ------------------------------------------------------------------------------------------

</script>

<template>
    <div class="home-layout">
        <!-- 统一的动态背景 -->
        <div class="background-animation"></div>

        <!-- 侧边导航栏 (Wiki风格的核心) -->
        <aside class="sidebar">
            <div class="sidebar-header">
                <h1 class="logo">Aphrodite</h1>
            </div>
            <nav class="sidebar-nav">
                <ul>
                    <li @click="SetView('home')" :class="{ active: activeview === 'home' }">
                        <a><i class="icon">🏠</i> 首页</a>
                    </li>
                    <li @click="SetView('my-posts')" :class="{ active: activeview === 'my-posts' }">
                        <a><i class="icon">📄</i> 个人帖管理</a>
                    </li>
                    <li @click="SetView('profile')" :class="{ active: activeview === 'profile' }">
                        <a><i class="icon">⚙️</i> 个人信息管理</a>
                    </li>
                </ul>
            </nav>
            <div class="sidebar-footer">
                <div class="user-profile">
                    <img :src="user.avatar" alt="avatar" class="user-avatar">
                    <span class="user-name">{{ user.name }}</span>
                </div>
                <button @click="HandleLogout" class="logout-button">登出</button>
            </div>
        </aside>

        <!-- 主内容区 -->
        <main class="main-content">
            <header class="main-header">
                <h2>校园动态</h2>
                <button class="btn-primary">发布新帖</button>
            </header>

            <!-- 帖子流 -->
            <!-- 所有的帖子 都在这里 -->
            <div class="post-feed">
                <div v-for="post in posts" :key="post.id" class="post-card">
                    <div class="post-header">
                        <img :src="post.author.avatar" alt="author avatar" class="author-avatar">
                        <div class="author-info">
                            <span class="author-name">{{ post.author.name }}</span>
                            <span class="post-timestamp">{{ post.timestamp }}</span>
                        </div>
                    </div>
                    <div class="post-body">
                        <p>{{ post.content }}</p>
                    </div>
                    <div class="post-footer">
                        <button class="action-btn">❤️ {{ post.likes }}</button>
                        <button class="action-btn">💬 {{ post.comments }}</button>
                        <button class="action-btn">🔗 分享</button>
                    </div>
                </div>
            </div>
        </main>
    </div>
</template>

<style scoped>
/* 🎨 核心设计语言：颜色变量和字体 */
:root {
    --soft-pink: #e8c2ca;
    --soft-blue: #b2c7e3;
    --accent-pink: #d89aab;
    --text-dark: #3a4b60;
    --text-light: #6e7d8d;
    --glass-bg: rgba(255, 255, 255, 0.25);
    --glass-border: rgba(255, 255, 255, 0.4);
    --sidebar-bg: rgba(255, 255, 255, 0.15);
    --active-bg: rgba(255, 255, 255, 0.3);
}

.home-layout {
    display: flex;
    min-height: 100vh;
    position: relative;
    font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
}

.background-animation {
    position: fixed;
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

/* 侧边栏样式 */
.sidebar {
    width: 260px;
    flex-shrink: 0;
    background: var(--sidebar-bg);
    backdrop-filter: blur(15px);
    border-right: 1px solid var(--glass-border);
    display: flex;
    flex-direction: column;
    padding: 20px;
    color: var(--text-dark);
}

.sidebar-header .logo {
    font-size: 28px;
    font-weight: 300;
    text-align: center;
    margin-bottom: 40px;
    letter-spacing: 2px;
}

.sidebar-nav ul {
    list-style: none;
    padding: 0;
    margin: 0;
}

.sidebar-nav li {
    padding: 15px 20px;
    margin-bottom: 10px;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.sidebar-nav li.active {
    background-color: var(--active-bg);
    font-weight: bold;
}

.sidebar-nav li:hover:not(.active) {
    background-color: rgba(255, 255, 255, 0.1);
}

.sidebar-nav a {
    text-decoration: none;
    color: inherit;
    display: flex;
    align-items: center;
}

.sidebar-nav .icon {
    margin-right: 15px;
    font-style: normal;
}

.sidebar-footer {
    margin-top: auto;
    /* 将页脚推到底部 */
    padding-top: 20px;
    border-top: 1px solid var(--glass-border);
}

.user-profile {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
}

.user-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
    background: white;
}

.logout-button {
    width: 100%;
    padding: 10px;
    background-color: transparent;
    border: 1px solid var(--accent-pink);
    color: var(--accent-pink);
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s;
}

.logout-button:hover {
    background-color: var(--accent-pink);
    color: white;
}

/* 主内容区样式 */
.main-content {
    flex-grow: 1;
    padding: 40px;
    overflow-y: auto;
    max-height: 100vh;
}

.main-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
    color: white;
}

.main-header h2 {
    font-size: 24px;
    font-weight: 400;
    text-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
}

.btn-primary {
    /* 复用之前的按钮样式 */
    padding: 12px 25px;
    font-size: 16px;
    font-weight: bold;
    color: rgb(14, 12, 12);
    background: linear-gradient(90deg, var(--accent-pink), var(--soft-blue));
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
}

.btn-primary:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(178, 199, 227, 0.4);
}

/* 帖子卡片样式 */
.post-feed {
    display: grid;
    gap: 25px;
}

.post-card {
    background: var(--glass-bg);
    backdrop-filter: blur(10px);
    border: 1px solid var(--glass-border);
    border-radius: 15px;
    padding: 20px;
    color: var(--text-dark);
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s;
}

.post-card:hover {
    transform: translateY(-5px);
}

.post-header {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
}

.author-avatar {
    width: 45px;
    height: 45px;
    border-radius: 50%;
    margin-right: 15px;
}

.author-info {
    display: flex;
    flex-direction: column;
}

.author-name {
    font-weight: bold;
}

.post-timestamp {
    font-size: 12px;
    color: var(--text-light);
}

.post-body {
    margin-bottom: 20px;
    line-height: 1.6;
}

.post-footer {
    display: flex;
    gap: 15px;
    border-top: 1px solid var(--glass-border);
    padding-top: 15px;
}

.action-btn {
    background: none;
    border: none;
    color: var(--text-light);
    cursor: pointer;
    transition: color 0.3s;
    font-size: 14px;
}

.action-btn:hover {
    color: var(--accent-pink);
}
</style>
