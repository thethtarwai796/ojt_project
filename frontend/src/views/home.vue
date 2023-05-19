<template>
  <div class="blue lighten-5">
    <heading></heading>
    <!-- Category Menu -->
    <template>
      <v-card style="border-color:black;border-style: solid;">
        <v-navigation-drawer clipped app>
          <v-list dense nav>
            <v-list-title class="">Categories</v-list-title>
            <hr>
            </hr>
            <v-list-item link @click="onClickAllCategory()">
              <v-list-content>All</v-list-content>
            </v-list-item>
            <v-list-item class="display-3" v-for="(category,i) in categories" :key="i" link
              @click="onClickCategory(category.id)">

              <v-list-item-content>
                <v-list-item-title>{{ category.name }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-navigation-drawer>
      </v-card>
    </template>
    <v-container>
      <v-row>
        <v-col cols="12">
          <v-card>
            <v-card-text>
              <v-card-title style="width:800px">
                <v-text-field  v-model="searchkey" style="border:solid;border-color: black;height :50px;width:fit-content"
                rounded placeholder="What are you looking for?" class="p-3"></v-text-field>
              <v-btn class="black white--text" @click="search()">
                <v-icon size="30" class="black white--text" >search</v-icon>Search
              </v-btn>
              </v-card-title>
              
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <v-row>
            <v-col cols="3" v-for="(product, index) in products" :key="index">
              <v-card @click="gotoProductDetail(product)" height="600">
                <v-card-text>
                  <v-img :src="localDomain + product.productPath" height="400" cover></v-img>
                  <div class="pt-3">
                    <div class="text-h5 black--text">{{ product.pname }} ({{product.color}})</div>
                    <div class="text-body-1 black--text"><i>{{ product.price }} Kyat</i></div>
                    <div class="text-body-1">Available Stock::{{ product.stockQty }} </div>
                    <div>

                      <v-btn style="font-size: small;" class="white" @click="
                    gotoProductDetail(product)">
                        <v-icon>add_shopping_cart</v-icon>Add to cart</v-btn>
                    </div>

                  </div>

                </v-card-text>
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
  import heading from "../components/heading.vue";


  export default {
    name: "home",

    components: { heading },

    data() {
      return {
        localDomain: utils.constant.localDomain,

        categories: [],
        products: [],
        backupProduct: [],
        searchkey:"",
      };
    },

    async created() {
      await this.getAllCategory();
      await this.getAllProduct();
    },

    methods: {
      async getAllProduct() {
        const resp = await utils.http.get("/product/getAll");
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.products = data;
            this.backupProduct = data;
          }
        }
      },
      async getAllCategory() {
        const resp = await utils.http.get("/category/getAll");
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.categories = data;
          }
        }
      },
      async onClickCategory(id) {
        const resp = await utils.http.get("/product/getByCategory/" + id);
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.products = data;
          }
        }

      },
      onClickAllCategory() {
        this.products = this.backupProduct;
      },

      async search() {
        const resp = await utils.http.get("/product/searchProduct?pname=" + this.searchkey);
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.products = data;
            this.searchkey = "";
          }
        }

      },

      gotoProductDetail(product) {
        this.$router.push({
          path: "/product_detail/" + product.pid,
        });
      },


    },
  };
</script>