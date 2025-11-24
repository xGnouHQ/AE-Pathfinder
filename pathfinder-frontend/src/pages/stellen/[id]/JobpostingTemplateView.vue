<template>
  <v-container v-if="loggedIn">
    <h1 class="mb-6">Stellenausschreibung</h1>

    <v-container class="box">
      <v-row>
        <!-- JobCard -->
        <v-col cols="12" v-if="stelle">
          <JobCard
            :stelle="stelle"
            :bereits-beworben-ids="bewerbungsIds"
            @merken="merkeStelle"
          />
        </v-col>

        <v-col v-else cols="12">
          <v-alert type="info">Stelle wird geladen...</v-alert>
        </v-col>

        <!-- Bewerbungsbutton -->
        <v-col cols="12" class="mt-3" v-if="stelle?.status === 'OFFEN'">
          <h2>Deine Bewerbung</h2>
          <p>Haben wir dein Interesse geweckt? Dann freuen wir uns über deine Bewerbung!</p>

          <div class="d-flex align-center">
            <!-- Bereits beworben -->
            <v-btn
              v-if="hatBereitsBeworben(stelle.id)"
              color="grey"
              disabled
              outlined
              class="mr-4"
            >
              Bereits beworben
            </v-btn>

            <!-- Datum der Bewerbung -->
            <span v-if="hatBereitsBeworben(stelle.id)">
              Eingereicht am: {{ formatDate(getEingereichtAm(stelle.id)) }}
            </span>

            <!-- Bewerbungsbutton, wenn noch nicht beworben -->
            <BaseButtonApplication
              v-else
              @click="openDialog(stelle)"
            >
              Jetzt bewerben
            </BaseButtonApplication>
          </div>
        </v-col>

        <!-- Zurück -->
        <v-col cols="12" class="mt-3">
          <v-btn color="primary" @click="goBack">Zurück zur Übersicht</v-btn>
        </v-col>
      </v-row>
    </v-container>

    <!-- Bewerbungsdialog -->
    <BaseDialogApplication
      v-model="dialogOpen"
      :job="selectedStelle"
      :uploaded-files="nwkDocuments"
      :nwk-id="nwk?.id"
      @submitted="handleSubmit"
    />
  </v-container>

  <div v-else>
    <p>Bitte einloggen...</p>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

import JobCard from '@/components/stellen/JobCard.vue'
import BaseButtonApplication from '@/components/common/BaseButtonApplication.vue'
import BaseDialogApplication from '@/components/bewerbungen/BaseDialogApplication.vue'

const route = useRoute()
const router = useRouter()

const API_STELLE = '/api/stellenportal'
const API_MERKLISTE = '/api/meineListe'
const API_NWK_DOCS = '/api/meinKonto/documents'
const API_BEW = '/api/bewerbungen'

const nwk = ref<{ id: number } | null>(null)
const loggedIn = ref(false)
const stelle = ref<any>(null)
const dialogOpen = ref(false)
const selectedStelle = ref<any>(null)
const nwkDocuments = ref<any[]>([])
const bewerbungs = ref<any[]>([]) // Bewerbungen der Nachwuchskraft
const bewerbungsIds = ref<number[]>([]) // IDs der Stellen, auf die sich NWK bereits beworben hat

// -------------------------------------------------------------
// Stelle laden
// -------------------------------------------------------------
const ladeStelle = async (id: string | string[]) => {
  if (!id) return
  try {
    const res = await axios.get(`${API_STELLE}/${id}`)
    stelle.value = res.data
  } catch (err) {
    console.error("Fehler beim Laden der Stelle:", err)
  }
}

// Bewerbungen der NWK laden, um zu prüfen, ob bereits beworben
const ladeBewerbungen = async () => {
  if (!nwk.value) return
  try {
    const res = await axios.get(`${API_BEW}/nachwuchskraft/${nwk.value.id}`)
    bewerbungs.value = res.data
    bewerbungsIds.value = res.data.map((b: any) => b.stelleId)
  } catch (err) {
    console.error("Fehler beim Laden der Bewerbungen:", err)
  }
}

// -------------------------------------------------------------
// Watcher Route-ID
// -------------------------------------------------------------
watch(
  () => route.params.id,
  id => { if (id) ladeStelle(id) },
  { immediate: true }
)

// -------------------------------------------------------------
// Bewerbungsdialog öffnen
// -------------------------------------------------------------
function openDialog(s: any) {
  selectedStelle.value = s
  dialogOpen.value = true
}

// -------------------------------------------------------------
// Zur Merkliste hinzufügen
// -------------------------------------------------------------
async function merkeStelle(id: number) {
  if (!nwk.value) return
  try {
    await axios.post(`/api/meineListe/${id}/merken/nachwuchskraft/${nwk.value.id}`)
    stelle.value.gemerkt = true
  } catch (err) {
    console.error(err)
  }
}

// -------------------------------------------------------------
// Dokumente der Nachwuchskraft laden
// -------------------------------------------------------------
const ladeDocuments = async () => {
  if (!nwk.value) return
  try {
    const res = await axios.get(`${API_NWK_DOCS}/${nwk.value.id}`)
    nwkDocuments.value = res.data.documents || []
  } catch (err) {
    console.error("Fehler beim Laden der Dokumente:", err)
  }
}

// -------------------------------------------------------------
// Prüfen, ob NWK bereits auf die Stelle beworben hat
// -------------------------------------------------------------
function hatBereitsBeworben(stelleId: number): boolean {
  return bewerbungsIds.value.includes(stelleId)
}

// Datum der Bewerbung holen
function getEingereichtAm(stelleId: number): string | null {
  const bew = bewerbungs.value.find((b: any) => b.stelleId === stelleId)
  return bew ? bew.eingereichtAm : null
}

// Hilfsfunktion für Datum
function formatDate(dateStr?: string) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleDateString('de-DE')
}

// -------------------------------------------------------------
// Zurück
// -------------------------------------------------------------
function goBack() {
  router.push('/stellen/JobpostingOverviewView')
}

// -------------------------------------------------------------
// Mounted
// -------------------------------------------------------------
onMounted(() => {
  loggedIn.value = sessionStorage.getItem('loggedIn') === 'true'

  if (!loggedIn.value) {
    router.replace('/login')
    return
  }

  const userJson = sessionStorage.getItem('user')
  if (userJson) {
    const userData = JSON.parse(userJson)
    nwk.value = { id: userData.id }

    ladeDocuments()
    ladeBewerbungen()
  }
})
</script>

<style scoped>
.box {
  margin-top: 20px;
}
</style>
