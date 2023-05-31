<template>
  <v-app-bar clipped-left class="pink white--text" height="100px" app>

    <img src="../assets/mainlolo.jpeg" style="height: 70px;border-radius: 20px;"></img>
    <h2 class="display-3">
      ShopaHolic</h2>
      
        
     
    


    <v-spacer></v-spacer>

    <!-- Home -->
    <router-link class="mx-2 navlink mr-10" to="/">
      <v-icon size="40" class="white--text">home</v-icon>
    </router-link>

    <!-- Cart -->
    |
    
      <v-icon size="40" class="pink white--text mr-10" @click="onClickCart()">shopping_cart</v-icon>   

    <!-- Order History -->
    |
    
      <v-icon size="40" class="white--text mr-10" @click="onClickHistory()">history_edu</v-icon>
   

    <!-- Register and login -->
    <span v-if="!isLogin">|</span>
    <router-link v-if="!isLogin" class="mx-2 navlink mr-10" to="/login">
      <v-btn><v-icon class="whitesmoke pink--text">person</v-icon>Sign in</v-btn>
    </router-link>

  
    <!-- Profile -->
    <span v-if="isLogin">|</span>
    <router-link v-if="isLogin" class="mx-2 navlink rounded-circle mr-10" to="/profile">
      <v-icon class="white pink--text rounded-circle" size="40">person</v-icon>
    </router-link>

    <!-- Login out -->
    <span v-if="isLogin">|</span>
    <a v-if="isLogin" class="mx-2 navlink mr-10" @click="logout()">
      <v-icon size="40" class="white--text">logout</v-icon>
    </a>
  </v-app-bar>
</template>

<script>
  export default {
    name: "heading",

    data: () => ({
      loginUser: {},
      isLogin: false,
    }),

    created() {
      // Login User
      this.loginUser = this.$store.state.loginUser;
      this.$store.watch(
        () => {
          return this.$store.state.loginUser;
        },
        (newVal, oldVal) => {
          this.loginUser = newVal;
        },
        {
          deep: true,
        }
      );

      // IsLogin
      this.isLogin = this.$store.state.isLogin;
      this.$store.watch(
        () => {
          return this.$store.state.isLogin;
        },
        (newVal, oldVal) => {
          this.isLogin = newVal;
        },
        {
          deep: true,
        }
      );
    },

    methods: {
      logout() {
        this.$store.commit("logout");
        // If current path is not home page, Go to home page
        if (this.$route.path != "/") {
          this.$router.push({ path: "/" });
        }
      },
      onClickCart(){
         if(this.isLogin==false)
         this.$router.push({path:"/login"})
         else this.$router.push({ path: "/cartlist" });
      },
      onClickHistory(){
         if(this.isLogin==false)
         this.$router.push({path:"/login"})
         else this.$router.push({ path: "/userorderhistory" });
      }
    },
    // computed:{
    //   cartItemCount(){
    //     console.log(this.$store.state.cartitemcount);
    //     return this.$store.state.cartitemcount;
    //   }
    // }
      
    
    
  };
</script>

<style scoped>
  .navlink {
    color: white !important;
    text-decoration: none;
  }

  .navlink:hover {
    cursor: pointer;
    text-decoration: underline;
  }

  .navtitle {
    color: white !important;
    text-decoration: none;
    cursor: pointer !important;
  }
  
</style>