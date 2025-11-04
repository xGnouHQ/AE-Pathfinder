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
import { useRouter } from 'vue-router'

const props = defineProps<{
  jobId: number
}>();

const bookmarked = ref(false)
const router = useRouter()

function toggleBookmark() {
  bookmarked.value = !bookmarked.value

  if (bookmarked.value) {
    // Navigiere zur Merkliste
    router.push({ path: '/merken/BookmarkView', query: { jobId: props.jobId } })
  }
}
</script>
