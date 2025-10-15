<template>
  <v-container>
    <h1 class="mb-6">Mein Konto</h1>

    <!-- Profilinformation -->
    <v-card class="mt-4">
      <v-card-title class="d-flex justify-space-between align-center">
        <span>Profilinformationen</span>
        <v-btn
          color="primary"
          variant="outlined"
          @click="dialogProfileOpen = true"
          size="small"
        >
          Bearbeiten
        </v-btn>
      </v-card-title>
      <v-divider></v-divider>

      <v-card-text>
        <v-row>
          <v-col cols="12">Personalnummer: {{ nwk.personalnumber }}</v-col>
        </v-row>
        <v-row>
          <v-col cols="12" md="6">Nachname: {{ nwk.surename }}</v-col>
          <v-col cols="12" md="6">Vorname: {{ nwk.firstname }}</v-col>
        </v-row>
        <v-row>
          <v-col cols="12">E-Mail: {{ nwk.mail }}</v-col>
        </v-row>
        <v-row>
          <v-col cols="12" md="6">Jahrgang: {{ nwk.year }}</v-col>
          <v-col cols="12" md="6">Studienrichtung: {{ nwk.major }}</v-col>
        </v-row>
      </v-card-text>
    </v-card>

    <!-- Mehrfach-Datei Upload -->
    <v-card class="mt-4">
      <v-card-title class="d-flex justify-space-between align-center">
        <span>Dokumente</span>
        <v-btn color="secondary" @click="dialogOpen = true" small>
          Dokumente hochladen
        </v-btn>
      </v-card-title>
      <v-divider></v-divider>

      <v-card-text>
        <div v-if="savedFiles.length === 0" class="text--disabled">
          Keine Dokumente hochgeladen.
        </div>

        <v-list v-else dense>
          <v-list-item v-for="file in savedFiles" :key="file.id">
            <v-list-item-icon>
              <v-icon>mdi-file-document-outline</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>
                <a
                  v-if="file.url"
                  :href="file.url"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  {{ file.name }}
                </a>
                <span v-else>{{ file.name }}</span>
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-card-text>

      <NwkUploadDocumentsDialog
        v-model="dialogOpen"
        :savedFiles="savedFiles"
        @save="handleSave"
      />
    </v-card>

    <!-- Profil bearbeiten Dialog -->
    <NwkUpdateDialog
      v-model="dialogProfileOpen"
      :nwk="nwk"
      @save="handleProfileSave"
    />
  </v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import NwkUploadDocumentsDialog from "@/components/nachwuchskraefte/NwkUploadDocumentsDialog.vue";
import NwkUpdateDialog from "@/components/nachwuchskraefte/NwkUpdateDialog.vue";

const nwk = ref({
  gender: 'männlich',
  personalnumber: '123456',
  surename: 'Mustermann',
  firstname: 'Max',
  mail: 'max.mustermann@example.com',
  year: '23/26',
  major: 'Informatik',
});

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

const dialogOpen = ref(false);
const dialogProfileOpen = ref(false);

function handleSave(files: StoredFile[]) {
  savedFiles.value = files;
  console.log('Aktualisierte Dateien:', savedFiles.value);
  // Hier: Upload an Server, API-Call, etc.
}

function handleProfileSave(updatedNwk: typeof nwk.value) {
  nwk.value = { ...updatedNwk };
  alert('Profil wurde aktualisiert.');
}

function editProfile() {
  dialogProfileOpen.value = true;
}
</script>
