<template>
  <v-btn
    color="secondary"
    variant="outlined"
    @click="$emit('click')"
  >
    Abbrechen
  </v-btn>
</template>

<script setup lang="ts">
import { VBtn } from "vuetify/components";
defineEmits<{ (e: 'click'): void }>();
</script>
