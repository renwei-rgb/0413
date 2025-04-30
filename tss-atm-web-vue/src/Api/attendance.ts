import request from '../utils/request'

export const checkIn = () => {
    return request({
        url: '/attendance/check-in',
        method: 'post'
    })
}

export const checkOut = () => {
    return request({
        url: '/attendance/check-out',
        method: 'post'
    })
}

export const getAttendanceList = (params: {
    startDate: string
    endDate: string
    page: number
    size: number
}) => {
    return request({
        url: '/attendance/list',
        method: 'get',
        params
    })
}