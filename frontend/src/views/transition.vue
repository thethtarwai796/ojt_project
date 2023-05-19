<template>
    <div>
        <myheading></myheading>
        <mydrawer></mydrawer>
        <h1 class="">Transition History</h1>
        <v-data-table title="Today Orders" :headers="headers" :items="desserts" :items-per-page="5"
        class="text--20 mt-2 elevation-3 display-1">
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
            { text: 'Used Card', align: 'start', value: 'card' },
            
          ],
          desserts: [
            {
        
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
              card: 'AYA credit card 1',
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
              card: 'AYA credit card 1',
            },
            {
              name: 'Frozen Yogurt',
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
              card: 'AYA credit card 1',
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
              card: 'AYA credit card 1',
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
              card: 'AYA credit card 1',
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
              card: 'AYA credit card 1',
            },
            {
              name: 'Frozen Yogurt',
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
              card: 'AYA credit card 1',
            },
            {
             
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
              card: 'AYA credit card 1',
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
              card: 'AYA credit card 1',
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
              card: 'AYA credit card 1',
            },
            {
              
              tid: 5894858493,
              name: 'Customer1',
              date: '2023-03-03',
              amount: 50000,
              card: 'AYA credit card 1',
            },
            
            
          ],
        }
      },
    }
  </script>