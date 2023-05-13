<template>
  <!-- SPA - Single Page Application -->
  <v-app>
    <myheading/>
    <mydrawer/>
    <v-main>
    
      <router-view class="mx-2 my-2" style="height:100%" />
    
  </v-main> 
  </v-app>
</template>

<script>
import myheading from "./components/myheading.vue";
import mydrawer from "./components/mydrawer.vue";

export default {
  name: "App",

  components: {
    myheading,
    mydrawer,
  },

  data: () => ({}),
};
</script>
