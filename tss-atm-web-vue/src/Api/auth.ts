import request from '../utils/request'

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
    return request({
        url: `/auth/login?username=${encodeURIComponent(data.username)}&password=${encodeURIComponent(data.password)}`,
        method: 'post'
    })
}

export const register = (data: RegisterData) => {
    return request({
        url: '/auth/register',
        method: 'post',
        data
    })
}

export const logout = () => {
    return request({
        url: '/auth/logout',
        method: 'post'
    })
}