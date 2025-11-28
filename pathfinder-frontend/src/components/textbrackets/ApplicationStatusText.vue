<template>
  <div>
    <h3 class="text-h6 font-weight-medium mb-3">Application Status</h3>

    <div v-if="notifications.length > 0">
      <v-list>
       <v-hover
          v-for="note in notifications"
          :key="note.id"
          v-slot="{ isHovering, props: hoverProps }"
        >
          <v-list-item
            v-bind="hoverProps"
            link
            nav
            :to="`/bewerbungen/${note.id}/ApplicationView`"
            :class="[
              'transition-all',
              getStatusHoverColor(note.status, !!isHovering)
            ]"
          >
           <v-list-item link nav @click="$emit('open', note.id)" class="transition-all hover:bg-grey-lighten-4">
             <!-- Icon + Titel nebeneinander -->
             <div class="d-flex align-center">
               <v-icon :color="getStatusColor(note.status)" class="mr-2">
                 {{ getStatusIcon(note.status) }}
               </v-icon>
               <v-list-item-title :class="`text-${getStatusColor(note.status)} font-weight-medium`">
                 Bewerbung für "{{ note.stelleTitel }}" ist {{ note.status.toLowerCase() }}.
               </v-list-item-title>
             </div>

             <!-- Untertitel direkt unter Titel -->
             <v-list-item-subtitle class="mt-1">
               Eingereicht am: {{ formatDate(note.eingereichtAm) }}
             </v-list-item-subtitle>
           </v-list-item>


          </v-list-item>
        </v-hover>

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

/* ---------------------------------------------------------
   🔍 Bewerbungen laden
---------------------------------------------------------*/
async function loadNotifications() {
  if (!props.nwkId) return

  try {
    const res = await fetch(`/api/bewerbungen/nachwuchskraft/${props.nwkId}`)
    if (!res.ok) throw new Error(`Fehler beim Laden: ${res.status}`)

    const data = await res.json()

    notifications.value = data.map((b: any) => ({
      id: b.id,
      stelleTitel: b.stelleTitel ?? `Stelle #${b.stelleId}`,
      status: b.status,
      eingereichtAm: b.eingereichtAm,
      stelleId: b.stelleId
    }))
  } catch (err) {
    console.error('Fehler beim Laden der Bewerbungen:', err)
  }
}

/* ---------------------------------------------------------
   📅 Datum formatieren
---------------------------------------------------------*/
function formatDate(dateStr: string) {
  if (!dateStr) return '-'
  return new Date(dateStr).toLocaleDateString('de-DE')
}

/* ---------------------------------------------------------
   🎨 Farben & Icons für Status
---------------------------------------------------------*/
function getStatusColor(status: string) {
  switch (status.toLowerCase()) {
    case 'angenommen': return 'green'
    case 'abgelehnt': return 'red'
    case 'in bearbeitung': return 'amber'
    case 'eingereicht': return 'blue'
    default: return 'grey'
  }
}

function getStatusIcon(status: string) {
  switch (status.toLowerCase()) {
    case 'angenommen': return 'mdi-check-circle'
    case 'abgelehnt': return 'mdi-close-circle'
    case 'in bearbeitung': return 'mdi-timer-sand'
    case 'eingereicht': return 'mdi-send'
    default: return 'mdi-information'
  }
}

/* ---------------------------------------------------------
   ✨ Hover-Farbe abhängig vom Status
---------------------------------------------------------*/
function getStatusHoverColor(status: string, hovering: boolean) {
  if (!hovering) return ''

  switch (status.toLowerCase()) {
    case 'angenommen': return 'bg-green-lighten-4 elevation-2'
    case 'abgelehnt': return 'bg-red-lighten-4 elevation-2'
    case 'in bearbeitung': return 'bg-amber-lighten-4 elevation-2'
    case 'eingereicht': return 'bg-blue-lighten-4 elevation-2'
    default: return 'bg-grey-lighten-4 elevation-2'
  }
}

/* ---------------------------------------------------------
   🔄 Reload bei ID-Änderung
---------------------------------------------------------*/
onMounted(() => loadNotifications())
watch(() => props.nwkId, () => loadNotifications())
</script>
