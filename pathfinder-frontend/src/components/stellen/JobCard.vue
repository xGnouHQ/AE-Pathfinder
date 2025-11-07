<template>
  <v-card class="mb-4">
    <v-card-title class="d-flex justify-space-between align-center">
      <span class="text-h6">{{ job.title }}</span>

      <!-- Bookmark Button -->
      <BaseButtonMarkJob :jobId="job.id" />
    </v-card-title>

    <v-card-text>
      <v-row>
        <v-col cols="6">Datum: {{ job.date }}</v-col>
        <v-col cols="6">Entgeltgruppe: {{ job.payGrade }}</v-col>
        <v-col cols="6">Art: {{ job.type }}</v-col>
        <v-col cols="6">Vertrag: {{ job.contractType }}</v-col>
        <v-col cols="12">Bereich: {{ job.area }}</v-col>
      </v-row>

      <strong class="job-section">Erwartungen:</strong>
      <v-divider></v-divider>
      <p class="job-section">{{ job.expectations }}</p>

      <strong class="job-section">Anforderungen:</strong>
      <v-divider></v-divider>
      <p class="job-section">{{ job.requirements }}</p>

      <strong class="job-section">Verantwortlicher:</strong>
      <v-divider></v-divider>
      <p class="job-section">
        {{ job.responsible.name }} –
        <a :href="`mailto:${job.responsible.email}`">{{ job.responsible.email }}</a>
      </p>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import BaseButtonMarkJob from '@/components/common/BaseButtonMarkJob.vue'

interface Responsible {
  name: string
  email: string
}

interface Job {
  id: number
  title: string
  date: string
  type: string
  contractType: string
  payGrade: string
  start: string
  area: string
  expectations: string
  requirements: string
  responsible: Responsible
}

const props = defineProps<{ job: Job }>()
</script>

<style scoped>
.job-section {
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
}
</style>
