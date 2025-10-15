<template>
  <v-container>
    <h1 class="mb-6">Gemerkte Stellen</h1>

    <v-container class="box">
      <v-row>
        <v-col v-if="filteredJobs.length === 0" cols="12" class="text-center text--disabled">
          Keine gemerkten Stellen.
        </v-col>

        <v-col v-for="job in filteredJobs" :key="job.id" cols="12">
          <JobCard :job="job" />
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script setup lang="ts">
import { inject, computed } from 'vue';
import JobCard from '@/components/stellen/JobCard.vue';

interface Job {
  id: number;
  title: string;
  date: string;
  type: string;
  contractType: string;
  payGrade: string;
  start: string;
  area: string;
  expectations: string;
  requirements: string;
}

// Bookmarked Jobs aus provide holen
const bookmarkedJobs = inject<Job[]>('bookmarkedJobs', [])

// Filter nur Jobs mit id === 1
const filteredJobs = computed(() => {
  return bookmarkedJobs.filter(job => job.id === 1)
})
</script>

<style scoped>
.box {
  margin-top: 20px;
}
</style>
