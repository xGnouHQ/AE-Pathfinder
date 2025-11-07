<template>
  <v-card class="pa-4 mb-4">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>{{ job.title }}</span>
      <v-btn
        small
        color="red"
        icon
        @click="removeJob"
        title="Gemerkte Stelle entfernen"
      >
        <v-icon>mdi-delete</v-icon>
      </v-btn>
    </v-card-title>

    <v-card-subtitle>
      {{ job.department }} | {{ job.payGrade }} | {{ job.date }}
    </v-card-subtitle>

    <v-card-text>{{ job.description }}</v-card-text>

    <!-- Snackbar -->
    <v-snackbar v-model="snackbar.show" :timeout="2500" top right>
      {{ snackbar.message }}
      <template #actions>
        <v-btn text @click="snackbar.show = false">Schließen</v-btn>
      </template>
    </v-snackbar>
  </v-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'

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
  savedJobs: Job[] // Array, aus dem die Stelle entfernt wird
}>()

const snackbar = ref({ show: false, message: '' })

function removeJob() {
  const index = props.savedJobs.findIndex(j => j.id === props.job.id)
  if (index !== -1) {
    props.savedJobs.splice(index, 1)
    snackbar.value.message = `Stelle "${props.job.title}" wurde aus der Merk-Liste entfernt.`
    snackbar.value.show = true
  }
}
</script>
