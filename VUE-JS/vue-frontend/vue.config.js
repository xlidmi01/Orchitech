const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  devServer: {
    proxy: {
      '/todoEntry': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
