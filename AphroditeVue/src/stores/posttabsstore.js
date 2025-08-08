import { defineStore } from 'pinia';
import { ref } from 'vue';

export const UsePostTabsStore = defineStore('postTabs', () => {
  const OpenedTabs = ref([]);
  const ActiveTabId = ref(null);

  function AddPostTab(post) {
    const exists = OpenedTabs.value.some(tab => String(tab.id) === String(post.id));
    if (!exists) {
      OpenedTabs.value.push({
        id: String(post.id),
        title: post.title || `帖子 ${post.id}`,
      });
    }
    ActiveTabId.value = String(post.id);
  }
  function RemoveTab(postId) {
    const index = OpenedTabs.value.findIndex(tab => String(tab.id) === String(postId));
    if (index !== -1) {
      OpenedTabs.value.splice(index, 1);
      if (ActiveTabId.value === String(postId) && OpenedTabs.value.length > 0) {
        ActiveTabId.value = OpenedTabs.value[OpenedTabs.value.length - 1].id;
      } else if (OpenedTabs.value.length === 0) {
        ActiveTabId.value = null;
      }
    }
  }

  return {
    OpenedTabs,
    ActiveTabId,
    AddPostTab,
    RemoveTab,
  };
});
