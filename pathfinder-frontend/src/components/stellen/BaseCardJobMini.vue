<template>
  <v-card class="pa-4 mb-4">
    <v-card-title class="d-flex justify-space-between align-center">
      <div>
        <h2>{{ job.titel }}</h2>
        <p class="text-subtitle-1">
          {{ job.standort }} | {{ job.status }} | Bewerbungsfrist: {{ formatDate(job.bewerbungsfrist) }}
        </p>
      </div>

      <!-- MatchScore korrekt anzeigen -->
      <v-chip
        v-if="job.matchingScore !== undefined && job.matchingScore !== null && job.matchingScore > 0"
        color="green"
        variant="flat"
        class="font-weight-medium"
      >
        Matchscore {{ job.matchingScore.toFixed(2) }} %
      </v-chip>

      <v-chip
        v-else
        color="grey"
        variant="outlined"
        class="font-weight-medium"
      >
        Keine Übereinstimmung
      </v-chip>
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
interface Tag { id: number; name: string }

interface Stelle {
  id: number
  titel: string
  beschreibung: string
  standort: string
  status: 'OFFEN' | 'GESCHLOSSEN'
  bewerbungsfrist?: string
  tags?: Tag[]
  matchingScore?: number  // <-- Double vom Backend
}

const props = defineProps<{
  job: Stelle
}>()

function formatDate(dateStr?: string) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleDateString('de-DE')
}
</script>

<style scoped></style>
