import axios from 'axios'
import request from '../utils/request'

const api = axios.create({
    baseURL: '/api'
})

// 请求拦截器
api.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token')
        if (token) {
            config.headers.Authorization = `Bearer ${token}`
        }
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

// 响应拦截器
api.interceptors.response.use(
    response => response,
    error => {
        if (error.response?.status === 401) {
            localStorage.removeItem('token')
            window.location.href = '/login'
        }
        return Promise.reject(error)
    }
)

export interface LoginData {
    username: string
    password: string
}

export interface RegisterData {
    username: string
    password: string
    confirmPassword: string
    email?: string
    phone?: string
    employeeId?: string
    department?: string
    realName?: string
}

export const login = (data: LoginData) => {
    return api.post('/auth/login', data)
}

export const register = (data: RegisterData) => {
    return request({
        url: '/auth/register',
        method: 'post',
        data
    })
}

export default api