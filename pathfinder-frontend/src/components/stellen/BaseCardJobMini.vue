<template>
  <v-card class="pa-4 mb-4">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>{{ job.title }}</span>
      <!-- MatchScore einbinden -->
      <JobMatchScore :job="job" :profile="profile" />
    </v-card-title>

    <v-card-subtitle>
      {{ job.department }} | {{ job.payGrade }} | {{ job.date }}
    </v-card-subtitle>

    <v-card-text>{{ job.description }}</v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import JobMatchScore from './JobMatchScore.vue'

interface Job {
  id: number
  title: string
  description: string
  department: string
  payGrade: string
  date: string
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
</script>
