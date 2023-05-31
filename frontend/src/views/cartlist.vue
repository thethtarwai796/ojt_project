<template>
  <div>
    <myheading></myheading>
    <div v-if="!orderList.length" class="bg"> 

      </div>
    <div v-else>
      <v-stepper v-model="e1">
        <v-stepper-header>
          <v-stepper-step :complete="e1 > 1" step="1">Cart List</v-stepper-step>

          <v-divider></v-divider>

          <v-stepper-step :complete="e1 > 2" step="2">Billing</v-stepper-step>

          <v-divider></v-divider>

          <v-stepper-step step="3">Order Confirmation</v-stepper-step>
        </v-stepper-header>
        <v-stepper-items>
          <v-stepper-content step="1" style="height:100%">


            <h1 class="text-center pt-10">Your Cart List</h1>
            <h3 class="text-center pt-10">Estimate Time::<h2>About 1 weeks</h2> </h3>
            <!-- <Cart table></Cart> -->
            <v-data-table :headers="headers" :items="orderList" :items-per-page="5" class=" mt-2 elevation-1"
              style="width:70% ;margin-left: 15%;">
              <template v-slot:item.productImage="{ item }">
                <v-img :src="localDomain + item.product.productPath" height="50" width="50" contain></v-img>
              </template>

              <template v-slot:item.quantity="{ item }">
                <v-text-field type="number" max="item.product.stockQty" min="0" :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v > 0 && v <= item.product.stockQty) ||
                'Stock quantity is not available',
            ]" v-model="item.qty" required style="width: 50px;"></v-text-field>
              </template>

              <template v-slot:item.subtotal="{item}">
                {{(item.product.price* item.qty)-(item.product.price* item.qty*(item.product.discount*0.01))}}
              </template>

              <template v-slot:item.action="{ item }">
                <v-icon class="red--text" @click="onclickdelete(item)">delete</v-icon>
              </template>
              <template slot="body.append">
                <tr class="pink--text">
                  <th></th>
                  <th></th>
                  <th></th>
                  <th></th>
                  <th></th>
<!-- <total></total> -->
                  <th class="title">Totals</th>

                  <th class="title" v-if="flag">{{total}}</th>

                </tr>
              </template>
            </v-data-table>
            <v-btn class=" pink white--text mt-10" style="margin-left: 80%;" @click="e1 = 2">
              Next
            </v-btn>
          </v-stepper-content>
          <v-stepper-content step="2">
            <!-- Bill form -->
            <v-form class="px-10" ref="billform" v-model="billform">
            <v-card style="width:50%;margin-left: 25%;">
              <v-text-field v-model="deliveraddress" :counter="200" :rules="[
              (v) => !!v || 'Required',
              
            ]" label="Add Deliver Address" required outlined></v-text-field>
            <hr>
            </v-card>
            
            <v-card style="width:50%;margin-left: 25%;" class="mt-10">
              <v-row>
                <v-col cols="12">
                  <v-card-title><v-icon class="white pink--text">credit_card</v-icon>Add Credit or Debit Card</v-card-title>
                  <v-text-field :counter="16" :rules="[
                  (v) => !!v || 'Required',
                  (v) =>
                    (v && v.length == 16) ||
                    'Card Number must be  16 numbers',
                ]" label="Card Number" value="1234567890123456" required outlined>
                  </v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col cols="6">
                  <v-text-field  :counter="5" :rules="[
                  (v) => !!v || 'Required',
                  (v) =>
                    (v && v.length == 5) ||
                    'MM/YY must be  5 characters',
                ]" label="MM/YY" value="12/12" required outlined>
                  </v-text-field>
                </v-col>
                <v-col cols="6">
                  <v-text-field :counter="4" :rules="[
                  (v) => !!v || 'Required',
                  (v) =>
                    (v && v.length ==4) ||
                    'CVC must be 4 characters',
                ]" label="CVC" value="123" required outlined>
                  </v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col cols="12">
                  <v-text-field :counter="100" :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 100) ||
                'Owner name must be less than 100 characters',
            ]" label="Card Owner" value="Thet Htar Wai" required outlined>
                  </v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col cols="6">
                  </v-text-field>
                </v-col>
                <v-col cols="6">
                  
                  <v-btn class=" white pink--text mt-5" style="margin-left:50%" @click="e1 = 1" outlined>
                    Cancle
                  </v-btn>
                  <v-btn :disabled="!billform"  class=" pink white--text mt-5 text-end" style="margin-left: 3%;" @click="e1 = 3">
                    Next
                  </v-btn>
                </v-col>
              </v-row>
            </v-card>
          </v-form>
            


          </v-stepper-content>

          <v-stepper-content step="3">

            <v-row justify="center" align="center">
            <v-card style="width:30%;height:50%;text-align: center;" class="mt-10 mb-10">
              <v-icon size="100" class="green white--text mt-10">task</v-icon><br>
              <h1 class="mt-10">Your Order is confirmed</h1>
              
              
                <v-btn class=" pink white--text mt-5" width="100%" @click="ordercreate()">OK</v-btn>
              
            </v-card>
            </v-row>

          </v-stepper-content>

        </v-stepper-items>

      </v-stepper>

    </div>
  </div>

