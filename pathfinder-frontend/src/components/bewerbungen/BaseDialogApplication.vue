<template>
  <v-dialog v-model="dialog" max-width="600">
    <v-card>
      <!-- Titel -->
      <v-card-title class="text-h6 font-weight-bold">
        Bewerbung für: {{ job?.title || "Unbekannte Stelle" }}
      </v-card-title>

      <v-divider></v-divider>

      <!-- Inhalt -->
      <v-card-text>
        <!-- Checkbox -->
        <v-checkbox
          v-model="consent"
          label="Ich willige ein, dass meine persönlichen Daten für diese Bewerbung weitergegeben werden."
        ></v-checkbox>

        <!-- Dokumente auswählen -->
        <v-select
          v-model="selectedFiles"
          :items="uploadedFiles"
          item-title="name"
          item-value="id"
          label="Dokumente auswählen, die mitgesendet werden sollen"
          multiple
          chips
        ></v-select>

        <!-- Notizfeld -->
        <v-textarea
          v-model="hrNote"
          label="Notiz für HR"
          placeholder="Optional: Zusätzliche Informationen oder Wünsche"
          rows="3"
          auto-grow
        ></v-textarea>
      </v-card-text>

      <v-divider></v-divider>

      <!-- Buttons -->
      <v-card-actions class="justify-end">
        <BaseButtonCancel @click="closeDialog" />
        <BaseButtonVerify
          :disabled="!consent"
          @click="submitApplication"
        />
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch } from "vue";
import BaseButtonCancel from "@/components/common/BaseButtonCancel.vue";
import BaseButtonVerify from "@/components/common/BaseButtonVerify.vue";

interface FileItem {
  id: string;
  name: string;
}

interface Job {
  title: string;
  date: string;
  type: string;
  contractType: string;
  payGrade: string;
  start: string;
  area: string;
  expectations: string;
  requirements: string;
}

const props = defineProps<{
  modelValue: boolean;
  job: Job | null;
  uploadedFiles: FileItem[];
}>();

const emit = defineEmits<{
  (e: "update:modelValue", value: boolean): void;
  (e: "submit", data: {
    job: Job;
    consent: boolean;
    selectedFiles: string[];
    hrNote: string;
  }): void;
}>();

const dialog = ref(props.modelValue);
const consent = ref(false);
const selectedFiles = ref<string[]>([]);
const hrNote = ref("");

// v-model synchronisieren
watch(() => props.modelValue, (val) => (dialog.value = val));
watch(dialog, (val) => emit("update:modelValue", val));

function closeDialog() {
  dialog.value = false;
}

function submitApplication() {
  if (!props.job) return;

  emit("submit", {
    job: props.job,
    consent: consent.value,
    selectedFiles: selectedFiles.value,
    hrNote: hrNote.value,
  });

  // reset + schließen
  consent.value = false;
  selectedFiles.value = [];
  hrNote.value = "";
  dialog.value = false;
}
</script>

<style scoped>
.v-card-title {
  font-weight: 600;
}
</style>
