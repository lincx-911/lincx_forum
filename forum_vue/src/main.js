// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import axios from 'axios'
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import bHeader from './components/b-header'
import bAside from './components/b-aside'
import bPublic from './components/b-public'
import bButton from './components/b-button'
import bEditor from './components/b-editor'
import bLogin from './components/b-login'

import bRegister from './components/b-register'
import bForget from './components/b-forget'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import hljs from "highlight.js";
import 'github-markdown-css/github-markdown.css';

Vue.directive('highlight', function (el) {
  const blocks = el.querySelectorAll('pre code')
  blocks.forEach(block => {
    hljs.highlightBlock(block)
  })
})

Vue.config.productionTip = false
Vue.prototype.$http = axios
Vue.use(ElementUI)
Vue.use(mavonEditor)
Vue.component("b-header",bHeader)
Vue.component("b-aside",bAside)
Vue.component("b-public",bPublic)
Vue.component('b-button',bButton)
Vue.component('b-editor',bEditor)
Vue.component('b-login',bLogin)
Vue.component('b-register',bRegister)
Vue.component('b-forget',bForget)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
Date.prototype.Format = function (fmt) {
  var o = {
      "M+": this.getMonth() + 1, //月份 
      "d+": this.getDate(), //日 
      "H+": this.getHours(), //小时 
      "m+": this.getMinutes(), //分 
      "s+": this.getSeconds(), //秒 
      "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
      "S": this.getMilliseconds() //毫秒 
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
  if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}