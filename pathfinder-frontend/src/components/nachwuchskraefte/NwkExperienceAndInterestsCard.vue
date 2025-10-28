<template>
    <v-card class="pa-4 mb-6">
        <!-- Header mit Titel und Bearbeiten-Button -->
        <v-card-title class="d-flex justify-space-between align-center">
            <span>Erfahrungen & Interessen</span>
            <v-btn
                v-if="editable"
                color="primary"
                variant="outlined"
                size="small"
                @click="$emit('edit')"
            >
                Bearbeiten
            </v-btn>
        </v-card-title>
        <v-divider></v-divider>
        <v-card-text>
            <!-- Praktika -->
            <div class="mb-4">
                <h3>Praktika</h3>
                <ol v-if="filledExperiences.length > 0">
                    <li
                        v-for="(praktikum, index) in filledExperiences"
                        :key="index"
                    >
                        {{ praktikum }}
                    </li>
                </ol>
                <p v-else>Noch keine Praktika angegeben.</p>
            </div>

            <!-- Programmierkenntnisse -->
            <div class="mb-4">
                <h3>Programmierkenntnisse</h3>
                <div
                    v-if="nwk.knowsProgramming && nwk.programmingLanguages.length > 0"
                >
                    <p>
                        Programmiersprachen: {{
                        nwk.programmingLanguages.slice(0,5).join(', ') }}
                    </p>
                </div>
                <div v-else>
                    <p>Keine Programmierkenntnisse angegeben.</p>
                </div>
            </div>

            <!-- Interessen -->
            <div>
                <h3>Interessen</h3>
                <ol v-if="filledInterests.length > 0">
                    <li
                        v-for="(interest, index) in filledInterests"
                        :key="index"
                    >
                        {{ interest }}
                    </li>
                </ol>
                <p v-else>Noch keine Interessen angegeben.</p>
            </div>
        </v-card-text>
    </v-card>
</template>

<script setup lang="ts">
    import { computed, defineProps } from 'vue';

    interface NwkExperience {
      experiences: string[];
      knowsProgramming: boolean;
      programmingLanguages: string[];
      interests: string[];
    }

    const props = defineProps<{
      nwk: NwkExperience;
      editable?: boolean;
    }>();

    // Maximal 5 Praktika anzeigen, nur wenn nicht leer
    const filledExperiences = computed(() =>
      props.nwk.experiences.filter(exp => exp && exp.trim() !== '').slice(0, 5)
    );

    // Maximal 5 Interessen anzeigen, nur wenn nicht leer
    const filledInterests = computed(() =>
      props.nwk.interests.filter(interest => interest && interest.trim() !== '').slice(0, 5)
    );
</script>
