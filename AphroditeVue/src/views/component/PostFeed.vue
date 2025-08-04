<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { FetchAllPosts } from "@/api/post.js";
import { TimeFormat } from "@/utils/timeformat.js";
import {MapPostToCardData} from '@/utils/map'

const router = useRouter();
const posts = ref([]);
const isloading = ref(true);


const GoToPostDetail = (postId) => {
  console.log(`准备跳转到帖子详情页,ID: ${postId}`);
};

onMounted(async () => {
  try {
    const postsArray = await FetchAllPosts();
    if (Array.isArray(postsArray)) {
      if (postsArray.length > 0) {
        posts.value = postsArray.map(MapPostToCardData);
      } else {
        posts.value = [];
        console.log("获取成功，但帖子列表为空");
      }
    } else {
      console.error("获取帖子失败: API返回的数据格式不正确", postsArray);
    }
  } catch (error) {
    console.error("获取帖子失败:", error.response.data);
  } finally {
    isloading.value = false;
  }
});
</script>

<template>
  <div v-if="isloading" class="loading-state">
    <p>正在努力加载动态...</p>
  </div>

  <div v-else class="post-feed">
    <div
      v-for="post in posts"
      :key="post.post.id"
      class="post-card"
      @click="GoToPostDetail(post.post.id)"
    >
      <div class="post-header">
        <img
          :src="post.author.avatar"
          alt="author avatar"
          class="author-avatar"
        />
        <div class="author-info">
          <span class="author-name">{{ post.author.username }}</span>
          <span class="post-timestamp">{{ post.time.create_at }}</span>
        </div>
      </div>

      <div class="post-body">
        <h3 v-if="post.post.title" class="post-title">
          {{ post.post.title }}
        </h3>
        <p>{{ post.post.content }}</p>
      </div>

      <div class="post-footer">
        <button class="action-btn">❤️ {{ post.count.likes }}</button>
        <button class="action-btn">💬 {{ post.count.comments }}</button>
        <button class="action-btn">👀 {{ post.count.views }}</button>
        <button class="action-btn">🔗 分享</button>
      </div>
    </div>

    <div v-if="posts.length === 0" class="empty-state">
      <h2>空空如也</h2>
      <p>还没有人发布动态，快来成为第一个吧！</p>
    </div>
  </div>
</template>

<style scoped>
@import url("@/static/PostFeed.css");
</style>
