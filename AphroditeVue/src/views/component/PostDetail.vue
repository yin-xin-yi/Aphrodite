<!-- src/views/component/PostDetail.vue -->
<template>
  <div class="post-detail-container">
    <div v-if="post" class="post-content-wrapper">
      <!-- 帖子头部：作者信息和时间 -->
      <div class="post-header">
        <img :src="post.authorAvatar" alt="作者头像" class="author-avatar" />
        <div class="author-info">
          <span class="author-name">{{ post.authorName }}</span>
          <span class="post-time">{{ formatTime(post.createdAt) }}</span>
        </div>
      </div>

      <!-- 帖子标题 -->
      <h1 class="post-title">{{ post.title }}</h1>

      <!-- 帖子正文内容 -->
      <div class="post-content" v-html="post.content"></div>

      <!-- 帖子互动操作区 -->
      <div class="post-actions">
        <button @click="handleLike" :class="{ 'liked-btn': post.isLiked }">
          <i class="fas fa-heart"></i> {{ post.likeCount }}
        </button>
        <button>
          <i class="fas fa-comment"></i> {{ post.commentCount }}
        </button>
        <button>
          <i class="fas fa-bookmark"></i> {{ post.bookmarkCount }}
        </button>
        <button>
          <i class="fas fa-share-alt"></i> 分享
        </button>
      </div>

      <!-- 评论区已移除 -->

    </div>
    <div v-else class="loading-state">
      <p v-if="loading">帖子加载中...</p>
      <p v-else>帖子未找到或加载失败。</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import { usePostTabsStore } from '@/stores/postTabsStore'; 
import { getPostDetail, ClickLike } from '@/api/post'; 

// 评论区组件已移除，所以不再需要导入 CommentSection
// import CommentSection from '@/components/CommentSection.vue'; // <-- 这行已移除

const route = useRoute();
const post = ref(null);
const loading = ref(true); 
const postTabsStore = usePostTabsStore();

const postId = ref(route.params.id);

const fetchPost = async (id) => {
  loading.value = true;
  post.value = null; 
  try {
    const response = await getPostDetail(id); // 调用后端 API 获取帖子详情
    post.value = response.data;
    // 将当前帖子添加到全局的 openPosts 列表中
    postTabsStore.addPostTab({ id: post.value.id, title: post.value.title });
  } catch (error) {
    console.error('获取帖子详情失败:', error);
    post.value = null; 
  } finally {
    loading.value = false; 
  }
};

onMounted(() => {
  fetchPost(postId.value);
});

watch(() => route.params.id, (newId) => {
  if (newId && newId !== postId.value) {
    postId.value = newId; 
    fetchPost(newId);
  }
});

const handleLike = async () => {
  if (!post.value) return;
  try {
    const response = await ClickLike(post.value.id);
    post.value.likeCount = response.likeCount;
    post.value.isLiked = response.liked;
  } catch (error) {
    console.error('点赞失败:', error);
  }
};

const formatTime = (isoString) => {
    const date = new Date(isoString);
    return date.toLocaleString();
};
</script>

<style scoped>
.post-detail-container {
  background-color: #fff;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  min-height: 500px; 
  display: flex;
  flex-direction: column;
}

.loading-state {
  text-align: center;
  padding: 50px;
  color: #888;
  font-size: 1.2em;
}

.post-header {
  display: flex;
  align-items: center;
  margin-bottom: 25px;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
}

.author-avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin-right: 15px;
  object-fit: cover;
  border: 2px solid #f0f0f0;
}

.author-info {
  display: flex;
  flex-direction: column;
}

.author-name {
  font-weight: bold;
  color: #333;
  font-size: 1.2em;
}

.post-time {
  font-size: 0.9em;
  color: #777;
  margin-top: 5px;
}

.post-title {
  font-size: 2.5em;
  font-weight: 700;
  color: #222;
  margin-bottom: 30px;
  line-height: 1.3;
  word-break: break-word; /* 确保长标题能换行 */
}

.post-content {
  font-size: 1.15em;
  line-height: 1.8;
  color: #444;
  margin-bottom: 40px; /* 调整底部间距，因为评论区没了 */
  white-space: pre-wrap; /* 保留文本中的换行和空格 */
  word-wrap: break-word; /* 自动换行 */
}

/* 针对 v-html 渲染的图片和表格等内容进行基本样式重置 */
.post-content :deep(img) {
  max-width: 100%;
  height: auto;
  display: block;
  margin: 15px 0;
  border-radius: 4px;
}
.post-content :deep(p) {
  margin-bottom: 1em;
}
.post-content :deep(h1), .post-content :deep(h2), .post-content :deep(h3) {
  margin-top: 1.5em;
  margin-bottom: 0.8em;
  color: #333;
}
.post-content :deep(ul), .post-content :deep(ol) {
  margin-left: 20px;
  margin-bottom: 1em;
}
.post-content :deep(blockquote) {
  border-left: 4px solid #ccc;
  padding-left: 10px;
  color: #666;
  margin: 1em 0;
}

.post-actions {
  display: flex;
  gap: 30px;
  padding: 20px 0;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
  margin-bottom: 30px; 
}

.post-actions button {
  background: none;
  border: none;
  color: #666;
  cursor: pointer;
  font-size: 1.1em;
  display: flex;
  align-items: center;
  gap: 10px;
  transition: color 0.2s, transform 0.2s;
}

.post-actions button:hover {
  color: #007bff;
  transform: translateY(-2px);
}

.post-actions .liked-btn {
  color: #ff4d4f; /* 点赞后的醒目红色 */
}
</style>
