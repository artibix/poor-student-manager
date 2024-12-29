import request from '@/utils/request'

// 获取首页统计数据
export function getStatistics() {
  return request({
    url: '/poor/dashboard/statistics',
    method: 'get'
  })
}