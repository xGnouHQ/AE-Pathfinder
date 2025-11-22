<template>
  <v-card>
    <v-card-title>Persönliche Daten</v-card-title>
    <v-divider></v-divider>

    <v-card-text v-if="nwk">
      <v-row>
        <v-col><strong>ID:</strong> {{ nwk.id }}</v-col>
        <v-col><strong>Personalnummer:</strong> {{ nwk.personalnummer }}</v-col>
      </v-row>

      <v-row>
        <v-col><strong>Nachname:</strong> {{ nwk.nachname }}</v-col>
        <v-col><strong>Vorname:</strong> {{ nwk.vorname }}</v-col>
      </v-row>

      <v-row>
        <v-col><strong>E-Mail:</strong> {{ nwk.email }}</v-col>
      </v-row>

      <v-row>
        <v-col><strong>Jahrgang:</strong> {{ nwk.jahrgang }}</v-col>
        <v-col><strong>Studienrichtung:</strong> {{ nwk.studienrichtung }}</v-col>
      </v-row>

      <v-row>
        <v-col>
          <strong>Praktika:</strong>
          <ul v-if="nwk.praktika && nwk.praktika.length">
            <li v-for="(dept, i) in nwk.praktika" :key="i">{{ dept.name }}</li>
          </ul>
          <span v-else>Keine Angaben</span>
        </v-col>
      </v-row>
    </v-card-text>

    <v-card-text v-else class="d-flex justify-center">
      <v-progress-circular indeterminate color="primary"></v-progress-circular>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

// DTO Interface passend zum Backend
interface AbteilungDTO {
  id: number
  name: string
}

interface Nachwuchskraft {
  id: number
  personalnummer: string
  vorname: string
  nachname: string
  email: string
  jahrgang: string
  studienrichtung: string
  praktika?: AbteilungDTO[]
}

const nwk = ref<Nachwuchskraft | null>(null)

onMounted(async () => {
  const loggedIn = sessionStorage.getItem('loggedIn') === 'true'
  if (!loggedIn) {
    console.error('Nutzer nicht eingeloggt')
    return
  }

  const userJson = sessionStorage.getItem('user')
  if (!userJson) {
    console.error('Kein eingeloggter Nutzer gefunden')
    return
  }

  const userData = JSON.parse(userJson)
  const nwkId = userData.id

  try {
    const res = await fetch(`/api/meinKonto/personal/${nwkId}`)
    if (!res.ok) throw new Error(`Fehler beim Laden: ${res.status}`)
    const data = await res.json()

    // DTO direkt übernehmen
    nwk.value = {
      id: data.id,
      personalnummer: data.personalnummer,
      vorname: data.vorname,
      nachname: data.nachname,
      email: data.email,
      jahrgang: data.jahrgang ?? data.eintrittsjahr,
      studienrichtung: data.studienrichtung,
      praktika: data.praktika ?? []
    }
  } catch (err) {
    console.error('Fehler beim Laden der Daten:', err)
  }
})
</script>

<style scoped>
ul { margin: 0; padding-left: 1.2rem; }
li { list-style-type: disc; }
</style>
