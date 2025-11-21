import { createApp } from 'vue'
import App from './App.vue'
import LoginView from '@/pages/LoginView.vue'
import { registerPlugins } from './plugins'

// Prüfen, ob Nutzer eingeloggt ist
const loggedIn = localStorage.getItem('loggedIn') === 'true'

// Root-Komponente wählen: LoginView oder App
const rootComponent = loggedIn ? App : LoginView
const app = createApp(rootComponent)

// Plugins nur registrieren, wenn App.vue geladen wird
if (loggedIn) {
  registerPlugins(app)
}

app.mount('#app')
