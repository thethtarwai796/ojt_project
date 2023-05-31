<template>
  <div>
  <nav>
    <v-app-bar clipped-left class="pink white--text" height="100px" app>
      
      
        <img src="../assets/mainlolo.jpeg" style="height: 70px;border-radius: 20px;"></img>
    <h2 class="display-3">
      ShopaHolic</h2>
      

      <v-spacer></v-spacer>
     <!-- Profile -->
    <router-link v-if="isLogin" class="mx-2 navlink rounded-circle mr-10" to="/profile">
      <v-icon class="white pink--text rounded-circle" size="40">person</v-icon>
    </router-link>

    <!-- Login out -->
    <span v-if="isLogin">|</span>
    <a v-if="isLogin" class="mx-2 navlink mr-10" @click="logout()">
      <v-icon size="40" class="white--text">logout</v-icon>
    </a>
    </v-app-bar>

    
  </nav>
</div></template>
  
<script>
  export default {
    name: "myheading",

    data() {
      
      return {
        drawer: false,
        loginUser: {},
        isLogin: false,
      }
    }
    ,
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
    }
    
  };
</script>