<template>
  <v-card>
    <v-card-title>Persönliche Daten</v-card-title>
    <v-divider></v-divider>

    <v-card-text v-if="nwk">
      <v-row>
        <v-col><strong>ID:</strong> {{ nwk.id }}</v-col>
        <v-col><strong>Personalnummer:</strong> {{ nwk.personalnummer }}</v-col>
      </v-row>

      <v-row>
        <v-col><strong>Nachname:</strong> {{ nwk.nachname }}</v-col>
        <v-col><strong>Vorname:</strong> {{ nwk.vorname }}</v-col>
      </v-row>

      <v-row>
        <v-col><strong>E-Mail:</strong> {{ nwk.email }}</v-col>
      </v-row>

      <v-row>
        <v-col><strong>Jahrgang:</strong> {{ nwk.jahrgang }}</v-col>
        <v-col><strong>Studienrichtung:</strong> {{ nwk.studienrichtung }}</v-col>
      </v-row>

      <v-row>
        <v-col>
          <strong>Praktika:</strong>
          <ul v-if="departmentList.length">
            <li v-for="(item, i) in departmentList" :key="i">{{ item }}</li>
          </ul>
          <span v-else>Keine Angaben</span>
        </v-col>
      </v-row>
    </v-card-text>

    <v-card-text v-else class="d-flex justify-center">
      <v-progress-circular indeterminate color="primary"></v-progress-circular>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'

// Interface passend zum Backend
interface Nachwuchskraft {
  id: number
  personalnummer: string
  vorname: string
  nachname: string
  email: string
  jahrgang: string
  studienrichtung: string
  departments?: string
}

const nwk = ref<Nachwuchskraft | null>(null)

// Departments in Array aufsplitten
const departmentList = computed(() => {
  if (!nwk.value?.departments) return []
  return nwk.value.departments
    .split(/[;,\\n]/)
    .map(d => d.trim())
    .filter(d => d.length > 0)
})

// Daten vom Backend laden
onMounted(async () => {
  const userJson = localStorage.getItem('user')
  if (!userJson) {
    console.error('Kein eingeloggter Nutzer gefunden')
    return
  }

  const userData = JSON.parse(userJson)
  const nwkId = userData.id

  try {
    const res = await fetch(`/api/meinKonto/personal/${nwkId}`)
    if (!res.ok) throw new Error(`Fehler beim Laden: ${res.status}`)
    const data = await res.json()

    // Backend Response prüfen
    console.log('Backend Response:', data)

    nwk.value = {
      id: data.id,
      personalnummer: data.personalnummer,
      vorname: data.vorname,
      nachname: data.nachname,
      email: data.email,
      jahrgang: data.jahrgang ?? data.eintrittsjahr,
      studienrichtung: data.studienrichtung,
      departments: data.departments // optional vom Backend
    }
  } catch (err) {
    console.error('Fehler beim Laden der Daten:', err)
  }
})
</script>

<style scoped>
ul { margin: 0; padding-left: 1.2rem; }
li { list-style-type: disc; }
</style>
