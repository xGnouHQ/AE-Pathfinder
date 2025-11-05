import { createApp } from 'vue'
import App from './App.vue'
import LoginView from '@/pages/LoginView.vue'
import { registerPlugins } from '@/plugins'

// Prüfen, ob Benutzer eingeloggt ist
const loggedIn = localStorage.getItem('loggedIn') === 'true'

// Root-Komponente wählen
const rootComponent = loggedIn ? App : LoginView
const app = createApp(rootComponent)

// Plugins nur für App.vue registrieren
if (loggedIn) {
  registerPlugins(app)
}

app.mount('#app')
