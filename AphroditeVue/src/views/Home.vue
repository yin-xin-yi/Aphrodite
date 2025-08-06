<script setup>
import { reactive, computed, ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { GetUserName} from "@/utils/auth";
import EditorPost from "@/views/component/EditorPost.vue";
import { RemoveAuthInfo } from "../utils/auth";
const router = useRouter();
const route = useRoute();
const ShowEditor = ref(false);

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


const OpenEditor = () => {
  ShowEditor.value = true;
};

const CloseEditor = () => {
  ShowEditor.value = false;
};

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
  RemoveAuthInfo();
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
