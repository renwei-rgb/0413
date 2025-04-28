<script setup lang="ts">
import { onMounted } from 'vue'
import { useUserStore } from './stores/users'

const userStore = useUserStore()

onMounted(() => {
  // 检查本地存储中是否有token
  const token = localStorage.getItem('token')
  if (token) {
    userStore.setToken(token)
  }
})
</script>

<template>
  <router-view></router-view>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100vh;
}
</style>
