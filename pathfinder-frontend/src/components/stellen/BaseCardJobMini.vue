<template>
  <v-card class="pa-4 mb-4">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>{{ job.titel }}</span>
      <!-- MatchScore einbinden -->
      <JobMatchScore :job="job" :profile="profile" />
    </v-card-title>

    <v-card-subtitle>
      {{ job.standort }} | {{ job.status }} | Bewerbungsfrist: {{ job.bewerbungsfrist }}
    </v-card-subtitle>

    <v-card-text>{{ job.beschreibung }}</v-card-text>

  </v-card>
</template>

<script setup lang="ts">
import JobMatchScore from './JobMatchScore.vue'

interface NwkExperience {
  experiences: string[]
  knowsProgramming: boolean
  programmingLanguages: string[]
  interests: string[]
}

interface Stelle {
  id: number
  titel: string
  beschreibung: string
  standort: string
  status: 'OFFEN' | 'GESCHLOSSEN'
  bewerbungsfrist: string
}

const props = defineProps<{
  job: Stelle
  profile: NwkExperience
}>()
</script>
