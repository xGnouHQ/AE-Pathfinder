<template>
  <v-card outlined class="mb-4">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>{{ job.title }}</span>
    </v-card-title>

    <v-divider />

    <v-card-text>
      <v-row>
        <v-col>Eingangsdatum: {{ job.date }}</v-col>
        <v-col>Entgeltgruppe: {{ job.payGrade }}</v-col>
        <v-col>Referat: {{ job.department }}</v-col>
      </v-row>
    </v-card-text>

    <v-divider/>
     <v-card-text>
           <v-row>
             <v-col>Status: <strong>{{ job.status }}</strong></v-col>
           </v-row>
    </v-card-text>

    <v-card-actions v-if="job.status === 'akzeptiert'">
      <v-btn color="primary" @click="$emit('open-message')">Message</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue'

interface Job {
  title: string
  date: string
  payGrade: string
  department: string
  status: 'eingegangen' | 'in_bearbeitung' | 'akzeptiert' | 'abgelehnt'
}

defineProps<{
  job: Job
}>()

defineEmits<{
  (e: 'open-message'): void
}>()
</script>

