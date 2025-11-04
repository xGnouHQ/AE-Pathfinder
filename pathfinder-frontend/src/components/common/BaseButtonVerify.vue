<template>
  <v-btn
    color="primary"
    @click="$emit('click')"
  >
    Bestätigen
  </v-btn>
</template>

<script setup lang="ts">
import { VBtn } from "vuetify/components";
defineEmits<{ (e: 'click'): void }>();
</script>
