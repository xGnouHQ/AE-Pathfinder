<template>
  <v-dialog v-model="internalModelValue" max-width="600px" persistent>
    <v-card>
      <v-card-title>Dateien hochladen</v-card-title>
      <v-divider></v-divider>
      <v-card-text>
        <!-- Upload Input hier -->
        <input type="file" multiple @change="onFilesSelected" />
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text @click="closeDialog">Abbrechen</v-btn>
        <v-btn color="primary" @click="saveFiles">Speichern</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, defineEmits, defineProps } from 'vue'

interface StoredFile {
  id: string
  name: string
  fileObject?: File
}

const props = defineProps<{
  modelValue: boolean
  savedFiles: StoredFile[]
}>()

const emit = defineEmits(['update:modelValue', 'save'])

// Lokaler state für v-model
const internalModelValue = ref(props.modelValue)

// Synchronisiere prop mit lokalem state
watch(() => props.modelValue, (val) => {
  internalModelValue.value = val
})

// Wenn sich lokaler state ändert, emittiere update zurück
watch(internalModelValue, (val) => {
  emit('update:modelValue', val)
})

const internalFiles = ref<StoredFile[]>([])

function closeDialog() {
  internalModelValue.value = false
}

function onFilesSelected(event: Event) {
  const target = event.target as HTMLInputElement
  if (!target.files) return
  for (const file of Array.from(target.files)) {
    internalFiles.value.push({
      id: crypto.randomUUID(),
      name: file.name,
      fileObject: file,
    })
  }
}

function saveFiles() {
  const allFiles = [...props.savedFiles, ...internalFiles.value]
  emit('save', allFiles)
  internalModelValue.value = false
}
</script>
