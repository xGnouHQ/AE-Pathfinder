<template>
  <v-container>
    <h1>Bewerbung: {{ job?.title }}</h1>

    <BaseCardApplicationProcess
      v-if="job"
      :job="job"
      @open-message="dialogOpen = true"
    />

    <!-- Dialog nur anzeigen, wenn akzeptiert -->
    <BaseDialogMessage v-model="dialogOpen" />

    <v-btn color="primary" @click="goBack" class="mt-4">
      Zurück zur Übersicht
    </v-btn>
  </v-container>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import BaseCardApplicationProcess from '@/components/bewerbungen/BaseCardApplicationProcess.vue'
import BaseDialogMessage from '@/components/bewerbungen/BaseDialogMessage.vue'

// Dummy-Daten
const jobs = [
  { id: 1, title: 'DevOps Junior', date: '01.09.2025', payGrade: 'E10', department: 'it@M', status: 'akzeptiert', description: 'Du unterstützt das DevOps-Team bei der Automatisierung von Prozessen.' },
  { id: 2, title: 'Frontend Developer', date: '15.08.2025', payGrade: 'E9', department: 'Web Development', status: 'abgelehnt', description: 'Frontend-Entwicklung mit Vue.js und Vuetify.' }
]

const route = useRoute()
const router = useRouter()

const jobId = Number(route.params.id)
const job = computed(() => jobs.find(j => j.id === jobId))

const dialogOpen = ref(false)

function goBack() {
  router.push('/bewerbungen/ApplicationlistView')
}
</script>
