<template>
    <div>
        <v-row>


            <!-- Movie table -->
            <v-col cols="10">
                <v-row>
                    <!-- <v-col cols="3">
                        <v-btn class="ma-5 yellow" link @click="createProduct('/product_create')">add product</v-btn>

                    </v-col> -->
                    <v-col cols="3">
                        <v-card>
                            <v-card-item>
                              <v-card-title>Total Instock</v-card-title>
                              <v-card-text>
                                0
                              </v-card-text>
                            </v-card-item>
                            
                          </v-card>
                    </v-col>
                </v-row>
                
                <v-data-table :headers="headers" :items="products" :items-per-page="5" class="elevation-1">
                    <!-- Poster Img -->
                    

                    <!-- Buttons -->
                    <template v-slot:item.actions="{ item }">
                        <!-- Update Movie -->
                        <v-btn class="mr-2" color="primary" fab x-small elevation="2" @click="onClickUpdateProduct(item)">
                            <v-icon>mdi-pencil</v-icon>
                        </v-btn>
                        <!-- Delete Movie Btn -->
                        <v-btn color="red" fab dark x-small elevation="2" @click="toDeleteProduct = item;deleteDialog = true;">
                            <v-icon>mdi-delete</v-icon>
                        </v-btn>
                    </template>
                </v-data-table>
            </v-col>
        </v-row>
        <v-btn class="ma-5 yellow" link @click="createProduct('/product_create')">add product</v-btn>


        <!-- Delete Dialog -->
        <v-dialog v-model="deleteDialog" width="400">
            <v-card>
                <!-- Dialog Heading -->
                <v-toolbar color="primary" dark>
                    <div>Delete This Product?</div>
                    <v-spacer></v-spacer>
                    <v-btn icon @click="deleteDialog = false">
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                </v-toolbar>

                <!-- Delete Movie Info -->
                <v-card-text class="pa-4">
                    <v-row dense>
                        <v-col cols="3" class="font-weight-bold">ID</v-col>
                        <v-col cols="7">{{ toDeleteProduct.id }}</v-col>
                        <v-col cols="3" class="font-weight-bold">Name</v-col>
                        <v-col cols="7">{{ toDeleteProduct.name }}</v-col>
                    </v-row>
                </v-card-text>

                <!-- Delete Movie Btn -->
                <v-card-actions class="justify-end">
                    <v-btn color="red" dark @click="deleteProduct()">Delete</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <!-- Update Dialog -->
        <v-dialog v-model="updateDialog" width="500">
            <v-card>
                <!-- Dialog Heading -->
                <v-toolbar color="primary" dark>
                    <div>Update This Product?</div>
                    <v-spacer></v-spacer>
                    <v-btn icon @click="updateDialog = false">
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                </v-toolbar>

                <!-- Update Form -->
                <v-card-text class="pa-4">
                    <v-form ref="movieForm" v-model="movieForm">
                        <!-- Update Movie Title -->
                        <v-text-field v-model="toUpdateProduct.name" :counter="50" :rules="[
                  (v) => !!v || 'Required',
                  (v) =>
                    (v && v.length <= 50) ||
                    'Name must be less than 50 characters',
                ]" label="Name" required></v-text-field>

                        <!-- Update Movie Overview -->
                        <v-text-field v-model="toUpdateProduct.colour" :counter="1000" :rules="[
                  (v) => !!v || 'Required',
                  (v) =>
                    (v && v.length <= 1000) ||
                    'colour must be less than 1000 characters',
                ]" label="Colour" required></v-text-field>

                        <!-- Update Movie Budget -->
                        <v-text-field v-model="toUpdateProduct.totalstock" type="number" suffix="MMK" max="999999" min="1"
                            :rules="[
                  (v) => !!v || 'Required',
                  (v) =>
                    (v && v > 0 && v <= 999999) ||
                    'Stock must be between 0 and 999999 MMK',
                ]" label="Total Stock" required></v-text-field>

                        

                        <!-- Update Movie Category -->
                        <v-checkbox v-model="toUpdateProduct.price" label="Price"></v-checkbox>

                        <!-- Update Movie Poster -->
                        <v-file-input v-model="toUpdateProduct.product" label="Product" show-size prepend-icon="mdi-camera"
                            placeholder="Choose Product Image" accept="image/png, image/jpeg" :rules="[
                  (v) =>
                    !v ||
                    v.size < 10000000 ||
                    'Image size should be less than 10 MB!',
                ]" @change="onChangeProduct"></v-file-input>

                        <!-- Poster Preview -->
                        <!-- Poster is not selected (null) -->
                        <v-img v-if="productPreviewPath == null" :src="localDomain + toUpdateProduct.productPath"
                            width="200" height="150" contain>
                        </v-img>
                        <!-- Poster is selected (not null) -->
                        <v-img v-if="productPreviewPath != null" :src="productPreviewPath" width="200" height="150"
                            contain>
                        </v-img>

                        

                        <!-- Update Btn -->
                        <v-btn :disabled="!movieForm" color="success" class="mr-4" @click="updateProduct">
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
    </div>
