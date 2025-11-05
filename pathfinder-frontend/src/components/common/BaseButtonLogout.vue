<template>
  <v-btn color="error" variant="outlined" @click="onLogout" icon>
    <v-icon>mdi-power</v-icon>
  </v-btn>
</template>

<script lang="ts" setup>
import { defineProps } from 'vue'

const props = defineProps<{
  onLogout: () => void
}>()
</script>

<style scoped>
.v-btn {
  transition: transform 0.2s;
}
.v-btn:hover {
  transform: scale(1.1);
}
</style>
