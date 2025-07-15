<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const isLoggedIn = ref(false);
const user = ref({
    name: '前端小能手',
    avatar: 'https://api.multiavatar.com/前端小能手.svg',
});

const featuredBars = ref([
    { id: 1, name: 'Vue', description: '渐进式JavaScript框架', cover: 'https://v2.cn.vuejs.org/images/logo.svg', },
    { id: 2, name: 'Java', description: '企业级应用开发首选', cover: '../../public/java.svg', },
    { id: 3, name: '摸鱼', description: '工作再忙，也要摸鱼', cover: 'https://api.iconify.design/mdi:fish.svg?color=%23ff69b4', },
    { id: 4, name: '美食', description: '唯有爱与美食不可辜负', cover: 'https://api.iconify.design/emojione:pot-of-food.svg', },
]);

const hotPosts = ref([
    { id: 101, barName: 'Vue', title: 'Vue 3.4 正式发布，性能大幅提升！', author: 'Evan You', replyCount: 99, viewCount: '10k' },
    { id: 102, barName: 'Java', title: 'Spring Boot 3.2.0 现已可用，虚拟线程支持来了', author: 'Java官方', replyCount: 152, viewCount: '15k' },
    { id: 103, barName: '摸鱼', title: '【技术讨论】如何在老板眼皮底下优雅地摸鱼？', author: '摸鱼大师', replyCount: 520, viewCount: '22k' },
    { id: 104, barName: '美食', title: '深夜放毒，晒一晒你的宵夜！', author: '吃货一枚', replyCount: 345, viewCount: '18k' },
]);

const GoToLogin = () => {
    router.push('/RegisterAndLogin');
};
const navigateToBar = (barName) => {
    router.push(`/bar/${barName.toLowerCase()}`);
};

// 仅为演示：切换登录状态的方法
const toggleLoginStatus = () => {
    isLoggedIn.value = !isLoggedIn.value;
};

</script>



<template>
    <div class="home-container">
        <!-- 1. 顶部导航栏 -->
        <header class="home-header">
            <div class="logo">
                <!-- 修正 & 优化 1: Logo 路径修正，并添加点击返回首页的功能 -->
                <router-link to="/" class="logo-link">
                    <img src="/my-message.svg" alt="贴吧Logo" class="logo-img">
                    <span class="logo-text">我的贴吧</span>
                </router-link>
            </div>
            <div class="search-bar">
                <input type="text" placeholder="搜索你感兴趣的吧..." />
                <button class="search-btn">
                    <i class="fas fa-search"></i> 搜索
                </button>
            </div>
            <div class="user-profile">
                <template v-if="isLoggedIn">
                    <img :src="user.avatar" alt="用户头像" class="avatar" />
                    <span class="username">{{ user.name }}</span>
                    <!-- 实际项目中，这里会有退出登录等操作 -->
                </template>
                <template v-else>
                    <!-- 修正 & 优化 2: 这是您要求的跳转到登录/注册的选项 -->
                    <button class="login-btn" @click="GoToLogin">登录 / 注册</button>
                </template>
            </div>
        </header>

        <!-- 主体内容区域 -->
        <main class="main-content">
            <!-- 仅为演示：一个切换登录状态的模拟按钮 -->
            <div class="demo-switch">
                <button @click="toggleLoginStatus">
                    {{ isLoggedIn ? '模拟退出登录' : '模拟登录成功' }}
                </button>
                <span> (此按钮仅为开发演示)</span>
            </div>

            <!-- 推荐“吧”模块 -->
            <section class="featured-bars-section">
                <h2><i class="fas fa-star icon-star"></i> 推荐吧</h2>
                <div class="bars-grid">
                    <div v-for="bar in featuredBars" :key="bar.id" class="bar-card" @click="navigateToBar(bar.name)">
                        <img :src="bar.cover" :alt="bar.name" class="bar-cover" />
                        <div class="bar-info">
                            <h3 class="bar-name">{{ bar.name }}吧</h3>
                            <p class="bar-desc">{{ bar.description }}</p>
                        </div>
                    </div>
                </div>
            </section>

            <!-- 热门帖子列表 -->
            <section class="hot-posts-section">
                <h2><i class="fas fa-fire-alt icon-fire"></i> 热门帖子</h2>
                <div class="post-list">
                    <div v-for="post in hotPosts" :key="post.id" class="post-item">
                        <div class="post-title-wrapper">
                            <span class="post-bar-tag">[{{ post.barName }}吧]</span>
                            <!-- 修正 & 优化 3: 使用 router-link 实现无刷新跳转 -->
                            <router-link :to="`/post/${post.id}`" class="post-title">{{ post.title }}</router-link>
                        </div>
                        <div class="post-meta">
                            <span><i class="fas fa-user"></i> {{ post.author }}</span>
                            <span><i class="fas fa-comment-dots"></i> {{ post.replyCount }}</span>
                            <span><i class="fas fa-eye"></i> {{ post.viewCount }}</span>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    </div>
</template>