</template>

<script>
    import utils from "../utils/utils";
    
    export default {
        name: "productlist",

        components: {  },

        data() {
            return {
                localDomain: utils.constant.localDomain,

                headers: [
                    { text: "ID", value: "id", sortable: true },
                    { text: "Image", value: "productPath", sortable: false },
                    { text: "Name", value: "name", sortable: true },
                    { text: "Colour", value: "colour", sortable: true },
                    { text: "Total Instock", value: "totalstock", width: 200, sortable: false },
                    { text: "Unit Price", value: "price", sortable: true },
                    { text: "Actions", value: "actions", sortable: false },
                ],
                products: [],

                deleteDialog: false,
                toDeleteProduct: {},

                updateDialog: false,
                movieForm: false,
                toUpdateProduct: {
                    name: "",
                    totalstock: 0,
                    colour: "",
                    productPath: "",
                    product: null,
                },
                productPreviewPath: null,
                

                errorAlert: false,
                loading: false,
                countinstock:0,

                
            };
        },

        async created() {
            
            await this.fecthProduct();
            await this.countInstock()
        },

        methods: {
            async fetchProduct() {
                const resp = await utils.http.get("/getAllProduct");
                if (resp && resp.status === 200) {
                    const data = await resp.json();
                    if (data) {
                        this.products = data;
                    }
                }
            },
            async countInstock() {
          const resp = await utils.http.get("/countInstock");
          if (resp && resp.status === 200) {
            const data = await resp.json();
            if (data) {
              this.countinstock = data;
            }
          }
        },

            

            async deleteProduct() {
                const resp = await utils.http.del(
                    "/deleteProduct" + this.toDeleteProduct.id
                );
                if (resp && resp.status === 200) {
                    this.deleteDialog = false;
                    // Refresh Movies
                    await this.fetchProduct();
                } else {
                    this.errorAlert = true;
                }
            },

            onClickUpdateProduct(item) {
                this.updateDialog = true;
                // Copy Object
                this.toUpdateProduct = Object.assign({}, item);
                this.toUpdateProduct.product = null;
                
                this.productPreviewPath = null;
                
            },

            async toUpdateProduct() {
                // Form Validation
                if (this.$refs.movieForm.validate()) {
                    this.errorAlert = false;

                    this.loading = true;

                    let productPath = this.toUpdateProduct.productPath;
                    

                    // Step 1 -> Update Poster

                    // Null -> Poster is not selected
                    if (this.toUpdateProduct.product != null) {
                        // Update Poster API
                        const respPoster = await utils.http.putMedia(
                            "/updataFile",
                            this.toUpdateProduct.product,
                            this.toUpdateProduct.product.type,
                            this.toUpdateProduct.productPath
                        );
                        if (respPoster && respPoster.status === 200) {
                        productPath = await respPoster.text();
                        } else {
                            console.debug("Poster Update Failed");
                        }
                    }

                    // Step 2 -> Update Trailer

                    

                    // Step 3 -> Update Movie

                    const respMovie = await utils.http.put(
                        "/admin/updateProduct" + this.toUpdateProduct.id,
                        {
                            name: this.toUpdateProduct.name,
                            colour: this.toUpdateProduct.colour,
                            totalstock: this.toUpdateProduct.totalstock,
                            
                            price: this.toUpdateProduct.price,
                            productPath: productPath,
                            
                        }
                    );
                    if (respMovie && respMovie.status === 200) {
                        this.toUpdateProduct.product = null;
                        
                        this.updateDialog = false;
                        // Refresh Movies
                        await this.fetchProduct();
                    } else {
                        this.errorAlert = true;
                    }

                    this.loading = false;
                }
            },

            onChangeProduct(product) {
                this.productPreviewPath = URL.createObjectURL(product);
            },
            createProduct(path) {
      // If Current Path is same with Clicked Path, No Go to Route
      if (this.$route.path != path) {
        this.$router.push({ path: path });
      }
    }

            
        },
    };
</script>