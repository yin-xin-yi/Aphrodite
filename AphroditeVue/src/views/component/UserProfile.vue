<script setup>
import { reactive } from "vue";
import { GetUserName, GetUserId } from "@/utils/auth.js";
import { AlterPassword,FetchEmail } from "@/api/rl";
import { ref, onMounted } from "vue";

const userProfile = reactive({
  username: GetUserName() || "",
  email: "",
  avatarUrl: `https://api.multiavatar.com/${GetUserName() || "Default"}.svg`, // 修正了 multiavatar API 地址
});

const passwordFields = reactive({
  currentPassword: "",
  newPassword: "",
  confirmPassword: "",
});

const handleProfileUpdate = async () => {
  if (!userProfile.username) {
    alert("用户名不能为空！");
    return;
  }

  try {
    alert("保存成功")
  } catch (error) {
    console.error("更新个人信息失败:", error);
    alert("更新失败，请稍后重试。");
  }
};

const handlePasswordChange = async () => {
  if (
    !passwordFields.currentPassword ||
    !passwordFields.newPassword ||
    !passwordFields.confirmPassword
  ) {
    alert("所有密码字段都不能为空！");
    return;
  }
  if (passwordFields.newPassword !== passwordFields.confirmPassword) {
    alert("两次输入的新密码不一致！");
    return;
  }
  if (passwordFields.newPassword === passwordFields.currentPassword) {
    alert("新密码不能与当前密码相同！");
    return;
  }

  try {
    const userId = GetUserId();
    if (!userId) {
      alert("无法获取用户ID，请重新登录");
      return;
    }

    const alterForm = {
      userId: userId,
      currentPassword: passwordFields.currentPassword,
      newPassword: passwordFields.newPassword,
    };

    const response = await AlterPassword(alterForm);
    console.log(response);
    if (response) {
      alert(response);
      Object.keys(passwordFields).forEach((key) => (passwordFields[key] = ""));
    } else {
      alert("密码修改成功，但响应内容异常，请联系管理员。");
      console.warn("后端响应成功，但内容非预期:", response);
    }
  } catch (error) {
    console.error("修改密码失败:", error);
    let errorMessage = "修改密码失败，请稍后重试。";
    if (error.response && error.respoFfnse.data) {
      errorMessage = error.response.data;
    }
    alert(errorMessage);
  }
};

onMounted(async () => {
  const response = await FetchEmail();
  if (response && response) {
    userProfile.email = response;
  }
});

</script>

<template>F
  <div class="profile-panel">
    <div class="profile-section">
      <h3>个人资料</h3>
      <form @submit.prevent="handleProfileUpdate" class="profile-form">
        <div class="form-item avatar-item">
          <label>当前头像</label>
          <img :src="userProfile.avatarUrl" alt="User Avatar" class="profile-avatar" />
          <button type="button" class="change-avatar-btn">更换头像</button>
        </div>
        <div class="form-item">
          <label for="username">用户名</label>
          <input id="username" type="text" v-model="userProfile.username" placeholder="输入你的新用户名" />
        </div>
        <div class="form-item">
          <label for="email">电子邮箱</label>
          <input id="email" type="email" v-model="userProfile.email" placeholder="输入你的电子邮箱" />
        </div>
        <button type="submit" class="submit-btn">保存更改</button>
      </form>
    </div>

    <div class="profile-section">
      <h3>修改密码</h3>
      <form @submit.prevent="handlePasswordChange" class="profile-form">
        <div class="form-item">
          <label for="current-password">当前密码</label>
          <input id="current-password" type="password" v-model="passwordFields.currentPassword" placeholder="输入当前密码" />
        </div>
        <div class="form-item">
          <label for="new-password">新密码</label>
          <input id="new-password" type="password" v-model="passwordFields.newPassword" placeholder="输入新密码" />
        </div>
        <div class="form-item">
          <label for="confirm-password">确认新密码</label>
          <input id="confirm-password" type="password" v-model="passwordFields.confirmPassword" placeholder="再次输入新密码" />
        </div>
        <button type="submit" class="submit-btn">确认修改密码</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
@import url("@/static/UserProfile");
</style>
