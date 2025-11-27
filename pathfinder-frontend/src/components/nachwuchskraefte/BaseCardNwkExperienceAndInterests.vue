<template>
  <v-card class="pa-4 mb-6">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>Erfahrungen & Interessen</span>
      <BaseButtonEdit v-if="editable" @click="$emit('edit')" />
    </v-card-title>

    <v-divider />

    <v-card-text>
      <div class="mb-4">
        <h3>Wunschabteilungen</h3>
        <ul v-if="departments.length" class="pl-4">
          <li v-for="d in departments" :key="d.id">{{ d.name }}</li>
        </ul>
        <p v-else>Noch keine Abteilungen angegeben.</p>
      </div>

      <div class="mb-4">
        <h3>Interessen</h3>
        <ul v-if="interests.length" class="pl-4">
          <li v-for="t in interests" :key="t.id">{{ t.name }}</li>
        </ul>
        <p v-else>Noch keine Interessen angegeben.</p>
      </div>

      <div v-if="knowsProgramming">
        <strong>Programmieren:</strong> Ja
        <span v-if="programmingLanguages.length">({{ programmingLanguages.join(', ') }})</span>
      </div>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { computed, defineProps } from 'vue'
import BaseButtonEdit from '@/components/common/BaseButtonEdit.vue'

interface Abteilung { id: number; name: string }
interface Tag { id: number; name: string }

const props = defineProps<{
  nwkExperience: {
    wunschabteilungen: Abteilung[]
    interessen: Tag[]
    knowsProgramming: boolean
    programmingLanguages?: string[]
  }
  editable?: boolean
}>()

const departments = computed(() => props.nwkExperience.wunschabteilungen)
const interests = computed(() => props.nwkExperience.interessen)
const knowsProgramming = computed(() => props.nwkExperience.knowsProgramming)
const programmingLanguages = computed(() => props.nwkExperience.programmingLanguages ?? [])
</script>

<style scoped>
ul { margin: 0; padding-left: 1.2rem; }
li { list-style-type: disc; }
.mb-4 { margin-bottom: 1rem; }
</style>
