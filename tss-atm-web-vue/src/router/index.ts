import { createRouter, createWebHistory } from 'vue-router'
import axios from 'axios'
import { ElMessage } from 'element-plus'

const routes = [
    {
        path: '/',
        name: 'Portal',
        component: () => import('../views/Portal.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    },
    {
        path: '/home',
        name: 'Layout',
        component: () => import('../layout/index.vue'),
        redirect: '/home/dashboard',
        children: [
            {
                path: 'dashboard',
                name: 'Dashboard',
                component: () => import('../views/dashboard/index.vue'),
                meta: { requiresAuth: true }
            },
            {
                path: 'attendance',
                name: 'Attendance',
                component: () => import('../views/attendance/index.vue')
            },
            {
                path: 'statistics',
                name: 'Statistics',
                component: () => import('../views/statistics/index.vue')
            }
        ]
    },
    {
        path: '/dashboard',
        redirect: '/home/dashboard'
    },
    {
        path: '/',
        redirect: '/home/dashboard'
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router

// 路由守卫
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')
    if (to.meta.requiresAuth && !token) {
        next('/login')
    } else {
        next()
    }
})

const getUserId = () => localStorage.getItem('userId')

// 打卡时
const handleCheckIn = async () => {
  try {
    checkInLoading.value = true
    // TODO: 调用打卡API
    ElMessage.success('上班打卡成功')
    checkInDisabled.value = false
    checkOutDisabled.value = false
    todayRecord.value.checkIn = currentTime.value
  } catch (error: any) {
    ElMessage.error(error.message || '打卡失败')
  } finally {
    checkInLoading.value = false
  }
}

router.push('/home/dashboard')