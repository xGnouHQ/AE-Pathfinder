<template>
  <v-container>
    <h1 class="mb-6">Meine gemerkten Stellen</h1>

    <v-row>
      <v-col
        v-for="job in bookmarkedJobs"
        :key="job.id"
        cols="12"
        md="6"
        lg="4"
      >
        <BaseCardMarkJob
          :job="job"
          :profile="userProfile"
          :savedJobs="bookmarkedJobs"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import BaseCardMarkJob from '@/components/stellen/BaseCardMarkJob.vue'

interface JobMini {
  id: number
  title: string
  date: string
  payGrade: string
  department: string
  description: string
}

// Beispielprofil des Users (für Matching)
const userProfile = ref({
  experiences: ['DevOps', 'CI/CD', 'Linux'],
  knowsProgramming: true,
  programmingLanguages: ['Python', 'Bash', 'Docker'],
  interests: ['Automatisierung', 'Cloud', 'Sicherheit']
})

// Beispielhafte gemerkte Jobs
const bookmarkedJobs = ref<JobMini[]>([
  {
    id: 1,
    title: 'DevOps Junior',
    date: '01.11.2025',
    payGrade: 'E10 TVöD',
    department: 'it@M',
    description: 'Unterstütze das DevOps-Team bei der Automatisierung unserer Systeme.'
  },
  {
    id: 2,
    title: 'Softwareentwickler Frontend',
    date: '15.12.2025',
    payGrade: 'E11 TVöD',
    department: 'Digitalisierung',
    description: 'Entwickle moderne Webanwendungen auf Basis von Vue.js und TypeScript.'
  }
])
</script>
