import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

// 创建 axios 实例
const request = axios.create({
    baseURL: '/api', // 这里的 /api 会被 vite 代理转发
    timeout: 15000
})

// 请求拦截器
request.interceptors.request.use(
    config => {
        // 添加调试日志
        console.log('发送请求:', {
            url: config.url,
            method: config.method,
            data: config.data,
            headers: config.headers
        })
        // 从 localStorage 获取 token
        const token = localStorage.getItem('token')
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`
        }
        return config
    },
    error => {
        console.error('请求错误:', error)
        return Promise.reject(error)
    }
)

// 响应拦截器
request.interceptors.response.use(
    response => {
        // 添加调试日志
        console.log('收到响应:', response.data)
        const res = response.data
        // 这里可以根据后端的响应结构做相应的处理
        if (res.code === 200) {
            return res
        } else {
            ElMessage.error(res.message || '请求失败')
            return Promise.reject(new Error(res.message || '请求失败'))
        }
    },
    error => {
        // 添加调试日志
        console.error('响应错误:', error.response || error)
        if (error.response) {
            switch (error.response.status) {
                case 401:
                    // token 过期或未登录
                    localStorage.removeItem('token')
                    router.push('/login')
                    break
                case 403:
                    ElMessage.error('没有权限')
                    break
                case 404:
                    ElMessage.error('请求的资源不存在')
                    break
                case 500:
                    ElMessage.error(error.response.data?.message || '服务器错误')
                    break
                default:
                    ElMessage.error(error.response.data?.message || '请求失败')
            }
        } else {
            ElMessage.error('网络错误，请检查您的网络连接')
        }
        return Promise.reject(error)
    }
)

export default request