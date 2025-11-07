<template>
  <v-card>
    <v-card-title class="d-flex justify-space-between align-center">
      <span>Persönliche Daten</span>
    </v-card-title>

    <v-divider></v-divider>

    <v-card-text>
      <v-row>
        <v-col>Personalnummer: {{ nwk.personalnumber }}</v-col>
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
  </v-card>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

interface Nachwuchs {
  personalnumber: string
  surename: string
  firstname: string
  mail: string
  year: string
  major: string
  departments: string
}

defineProps<{ editable?: boolean }>()
defineEmits<{ (e: 'edit'): void }>()

const nwk = ref<Nachwuchs>({
  personalnumber: '20231057',
  surename: 'Mustermann',
  firstname: 'Max',
  mail: 'max.mustermann@muenchen.de',
  year: '2023/2026',
  major: 'Verwaltungsinformatik',
  departments: 'IT@M, Kommunalreferat GPAM, Kreisverwaltungsreferat'
})

// Bevorzugte Abteilungen als Liste (max 6)
const departmentList = computed(() => {
  if (!nwk.value.departments) return []
  return nwk.value.departments
    .split(/[;,\\n]/)
    .map(item => item.trim())
    .filter(item => item.length > 0)
    .slice(0, 6)
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
