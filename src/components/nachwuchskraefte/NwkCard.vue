<template>
  <v-card>
    <v-card-title> Profilinformationen </v-card-title>
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
        <v-col> E-Mail: {{ nwk.mail }} </v-col>
      </v-row>
      <v-row>
        <v-col> Jahrgang: {{ nwk.year }} </v-col>
        <v-col> Studienrichtung: {{ nwk.major }} </v-col>
       </v-row>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
defineProps<{
  nwk: {
    gender: string;
    personalnumber: string;
    surename: string;
    firstname: string;
    mail: string;
    year: string;
    major: string;
  };
}>();
</script>

<style scoped>
/* Füge hier spezifische Stile für die JobCard hinzu, wenn nötig */
</style>
