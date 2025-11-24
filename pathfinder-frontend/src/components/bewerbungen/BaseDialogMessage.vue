<template>
  <v-dialog v-model="isOpen" max-width="500px">
    <v-card>
      <v-card-title>Nachricht von Servicebereich</v-card-title>
      <v-divider />
      <v-card-text>
        <p>Ihre Bewerbung wurde akzeptiert!</p>
        <p>Rückmeldung von den Verantwortlichen: Alles gut vorbereitet, bitte Termine beachten.</p>
        <v-textarea
          v-model="reply"
          label="Antworten"
          rows="3"
        />
      </v-card-text>
      <v-divider />
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary" @click="sendReply">Senden</v-btn>
        <v-btn color="secondary" @click="close">Schließen</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits, watch } from 'vue'

const props = defineProps<{ modelValue: boolean }>()
const emit = defineEmits<{ (e: 'update:modelValue', val: boolean): void }>()

const isOpen = ref(props.modelValue)
const reply = ref('')

// Watcher, um v-model zu synchronisieren
watch(() => props.modelValue, val => (isOpen.value = val))
watch(isOpen, val => emit('update:modelValue', val))

function close() {
  isOpen.value = false
}

// ⭐ Anpassung: Feedback beim Senden
function sendReply() {
  // Feedback anzeigen
  alert('Nachricht abgeschickt')

  // Optional: Log in der Konsole
  console.log('Antwort gesendet:', reply.value)

  // Dialog schließen
  close()

  // Textarea zurücksetzen
  reply.value = ''
}
</script>
