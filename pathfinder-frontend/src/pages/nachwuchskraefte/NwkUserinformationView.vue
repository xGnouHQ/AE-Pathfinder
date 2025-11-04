<template>
  <v-container>
    <h1 class="mb-6">Mein Konto</h1>

    <!-- Profil -->
    <BaseCardNwkPersonal
      :nwk="nwk"
      editable
      class="mt-4"
      @edit="dialogProfileOpen = true"
    />

    <!-- Erfahrungen & Interessen -->
        <NwkExperienceAndInterestsCard
          :nwk="nwkExperience"
          editable
          class="mt-4"
          @edit="dialogExperienceOpen = true"
        />

        <!-- Dialoge -->
        <NwkUploadDocumentsDialog
          v-model="dialogOpen"
          :savedFiles="savedFiles"
          @save="handleSave"
        />

        <NwkUpdateProfileDialog
          v-model="dialogProfileOpen"
          :nwk="nwk"
          @save="handleProfileSave"
        />

        <NwkUpdateExpierenceAndIntrestsDialog
          v-model="dialogExperienceOpen"
          :nwk="nwkExperience"
          @save="handleExperienceSave"
        />

    <!-- Dokumente -->
    <NwkDocumentsCard
      :savedFiles="savedFiles"
      editable
      @upload="dialogOpen = true"
    />


  </v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import NwkUploadDocumentsDialog from "@/components/nachwuchskraefte/NwkUploadDocumentsDialog.vue"
import BaseCardNwkPersonal from "@/components/nachwuchskraefte/BaseCardNwkPersonal.vue";
import NwkDocumentsCard from "@/components/nachwuchskraefte/NwkDocumentsCard.vue";
import NwkExperienceAndInterestsCard from "@/components/nachwuchskraefte/NwkExperienceAndInterestsCard.vue";
import NwkUpdateExpierenceAndIntrestsDialog from "@/components/nachwuchskraefte/NwkUpdateExpierenceAndIntrestsDialog.vue";

// Beispiel-Daten Profil
const nwk = ref({
  gender: 'männlich',
  personalnumber: '123456',
  surename: 'Mustermann',
  firstname: 'Max',
  mail: 'max.mustermann@example.com',
  year: '23/26',
  major: 'Informatik',
});

// Beispiel-Dateien
interface StoredFile {
  id: string;
  name: string;
  url?: string;
  fileObject?: File;
}

const savedFiles = ref<StoredFile[]>([
  { id: '1', name: 'Lebenslauf_MaxMueller.pdf', url: '/uploads/Lebenslauf_MaxMueller.pdf' },
  { id: '2', name: 'Zeugnis_MaxMueller.pdf', url: '/uploads/Zeugnis_MaxMueller.pdf' },
]);

// Dialog-States
const dialogOpen = ref(false);
const dialogProfileOpen = ref(false);
const dialogExperienceOpen = ref(false);

// Methoden Profil & Dokumente
function handleSave(files: StoredFile[]) {
  savedFiles.value = files;
  console.log('Aktualisierte Dateien:', savedFiles.value);
}

function handleProfileSave(updatedNwk: typeof nwk.value) {
  nwk.value = { ...updatedNwk };
  alert('Profil wurde aktualisiert.');
}

// Beispiel-Daten Erfahrungen & Interessen
interface NwkExperience {
  experiences: string[];
  knowsProgramming: boolean;
  programmingLanguages: string[];
  interests: string[];
}

const nwkExperience = ref<NwkExperience>({
  experiences: ['', '', '', '', ''],
  knowsProgramming: false,
  programmingLanguages: [],
  interests: ['', '', '', '', ''],
});

// Methode Erfahrung & Interessen speichern
function handleExperienceSave(updated: NwkExperience) {
  nwkExperience.value = { ...updated };
  alert('Erfahrungen & Interessen gespeichert!');
}
</script>
