<template>
  <v-container v-if="loggedIn">
    <h1 class="mb-6">Offene Stellen</h1>

    <v-text-field
      v-model="search"
      label="Search"
      prepend-inner-icon="mdi-magnify"
      variant="outlined"
      hide-details
      single-line
    />

    <v-container class="box">
      <v-row>
        <v-col
          v-for="stelle in filteredStellen"
          :key="stelle.id"
          cols="12"
        >
          <router-link
            :to="`/stellen/${stelle.id}/JobpostingTemplateView`"
            class="no-underline"
          >
            <BaseCardJobMini
              :job="stelle"
              @merke="() => merkeStelle(stelle.id)"
            />
          </router-link>
        </v-col>
      </v-row>
    </v-container>
  </v-container>

  <!-- Login Weiterleitung, falls nicht eingeloggt -->
  <div v-else>
    <p>Bitte einloggen...</p>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import BaseCardJobMini from '@/components/stellen/BaseCardJobMini.vue'

const router = useRouter()
const search = ref("")
const stellen = ref<any[]>([])
const profileId = ref<number | null>(null)
const loggedIn = ref(false)
const API_URL = '/api/stellen'

// Prüfen, ob Nutzer eingeloggt ist (SessionStorage)
onMounted(() => {
  loggedIn.value = sessionStorage.getItem('loggedIn') === 'true'

  if (!loggedIn.value) {
    router.replace('/login') // Weiterleitung zum Login
    return
  }

  const userJson = sessionStorage.getItem("user")
  if (userJson) {
    const userData = JSON.parse(userJson)
    profileId.value = userData.id
    ladeStellen()
  } else {
    console.error("Kein eingeloggter Nutzer gefunden")
  }
})

// ------------------ Lade alle Stellen ------------------
const ladeStellen = async () => {
  if (!profileId.value) return
  try {
    const response = await axios.get(API_URL)
    stellen.value = response.data
    for (const stelle of stellen.value) {
      await ladeMatchingScore(stelle)
    }
  } catch (error) {
    console.error("Fehler beim Laden der Stellen:", error)
  }
}

// ------------------ Matching Score ------------------
const ladeMatchingScore = async (stelle: any) => {
  if (!profileId.value) return
  try {
    const response = await axios.get(
      `/api/matching/${profileId.value}/${stelle.id}`
    )
    stelle.matchingScore = response.data
  } catch (error) {
    console.error(`Fehler beim Matching-Score für Stelle ${stelle.id}:`, error)
    stelle.matchingScore = 0
  }
}

// ------------------ Merkfunktion ------------------
const merkeStelle = async (stellenId: number) => {
  if (!profileId.value) return alert("Kein eingeloggter Nutzer gefunden")
  try {
    const response = await axios.post(
      `${API_URL}/${stellenId}/merken`,
      null,
      { params: { nachwuchskraftId: profileId.value } }
    )
    alert(response.data)
  } catch (error: any) {
    alert(error.response?.data || "Fehler beim Merken der Stelle")
  }
}

// ------------------ Gefilterte Stellen ------------------
const filteredStellen = computed(() => {
  return stellen.value
    .filter(s =>
      s.titel.toLowerCase().includes(search.value.toLowerCase()) ||
      s.beschreibung.toLowerCase().includes(search.value.toLowerCase())
    )
    .sort((a, b) => (b.matchingScore ?? 0) - (a.matchingScore ?? 0))
})
</script>

<style scoped>
.box {
  margin: 2% 1% 1%;
  border: 2px solid #0000001a;
}
.no-underline {
  text-decoration: none;
  color: inherit;
}
</style>
