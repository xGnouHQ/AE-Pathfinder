<template>
  <div>
    <h3 class="text-h6 font-weight-medium mb-3">Application Status</h3>

    <div v-if="notifications.length > 0">
      <v-list>
        <v-list-item v-for="note in notifications" :key="note.id" prepend-icon="mdi-bell-outline">
          <v-list-item-title>{{ note.message }}</v-list-item-title>
          <v-list-item-subtitle>{{ note.date }}</v-list-item-subtitle>
        </v-list-item>
      </v-list>
    </div>

    <div v-else>
      <v-alert type="info" variant="tonal">
        No new notifications.
      </v-alert>
    </div>
  </div>
</template>

<script setup lang="ts">
interface Notification {
  id: number
  message: string
  date: string
}

const props = defineProps<{ notifications: Notification[] }>()
</script>
