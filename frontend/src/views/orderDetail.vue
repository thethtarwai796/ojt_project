<template>
  <div>
    <myheading></myheading>
    <mydrawer></mydrawer>
    <v-container>
      <v-row>
      <v-col>
        <div>
          <!-- ORder Number -->
          <h1 class="mb-10" style="width:fit-content;">Order Number- {{orderNumber}}</h1><v-spacer></v-spacer>
          <strong style="font-size: x-large;" class="rounded pink lighten-5 pink--text mb-2 pa-2 ">
            Status : {{detail[0].order.status}}
          </strong>
        </div>


        <v-row>
          <v-col cols="8">
            <!-- Order Item table -->
            <v-card class="rounded-x1">
              <v-data-table :headers="headers" :items="detail" :items-per-page="5" class=" mt-2 elevation-1">
                <template v-slot:item.productImage="{ item }">
                  <v-img :src="localDomain + item.product.productPath" height="50" width="50" contain></v-img>
                </template>
                <template v-slot:item.subtotal="{ item }">
                  {{(item.product.price* item.qty)-(item.product.price* item.qty*(item.product.discount*0.01))}}
                </template>
              </v-data-table>
            </v-card>
          </v-col>
          <v-col cols="4">
            <!-- Customer info -->
            <v-card class="rounded-x1">
              <v-row>
                <v-col cols="2" offset="1" class="mt-5">

                  <v-icon size="60" class="rounded blue white--text">account_circle
                  </v-icon>
                </v-col>
                <v-col cols="8" offset="1" class="mt-2">

                  <v-card-item>
                    <v-card-title style="color:black;font-size:x-large;" class="mb-5">Customer Summary</v-card-title>
                    <v-card-text style="font-size: larger;" class="font-weight-bold">
                      <v-row class="mb-5">
                        Name<v-spacer></v-spacer><v-span class="ml-10">{{detail[0].order.customer.cname}}</v-span>
                      </v-row>
                      <v-row class="mb-5">
                        Email<v-spacer></v-spacer><v-span class="ml-10">{{detail[0].order.customer.email}}</v-span>
                      </v-row>
                      <v-row class="mb-5">
                        Phone<v-spacer></v-spacer><v-span class="ml-10">{{detail[0].order.customer.phone}}</v-span>
                      </v-row>
                      <v-row class="mb-5">
                        Address<v-spacer></v-spacer><v-span class="ml-10">{{detail[0].order.customer.address}}</v-span>
                      </v-row>
                      <v-row class="mb-5">
                        Delivery Address<v-spacer></v-spacer> <v-span
                          class="ml-10">{{detail[0].order.deliverAddress}}</v-span>
                      </v-row>

                    </v-card-text>
                  </v-card-item>

                </v-col>
              </v-row>


            </v-card>

          </v-col>



        </v-row>
        <v-row>
          <v-col cols="8">
            <!-- Total -->
            <v-card class="rounded-x1 pink lighten-5 pink--text">
              <v-row>
                <v-col cols="12" md="4" sm="12" offset="1" class="mt-4 text-center">
                  <strong style="font-size: x-large;">Total
                  </strong>
                </v-col>
                <v-col cols="12" sm="12" md="5" offset="1" class="mt-2">
                  <v-card-item>

                    <v-card-text style="font-size: x-large;" class="font-weight-bold text-center">
                      {{total}}
                    </v-card-text>
                  </v-card-item>
                </v-col>

              </v-row>
            </v-card>


          </v-col>
          <v-col cols="4">
            <v-card class="rounded-x1">

              <div v-if="!flag" class="text-center">
                <v-btn class="green white--text" @click="changeStatus()">Click Here to change Status : Finish</v-btn>
              </div>
            </v-card>


          </v-col>

        </v-row>


      </v-col>

      </v-row>

    </v-container>


  </div>
</template>
<script>
  import utils from "../utils/utils";
  import myheading from "../components/myheading.vue";
  import mydrawer from "../components/mydrawer.vue";
  export default {
    name: "orderDetail",
    components: { mydrawer, myheading },

    data() {
      return {
        localDomain: utils.constant.localDomain,
        orderNumber: this.$route.params.orderNumber,
        detail: [],
        flag: true,

        total: 0,

        headers: [
          { text: "Item", value: "productImage", sortable: true },
          { text: "Name", value: "product.pname", sortable: true },
          { text: "Color", value: "product.color", sortable: false },
          { text: "Price", value: "product.price", sortable: true },
          { text: "Discount %", value: "product.discount", sortable: true },
          { text: "Quantity", value: "qty", sortable: true },
          { text: "Sub Total", value: "subtotal", sortable: true },


        ],
        //customer:{}

      };
    },

    async created() {

      await this.getOrderDetail()


    },
    methods: {

      async getOrderDetail() {
        const resp = await utils.http.post("/order/findByOrderNo/" + this.orderNumber);
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {

            this.detail = data;
            this.total = this.detail[0].order.total;
            if (data[0].order.status == "pending") {
              this.flag = false;
            }


          }
        }
      },
      async changeStatus() {


        const resp = await utils.http.post("/order/finished/" + this.orderNumber);
        if (resp.status === 200) {

          this.$router.push({ path: "/dashboard" });
        }


      },





    }



  };
</script>