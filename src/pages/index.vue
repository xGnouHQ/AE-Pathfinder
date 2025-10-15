<template>
  <v-container>
    <v-row class="text-center">
      <v-col cols="12" class="mb-4">
        <h1 class="text-h3 font-weight-bold mb-3">
          Willkommen beim Pathfinder!
        </h1>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <v-card
          color="surface-variant"
          title="Dashboard"
          subtitle="Keine Neuigkeiten"
          :variant="variant"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts" setup>
const variant = 'outlined' // oder z.B. 'elevated' / 'tonal' je nach Design
</script>
