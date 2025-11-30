// plugins/vuetify.ts
import { createVuetify } from 'vuetify'
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

export default createVuetify({
  theme: {
    defaultTheme: 'dark', // Erzwingt immer Dark Mode
    themes: {
      dark: {
        dark: true,
        colors: {
          background: '#121212',      // Seiten-Hintergrund
          surface: '#1E1E1E',         // Cards, Panels
          primary: '#90caf9',
          secondary: '#f48fb1',
          success: '#66bb6a',
          info: '#29b6f6',
          warning: '#ffa726',
          error: '#ef5350',
          'on-background': '#ffffff', // Textfarbe auf Hintergrund
          'on-surface': '#ffffff',    // Textfarbe auf Cards
        },
      },
    },
  },
})
