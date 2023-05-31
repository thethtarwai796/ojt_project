<template>
    <div>
        <myheading></myheading>
        <mydrawer></mydrawer>
        <v-container>
            <v-col>
                <v-row class="mt-10 mb-10">
                    <h1>Order Lists</h1>
                </v-row>

                <!-- Show Order List -->
                <v-row>

                    <v-col cols="12" v-for="(item, index) in orders" :key="index">
                        <v-card @click="getOrderDetail(item)" rounded="lg" elevation="8" class="mb-5">
                            <v-row>
                                <v-card-title>
                                    #{{ item.orderNumber }}
                                </v-card-title>
                                <v-spacer></v-spacer>

                                <v-cart-text class="text-center">
                                    <span class="pink--text" style="font-size: x-large;">
                                        {{item.total}}MMK
                                    </span>
                                    
                                    <v-spacer></v-spacer>
                                    {{item.orderDate}}
                                </v-cart-text>
                                <v-spacer></v-spacer>
                                <v-cart-text class="mr-10 mt-4" style="font-size: larger;">
                                    <span v-if="item.status=='pending'" class="pink--text" >
                                        {{item.status}}
                                    </span>
                                    <span v-else class="grey--text">
                                        {{item.status}}

                                    </span>
                                </v-cart-text>

                            </v-row>


                        </v-card>

                    </v-col>

                </v-row>

                <!-- detail dialog -->
                <v-dialog v-model="detaildialog" width="50%">
                    <v-card>
                        <!-- Dialog Heading -->
                        <v-toolbar color="pink white--text" dark>
                            <div>Order ID::#{{orderNo}}</div>
                            <v-spacer></v-spacer>
                            <v-btn icon @click="detaildialog = false">
                                <v-icon>mdi-close</v-icon>
                            </v-btn>
                        </v-toolbar>

                        <!-- Item table -->

                        
                                <v-data-table :headers="headers" :items="detail" :items-per-page="5" class=" mt-2 elevation-1">
                                  <template v-slot:item.productImage="{ item }">
                                    <v-img :src="localDomain + item.product.productPath" height="50" width="50" contain></v-img>
                                  </template>
                                  <template v-slot:item.subtotal="{ item }">
                                    {{(item.product.price* item.qty)-(item.product.price* item.qty*(item.product.discount*0.01))}}
                                  </template>
                                </v-data-table>

                               
                        </v-card></v-dialog>

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
        name: "userorderhistory",
        components: { myheading, mydrawer, piechart, barchart },

        data() {
            return {
                localDomain: utils.constant.localDomain,
                orders: [],
                user: {},
                detail:[],
                detaildialog:false,
                total:0,
                orderNo:0,
                headers: [
          { text: "Item", value: "productImage", sortable: true },
          { text: "Name", value: "product.pname", sortable: true },
          { text: "Color", value: "product.color", sortable: false },
          { text: "Price", value: "product.price", sortable: true },
          { text: "Discount %", value: "product.discount", sortable: true },
          { text: "Quantity", value: "qty", sortable: true },
          { text: "Sub Total", value: "subtotal", sortable: true },


        ],
            };
        },

        async created() {
            this.user = this.$store.state.loginUser;
            await this.getOrderByCustomer()
        },

        methods: {

            async getOrderByCustomer() {
                const resp = await utils.http.post("/order/getbycustomer", this.user);
                if (resp && resp.status === 200) {
                    const data = await resp.json();
                    if (data) {
                        this.orders = data;
                    }
                }
            },

            async getOrderDetail(item) {
        const resp1= await utils.http.post("/order/findByOrderNo/" + item.orderNumber);
        if (resp1 && resp1.status === 200) {
          const data1 = await resp1.json();
          if (data1) {

            this.detail = data1;
            this.total = item.total;
            this.orderNo=item.orderNumber;
            this.detaildialog=true


          }
        }
      },

        }


    };
</script>