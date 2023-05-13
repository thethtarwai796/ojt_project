<template>
  <div>
  <nav>
    <v-app-bar clipped-left class="pink white--text" app>
      
      <v-app-bar-title>
        <h2 class="display-3">
          ShopaHolic</h2>
      </v-app-bar-title>

      <v-spacer></v-spacer>
      <v-btn>Log out</v-btn>




    </v-app-bar>

    
  </nav>
</div></template>
  
<script>
  export default {
    name: "myheading",

    data() {
      return {
        drawer: false
      }
    }
    ,
  };
</script>