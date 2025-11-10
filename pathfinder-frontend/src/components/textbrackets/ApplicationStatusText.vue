<template>
  <div>
    <h3 class="text-h6 font-weight-medium mb-3">Application Status</h3>

    <div v-if="notifications.length > 0">
      <v-list>
        <v-list-item
          v-for="note in notifications"
          :key="note.id"
          prepend-icon="mdi-bell-outline"
        >
          <v-list-item-title>
            Bewerbung für "{{ note.stelleTitel }}" ist {{ note.status.toLowerCase() }}.
          </v-list-item-title>
          <v-list-item-subtitle>
            Eingereicht am: {{ formatDate(note.eingereichtAm) }}
          </v-list-item-subtitle>
        </v-list-item>
      </v-list>
    </div>

    <div v-else>
      <v-alert type="info" variant="tonal">
        Keine neuen Bewerbungen.
      </v-alert>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'

interface BewerbungNotification {
  id: number
  stelleTitel: string
  status: string
  eingereichtAm: string
  stelleId: number
}

const props = defineProps<{ nwkId: number }>()
const notifications = ref<BewerbungNotification[]>([])

async function loadNotifications() {
  if (!props.nwkId) return
  try {
    const res = await fetch(`/api/bewerbungen/nachwuchskraft/${props.nwkId}`)
    if (!res.ok) throw new Error(`Fehler beim Laden: ${res.status}`)

    const data = await res.json()
    notifications.value = data.map((b: any) => ({
      id: b.id,
      stelleTitel: b.stelle?.titel ?? `Stelle #${b.stelle?.id ?? 'Unbekannt'}`,
      status: b.status,
      eingereichtAm: b.eingereichtAm,
      stelleId: b.stelle?.id ?? 0
    }))
  } catch (err) {
    console.error('Fehler beim Laden der Bewerbungen:', err)
  }
}

function formatDate(dateStr: string) {
  if (!dateStr) return '-'
  return new Date(dateStr).toLocaleDateString('de-DE')
}

onMounted(() => loadNotifications())
watch(() => props.nwkId, () => loadNotifications())
</script>
