<template>
  <v-card class="mb-4" @click="goToDetail">
    <v-card-title class="d-flex justify-space-between align-center">
      <span class="text-h6">
        Bewerbung ID: {{ bewerbung.id }} – {{ bewerbung.stelle?.titel || 'Lädt...' }}
      </span>
      <ApplicationStatusDisplay :status="bewerbung.status" />
    </v-card-title>

    <v-divider />

    <v-card-text>
      <v-row>
        <v-col>Eingereicht: {{ formatDate(bewerbung.eingereichtAm) }}</v-col>
        <v-col>Standort: {{ bewerbung.stelle?.standort || '–' }}</v-col>
        <v-col>
          Bewerbungsfrist:
          {{ bewerbung.stelle?.bewerbungsfrist ? formatDate(bewerbung.stelle.bewerbungsfrist) : '–' }}
        </v-col>
      </v-row>

      <!-- Zurückziehen Button -->
      <v-btn
        color="error"
        variant="text"
        class="mt-2"
        @click.stop="emitWithdraw"
        size="small"
      >
        Bewerbung zurückziehen
      </v-btn>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue'
import ApplicationStatusDisplay from '@/components/bewerbungen/ApplicationStatusDisplay.vue'

interface Stelle {
  id: number
  titel: string
  standort: string
  bewerbungsfrist?: string
}

interface Bewerbung {
  id: number
  status: 'EINGEREICHT' | 'IN_PRUEFUNG' | 'ABGELEHNT' | 'ANGELADEN' | 'ANGENOMMEN'
  kommentar?: string
  eingereichtAm: string
  nachwuchskraftId: number
  stelleId: number
  stelle?: Stelle
}

const props = defineProps<{ bewerbung: Bewerbung }>()
const emit = defineEmits<{
  (e: 'withdraw', id: number): void
  (e: 'detail', id: number): void
}>()

const formatDate = (dateString: string) => new Date(dateString).toLocaleDateString()

// Event zum Zurückziehen
const emitWithdraw = () => {
  const confirmDelete = confirm("Möchten Sie diese Bewerbung wirklich zurückziehen?")
  if (!confirmDelete) return
  emit('withdraw', props.bewerbung.id)
}

// Event zur Detailansicht
const goToDetail = () => {
  emit('detail', props.bewerbung.id)
}
</script>
