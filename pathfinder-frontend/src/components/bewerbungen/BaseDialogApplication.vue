<template>
  <v-dialog v-model="dialog" max-width="600">
    <v-card>
      <v-card-title class="text-h6 font-weight-bold">
        Bewerbung für: {{ job?.title || "Unbekannte Stelle" }}
      </v-card-title>

      <v-divider></v-divider>

      <v-card-text>
        <!-- Einwilligung -->
        <v-checkbox
          v-model="consent"
          label="Ich willige ein, dass meine persönlichen Daten für diese Bewerbung weitergegeben werden."
        />

        <!-- Dokumente immer sichtbar -->
        <div class="mt-4">
          <p class="font-weight-medium mb-2">Dokumente auswählen (optional)</p>
          <v-select
            v-model="selectedFiles"
            :items="uploadedFiles"
            item-title="name"
            item-value="id"
            multiple
            chips
            dense
            :disabled="uploadedFiles.length === 0"
            placeholder="Keine Dokumente vorhanden"
          />
        </div>

        <!-- Optional: HR-Notiz -->
        <v-textarea
          v-model="hrNote"
          label="Notiz für HR (optional)"
          placeholder="Zusätzliche Informationen oder Wünsche"
          rows="3"
          auto-grow
          class="mt-4"
        />
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions class="justify-end">
        <BaseButtonCancel @click="closeDialog" />
        <BaseButtonVerify :disabled="!consent" @click="submitApplication" />
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch } from "vue";
import BaseButtonCancel from "@/components/common/BaseButtonCancel.vue";
import BaseButtonVerify from "@/components/common/BaseButtonVerify.vue";

interface FileItem { id: number; name: string; path: string; typ: string; hochgeladenAm: string; }
interface Job { id?: number; title: string; }

const props = defineProps<{
  modelValue: boolean;
  job: Job | null;
  uploadedFiles: FileItem[];
}>();

const emit = defineEmits<{
  (e: "update:modelValue", value: boolean): void;
  (e: "submit", data: { job: Job; consent: boolean; selectedFiles: number[]; hrNote: string }): void;
}>();

const dialog = ref(props.modelValue);
const consent = ref(false);
const selectedFiles = ref<number[]>([]);
const hrNote = ref("");

// Watch für v-model Synchronisation
watch(() => props.modelValue, val => (dialog.value = val));
watch(dialog, val => emit("update:modelValue", val));

// Dialog schließen
function closeDialog() {
  dialog.value = false;
}

// Submit minimalistisch
function submitApplication() {
  if (!props.job) return;

  emit("submit", {
    job: props.job,
    consent: consent.value,
    selectedFiles: selectedFiles.value,
    hrNote: hrNote.value,
  });

  // Reset
  consent.value = false;
  selectedFiles.value = [];
  hrNote.value = "";
  dialog.value = false;
}
</script>

<style scoped>
.mt-4 {
  margin-top: 16px;
}
.font-weight-medium {
  font-weight: 500;
}
</style>
