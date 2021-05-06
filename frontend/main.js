import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false
Vue.prototype.serverUrl="http://localhost:8080/mgs"
// Vue.prototype.serverUrl="http://47.93.1.66:8080/mgs"




App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
