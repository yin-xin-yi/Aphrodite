import { defineStore } from 'pinia';

export const PostStore = defineStore('postTabs', {
  state: () => ({
    openPosts: [], 
    maxOpenTabs: 5, 
  }),
  actions: {
    addPostTab(post) {
      const existingIndex = this.openPosts.findIndex(p => p.id === post.id);
      if (existingIndex !== -1) {
        const [existingPost] = this.openPosts.splice(existingIndex, 1);
        this.openPosts.unshift(existingPost);
      } else {
        this.openPosts.unshift(post);
        if (this.openPosts.length > this.maxOpenTabs) {
          this.openPosts.pop();
        }
      }
    },
    removePostTab(postId) {
      this.openPosts = this.openPosts.filter(p => p.id !== postId);
    },
  },
});
