<template>
  <v-container v-if="bewerbung">
    <h1>Bewerbung: {{ bewerbung.stelle?.titel || 'Lädt...' }}</h1>

    <!-- Bewerbungsdetails anzeigen -->
    <BaseCardApplicationProcess
      :job="bewerbung"
      @open-message="dialogOpen = true"
    />

    <!-- Dialog nur anzeigen, wenn akzeptiert -->
    <BaseDialogMessage v-model="dialogOpen" />

    <v-btn color="primary" @click="goBack" class="mt-4">
      Zurück zur Übersicht
    </v-btn>
  </v-container>

  <!-- Loading / Info -->
  <v-container v-else>
    <v-alert type="info">
      Bewerbung wird geladen...
    </v-alert>
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import BaseCardApplicationProcess from '@/components/bewerbungen/BaseCardApplicationProcess.vue'
import BaseDialogMessage from '@/components/bewerbungen/BaseDialogMessage.vue'

// Interfaces
interface Servicebereichsleiter {
  id: number
  bereich: string
  kontaktperson: string
  email: string
  telefonnummer: string
}

interface Tag {
  id: number
  name: string
}

interface Stelle {
  id: number
  titel: string
  standort: string
  beschreibung?: string
  status: 'OFFEN' | 'GESCHLOSSEN'
  bewerbungsfrist?: string
  servicebereichsleiter?: Servicebereichsleiter
  tags?: Tag[]
}

interface Nachwuchskraft {
  id: number
  vorname: string
  nachname: string
  email: string
}

interface Bewerbung {
  id: number
  status: 'EINGEREICHT' | 'IN_PRUEFUNG' | 'ABGELEHNT' | 'ANGELADEN' | 'ANGENOMMEN'
  kommentar?: string
  eingereichtAm: string
  nachwuchskraft?: Nachwuchskraft
  stelle?: Stelle
}

// Router
const route = useRoute()
const router = useRouter()
const jobId = Number(route.params.id)

// Daten
const bewerbung = ref<Bewerbung | null>(null)
const dialogOpen = ref(false)
const API_BEW = 'http://localhost:8080/api/bewerbungen'

// Bewerbung laden
onMounted(async () => {
  try {
    const res = await axios.get<Bewerbung>(`${API_BEW}/${jobId}`)
    bewerbung.value = res.data
  } catch (err) {
    console.error('Fehler beim Laden der Bewerbung:', err)
    alert('Bewerbung konnte nicht geladen werden')
  }
})

// Zurück zur Übersicht
function goBack() {
  router.push('/bewerbungen/ApplicationListView')
}
</script>

<style scoped>
h1 {
  margin-bottom: 1rem;
}
</style>
