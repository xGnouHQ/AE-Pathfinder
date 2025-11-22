<template>
  <v-dialog v-model="internalModel" max-width="500px" persistent>
    <v-card>
      <v-card-title>Datei hochladen</v-card-title>
      <v-divider></v-divider>

      <v-card-text>
        <!-- Datei auswählen -->
        <v-file-input
          v-model="selectedFile"
          label="Datei auswählen"
          accept=".pdf,.doc,.docx,.txt"
          outlined
          dense
          required
        />

        <!-- Dokumenttyp Dropdown -->
        <v-select
          v-model="selectedTyp"
          :items="docTypes"
          label="Dokumenttyp auswählen"
          outlined
          dense
          required
          class="mt-4"
        />

        <p v-if="error" class="text-red mt-2">{{ error }}</p>
      </v-card-text>

      <v-card-actions>
        <v-spacer />
        <v-btn text @click="closeDialog">Abbrechen</v-btn>
        <v-btn color="primary" @click="uploadFile">Hochladen</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue'

interface StoredFile {
  id: number | string
  name: string
  url?: string
  typ?: 'LEBENSLAUF' | 'MOTIVATIONSSCHREIBEN' | 'ZEUGNIS' | 'SONSTIGES'
}

// Props
const props = defineProps<{
  modelValue: boolean
  nwkId: number
  savedFiles: StoredFile[]
}>()

const emit = defineEmits(['update:modelValue', 'save'])

// Dialog v-model intern
const internalModel = ref(props.modelValue)
watch(() => props.modelValue, val => (internalModel.value = val))
watch(internalModel, val => emit('update:modelValue', val))

// Datei + Typ
const selectedFile = ref<File | null>(null)
const selectedTyp = ref<StoredFile['typ'] | null>(null)
const error = ref('')

// Dokumenttypen
const docTypes: StoredFile['typ'][] = [
  'LEBENSLAUF',
  'MOTIVATIONSSCHREIBEN',
  'ZEUGNIS',
  'SONSTIGES'
]

// Datei hochladen
async function uploadFile() {
  if (!selectedFile.value || !selectedTyp.value) {
    error.value = 'Bitte Datei und Dokumenttyp auswählen!'
    return
  }

  const formData = new FormData()
  formData.append('file', selectedFile.value)
  formData.append('nwkId', props.nwkId.toString())
  formData.append('typ', selectedTyp.value)

  try {
    const res = await fetch('/api/meinKonto/documents', {
      method: 'POST',
      body: formData
    })

    if (!res.ok) throw new Error(`Upload fehlgeschlagen: ${res.status}`)
    const saved = await res.json()

    // Zur Liste hinzufügen
    const updatedFiles = [...props.savedFiles]
    updatedFiles.push({
      id: saved.id,
      name: saved.fileName.split('/').pop(),
      url: saved.fileName,
      typ: saved.typ
    })

    emit('save', updatedFiles)
    closeDialog()
  } catch (err) {
    console.error(err)
    error.value = 'Fehler beim Upload!'
  }
}

function closeDialog() {
  selectedFile.value = null
  selectedTyp.value = null
  error.value = ''
  internalModel.value = false
}
</script>

<style scoped>
.text-red {
  color: red;
}
.mt-2 {
  margin-top: 0.5rem;
}
.mt-4 {
  margin-top: 1rem;
}
</style>
