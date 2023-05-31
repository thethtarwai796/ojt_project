<template>
  <!-- SPA - Single Page Application -->
  <v-app>
    
    <v-main class="pink lighten-5">
    
      <router-view class="mx-2 my-2" style="height:100%" />
    
  </v-main> 
  <footing></footing>
  </v-app>
</template>

<script>
import myheading from "./components/myheading.vue";
import mydrawer from "./components/mydrawer.vue";
import footing from "./components/footing.vue";


export default {
  name: "App",

  components: {
    myheading,
    mydrawer,
    footing
  },

  data: () => ({}),
};
</script>
