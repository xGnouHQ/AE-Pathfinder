<template>
  <v-card
    class="pa-4 mb-4 custom-card"
    @click="goToDetail"
    style="cursor: pointer;"
  >
    <!-- Titel -->
    <v-card-title class="pa-0 text-black">
      <div class="title-wrapper">
        {{ job.titel }}
      </div>
    </v-card-title>
    <p class="mt-4"></p>
    <v-card-subtitle class="pt-0 text-black">
      {{ job.standort }} | {{ job.referat }} | {{ job.entgeltgruppe }}
    </v-card-subtitle>

    <v-card-text class="text-black">
      <p class="text-caption">
        Gemerkt am: {{ formatDate(job.gemerktAm) }}
      </p>
    </v-card-text>
    <!-- Button unten rechts -->
    <div class="d-flex justify-end mt-2">
      <v-btn
        small
        color="red"
        icon
        @click.stop="$emit('remove')"
        title="Gemerkte Stelle entfernen"
      >
        <v-icon>mdi-delete</v-icon>
      </v-btn>
    </div>
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

const props = defineProps<{ job: JobCard }>()
const router = useRouter()

const goToDetail = () => {
  router.push(`/stellen/${props.job.stellenId}/JobpostingTemplateView`)
}

const formatDate = (dateStr: string) => {
  return new Date(dateStr).toLocaleDateString('de-DE')
}
</script>

<style scoped>
.custom-card {
  background-color: white;        /* Hintergrund weiß */
  border: 1px solid black;        /* Schwarze Outline */
  border-radius: 12px;            /* Runde Ecken */
  width: 100%;                     /* Card volle Breite des Containers */
  max-width: 300px;                /* Optional: max-Breite für Layout */
  max-height: 250px;               /* Alle Cards gleich hoch */
  display: flex;
  flex-direction: column;          /* Damit Button unten rechts bleibt */
  justify-content: space-between;  /* Verteilt Inhalt und Button gleichmäßig */
}


.title-wrapper {
  white-space: normal;             /* Titel umbrechen */
  word-break: break-word;          /* Lange Titel umbrechen */
}

.v-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}
</style>
