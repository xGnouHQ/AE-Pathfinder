<template>
  <v-container>
    <h1 class="mb-6">Mein Konto</h1>

    <!-- Persönliche Daten -->
    <BaseCardNwkPersonal
      v-if="nwk"
      :nwk="nwk"
      editable
      class="mt-4"
      @edit="dialogProfileOpen = true"
    />
    <v-card v-else class="mt-4 pa-4" outlined>
      <v-row justify="center">
        <v-progress-circular indeterminate color="primary" />
      </v-row>
      <v-row justify="center" class="mt-2">
        <span>Lade persönliche Daten...</span>
      </v-row>
    </v-card>

    <!-- Abteilungen & Interessen -->
    <BaseCardNwkExperienceAndInterests
      v-if="nwkExperience"
      :nwkExperience="nwkExperience"
      editable
      class="mt-4"
      @edit="dialogExperienceOpen = true"
    />

    <!-- Dokumente -->
    <BaseCardNwkDocuments
      v-if="nwk"
      :savedFiles="savedFiles"
      :nwkId="nwk.id"
      editable
      @upload="dialogOpen = true"
      @delete="handleDeleteFile"
    />

    <!-- Dialoge -->
    <BaseDialogNwkUploadDocuments
      v-model="dialogOpen"
      :savedFiles="savedFiles"
      :nwkId="nwk?.id"
      @save="handleUploadSave"
    />

    <BaseDialogNwkUpdateExperienceAndInterests
      v-model="dialogExperienceOpen"
      :nwkExperience="nwkExperience"
      :nwkId="nwk?.id"
      @save="handleExperienceSave"
    />
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue"
import { useRouter } from 'vue-router'
import BaseDialogNwkUploadDocuments from "@/components/nachwuchskraefte/BaseDialogNwkUploadDocuments.vue"
import BaseCardNwkPersonal from "@/components/nachwuchskraefte/BaseCardNwkPersonal.vue"
import BaseCardNwkDocuments from "@/components/nachwuchskraefte/BaseCardNwkDocuments.vue"
import BaseCardNwkExperienceAndInterests from "@/components/nachwuchskraefte/BaseCardNwkExperienceAndInterests.vue"
import BaseDialogNwkUpdateExperienceAndInterests from "@/components/nachwuchskraefte/BaseDialogNwkUpdateExperienceAndInterests.vue"

interface Nachwuchskraft {
  id: number
  personalnummer: string
  vorname: string
  nachname: string
  email: string
  studienrichtung: string
  jahrgang: string
  interessen: { id: number; name: string }[]
  wunschabteilungen: { id: number; name: string }[]
}

interface NwkExperience {
  wunschabteilungen: { id: number; name: string }[]
  interessen: { id: number; name: string }[]
  knowsProgramming: boolean
  programmingLanguages?: string[]
}

interface StoredFile {
  id: number
  name: string
  url: string
  fileObject?: File
}

const router = useRouter()

// ----------------- State -----------------
const nwk = ref<Nachwuchskraft | null>(null)
const nwkExperience = ref<NwkExperience | null>(null)
const savedFiles = ref<StoredFile[]>([])

const dialogOpen = ref(false)
const dialogProfileOpen = ref(false)
const dialogExperienceOpen = ref(false)

// ----------------- Daten laden -----------------
async function loadPersonal() {
  if (!nwk.value) return

  try {
    const res = await fetch(`/api/meinKonto/personal/${nwk.value.id}`)
    if (!res.ok) throw new Error(`Fehler: ${res.status}`)
    const data = await res.json()

    nwk.value = {
      id: data.id,
      personalnummer: data.personalnummer,
      vorname: data.vorname,
      nachname: data.nachname,
      email: data.email,
      studienrichtung: data.studienrichtung,
      jahrgang: data.jahrgang,
      interessen: data.interessen ?? [],
      wunschabteilungen: data.wunschabteilungen ?? []
    }

    await loadExperience() // Experience laden
  } catch (err) {
    console.error('Fehler beim Laden der persönlichen Daten:', err)
  }
}

async function loadExperience() {
  if (!nwk.value) return

  try {
    const res = await fetch(`/api/meinKonto/experience/${nwk.value.id}`)
    if (!res.ok) throw new Error(`Fehler: ${res.status}`)
    const data = await res.json()

    // ❗ Nur initialisieren, wenn noch nicht vorhanden
    if (!nwkExperience.value) {
      nwkExperience.value = {
        wunschabteilungen: data.wunschabteilungen ?? [],
        interessen: data.interessen ?? [],
        knowsProgramming: data.knowsProgramming ?? false,
        programmingLanguages: data.programmingLanguages ?? []
      }
    }
    // Wenn nwkExperience schon existiert → NICHT überschreiben
  } catch (err) {
    console.error('Fehler beim Laden der Experience:', err)
  }
}


// ----------------- Speichern nach Dialog -----------------
function handleExperienceSave(updated: NwkExperience) {
  // PUT an Backend
  fetch(`/api/meinKonto/experience/${nwk.value!.id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(updated)
  }).catch(err => console.error(err))

  // Lokal übernehmen → Card sofort aktualisiert
  nwkExperience.value = { ...updated }
  dialogExperienceOpen.value = false
}


// ----------------- Dokumente -----------------
async function loadDocuments() {
  if (!nwk.value) return
  try {
    const res = await fetch(`/api/meinKonto/documents/${nwk.value.id}`)
    if (res.status === 204) {
      savedFiles.value = []
      return
    }
    const data = await res.json()
    savedFiles.value = data.map((d: any) => ({
      id: d.id,
      name: d.dateipfad.split('/').pop() ?? 'Unbekannt',
      url: d.dateipfad
    }))
  } catch (err) {
    console.error('Fehler beim Laden der Dokumente:', err)
  }
}

async function handleUploadSave(newFiles: StoredFile[]) { }
async function handleDeleteFile(id: number) { }

// ----------------- onMounted -----------------
onMounted(() => {
  const userJson = sessionStorage.getItem('user')
  if (!userJson) {
    router.replace('/login')
    return
  }

  const userData = JSON.parse(userJson)
  nwk.value = { ...userData }

  if (!nwkExperience.value) {
    nwkExperience.value = {
      wunschabteilungen: nwk.value.wunschabteilungen ?? [],
      interessen: nwk.value.interessen ?? [],
      knowsProgramming: false,
      programmingLanguages: []
    }
  }

  loadPersonal()
  loadDocuments()
})
</script>

<style scoped>
.v-card-title { font-weight: 600; }
</style>
