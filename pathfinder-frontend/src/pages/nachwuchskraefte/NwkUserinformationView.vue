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
  try {
    const res = await fetch(`/api/meinKonto/personal/1`) // feste ID
    if (!res.ok) throw new Error(`Fehler beim Laden: ${res.status}`)
    nwk.value = await res.json()
  } catch (err) {
    console.error('Fehler beim Laden der persönlichen Daten:', err)
  }
}

async function loadExperience() {
  try {
    const nwkId = nwk.value?.id
    if (!nwkId) return

    const res = await fetch(`/api/meinKonto/experience/${nwkId}`)
    if (!res.ok) throw new Error(`Fehler beim Laden: ${res.status}`)
    const data = await res.json()
    nwkExperience.value = {
      interessen: nwk.value.interessen.map(t => ({ id: t.id, name: t.name })),
      wunschabteilungen: nwk.value.wunschabteilungen.map(d => ({ id: d.id, name: d.name })),
      knowsProgramming: false
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
  nwkExperience.value = {
    wunschabteilungen: updated.wunschabteilungen.map(d => ({ id: d.id, name: d.name })),
    interessen: updated.interessen.map(t => ({ id: t.id, name: t.name })),
    knowsProgramming: updated.knowsProgramming
  }
  alert("Bevorzugte Abteilungen & Interessen gespeichert!")
}

async function handleUploadSave(newFiles: StoredFile[]) {
  const nwkId = nwk.value?.id
  if (!nwkId) return

  try {
    for (const file of newFiles) {
      if (!file.fileObject) continue
      const formData = new FormData()
      formData.append('file', file.fileObject)
      formData.append('nwkId', nwkId.toString())

      const res = await fetch(`/api/meinKonto/documents`, {
        method: 'POST',
        body: formData
      })
      if (!res.ok) throw new Error(`Upload fehlgeschlagen: ${res.status}`)
      const saved = await res.json()
      savedFiles.value.push({
        id: saved.id,
        name: saved.dateipfad.split('/').pop() ?? saved.name,
        url: saved.dateipfad
      })
    }
    alert('Dateien erfolgreich hochgeladen!')
  } catch (err) {
    console.error('Fehler beim Hochladen der Datei(en):', err)
    alert('Fehler beim Hochladen der Datei(en).')
  }
}

async function handleDeleteFile(fileId: number) {
  try {
    const res = await fetch(`/api/meinKonto/documents/${fileId}`, { method: 'DELETE' })
    if (!res.ok) throw new Error(`Fehler: ${res.status}`)
    savedFiles.value = savedFiles.value.filter(f => f.id !== fileId)
  } catch (err) {
    console.error('Fehler beim Löschen:', err)
    alert('Fehler beim Löschen des Dokuments.')
  }
}

// ----------------------------
// onMounted
// ----------------------------
onMounted(async () => {
  await loadPersonal()
  await loadExperience()
  await loadDocuments()
})
</script>

<style scoped>
.v-card-title { font-weight: 600; }
</style>
