<template>
  <v-card class="mb-4">
    <v-card-title class="d-flex justify-space-between align-center">
      <span class="text-h6">{{ job.title }}</span>
      <v-btn
        :color="isBookmarked ? 'warning' : 'primary'"
        :variant="isBookmarked ? 'tonal' : 'outlined'"
        size="small"
        @click="toggleBookmark"
      >
        {{ isBookmarked ? 'Entfernen' : 'Merken' }}
      </v-btn>
    </v-card-title>
    <v-card-text>
      <v-row>
        <v-col cols="6">Datum: {{ job.date }}</v-col>
        <v-col cols="6">Entgeltgruppe: {{ job.payGrade }}</v-col>
        <v-col cols="6">Art: {{ job.type }}</v-col>
        <v-col cols="6">Vertrag: {{ job.contractType }}</v-col>
        <v-col cols="12">Bereich: {{ job.area }}</v-col>
      </v-row>

      <p class="job-section"></p>
      <strong class="job-section">Erwartungen:</strong>
      <v-divider></v-divider>
      <p class="job-section">{{ job.expectations }}</p>

      <strong class="job-section">Anforderungen:</strong>
      <v-divider></v-divider>
      <p class="job-section">{{ job.requirements }}</p>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'


interface Job {
  title: string
  date: string
  type: string
  contractType: string
  payGrade: string
  start: string
  area: string
  expectations: string
  requirements: string
}

const props = defineProps<{
  job: Job
}>()

const isBookmarked = ref(false)

function toggleBookmark() {
  isBookmarked.value = !isBookmarked.value
  // Optional: Emit für globale Bookmark-Verwaltung
  // emit('bookmark-changed', { job: props.job, bookmarked: isBookmarked.value })
}
</script>

<style scoped>
p {
  margin-bottom: 8px;
}
.job-section {
  margin-top: 24px;
  margin-bottom: 24px;
}

</style>
