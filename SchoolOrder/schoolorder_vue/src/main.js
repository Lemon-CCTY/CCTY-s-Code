// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

Vue.config.productionTip = false
//引入element-ui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI,{size:"mini"})
//粒子特效
import VueParticles from 'vue-particles'
Vue.use(VueParticles)
//全局样式设置
import './assets/globle.css'

//axios的使用
import request from "@/utils/request";
Vue.prototype.request=request



/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
