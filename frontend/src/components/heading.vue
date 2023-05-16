<template>
  <v-app-bar clipped-left class="pink white--text" app>


    <h2 class="display-3">
      ShopaHolic</h2>
      
        
     
    


    <v-spacer></v-spacer>

    <!-- Home -->
    <router-link class="mx-2 navlink mr-10" to="/"><v-icon size="40" class="white--text">home</v-icon></router-link>

    <!-- Cart -->
    |
    <router-link class="mx-2 navlink mr-10" to="/">
      <v-icon size="40" class="pink white--text">shopping_cart</v-icon>
      <v-span class="black m-5 rounded-pill">1</v-span>
    </router-link>

    <!-- Order History -->
    |
    <router-link class="mx-2 navlink mr-10" style="border-right: black" to="/"><v-icon size="40" class="white--text">history_edu</v-icon></router-link>

    <!-- Register and login -->
    <span v-if="!isLogin">|</span>
    <router-link v-if="!isLogin" class="mx-2 navlink mr-10" to="/register">
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
    },
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