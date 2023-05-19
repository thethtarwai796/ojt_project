<template>
  <div>
    <myheading></myheading>
    <mydrawer></mydrawer>
    <v-container>
      <v-col>
        <h1 class="mb-10 text-center">Create Product</h1>
  <v-row>
    <v-col cols="3"></v-col>
    <v-col cols="6">
      <v-card class="pt-10 pb-10 rounded-lg  lighten-1 text-center w-50">
        <v-layout row wrap justify-center>
          <v-flex xs6 md6>
            <!-- Create Product Form -->
            <v-col cols="10">
              <v-form class="px-10" ref="movieForm" v-model="movieForm">
                <!-- Product name -->
                <v-text-field v-model="pname" :counter="50" :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 50) ||
                'Product name must be less than 50 characters',
            ]" label="Product Name" required></v-text-field>

                <!-- Color -->
                <v-text-field v-model="color" :counter="1000" :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 1000) ||
                'Color must be less than 50 characters',
            ]" label="Color" required></v-text-field>

                <!-- Stock qty -->
                <v-text-field v-model="stockQty" type="number" max="999999" min="0" :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v >= 0 && v <= 999999) ||
                'Stock quantity must be between 0 and 999999 MMK',
            ]" label="Stock quentity" required></v-text-field>

                <!--Price -->
                <v-text-field v-model="price" type="number" max="999999" min="1" :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v > 0 && v <= 999999) ||
                'Price must be between 0 and 999999 MMK',
            ]" label="Price" required></v-text-field>
                <!-- Discount  -->
                <v-text-field v-model="discount" type="number" max="100" min="0" :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v >= 0 && v <= 999999) ||
                'Discount rae must be between 0 and 100 MMK',
            ]" label="Discount Rate" required></v-text-field>

                <!-- Category -->
                <v-select v-model="category" :items="productCategoryList" item-text="name" item-value="id"
                  :rules="[(v) => !!v || 'Required']" label="Category" required>
                </v-select>



                <!-- Poster -->
                <v-file-input v-model="poster" label="Poster" show-size prepend-icon="mdi-camera"
                  placeholder="Choose Poster Image" accept="image/png, image/jpeg" :rules="[
              (v) => !!v || 'Required',
              (v) =>
                !v ||
                v.size < 10000000 ||
                'Image size should be less than 10 MB!',
            ]" @change="onChangePoster"></v-file-input>

                <!-- Poster Preview -->
                <v-img v-if="posterPreviewPath != null" :src="posterPreviewPath" width="200" height="150" contain>
                </v-img>


                <!-- Create Btn -->
                <v-btn :disabled="!movieForm" color="blue" class="mt-4 mr-4" @click="createProduct()">
                  <span v-if="!loading">Create Product</span>
                  <v-progress-circular v-else indeterminate color="blue-darken"></v-progress-circular>
                </v-btn>

                <!-- Error Alert For Movie -->
                <v-alert class="mt-3" v-show="errorAlert" dense type="error">
                  Create Product Failed!
                </v-alert>


              </v-form>
            </v-col>
          </v-flex>
        </v-layout>
      </v-card>
    </v-col>
  </v-row>
      </v-col>
    </v-container>
  </div>
</template>

<script>
  import myheading from "../components/myheading.vue";
  import mydrawer from "../components/mydrawer.vue";

  import utils from "../utils/utils";

  export default {
    name: "product_create",
    components: { mydrawer,myheading },

    data() {
      return {
        movieForm: false,

        pname: "t shirt",
        color: "cream",
        stockQty: 100,
        price: 10000,
        discount: 0,

        category: "",

        errorAlert: false,
        errorAlert2: false,

        loading: false,

        poster: null,
        posterPreviewPath: null,


        productCategoryList: [],
      };
    },

    async created() {
      await this.fetchProductCategories();
    },

    methods: {
      async fetchProductCategories() {
        const resp = await utils.http.get("/category/getAll");
        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.productCategoryList = data;
          }
        }
      },

      async createProduct() {
        // Form Validation
        if (this.$refs.movieForm.validate()) {
          this.errorAlert = false;
          this.loading = true;


          if (true) {
            let respPosterData = null;


            // Step 2 -> Poster

            const respPoster = await utils.http.postMedia(
              "/file/create",
              this.poster,
              this.poster.type
            );
            if (respPoster.status === 200) {
              respPosterData = await respPoster.text();
            } else {
              this.errorAlert = true;
            }


            // Step 4 -> Create Product

            if (respPosterData) {
              // Create Product API
              const respMovie = await utils.http.post("/product/create", {
                pname: this.pname,
                color: this.color,
                stockQty: this.stockQty,
                price: this.price,
                discount: this.discount,
                category: { id: this.category },
                productPath: respPosterData,

              });

              if (respMovie && respMovie.status === 200) {

                this.$router.push({ path: "/productlist2" });
              } else {
                this.errorAlert = true;
              }
            }
          }

          this.loading = false;
        }
      },

      onChangePoster(poster) {
        this.posterPreviewPath = URL.createObjectURL(poster);
      },


    },
  };
</script>