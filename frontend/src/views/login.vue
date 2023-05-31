<template>
  <div>
    <container>
      <div class=" d-flex justify-center mt-10 mb-10">
        <img src="../assets/mainlolo.jpeg" style="height: 70px;border-radius: 20px;"></img>
        <h2 class="display-3">
          ShopaHolic</h2>
      </div>


      <div class="d-flex justify-center">
        <v-card width="40%">
          
          <v-card-title class="text-h6 text-md-h5 text-lg-h4 mt-10">To be Continue, please Log in</v-card-title>
          <v-card-text>
            <v-form ref="loginForm" v-model="loginForm">
              <!-- Email -->
              <v-text-field v-model="email" :rules="[
                      (v) => !!v || 'Required',
                      (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
                    ]" label="E-mail" outlined required class="mt-10"> </v-text-field>

              <!-- Password -->
              <v-text-field v-model="password" :counter="10" :rules="[
                      (v) => !!v || 'Required',
                      (v) =>
                        (v && v.length <=10) ||
                        'Password must be less than 10characters',
                    ]" :type="passwordShow ? 'text' : 'password'"
                :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'" @click:append="passwordShow = !passwordShow"
                label="Password" outlined required></v-text-field>

              <!-- Login Btn -->
              <v-btn :disabled="!loginForm" class="mr-4 mb-5 pink white--text" style="width: 100%;"  @click="login()">
                <span v-if="!loading">Login</span>
                <v-progress-circular v-else indeterminate color="primary"></v-progress-circular>
              </v-btn>

              <!-- Error Msg -->
              <v-alert class="mt-3" v-show="errorAlert" dense type="error">
                Login Failed! <br />
                Email or Password is wrong!
              </v-alert>
              <br> 
              <span style="font-size: large;">
              Don't have account?
              <router-link class="mx-2 navlink" to="/register"> Sign up</router-link>
            </span>
            </v-form>
          </v-card-text>
        
        </v-card>
      </div>



    </container>


  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "login",

  components: {},

  data() {
    return {
      loginForm: false,
      email: "",
      password: "",
      passwordShow: false,
      errorAlert: false,
      loading: false,
    };
  },

  async created() {},

  methods: {
    async login() {
      if (this.$refs.loginForm.validate()) {
        this.errorAlert = false;

        try {
          this.loading = true;

          // API Call
          const resp = await utils.http.post("/login", {
            gmail: this.email,
            password: this.password,
          });
          if (resp && resp.status === 200) {
            const data = await resp.json();
            if (data) {
              // Store Login Info in Vuex
              this.$store.commit("setLoginUser", data);
              // If Admin -> Go to Admin
              // If User -> Go to Home
              console.log(data);
              if (data.role == "admin") {
                this.$router.push({ path: "/dashboard" });
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
