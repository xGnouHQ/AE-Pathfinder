<template>
  <v-card>
    <v-card-title class="d-flex justify-space-between align-center">
      <span>Persönliche Daten</span>
    </v-card-title>

    <v-divider></v-divider>

    <v-card-text v-if="nwk">
      <v-row>
        <v-col>Personalnummer: {{ nwk.id }}</v-col>
      </v-row>
      <v-row>
        <v-col>Nachname: {{ nwk.surename }}</v-col>
        <v-col>Vorname: {{ nwk.firstname }}</v-col>
      </v-row>
      <v-row>
        <v-col>E-Mail: {{ nwk.mail }}</v-col>
      </v-row>
      <v-row>
        <v-col>Jahrgang: {{ nwk.year }}</v-col>
        <v-col>Studienrichtung: {{ nwk.major }}</v-col>
      </v-row>

      <!-- Bevorzugte Abteilungen -->
      <v-row>
        <v-col>
          <strong>Praktika:</strong>
          <ul v-if="departmentList.length > 0" class="pl-4">
            <li v-for="(item, index) in departmentList" :key="index">{{ item }}</li>
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

interface Nachwuchs {
  id: long
  personalnummer: string
  surename: string
  firstname: string
  mail: string
  year: string
  major: string
  departments: string
}

// Props & Events
defineProps<{ editable?: boolean }>()
defineEmits<{ (e: 'edit'): void }>()

// State
const nwk = ref<Nachwuchs | null>(null)
const nwkId = 1 // hier kannst du die ID setzen, die du laden willst

// Departments als Liste
const departmentList = computed(() => {
  if (!nwk.value?.departments) return []
  return nwk.value.departments
    .split(/[;,\\n]/)
    .map(item => item.trim())
    .filter(item => item.length > 0)
    .slice(0, 6)
})

// Daten vom Backend holen
onMounted(async () => {
  try {
    const res = await fetch(`/api/meinKonto/personal/${nwkId}`)
    if (!res.ok) throw new Error(`Fehler beim Laden: ${res.status}`)

    const data = await res.json()
    // Mapping der Backend-Daten auf das Format von Nachwuchs
    nwk.value = {
      id: data.id ?? '',
      personalnummer: data.personalnummer ?? '',
      surename: data.nachname ?? '',
      firstname: data.vorname ?? '',
      mail: data.email ?? '',
      year: data.eintrittsjahr?.toString() ?? '',
      major: data.studienrichtung ?? '',
      departments: data.departments ?? '' // falls vorhanden, sonst leer
    }
  } catch (err) {
    console.error('Fehler beim Laden der Daten:', err)
  }
})
</script>

<style scoped>
.v-card-title {
  font-weight: 600;
}
ul {
  margin: 0;
  padding-left: 1.2rem;
}
li {
  list-style-type: disc;
}
</style>
