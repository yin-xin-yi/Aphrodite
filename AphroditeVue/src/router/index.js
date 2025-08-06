import { createRouter, createWebHistory } from 'vue-router'
import { GetToken } from '@/utils/auth'

const routes = [
  {
    path: '/RegisterAndLogin',
    name: 'RegisterAndLogin',
    component:() => import('@/views/RegisterAndLogin.vue')
  },
  {
    path: '/Home',
    name: 'Home',
    component: () => import('@/views/Home.vue'),
    redirect: '/Home/PostFeed',
    children: [
      {
        path: 'PostFeed',
        name: 'PostFeed',
        component: () => import('@/views/component/PostFeed.vue')
      },
      {
        path: 'MyPosts',
        name: 'MyPosts',
        component: () => import('@/views/component/MyPosts.vue')
      },
      {
        path: 'UserProfile', 
        name: 'UserProfile',
        component: () => import('@/views/component/UserProfile.vue')
      },
      {
        path: 'posts/:id', 
        name: 'PostDetail',
        component: () => import('@/views/component/PostDetail.vue'), 
        props: true // 允许将路由参数 :id 作为 props 传递给 PostDetail 组件
      }
    ]
  },
  {
    path: '/', 
    name: 'Index',
    component: () => import('@/views/Index.vue'),
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