<template>
  <v-container>
    <h1 class="mb-6">Meine Bewerbungen</h1>

    <v-container class="box">
      <v-row>
        <v-col v-for="bewerbung in bewerbungen" :key="bewerbung.id" cols="12">
          <BaseCardApplicationMini
            :bewerbung="bewerbung"
            @click="goToDetail(bewerbung.id)"
          />
        </v-col>

        <v-col v-if="bewerbungen.length === 0" cols="12">
          <p>Keine Bewerbungen für diese Nachwuchskraft gefunden.</p>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import BaseCardApplicationMini from '@/components/bewerbungen/BaseCardApplicationMini.vue'

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
const router = useRouter()
const nwkId = 1
const bewerbungen = ref<Bewerbung[]>([])
const API_BEW = 'http://localhost:8080/api/bewerbungen'

// Bewerbungen laden
const ladeBewerbungen = async () => {
  try {
    const res = await axios.get<Bewerbung[]>(`${API_BEW}/nachwuchskraft/${nwkId}`)
    bewerbungen.value = res.data
  } catch (err) {
    console.error('Fehler beim Laden der Bewerbungen:', err)
    alert('Fehler beim Laden der Bewerbungen')
  }
}

// Weiterleitung zur Detailansicht
const goToDetail = (id: number) => {
  router.push(`/bewerbungen/${id}/ApplicationView`)
}

onMounted(ladeBewerbungen)
</script>

<style scoped>
.box {
  margin: 2% 1% 1%;
  border: 2px solid #0000001a;
  padding: 16px;
}
</style>
