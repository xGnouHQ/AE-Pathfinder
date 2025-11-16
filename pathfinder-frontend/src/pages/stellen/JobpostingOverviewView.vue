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
          ></v-text-field>

    <v-container class="box">
      <v-row>
        <v-col
          v-for="stelle in stellen"
          :key="stelle.id"
          :search="search"
          cols="12"
        >
          <!-- Router-Link zur Detailseite -->
          <router-link
            :to="`/stellen/${stelle.id}/JobpostingTemplateView`"
            class="no-underline"
          >
            <BaseCardJobMini
              :job="stelle"
              :profile="nwkExperience"
              @merke="() => merkeStelle(stelle.id)"
            />
          </router-link>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>


<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import BaseCardJobMini from '@/components/stellen/BaseCardJobMini.vue'

const search = ref("")

// Backend-URL
const API_URL = 'http://localhost:8080/api/stellen'

// Profil der Nachwuchskraft (Beispieldaten)
const nwkExperience = ref({
  id: 1,
  experiences: ['Praktikum Webentwicklung', 'Backend bei Stadtverwaltung'],
  knowsProgramming: true,
  programmingLanguages: ['JavaScript', 'Python'],
  interests: ['Webentwicklung', 'Cloud', 'Datenbanken', 'IT-Sicherheit']
})

// --- TypeScript Interfaces ---
interface Tag {
  id: number
  name: string
}

interface Servicebereichsleiter {
  id: number
  name: string
}

interface Stelle {
  id: number           // ✅ long → number (TS-kompatibel)
  titel: string
  standort: string
  beschreibung: string
  tags: Tag[]
  status: 'OFFEN' | 'GESCHLOSSEN'
  bewerbungsfrist: string
  servicebereichsleiter: Servicebereichsleiter
  bewerbungen: any[]
}

// --- Reactive Variablen ---
const stellen = ref<Stelle[]>([])

// --- Methoden ---
const ladeStellen = async () => {
  try {
    const response = await axios.get(API_URL)
    stellen.value = response.data
  } catch (error) {
    console.error('Fehler beim Laden der Stellen:', error)
  }
}

// Wird beim Laden der Seite ausgeführt
onMounted(ladeStellen)

// Stelle merken (Beispiel-Funktion)
const merkeStelle = async (stellenId: number) => {
  try {
    const response = await axios.post(
      `${API_URL}/${stellenId}/merken`,
      null,
      { params: { nachwuchskraftId: nwkExperience.value.id } }
    )
    alert(response.data)
  } catch (error: any) {
    console.error('Fehler beim Merken der Stelle:', error)
    alert(error.response?.data || 'Fehler beim Merken der Stelle')
  }
}
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
