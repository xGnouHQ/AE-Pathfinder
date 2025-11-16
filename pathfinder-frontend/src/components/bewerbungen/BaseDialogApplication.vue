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

        <!-- Dokumente auswählen: nur Dateiname oder Typ anzeigen -->
        <v-select
          v-model="selectedFiles"
          :items="uploadedFiles.map(f => ({ id: f.id, name: f.dateipfad.split('/').pop() || f.typ }))"
          item-title="name"
          item-value="id"
          multiple
          chips
          dense
          :disabled="uploadedFiles.length === 0"
          placeholder="Keine Dokumente vorhanden"
        />

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

interface FileItem { id: number; dateipfad: string; typ: string; hochgeladenAm: string; }
interface Job { id?: number; title: string; }

const props = defineProps<{
  modelValue: boolean;
  job: Job | null;
  uploadedFiles: FileItem[];
  nwkId: number;
}>();

const emit = defineEmits<{
  (e: "update:modelValue", value: boolean): void;
  (e: "submitted"): void;
}>();

const dialog = ref(props.modelValue);
const consent = ref(false);
const selectedFiles = ref<number[]>([]);
const hrNote = ref("");

// Watch für v-model Synchronisation
watch(() => props.modelValue, val => (dialog.value = val));
watch(dialog, val => emit("update:modelValue", val));

function closeDialog() { dialog.value = false; }

async function submitApplication() {
  if (!props.job) return;

  try {
    const fileNames = selectedFiles.value.length > 0
      ? selectedFiles.value
          .map(id => props.uploadedFiles.find(f => f.id === id)?.dateipfad.split('/').pop() || f.typ)
          .filter(name => name != null)
      : null;

    const payload = {
      stelleId: props.job.id,
      nachwuchskraftId: props.nwkId,
      fileNames: fileNames,      // nur Dateinamen übermitteln
      hrNote: hrNote.value || null
    };

    const res = await fetch("/api/bewerbungen", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    if (!res.ok) throw new Error("Fehler beim Erstellen der Bewerbung");

    alert("Bewerbung erfolgreich erstellt!");

    consent.value = false;
    selectedFiles.value = [];
    hrNote.value = "";
    dialog.value = false;

    emit("submitted");
  } catch (err) {
    console.error(err);
    alert("Fehler beim Bewerben!");
  }
}
</script>

<style scoped>
.mt-4 { margin-top: 16px; }
.font-weight-medium { font-weight: 500; }
</style>
