<template>
  <v-dialog v-model="internalModel" max-width="600px" persistent>
    <v-card>
      <v-card-title>Abteilungen & Interessen bearbeiten</v-card-title>

      <v-card-text>
        <!-- Bevorzugte Abteilungen -->
        <div class="mb-4">
          <h3>Wunschabteilungen <span class="text-red">*</span></h3>
          <v-select
            v-for="i in 3"
            :key="'dept-' + i"
            v-model="selectedDepartmentsNames[i-1]"
            :items="availableDepartments(i-1)"
            label="Abteilung auswählen"
            clearable
            hide-details
            class="mb-2"
          />
        </div>

        <!-- Programmieren optional -->
        <v-checkbox
          v-model="formData.knowsProgramming"
          label="Kannst du programmieren?"
        />
        <v-text-field
          v-if="formData.knowsProgramming"
          v-model="formData.programmingLanguagesString"
          label="Programmiersprachen (Komma getrennt)"
          hide-details
        />

        <!-- Interessen -->
        <div class="mb-4 mt-4">
          <h3>Interessen <span class="text-red">*</span></h3>
          <v-select
            v-for="i in 4"
            :key="'tag-' + i"
            v-model="selectedTagsNames[i-1]"
            :items="availableTags(i-1)"
            label="Interesse auswählen"
            clearable
            hide-details
            class="mb-2"
          />
        </div>

        <p v-if="error" class="text-red mt-2">{{ error }}</p>
      </v-card-text>

      <v-card-actions>
        <v-spacer />
        <v-btn text @click="close">Abbrechen</v-btn>
        <v-btn color="primary" @click="save">Speichern</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, computed, onMounted } from 'vue'

interface Abteilung { id: number; name: string }
interface Tag { id: number; name: string }

interface NwkExperienceApiPayload {
  id: number
  interessen: Tag[]
  wunschabteilungen: Abteilung[]
  programmieren: boolean
  programmiersprachen: string
}

const props = defineProps<{
  modelValue: boolean
  nwkExperience: {
    wunschabteilungen: Abteilung[]
    interessen: Tag[]
    knowsProgramming?: boolean
    programmingLanguages?: string[]
  } | null
  nwkId: number
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void
  (e: 'save', updated: NwkExperienceApiPayload): void
}>()

const internalModel = ref(props.modelValue)
const error = ref('')

// ---------------- Optionen ----------------
const allDepartments = ref<Abteilung[]>([])
const allTags = ref<Tag[]>([])

async function loadOptions() {
  try {
    const res = await fetch('/api/meinKonto/options')
    if (!res.ok) throw new Error(`Fehler beim Laden der Optionen: ${res.status}`)
    const data = await res.json()
    allDepartments.value = data.abteilungen ?? []
    allTags.value = data.tags ?? []
  } catch (err) {
    console.error(err)
  }
}

// ---------------- Auswahlmodelle ----------------
const selectedDepartmentsNames = ref<string[]>(['', '', ''])
const selectedTagsNames = ref<string[]>(['', '', ''])
const formData = ref({ knowsProgramming: false, programmingLanguagesString: '' })

// ---------------- Initialisierung ----------------
let initialized = false
watch(
  () => props.modelValue,
  val => {
    if (val && props.nwkExperience && !initialized) {
      selectedDepartmentsNames.value = props.nwkExperience.wunschabteilungen
        .map(d => d.name)
        .concat(['', '', ''])
        .slice(0, 3)

      selectedTagsNames.value = props.nwkExperience.interessen
        .map(t => t.name)
        .concat(['', '', ''])
        .slice(0, 3)

      formData.value.knowsProgramming = props.nwkExperience.knowsProgramming ?? false
      formData.value.programmingLanguagesString =
        (props.nwkExperience.programmingLanguages || []).join(', ')

      initialized = true
    }
  },
  { immediate: true }
)

// ---------------- Dynamische Optionen ohne Duplikate ----------------
function availableDepartments(index: number) {
  return allDepartments.value
    .map(d => d.name)
    .filter(name => !selectedDepartmentsNames.value.includes(name) || name === selectedDepartmentsNames.value[index])
}

function availableTags(index: number) {
  return allTags.value
    .map(t => t.name)
    .filter(name => !selectedTagsNames.value.includes(name) || name === selectedTagsNames.value[index])
}

// ---------------- Sync Dialog ----------------
watch(() => props.modelValue, val => (internalModel.value = val))
watch(internalModel, val => emit('update:modelValue', val))

function close() {
  error.value = ''
  internalModel.value = false
}

// ---------------- Speichern + PUT ----------------
async function save() {
  if (selectedDepartmentsNames.value.every(v => !v) || selectedTagsNames.value.every(v => !v)) {
    error.value = 'Bitte mindestens eine Abteilung und ein Interesse auswählen.'
    return
  }

  const selectedDepartments = selectedDepartmentsNames.value
    .map(name => allDepartments.value.find(d => d.name === name))
    .filter((d): d is Abteilung => !!d)

  const selectedTags = selectedTagsNames.value
    .map(name => allTags.value.find(t => t.name === name))
    .filter((t): t is Tag => !!t)

  const payload: NwkExperienceApiPayload = {
    id: props.nwkId,
    wunschabteilungen: selectedDepartments,
    interessen: selectedTags,
    programmieren: formData.value.knowsProgramming,
    programmiersprachen: formData.value.programmingLanguagesString
      .split(',')
      .map(s => s.trim())
      .filter(Boolean)
      .join(', ')
  }

  try {
    const res = await fetch(`/api/meinKonto/experience/${props.nwkId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    })
    if (!res.ok) throw new Error(`Fehler beim Speichern: ${res.status}`)

    emit('save', payload)
    initialized = false
    close()
  } catch (err) {
    console.error(err)
    error.value = 'Fehler beim Speichern. Bitte erneut versuchen.'
  }
}

// ---------------- onMounted ----------------
onMounted(() => {
  loadOptions()
})
</script>

<style scoped>
.text-red { color: red; }
.mb-2 { margin-bottom: 0.5rem; }
.mb-4 { margin-bottom: 1rem; }
</style>
