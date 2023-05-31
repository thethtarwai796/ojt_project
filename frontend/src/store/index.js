import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {},

  state: {
    loginUser: {},
    isLogin: false,
    orders:[],
    pid:0,
    cartitemcount:0,
    total:0,
  },

  getters: {
    loginUser: (state) => {
      let loginUser = sessionStorage.getItem("loginUser");
      if (loginUser != null) {
        state.loginUser = JSON.parse(loginUser);
        return JSON.parse(loginUser);
      } else {
        return state.loginUser;
      }
    },
    isLogin: (state) => {
      let isLogin = sessionStorage.getItem("isLogin");
      if (isLogin != null) {
        state.isLogin = isLogin;
        return isLogin;
      } else {
        return state.isLogin;
      }
    },
    orders:(state)=>{
      let orders=sessionStorage.getItem("orders");
      if (loginUser != null) {
        state.orders=JSON.parse(orders);
        return JSON.parse(orders);
      }
      else{
        return state.orders;
      }
      
    },
    cartitemcount:(state)=>{
      return state.orders.length;
    }
    ,
    total:(state)=>{
      return state.total;
      
    }
  },

  mutations: {
    setLoginUser(state, user) {
      sessionStorage.setItem("loginUser", JSON.stringify(user));
      sessionStorage.setItem("isLogin", true);
      state.loginUser = user;
      state.isLogin = true;
    },
    logout(state) {
      sessionStorage.removeItem("loginUser");
      sessionStorage.removeItem("isLogin");
      state.loginUser = {};
      state.isLogin = false;
    },
    removeorders(state){
      sessionStorage.removeItem("orders")
      state.orders=[]
    },
    
    setOrders(state,order){
      let productincart=state.orders.find(item=>{
        return item.product.pid===order.product.pid});
      
      if(productincart){
         productincart.qty=parseInt(order.qty);
         state.total=0;
      state.orders.forEach(order=>{
        state.total+=(order.product.price*order.qty);
      });
          return;
      }
      sessionStorage.setItem("orders",JSON.stringify(order));
      state.orders.push(order);
      state.total=0;
      state.orders.forEach(order=>{
        state.total+=(order.product.price*order.qty);
      });
      console.log(state.orders)
      

    },
    
    
    
  },
  

  actions: {},
});
