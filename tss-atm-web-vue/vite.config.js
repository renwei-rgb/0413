module.exports = {
  devServer: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8889',
        changeOrigin: true
      }
    }
  }
}
