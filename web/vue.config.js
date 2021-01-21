module.exports = {
  "transpileDependencies": [
    "vuetify"
  ],

  pluginOptions: {
    i18n: {
      locale: 'en',
      fallbackLocale: 'cz',
      localeDir: 'locales',
      enableInSFC: true
    }
  },

  chainWebpack: config => {
    config
        .plugin('html')
        .tap(args => {
          args[0].title = 'My Debt'
          return args
        })
  }
}
