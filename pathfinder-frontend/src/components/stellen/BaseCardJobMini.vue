<template>
<v-card rounded="lg" class="pa-4 mb-4 job-card" :class="{ 'beworben-card': job.beworben }">
  <v-card-title class="d-flex justify-space-between align-center">
    <div class="job-title-section">
      <p v-if="job.beworben" class="text-caption grey--text mb-1">
                     " Bereits beworben "
      </p>
      <h2>{{ job.titel }}</h2>
      <p class="text-subtitle-1">
        {{ job.standort }} | {{ job.status }} | Bewerbungsfrist: {{ formatDate(job.bewerbungsfrist) }}
      </p>
    </div>

    <div class="text-right">
      <!-- Matchscore -->
      <v-chip
        v-if="job.matchingScore && job.matchingScore > 0"
        :class="[
          job.beworben ? 'chip-beworben' : job.matchingScore < 50 ? 'chip-low' : 'chip-high'
        ]"
        variant="text"
        class="font-weight-medium"
      >
        Matchscore: {{ job.matchingScore.toFixed(2) }} %
      </v-chip>

      <v-chip
        v-else
        variant="outlined"
        class="font-weight-medium"
      >
        Keine Übereinstimmung
      </v-chip>

    </div>
  </v-card-title>
</v-card>
</template>

<script setup lang="ts">
interface Stelle {
  id: number
  titel: string
  standort: string
  status: 'OFFEN' | 'GESCHLOSSEN'
  bewerbungsfrist?: string
  matchingScore?: number
  beworben?: boolean
}

const props = defineProps<{ job: Stelle }>()

function formatDate(dateStr?: string) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleDateString('de-DE')
}
</script>

<style scoped>
.job-card {
  border: 1px solid #dcdcdc;
}

.beworben-card {
  background-color: grey;
}

.chip-beworben {
  color: white;
  border: 1px solid white;
}

.chip-low {
  border: 1px solid yellow;
  color: yellow;
}

.chip-high {
  border: 1px solid #66bb6a;
  color: #66bb6a;
}

.chip-none {
  color: grey;
  border: 1px solid grey;
}
</style>
