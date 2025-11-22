<template>
  <v-container v-if="loggedIn">
    <h1 class="mb-6">Stellenausschreibung</h1>

    <v-container class="box">
      <v-row>
        <!-- JobCard -->
        <v-col cols="12" v-if="stelle">
          <JobCard :stelle="stelle" @merken="merkeStelle" />
        </v-col>

        <v-col v-else cols="12">
          <v-alert type="info">Stelle wird geladen...</v-alert>
        </v-col>

        <!-- Bewerbungsbutton nur für offene Stellen -->
        <v-col cols="12" class="mt-3" v-if="stelle?.status === 'OFFEN'">
          <h2> Deine Bewerbung </h2>
          <p> Haben wir dein Interesse geweckt? Dann freuen wir uns über deine Bewerbung!</p>

          <BaseButtonApplication @click="openDialog(stelle)" />
        </v-col>

        <!-- Zurück-Button -->
        <v-col cols="12" class="mt-3">
          <v-btn color="primary" @click="goBack">
            Zurück zur Übersicht
          </v-btn>
        </v-col>
      </v-row>
    </v-container>

    <!-- Bewerbungsdialog -->
    <BaseDialogApplication
      v-model="dialogOpen"
      :job="selectedStelle"
      :uploaded-files="nwkDocuments"
      :nwk-id="nwk?.id"
      @submit="handleSubmit"
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

const API_STELLE = 'http://localhost:8080/api/stellen'
const API_MERKLISTE = 'http://localhost:8080/api/meineListe'
const API_SBL = 'http://localhost:8080/api/servicebereichsleiter'
const API_NWK_DOCS = 'http://localhost:8080/api/meinKonto/documents'

interface Nachwuchskraft { id: number }
const nwk = ref<Nachwuchskraft | null>(null)
const loggedIn = ref(false)
const stelle = ref<any>(null)
const dialogOpen = ref(false)
const selectedStelle = ref<any>(null)
const nwkDocuments = ref<any[]>([])

// ------------------ Stelle laden ------------------
const ladeStelle = async (id: string | string[]) => {
  if (!nwk.value) return

  try {
    const res = await axios.get(`${API_STELLE}/${id}`)
    let s = res.data

    // Servicebereichsleiter laden
    if (!s.servicebereichsleiter && s.servicebereichsleiter_id) {
      const sblRes = await axios.get(`${API_SBL}/${s.servicebereichsleiter_id}`)
      s.servicebereichsleiter = {
        id: sblRes.data.id,
        name: sblRes.data.kontaktperson || sblRes.data.bereich
      }
    }

    // Merkliste prüfen
    const merklisteRes = await axios.get(`${API_MERKLISTE}/nachwuchskraft/${nwk.value.id}`)
    const gemerktIds = merklisteRes.data.map((e: any) => e.stelle.id)
    s.gemerkt = gemerktIds.includes(s.id)

    stelle.value = s
  } catch (err) {
    console.error(err)
  }
}

watch(() => route.params.id, id => { if (id) ladeStelle(id) }, { immediate: true })

function openDialog(s: any) {
  selectedStelle.value = s
  dialogOpen.value = true
}

function goBack() {
  router.push('/stellen/JobpostingOverviewView')
}

async function merkeStelle(id: number) {
  if (!nwk.value || stelle.value?.status !== 'OFFEN') return
  try {
    await axios.post(`${API_MERKLISTE}/${id}/merken/nachwuchskraft/${nwk.value.id}`)
    stelle.value.gemerkt = true
  } catch (err) {
    console.error(err)
  }
}

// ------------------ Nachwuchskraft-Dokumente laden ------------------
const ladeDocuments = async () => {
  if (!nwk.value) return
  try {
    const res = await axios.get(`${API_NWK_DOCS}/${nwk.value.id}`)
    nwkDocuments.value = res.data
  } catch (err) {
    console.error(err)
  }
}

// ------------------ onMounted ------------------
onMounted(() => {
  loggedIn.value = sessionStorage.getItem('loggedIn') === 'true'

  if (!loggedIn.value) {
    router.replace('/login')
    return
  }

  const userJson = sessionStorage.getItem("user")
  if (userJson) {
    const userData = JSON.parse(userJson)
    nwk.value = { id: userData.id }
    ladeDocuments()
    if (route.params.id) ladeStelle(route.params.id)
  } else {
    console.error("Kein eingeloggter Nutzer gefunden")
  }
})
</script>

<style scoped>
.box { margin-top: 20px; }
</style>
