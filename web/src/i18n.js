import { createI18n } from 'vue-i18n'
import en from './locales/en.json'
import cz from './locales/cz.json'

// legacy: true keeps the Options API surface ($t, $i18n.locale) the components use.
export default createI18n({
  legacy: true,
  globalInjection: true,
  locale: import.meta.env.VITE_I18N_LOCALE || 'en',
  fallbackLocale: import.meta.env.VITE_I18N_FALLBACK_LOCALE || 'en',
  messages: { en, cz }
})
