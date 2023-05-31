<template>
  <div>
    <div class=" d-flex justify-center mt-10 mb-10">
      <img src="../assets/mainlolo.jpeg" style="height: 70px;border-radius: 20px;"></img>
      <h2 class="display-3">
        ShopaHolic</h2>
    </div>
    <!-- Profile Card -->
    <v-card class="mx-auto pa-3 text-center" max-width="40%">
      <h1>Your Profile</h1>
       <v-text-field style="font-size: larger;font-weight: bold;" v-model="cname" outlined label="Name" @change="profileupdate"/>
       <v-text-field style="font-size: larger;font-weight: bold;" v-model="email" outlined label="Email"/>
       <v-text-field style="font-size: larger;font-weight: bold;" v-model="phno" outlined label="Phone Number"/>
       <v-text-field style="font-size: larger;font-weight: bold;" v-model="address" outlined label="Address"/>

      <!-- Change Pwd Btn -->
      <v-btn text color="pink accent-4" @click="changePwd()">
        Change Password
      </v-btn><br>
      <v-btn :disabled="!save" text class="pink white--text" @click="" width="100%">
        Save Profile
      </v-btn>

      
    </v-card>

    <!-- change password Dialog -->
    <v-dialog v-model="changepwddialog" max-width="800">
      <v-card>
        <!-- Dialog Title -->
        <v-toolbar class="pink white--text">
          <div>Password update?</div>
          <v-spacer></v-spacer>
          <!-- Close Btn -->
          <v-btn icon @click="recordDialog=false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <!-- Dialog Content -->
        <form>
          <v-text-field v-show="false" v-model="ori_id" hidden></v-text-field>
  
          <!-- Current Password -->
          <v-text-field
            v-model="current_pwd"
            :counter="10"
            label="Current Password"
            required
          ></v-text-field>
  
          <!-- New Password -->
          <v-text-field
            v-model="new_pwd"
            label="New Password"
            required
          ></v-text-field>
  
          <!-- Confirm New Password -->
          <v-text-field
            v-model="con_new_pwd"
            label="Confirm New Password"
            required
          ></v-text-field>
  
          <!-- Submit Btn -->
          <v-btn class="mr-4" @click="submit"> submit </v-btn>
  
          <!-- Clear Btn -->
          <v-btn @click="clear"> clear </v-btn>
  
          <!-- Error Alert -->
          <v-alert class="mt-3" v-show="errorAlert" dense type="error">
            {{ errMsg }} <br />
          </v-alert>
        </form>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "profile",
  data() {
    return {
      loginUser: {},
      save:false,
      con_new_pwd: "",
      new_pwd: "",
      ori_id: "",
      current_pwd: "",
      errorAlert: false,
      errMsg: "",
      changepwddialog:false,
    };
  },

  created() {
    this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () => {
        return this.$store.state.loginUser;
      },
      (newVal, oldVal) => {
        this.loginUser = newVal;
      },
      {
        deep: true,
      }
    );
  },
  computed:{
    cname(){return this.loginUser.cname},
    email(){return this.loginUser.email},
    phno(){return this.loginUser.phone},
    address(){return this.loginUser.address},

},

  methods: {
    changePwd() {
    this.changepwddialog=true;
    },
    profileupdate(){
      this.save=true;
    },
    async submit() {
      const resp = await utils.http.post("/changePwd", {
        ori_id: this.loginUser.cid,
        current_pwd: this.current_pwd,
        new_pwd: this.new_pwd,
        con_new_pwd: this.con_new_pwd,
      });
      if (resp.status === 200) {
        this.$router.push({ path: "/" });
      } else {
        this.errorAlert = true;
        const data = await resp.json();
        this.errMsg = data.message;
      }
    },

    clear() {
      this.current_pwd = "";
      this.new_pwd = "";
      this.con_new_pwd = "";
    },
    
  },
};
</script>
