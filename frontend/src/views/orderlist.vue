<template>
  <div>
    <myheading></myheading>
    <mydrawer></mydrawer>
    <v-container>
      <v-col>
        
        <v-row>
          <v-col cols="4">
            <v-card class="rounded-x1">
              <v-row>
                <v-col cols="4" md="2" sm="4" offset="1" class="mt-5">

                  <v-icon size="60" class="rounded dark orange white--text">list
                  </v-icon>
                </v-col>
                <v-col cols="8" sm="8" md="6" offset="1" class="mt-2">
                  <v-card-item>
                    <v-card-title style="color:gray">Total Order</v-card-title>
                    <v-card-text style="font-size: larger;" class="font-weight-bold">
                      {{totalOrder}}
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
                    <v-card-title style="color:gray">Finished Orders</v-card-title>
                    <v-card-text style="font-size: larger;" class="font-weight-bold">
                      {{finishOrder}}
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

          
        </v-row>
        <v-row class="mt-10 mb-10">
          <h1>Order Lists</h1>
          
                
        </v-row>
        <v-row>
          <v-col cols="12">
            <v-card>
              <v-row>
                <v-col cols="4">
                  <v-text-field type="number" style="border:solid" v-model="searchkey" rounded
                    placeholder="Search with Order_No:"></v-text-field>
                </v-col>
                <v-col cols="2">
                  <v-icon size="30" class="grey white--text mt-5" @click="search()">search</v-icon>
                </v-col>
                <v-col cols="4">
                  <v-select rounded v-model="status" :items="statusList" item-text="name" item-value="id"
                  :rules="[(v) => !!v || 'Required']" label="Category" required 
                  v-on:change="changeRoute()" style="border:solid; width:200px"
                  >
                </v-select>
                </v-col>

              </v-row>
            </v-card>

          </v-col>
        </v-row>
        
        <v-data-table title="Today Orders" :headers="headers" :items="orderDetails" :items-per-page="10"
          class="text--20 mt-2 elevation-1">
          <template v-slot:item.actions="{ item }">
            <!-- Update Movie -->
            
              <v-icon link @click="onClickView(item)">more_vert</v-icon>
          </template>
        </v-data-table>


      </v-col>

      </v-row>

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
    name: "orderlist",
    components: { myheading, mydrawer, piechart, barchart },

    data() {
      return {
        totalOrder: 0,
        finishOrder: 0,
        pendingOrder: 0,
        orderDetails: [],
        newOrderDetails:[],
        pendingOrderDetails:[],
        finishOrderDetails:[],
        receipt: "",
        localDomain: utils.constant.localDomain,
        statusList:["All","Finished","Pending"],
        status:"",
        searchkey:"",
        headers: [
          { text: "Order Number", value: "orderNumber", sortable: true },
          { text: "Customer Name", value: "customer.cname", sortable: false },
          { text: "Total", value: "total", sortable: true },
          { text: "Ordered Date", value: "date", sortable: true },
          { text: "Status", value: "status", sortable: true },

          { text: "Details", value: "actions", sortable: false },
        ],

      };
    },

    async created() {
      await this.getTotalOrder()
      await this.getFinishOrders()
      await this.getPendingOrders()
      await this.getOrderDetails()

    },
    methods: {
      async getTotalOrder() {
        const resp = await utils.http.get("/order/getCount");
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.totalOrder = data;
          }
        }
      },
      async getFinishOrders() {
        const resp = await utils.http.get("/order/getShippedCount");
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.finishOrder = data;
          }
        }
      },
      async getPendingOrders() {
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
            this.newOrderDetails=data;
          }
        }
      },

      onClickView(item) {
        this.receipt = item;
        this.$router.push({ path: "/orderDetail/" + this.receipt.orderNumber, });

      },

      async changeRoute() {
        if(this.status=="All")
          this.orderDetails=this.newOrderDetails;
        else{
          const resp = await utils.http.get("/order/getByStatus/" +this.status);
          if (resp && resp.status === 200) {
            const data = await resp.json();
            if (data) {
              this.orderDetails = data;
              console.log(data);
            }
          }
        }
    },
    
    // async search() {
    //     const resp = await utils.http.get("/order/getOneOrderList/" + this.searchkey);
    //     if (resp && resp.status === 200) {
    //       const data = await resp.json();
    //       if (data) {
    //         this.orderDetails = data;
    //         this.searchkey = "";
    //       }
    //     }

    //   },

    async search() {
      this.newOrderDetails.forEach(o => {
        if(o.orderNumber==this.searchkey){
          
          this.orderDetails=[o];
          console.log(this.orderDetails);
        }
      });
            this.searchkey = "";
      },
    }


  };
</script>