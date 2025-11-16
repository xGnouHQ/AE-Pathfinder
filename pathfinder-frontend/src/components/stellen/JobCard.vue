<template>
  <v-card class="pa-4 mb-4" v-if="stelle">
    <v-card-title>
      <div class="d-flex justify-space-between align-center w-100">
        <div>
          <h2>{{ stelle.titel }}</h2>

          <p class="text-subtitle-1"> Standort: {{ stelle.standort }}</p>
        </div>

        <!-- Merken nur für offene Stellen -->
        <v-btn
          v-if="stelle.status === 'OFFEN'"
          :color="stelle.gemerkt ? 'success' : 'primary'"
          outlined
          @click="handleMerken(stelle.id)"
        >
          {{ stelle.gemerkt ? 'Gemerkte Stelle' : 'Merken' }}
        </v-btn>
      </div>
    </v-card-title>
    <v-divider></v-divider>
    <v-card-text>

      <p> {{ stelle.beschreibung }}</p>

      <!-- Tags -->
      <v-row dense class="mt-2">
        <v-col v-for="tag in stelle.tags || []" :key="tag.id" cols="auto">
          <v-chip small outlined>{{ tag.name }}</v-chip>
        </v-col>
      </v-row>
      <p></p>

      <v-divider></v-divider>
      <p v-if="stelle.servicebereichsleiter">
        <strong>Dein/e Ansprechpartner*in:</strong>
        <p> - {{ stelle.servicebereichsleiter.bereich }} - {{ stelle.servicebereichsleiter.kontaktperson }}</p>
        <p> - E-Mail: {{ stelle.servicebereichsleiter.email }} </p>
        <p> - Telefonnummer: {{ stelle.servicebereichsleiter.telefonnummer }} </p>
      </p>
    </v-card-text>

    <!-- Snackbar für Bestätigung -->
    <v-snackbar v-model="snackbar" :timeout="3000" top right>
      Stelle erfolgreich gemerkt!
      <template #actions>
        <v-btn text @click="snackbar = false">Schließen</v-btn>
      </template>
    </v-snackbar>
  </v-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'

interface Tag { id: number; name: string }
interface Servicebereichsleiter { id: number; kontaktperson?: string; name?: string ; email?: string; bereich?: string; telefonnummer?: string }

interface Stelle {
  id: number
  titel: string
  beschreibung: string
  standort: string
  status: 'OFFEN' | 'GESCHLOSSEN'
  bewerbungsfrist?: string
  tags?: Tag[]
  servicebereichsleiter?: Servicebereichsleiter
  gemerkt?: boolean
}

const props = defineProps<{ stelle: Stelle }>()

const snackbar = ref(false)

// Formatierung Bewerbungsfrist
function formatDate(dateStr?: string) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleDateString('de-DE')
}

// Klick auf Merken-Button
function handleMerken(id: number) {
  // Emit nach Parent, damit die Stelle auch dort als gemerkt markiert wird
  props.stelle.gemerkt = true
  // Snackbar anzeigen
  snackbar.value = true
  // Event für Parent
  // @ts-ignore
  emit('merken', id)
}
</script>

<style scoped>
</style>
