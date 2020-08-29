import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/pages/index'
import Article from '@/pages/b-article'
import Mine from '@/pages/mine'
import Categories from '@/pages/categories'
Vue.use(Router)
Vue.use(Article)
Vue.use(Mine)
Vue.use(Categories)
const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};
export default new Router({
  // mode:'history',
  routes: [
    {
      path: '/',
      name: 'index',
      component: Index
    },
    {
      path: '/bkl',
      name: 'bkl',
      component: Index
    },
    {
      path: '/focus',
      name: 'focus',
      component: Index
    },
    {
      path: '/baq',
      name: 'baq',
      component: Index
    },
    {
      path: '/bsf',
      name: 'bsf',
      component: Index
    },
    {
      path: '/bws',
      name: 'bws',
      component: Index
    },
    {
      path:'/article/:id',
      name:'article',
      component:Article
    },
    
    {
      path:'/user/:id',
      name:'mine',
      component:Mine
    },
   
    {
      path:'/cgies',
      name:'categories',
      component:Categories
    }
  ]
})
