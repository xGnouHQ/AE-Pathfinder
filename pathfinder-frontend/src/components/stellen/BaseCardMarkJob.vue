<template>
  <v-card class="pa-4 mb-4">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>{{ job.titel }}</span>
      <v-btn
        small
        color="red"
        icon
        @click="$emit('remove')"
        title="Gemerkte Stelle entfernen"
      >
        <v-icon>mdi-delete</v-icon>
      </v-btn>
    </v-card-title>

    <v-card-subtitle>
      {{ job.standort || '–' }} | {{ job.servicebereichsleiter.kontaktperson || '–' }}
    </v-card-subtitle>

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

interface Stelle {
  id: number
  titel: string
  standort?: string
}

const props = defineProps<{
  job: Stelle
  profile?: any
}>()

const snackbar = ref({ show: false, message: '' })
</script>
