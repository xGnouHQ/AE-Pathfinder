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
              <a v-if="file.url" :href="file.url" target="_blank">{{ file.name }}</a>
              <span v-else>{{ file.name }}</span>
            </v-list-item-title>
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

    <!-- Snackbar -->
    <v-snackbar v-model="snackbar.show" :timeout="3000" top right>
      {{ snackbar.message }}
      <template #actions>
        <v-btn text @click="snackbar.show = false">Schließen</v-btn>
      </template>
    </v-snackbar>

    <!-- Upload Dialog -->
    <BaseDialogNwkUploadDocuments
      v-model="dialogOpen"
      :savedFiles="files"
      :nwkId="nwkId"
      @save="handleUploadSave"
    />
  </v-card>
</template>

<script setup lang="ts">
import { ref, onMounted, defineProps } from 'vue'
import BaseDialogNwkUploadDocuments from './BaseDialogNwkUploadDocuments.vue'

interface StoredFile {
  id: number | string
  name: string
  url?: string
  fileObject?: File
}

// Props
const props = defineProps<{ nwkId?: number }>()
const nwkId = ref<number>(props.nwkId ?? 0) // Default 0, wird ggf. überschrieben

const files = ref<StoredFile[]>([])
const snackbar = ref({ show: false, message: '' })
const dialogOpen = ref(false)

// ----------------------------
// Dokumente vom Backend laden
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
    files.value = data.map((d: any) => ({
      id: d.id,
      name: d.dateipfad.split('/').pop() ?? d.name ?? 'Unbekannt',
      url: d.dateipfad ?? ''
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
async function deleteFile(fileId: number | string) {
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
// Upload speichern (vom Dialog)
// ----------------------------
function handleUploadSave(newFiles: StoredFile[]) {
  files.value = newFiles
  snackbar.value.message = 'Dateien wurden hochgeladen.'
  snackbar.value.show = true
}

// ----------------------------
// onMounted: nwkId aus localStorage, dann Dokumente laden
// ----------------------------
onMounted(() => {
  if (!nwkId.value) {
    const userJson = localStorage.getItem('user')
    if (userJson) {
      const userData = JSON.parse(userJson)
      nwkId.value = userData.id
    } else {
      console.error('Kein eingeloggter Nutzer gefunden')
      return
    }
  }
  loadDocuments()
})
</script>
