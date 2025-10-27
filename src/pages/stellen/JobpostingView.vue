<template>
  <v-container>
    <h1 class="mb-6">Offene Stellen</h1>

    <v-container class="box">
      <v-row>
        <v-col v-for="job in jobs" :key="job.id" cols="12">
          <router-link :to="job.link" class="no-underline">
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
import JobMatchScore from '@/components/stellen/JobMatchScore.vue'
import { ref } from 'vue'

const nwkExperience = ref({
  experiences: ['Praktikum Webentwicklung', 'Backend bei Stadtverwaltung'],
  knowsProgramming: true,
  programmingLanguages: ['JavaScript', 'Python'],
  interests: ['Webentwicklung', 'Cloud', 'Datenbanken', 'IT-Sicherheit']
})

const jobs = ref([
  {
    id: 1,
    title: 'DevOps Junior',
    date: '12.11.2025',
    payGrade: 'E10',
    department: 'it@M',
    description: 'Du arbeitest im Bereich Cloud-Automatisierung mit Linux und Docker.',
    link: '/stellen/JobDisplayVorlage'
  },
  {
    id: 2,
    title: 'Frontend Developer',
    date: '18.11.2025',
    payGrade: 'E9',
    department: 'Web Development',
    description: 'Gestaltung moderner Benutzeroberflächen mit JavaScript und Vue.',
    link: '/stellen/JobDisplayVorlage2'
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
}
.job-description {
  margin-top: 10px;
}
</style>