</template>
<script>
  import utils from "../utils/utils";
  import myheading from "../components/heading.vue";
  export default {
    name: "dashboard",
    components: { myheading },
    data() {
      return {
        e1: 1,
        billform:false,
        isLogin: false,
        localDomain: utils.constant.localDomain,
        orderList: [],
        orders:[],
        flag: false,
        total:0,
        deliveraddress:"Taungoo",
        data:"",
        headers: [
          { text: "Product ID", value: "product.pid", sortable: true },
          { text: "Item", value: "productImage", sortable: true },
          { text: "Name", value: "product.pname", sortable: true },
          { text: "Color", value: "product.color", sortable: false },
          { text: "Price", value: "product.price", sortable: true },
          { text: "Discount %", value: "product.discount", sortable: true },
          { text: "Quantity", value: "quantity", sortable: true },
          { text: "Sub Total", value: "subtotal", sortable: true },
          { text: "", value: "action", sortable: false },
        ],
        cards: [],
      }
    },
    async created() {
      // islogin  check
      this.isLogin = this.$store.state.isLogin;
      this.$store.watch(
        () => {
          return this.$store.state.isLogin;
        },
        (newVal, oldVal) => {
          this.isLogin = newVal;
        },
        {
          deep: true,
        }
      );

      //get orders from session
      this.orderList = this.$store.state.orders;
      this.$store.watch(
        () => {
          return this.$store.state.orders;
        },
        {
          deep: true,
        }
      );

      this.orderList.map(a=>(a.product.price*a.qty)-(a.product.price* a.qty*(a.product.discount*0.01))).forEach(element => {
            this.total+=element;
            });
            console.log(this.total);
            this.flag=true;

      await this.getCards();
    },

    methods: {

      async getCards() {
        const resp = await utils.http.get("/product/getAll");
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.products = data;
            this.backupProduct = data;
          }
        }
      },
      onclickdelete(item) {
        this.orderList.splice(this.orderList.indexOf(item),1);
        this.total=0;
        this.orderList.map(a=>
        (a.product.price*a.qty)-(a.product.price* a.qty*(a.product.discount*0.01))).forEach(element =>{
            this.total+=element;
            });
            console.log(this.total);
            this.flag=true;
      },

      updateqty(item) {
        item.qty = parseInt(item.qty - 1);
        this.$store.commit("setOrders", item);
        console.log(item);
        computed;
      },
      async ordercreate(){
        //order create
        const resp = await utils.http.post("/order/create",{
            customer: this.$store.state.loginUser,
            total: this.total,
            status:'pending',
            deliverAddress:this.deliveraddress,
          });
        if (resp && resp.status === 200) {
          this.data = await resp.json();
        }

        //insert order detail
        this.orderList.forEach(element => {
          this.orders.push({order:this.data,product:element.product,qty:element.qty});
        });
        const resp2 = await utils.http.post("/order/createDetail",this.orders);
        if (resp2 && resp2.status === 200) {
          const data2 = await resp2.json();
        }

        //update stock
        const resp3 = await utils.http.post("/order/updateStock",this.orders);
        if (resp3 && resp3.status === 200) {
          this.$store.commit("removeorders");
          this.$router.push({
          path:"/"
        });
        }
        


        
        //console.log(this.orderList);
      }

    },
    // computed: {
    //   total() {
    //     //return this.$store.state.total;
        
    //   }
    // }

  }

</script>

<style>
  .bg {
    background-image: url('../assets/emptycart.png');
    background-position: center;
    text-align: center;
    padding-top: 10%;
    height: 100%;

  }
</style>