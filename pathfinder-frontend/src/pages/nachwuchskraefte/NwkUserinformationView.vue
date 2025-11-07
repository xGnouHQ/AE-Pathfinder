<template>
  <v-container>
    <h1 class="mb-6">Mein Konto</h1>

    <!-- Persönliche Daten -->
    <BaseCardNwkPersonal
      :nwk="nwk"
      editable
      class="mt-4"
      @edit="dialogProfileOpen = true"
    />

    <!-- Bevorzugte Abteilungen & Interessen -->
    <BaseCardNwkExperienceAndInterests
      :nwk="nwkExperience"
      editable
      class="mt-4"
      @edit="dialogExperienceOpen = true"
    />

    <!-- Dialoge -->
    <BaseDialogNwkUploadDocuments
      v-model="dialogOpen"
      :savedFiles="savedFiles"
      @save="handleSave"
    />

    <BaseDialogNwkUpdateExperienceAndInterests
      v-model="dialogExperienceOpen"
      :nwk="nwkExperience"
      @save="handleExperienceSave"
    />

    <!-- Dokumente -->
    <BaseCardNwkDocuments
      :savedFiles="savedFiles"
      editable
      @upload="dialogOpen = true"
    />
  </v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// ----------------------------
// Komponenten
// ----------------------------
import BaseDialogNwkUploadDocuments from '@/components/nachwuchskraefte/BaseDialogNwkUploadDocuments.vue'
import BaseCardNwkPersonal from '@/components/nachwuchskraefte/BaseCardNwkPersonal.vue'
import BaseCardNwkDocuments from '@/components/nachwuchskraefte/BaseCardNwkDocuments.vue'
import BaseCardNwkExperienceAndInterests from '@/components/nachwuchskraefte/BaseCardNwkExperienceAndInterests.vue'
import BaseDialogNwkUpdateExperienceAndInterests from '@/components/nachwuchskraefte/BaseDialogNwkUpdateExperienceAndInterests.vue'

// ----------------------------
// Persönliche Daten
// ----------------------------
interface Nachwuchs {
  gender: string
  personalnumber: string
  surename: string
  firstname: string
  mail: string
  year: string
  major: string
  departments: string
}

const nwk = ref<Nachwuchs>({
  gender: 'männlich',
  personalnumber: '20231057',
  surename: 'Mustermann',
  firstname: 'Max',
  mail: 'max.mustermann@muenchen.de',
  year: '2023/2026',
  major: 'Verwaltungsinformatik',
  departments: 'IT@M, Kommunalreferat GPAM, Kreisverwaltungsreferat'
})

// ----------------------------
// Dokumente
// ----------------------------
interface StoredFile {
  id: string
  name: string
  url?: string
  fileObject?: File
}

const savedFiles = ref<StoredFile[]>([
  { id: '1', name: 'Lebenslauf_MaxMustermann.pdf', url: '/uploads/Lebenslauf_MaxMustermann.pdf' },
  { id: '2', name: 'Motivationsschreiben_MaxMustermann.pdf', url: '/uploads/Motivationsschreiben_MaxMustermann.pdf' },
  { id: '3', name: 'Zeugnis_FH_Muenchen_2024.pdf', url: '/uploads/Zeugnis_FH_Muenchen_2024.pdf' }
])

// ----------------------------
// Dialog-States
// ----------------------------
const dialogOpen = ref(false)
const dialogProfileOpen = ref(false)
const dialogExperienceOpen = ref(false)

// ----------------------------
// Upload & Dokumente speichern
// ----------------------------
function handleSave(files: StoredFile[]) {
  savedFiles.value = files
  console.log('Aktualisierte Dateien:', savedFiles.value)
}

// ----------------------------
// Bevorzugte Abteilungen & Interessen
// ----------------------------
interface NwkExperience {
  departments: string[]
  knowsProgramming: boolean
  programmingLanguages: string[]
  interests: string[]
}

const nwkExperience = ref<NwkExperience>({
  departments: [
    'IT@M - IT-Dienstleistungen',
    'Kommunalreferat - Stadtplanung',
    'Kreisverwaltungsreferat - Bürgerdienste'
  ],
  knowsProgramming: true,
  programmingLanguages: ['Java', 'Python', 'JavaScript'],
  interests: ['Digitale Verwaltung', 'Smart City Projekte', 'Nachhaltige IT-Lösungen']
})

function handleExperienceSave(updated: NwkExperience) {
  nwkExperience.value = { ...updated }
  alert('Bevorzugte Abteilungen & Interessen gespeichert!')
}
</script>
