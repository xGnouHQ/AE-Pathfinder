<template>
  <v-chip
    v-if="score !== null && score > 0"
    color="green"
    variant="flat"
    class="font-weight-medium"
  >
    Matchscore {{ score.toFixed(2) }} %
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
import { ref, onMounted, watch } from 'vue'
import axios from 'axios'

interface NwkExperience { id: number }
interface Stelle { id: number }

const props = defineProps<{
  job: Stelle
  profile: NwkExperience
}>()

const score = ref<number | null>(null)

const fetchScore = async () => {
  if (!props.profile?.id || !props.job?.id) {
    score.value = 0
    return
  }

  try {
    const response = await axios.get(
      `http://localhost:8080/api/matching/${props.profile.id}/${props.job.id}`
    )
    // Backend liefert double zwischen 0 und 100
    score.value = Number(response.data ?? 0)
  } catch (error) {
    console.error('Fehler beim Laden des Matching-Scores:', error)
    score.value = 0
  }
}

onMounted(fetchScore)
watch(() => [props.job.id, props.profile.id], fetchScore)
</script>
