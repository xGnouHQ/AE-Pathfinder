<template>
  <v-dialog v-model="internalModel" max-width="600px" persistent>
    <v-card>
      <v-card-title>Abteilungen & Interessen bearbeiten</v-card-title>
      <v-card-text>
        <!-- Abteilungen Dropdowns -->
        <div class="mb-4">
          <h3>Bevorzugte Abteilungen <span class="text-red">*</span></h3>
          <v-select
            v-for="i in 3"
            :key="'dept-' + i"
            v-model="selectedDepartmentsNames[i-1]"
            :items="departmentNames"
            label="Abteilung auswählen"
            clearable
            hide-details
            class="mb-2"
          />
        </div>

        <!-- Checkbox Programmieren -->
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

        <!-- Interessen Dropdowns -->
        <div class="mb-4">
          <h3>Interessen <span class="text-red">*</span></h3>
          <v-select
            v-for="i in 3"
            :key="'tag-' + i"
            v-model="selectedTagsNames[i-1]"
            :items="tagNames"
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
import { ref, watch, defineProps, defineEmits, computed } from 'vue'

interface Abteilung { id: number; name: string }
interface Tag { id: number; name: string }
interface NwkExperience {
  wunschabteilungen: Abteilung[]
  interessen: Tag[]
  knowsProgramming: boolean
  programmingLanguages: string[]
}

const props = defineProps<{
  modelValue: boolean
  nwkExperience: NwkExperience | null
  nwkId: number
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void
  (e: 'save', updated: NwkExperience): void
}>()

const internalModel = ref(props.modelValue)
const error = ref('')

// Dropdown-Optionen
const allDepartments: Abteilung[] = [
  { id: 1, name: 'IT@M - IT-Dienstleistungen' },
  { id: 2, name: 'IT-Architektur' },
  { id: 3, name: 'Finanzen' },
  { id: 4, name: 'Kreisverwaltungsreferat - Bürgerdienste' },
  { id: 5, name: 'Kommunalreferat - Stadtplanung' }
]

const allTags: Tag[] = [
  { id: 1, name: 'Java' },
  { id: 2, name: 'Spring Boot' },
  { id: 3, name: 'Projektmanagement' },
  { id: 4, name: 'Teamarbeit' },
  { id: 5, name: 'Kommunikation' },
  { id: 6, name: 'Datenanalyse' },
  { id: 7, name: 'Bürgerdienste' },
  { id: 8, name: 'Stadtplanung' }
]

// Nur Namen für die Dropdowns
const departmentNames = computed(() => allDepartments.map(d => d.name))
const tagNames = computed(() => allTags.map(t => t.name))

// Auswahlmodelle: nur Namen für die v-selects
const selectedDepartmentsNames = ref<string[]>(['', '', ''])
const selectedTagsNames = ref<string[]>(['', '', ''])
const formData = ref({ knowsProgramming: false, programmingLanguagesString: '' })

// Props → initialisieren
watch(
  () => props.modelValue,
  val => {
    if (val && props.nwkExperience) {
      selectedDepartmentsNames.value = props.nwkExperience.wunschabteilungen
        .map(d => d.name)
        .concat(['', '', ''])
        .slice(0,3)

      selectedTagsNames.value = props.nwkExperience.interessen
        .map(t => t.name)
        .concat(['', '', ''])
        .slice(0,3)

      formData.value.knowsProgramming = props.nwkExperience.knowsProgramming
      formData.value.programmingLanguagesString = (props.nwkExperience.programmingLanguages || []).join(', ')
    }
  },
  { immediate: true }
)

watch(() => props.modelValue, val => (internalModel.value = val))
watch(internalModel, val => emit('update:modelValue', val))

function close() {
  error.value = ''
  internalModel.value = false
}

async function save() {
  if (selectedDepartmentsNames.value.every(v => !v) || selectedTagsNames.value.every(v => !v)) {
    error.value = 'Bitte mindestens eine Abteilung und ein Interesse auswählen.'
    return
  }

  // IDs anhand der Namen ermitteln
  const departmentIds = selectedDepartmentsNames.value
    .map(name => allDepartments.find(d => d.name === name)?.id)
    .filter(Boolean) as number[]

  const tagIds = selectedTagsNames.value
    .map(name => allTags.find(t => t.name === name)?.id)
    .filter(Boolean) as number[]

  const updatedNwk: NwkExperience = {
    wunschabteilungen: allDepartments.filter(d => departmentIds.includes(d.id)),
    interessen: allTags.filter(t => tagIds.includes(t.id)),
    knowsProgramming: formData.value.knowsProgramming,
    programmingLanguages: formData.value.programmingLanguagesString
      .split(',')
      .map(s => s.trim())
      .filter(s => s.length > 0)
  }

  try {
    const res = await fetch(`/api/meinKonto/experience/${props.nwkId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(updatedNwk)
    })

    if (!res.ok) throw new Error(`Fehler: ${res.status}`)
    emit('save', updatedNwk)
    close()
    alert('Erfahrungen & Interessen gespeichert!')
  } catch (err) {
    console.error(err)
    error.value = 'Fehler beim Speichern!'
  }
}
</script>

<style scoped>
.text-red { color: red; }
.mb-2 { margin-bottom: 0.5rem; }
.mb-4 { margin-bottom: 1rem; }
</style>
