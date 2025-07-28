<script setup>
import { ref } from 'vue';
import { CreatePost } from '@/api/post.js';
import { GetUserId } from '@/utils/auth';

const emit = defineEmits(['close', 'post-created']);

const title = ref('');
const content = ref('');
const isAnonymous = ref(false);
const isSubmitting = ref(false);

const HandleSubmit = async () => {
    if (!title.value.trim() || !content.value.trim()) {
        alert('标题和内容不能为空！');
        return;
    }

    isSubmitting.value = true;
    let userid = GetUserId();


    try {
        const postData = {
            userid: userid,
            title: title.value,
            content: content.value,
            isAnonymous: isAnonymous.value
        };
        const response = await CreatePost(postData);

        if (response.code === 201) { 
            emit('post-created', response.data);
            alert('发布成功！');
            closeModal();
        } else {
            alert(`发布失败: ${response.message || '未知错误'}`);
        }
    } catch (error) {
        console.error("发布帖子失败:", error);
        alert('发布失败，请检查网络或联系管理员。');
    } finally {
        isSubmitting.value = false;
    }
};

const closeModal = () => {
    emit('close');
};
</script>

<template>
    <div class="modal-overlay" @click.self="closeModal">
        <div class="modal-content">
            <header class="modal-header">
                <h2>创作新帖子</h2>
                <button class="close-btn" @click="closeModal">&times;</button>
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
                        <input type="checkbox" v-model="isAnonymous">
                        匿名发布
                    </label>
                </div>
                <footer class="modal-footer">
                    <button type="button" class="btn-cancel" @click="closeModal">取消</button>
                    <button type="submit" class="btn-submit" :disabled="isSubmitting">
                        {{ isSubmitting ? '发布中...' : '立即发布' }}
                    </button>
                </footer>
            </form>
        </div>
    </div>
</template>

<style scoped>
:root {
    --soft-pink: #F7CAC9;
    --soft-blue: #92A8D1;
    --accent-pink: #C7758A;
    --text-dark: #4A4453;
    --text-light: #8D839C;
    --glass-bg: rgba(255, 255, 255, 0.25);
    --glass-border: rgba(255, 255, 255, 0.4);
}

.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
    backdrop-filter: blur(5px);
}

.modal-content {
    background: var(--glass-bg, rgba(255, 255, 255, 0.25));
    backdrop-filter: blur(15px);
    border: 1px solid var(--glass-border, rgba(255, 255, 255, 0.4));
    border-radius: 15px;
    padding: 30px;
    width: 90%;
    max-width: 600px;
    box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
    color: var(--text-dark, #4A4453);
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    border-bottom: 1px solid var(--glass-border, rgba(255, 255, 255, 0.4));
    padding-bottom: 15px;
}

.modal-header h2 {
    margin: 0;
    font-weight: 400;
}

.close-btn {
    background: none;
    border: none;
    font-size: 24px;
    cursor: pointer;
    color: var(--text-dark, #4A4453);
}

.post-form .form-group {
    margin-bottom: 20px;
}

.post-form label {
    display: block;
    margin-bottom: 8px;
    font-weight: bold;
}

.post-form input[type="text"],
.post-form textarea {
    width: 100%;
    padding: 12px;
    border-radius: 8px;
    border: 1px solid var(--glass-border, rgba(255, 255, 255, 0.4));
    background-color: rgba(255, 255, 255, 0.5);
    color: var(--text-dark, #4A4453);
    box-sizing: border-box; /* 确保 padding 不会撑大宽度 */
}

.post-form input[type="text"]:focus,
.post-form textarea:focus {
    outline: none;
    border-color: var(--accent-pink, #C7758A);
    box-shadow: 0 0 0 2px rgba(199, 117, 138, 0.5);
}

.form-options {
    margin-bottom: 20px;
    display: flex;
    align-items: center;
}

.form-options label {
    display: flex;
    align-items: center;
    cursor: pointer;
}

.form-options input[type="checkbox"] {
    margin-right: 8px;
}

.modal-footer {
    display: flex;
    justify-content: flex-end;
    gap: 15px;
}

.btn-cancel, .btn-submit {
    padding: 10px 20px;
    border-radius: 8px;
    border: none;
    cursor: pointer;
    font-weight: bold;
    transition: all 0.3s ease;
}

.btn-cancel {
    background-color: #f0f0f0;
    color: #333;
}
.btn-cancel:hover {
    background-color: #e0e0e0;
}

.btn-submit {
    background-color: var(--accent-pink, #C7758A);
    color: white;
}
.btn-submit:hover:not(:disabled) {
    transform: translateY(-2px);
    box-shadow: 0 4px 15px rgba(199, 117, 138, 0.4);
}
.btn-submit:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
}
</style>
