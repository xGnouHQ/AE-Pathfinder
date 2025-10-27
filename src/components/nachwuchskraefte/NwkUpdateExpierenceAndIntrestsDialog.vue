<template>
  <v-dialog v-model="internalModel" max-width="600px">
    <v-card>
      <v-card-title>Erfahrungen & Interessen bearbeiten</v-card-title>
      <v-card-text>
        <v-form ref="form">
          <!-- Praktika -->
          <div class="mb-4">
            <h3>Praktika</h3>
            <v-text-field
              v-for="(exp, index) in formData.experiences"
              :key="index"
              v-model="formData.experiences[index]"
              :label="`Praktikum ${index + 1}`"
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
            <h3>Interessen</h3>
            <v-text-field
              v-for="(interest, index) in formData.interests"
              :key="index"
              v-model="formData.interests[index]"
              :label="`Interesse ${index + 1}`"
              class="mb-2"
            />
          </div>
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
import { ref, watch } from 'vue';

interface NwkExperience {
  experiences: string[];
  knowsProgramming: boolean;
  programmingLanguages: string[];
  interests: string[];
}

const props = defineProps<{
  modelValue: boolean;
  nwk: NwkExperience;
}>();

const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void;
  (e: 'save', updatedNwk: NwkExperience): void;
}>();

// **Lokale Kopie von modelValue für v-model**
const internalModel = ref(props.modelValue);

// Props synchronisieren
watch(() => props.modelValue, (val) => internalModel.value = val);
watch(internalModel, (val) => emit('update:modelValue', val));

// Form-Daten
const formData = ref({
  experiences: ['', '', '', '', ''],
  knowsProgramming: false,
  programmingLanguagesString: '',
  interests: ['', '', '', '', ''],
});

// Props initialisieren
watch(
  () => props.nwk,
  (newVal) => {
    formData.value.experiences = [...newVal.experiences];
    formData.value.knowsProgramming = newVal.knowsProgramming;
    formData.value.programmingLanguagesString = newVal.programmingLanguages.join(', ');
    formData.value.interests = [...newVal.interests];
  },
  { immediate: true }
);

// Wenn Programmieren deaktiviert wird, Felder leeren
watch(
  () => formData.value.knowsProgramming,
  (val) => {
    if (!val) formData.value.programmingLanguagesString = '';
  }
);

// Methoden
function close() {
  internalModel.value = false;
}

function save() {
  const updatedNwk: NwkExperience = {
    experiences: [...formData.value.experiences],
    knowsProgramming: formData.value.knowsProgramming,
    programmingLanguages: formData.value.programmingLanguagesString
      .split(',')
      .map(lang => lang.trim())
      .filter(lang => lang.length > 0),
    interests: [...formData.value.interests],
  };
  emit('save', updatedNwk);
  close();
}
</script>
