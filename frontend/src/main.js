import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import store from "./store";
import 'material-design-icons-iconfont/dist/material-design-icons.css'
// import ApexCharts from "apexcharts";
// import VueApexCharts from "vue-apexcharts"

Vue.config.productionTip = false;
// Vue.use(VueApexChart);
// Vue.component('apexcharts',VueApexCharts)

new Vue({
  router,
  vuetify,
  store,
  render: (h) => h(App),
}).$mount("#app");
