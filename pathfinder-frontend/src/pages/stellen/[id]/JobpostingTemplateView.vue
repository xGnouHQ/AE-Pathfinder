<template>
  <v-container>
    <h1 class="mb-6">Stellenausschreibung</h1>

    <v-container class="box">
      <v-row>
        <!-- JobCard anzeigen -->
        <v-col cols="12">
          <JobCard
            v-if="stelle"
            :stelle="stelle"
            :nwk="nwk"
            @merken="merkeStelle"
          />
        </v-col>

        <!-- Bewerbungsbutton -->
        <v-col cols="12" class="mt-3">
          <BaseButtonApplication
            v-if="stelle && stelle.status === 'OFFEN'"
            @click="openDialog(stelle)"
          />
        </v-col>
      </v-row>
    </v-container>

    <!-- Bewerbungsdialog -->
    <BaseDialogApplication
      v-model="dialogOpen"
      :job="selectedStelle"
      :uploaded-files="nwkDocuments"
      @submit="handleSubmit"
    />
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";

import JobCard from "@/components/stellen/JobCard.vue";
import BaseButtonApplication from "@/components/common/BaseButtonApplication.vue";
import BaseDialogApplication from "@/components/bewerbungen/BaseDialogApplication.vue";

const API_STELLE = "http://localhost:8080/api/stellen";
const API_BEWERBUNG = "http://localhost:8080/api/bewerbungen";
const API_MERKLISTE = "http://localhost:8080/api/meineListe";
const API_NWK_DOCS = "http://localhost:8080/api/meinKonto/documents";

const route = useRoute();

const stelle = ref<any>(null);
const nwk = ref<any>({ id: 1 }); // Beispiel Nachwuchskraft
const dialogOpen = ref(false);
const selectedStelle = ref<any>(null);
const nwkDocuments = ref<any[]>([]);

// --- Stelle laden + Merkliste prüfen
const ladeStelle = async (id: string | string[]) => {
  try {
    const res = await axios.get(`${API_STELLE}/${id}`);
    const merklisteRes = await axios.get(`${API_MERKLISTE}/nachwuchskraft/${nwk.value.id}`);
    const gemerktIds = merklisteRes.data.map((e: any) => e.stelle.id);

    // Backend-Feld 'titel' → Frontend-Feld 'title'
    stelle.value = {
      ...res.data,
      title: res.data.titel,
      gemerkt: gemerktIds.includes(res.data.id)
    };
  } catch (err) {
    console.error("Fehler beim Laden der Stelle:", err);
  }
};

// --- Nachwuchskraft-Dokumente laden
const ladeNachwuchskraft = async () => {
  try {
    const resDocs = await axios.get(`${API_NWK_DOCS}/${nwk.value.id}`);
    nwkDocuments.value = resDocs.data.map((doc: any) => ({
      id: doc.id,
      name: doc.dateipfad.split("/").pop(),
      path: doc.dateipfad,
      typ: doc.typ,
      hochgeladenAm: doc.hochgeladenAm,
    }));
  } catch (err) {
    console.error("Fehler beim Laden der Dokumente:", err);
  }
};

// --- Route beobachten
watch(
  () => route.params.id,
  (id) => {
    if (id) ladeStelle(id);
  },
  { immediate: true }
);

// --- Dialog öffnen
function openDialog(stelleData: any) {
  selectedStelle.value = stelleData;
  dialogOpen.value = true;
}

// --- Bewerbung absenden
async function handleSubmit(data: { job: any; consent: boolean; selectedFiles?: number[]; hrNote?: string }) {
  if (!data.job || !nwk.value) return;

  try {
    await axios.post(
      API_BEWERBUNG,
      {
        stelleId: data.job.id,
        nachwuchskraftId: nwk.value.id,
        hrNote: data.hrNote || "",
        fileIds: data.selectedFiles || []
      },
      { headers: { "Content-Type": "application/json" } }
    );

    alert(`✅ Bewerbung für "${data.job.title}" erfolgreich gesendet!`);
    dialogOpen.value = false;
  } catch (err: any) {
    console.error("Fehler beim Absenden:", err);
    alert(err.response?.data?.message || "Fehler beim Absenden der Bewerbung");
  }
}

// --- Stelle merken
async function merkeStelle(id: number) {
  try {
    await axios.post(`${API_MERKLISTE}/${id}/merken/nachwuchskraft/${nwk.value.id}`);
    alert("Stelle erfolgreich gemerkt!");
    if (stelle.value && stelle.value.id === id) {
      stelle.value.gemerkt = true;
    }
  } catch (err: any) {
    console.error(err);
    alert(err.response?.data || "Fehler beim Merken der Stelle");
  }
}

// --- Mounted
onMounted(async () => {
  await ladeNachwuchskraft();
  if (route.params.id) await ladeStelle(route.params.id);
});
</script>


<style scoped>
.box {
  margin-top: 20px;
}
</style>
