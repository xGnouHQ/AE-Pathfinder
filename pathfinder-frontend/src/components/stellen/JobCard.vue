<template>
  <v-card class="pa-4 mb-4" v-if="stelle">
    <v-card-title>
      <div class="d-flex justify-space-between align-center w-100">
        <div>
          <h2>{{ stelle.titel }}</h2>
          <p class="text-subtitle-1">Standort: {{ stelle.standort }}</p>
        </div>

        <!-- Merken nur für offene Stellen -->
        <v-btn
          v-if="stelle.status === 'OFFEN'"
          :color="gemerkt ? 'success' : 'primary'"
          outlined
          @click="handleMerken(stelle.id)"
        >
          {{ gemerkt ? 'Gemerkte Stelle' : 'Merken' }}
        </v-btn>
      </div>
    </v-card-title>

    <v-divider></v-divider>

    <v-card-text>
      <p>{{ stelle.beschreibung }}</p>

      <!-- Tags -->
      <div v-if="stelle.tags && stelle.tags.length">
        <v-chip
          v-for="(tag, i) in stelle.tags"
          :key="i"
          color="primary"
          class="mr-2"
        >
          {{ tag }}
        </v-chip>
      </div>

      <!-- Servicebereichsleiter unverändert -->
      <div class="mt-4" v-if="stelle.servicebereichsleiter">
        <p><strong>Kontakt:</strong> {{ stelle.servicebereichsleiter }}</p>
      </div>
    </v-card-text>

    <!-- Snackbar -->
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
import axios from 'axios'

interface Tag { id: number; name: string }
interface Servicebereichsleiter { id?: number; kontaktperson?: string; name?: string; email?: string; bereich?: string; telefonnummer?: string }

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

// Props & Emit
const props = defineProps<{ stelle: Stelle }>()
const emit = defineEmits<{ (e: 'merken', id: number): void }>()

// Lokaler gemerkt-Status
const gemerkt = ref(props.stelle.gemerkt ?? false)
const snackbar = ref(false)

// NWK-ID aus SessionStorage
const nwkId = ref<number | null>(null)
const userJson = sessionStorage.getItem('user')
if (userJson) {
  const userData = JSON.parse(userJson)
  nwkId.value = userData.id
} else {
  console.error('Kein eingeloggter Nutzer gefunden')
}

// Klick auf Merken-Button
const handleMerken = async (stellenId: number) => {
  if (!nwkId.value) {
    console.error('NWK-ID fehlt, kann Stelle nicht merken')
    return
  }

  try {
    // POST an Backend mit nachwuchskraftId als Request-Parameter
    await axios.post(`/api/stellenportal/${stellenId}/merken`, null, {
      params: { nachwuchskraftId: nwkId.value }
    })

    gemerkt.value = true
    snackbar.value = true
    emit('merken', stellenId)
  } catch (err) {
    console.error('Fehler beim Merken der Stelle:', err)
    snackbar.value = true
  }
}
</script>

<style scoped>
</style>
