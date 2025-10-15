<template>
  <v-container>
    <h1 class="mb-6">Offene Stellen</h1>

    <v-container class="box">
      <v-row>
        <v-col v-for="job in jobs" :key="job.id" cols="12">
          <router-link :to="job.link" class="no-underline">
            <v-card>
              <v-card-title>{{ job.title }}</v-card-title>
              <v-card-text>
                <v-row>
                  <v-col>
                    Datum: {{ job.date }}
                  </v-col>
                  <v-col>
                    Entgeltgruppe: {{ job.payGrade }}
                  </v-col>
                  <v-col>
                    Referat: {{ job.department }}
                  </v-col>
                </v-row>
                <div class="job-description">
                  {{ job.description }} <!-- Individueller Text -->
                </div>
              </v-card-text>
            </v-card>
          </router-link>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue';

// Beispiel-Daten für offene Stellen
const jobs = ref([
  {
    id: 1, // Eindeutige ID
    title: 'DevOps Junior',
    date: 'XX.XX.XXXX',
    payGrade: 'E10',
    department: 'it@M',
    link: 'stellen/JobDisplayVorlage'
  },
  {
    id: 2, // Eindeutige ID
    title: 'Frontend Developer',
    date: 'YY.YY.YYYY',
    payGrade: 'E9',
    department: 'Web Development',
    link: '/JobDisplayVorlage2'
  },
  // Weitere Jobs können hier hinzugefügt werden...
]);
</script>

<style scoped>
.box {
  margin: 2% 1% 1%;
  border: 2px solid #0000001a;
}
.no-underline {
  text-decoration: none; /* Entferne die Unterstreichung von Links */
}
.job-description {
  margin-top: 10px; /* Abstand zwischen den Feldern und der Beschreibung */
}
</style>
