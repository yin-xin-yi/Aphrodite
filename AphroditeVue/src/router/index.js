import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/RegisterAndLogin',
    name: 'RegisterAndLogin',
    component:() => import('../views/RegisterAndLogin.vue')
  },
  {
    path: '/Home',
    name: 'Home',
    component:() => import('../views/Home.vue')
  },
  {
    path: '/', 
    name: 'Index',
    component: () => import('../views/Index.vue'),
  },

]
const router = createRouter({
  history: createWebHistory(), 
  routes: routes
})
export default router
