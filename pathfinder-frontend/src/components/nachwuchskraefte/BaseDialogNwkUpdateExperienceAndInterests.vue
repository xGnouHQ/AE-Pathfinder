<template>
  <v-dialog v-model="internalModel" max-width="600px" persistent>
    <v-card>
      <v-card-title>Abteilungen & Interessen bearbeiten</v-card-title>
      <v-card-text>
        <!-- Bevorzugte Abteilungen -->
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
import { ref, watch, computed, defineProps, defineEmits } from 'vue'

interface Abteilung { id: number; name: string }
interface Tag { id: number; name: string }

interface NwkExperience {
  interessen: Tag[]
  wunschabteilungen: Abteilung[]
  knowsProgramming?: boolean
  programmingLanguages?: string[]
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

// ---------------- Hardcodierte Dropdowns ----------------
const allDepartments: Abteilung[] = [
  { id: 1, name: 'KM11' }, { id: 2, name: 'KM12' }, { id: 3, name: 'KM13' },
  { id: 4, name: 'KM21' }, { id: 5, name: 'KM22' }, { id: 6, name: 'KM23' },
  { id: 7, name: 'KM31' }, { id: 8, name: 'KM32' }, { id: 9, name: 'KM41' },
  { id: 10, name: 'KM42' }, { id: 11, name: 'KM43' }, { id: 12, name: 'KM51' },
  { id: 13, name: 'KM52' }, { id: 14, name: 'KM53' }, { id: 15, name: 'KM61' },
  { id: 16, name: 'KM62' }, { id: 17, name: 'KM81' }, { id: 18, name: 'KM82' },
  { id: 19, name: 'KM83' }, { id: 20, name: 'IBS11' }, { id: 21, name: 'IBS12' },
  { id: 22, name: 'IBS13' }, { id: 23, name: 'IBS21' }, { id: 24, name: 'IBS22' },
  { id: 25, name: 'IBS23' }, { id: 26, name: 'IBS31' }, { id: 27, name: 'IBS32' },
  { id: 28, name: 'IBS41' }, { id: 29, name: 'IBS42' }, { id: 30, name: 'IBS43' },
  { id: 31, name: 'IBS51' }, { id: 32, name: 'IBS52' }, { id: 33, name: 'IBS53' },
  { id: 34, name: 'IBS61' }, { id: 35, name: 'IBS62' }, { id: 36, name: 'IBS81' },
  { id: 37, name: 'IBS82' }, { id: 38, name: 'IBS83' }
]

const allTags: Tag[] = [
  { id: 1, name: 'Java' }, { id: 2, name: 'Spring Boot' }, { id: 3, name: 'Projektmanagement' },
  { id: 4, name: 'Teamarbeit' }, { id: 5, name: 'Kommunikation' }, { id: 6, name: 'Datenanalyse' },
  { id: 7, name: 'Bürgerdienste' }, { id: 8, name: 'Stadtplanung' }, { id: 9, name: 'DevOps' },
  { id: 10, name: 'CI/CD' }, { id: 11, name: 'Docker' }, { id: 12, name: 'Kubernetes' },
  { id: 13, name: 'Linux' }, { id: 14, name: 'Windows Client' }, { id: 15, name: 'Datenbanken' },
  { id: 16, name: 'SQL' }, { id: 17, name: 'Security' }, { id: 18, name: 'Netzwerk' },
  { id: 19, name: 'Frontend' }, { id: 20, name: 'Backend' }, { id: 21, name: 'Cloud' },
  { id: 22, name: 'Monitoring' }, { id: 23, name: 'Support' }, { id: 24, name: 'Incident Response' },
  { id: 25, name: 'ETL' }, { id: 26, name: 'Identity & Access' }, { id: 27, name: 'Virtualisierung' },
  { id: 28, name: 'Webentwicklung' }
]

const departmentNames = computed(() => allDepartments.map(d => d.name))
const tagNames = computed(() => allTags.map(t => t.name))

// ---------------- Auswahlmodelle ----------------
const selectedDepartmentsNames = ref<string[]>(['', '', ''])
const selectedTagsNames = ref<string[]>(['', '', ''])
const formData = ref({ knowsProgramming: false, programmingLanguagesString: '' })

// ---------------- Props → initialisieren ----------------
watch(
  () => props.modelValue,
  val => {
    if (val && props.nwkExperience) {
      selectedDepartmentsNames.value = props.nwkExperience.wunschabteilungen
        .map(d => d.name)
        .concat(['', '', ''])
        .slice(0, 3)

      selectedTagsNames.value = props.nwkExperience.interessen
        .map(t => t.name)
        .concat(['', '', ''])
        .slice(0, 3)

      formData.value.knowsProgramming = props.nwkExperience.knowsProgramming ?? false
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

// ---------------- Speichern ----------------
async function save() {
  if (selectedDepartmentsNames.value.every(v => !v) || selectedTagsNames.value.every(v => !v)) {
    error.value = 'Bitte mindestens eine Abteilung und ein Interesse auswählen.'
    return
  }

  // IDs anhand der Namen
  const wunschabteilungenIds = selectedDepartmentsNames.value
    .map(name => allDepartments.find(d => d.name === name)?.id)
    .filter((id): id is number => !!id)

  const interessenIds = selectedTagsNames.value
    .map(name => allTags.find(t => t.name === name)?.id)
    .filter((id): id is number => !!id)

  const payload = {
    wunschabteilungenIds,
    interessenIds,
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
      body: JSON.stringify(payload)
    })

    if (!res.ok) throw new Error(`Fehler: ${res.status}`)

    const data = await res.json() // <-- ExperienceResponse vom Backend
    emit('save', data)
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
