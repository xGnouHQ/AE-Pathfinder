<template>
  <v-dialog v-model="internalModel" max-width="600px" persistent>
    <v-card>
      <v-card-title>Dateien hochladen</v-card-title>
      <v-divider></v-divider>

      <v-card-text>
        <!-- Datei-Input -->
        <input type="file" multiple @change="onFilesSelected" />

        <!-- Vorschau ausgewählter Dateien -->
        <ul>
          <li v-for="file in internalFiles" :key="file.id">{{ file.name }}</li>
        </ul>
        <p v-if="internalFiles.length === 0">Noch keine Dateien ausgewählt.</p>
      </v-card-text>

      <v-card-actions>
        <v-spacer />
        <v-btn text @click="closeDialog">Abbrechen</v-btn>
        <v-btn color="primary" @click="saveFiles">Speichern</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue'

interface StoredFile {
  id: string | number
  name: string
  fileObject?: File
  url?: string
}

// Props
const props = defineProps<{
  modelValue: boolean
  savedFiles: StoredFile[]
  nwkId: number
}>()

// Emits
const emit = defineEmits(['update:modelValue', 'save'])

// Lokaler State für v-model
const internalModel = ref(props.modelValue)
watch(() => props.modelValue, (val) => (internalModel.value = val))
watch(internalModel, (val) => emit('update:modelValue', val))

// Temporäre Dateien im Dialog
const internalFiles = ref<StoredFile[]>([])

// Dateien auswählen
function onFilesSelected(event: Event) {
  const target = event.target as HTMLInputElement
  if (!target.files) return

  for (const file of Array.from(target.files)) {
    internalFiles.value.push({
      id: crypto.randomUUID(),
      name: file.name,
      fileObject: file
    })
  }
}

// Dateien speichern
async function saveFiles() {
  const nwkId = props.nwkId
  const allFiles: StoredFile[] = [...props.savedFiles]

  for (const file of internalFiles.value) {
    if (!file.fileObject) continue
    const formData = new FormData()
    formData.append('file', file.fileObject)
    formData.append('nwkId', nwkId.toString())

    const res = await fetch(`/api/meinKonto/documents`, {
      method: 'POST',
      body: formData
    })
    if (!res.ok) {
      console.error(`Upload fehlgeschlagen: ${res.status}`)
      continue
    }
    const saved = await res.json()
    allFiles.push({
      id: saved.id,
      name: saved.dateipfad.split('/').pop() ?? saved.name,
      url: saved.dateipfad
    })
  }

  emit('save', allFiles)
  closeDialog()
}

// Dialog schließen
function closeDialog() {
  internalFiles.value = []
  internalModel.value = false
}
</script>
