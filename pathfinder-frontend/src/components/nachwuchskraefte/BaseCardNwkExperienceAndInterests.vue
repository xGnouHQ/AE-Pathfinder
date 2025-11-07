<template>
  <v-card class="pa-4 mb-6">
    <!-- Header mit Titel und Bearbeiten-Button -->
    <v-card-title class="d-flex justify-space-between align-center">
      <span>Erfahrungen & Interessen</span>
      <BaseButtonEdit v-if="editable" @click="$emit('edit')" />
    </v-card-title>
    <v-divider></v-divider>
    <v-card-text>
      <!-- Abteilungen -->
      <div class="mb-4">
        <h3>Bevorzugte Abteilungen</h3>
        <ul v-if="filledDepartments.length > 0" class="pl-4">
          <li v-for="(dept, index) in filledDepartments" :key="index">{{ dept }}</li>
        </ul>
        <p v-else>Noch keine Abteilungen angegeben.</p>
      </div>

      <!-- Programmierkenntnisse -->
      <div class="mb-4">
        <h3>Programmierkenntnisse</h3>
        <p v-if="nwk.knowsProgramming && nwk.programmingLanguages.length > 0">
          Programmiersprachen: {{ nwk.programmingLanguages.slice(0,5).join(', ') }}
        </p>
        <p v-else>Keine Programmierkenntnisse angegeben.</p>
      </div>

      <!-- Interessen -->
      <div>
        <h3>Interessen</h3>
        <ul v-if="filledInterests.length > 0" class="pl-4">
          <li v-for="(interest, index) in filledInterests" :key="index">{{ interest }}</li>
        </ul>
        <p v-else>Noch keine Interessen angegeben.</p>
      </div>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { computed, defineProps } from 'vue'
import BaseButtonEdit from '@/components/common/BaseButtonEdit.vue'

interface NwkExperience {
  departments: string[]
  knowsProgramming: boolean
  programmingLanguages: string[]
  interests: string[]
}

const props = defineProps<{
  nwk: NwkExperience
  editable?: boolean
}>()

// Abteilungen filtern und max. 5 anzeigen
const filledDepartments = computed(() =>
  props.nwk.departments.filter(d => d && d.trim() !== '').slice(0, 5)
)

// Interessen filtern und max. 5 anzeigen
const filledInterests = computed(() =>
  props.nwk.interests.filter(i => i && i.trim() !== '').slice(0, 5)
)
</script>

<style scoped>
ul {
  margin: 0;
  padding-left: 1.2rem;
}
li {
  list-style-type: disc;
}
</style>
