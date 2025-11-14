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
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import MatchingJobText from '@/components/textbrackets/MatchingJobText.vue'
import ApplicationStatusText from '@/components/textbrackets/ApplicationStatusText.vue'

// Router
const router = useRouter()

// 🧩 Nachwuchskraft-ID (später dynamisch, aktuell fest 1)
const nwkId = 1

// 🔹 Beispielprofil (nur für Matching)
const nwkExperience = ref({
  experiences: ['Web Development Internship', 'Backend at City Office'],
  knowsProgramming: true,
  programmingLanguages: ['JavaScript', 'Python', 'Docker'],
  interests: ['Cloud', 'Automation', 'IT Security']
})

// 🔹 Beispieljobs (nur für Matching-Anzeige)
const jobs = ref([
  { id: 1, title: 'DevOps Junior', department: 'IT Department', description: 'Manage cloud systems' },
  { id: 2, title: 'Frontend Developer', department: 'Web Development', description: 'Develop UI/UX' },
  { id: 3, title: 'Data Analyst', department: 'Controlling', description: 'Analyze data' }
])

// 🔹 Matching-Berechnung
function calculateMatch(job: any, profile: any): number {
  const text = `${job.title} ${job.description}`.toLowerCase()
  const keywords = [
    ...profile.experiences,
    ...profile.interests,
    ...(profile.knowsProgramming ? profile.programmingLanguages : [])
  ].filter(Boolean).map((s: string) => s.toLowerCase())

  if (!keywords.length) return 0

  const matches = keywords.filter((kw) => text.includes(kw)).length
  return Math.round((matches / keywords.length) * 100)
}

// 🔹 Nur relevante Jobs anzeigen (>=70%)
const matchingJobs = computed(() =>
  jobs.value
    .map(job => ({ ...job, match: calculateMatch(job, nwkExperience.value) }))
    .filter(job => job.match >= 70)
)

function goToJob(job: any) {
  router.push(`/jobs/${job.id}`)
}
</script>
