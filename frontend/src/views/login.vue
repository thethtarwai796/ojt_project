<template>
  <div  class="blue lighten-5">
    <container>
      <div class=" d-flex justify-center">
       
      </div>


      <div class="d-flex justify-center pt-5">
        <v-card width="50%">
          <v-card-title class="text-h6 text-md-h5 text-lg-h4">User Log in</v-card-title>
          <v-card-text>
            <v-form ref="loginForm" v-model="loginForm">
              <!-- Email -->
              <v-text-field v-model="email" :rules="[
                      (v) => !!v || 'Required',
                      (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
                    ]" label="E-mail" outlined required></v-text-field>

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
              <v-btn :disabled="!loginForm" color="#146C94" class="mr-4 white--text" @click="login()">
                <span v-if="!loading">Login</span>
                <v-progress-circular v-else indeterminate color="primary"></v-progress-circular>
              </v-btn>

              <!-- Error Msg -->
              <v-alert class="mt-3" v-show="errorAlert" dense type="error">
                Login Failed! <br />
                Email or Password is wrong!
              </v-alert>
              <br>
              <router-link class="mx-2 navlink" to="/">Not Register yet? Sign up</router-link>
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
