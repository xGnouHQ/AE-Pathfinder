<template>
  <v-container>
    <h1 class="mb-6">Stellenausschreibung</h1>
    <v-container class="box">
      <v-row>
        <v-col v-for="job in jobs" :key="job.title" cols="12">
          <JobCard :job="job" />
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import JobCard from '@/components/stellen/JobCard.vue'

const jobs = ref([
  {
    title: 'DevOps Junior',
    date: 'XX.XX.XXXX',
    type: 'Vollzeit',
    contractType: 'unbefristet',
    payGrade: 'E10 TvöD',
    start: 'bald',
    area: 'it@M',
    expectations: 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit...',
    requirements: 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit...',
  },
])
</script>

<style scoped>
.box {
  margin-top: 20px;
}
</style>
