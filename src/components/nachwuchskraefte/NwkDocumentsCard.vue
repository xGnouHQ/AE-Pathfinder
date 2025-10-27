<template>
  <v-card class="mt-4">
    <v-card-title class="d-flex justify-space-between align-center">
      <span>Dokumente</span>
      <v-btn color="secondary" @click="$emit('upload')" small>
        Dokumente hochladen
      </v-btn>
    </v-card-title>

    <v-divider></v-divider>

    <v-card-text>
      <div v-if="savedFiles.length === 0" class="text--disabled">
        Keine Dokumente hochgeladen.
      </div>

      <v-list v-else dense>
        <v-list-item v-for="file in savedFiles" :key="file.id">
          <v-list-item-icon>
            <v-icon>mdi-file-document-outline</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>
              <a
                v-if="file.url"
                :href="file.url"
                target="_blank"
                rel="noopener noreferrer"
              >
                {{ file.name }}
              </a>
              <span v-else>{{ file.name }}</span>
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
interface StoredFile {
  id: string;
  name: string;
  url?: string;
  fileObject?: File;
}

defineProps<{
  savedFiles: StoredFile[];
}>();

defineEmits<{
  (e: 'upload'): void;
}>();
</script>

<style scoped>
.text--disabled {
  color: rgba(0, 0, 0, 0.6);
}
</style>
