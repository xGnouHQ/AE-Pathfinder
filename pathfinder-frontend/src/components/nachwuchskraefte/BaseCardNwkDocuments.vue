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
      @save="handleUploadSave"
    />
  </v-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import BaseDialogNwkUploadDocuments from './BaseDialogNwkUploadDocuments.vue' // Pfad anpassen

interface StoredFile {
  id: string
  name: string
  url?: string
  fileObject?: File
}

const files = ref<StoredFile[]>([
  { id: '1', name: 'Lebenslauf.pdf', url: '/files/Lebenslauf.pdf' },
  { id: '2', name: 'Zeugnis.docx', url: '/files/Zeugnis.docx' },
])

const snackbar = ref({ show: false, message: '' })
const dialogOpen = ref(false)

// Datei löschen
function deleteFile(fileId: string) {
  const file = files.value.find(f => f.id === fileId)
  if (!file) return

  files.value = files.value.filter(f => f.id !== fileId)
  snackbar.value.message = `Dokument "${file.name}" wurde gelöscht.`
  snackbar.value.show = true
}

// Upload speichern
function handleUploadSave(newFiles: StoredFile[]) {
  files.value = newFiles
  snackbar.value.message = `Dateien wurden hochgeladen.`
  snackbar.value.show = true
}
</script>
