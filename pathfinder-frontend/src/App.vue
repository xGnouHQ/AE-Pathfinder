<template>
  <v-app v-if="loggedIn">
    <v-app-bar image="@/assets/webcover0.png">
      <v-row align="center" class="w-100">
        <!-- Linke Seite: Logo + Titel -->
        <v-col class="d-flex align-center" cols="auto">
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
            <v-toolbar-title class="font-weight-bold text-black">
              Pathfinder
            </v-toolbar-title>
          </router-link>
        </v-col>

        <!-- Spacer -->
        <v-spacer></v-spacer>

        <!-- Rechte Seite: Power Button mit Abstand zum Rand -->
        <v-col class="d-flex align-center justify-end" cols="auto" style="padding-right: 16px;">
          <BaseButtonLogout :onLogout="logout" />
        </v-col>
      </v-row>
    </v-app-bar>


    <v-navigation-drawer v-model="drawer" app class="drawer-background">
      <v-list>
        <v-list-item v-for="item in menuItems" :key="item.title">
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

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import BaseButtonLogout from '@/components/common/BaseButtonLogout.vue'

const drawer = ref(true)
const menuItems = ref([
  { title: 'Startseite', route: '/' },
  { title: 'Stellenportal', route: '/stellen/JobpostingOverviewView' },
  { title: 'Bewerbungen', route: '/bewerbungen/ApplicationListView' },
  { title: 'Mein Konto', route: '/nachwuchskraefte/NwkUserinformationView' },
  { title: 'Meine Liste', route: '/merken/BookmarkView' },
])

const loggedIn = ref(false)

onMounted(() => {
  loggedIn.value = localStorage.getItem('loggedIn') === 'true'
})

function logout() {
  localStorage.removeItem('loggedIn')
  window.location.reload() // LoginView wird neu gemountet
}
</script>

<style scoped>
.main-background {
  background-color: #ffffff;
  color: #000000;
  min-height: 100vh;
}

.drawer-background {
  background-color: #EEE9E9 ;
}

.no-underline,
.no-underline:hover {
  text-decoration: none;
}

.customfooter {
  justify-content: center;
}

</style>
