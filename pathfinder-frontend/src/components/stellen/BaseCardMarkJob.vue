<template>
  <v-card class="pa-4 mb-4" @click="goToDetail" style="cursor: pointer;">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>{{ job.titel }}</span>

      <v-btn
        small
        color="red"
        icon
        @click.stop="$emit('remove')"
        title="Gemerkte Stelle entfernen"
      >
        <v-icon>mdi-delete</v-icon>
      </v-btn>
    </v-card-title>

    <v-card-subtitle>
      {{ job.standort }} | {{ job.referat }} | EG {{ job.entgeltgruppe }}
    </v-card-subtitle>

    <v-card-text>
      <p class="mt-3 text-caption grey--text">
        Gemerkt am: {{ formatDate(job.gemerktAm) }}
      </p>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

interface JobCard {
  stellenId: number
  titel: string
  standort: string
  referat: string
  entgeltgruppe: string
  tags: string[]
  gemerktAm: string
}

// Props korrekt abgreifen
const props = defineProps<{
  job: JobCard
}>()

const router = useRouter()

// Weiterleitung zur Detailseite
const goToDetail = () => {
  router.push(`/stellen/${props.job.stellenId}/JobpostingTemplateView`)
}

// Optional: Datum formatieren
const formatDate = (dateStr: string) => {
  return new Date(dateStr).toLocaleDateString('de-DE')
}
</script>

<style scoped>
.v-card {
  transition: 0.2s;
}
.v-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}
</style>
