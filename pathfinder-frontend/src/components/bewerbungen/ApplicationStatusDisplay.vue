<template>
  <v-chip :color="chipColor" size="small" text-color="white">
    <v-icon start :icon="icon" size="18" />
    {{ label }}
  </v-chip>
</template>

<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{
  status: 'EINGEREICHT' | 'IN_PRUEFUNG' | 'ABGELEHNT' | 'ANGELADEN' | 'ANGENOMMEN'
}>()

const label = computed(() => {
  switch (props.status) {
    case 'EINGEREICHT': return 'Eingereicht'
    case 'IN_PRUEFUNG': return 'In Prüfung'
    case 'ABGELEHNT': return 'Abgelehnt'
    case 'ANGELADEN': return 'Eingeladen'
    case 'ANGENOMMEN': return 'Angenommen'
    default: return 'Unbekannt'
  }
})

const chipColor = computed(() => {
  switch (props.status) {
    case 'EINGEREICHT': return 'grey'
    case 'IN_PRUEFUNG': return 'blue'
    case 'ABGELEHNT': return 'red'
    case 'ANGELADEN': return 'orange'
    case 'ANGENOMMEN': return 'green'
    default: return 'grey'
  }
})

const icon = computed(() => {
  switch (props.status) {
    case 'EINGEREICHT': return 'mdi-email-outline'
    case 'IN_PRUEFUNG': return 'mdi-progress-clock'
    case 'ABGELEHNT': return 'mdi-close-circle-outline'
    case 'ANGELADEN': return 'mdi-account-check-outline'
    case 'ANGENOMMEN': return 'mdi-check-circle-outline'
    default: return 'mdi-help-circle-outline'
  }
})
</script>
