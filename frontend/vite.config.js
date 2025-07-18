import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true, // 允许跨域
        rewrite: (path) => path.replace(/^\/api/, '')  // 前端请求 /api/users，实际代理到 http://localhost:8081/users
      }
    }
  }
})
