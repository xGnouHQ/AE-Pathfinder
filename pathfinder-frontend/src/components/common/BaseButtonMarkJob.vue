<template>
  <v-btn
    :color="bookmarked ? 'warning' : 'primary'"
    :variant="bookmarked ? 'tonal' : 'outlined'"
    size="small"
    @click="toggleBookmark"
  >
    {{ bookmarked ? 'Entfernen' : 'Merken' }}
  </v-btn>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const props = defineProps<{
  jobId: number
}>()

const bookmarked = ref(false)

function toggleBookmark() {
  bookmarked.value = !bookmarked.value
  // Kein Router-Navigation mehr
  console.log(`Job ${props.jobId} bookmarked: ${bookmarked.value}`)
}
</script>
