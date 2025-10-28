<!-- src/components/ApplicationStatusDisplay.vue -->
<template>
  <v-chip :color="chipColor" size="small" text-color="white">
    <v-icon start :icon="icon" size="18" />
    {{ label }}
  </v-chip>
</template>

<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{
  status: 'eingegangen' | 'in_bearbeitung' | 'akzeptiert' | 'abgelehnt'
}>()

const label = computed(() => {
  switch (props.status) {
    case 'eingegangen': return 'Eingegangen'
    case 'in_bearbeitung': return 'In Bearbeitung'
    case 'akzeptiert': return 'Akzeptiert'
    case 'abgelehnt': return 'Abgelehnt'
    default: return 'Unbekannt'
  }
})

const chipColor = computed(() => {
  switch (props.status) {
    case 'eingegangen': return 'grey'
    case 'in_bearbeitung': return 'warning'
    case 'akzeptiert': return 'green'
    case 'abgelehnt': return 'red'
    default: return 'grey'
  }
})

const icon = computed(() => {
  switch (props.status) {
    case 'eingegangen': return 'mdi-email-outline'
    case 'in_bearbeitung': return 'mdi-progress-clock'
    case 'akzeptiert': return 'mdi-check-circle-outline'
    case 'abgelehnt': return 'mdi-close-circle-outline'
    default: return 'mdi-help-circle-outline'
  }
})
</script>
