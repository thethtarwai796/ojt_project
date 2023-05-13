<template>
    <div id="chart" v-if="!flag" >
        <apexchart  type="bar" height="350" :options="chartOptions" :series="series"></apexchart>
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
            return{
          flag:true,
          series: [{
            name: 'Total',
            data: []
          }],
          chartOptions: {
            chart: {
              height: 350,
              type: 'bar',
            },
            plotOptions: {
              bar: {
                borderRadius: 10,
                dataLabels: {
                  position: 'top', // top, center, bottom
                },
              }
            },
            dataLabels: {
              enabled: true,
              formatter: function (val) {
                return val + "MMK";
              },
              offsetY: -20,
              style: {
                fontSize: '12px',
                colors: ["#304758"]
              }
            },
            
            xaxis: {
              categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
              position: 'top',
              axisBorder: {
                show: false
              },
              axisTicks: {
                show: false
              },
              crosshairs: {
                fill: {
                  type: 'gradient',
                  gradient: {
                    colorFrom: '#D8E3F0',
                    colorTo: '#BED1E6',
                    stops: [0, 100],
                    opacityFrom: 0.4,
                    opacityTo: 0.5,
                  }
                }
              },
              tooltip: {
                enabled: true,
              }
            },
            yaxis: {
              axisBorder: {
                show: false
              },
              axisTicks: {
                show: false,
              },
              labels: {
                show: false,
                formatter: function (val) {
                  return val + "MMK";
                }
              }
            
            },
            title: {
              text: 'Monthly Sale , 2023',
              floating: true,
              offsetY: 330,
              align: 'center',
              style: {
                color: '#444'
              }
            }
          },
          
          
        }
        },
        async created() {
           await this.getTotalSaleByMonth()
          },
    methods: {
      
      async getTotalSaleByMonth() {
        const resp = await utils.http.get("/order/getTotalSaleByMonth");
        if (resp && resp.status === 200) {
          const result = await resp.json(); 
         
          this.series[0].data = result.map(a => a);
          this.flag = false;

        }
      },
    },
    
  }

</script>