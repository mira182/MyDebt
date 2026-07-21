import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import { aliases, mdi } from 'vuetify/iconsets/mdi'

export default createVuetify({
  theme: {
    defaultTheme: 'dark',
    // Match the indigo accent already used by the tab progress bars.
    themes: {
      dark: { colors: { primary: '#6366f1' } },
      light: { colors: { primary: '#6366f1' } }
    }
  },
  icons: {
    defaultSet: 'mdi',
    aliases,
    sets: { mdi }
  }
})
