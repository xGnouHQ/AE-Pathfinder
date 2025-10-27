<template>
  <v-chip
    v-if="matchPercent > 0"
    color="green"
    variant="flat"
    class="font-weight-medium"
  >
    Matchscore {{ matchPercent }} %
  </v-chip>

  <v-chip
    v-else
    color="grey"
    variant="outlined"
    class="font-weight-medium"
  >
    Keine Übereinstimmung
  </v-chip>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Job {
  title: string
  description: string
}

interface NwkExperience {
  experiences: string[]
  knowsProgramming: boolean
  programmingLanguages: string[]
  interests: string[]
}

const props = defineProps<{
  job: Job
  profile: NwkExperience
}>()

// Matching-Logik (berechnet den Prozentwert)
function calculateMatch(job: Job, profile: NwkExperience): number {
  const text = `${job.title} ${job.description}`.toLowerCase()

  const keywords = [
    ...profile.experiences,
    ...profile.interests,
    ...(profile.knowsProgramming ? profile.programmingLanguages : [])
  ]
    .filter(Boolean)
    .map((s: string) => s.toLowerCase())

  if (keywords.length === 0) return 0

  const matches = keywords.filter((kw) => text.includes(kw)).length
  return Math.round((matches / keywords.length) * 100)
}

const matchPercent = computed(() => calculateMatch(props.job, props.profile))
</script>
