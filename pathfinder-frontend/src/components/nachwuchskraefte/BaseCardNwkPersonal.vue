<template>
  <v-card>
    <v-card-title class="d-flex justify-space-between align-center">
      <span>Persönliche Daten</span>
    </v-card-title>

    <v-divider></v-divider>

    <v-card-text>
      <v-row>
        <v-col>Personalnummer: {{ nwk.personalnumber }}</v-col>
      </v-row>
      <v-row>
        <v-col>Nachname: {{ nwk.surename }}</v-col>
        <v-col>Vorname: {{ nwk.firstname }}</v-col>
      </v-row>
      <v-row>
        <v-col>E-Mail: {{ nwk.mail }}</v-col>
      </v-row>
      <v-row>
        <v-col>Jahrgang: {{ nwk.year }}</v-col>
        <v-col>Studienrichtung: {{ nwk.major }}</v-col>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">

interface Nachwuchs {
  gender: string;
  personalnumber: string;
  surename: string;
  firstname: string;
  mail: string;
  year: string;
  major: string;
}

defineProps<{
  nwk: Nachwuchs;
  editable?: boolean; // zeigt den "Bearbeiten"-Button, wenn true
}>();

defineEmits<{
  (e: 'edit'): void;
}>();
</script>

<style scoped>
.v-card-title {
  font-weight: 600;
}
</style>
