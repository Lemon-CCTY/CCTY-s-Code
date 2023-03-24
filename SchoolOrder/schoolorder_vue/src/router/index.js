import Vue from 'vue'
import Router from 'vue-router'

import login from '@/views/login'

Vue.use(Router)
// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/login',
      name: '登录',
      component: login
    },
    {
      path: '/register',
      name: '注册',
      component: () => import('@/views/register'),
    },
    {
      path: '/',
      name: 'Manage',
      component: () => import('@/views/background/Manage'),
      redirect: "/login",
      meta: {title: '首页'},
      children: [
        {
          path: '/file',
          name: '文件管理',
          meta: {title: '文件管理'},
          component: () => import('@/views/File'),
        },
        ,{//children表子路由
          path: 'Home',
          name: 'Home', meta: {title: '数据显示'},
          component: () => import('@/views/background/back_Home')
        },
        {
          path: 'consumer_manage',
          name: '消费者管理',
          meta: {title: '消费者管理'},
          component: () => import('@/views/background/consumer_manage'),
          children: [{
            path: 'order_manage',
            name: '评价管理',
            meta: {title: '评价管理'},
            component: () => import('@/views/background/order_manage'),
          }]
        },
        {
          path: 'order_manage',
          name: '订单管理',
          meta: {title: '订单管理'},
          component: () => import('@/views/background/order_manage')
        },
        {
          path: 'hongyi_goods',
          name: '弘毅园',
          meta: {title: '弘毅园'},
          component: () => import('@/views/background/hongyi_goods')
        },  {
          path: 'kaiwu_goods',
          name: '开物园',
          meta: {title: '开物园'},
          component: () => import('@/views/background/kaiwu_goods')
        },
        {
          path: 'sellers_unaudited',
          name: '未审核商家',
          meta: {title: '未审核商家'},
          component: () => import('@/views/background/sellers_unaudited')
        }, {
          path: 'seller_approved',
          name: '已审核商家',
          meta: {title: '已审核商家'},
          component: () => import('@/views/background/seller_approved')
        },
        {
          path: 'evaluations_manage',
          name: '评价管理',
          meta: {title: '评价管理'},
          component: () => import('@/views/background/evaluations_manage')
        },
      ]
    },

  ]
})
