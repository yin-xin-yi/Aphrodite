import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'RegisterAndLogin',
    component:() => import('../views/RegisterAndLogin.vue')
  },
  // {
  //   path: '/', 
  //   name: 'Home',
  //   component: () => import('../views/Home.vue'),
  //   meta: { title: '首页 - 我的贴吧' }
  // },

]
const router = createRouter({
  history: createWebHistory(), 
  routes: routes
})
export default router
