<template>
  <v-container>
    <h1 class="mb-6">Eingegangene Bewerbungen</h1>

    <v-container class="box">
      <v-row>
        <v-col v-for="job in jobs" :key="job.id" cols="12">
          <v-card class="mb-4">
            <v-card-title class="d-flex justify-space-between align-center">
              <!-- Job-Titel: Weiterleitung zu ApplicationView via Pfad -->
              <router-link
                :to="`/bewerbungen/${job.id}/ApplicationView`"
                class="no-underline"
              >
                <span class="text-h6">{{ job.title }}</span>
              </router-link>

              <!-- Bewerbungsstatus-Komponente -->
              <ApplicationStatusDisplay :status="job.status" />
            </v-card-title>

            <v-divider />

            <v-card-text>
              <v-row>
                <v-col>Datum: {{ job.date }}</v-col>
                <v-col>Entgeltgruppe: {{ job.payGrade }}</v-col>
                <v-col>Referat: {{ job.department }}</v-col>
              </v-row>

              <div class="job-description">{{ job.description }}</div>

              <!-- Bewerbung zurückziehen -->
              <v-btn
                color="error"
                variant="text"
                class="mt-2"
                @click="withdrawApplication(job.id)"
                size="small"
              >
                Bewerbung zurückziehen
              </v-btn>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import ApplicationStatusDisplay from '@/components/bewerbungen/ApplicationStatusDisplay.vue'

interface Job {
  id: number
  title: string
  date: string
  payGrade: string
  department: string
  description: string
  status: 'eingegangen' | 'in_bearbeitung' | 'akzeptiert' | 'abgelehnt'
}

// Dummy-Daten
const jobs = ref<Job[]>([
  {
    id: 1,
    title: 'DevOps Junior',
    date: '01.09.2025',
    payGrade: 'E10',
    department: 'it@M',
    description: 'Du unterstützt das DevOps-Team bei der Automatisierung von Prozessen.',
    status: 'in_bearbeitung'
  },
  {
    id: 2,
    title: 'Frontend Developer',
    date: '15.08.2025',
    payGrade: 'E9',
    department: 'Web Development',
    description: 'Frontend-Entwicklung mit Vue.js und Vuetify.',
    status: 'abgelehnt'
  }
])

function withdrawApplication(id: number) {
  const confirmed = confirm('Möchtest du die Bewerbung wirklich zurückziehen?')
  if (confirmed) {
    jobs.value = jobs.value.filter(job => job.id !== id)
  }
}
</script>

<style scoped>
.box {
  margin: 2% 1% 1%;
  border: 2px solid #0000001a;
  padding: 16px;
}
.no-underline {
  text-decoration: none;
  color: inherit;
}
.job-description {
  margin-top: 10px;
}
</style>
