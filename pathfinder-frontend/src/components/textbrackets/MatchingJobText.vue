<template>
  <div>
    <h3 class="text-h6 font-weight-medium mb-3">Stellenempfehlungen</h3>

    <div v-if="matchingJobs.length > 0">
      <v-row>
        <v-col
          class="card-hover"
          v-for="job in matchingJobs"
          :key="job.id"
          cols="12"
          md="6"
        >
          <v-card variant="outlined" class="pa-3" @click="$emit('view-job', job)">
            <div class="d-flex justify-space-between align-center mb-2">
              <strong>{{ job.titel }}</strong>
              <v-chip
                border="success sm opacity-100"
                color="green"
                ize="small"
                variant="text"
                v-if="job.matchingScore !== undefined && job.matchingScore > 0"
              >
               MatchScore {{ job.matchingScore.toFixed(2) }} %
              </v-chip>
            </div>
          </v-card>
        </v-col>
      </v-row>
    </div>

    <div v-else>
      <v-alert type="info" variant="tonal">
        No jobs with more than 50% match found.
      </v-alert>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, defineProps } from 'vue'

interface Job {
  id: number
  titel: string
  beschreibung: string
  matchingScore?: number
}

const props = defineProps<{ jobs: Job[] }>()

// Filter: nur Jobs mit MatchingScore > 50%
const matchingJobs = computed(() =>
  props.jobs.filter(job => (job.matchingScore ?? 0) > 50)
)
</script>

<style scoped>

.card-hover {
  transition: transform 0.2s, box-shadow 0.2s;
}

.card-hover:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
  cursor: pointer;
}
</style>
