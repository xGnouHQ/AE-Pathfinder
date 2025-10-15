<template>
  <v-dialog v-model="internalModelValue" max-width="500px" persistent>
    <v-card>
      <v-card-title>Profil bearbeiten</v-card-title>
      <v-divider></v-divider>

      <v-card-text>
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
            label="Personalnummer"
            v-model="localNwk.personalnumber"
            :rules="[rules.required]"
            disabled
          />
          <v-text-field
            label="Nachname"
            v-model="localNwk.surename"
            :rules="[rules.required]"
          />
          <v-text-field
            label="Vorname"
            v-model="localNwk.firstname"
            :rules="[rules.required]"
          />
          <v-text-field
            label="E-Mail"
            v-model="localNwk.mail"
            :rules="[rules.required, rules.email]"
          />
          <v-text-field
            label="Jahrgang"
            v-model="localNwk.year"
            :rules="[rules.required, rules.year]"
          />
          <v-text-field
            label="Studienrichtung"
            v-model="localNwk.major"
            :rules="[rules.required]"
          />
        </v-form>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text @click="closeDialog">Abbrechen</v-btn>
        <v-btn color="primary" :disabled="!valid" @click="save">Speichern</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, reactive, toRefs } from 'vue';

interface Nwk {
  gender: string;
  personalnumber: string;
  surename: string;
  firstname: string;
  mail: string;
  year: string;
  major: string;
}

const props = defineProps<{
  modelValue: boolean,
  nwk: Nwk
}>();

const emit = defineEmits(['update:modelValue', 'save']);

const internalModelValue = ref(props.modelValue);

watch(() => props.modelValue, (val) => {
  internalModelValue.value = val;
});

watch(internalModelValue, (val) => {
  emit('update:modelValue', val);
});

// Lokale Kopie der Daten für Editierung
const localNwk = reactive({ ...props.nwk });

watch(() => props.nwk, (newVal) => {
  Object.assign(localNwk, newVal);
});

// Validation
const valid = ref(false);
const rules = {
  required: (v: string) => !!v || 'Pflichtfeld',
  email: (v: string) => /.+@.+\..+/.test(v) || 'Ungültige E-Mail',
  year: (v: string) => /^\d{4}$/.test(v) || 'Jahrgang muss 4-stellig sein',
};

function closeDialog() {
  internalModelValue.value = false;
}

function save() {
  if (!valid.value) return;
  emit('save', { ...localNwk });
  internalModelValue.value = false;
}
</script>
