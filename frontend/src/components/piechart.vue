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
            text: 'Total Sale By Category'
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
      await this.getCategory(),
        await this.getTotalSale()
    },
    methods: {
      async getCategory() {
        const resp = await utils.http.get("/category/getAll");
        if (resp && resp.status === 200) {
          const data = await resp.json();
          this.chartOptions.labels = data.map(a => a.name);
        }
      },
      async getTotalSale() {
        const resp = await utils.http.get("/order/getTotalSaleByCategory");
        if (resp && resp.status === 200) {
          const data = await resp.json();
          this.series = data.map(a => a);
          this.flag = false;

        }
      },
    }
  }



</script>