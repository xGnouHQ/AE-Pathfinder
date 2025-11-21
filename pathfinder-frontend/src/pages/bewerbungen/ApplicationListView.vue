<template>
  <v-container>
    <h1 class="mb-6">Meine Bewerbungen</h1>

    <v-container class="box">
      <v-row>
        <v-col v-for="bewerbung in bewerbungen" :key="bewerbung.id" cols="12">
          <BaseCardApplicationMini
            :bewerbung="bewerbung"
            @detail="goToDetail"
            @withdraw="handleWithdraw"
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

interface Stelle {
  id: number
  titel: string
  standort: string
  bewerbungsfrist?: string
}

interface Bewerbung {
  id: number
  status: 'EINGEREICHT' | 'IN_PRUEFUNG' | 'ABGELEHNT' | 'ANGELADEN' | 'ANGENOMMEN'
  kommentar?: string
  eingereichtAm: string
  nachwuchskraftId: number
  stelleId: number
  stelle?: Stelle
}

const router = useRouter()
const nwkId = ref<number | null>(null)  // Dynamisch aus Login
const bewerbungen = ref<Bewerbung[]>([])
const API_BEW = 'http://localhost:8080/api/bewerbungen'

// Bewerbungen laden
const ladeBewerbungen = async () => {
  if (!nwkId.value) return
  try {
    const res = await axios.get<Bewerbung[]>(`${API_BEW}/nachwuchskraft/${nwkId.value}`)
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

// Bewerbung zurückziehen
const handleWithdraw = async (id: number) => {
  if (!nwkId.value) return
  try {
    await axios.delete(`${API_BEW}/${id}`)
    alert("Bewerbung erfolgreich zurückgezogen")
    bewerbungen.value = bewerbungen.value.filter(b => b.id !== id)
  } catch (err) {
    console.error('Fehler beim Zurückziehen der Bewerbung:', err)
    alert("Fehler beim Zurückziehen der Bewerbung")
  }
}

// onMounted: Nachwuchskraft-ID aus localStorage laden
onMounted(() => {
  const userJson = localStorage.getItem('user')
  if (userJson) {
    const userData = JSON.parse(userJson)
    nwkId.value = userData.id
    ladeBewerbungen()
  } else {
    console.error('Kein eingeloggter Nutzer gefunden')
  }
})
</script>

<style scoped>
.box {
  margin: 2% 1% 1%;
  border: 2px solid #0000001a;
  padding: 16px;
}
</style>
