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

    <v-divider />

    <!-- ⭐ STATUS TRACK -->
    <v-card-text>
      <div class="d-flex align-center justify-space-between">

        <div
          v-for="(step, index) in statusSteps"
          :key="index"
          class="text-center"
          style="flex: 1;"
        >
          <!-- Icon -->
          <v-icon
            :color="step.value === job.status ? 'primary' : 'grey'"
            size="32"
          >
            {{ step.icon }}
          </v-icon>

          <!-- Label -->
          <div
            :style="{
              fontWeight: step.value === job.status ? 'bold' : 'normal',
              color: step.value === job.status ? '#1976D2' : '#555'
            }"
          >
            {{ step.label }}
          </div>

        </div>
        <!-- Button nur bei EINGELADEN -->
            <v-card-actions v-if="job.status === 'ANGELADEN'">
              <v-btn color="primary" @click="$emit('open-message')">Message</v-btn>
            </v-card-actions>
      </div>

    </v-card-text>



  </v-card>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue'

interface Job {
  title: string
  date: string
  payGrade: string
  department: string
  status: 'EINGEREICHT' | 'IN_PRUEFUNG' | 'ABGELEHNT' | 'EINGELADEN' | 'ANGENOMMEN'
}

const props = defineProps<{ job: Job }>()
defineEmits<{ (e: 'open-message'): void }>()

// ⭐ ICON + LABEL Mapping
const statusSteps = [
  { value: 'EINGEREICHT', label: 'Eingereicht', icon: 'mdi-upload' },
  { value: 'IN_PRUEFUNG', label: 'In Prüfung', icon: 'mdi-magnify' },
  { value: 'ABGELEHNT', label: 'Abgelehnt', icon: 'mdi-close-circle' },
  { value: 'ANGELADEN', label: 'Eingeladen', icon: 'mdi-calendar' },
  { value: 'ANGENOMMEN', label: 'Angenommen', icon: 'mdi-check-circle' }
]
</script>
