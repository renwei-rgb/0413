<template>
  <div class="login-container">
    <div class="login-background"></div>
    <el-card class="login-card">
      <template #header>
        <h2 class="login-title">ATM System Login</h2>
      </template>
      
      <el-form
        ref="loginForm"
        :model="formData"
        :rules="rules"
        label-position="top"
      >
        <el-form-item label="Username" prop="username">
          <el-input
            v-model="formData.username"
            placeholder="Enter your username"
            prefix-icon="User"
          />
        </el-form-item>
        
        <el-form-item label="Password" prop="password">
          <el-input
            v-model="formData.password"
            type="password"
            placeholder="Enter your password"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            class="login-button"
            @click="handleLogin"
          >
            Login
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import * as authApi from '@/api/auth'

const router = useRouter()
const loading = ref(false)

const formData = reactive({
  username: '',
  password: ''
})

const rules: FormRules = {
  username: [
    { required: true, message: 'Please input username', trigger: 'blur' }
  ],
  password: [
    { required: true, message: 'Please input password', trigger: 'blur' }
  ]
}

const loginForm = ref<FormInstance>()

const handleLogin = async () => {
  if (!loginForm.value) return
  
  await loginForm.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const { data } = await authApi.login(formData)
        localStorage.setItem('token', data.token)
        ElMessage.success('Login successful')
        router.push('/home/dashboard')
      } catch (error) {
        console.error('Login error:', error)
        ElMessage.error('Invalid username or password')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f7fa;
}

.login-card {
  width: 100%;
  max-width: 400px;
  margin: 20px;
}

.login-title {
  text-align: center;
  margin: 0;
  color: #303133;
}

.login-button {
  width: 100%;
}

.login-background {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image: url('https://source.unsplash.com/random/1920x1080/?nature');
  background-size: cover;
  background-position: center;
  opacity: 0.1;
  z-index: 0;
}
</style>