<template>
  <v-app>
    <v-app-bar image="@/assets/webcover0.png">
      <v-row align="center">
        <v-col class="d-flex align-center justify-start" cols="3">
          <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
          <router-link to="/">
            <img
              alt="Logo der Applikation"
              class="mt-1 mr-1"
              height="50"
              src="@/assets/Pathfinder_Logo_1.0.png"
              width="50"
            />
          </router-link>
          <router-link to="/">
            <v-toolbar-title class="font-weight-bold">
              <span class="text-black">Pathfinder</span>
            </v-toolbar-title>
          </router-link>
        </v-col>
        <v-col class="d-flex align-center justify-center" cols="6" />
        <v-col class="d-flex align-center justify-end" cols="3">
          <v-btn color="error" variant="outlined" @click="logout" size="small">
            Logout
          </v-btn>
        </v-col>
      </v-row>
    </v-app-bar>
    <v-navigation-drawer v-model="drawer" app class="drawer-background">
          <v-list>
            <v-list-item v-for="item in menuItems" :key="item.title" @click="">
              <router-link :to="item.route" class="no-underline">
                <v-list-item-content>
                  <v-list-item-title>{{ item.title }}</v-list-item-title>
                </v-list-item-content>
              </router-link>
            </v-list-item>
          </v-list>
    </v-navigation-drawer>
    <v-main class="main-background">

        <router-view v-slot="{ Component }">
          <v-fade-transition mode="out-in">
            <component :is="Component" />
          </v-fade-transition>
        </router-view>

      <v-footer app class="customfooter">
        Gestalte deine Zukunft mit ~ Pathfinder !
      </v-footer>
    </v-main>
  </v-app>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const drawer = ref(true);
    const menuItems = ref([
      { title: 'Startseite', route: '/' },
      { title: 'Stellenportal', route: '/stellen/JobpostingOverviewView' },
      { title: 'Bewerbungen', route: '/bewerbungen/ApplicationListView' },
      { title: 'Mein Konto', route: '/nachwuchskraefte/NwkUserinformationView' },
      { title: 'Meine Liste', route: '/merken/BookmarkView' },
    ]);

  const router = useRouter();

      function logout() {
        // Beispiel: Clear localStorage / auth tokens
        localStorage.clear(); // oder z.B. localStorage.removeItem("token")

        // Weiterleitung zur Login-Seite
        router.push('/login');
      }

      return { drawer, menuItems, logout };
      },
};

</script>

<style scoped>

.main-background {
  background-color: #ffffff;
  color: #000000;
  min-height: 100vh;
}

.v-app-bar {
  color: "#FFCC00";
}

.drawer-background {
  background-color: #EEE9E9 ;
}

.v-navigation-drawer .v-list-item-title {
  font-size: 16px;
  padding-bottom: 1px;
  color: #000000;
}

.no-underline,
.no-underline:hover {
  text-decoration: none;
}

.customfooter {
  justify-content: center;
}

</style>
