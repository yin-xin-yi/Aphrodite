import { formToJSON } from 'axios'
import { createRouter, createWebHistory } from 'vue-router'
import { GetToken } from '../utils/token'

const routes = [
  {
    path: '/RegisterAndLogin',
    name: 'RegisterAndLogin',
    component:() => import('../views/RegisterAndLogin.vue')
  },
  {
    path: '/Home',
    name: 'Home',
    component:() => import('../views/Home.vue'),
    meta: { 
      requiresAuth: true,   // 这里表示 需要认证
      title: '校园墙'
    }
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



router.beforeEach((to, from, next) => {
  const hasToken = GetToken();
  if (to.meta.requiresAuth) {
    if (hasToken) {
      next();
    } else {
      alert('请先登录！');
      next({ name: 'RegisterAndLogin' });
    }
  } else {
    if ((to.name === 'Index' || to.name === 'RegisterAndLogin') && hasToken) {
      next({ name: 'Home' });
    } else {
      next();
    }
  }
});
export default router
