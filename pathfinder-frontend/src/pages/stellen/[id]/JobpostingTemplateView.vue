<template>
  <v-container>
    <h1 class="mb-6">Stellenausschreibung</h1>

    <v-container class="box">
      <v-row>
        <v-col cols="12">
          <!-- JobCard zeigt die Details der ausgewählten Stelle -->
          <JobCard v-if="job" :job="job" />
        </v-col>

        <v-col cols="12" class="mt-3">
          <!-- Bewerbungsbutton -->
          <BaseButtonApplication v-if="job" @click="openDialog(job)" />
        </v-col>
      </v-row>
    </v-container>

    <!-- Bewerbungsdialog -->
    <BaseDialogApplication
      v-model="dialogOpen"
      :job="selectedJob"
      :uploaded-files="uploadedFiles"
      @submit="handleSubmit"
    />
  </v-container>
</template>

<script setup lang="ts">
import { ref, computed } from "vue"
import { useRoute } from "vue-router"

import JobCard from "@/components/stellen/JobCard.vue"
import BaseButtonApplication from "@/components/common/BaseButtonApplication.vue"
import BaseDialogApplication from "@/components/bewerbungen/BaseDialogApplication.vue"

interface Job {
  id: number
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

// Dummy-Daten für alle Jobs
const jobs = ref<Job[]>([
  {
    id: 1,
    title: "DevOps Junior",
    date: "01.11.2025",
    type: "Vollzeit",
    contractType: "unbefristet",
    payGrade: "E10 TvöD",
    start: "bald",
    area: "it@M",
    expectations:
      "Du unterstützt unser DevOps-Team bei der Automatisierung und dem Betrieb von Cloud-Systemen.",
    requirements: "Kenntnisse in CI/CD, Docker, Linux und Git sind wünschenswert.",
  },
  {
    id: 2,
    title: "Frontend Developer",
    date: "15.12.2025",
    type: "Vollzeit",
    contractType: "unbefristet",
    payGrade: "E9 TvöD",
    start: "bald",
    area: "Web Development",
    expectations: "Frontend-Entwicklung mit Vue.js und Vuetify.",
    requirements: "Kenntnisse in HTML, CSS, JavaScript und Vue erforderlich.",
  },
])

// Router-Parameter abrufen
const route = useRoute()
const jobId = Number(route.params.id)

// Job basierend auf id filtern
const job = computed(() => jobs.value.find((j) => j.id === jobId))

// Dialog-Handling
const dialogOpen = ref(false)
const selectedJob = ref<Job | null>(null)
const uploadedFiles = ref([
  { id: "1", name: "Lebenslauf_MaxMustermann.pdf" },
  { id: "2", name: "Motivationsschreiben.pdf" },
  { id: "3", name: "Zeugnis_2024.pdf" },
])

function openDialog(job: Job) {
  selectedJob.value = job
  dialogOpen.value = true
}

function handleSubmit(data: any) {
  console.log("Bewerbung übermittelt:", data)
  alert(`✅ Bewerbung für "${data.job.title}" wurde erfolgreich gesendet!`)
}
</script>

<style scoped>
.box {
  margin-top: 20px;
}
</style>
