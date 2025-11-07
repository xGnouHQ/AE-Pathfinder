<template>
  <v-container>
    <h1 class="mb-6">Offene Stellen</h1>

    <v-container class="box">
      <v-row>
        <v-col v-for="job in jobs" :key="job.id" cols="12">
          <router-link :to="`/stellen/${job.id}/JobpostingTemplateView`" class="no-underline">
            <BaseCardJobMini :job="job" :profile="nwkExperience" />
          </router-link>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import BaseCardJobMini from '@/components/stellen/BaseCardJobMini.vue'

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
</style>
