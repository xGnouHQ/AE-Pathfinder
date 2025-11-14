<template>
  <!-- Wichtig: Dialog sichtbar, wenn internalModel true -->
  <v-dialog v-model="internalModel" max-width="600px" persistent>
    <v-card>
      <v-card-title class="text-h6">
        Bevorzugte Abteilungen & Interessen bearbeiten
      </v-card-title>

      <v-card-text>
        <v-form ref="form">
          <!-- Abteilungen -->
          <div class="mb-4">
            <h3>Bevorzugte Abteilungen <span class="text-red">*</span></h3>
            <v-select
              v-for="(item, index) in formData.erfahrung"
              :key="'erfahrung-' + index"
              v-model="formData.erfahrung[index]"
              :label="`Abteilung ${index + 1}`"
              :items="erfahrungOptions"
              class="mb-2"
              clearable
              hide-details
            />
          </div>

          <!-- Programmieren -->
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
          <div class="mt-4">
            <h3>Interessen <span class="text-red">*</span></h3>
            <v-select
              v-for="(interest, index) in formData.interests"
              :key="'interest-' + index"
              v-model="formData.interests[index]"
              :label="`Interesse ${index + 1}`"
              :items="interestOptions"
              class="mb-2"
              clearable
              hide-details
            />
          </div>

          <!-- Fehlermeldung -->
          <p v-if="error" class="text-red mt-2">{{ error }}</p>
        </v-form>
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
import { ref, watch } from 'vue'

interface NwkExperience {
  erfahrung: string[]
  knowsProgramming: boolean
  programmingLanguages: string[]
  interests: string[]
}

const props = defineProps<{
  modelValue: boolean
  nwkExperience: NwkExperience
  nwkId: number
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void
  (e: 'save', updated: NwkExperience): void
}>()

const internalModel = ref(props.modelValue)
const error = ref('')

// Synchronisation mit Props
watch(() => props.modelValue, val => (internalModel.value = val))
watch(internalModel, val => emit('update:modelValue', val))

// Optionen
const erfahrungOptions = ['Km1', 'Km2', 'Km3', 'Km4']
const interestOptions = ['Programmieren', 'Teamarbeit', 'Infrastruktur', 'Projekt']

// Default-Werte
const formData = ref({
  erfahrung: [null, null, null],
  knowsProgramming: false,
  programmingLanguagesString: '',
  interests: [null, null, null, null, null]
})

// Props → formData synchronisieren
watch(
  () => props.nwkExperience,
  newVal => {
    if (!newVal) return
    formData.value.erfahrung = newVal.erfahrung?.length
      ? [...newVal.erfahrung]
      : [null, null, null]
    formData.value.knowsProgramming = newVal.knowsProgramming
    formData.value.programmingLanguagesString = newVal.programmingLanguages.join(', ')
    formData.value.interests = newVal.interests?.length
      ? [...newVal.interests]
      : [null, null, null, null, null]
  },
  { immediate: true }
)

watch(() => formData.value.knowsProgramming, val => {
  if (!val) formData.value.programmingLanguagesString = ''
})

function close() {
  error.value = ''
  internalModel.value = false
}

async function save() {
  const hasErfahrung = formData.value.erfahrung.some(e => e && e.trim().length > 0)
  const hasInterest = formData.value.interests.some(i => i && i.trim().length > 0)

  if (!hasErfahrung || !hasInterest) {
    error.value = 'Bitte mindestens eine bevorzugte Abteilung und ein Interesse angeben.'
    return
  }

  const updatedNwk: NwkExperience = {
    erfahrung: formData.value.erfahrung.filter(Boolean),
    knowsProgramming: formData.value.knowsProgramming,
    programmingLanguages: formData.value.programmingLanguagesString
      .split(',')
      .map(lang => lang.trim())
      .filter(lang => lang.length > 0),
    interests: formData.value.interests.filter(Boolean)
  }

  try {
    const res = await fetch(`/api/meinKonto/experience/${props.nwkId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        interessen: updatedNwk.interests.join(', '),
        erfahrung: updatedNwk.erfahrung.join(', ')
      })
    })

    const text = await res.text() // 👈 Antworttext lesen
    console.log('Serverantwort:', res.status, text) // 👈 Debug

    if (!res.ok) throw new Error(`Fehler: ${res.status}`)
    emit('save', updatedNwk)
    close()
    alert('Erfahrungen & Interessen gespeichert!')
  } catch (err) {
    console.error('Fehler beim Speichern:', err)
    error.value = 'Fehler beim Speichern!'
  }
}
</script>
