<template>
  <v-btn color="success" variant="outlined" @click="onLogin" icon>
    <v-icon>mdi-power</v-icon>
  </v-btn>
</template>

<script lang="ts" setup>
import { defineProps } from 'vue'

const props = defineProps<{
  onLogin: () => void
}>()
</script>

<style scoped>
/* optional: Hover / Animation */
.v-btn {
  transition: transform 0.2s;
}
.v-btn:hover {
  transform: scale(1.1);
}
</style>
