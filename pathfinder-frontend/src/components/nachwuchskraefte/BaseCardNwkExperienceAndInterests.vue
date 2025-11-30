<template>
  <v-card class="pa-4 mb-6">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>Erfahrungen & Interessen</span>
      <BaseButtonEdit v-if="editable" @click="$emit('edit')" class="mr-2" />
    </v-card-title>

    <v-divider />

    <v-card-text>
      <div class="mb-4">
        <h3>Wunschabteilungen</h3>
        <ul v-if="departments.length > 0">
          <li v-for="d in departments" :key="d.id">{{ d.name }}</li>
        </ul>
        <p v-else class="text-muted">Noch keine Abteilungen angegeben.</p>
      </div>

      <div class="mb-4">
        <h3>Interessen</h3>
        <ul v-if="interests.length > 0">
          <li v-for="t in interests" :key="t.id">{{ t.name }}</li>
        </ul>
        <p v-else class="text-muted">Noch keine Interessen angegeben.</p>
      </div>

      <!-- Programmieren -->
            <div v-if="programmieren">
              <strong>Programmieren:</strong> Ja
              <div v-if="programmiersprachen">
                <strong>Programmiersprachen:</strong> {{ programmiersprachen }}
              </div>
            </div>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { computed, defineProps } from 'vue'
import BaseButtonEdit from '@/components/common/BaseButtonEdit.vue'

interface ExperienceDTO {
  interessen: { id: number; name: string }[]
  wunschabteilungen: { id: number; name: string }[]
  programmieren?: boolean
  programmiersprachen: string | null
}

const props = defineProps<{ nwkExperience: ExperienceDTO, editable?: boolean }>()
const departments = computed(() => props.nwkExperience.wunschabteilungen ?? [])
const interests = computed(() => props.nwkExperience.interessen ?? [])
const programmieren = computed(() => props.nwkExperience.programmieren ?? false)
const programmiersprachen = computed(() => props.nwkExperience.programmiersprachen ?? '')
</script>


<style scoped>
.text-muted { color: #6c757d; }
.mb-4 { margin-bottom: 1rem; }
.mr-2 { margin-right: 0.5rem; }
ul { padding-left: 1.2rem; margin: 0; }
li { list-style-type: disc; }
</style>
