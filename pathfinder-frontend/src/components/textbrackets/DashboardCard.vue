<template>
  <v-container>
    <v-card outlined class="pa-4">
      <v-card-title>
        <v-icon color="primary" class="mr-2">mdi-view-dashboard</v-icon>
        Dashboard
      </v-card-title>

      <v-card-text>
        <!-- Matching Jobs Section -->
        <MatchingJobText
          :jobs="matchingJobs"
          @view-job="goToJob"
        />

        <v-divider class="my-6" />

        <!-- Application Status Section -->
        <ApplicationStatusText :nwkId="nwkId" />
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import MatchingJobText from '@/components/textbrackets/MatchingJobText.vue'
import ApplicationStatusText from '@/components/textbrackets/ApplicationStatusText.vue'

const router = useRouter()
const nwkId = 1 // Nachwuchskraft-ID (dynamisch später)

interface Job {
  id: number
  titel: string
  beschreibung: string
  standort: string
  matchingScore?: number
}

const jobs = ref<Job[]>([])

// --- Jobs laden und Matching-Score vom Backend abrufen ---
const ladeJobs = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/stellen')
    jobs.value = response.data

    // Matching-Score für jede Stelle abrufen
    await Promise.all(
      jobs.value.map(async (job) => {
        try {
          const scoreResp = await axios.get(
            `http://localhost:8080/api/matching/${nwkId}/${job.id}`
          )
          job.matchingScore = Number(scoreResp.data ?? 0)
        } catch {
          job.matchingScore = 0
        }
      })
    )
  } catch (error) {
    console.error('Fehler beim Laden der Jobs:', error)
  }
}

// --- Filter: nur Jobs mit MatchingScore > 50% ---
const matchingJobs = computed(() =>
  jobs.value.filter(job => (job.matchingScore ?? 0) > 50)
)

// --- Navigation zu Jobdetails ---
function goToJob(job: Job) {
  router.push(`/stellen/${job.id}/JobpostingTemplateView`)
}

onMounted(ladeJobs)
</script>
