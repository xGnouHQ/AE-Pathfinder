<template>
  <v-container>
    <h1 class="mb-6">Meine gemerkten Stellen</h1>

    <v-row>
      <v-col
        v-for="job in bookmarkedJobs"
        :key="job.id"
        cols="12"
        md="6"
        lg="4"
      >
        <BaseCardMarkJob
          :job="job"
          :profile="userProfile"
          @remove="removeJob(job.id)"
        />
      </v-col>

      <v-col v-if="bookmarkedJobs.length === 0" cols="12">
        <p>Keine gemerkten Stellen vorhanden.</p>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import BaseCardMarkJob from '@/components/stellen/BaseCardMarkJob.vue'

interface Stelle {
  id: number
  titel: string
  standort: string
  status?: string      // Status hinzugefügt
  art?: string
  vertragsart?: string
  entgeltgruppe?: string
  bereich?: string
  beschreibung?: string
  erwartungen?: string
  anforderungen?: string
}

interface GemerkteStelle {
  id: number // entspricht stelle.id
  stelle: Stelle
  erstelltAm: string
}

// Beispielprofil des Users
const userProfile = ref({
  experiences: ['DevOps', 'CI/CD', 'Linux'],
  knowsProgramming: true,
  programmingLanguages: ['Python', 'Bash', 'Docker'],
  interests: ['Automatisierung', 'Cloud', 'Sicherheit']
})

// Nachwuchskraft-ID (hier festgesetzt, kann dynamisch aus Login kommen)
const nwkId = 1

// Gemerkte Stellen
const bookmarkedJobs = ref<Stelle[]>([])

// Gemerkte Stellen vom Backend laden (nur offene Stellen)
const ladeGemerkteStellen = async () => {
  try {
    const response = await axios.get<GemerkteStelle[]>(`http://localhost:8080/api/meineListe/nachwuchskraft/${nwkId}`)
    // Nur die Stelle selbst extrahieren UND nur offene Stellen
    bookmarkedJobs.value = response.data
      .map(e => ({ ...e.stelle }))
      .filter(stelle => stelle.status !== 'Geschlossen')
  } catch (error) {
    console.error('Fehler beim Laden der gemerkten Stellen:', error)
    bookmarkedJobs.value = []
  }
}

// Eintrag aus der Merkliste löschen
const removeJob = async (stellenId: number) => {
  if (!confirm('Möchtest du diese Stelle wirklich aus der Merkliste entfernen?')) return
  try {
    await axios.delete(`http://localhost:8080/api/meineListe/${stellenId}/nachwuchskraft/${nwkId}`)
    bookmarkedJobs.value = bookmarkedJobs.value.filter(job => job.id !== stellenId)
  } catch (error) {
    console.error('Fehler beim Entfernen der Stelle:', error)
    alert('Fehler beim Entfernen der Stelle')
  }
}

onMounted(ladeGemerkteStellen)
</script>

<style scoped>
h1 {
  margin-bottom: 20px;
}
</style>
