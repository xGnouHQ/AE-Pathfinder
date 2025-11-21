<template>
  <v-container>
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
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import BaseCardJobMini from '@/components/stellen/BaseCardJobMini.vue'

// ------------------ Search ------------------
const search = ref("")

// Backend
const API_URL = 'http://localhost:8080/api/stellen'

// ------------------ Interfaces ------------------
interface Tag { id: number; name: string }

interface Stelle {
  id: number
  titel: string
  standort: string
  beschreibung: string
  tags: Tag[]
  status: string
  bewerbungsfrist: string
  matchingScore?: number
}

// ------------------ Data ------------------
const stellen = ref<Stelle[]>([])
const profileId = ref<number | null>(null)  // wird dynamisch gesetzt

// ------------------ Load all jobs ------------------
const ladeStellen = async () => {
  if (!profileId.value) return

  try {
    const response = await axios.get(API_URL)
    stellen.value = response.data

    // Matching-Score pro Stelle nachladen
    for (const stelle of stellen.value) {
      await ladeMatchingScore(stelle)
    }
  } catch (error) {
    console.error("Fehler beim Laden der Stellen:", error)
  }
}

// ------------------ Load score ------------------
const ladeMatchingScore = async (stelle: Stelle) => {
  if (!profileId.value) return
  try {
    const response = await axios.get(
      `http://localhost:8080/api/matching/${profileId.value}/${stelle.id}`
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

// ------------------ Computed (Filter + Sortierung) ------------------
const filteredStellen = computed(() => {
  return stellen.value
    .filter(s =>
      s.titel.toLowerCase().includes(search.value.toLowerCase()) ||
      s.beschreibung.toLowerCase().includes(search.value.toLowerCase())
    )
    .sort((a, b) => (b.matchingScore ?? 0) - (a.matchingScore ?? 0))
})

// ------------------ On Mounted ------------------
onMounted(() => {
  const userJson = localStorage.getItem("user")
  if (userJson) {
    const userData = JSON.parse(userJson)
    profileId.value = userData.id
    ladeStellen()
  } else {
    console.error("Kein eingeloggter Nutzer gefunden")
  }
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
