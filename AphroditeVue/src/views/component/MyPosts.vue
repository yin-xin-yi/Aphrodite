<script setup>
import { ref, onMounted } from "vue";
import { GetSelfPosts } from "@/api/post";
import { MapPostToCardData } from "@/utils/map";
import { GetUserId } from "@/utils/auth.js";

const myposts = ref([]);
const is_loading = ref(true);

onMounted(async () => {
  try {
    // GetSelfPosts 可以为个人 获取信息
    let user_id = GetUserId();
    console.log("777");
    const response = await GetSelfPosts(user_id);
    if (Array.isArray(response)) {
      if (response.length > 0) {
        myposts.value = response.map(MapPostToCardData);
      } else {
        myposts.value = [];
        console.log("response.length > 0 else -> 个人帖子为空");
      }
    } else {
      console.error(
        "Array.isArray(response) else -> 获取帖子失败 或者是api 返回的格式不争取"
      );
    }
  } catch (error) {
    console.error("try 获取我的帖子失败:", error);
    myposts.value = [];
  } finally {
    is_loading.value = false;
  }
});

const EditPost = (postId) => {
  console.log(`编辑帖子 ID: ${postId}`);
  alert(`功能开发中：准备编辑帖子 ${postId}`);
};

const DeletePost = (postId) => {
  console.log(`删除帖子 ID: ${postId}`);
  if (confirm(`确定要删除这篇帖子 (ID: ${postId}) 吗？`)) {
    myposts.value = myposts.value.filter((p) => p.post.id !== postId);
    alert("帖子已删除（模拟）");
  }
};
</script>

<template>
  <div class="management-panel">
    <!-- 这个加载 可以 学习一下 如何操作 -->
    <div v-if="is_loading" class="loading-state">
      <p>~ 正在飞速加载中 ~</p>
    </div>

    <div v-else-if="myposts.length > 0" class="posts-list">
      <div v-for="post in myposts" :key="post.post.id" class="post-item">
        <div class="post-info">
          <h4 class="post-title">{{ post.post.title }}</h4>
          <p class="post-content-preview">{{ post.post.content }}</p>
          <div class="post-meta">
            <span>发布于: {{ post.time.create_at }}</span>
            <span>❤️ {{ post.count.likes }}</span>
            <span>💬 {{ post.count.comments }}</span>
          </div>
        </div>
        <div class="post-actions">
          <button @click="EditPost(post.post.id)" class="action-btn edit-btn">
            编辑
          </button>
          <button
            @click="DeletePost(post.post.id)"
            class="action-btn delete-btn"
          >
            删除
          </button>
        </div>
      </div>
    </div>

    <div v-else class="empty-state">
      <h2>您还没有发布过任何帖子</h2>
      <p>点击首页的“发布新帖”按钮，分享你的校园生活吧！</p>
    </div>
  </div>
</template>

<style scoped>
@import url("@/static/MyPosts.css");
</style>
