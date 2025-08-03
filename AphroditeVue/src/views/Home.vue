<script setup>
import { reactive, computed, ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { GetUserName} from "@/utils/auth";
import EditorPost from "@/views/component/EditorPost.vue";

const router = useRouter();
const route = useRoute();
const ShowEditor = ref(false);

// 用户姓名和用户头像
const user = reactive({
  name: GetUserName() || "游客",
  avatar: `https://api.multiavatar.com/${GetUserName() || 'Yxy'}.svg`,
});

// 开始标题
const PageTitle = computed(() => {
  switch (route.name) {
    case 'PostFeed':
      return '校园动态';
    case 'MyPosts':
      return '个人帖管理';
    case 'UserProfile':
      return '个人信息管理';
    default:
      return 'Aphrodite';
  }
});


// 打开帖子编辑器
const OpenEditor = () => {
  ShowEditor.value = true;
};

// 关闭帖子编辑器
const CloseEditor = () => {
  ShowEditor.value = false;
};

// 创造帖子
const HandlePostCreated = () => {
  CloseEditor();
  alert("发布成功！");
  if(route.name !== 'PostFeed') {
    router.push({ name: 'PostFeed' });
  } else {
    window.location.reload();
  }
};

// 登出
const HandleLogout = () => {
  RemoveToken();
  router.push("/RegisterAndLogin");
};
</script>

<template>
  <div class="home-layout">
    <aside class="sidebar">
      <div class="sidebar-header">
        <h1 class="logo">Aphrodite</h1>
      </div>
      <nav class="sidebar-nav">
        <ul>

          <RouterLink to="/Home/PostFeed" custom v-slot="{ navigate, isActive }">
            <li @click="navigate" :class="{ active: isActive }">
              <a><i class="icon">🏠</i> 首页</a>
            </li>
          </RouterLink>
          <!-- 从 RouterLink 提供的作用域插槽中，解构出 navigate 方法和 isActive 状态 -->
          <RouterLink to="/Home/MyPosts" custom v-slot="{ navigate, isActive }">
            <li @click="navigate" :class="{ active: isActive }">
              <a><i class="icon">📄</i> 个人帖管理</a>
            </li>
          </RouterLink>
          <RouterLink to="/Home/UserProfile" custom v-slot="{ navigate, isActive }">
            <li @click="navigate" :class="{ active: isActive }">
              <a><i class="icon">⚙️</i> 个人信息管理</a>
            </li>
          </RouterLink>
        </ul>
      </nav>
      <div class="sidebar-footer">
        <div class="user-profile-footer">
          <img :src="user.avatar" alt="avatar" class="user-avatar" />
          <span class="user-name">{{ user.name }}</span>
        </div>
        <button @click="HandleLogout" class="logout-button">登出</button>
      </div>
    </aside>

    <main class="main-content">
      
      <header class="main-header">
        <h2>{{ PageTitle }}</h2>
        <button v-if="route.name === 'PostFeed'" class="btn-primary" @click="OpenEditor">发布新帖</button>
      </header>

      <RouterView />
    </main>

    <EditorPost
      v-if="ShowEditor"
      @close="CloseEditor"
      @post-created="HandlePostCreated"
    />
  </div>
</template>

<style scoped>
@import url("@/static/Home.css");
</style>
