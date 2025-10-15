<template>
  <v-container>
    <h1 class="mb-6">Stellenausschreibung</h1>
    <v-container class="box">
      <v-row>
        <v-col v-for="job in jobs" :key="job.title" cols="12">
          <job-card :job="job"></job-card>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import JobCard from '@/components/stellen/JobCard.vue'; // Stelle sicher, dass du die JobCard-Komponente importierst

// Beispiel-Daten für offene Stellen
const jobs = ref([
  {
    title: 'Frontend Developer',
    date: 'XX.XX.XXXX',
    type: 'Vollzeit',
    contractType: 'unbefristet',
    payGrade: 'E9 TvöD',
    start: 'bald',
    area: 'it@M',
    expectations: 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit...',
    requirements: 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit...',
  },
  // Füge hier weitere Stellenangebote hinzu
]);
</script>

<style scoped>
.box {
  margin-top: 20px;
}
</style
