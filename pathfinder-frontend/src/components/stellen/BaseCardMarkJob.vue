<template>
  <v-card class="pa-4 mb-4">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>{{ job.titel }}</span>

      <v-btn
        small
        color="red"
        icon
        @click="$emit('remove')"
        title="Gemerkte Stelle entfernen"
      >
        <v-icon>mdi-delete</v-icon>
      </v-btn>
    </v-card-title>

    <v-card-subtitle>
      {{ job.standort }} | {{ job.referat }} | EG {{ job.entgeltgruppe }}
    </v-card-subtitle>

    <v-card-text>
      <v-chip
        v-for="tag in job.tags"
        :key="tag"
        class="ma-1"
        small
        outlined
      >
        {{ tag }}
      </v-chip>

      <p class="mt-3 text-caption grey--text">
        Gemerkt am: {{ new Date(job.gemerktAm).toLocaleDateString() }}
      </p>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
interface JobCard {
  stellenId: number
  titel: string
  standort: string
  referat: string
  entgeltgruppe: string
  tags: string[]
  gemerktAm: string
}

defineProps<{
  job: JobCard
}>()
</script>
