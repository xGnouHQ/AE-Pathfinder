<template>
  <div class="login-background">
    <div class="login-card">
      <img src="@/assets/Pathfinder_Logo_1.0.png" alt="Logo" class="login-image" />
      <h1 class="text-h3 font-weight-bold mb-4">Login</h1>

      <form @submit.prevent="handleLogin">
        <input v-model="email" placeholder="E-Mail" class="login-input" />
        <input v-model="password" type="password" placeholder="Passwort" class="login-input" />
        <button type="submit" class="login-button">Einloggen</button>
        <p v-if="error" class="error">{{ error }}</p>
      </form>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import App from '@/App.vue'
import { createApp } from 'vue'
import { registerPlugins } from '@/plugins'
import axios from 'axios'

const email = ref('')
const password = ref('')
const error = ref('')

async function handleLogin() {
  error.value = ''
  try {
    const response = await axios.post('api/auth/login', {
      email: email.value,
      password: password.value
    })

    const user = response.data

    // Session Storage statt localStorage
    sessionStorage.setItem('loggedIn', 'true')
    sessionStorage.setItem('user', JSON.stringify(user))

    // Haupt-App starten
    const app = createApp(App)
    registerPlugins(app)
    app.mount('#app')
  } catch (err: any) {
    if (err.response) {
      if (err.response.status === 404) error.value = 'E-Mail nicht gefunden'
      else if (err.response.status === 401) error.value = 'Falsches Passwort'
      else error.value = 'Fehler beim Einloggen'
    } else {
      error.value = 'Server nicht erreichbar'
    }
  }
}
</script>


<style scoped>
.login-background {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}
.login-card {
  background-color: #fff;
  padding: 40px 30px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.1);
  text-align: center;
  width: 100%;
  max-width: 400px;
}
.login-image { width: 100px; margin-bottom: 20px; }
.login-input {
  display: block;
  width: 100%;
  padding: 10px 12px;
  margin-bottom: 15px;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 16px;
}
.login-button {
  width: 100%;
  padding: 10px;
  background-color: #1976d2;
  color: #fff;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  cursor: pointer;
}
.login-button:hover { background-color: #115293; }
.error { color: red; margin-top: 10px; font-weight: 500; }
</style>
