<template>
  <v-container>
    <v-row class="text-center mb-6">
      <v-col cols="12">
        <h1 class="text-h3 font-weight-bold">
          Welcome to Pathfinder!
        </h1>
      </v-col>
    </v-row>

    <!-- Dashboard Card -->
    <v-row>
      <v-col cols="12">
        <DashboardCard />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import DashboardCard from '@/components/textbrackets/DashboardCard.vue'
</script>
