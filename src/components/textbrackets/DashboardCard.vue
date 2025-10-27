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
        <ApplicationStatusText :notifications="notifications" />
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

// Example profile
const nwkExperience = ref({
  experiences: ['Web Development Internship', 'Backend at City Office'],
  knowsProgramming: true,
  programmingLanguages: ['JavaScript', 'Python', 'Docker'],
  interests: ['Cloud', 'Automation', 'IT Security']
})

// Example jobs
const jobs = ref([
  { id: 1, title: 'DevOps Junior', department: 'IT Department', description: 'Manage cloud systems' },
  { id: 2, title: 'Frontend Developer', department: 'Web Development', description: 'Develop UI/UX' },
  { id: 3, title: 'Data Analyst', department: 'Controlling', description: 'Analyze data' }
])

// Application notifications
const notifications = ref([
  { id: 1, message: 'Application for DevOps Junior has been reviewed.', date: '27.10.2025' },
  { id: 2, message: 'Frontend Developer application accepted!', date: '25.10.2025' }
])

// Matching calculation
function calculateMatch(job: any, profile: any): number {
  const text = `${job.title} ${job.description}`.toLowerCase()
  const keywords = [
    ...profile.experiences,
    ...profile.interests,
    ...(profile.knowsProgramming ? profile.programmingLanguages : [])
  ]
    .filter(Boolean)
    .map((s: string) => s.toLowerCase())

  if (!keywords.length) return 0

  const matches = keywords.filter((kw) => text.includes(kw)).length
  return Math.round((matches / keywords.length) * 100)
}

// Jobs filtered with match >= 70%
const matchingJobs = computed(() =>
  jobs.value
    .map(job => ({ ...job, match: calculateMatch(job, nwkExperience.value) }))
    .filter(job => job.match >= 70)
)

function goToJob(job: any) {
  router.push(`/jobs/${job.id}`)
}
</script>
