<template>
  <v-card class="pa-4 mb-4">
    <v-card-title class="d-flex justify-space-between align-center">
      <div>
        <h2>{{ job.titel }}</h2>
        <p class="text-subtitle-1">{{ job.standort }} | {{ job.status }} | Bewerbungsfrist: {{ formatDate(job.bewerbungsfrist) }}</p>
      </div>

      <!-- MatchScore einbinden -->
      <JobMatchScore :job="job" :profile="profile" />
    </v-card-title>

    <v-card-text>
      <p>{{ job.beschreibung }}</p>

      <!-- Tags -->
      <v-row dense class="mt-2">
        <v-col v-for="tag in job.tags || []" :key="tag.id" cols="auto">
          <v-chip small outlined>{{ tag.name }}</v-chip>
        </v-col>
      </v-row>

    </v-card-text>
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

interface Tag { id: number; name: string }
interface Servicebereichsleiter { id: number; kontaktperson?: string; name?: string }

interface Stelle {
  id: number
  titel: string
  beschreibung: string
  standort: string
  status: 'OFFEN' | 'GESCHLOSSEN'
  bewerbungsfrist?: string
  tags?: Tag[]
  gemerkt?: boolean
}

const props = defineProps<{
  job: Stelle
  profile: NwkExperience
}>()

function formatDate(dateStr?: string) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleDateString('de-DE')
}
</script>

<style scoped>
</style>
