<template>
  <v-card class="pa-4 mb-4" v-if="stelle">
    <v-card-title>
      <div class="d-flex justify-space-between align-center w-100">
        <div>
          <h2>{{ stelle.titel }}</h2>
          <p class="text-subtitle-1">
            Standort: {{ stelle.standort }}
            Entgeltgruppe: {{ stelle.entgeltgruppe }}
            Referat: {{ stelle.referat }}
            Frist: {{ stelle.bewerbungsfrist }}
          </p>
        </div>

        <!-- Merken nur für offene Stellen -->
        <v-btn
          v-if="stelle.status === 'OFFEN'"
          :color="stelle.gemerkt ? 'success' : 'primary'"
          outlined
          class="mr-4"
          @click="$emit('merken', stelle.id)"
        >
          {{ stelle.gemerkt ? 'Gemerkte Stelle' : 'Merken' }}
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
          {{ tag.name || tag }}
        </v-chip>
      </div>

      <!-- Kontakt -->
      <div class="mt-4" v-if="stelle.servicebereichsleiter">
        <p><strong>Kontakt:</strong> {{ stelle.servicebereichsleiter.name || stelle.servicebereichsleiter }}</p>
      </div>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
interface Tag {
  id?: number
  name?: string
}

interface Servicebereichsleiter {
  id?: number
  kontaktperson?: string
  name?: string
  email?: string
  bereich?: string
  telefonnummer?: string
}

interface Stelle {
  id: number
  titel: string
  beschreibung: string
  standort: string
  entgeltgruppe: string
  referat: string
  status: 'OFFEN' | 'GESCHLOSSEN'
  bewerbungsfrist?: string
  tags?: Tag[] | string[]
  servicebereichsleiter?: Servicebereichsleiter | string
  gemerkt?: boolean
}

// Props & Emits
const props = defineProps<{
  stelle: Stelle
}>()
const emit = defineEmits<{ (e: 'merken', id: number): void }>()
</script>

<style scoped>
</style>
