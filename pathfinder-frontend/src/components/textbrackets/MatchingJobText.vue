<template>
  <div>
    <h3 class="text-h6 font-weight-medium mb-3">Matching Job Offers</h3>

    <div v-if="matchingJobs.length > 0">
      <v-row>
        <v-col
          v-for="job in matchingJobs"
          :key="job.id"
          cols="12"
          md="6"
        >
          <v-card variant="outlined" class="pa-3">
            <div class="d-flex justify-space-between align-center mb-2">
              <strong>{{ job.title }}</strong>
              <v-chip color="green" variant="flat" size="small">
                {{ job.match }} % Match
              </v-chip>
            </div>
            <p class="text-body-2 mb-1">
              <strong>Department:</strong> {{ job.department }}
            </p>
            <p class="text-body-2 mb-2">{{ job.description }}</p>
            <v-btn color="primary" size="small" @click="$emit('view-job', job)">
              View Details
            </v-btn>
          </v-card>
        </v-col>
      </v-row>
    </div>
    <div v-else>
      <v-alert type="info" variant="tonal">
        No jobs with more than 70% match found.
      </v-alert>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, defineProps } from 'vue'

interface Job {
  id: number
  title: string
  department: string
  description: string
  match: number
}

const props = defineProps<{ jobs: Job[] }>()

const matchingJobs = computed(() => props.jobs)
</script>
