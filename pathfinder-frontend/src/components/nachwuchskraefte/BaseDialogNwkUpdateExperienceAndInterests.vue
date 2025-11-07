<template>
  <v-dialog v-model="internalModel" max-width="600px">
    <v-card>
      <v-card-title>Bevorzugte Abteilungen & Interessen bearbeiten</v-card-title>
      <v-card-text>
        <v-form ref="form">
          <!-- Bevorzugte Abteilungen -->
          <div class="mb-4">
            <h3>Bevorzugte Abteilungen <span class="text-red">*</span></h3>
            <v-text-field
              v-for="(dept, index) in formData.departments"
              :key="index"
              v-model="formData.departments[index]"
              :label="`Abteilung ${index + 1}`"
              class="mb-2"
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
          />

          <!-- Interessen -->
          <div class="mt-4">
            <h3>Interessen <span class="text-red">*</span></h3>
            <v-text-field
              v-for="(interest, index) in formData.interests"
              :key="index"
              v-model="formData.interests[index]"
              :label="`Interesse ${index + 1}`"
              class="mb-2"
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
  departments: string[]  // früher "experiences"
  knowsProgramming: boolean
  programmingLanguages: string[]
  interests: string[]
}

const props = defineProps<{
  modelValue: boolean
  nwk: NwkExperience
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void
  (e: 'save', updatedNwk: NwkExperience): void
}>()

const internalModel = ref(props.modelValue)
const error = ref('')

watch(() => props.modelValue, (val) => (internalModel.value = val))
watch(internalModel, (val) => emit('update:modelValue', val))

// 🟢 Initiale Felder (3 Abteilungen, 5 Interessen)
const formData = ref({
  departments: ['', '', ''],
  knowsProgramming: false,
  programmingLanguagesString: '',
  interests: ['', '', '', '', ''],
})

// props → formData synchronisieren
watch(
  () => props.nwk,
  (newVal) => {
    formData.value.departments = [...newVal.departments]
    formData.value.knowsProgramming = newVal.knowsProgramming
    formData.value.programmingLanguagesString = newVal.programmingLanguages.join(', ')
    formData.value.interests = [...newVal.interests]
  },
  { immediate: true }
)

watch(
  () => formData.value.knowsProgramming,
  (val) => {
    if (!val) formData.value.programmingLanguagesString = ''
  }
)

function close() {
  error.value = ''
  internalModel.value = false
}

function save() {
  const hasDepartment = formData.value.departments.some((d) => d.trim().length > 0)
  const hasInterest = formData.value.interests.some((i) => i.trim().length > 0)

  if (!hasDepartment || !hasInterest) {
    error.value = 'Bitte mindestens eine bevorzugte Abteilung und ein Interesse angeben.'
    return
  }

  const updatedNwk: NwkExperience = {
    departments: [...formData.value.departments],
    knowsProgramming: formData.value.knowsProgramming,
    programmingLanguages: formData.value.programmingLanguagesString
      .split(',')
      .map((lang) => lang.trim())
      .filter((lang) => lang.length > 0),
    interests: [...formData.value.interests],
  }

  emit('save', updatedNwk)
  close()
}
</script>