<style scoped>
/* 引入字体图标库 */
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css');

/* 修正 & 优化 5: 将CSS变量定义在组件根元素上，作用域更清晰 */
.home-container {
    --primary-blue: #4a90e2;
    --light-blue: #e9f2ff;
    --primary-pink: #ff69b4;
    --light-pink: #fff0f5;
    --text-color: #333;
    --text-light: #777;
    --border-color: #e0e0e0;
    --bg-color: #f8f9fa;
    /* 🎨 优化: 添加一个淡淡的渐变背景，让页面更有层次感 */
    background: linear-gradient(180deg, #ffffff 0%, var(--bg-color) 300px);
    min-height: 100vh;
}

/* 顶部导航栏 */
.home-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 15px 50px;
    background-color: white;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
    position: sticky;
    top: 0;
    z-index: 1000;
}

.logo-link {
    display: flex;
    align-items: center;
    text-decoration: none;
}

.logo-img {
    height: 40px;
    margin-right: 10px;
}

.logo-text {
    font-size: 24px;
    font-weight: bold;
    color: var(--primary-blue);
}

.search-bar {
    display: flex;
    width: 40%;
}

.search-bar input {
    flex-grow: 1;
    padding: 10px 15px;
    border: 2px solid var(--border-color);
    border-right: none;
    border-radius: 20px 0 0 20px;
    outline: none;
    transition: border-color 0.3s;
}

.search-bar input:focus {
    border-color: var(--primary-blue);
}

.search-btn {
    padding: 10px 20px;
    border: none;
    background: linear-gradient(45deg, var(--primary-blue), var(--primary-pink));
    color: white;
    font-weight: bold;
    border-radius: 0 20px 20px 0;
    cursor: pointer;
    transition: transform 0.2s, box-shadow 0.2s;
    display: flex;
    align-items: center;
    gap: 5px;
}

.search-btn:hover {
    transform: scale(1.05);
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.user-profile {
    display: flex;
    align-items: center;
}

.avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    border: 2px solid var(--primary-pink);
    margin-right: 10px;
}

.username {
    font-weight: 500;
    color: var(--text-color);
}

.login-btn {
    padding: 8px 20px;
    border: 2px solid var(--primary-blue);
    background-color: transparent;
    color: var(--primary-blue);
    font-weight: bold;
    border-radius: 20px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.login-btn:hover {
    background-color: var(--primary-blue);
    color: white;
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(74, 144, 226, 0.3);
}

/* 主体内容 */
.main-content {
    max-width: 1000px;
    margin: 20px auto;
    padding: 0 20px;
}

/* 演示用按钮样式 */
.demo-switch {
    margin-bottom: 20px;
    text-align: center;
}

.demo-switch button {
    padding: 5px 10px;
    border-radius: 5px;
    border: 1px solid var(--primary-pink);
    background: var(--light-pink);
    color: var(--primary-pink);
    cursor: pointer;
}

.demo-switch span {
    font-size: 12px;
    color: #999;
}

section {
    background-color: white;
    padding: 25px;
    border-radius: 12px;
    margin-bottom: 30px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
}

section h2 {
    font-size: 22px;
    color: var(--text-color);
    margin-bottom: 20px;
    border-left: 5px solid var(--primary-pink);
    padding-left: 10px;
    display: flex;
    align-items: center;
    gap: 8px;
}

.icon-star {
    color: #ffd700;
}

.icon-fire {
    color: #ff4500;
}

/* 推荐吧卡片 */
.bars-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 20px;
}

.bar-card {
    border: 1px solid var(--border-color);
    border-radius: 10px;
    overflow: hidden;
    cursor: pointer;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.bar-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 20px rgba(74, 144, 226, 0.2);
}

.bar-cover {
    width: 100%;
    height: 120px;
    object-fit: contain;
    background-color: var(--light-blue);
    padding: 10px;
}

.bar-info {
    padding: 15px;
}

.bar-name {
    font-size: 18px;
    margin: 0 0 5px 0;
    color: var(--primary-blue);
}

.bar-desc {
    font-size: 14px;
    color: var(--text-light);
    margin: 0;
}

/* 热门帖子列表 */
.post-list .post-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 10px;
    border-bottom: 1px dashed var(--border-color);
}

.post-list .post-item:last-child {
    border-bottom: none;
}

.post-title-wrapper {
    display: flex;
    align-items: center;
}

.post-bar-tag {
    background-color: var(--light-pink);
    color: var(--primary-pink);
    padding: 3px 8px;
    border-radius: 5px;
    font-size: 12px;
    margin-right: 10px;
    font-weight: bold;
}

.post-title {
    font-size: 16px;
    color: var(--text-color);
    text-decoration: none;
    transition: color 0.3s;
}

.post-title:hover {
    color: var(--primary-blue);
}

.post-meta {
    font-size: 13px;
    color: var(--text-light);
    display: flex;
    align-items: center;
    gap: 15px;
    min-width: 220px;
    justify-content: flex-end;
}

.post-meta i {
    margin-right: 4px;
}
</style>
