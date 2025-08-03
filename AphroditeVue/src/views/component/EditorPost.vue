<script setup>
import { ref } from 'vue';
import { CreatePost } from '@/api/post.js';
import { GetUserId } from '@/utils/auth';
import {PostStatus} from '@/common/PostStatus'

const emit = defineEmits(['close', 'post-created']);
const title = ref('');
const content = ref('');
const isanonymous = ref(false);
const issubmitting = ref(false);
const HandleSubmit = async () => {
    if (!title.value.trim() || !content.value.trim()) {
        alert('标题和内容不能为空！');
        return;
    }

    issubmitting.value = true;
    let userid = GetUserId();

    try {
        const postData = {
            userId: userid,
            title: title.value,
            content: content.value,
            isAnonymous: isanonymous.value,
            postStatus: PostStatus.published
        };
        const response = await CreatePost(postData);

        if (response.code && response.code === 200) { 
            emit('post-created', response);
            alert('发布成功！');
            CloseModal();
        } else {
            alert(`发布失败: ${response.message || '未知错误'}`);
        }
    } 
    catch (error) {
        console.error("发布帖子失败:", error);
        alert('发布失败，请检查网络或联系管理员');
    } 
    finally {
        issubmitting.value = false;
    }
};

const CloseModal = () => {
    emit('close');
};

</script>

<template>
    <div class="modal-overlay" @click.self="CloseModal">
        <div class="modal-content">

            <header class="modal-header">
                <h2>创作新帖子</h2>
                <button class="close-btn" @click="CloseModal">&times;</button>
            </header>
            <form @submit.prevent="HandleSubmit" class="post-form">
                <div class="form-group">
                    <label for="title">标题</label>
                    <input type="text" id="title" v-model="title" placeholder="起一个吸引人的标题吧" required>
                </div>
                <div class="form-group">
                    <label for="content">正文</label>
                    <textarea id="content" v-model="content" rows="10" placeholder="分享你的新鲜事..." required></textarea>
                </div>
                <div class="form-options">
                    <label>
                        <input type="checkbox" v-model="isanonymous">
                        匿名发布
                    </label>
                </div>
                <footer class="modal-footer">
                    <button type="button" class="btn-cancel" @click="CloseModal">取消</button>
                    <button type="submit" class="btn-submit" :disabled="issubmitting">
                        {{ issubmitting ? '发布中...' : '立即发布' }}
                    </button>
                </footer>
            </form>
        </div>
    </div>
</template>

<style scoped>
@import url('@/static/EditorPost.css');
</style>
