<template>
  <v-container>
    <h1 class="mb-6">Offene Stellen</h1>

    <v-container class="box">
      <v-row>
        <v-col v-for="job in jobs" :key="job.id" cols="12">
          <!-- Link zu dynamischer Job-Seite via id -->
          <router-link :to="`/stellen/${job.id}/JobpostingTemplateView`" class="no-underline">
            <v-card>
              <v-card-title class="d-flex justify-space-between align-center">
                <span>{{ job.title }}</span>
                <!-- Matching-Komponente -->
                <JobMatchScore :job="job" :profile="nwkExperience" />
              </v-card-title>

              <v-card-text>
                <v-row>
                  <v-col>Datum: {{ job.date }}</v-col>
                  <v-col>Entgeltgruppe: {{ job.payGrade }}</v-col>
                  <v-col>Referat: {{ job.department }}</v-col>
                </v-row>
                <div class="job-description">{{ job.description }}</div>
              </v-card-text>
            </v-card>
          </router-link>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import JobMatchScore from '@/components/stellen/JobMatchScore.vue'

const nwkExperience = ref({
  experiences: ['Praktikum Webentwicklung', 'Backend bei Stadtverwaltung'],
  knowsProgramming: true,
  programmingLanguages: ['JavaScript', 'Python'],
  interests: ['Webentwicklung', 'Cloud', 'Datenbanken', 'IT-Sicherheit']
})

// Beispiel-Daten für offene Stellen
const jobs = ref([
  {
    id: 1,
    title: 'DevOps Junior',
    date: '01.11.2025',
    payGrade: 'E10 TvöD',
    department: 'it@M',
    description: 'Du unterstützt unser DevOps-Team bei der Automatisierung und dem Betrieb von Cloud-Systemen.',
  },
  {
    id: 2,
    title: 'Frontend Developer',
    date: '15.12.2025',
    payGrade: 'E9 TvöD',
    department: 'Web Development',
    description: 'Frontend-Entwicklung mit Vue.js, Vuetify und modernen Webtechnologien.',
  },
])
</script>

<style scoped>
.box {
  margin: 2% 1% 1%;
  border: 2px solid #0000001a;
}
.no-underline {
  text-decoration: none;
  color: inherit;
}
.job-description {
  margin-top: 10px;
}
</style>
