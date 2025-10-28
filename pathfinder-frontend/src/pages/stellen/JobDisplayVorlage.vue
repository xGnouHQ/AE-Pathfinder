<template>
  <v-container>
    <h1 class="mb-6">Stellenausschreibung</h1>

    <v-container class="box">
      <v-row>
        <v-col v-for="job in jobs" :key="job.title" cols="12">
          <v-row class="align-center">
            <v-col>
              <JobCard :job="job" />
            </v-col>
            <v-col cols="auto">
              <v-btn color="primary" @click="openDialog(job)">
                Bewerben
              </v-btn>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>

    <!-- Bewerbungsdialog -->
    <ApplicationDialog
      v-model="dialogOpen"
      :job="selectedJob"
      :uploadedFiles="uploadedFiles"
      @submit="handleSubmit"
    />
  </v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import JobCard from '@/components/stellen/JobCard.vue'
import ApplicationDialog from '@/components/stellen/ApplicationDialog.vue'

interface Job {
  title: string
  date: string
  type: string
  contractType: string
  payGrade: string
  start: string
  area: string
  expectations: string
  requirements: string
}

const jobs = ref<Job[]>([
  {
    title: 'DevOps Junior',
    date: '01.11.2025',
    type: 'Vollzeit',
    contractType: 'unbefristet',
    payGrade: 'E10 TvöD',
    start: 'bald',
    area: 'it@M',
    expectations: 'Du unterstützt unser DevOps-Team bei der Automatisierung und dem Betrieb von Cloud-Systemen.',
    requirements: 'Kenntnisse in CI/CD, Docker, Linux und Git sind wünschenswert.',
  },
])

const uploadedFiles = ref([
  { id: '1', name: 'Lebenslauf_MaxMustermann.pdf' },
  { id: '2', name: 'Motivationsschreiben.pdf' },
  { id: '3', name: 'Zeugnis_2024.pdf' },
])

const dialogOpen = ref(false)
const selectedJob = ref<Job | null>(null)

function openDialog(job: Job) {
  selectedJob.value = job
  dialogOpen.value = true
}

function handleSubmit(data: any) {
  console.log('Bewerbung übermittelt:', data)
  alert(`Bewerbung für "${data.job.title}" wurde erfolgreich gesendet!`)
}
</script>

<style scoped>
.box {
  margin-top: 20px;
}
</style>
