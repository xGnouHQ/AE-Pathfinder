<template>
  <v-card class="pa-4 mb-4" v-if="stelle">
    <v-card-title class="d-flex justify-space-between align-start">
      <!-- Linke Seite: Titel + Details -->
      <div class="d-flex flex-column">
        <h2>{{ stelle.titel }}</h2>
        <v-divider></v-divider>
        <v-spacer></v-spacer>
        <p class="text-subtitle-1">Stellen ID: {{ stelle.id }}</p>
        <p class="text-subtitle-1">Standort: {{ stelle.standort }}</p>
        <p class="text-subtitle-1">Entgeltgruppe: {{ stelle.entgeltgruppe }}</p>
        <p class="text-subtitle-1">Referat: {{ stelle.referat }}</p>
        <p class="text-subtitle-1">Frist: {{ stelle.bewerbungsfrist }}</p>
      </div>

      <!-- Rechte Seite: Button auf Höhe Titel -->
      <div>
        <v-btn
          v-if="stelle.status === 'OFFEN'"
          :color="stelle.gemerkt ? 'success' : 'primary'"
          outlined
          @click="$emit('merken', stelle.id)"
        >
          {{ stelle.gemerkt ? 'Gemerkte Stelle' : 'Merken' }}
        </v-btn>
      </div>
    </v-card-title>

    <v-divider></v-divider>

    <v-card-text>
      <h2> Dein Einsatzbereich </h2>
      <p class="mt-4">{{ stelle.beschreibung }}</p>
      <p class="mt-4"></p>
      <h2> Deine Tätigkeiten </h2>
      <p class="mt-4"> Lorem ipsum dolor sit amet, consectetur adipiscing elit.
              Pellentesque vitae sapien ac urna varius tincidunt. Vestibulum ante ipsum
              primis in faucibus orci luctus et ultrices posuere cubilia curae; Integer vel
              turpis a justo sollicitudin feugiat. Duis vitae orci nec metus fermentum blandit.
              Sed nec risus non quam finibus sollicitudin a ac turpis. Morbi cursus, nisl at pretium
              fringilla, nulla erat bibendum elit, at porta magna augue a lorem.
      </p>
       <p class="mt-4"></p>
      <h2> Das bringst du mit </h2>
        <p class="mt-4"></p>
        <p> - Frondula quipster impletix </p>
        <p> - Zandor flenbit koordinatus </p>
        <p> - Blorptik und merandus von skopel </p>
        <p> - Quintera analysum und frenthos </p>
        <p> - Vortex planum et lumbrik </p>
        <p>  - Snorblik kommunikat </p>
        <p class="mt-4"></p>
      <!-- Tags -->
      <div v-if="stelle.tags && stelle.tags.length">
      <strong> Tags: </strong>
        <v-chip
          v-for="(tag, i) in stelle.tags"
          :key="i"
          color="primary"
          class="mr-2"
        >
           {{ typeof tag === 'object' ? tag.name : tag }}
        </v-chip>
      </div>

      <!-- Kontakt -->
      <div class="mt-4" v-if="stelle.servicebereichsleiter">
        <p> <strong>Kontakt:</strong></p>
        <p class="mt-4"></p>
        <p> {{ typeof stelle.servicebereichsleiter === 'object' ? stelle.servicebereichsleiter.name : stelle.servicebereichsleiter }}</p>
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
