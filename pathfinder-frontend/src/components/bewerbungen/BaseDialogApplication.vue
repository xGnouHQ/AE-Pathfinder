<template>
  <v-dialog v-model="dialog" max-width="600px" persistent>
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

        <!-- Datei hochladen -->
        <v-file-input
          v-model="selectedFile"
          label="Datei auswählen"
          accept=".pdf,.doc,.docx"
          show-size
          outlined
          dense
        />

        <!-- Dokumenttyp Dropdown -->
        <v-select
          v-if="selectedFile"
          v-model="selectedDocType"
          :items="docTypes"
          label="Dokumenttyp auswählen"
          dense
          outlined
        />

        <!-- Hochgeladene Dateien für Bewerbung auswählen -->
        <v-select
          v-model="selectedFilesForApplication"
          :items="uploadedFiles.map(f => ({ id: f.id, name: f.dateipfad.split('/').pop() || f.typ }))"
          item-title="name"
          item-value="id"
          multiple
          chips
          dense
          class="mt-4"
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
        <v-btn text @click="closeDialog">Abbrechen</v-btn>
        <v-btn color="primary" :disabled="!consent" @click="submitApplication">
          Bewerben
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch } from "vue";

interface FileItem {
  id: number;
  dateipfad: string;
  typ: string;
  hochgeladenAm: string;
}

interface Job {
  id?: number;
  title: string;
}

// Props
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

// Dialog und Form-State
const dialog = ref(props.modelValue);
const consent = ref(false);
const selectedFile = ref<File | null>(null);
const selectedDocType = ref<string | null>(null);
const selectedFilesForApplication = ref<number[]>([]);
const hrNote = ref("");

// Dokumenttypen
const docTypes = ["LEBENSLAUF", "MOTIVATIONSSCHREIBEN", "ZEUGNIS", "SONSTIGES"];

// v-model Synchronisation
watch(() => props.modelValue, val => dialog.value = val);
watch(dialog, val => emit("update:modelValue", val));

function closeDialog() {
  consent.value = false;
  selectedFile.value = null;
  selectedDocType.value = null;
  selectedFilesForApplication.value = [];
  hrNote.value = "";
  dialog.value = false;
}

// Upload Datei und senden Bewerbung
async function submitApplication() {
  try {
    let uploadedFileId: number | null = null;

    // Datei hochladen, wenn eine ausgewählt
    if (selectedFile.value) {
      if (!selectedDocType.value) {
        alert("Bitte einen Dokumenttyp auswählen!");
        return;
      }

      const formData = new FormData();
      formData.append("file", selectedFile.value);
      formData.append("nwkId", props.nwkId.toString());
      formData.append("typ", selectedDocType.value);

      const uploadRes = await fetch("/api/meinKonto/documents", {
        method: "POST",
        body: formData
      });

      if (!uploadRes.ok) {
        throw new Error("Datei-Upload fehlgeschlagen");
      }

      const savedFile = await uploadRes.json();
      uploadedFileId = savedFile.id;

      // Direkt in Auswahl für Bewerbung hinzufügen
      selectedFilesForApplication.value.push(uploadedFileId);
    }

    // Payload für Bewerbung
    const payload = {
      stelleId: props.job?.id,
      nachwuchskraftId: props.nwkId,
      fileIds: selectedFilesForApplication.value, // IDs der Dateien
      hrNote: hrNote.value || null
    };

    const res = await fetch("/api/bewerbungen", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    if (!res.ok) throw new Error("Fehler beim Erstellen der Bewerbung");

    alert("Bewerbung erfolgreich erstellt!");

    closeDialog();
    emit("submitted");
  } catch (err) {
    console.error(err);
    alert("Fehler beim Bewerben!");
  }
}
</script>

<style scoped>
.mt-4 { margin-top: 16px; }
</style>
