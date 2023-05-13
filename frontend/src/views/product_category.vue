<template>
    <div>
        <v-container>
            <v-row>
                <v-col>
                    <h1 class="mb-10">Categories</h1>
                    <v-btn @click="onClickAdd">+ Add Category</v-btn>

                    <v-data-table :headers="headers" :items="categories"
                        class="text--20 mt-2 elevation-1">
                        <template v-slot:item.actions="{ item }">
                            <!-- Update Category -->
                            <v-icon link @click="onClickEdit(item)">edit</v-icon>
                        </template>
                    </v-data-table>


                    <!-- Update Dialog -->
                    <v-dialog v-model="updateDialog" width="500">
                        <v-card>
                            <!-- Dialog Heading -->
                            <v-toolbar color="primary" dark>
                                <div>Update This Category?</div>
                                <v-spacer></v-spacer>
                                <v-btn icon @click="updateDialog = false">
                                    <v-icon>mdi-close</v-icon>
                                </v-btn>
                            </v-toolbar>

                            <!-- Update Form -->
                            <v-card-text class="pa-4">
                                <v-form ref="categoryForm" v-model="categoryForm">
                                    <!-- Update Category Name -->
                                    <v-text-field v-model="toUpdateCategory.name" :counter="50" :rules="[
                      (v) => !!v || 'Required',
                      (v) =>
                        (v && v.length <= 50) ||
                        'Name must be less than 50 characters',
                    ]" label="Name" required></v-text-field>
                    <!-- Update Btn -->
                    <v-btn
                    :disabled="!categoryForm"
                    color="success"
                    class="mr-4"
                    @click="updateCategory"
                  >
                    <span v-if="!loading">Update</span>
                    <v-progress-circular
                      v-else
                      indeterminate
                      color="primary"
                    ></v-progress-circular>
                  </v-btn>
      
                  <!-- Error Alert -->
                  <v-alert class="mt-3" v-show="errorAlert" dense type="error">
                    Update Movie Failed!
                  </v-alert>
                </v-form></v-card-text></v-card></v-dialog>

                <!-- Add Dialog -->
                <v-dialog v-model="addDialog" width="500">
                    <v-card>
                        <!-- Dialog Heading -->
                        <v-toolbar color="primary" dark>
                            <div>Add Category?</div>
                            <v-spacer></v-spacer>
                            <v-btn icon @click="addDialog = false">
                                <v-icon>mdi-close</v-icon>
                            </v-btn>
                        </v-toolbar>

                        <!-- Add Form -->
                        <v-card-text class="pa-4">
                            <v-form ref="categoryForm2" v-model="categoryForm2">
                                <!-- Add Category Name -->
                                <v-text-field v-model="category_name" :counter="50" :rules="[
                  (v) => !!v || 'Required',
                  (v) =>
                    (v && v.length <= 50) ||
                    'Name must be less than 50 characters',
                ]" label="Name" required></v-text-field>
                <!-- Update Btn -->
                <v-btn
                :disabled="!categoryForm2"
                color="success"
                class="mr-4"
                @click="addCategory"
              >
                <span v-if="!loading">Add</span>
                <v-progress-circular
                  v-else
                  indeterminate
                  color="primary"
                ></v-progress-circular>
              </v-btn>
  
              <!-- Error Alert -->
              <v-alert class="mt-3" v-show="errorAlert" dense type="error">
                Update Movie Failed!
              </v-alert>
            </v-form></v-card-text></v-card></v-dialog>


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
        name: "prodcut_category",
        components: { myheading, mydrawer },

        data() {
            return {
                updateDialog: false,
                categoryForm: false,
                toUpdateCategory: {
                  id:0,
                  name: ""},
                  errorAlert: false,
                loading: false,
                categories: [],

                addDialog:false,
                categoryForm2:false,
                category_name:"",


                headers: [
                    { text: "#ID", value: "id", sortable: true },
                    { text: "Name", value: "name", sortable: false },
                    { text: "Edit", value: "actions", sortable: false },
                ],

            };
        },

        async created() {

            await this.getCategories()

        },
        methods: {

            async getCategories() {
                const resp = await utils.http.get("/category/getAll");
                if (resp && resp.status === 200) {
                    const data = await resp.json();
                    if (data) {
                        this.categories = data;
                    }
                }
            },

            onClickEdit(item) {
                this.updateDialog = true;
                // Copy Object
                this.toUpdateCategory = Object.assign({}, item);


            },
            async updateCategory() {
      // Form Validation
      if (this.$refs.categoryForm.validate()) {
        this.errorAlert = false;
        this.loading = true;
        const respMovie = await utils.http.put(
          "/category/update/" + this.toUpdateCategory.id,
          {
            id:this.toUpdateCategory.id,
            name: this.toUpdateCategory.name,
          }
        );
        if (respMovie && respMovie.status === 200) {
          
          this.updateDialog = false;
          // Refresh Movies
          await this.getCategories();
        } else {
          this.errorAlert = true;
        }

        this.loading = false;

    }},
    onClickAdd() {
                this.addDialog = true;
            },
    async addCategory() {
      // Form Validation
      if (this.$refs.categoryForm2.validate()) {
        this.errorAlert = false;
        this.loading = true;
        const respMovie = await utils.http.post(
          "/category/create",
          {
            name: this.category_name,
          }
        );
        if (respMovie && respMovie.status === 200) {
          
          this.addDialog = false;
          // Refresh Movies
          await this.getCategories();
        } else {
          this.errorAlert = true;
        }

        this.loading = false;

    }}




        }


    };
</script>