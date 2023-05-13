<template>
  <div>
    <canvas ref="barChart"></canvas>
  </div>
</template>

<script>
import Chart from "chart.js";

export default {
  name: "SaleStatisticsBarChart",
  props: {
    data: {
      type: Array,
      required: true,
    },
  },
  mounted() {
    this.renderChart();
  },
  methods: {
    renderChart() {
      const ctx = this.$refs.barChart.getContext("2d");
      new Chart(ctx, {
        type: "bar",
        data: {
          labels: this.data.map((d) => d.label),
          datasets: [
            {
              label: "Sales",
              data: this.data.map((d) => d.value),
              backgroundColor: "#2196F3",
            },
          ],
        },
        options: {
          legend: {
            display: false,
          },
          scales: {
            yAxes: [
              {
                ticks: {
                  beginAtZero: true,
                },
              },
            ],
          },
        },
      });
    },
  },
};
</script>

<style scoped>
canvas {
  max-width: 100%;
}
</style>

