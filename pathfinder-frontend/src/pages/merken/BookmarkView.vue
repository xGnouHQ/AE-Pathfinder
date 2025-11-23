<template>
  <v-container>
    <h1 class="mb-6">Meine gemerkten Stellen</h1>

    <v-row>
      <v-col
        v-for="job in bookmarkedJobs"
        :key="job.stellenId"
        cols="12"
        md="6"
        lg="4"
      >
        <BaseCardMarkJob
          :job="job"
          @remove="removeJob(job.stellenId)"
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

/** 👇 GENAUES DTO WIE BACKEND ES LIEFERT */
interface GemerkteStelleDTO {
  stellenId: number
  titel: string
  standort: string
  referat: string
  entgeltgruppe: string
  tags: string[]
  gemerktAm: string
}

/** Nachwuchskraft-ID */
const nwkId = ref<number | null>(null)

/** Liste der gemerkten Stellen */
const bookmarkedJobs = ref<GemerkteStelleDTO[]>([])

/** 🔍 Gemerkte Stellen vom Backend laden */
const ladeGemerkteStellen = async () => {
  if (!nwkId.value) return

  try {
    const response = await axios.get<GemerkteStelleDTO[]>(
      `http://localhost:8080/api/meineListe/nachwuchskraft/${nwkId.value}`
    )

    bookmarkedJobs.value = response.data
  } catch (error) {
    console.error('Fehler beim Laden der gemerkten Stellen:', error)
    bookmarkedJobs.value = []
  }
}

/** ❌ Stelle aus der Merkliste entfernen */
const removeJob = async (stellenId: number) => {
  if (!nwkId.value) return

  if (!confirm('Diese Stelle wirklich entfernen?')) return

  try {
    await axios.delete(
      `http://localhost:8080/api/meineListe/${stellenId}/nachwuchskraft/${nwkId.value}`
    )

    bookmarkedJobs.value = bookmarkedJobs.value.filter(j => j.stellenId !== stellenId)
  } catch (error) {
    console.error('Fehler beim Entfernen der Stelle:', error)
    alert('Fehler beim Entfernen der Stelle')
  }
}

/** 🔐 Login & User-Id laden */
onMounted(() => {
  const loggedIn = sessionStorage.getItem('loggedIn') === 'true'

  if (!loggedIn) {
    window.location.href = '/login'
    return
  }

  const userJson = sessionStorage.getItem('user')
  if (userJson) {
    const userData = JSON.parse(userJson)
    nwkId.value = userData.id
    ladeGemerkteStellen()
  } else {
    console.error('Kein eingeloggter Nutzer gefunden')
  }
})
</script>

<style scoped>
h1 {
  margin-bottom: 20px;
}
</style>
