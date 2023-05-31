<template>
  <div>
    <myheading></myheading>
    <mydrawer></mydrawer>
    <v-container>
      <v-col><v-row>
        <h1 class="mb-10">Product List</h1><v-spacer></v-spacer>
        <v-btn class="pink lighten-4 pink--text" @click="goToCreate()">
          + Create Product
        </v-btn>
      </v-row>
        <v-row>
          <v-col cols="6">
            <!-- Category Select Box -->
            <v-row>
            <v-btn @click="getAllProduct" class="mr-4" >All</v-btn>
            <v-select rounded v-model="category" :items="categories" item-text="name" item-value="id"
              :rules="[(v) => !!v || 'Required']" label="Category" required v-on:change="changeRoute()" outlined style="width: 50%;">
              
            </v-select>
          </v-row>
            <!-- search box -->
            <v-card>
              <v-card-text>
                <v-card-title>
                  <v-text-field  v-model="searchkey" style="height :50px;" prepend-inner-icon="mdi-magnify"
                  rounded placeholder="What are you looking for?" outlined></v-text-field>
                </v-card-title>
                
              </v-card-text>
            </v-card>

            

          </v-col>
          <v-col cols="6">
            <bestsellpie></bestsellpie>
          </v-col>
        </v-row>
        <v-row>

          <v-col cols="12">
            <v-row>
              <!-- Show Product List -->
              <v-col cols="3" v-for="(product, index) in filterProduct" :key="index">
                <v-card @click="onClickUpdateProduct(product)" height="600" rounded="lg" elevation="8">
                  <v-card-text>
                    <v-img :src="localDomain + product.productPath" height="400" cover></v-img>
                    <div class="pt-3">
                      <div class="text-h5 black--text">{{ product.pname }} ({{product.color}})</div>
                      <div class="text-body-1 black--text"><i>{{ product.price }} Kyat</i></div>
                      <div class="text-body-1">Available Stock::{{ product.stockQty }} </div>
                      <div>

                        <v-btn style="font-size: small;" class="pink lighten-4 pink--text" @click="
                                    onClickUpdateProduct(product)
                                  ">
                          <v-icon class="">edit</v-icon>Edit</v-btn>
                      </div>

                    </div>

                  </v-card-text>
                </v-card>


                <!-- Update Dialog -->
                <v-dialog v-model="updateDialog" width="500">
                  <v-card>
                    <!-- Dialog Heading -->
                    <v-toolbar color="pink white--text" dark>
                      <div>Update This Product?</div>
                      <v-spacer></v-spacer>
                      <v-btn icon @click="updateDialog = false">
                        <v-icon>mdi-close</v-icon>
                      </v-btn>
                    </v-toolbar>

                    <!-- Update Form -->
                    <v-card-text class="pa-4">
                      <v-form ref="productForm" v-model="productForm">
                        <!-- Update Product Title -->
                        <v-text-field v-model="toUpdateProduct.pname" :counter="100" :rules="[
                                      (v) => !!v || 'Required',
                                      (v) =>
                                        (v && v.length <= 100) ||
                                        'Name must be less than 100 characters',
                                    ]" label="Name" required></v-text-field>

                        <!-- Update Product color -->
                        <v-text-field v-model="toUpdateProduct.color" :counter="100" :rules="[
                                      (v) => !!v || 'Required',
                                      (v) =>
                                        (v && v.length <= 100) ||
                                        'Overview must be less than 100 characters',
                                    ]" label="Color" required></v-text-field>

                        <!-- Update Product stock qty -->
                        <v-text-field v-model="toUpdateProduct.stockQty" type="number" suffix="Pieces" max="999999" min="1"
                          :rules="[
                                     (v) => !!v || 'Required',
                                     (v) =>
                                       (v && v > 0 && v <= 999999) ||
                                       'Stock quantity must be between 0 and 999999',
                                   ]" label="Stock" required></v-text-field>

                        <!-- Update Product price -->
                        <v-text-field v-model="toUpdateProduct.price" type="number" suffix="MMK" max="999999" min="1"
                          :rules="[
                                      (v) => !!v || 'Required',
                                      (v) =>
                                        (v && v > 0 && v <= 999999) ||
                                        'Unit Price must be between 0 and 999999 MMK',
                                    ]" label="Unit Price" required></v-text-field>

                        <!-- Update Product discount -->
                        <v-text-field v-model="toUpdateProduct.discount" type="number" suffix="%" max="100" min="0"
                          :rules="[
                                     
                                     (v) =>
                                       (v && v >= 0 && v <= 999999) ||
                                       'Discount must not be less than 0',
                                   ]" label="discount"></v-text-field>


                        <!-- Update  Category -->
                        <v-select v-model="toUpdateProduct.category" :items="categories" item-text="name"
                          item-value="id" :rules="[(v) => !!v || 'Required']" label="Category" required>
                        </v-select>



                        <!-- Update Product imagae -->
                        <v-file-input v-model="toUpdateProduct.poster" label="Poster" show-size
                          prepend-icon="mdi-camera" placeholder="Choose Poster Image" accept="image/png, image/jpeg"
                          :rules="[
                                      (v) =>
                                        !v ||
                                        v.size < 10000000 ||
                                        'Image size should be less than 10 MB!',
                                    ]" @change="onChangePoster"></v-file-input>

                        <!-- Poster Preview -->
                        <!-- Poster is not selected (null) -->
                        <v-img v-if="posterPreviewPath == null" :src="localDomain + toUpdateProduct.productPath"
                          width="200" height="150" contain>
                        </v-img>
                        <!-- Poster is selected (not null) -->
                        <v-img v-if="posterPreviewPath != null" :src="posterPreviewPath" width="200" height="150"
                          contain>
                        </v-img>
                        <!-- Update Btn -->
                        <v-btn :disabled="!productForm"  class="mr-4 pink lighten-4 pink--text" @click="updateProduct">
                          <span v-if="!loading">Update</span>
                          <v-progress-circular v-else indeterminate color="primary"></v-progress-circular>
                        </v-btn>

                        <!-- Error Alert -->
                        <v-alert class="mt-3" v-show="errorAlert" dense type="error">
                          Update Product Failed!
                        </v-alert>
                      </v-form>
                    </v-card-text>
                  </v-card>
                </v-dialog>
              </v-col>
            </v-row>
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
  import piechart from "../components/piechart.vue";
  import bestsellpie from "../components/bestsellpie.vue";
  import barchart from "../components/barchart.vue";

  export default {
    name: "dashboard",
    components: { myheading, mydrawer, piechart,bestsellpie, barchart },

    data() {
      return {
        localDomain: utils.constant.localDomain,
        products: [],
        categories: [],
        category: "",
        searchkey:'',

        updateDialog: false,
        productForm: false,
        toUpdateProduct: {
          pid: 0,
          pname: "",
          color: "",
          stockQty: 0,
          price: 0,
          discount: 0,
          category: 1,
          productPath: "",
          poster: null,
        },
        posterPreviewPath: null,
        errorAlert: false,
        loading: false,

      };
    },

    async created() {
      await this.getAllProduct(),
        await this.getAllCategory()

    },
    computed:{
      filterProduct:function(){
        return this.products.filter((product)=>{
          return product.pname.toLowerCase().match(this.searchkey.toLowerCase())
        }
        
        )
      }
    }
    ,
    methods: {

      async getAllProduct() {
        const resp = await utils.http.get("/product/getAll");
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.products = data;
          }
        }
        this.category=" ";
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
      async changeRoute() {
        const resp = await utils.http.get("/product/getByCategory/" + this.category);
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.products = data;
          }
        }

      },

      // async deleteProduct() {
      //   const resp = await utils.http.del(
      //     "/product/delete/" + this.toDeleteProduct.pid
      //   );
      //   if (resp && resp.status === 200) {
      //     this.deleteDialog = false;
      //     // Refresh Movies
      //     await this.getAllProduct();
      //   } else {
      //     this.errorAlert = true;
      //   }
      // },

      onClickUpdateProduct(product) {
        this.updateDialog = true;
        // Copy Object
        this.toUpdateProduct = Object.assign({}, product);
        this.toUpdateProduct.poster = null;
        this.posterPreviewPath = null;

      },

      async updateProduct() {
        // Form Validation
        if (this.$refs['productForm'][0].validate()) {
          this.errorAlert = false;

          this.loading = true;

          let productPath = this.toUpdateProduct.productPath;

          // Step 1 -> Update Poster

          // Null -> Poster is not selected
          if (this.toUpdateProduct.poster != null) {
            // Update Poster API
            const respPoster = await utils.http.putMedia(
              "/file/update",
              this.toUpdateProduct.poster,
              this.toUpdateProduct.poster.type,
              this.toUpdateProduct.productPath
            );
            if (respPoster && respPoster.status === 200) {
              productPath = await respPoster.text();
            } else {
              console.debug("Poster Update Failed");
            }
          }



          // Step 3 -> Update Movie

          const respMovie = await utils.http.put(
            "/product/update/" + this.toUpdateProduct.pid,
            {
              pid: this.toUpdateProduct.pid,
              pname: this.toUpdateProduct.pname,
              color: this.toUpdateProduct.color,
              stockQty: this.toUpdateProduct.stockQty,
              price: this.toUpdateProduct.price,
              productPath: productPath,
              discount: this.toUpdateProduct.discount,
              category: this.toUpdateProduct.category,



            }
          );
          if (respMovie && respMovie.status === 200) {
            this.toUpdateProduct.poster = null;
            this.updateDialog = false;
            // Refresh Movies
            await this.getAllProduct();
          } else {
            this.errorAlert = true;
          }

          this.loading = false;
        }
      },

      onChangePoster(poster) {
        this.posterPreviewPath = URL.createObjectURL(poster);
      },
      goToCreate() {
        this.$router.push({
          //path: "/product_update/" + product.id,
          path: "/product_create"
        });
      },

      





    }


  };
</script>