<template>
  <v-navigation-drawer clipped app>
    <v-list>
      <template v-for="(link, index) in links" active-class="red--text">
        <v-list-item :key="index" v-if="!link.subLinks" link @click="goToRoute(link.path)">
          <v-list-item-icon>
            <v-icon>{{ link.icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>{{ link.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-group :key="index" v-else>
          <template v-slot:activator>
            <v-icon>{{ link.icon }}</v-icon>
            <v-list-item-title>{{ link.title }}</v-list-item-title>
          </template>
          <v-list-item v-for="(sublink, subIndex) in link.subLinks" :key="subIndex" link @click="goToRoute(sublink.path)">
            <v-list-item-content>
              <v-list-item-title>{{ sublink.title }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-group>
      </template>
    </v-list>
  </v-navigation-drawer>
</template>
<script>
export default {
  data() {
    return {
      links: [
        {
          title: "Dashboard",
          icon: "mdi-home",
          path: "/dashboard",
        },
        {
          title: "Proucts",
          icon: "mdi-folder",
          subLinks: [
            {
              title: "Add Product",
              path: "/product_create",
            },
            {
              title: "Product List",
              path: "/productlist2",
            },
            {
              title: "Categories",
              path: "/product_category",
            },
          ],
        },
        {
          title: "Orders",
          icon: "list ",
          path: "/orderlist",
        },
        {
          title: "Statistics",
          icon: "mdi-account-supervisor ",
          path: "/orderlist",
        },
        {
          title: "Currency Exchange",
          icon: "mdi-account-supervisor ",
          path: "/product_create",
        },
      ],
    };
  },
  methods: {
    goToRoute(path) {
      // If Current Path is same with Clicked Path, No Go to Route
      if (this.$route.path != path) {
        this.$router.push({ path: path });
      }
    },
  },
};
</script>