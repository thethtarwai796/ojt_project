

<template>

  <div>
    <myheading></myheading>

        <mydrawer></mydrawer>
      
      
    <v-container>
      <v-col>
        <h1 class="mb-10">Dashboard</h1>
        <v-row>
          <v-col cols="4">
            <v-card class="rounded-x1">
              <v-row>
                <v-col cols="4" md="2" sm="4" offset="1" class="mt-5">

                  <v-icon size="60" class="rounded dark orange white--text">monetization_on
                  </v-icon>
                </v-col>
                <v-col cols="8" sm="8" md="6" offset="1" class="mt-2">
                  <v-card-item>
                    <v-card-title style="color:gray">Total Sale</v-card-title>
                    <v-card-text style="font-size: larger;" class="font-weight-bold">
                      {{totalSale}}MMK
                    </v-card-text>
                  </v-card-item>
                </v-col>
              </v-row>


            </v-card>
          </v-col>
          <v-col cols="4">
            <v-card class="rounded-x1">
              <v-row>
                <v-col cols="4" md="2" sm="4" offset="1" class="mt-5">
                  <v-icon size="60" class="rounded dark green white--text">shopping_cart
                  </v-icon>
                </v-col>
                <v-col cols="8" sm="8" md="6" offset="1" class="mt-2">
                  <v-card-item>
                    <v-card-title style="color:gray">Today Order</v-card-title>
                    <v-card-text style="font-size: larger;" class="font-weight-bold">
                      {{todayOrder}}
                    </v-card-text>
                  </v-card-item>
                </v-col>
              </v-row>


            </v-card>
          </v-col>
          <v-col cols="4">
            <v-card class="rounded-x1">
              <v-row>
                <v-col cols="4" md="2" sm="4" offset="1" class="mt-5">
                  <v-icon size="60" class="rounded dark yellow white--text">shopping_cart
                  </v-icon>
                </v-col>
                <v-col cols="8" sm="8" md="6" offset="1" class="mt-2">
                  <v-card-item>
                    <v-card-title style="color:gray">Pending Orders</v-card-title>
                    <v-card-text style="font-size: larger;" class="font-weight-bold">
                      {{pendingOrder}}
                    </v-card-text>
                  </v-card-item>
                </v-col>
              </v-row>


            </v-card>
          </v-col>

          <v-col cols="8">
            <v-card>
              <barchart></barchart>
            </v-card>

          </v-col>
          <v-col cols="4">
            <v-card style="height:100%">
              <piechart></piechart>
            </v-card>

          </v-col>
        </v-row>
        <v-data-table title="Today Orders" :headers="headers" :items="orderDetails" :items-per-page="5"
          class="text--20 mt-2 elevation-3 display-1">
          <template v-slot:item.actions="{ item }">
            <!-- Update Movie -->
            
              <v-icon link @click="onClickView(item)">more_vert</v-icon>
          </template>
        </v-data-table>


      </v-col>

     

    </v-container>
     

  </div>
</template>

<script>
  import utils from "../utils/utils";
  import myheading from "../components/myheading.vue";
  import mydrawer from "../components/mydrawer.vue";
  import piechart from "../components/piechart.vue";
  import barchart from "../components/barchart.vue";

  export default {
    name: "dashboard",
    components: { myheading, mydrawer, piechart, barchart },

    data() {
      return {
        totalSale: 0,
        todayOrder: 0,
        pendingOrder: 0,
        orderDetails: [],
        receipt: "",
        localDomain: utils.constant.localDomain,

        headers: [
          { text: "#Order Number", value: "orderNumber", sortable: true },
          { text: "Customer Name", value: "customer.cname", sortable: false },
          { text: "Total", value: "total", sortable: true },
          { text: "Ordered Date", value: "orderDate", sortable: true },
          { text: "Status", value: "status", sortable: true },

          { text: "Details", value: "actions", sortable: false },
        ],

      };
    },

    async created() {
      await this.getSale()
      await this.getToday()
      await this.getPending()
      await this.getOrderDetails()

    },
    methods: {
      async getSale() {
        const resp = await utils.http.get("/order/getTotalSale");
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.totalSale = data;
          }
        }
      },
      async getToday() {
        const resp = await utils.http.get("/order/getTodayOrder");
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.todayOrder = data;
          }
        }
      },
      async getPending() {
        const resp = await utils.http.get("/order/getPendingCount");
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.pendingOrder = data;
          }
        }
      },
      async getOrderDetails() {
        const resp = await utils.http.get("/order/getAll");
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.orderDetails = data;
          }
        }
      },

      onClickView(item) {

        // Copy Object
        //this.orderDetail = Object.assign({}, item);
        //this.$router.push({ path: "/orderDetail"});
        this.receipt = item;
        console.log(item);
        this.$router.push({ path: "/orderDetail/" + this.receipt.orderNumber, });

      },




    }


  };
</script>