<template>
    <div>
        <myheading></myheading>
        <mydrawer></mydrawer>
        <h1 class="">Transition History</h1>
        <v-data-table title="Today Orders" :headers="headers" :items="desserts" :items-per-page="5"
         class="mx-auto pa-12 pb-8 elevation-3 display-1"
        elevation="8"
        max-width="448"
        rounded="lg">
        </v-data-table>
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
          headers: [
            {
              title: 'Dessert (100g serving)',
              align: 'start',
              sortable: false,
              key: 'name',
            },
            { text: 'Transition ID', align: 'start', value: 'tid' },
            { text: 'Customer Name', align: 'start', value: 'name' },
            { text: 'Date', align: 'start', value: 'date' },
            { text: 'Amount', align: 'start', value: 'amount' },
            
            
          ],
          desserts: [
            {
        
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
            },
            {
              name: 'Frozen Yogurt',
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
            },
            {
              name: 'Frozen Yogurt',
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
            },
            {
             
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
            },
            
            
          ],
        }
      },
    }
  </script>