<template>
  <v-card class="pa-4 mb-4">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>Dokumente</span>
      <v-btn small text @click="dialogOpen = true">Hochladen</v-btn>
    </v-card-title>

    <v-divider></v-divider>

    <v-card-text>
      <v-list>
        <v-list-item v-for="file in files" :key="file.id">
          <v-list-item-content>
            <v-list-item-title>
              <a :href="file.dateipfad" target="_blank">{{ file.name }}</a>
            </v-list-item-title>
            <v-list-item-subtitle>Typ: {{ file.typ }}</v-list-item-subtitle>
            <v-list-item-subtitle>Hochgeladen: {{ formatDate(file.hochgeladenAm) }}</v-list-item-subtitle>
          </v-list-item-content>
          <v-list-item-action>
            <v-btn icon @click="deleteFile(file.id)">
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </v-list-item-action>
        </v-list-item>
      </v-list>
      <p v-if="files.length === 0">Keine Dokumente vorhanden.</p>
    </v-card-text>

    <v-snackbar v-model="snackbar.show" :timeout="3000" top right>
      {{ snackbar.message }}
      <template #actions>
        <v-btn text @click="snackbar.show = false">Schließen</v-btn>
      </template>
    </v-snackbar>

    <BaseDialogNwkUploadDocuments
      v-model="dialogOpen"
      :nwkId="nwkId"
      :savedFiles="files"
      @save="handleUploadSave"
    />
  </v-card>
</template>

<script setup lang="ts">
import { ref, onMounted, defineProps } from 'vue'
import BaseDialogNwkUploadDocuments from './BaseDialogNwkUploadDocuments.vue'

interface StoredFile {
  id: number
  name: string
  dateipfad: string
  typ: any
  hochgeladenAm: string
}

// Props
const props = defineProps<{ nwkId?: number }>()
const nwkId = ref<number>(props.nwkId ?? 0)

const files = ref<StoredFile[]>([])
const snackbar = ref({ show: false, message: '' })
const dialogOpen = ref(false)

// ----------------------------
// Dokumente laden
// ----------------------------
async function loadDocuments() {
  if (!nwkId.value) return

  try {
    const res = await fetch(`/api/meinKonto/documents/${nwkId.value}`)
    if (!res.ok) {
      if (res.status === 204) {
        files.value = []
        return
      }
      throw new Error(`Fehler beim Laden: ${res.status}`)
    }

    const data = await res.json()

    // Hier korrekt auf `documents` zugreifen
    files.value = data.documents.map((d: any) => ({
      id: d.id,
      name: d.dateipfad.split('/').pop() ?? 'Unbekannt',
      dateipfad: d.dateipfad,
      typ: d.typ,
      hochgeladenAm: d.hochgeladenAm
    }))
  } catch (err) {
    console.error('Fehler beim Laden der Dokumente:', err)
    snackbar.value.message = 'Fehler beim Laden der Dokumente'
    snackbar.value.show = true
  }
}

// ----------------------------
// Datei löschen
// ----------------------------
async function deleteFile(fileId: number) {
  try {
    const res = await fetch(`/api/meinKonto/documents/${fileId}`, { method: 'DELETE' })
    if (!res.ok) throw new Error(`Fehler: ${res.status}`)
    files.value = files.value.filter(f => f.id !== fileId)
    snackbar.value.message = 'Dokument gelöscht'
    snackbar.value.show = true
  } catch (err) {
    console.error('Fehler beim Löschen:', err)
    snackbar.value.message = 'Fehler beim Löschen des Dokuments'
    snackbar.value.show = true
  }
}

// ----------------------------
// Upload speichern
// ----------------------------
function handleUploadSave(newFiles: StoredFile[]) {
  files.value = newFiles
  snackbar.value.message = 'Dateien wurden hochgeladen.'
  snackbar.value.show = true
}

// ----------------------------
// Datum formatieren
// ----------------------------
function formatDate(dateStr: string) {
  return new Date(dateStr).toLocaleDateString('de-DE')
}

// ----------------------------
// onMounted: nwkId aus sessionStorage, dann laden
// ----------------------------
onMounted(() => {
  if (!nwkId.value) {
    const loggedIn = sessionStorage.getItem('loggedIn') === 'true'
    if (!loggedIn) return

    const userJson = sessionStorage.getItem('user')
    if (userJson) {
      const userData = JSON.parse(userJson)
      nwkId.value = userData.id
    }
  }
  loadDocuments()
})
</script>
