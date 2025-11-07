import { createApp, defineAsyncComponent } from 'vue'
import { registerPlugins } from '@/plugins'

// Root-Komponente wird dynamisch beim Login geladen
const rootComponent = defineAsyncComponent(() => import('@/pages/LoginView.vue'))

createApp(rootComponent).mount('#app')
