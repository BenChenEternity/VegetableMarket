import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from '@/layout'
import Cookies from 'js-cookie'
import axios from 'axios'

const userLevel = parseInt(Cookies.get('level'));

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/register',
    component: () => import('@/views/register/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/usermgmt',
    children: [{
      path: 'usermgmt',
      name: '用户管理',
      component: () => import('@/views/usermgmt/index'),
      meta: { title: '用户管理', icon: 'user' }
    }],
    hidden: userLevel < 3
  },

  {
    path: '/',
    component: Layout,
    redirect: '/goods',
    children: [{
      path: 'goods',
      name: '商品',
      component: () => import('@/views/goods/index'),
      meta: { title: '商品', icon: 'dashboard' }
    }]
  },

  {
    path: '/',
    component: Layout,
    children: [
      {
        path: 'myinfo',
        name: '个人信息',
        component: () => import('@/views/my-info/index'),
        meta: { title: '个人信息', icon: 'form' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes,
  base: 'vegetable'
})

const router = createRouter()
router.beforeEach(async (to, from, next) =>
{
  if (to.path === '/login' || to.path === '/register')
  {
    next();
  } else
  {
    const userId = Cookies.get('userId');
    const token = Cookies.get('token');
    const isValid = userId && token;

    if (!isValid)
    {
      console.log('没登录');
      next('/login');
    } else
    {
      try
      {
        const response = await axios.post('https://ajie.cool/api/check', {}, {
          headers: {
            'user-id': userId,
            'session-token': token,
          },
        });
        const isValidUser = response.data;
        if (!isValidUser)
        {
          console.log('用户身份验证失败');
          next('/login');
        } else
        {
          next();
        }
      } catch (error)
      {
        console.error('发生错误', error);
        next('/login');
        return;
      }
    }
  }
})

export function resetRouter()
{
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
