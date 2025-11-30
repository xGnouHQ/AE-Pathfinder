<template>
  <v-card outlined class="mb-4">
    <v-card-text>
      <v-row>
        <v-col>Bewerbung ID: {{ bewerbung.id }}</v-col>
      </v-row>
      <v-row>
              <v-col>Eingangsdatum: {{ formatDate(bewerbung.eingereichtAm) }}</v-col>
      </v-row>
    </v-card-text>
    <v-divider />
    <!-- ⭐ STATUS TRACK -->
    <v-card-text>
      <h1 > Bewerbungsstatusverfolgung </h1>
      <p class="mt-7"></p>
      <div class="d-flex align-center justify-space-between">
        <div
          v-for="(step, index) in statusSteps"
          :key="index"
          class="text-center"
          style="flex: 1;"
        >
          <!-- Icon -->
          <v-icon
            :color="step.value === bewerbung.status ? 'primary' : 'grey'"
            size="32"
          >
            {{ step.icon }}
          </v-icon>

          <!-- Label -->
          <div
            :style="{
              fontWeight: step.value === bewerbung.status ? 'bold' : 'normal',
              color: step.value === bewerbung.status ? '#1976D2' : '#555'
            }"
          >
            {{ step.label }}
          </div>
        </div>

        <!-- Button nur bei ANGELADEN -->
        <v-card-actions v-if="bewerbung.status === 'ANGELADEN'">
          <v-btn color="primary" @click="$emit('open-message')">Nachricht senden</v-btn>
        </v-card-actions>
      </div>
      <p class="mt-7"></p>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue'

interface Bewerbung {
  id: number
  stelleTitel: string
  eingereichtAm: string
  status: 'EINGEREICHT' | 'IN_PRUEFUNG' | 'ABGELEHNT' | 'ANGELADEN' | 'ANGENOMMEN'
}

const props = defineProps<{ bewerbung: Bewerbung }>()
defineEmits<{ (e: 'open-message'): void }>()

// ⭐ ICON + LABEL Mapping
const statusSteps = [
  { value: 'EINGEREICHT', label: 'Eingereicht', icon: 'mdi-upload' },
  { value: 'IN_PRUEFUNG', label: 'In Prüfung', icon: 'mdi-magnify' },
  { value: 'ABGELEHNT', label: 'Abgelehnt', icon: 'mdi-close-circle' },
  { value: 'ANGELADEN', label: 'Eingeladen', icon: 'mdi-calendar' },
  { value: 'ANGENOMMEN', label: 'Angenommen', icon: 'mdi-check-circle' }
]

// Hilfsfunktion Datum formatieren
const formatDate = (dateString: string) =>
  new Date(dateString).toLocaleDateString()
</script>
