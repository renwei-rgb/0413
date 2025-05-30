import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/login.vue')
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
                component: () => import('../views/home/Dashboard.vue'),
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
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')
    if (to.meta.requiresAuth && !token) {
        next('/login')
    } else {
        next()
    }
})

export default router