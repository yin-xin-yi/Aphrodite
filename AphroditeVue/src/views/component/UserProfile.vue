<script setup>
import { reactive } from 'vue';
import { GetUserName } from '@/utils/auth.js';
// 假设你有一个更新用户信息的API
// import { UpdateUserProfile } from '@/api/user.js';

// 使用 reactive 来管理用户的表单数据
const userProfile = reactive({
  username: GetUserName() || '',
  email: 'user@example.com', // 应该从API获取
  avatarUrl: `https://api.multiavatar.com/${GetUserName() || 'Default'}.svg`,
});

const passwordFields = reactive({
    currentPassword: '',
    newPassword: '',
    confirmPassword: ''
});

const handleProfileUpdate = async () => {
  // 1. 表单验证
  if (!userProfile.username) {
    alert('用户名不能为空！');
    return;
  }

  // 2. 调用API更新信息
  try {
    console.log('准备更新用户信息:', { username: userProfile.username, email: userProfile.email });
    // await UpdateUserProfile({ username: userProfile.username, email: userProfile.email });
    alert('个人信息更新成功！（模拟）');
  } catch (error) {
    console.error("更新个人信息失败:", error);
    alert('更新失败，请稍后重试。');
  }
};

const handlePasswordChange = async () => {
    // 1. 表单验证
    if(!passwordFields.currentPassword || !passwordFields.newPassword) {
        alert('当前密码和新密码不能为空！');
        return;
    }
    if(passwordFields.newPassword !== passwordFields.confirmPassword) {
        alert('两次输入的新密码不一致！');
        return;
    }

    // 2. 调用API修改密码
    try {
        console.log('准备修改密码...');
        // await ChangePassword(passwordFields);
        alert('密码修改成功！（模拟）');
        // 清空密码输入框
        Object.keys(passwordFields).forEach(key => passwordFields[key] = '');
    } catch (error) {
        console.error("修改密码失败:", error);
        alert('修改密码失败，请检查当前密码是否正确。');
    }
}
</script>

<template>
  <div class="profile-panel">
    <!-- 个人资料部分 -->
    <div class="profile-section">
      <h3>个人资料</h3>
      <form @submit.prevent="handleProfileUpdate" class="profile-form">
        <div class="form-item avatar-item">
          <label>当前头像</label>
          <img :src="userProfile.avatarUrl" alt="User Avatar" class="profile-avatar">
          <button type="button" class="change-avatar-btn">更换头像</button>
        </div>
        <div class="form-item">
          <label for="username">用户名</label>
          <input id="username" type="text" v-model="userProfile.username" placeholder="输入你的新用户名">
        </div>
        <div class="form-item">
          <label for="email">电子邮箱</label>
          <input id="email" type="email" v-model="userProfile.email" placeholder="输入你的电子邮箱">
        </div>
        <button type="submit" class="submit-btn">保存更改</button>
      </form>
    </div>

    <!-- 修改密码部分 -->
    <div class="profile-section">
        <h3>修改密码</h3>
        <form @submit.prevent="handlePasswordChange" class="profile-form">
            <div class="form-item">
                <label for="current-password">当前密码</label>
                <input id="current-password" type="password" v-model="passwordFields.currentPassword" placeholder="输入当前密码">
            </div>
            <div class="form-item">
                <label for="new-password">新密码</label>
                <input id="new-password" type="password" v-model="passwordFields.newPassword" placeholder="输入新密码">
            </div>
            <div class="form-item">
                <label for="confirm-password">确认新密码</label>
                <input id="confirm-password" type="password" v-model="passwordFields.confirmPassword" placeholder="再次输入新密码">
            </div>
            <button type="submit" class="submit-btn">确认修改密码</button>
        </form>
    </div>
  </div>
</template>

<style scoped>
@import url("@/static/UserProfile");
</style>
