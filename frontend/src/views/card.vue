<template>
    <div>
        <myheading></myheading>
        <mydrawer></mydrawer>
        <h1 class="">Card Type</h1>
        
        <v-card v-for="(dessert,index) in desserts" :key="index" class="mt-5"> 
            <v-card-text>
                    {{dessert.card}}
            </v-card-text>
        </v-card>
    </div>
    
  </template>
  <script>
    import utils from "../utils/utils";
  import myheading from "../components/myheading.vue";
  import mydrawer from "../components/mydrawer.vue";
    export default {
        components: { myheading, mydrawer },
      data () {
        return {
          itemsPerPage: 5,
          desserts: [
            {
              card: 'AYA credit card 1',
            },
            {
              card: 'KBZ credit card 1',
            },
            {
              card: 'YOMA credit card 1',
            },
            
            
          ],
        }
      },
    }
  </script>