<template>
    <div id="chart" v-if="!flag">
      <apexchart type="pie" width="380" :options="chartOptions" :series="series"></apexchart>
    </div>
  
  </template>
  <script>
    import VueApexCharts from "vue-apexcharts";
    import utils from "../utils/utils";
    export default {
      components: {
        apexchart: VueApexCharts,
      },
      data() {
        return {
          flag: true,
  
  
          series: [],
  
          chartOptions: {
            chart: {
              width:380,
             
              type: 'pie',
            },
            title: {
              text: 'Best Seller Product'
            },
            labels: [],
            responsive: [{
              breakpoint: 480,
              options: {
                chart: {
                  width: 200
                },
                legend: {
                  position: 'bottom'
                }
              }
            }]
          },
  
        }
      },
      async created() {
        
          await this.getBestSell()
      },
      methods: {
        
        async getBestSell() {
          const resp = await utils.http.get("/order/getBestSell");
          if (resp && resp.status === 200) {
            const data = await resp.json();
            this.series = data.map(a => a.total);
            this.chartOptions.labels = data.map(a => a.pname);
            this.flag = false;
  
          }
        },
      }
    }
  
  
  
  </script>