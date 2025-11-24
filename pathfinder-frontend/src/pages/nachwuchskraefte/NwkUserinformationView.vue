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
}

interface StoredFile {
  id: number
  name: string
  url: string
  fileObject?: File
}

const router = useRouter()
const loggedIn = ref(false)

const nwk = ref<Nachwuchskraft | null>(null)
const nwkExperience = ref<NwkExperience | null>(null)
const savedFiles = ref<StoredFile[]>([])

const dialogOpen = ref(false)
const dialogProfileOpen = ref(false)
const dialogExperienceOpen = ref(false)

// ----------------------------------------
// Backend laden
// ----------------------------------------
async function loadPersonal() {
  if (!nwk.value) return
  try {
    const res = await fetch(`/api/meinKonto/personal/${nwk.value.id}`)
    if (!res.ok) throw new Error(`Fehler beim Laden: ${res.status}`)
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

    // Nur initialisieren, wenn noch kein Wert existiert
    if (!nwkExperience.value) {
      nwkExperience.value = {
        interessen: nwk.value.interessen.map(t => ({ id: t.id, name: t.name })),
        wunschabteilungen: nwk.value.wunschabteilungen.map(d => ({ id: d.id, name: d.name })),
        knowsProgramming: data.knowsProgramming ?? false
      }
    }

  } catch (err) {
    console.error("Fehler beim Laden der persönlichen Daten:", err)
  }
}

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
      name: d.dateipfad.split("/").pop() ?? "Unbekannt",
      url: d.dateipfad
    }))
  } catch (err) {
    console.error("Fehler beim Laden der Dokumente:", err)
  }
}

// ----------------------------------------
// Aktionen
// ----------------------------------------
function handleExperienceSave(updated: NwkExperience) {
  // Wert übernehmen und nicht zurücksetzen
  nwkExperience.value = {
    wunschabteilungen: updated.wunschabteilungen.map(d => ({ id: d.id, name: d.name })),
    interessen: updated.interessen.map(t => ({ id: t.id, name: t.name })),
    knowsProgramming: updated.knowsProgramming
  }
  alert("Bevorzugte Abteilungen & Interessen gespeichert!")
}

async function handleUploadSave(newFiles: StoredFile[]) {
  // Implementierung wie bisher
}

async function handleDeleteFile(id: number) {
  // Implementierung wie bisher
}

// ----------------------------------------
// onMounted: Session-Login prüfen
// ----------------------------------------
onMounted(() => {
  loggedIn.value = sessionStorage.getItem("loggedIn") === "true"

  if (!loggedIn.value) {
    router.replace("/login")
    return
  }

  const userJson = sessionStorage.getItem("user")
  if (userJson) {
    const userData = JSON.parse(userJson)
    nwk.value = {
      id: userData.id,
      personalnummer: userData.personalnummer,
      vorname: userData.vorname,
      nachname: userData.nachname,
      email: userData.email,
      studienrichtung: userData.studienrichtung,
      jahrgang: userData.jahrgang,
      interessen: userData.interessen ?? [],
      wunschabteilungen: userData.wunschabteilungen ?? []
    }

    // Erfahrung nur initialisieren, wenn noch nicht gesetzt
    if (!nwkExperience.value) {
      nwkExperience.value = {
        interessen: nwk.value.interessen.map(t => ({ id: t.id, name: t.name })),
        wunschabteilungen: nwk.value.wunschabteilungen.map(d => ({ id: d.id, name: d.name })),
        knowsProgramming: false
      }
    }

    loadPersonal()
    loadDocuments()
  } else {
    console.error("Kein eingeloggter Nutzer gefunden")
  }
})
</script>

<style scoped>
.v-card-title { font-weight: 600; }
</style>
