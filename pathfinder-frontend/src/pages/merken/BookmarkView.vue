<template>
  <v-container>
    <h1>Meine Liste</h1>

    <v-row>
      <v-col v-for="job in bookmarkedJobs" :key="job.id" cols="12">
        <JobCard :job="job" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import JobCard from '@/components/stellen/JobCard.vue'

// Dummy: gemerkte Jobs
const bookmarkedJobs = ref([
  { id: 1, title: 'DevOps Junior', date: '01.11.2025', type: 'Vollzeit', contractType: 'unbefristet', payGrade: 'E10 TvöD', start: 'bald', area: 'it@M', expectations: 'Du unterstützt unser DevOps-Team...', requirements: 'Kenntnisse in CI/CD, Docker, Linux und Git' }
  // später dynamisch aus globalem Store/API
])

onMounted(() => {
  console.log('Gemerkte Jobs:', bookmarkedJobs.value)
})
</script>
