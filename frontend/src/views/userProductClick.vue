<template>
    <div>
        <myheading></myheading>
        <v-container>


            <v-row justify="center" class="mt-10">
                <v-card>
                    <v-row>
                        <v-col>
                            <h1 class="mb-10 text-center">{{this.product.pname}}</h1>

                        </v-col>

                    </v-row>
                    <v-row>
                        <v-col cols="4">

                            <div class="pt-5">
                                <v-img :src="localDomain + product.productPath" height="400" cover></v-img>
                            </div>
                        </v-col>
                        <v-col cols="8" class="pl-10">
                            <div v-if="product.discount==0">
                                <h2>{{product.price}}MMK</h2>
                            </div>
                            <div v-else>
                                <v-btn class="red white--text w-200px">{{product.discount}}%Off</v-btn><br>
                                <v-span>
                                    <h2>{{product.price-(product.price*(product.discount*0.01))}}MMK</h2>
                                </v-span>

                                <v-span class="text-decoration-line-through">{{product.price}}MMK</v-span><br>

                            </div>
                            <hr>
                            <div class="pt-5">
                                <i>
                                    <h3>(Colour- {{product.color}})</h3>
                                </i>
                                <h3>Available Stock :: {{product.stockQty}} Pieces</h3>
                            </div>
                            <hr>
                            <div class="pt-5">
                                <!-- Order qty -->
                                <v-form ref="qtyform" v-model="qtyform">
                                    <v-text-field style="width: 200px;" v-model="orderQty" type="number" max="product.stockQty" min="0" :rules="[
                                    (v) => !!v || 'Required',
                                    (v) =>
                                    (v && v >0 && v <= product.stockQty) ||
                                    'Your order quantity is not unavailable',
                                ]" label="Enter Order quentity" required></v-text-field>
                                <!-- Add to cart Btn -->
                            <v-btn :disabled="!qtyform" @click="gotocartlist()" class="pink white--text">
                                Add to Cart
                            </v-btn>
                                </v-form>
                                
                            </div>
                        </v-col>
                    </v-row>
                </v-card>
                <!-- Update Form -->

            </v-row>

        </v-container>
    </div>
</template>

<script>
    import myheading from "../components/heading.vue";

    import utils from "../utils/utils";

    export default {
        name: "userProductClick",
        components: {  myheading },

        data() {
            return {
                isLogin: false,
                localDomain: utils.constant.localDomain,
                pid: this.$route.params.pid,
                product: null,
                
                order:{
                    product:null,qty:0
                },
                orderQty:0,
                discountprice: 0,
                qtyform:false,
            };
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
            await this.getProduct();
        },

        methods: {
            async getProduct() {
                const resp = await utils.http.get("/product/getById/" + this.pid);
                if (resp && resp.status === 200) {
                    const data = await resp.json();
                    if (data) {
                        this.product = data;
                    }
                }

            },
            gotocartlist() {
                if(!this.isLogin){
                    this.$router.push({path:"/login"});
                }
                else{
                    this.order.product=this.product;
                    this.order.qty=parseInt(this.orderQty);
            // Passing array to cartlist.vue page and keep in session

            this.$store.commit("setOrders", this.order);
            this.$router.push({path:"/cartlist"})
                }
            
        },


        },

        


    };
</script>