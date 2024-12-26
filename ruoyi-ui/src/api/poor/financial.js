import request from '@/utils/request'

// 查询贫困资助列表
export function listFinancial(query) {
  return request({
    url: '/poor/financial/list',
    method: 'get',
    params: query
  })
}

// 查询贫困资助详细
export function getFinancial(id) {
  return request({
    url: '/poor/financial/' + id,
    method: 'get'
  })
}

// 新增贫困资助
export function addFinancial(data) {
  return request({
    url: '/poor/financial',
    method: 'post',
    data: data
  })
}

// 修改贫困资助
export function updateFinancial(data) {
  return request({
    url: '/poor/financial',
    method: 'put',
    data: data
  })
}

// 删除贫困资助
export function delFinancial(id) {
  return request({
    url: '/poor/financial/' + id,
    method: 'delete'
  })
}
