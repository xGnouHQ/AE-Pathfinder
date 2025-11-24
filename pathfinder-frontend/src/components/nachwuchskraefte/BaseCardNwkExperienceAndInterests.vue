<template>
  <v-card class="pa-4 mb-6">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>Erfahrungen & Interessen</span>
      <BaseButtonEdit v-if="editable" @click="$emit('edit')" />
    </v-card-title>

    <v-divider />

    <v-card-text>
      <!-- Wunschabteilungen -->
      <div class="mb-4">
        <h3>Bevorzugte Abteilungen</h3>
        <ul v-if="departments.length > 0" class="pl-4">
          <li v-for="dept in departments" :key="dept.id">
            {{ dept.name }}
          </li>
        </ul>
        <p v-else>
          Noch keine Abteilungen angegeben.
        </p>
      </div>

      <!-- Interessen -->
      <div class="mb-4">
        <h3>Interessen</h3>
        <ul v-if="interests.length > 0" class="pl-4">
          <li v-for="tag in interests" :key="tag.id">
            {{ tag.name }}
          </li>
        </ul>
        <p v-else>
          Noch keine Interessen angegeben.
        </p>
      </div>

      <!-- Programmieren Info nur anzeigen, wenn gesetzt -->
      <div v-if="knowsProgramming">
        <strong>Programmieren:</strong> Ja
      </div>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { computed, defineProps } from 'vue'
import BaseButtonEdit from '@/components/common/BaseButtonEdit.vue'

interface TagDTO { id: number; name: string }
interface AbteilungDTO { id: number; name: string }

const props = defineProps<{
  nwkExperience: {
    interessen: TagDTO[]
    wunschabteilungen: AbteilungDTO[]
    knowsProgramming?: boolean
  }
  editable?: boolean
}>()

// Computed für sauberen Zugriff
const departments = computed(() => props.nwkExperience?.wunschabteilungen ?? [])
const interests = computed(() => props.nwkExperience?.interessen ?? [])
const knowsProgramming = computed(() => props.nwkExperience?.knowsProgramming ?? false)
</script>

<style scoped>
ul { margin: 0; padding-left: 1.2rem; }
li { list-style-type: disc; }
.mb-4 { margin-bottom: 1rem; }
</style>
