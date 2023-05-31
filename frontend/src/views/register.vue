<template>
  <div>
    <v-container>
      <div class=" d-flex justify-center mt-10 mb-10">
        <img src="../assets/mainlolo.jpeg" style="height: 70px;border-radius: 20px;"></img>
        <h2 class="display-3">
          ShopaHolic</h2>
      </div>


      <div class="d-flex justify-center">
        <v-card width="40%">
          
          <v-card-title class="text-h6 text-md-h5 text-lg-h4 mt-10">To be Continue, please Sing Up</v-card-title>
          <v-card-text>
      <!-- Register Form -->
      <v-form ref="registerForm" v-model="registerForm">
        <!-- Name -->
        <v-text-field
          v-model="name"
          :counter="10"
          :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 10) || 'Name must be less than 10 characters',
          ]"
          label="Name"
          required
          outlined
        ></v-text-field>

        <!-- Email -->
        <v-text-field
          v-model="email"
          :rules="[
            (v) => !!v || 'Required',
            (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
          ]"
          label="E-mail"
          required
          outlined
        ></v-text-field>

        <!-- Password -->
        <v-text-field
          v-model="password"
          :counter="10"
          :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 10) ||
              'Password must be less than 10 characters',
          ]"
          label="Password"
          required
          outlined
        ></v-text-field>
        <!-- Address -->
        <v-text-field
        v-model="address"
        :counter="100"
        :rules="[
          (v) => !!v || 'Required',
          (v) =>
            (v && v.length <= 100) || 'Address must be less than 100 characters',
        ]"
        label="Address"
        required
        outlined
      ></v-text-field>
      <!-- Phone Number -->
      <v-text-field type="number"
          v-model="phno"
          :counter="11"
          :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 11) || 'Name must be 11 numbers',
          ]"
          label="Phone Number"
          required
          outlined
        ></v-text-field>
        <!-- Register Btn -->
        <v-btn
          :disabled="!registerForm"
          class="mr-4 mb-5 pink white--text" style="width: 100%;"
          @click="register()"
        >
          <span v-if="!loading">Register</span>
          <v-progress-circular
            v-else
            indeterminate
            color="primary"
          ></v-progress-circular>
        </v-btn>

        <!-- Error Alert -->
        <v-alert class="mt-3" v-show="errorAlert" dense type="error">
          Register Failed!
        </v-alert>
      </v-form>
    </v-card-text>
        
  </v-card>
</div>
    </v-container>
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "register",

  components: {},

  data() {
    return {
      registerForm: false,

      name: "member",
      email: "member@gmail.com",
      password: "member",
      address:"Taungoo",
      phno:"09111111111",
     
      errorAlert: false,
      loading: false,
    };
  },

  async created() {},

  methods: {
    async register() {
      if (this.$refs.registerForm.validate()) {
        this.errorAlert = false;

        try {
          this.loading = true;

          // API Call
          const resp = await utils.http.post("/register", {
            cname: this.name,
            email: this.email,
            password: this.password,
            address:this.address,
            phone:this.phno

          });
          if (resp.status === 200) {
            const data = await resp.json();
            if (data) {
              // Store Login Info in Vuex
              this.$store.commit("setLoginUser", data);
              // If Admin -> Go to Admin
              // If User -> Go to Home
              if (data.role == "admin") {
                this.$router.push({ path: "/admin" });
              } else {
                this.$router.push({ path: "/" });
              }
            }
          } else {
            this.errorAlert = true;
          }
        } catch (error) {
          console.log(error);
        } finally {
          this.loading = false;
        }
      }
    },
  },
};
</script>
