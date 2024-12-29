import request from '@/utils/request'

// 查询贫困申请列表
export function listApplication(query) {
  return request({
    url: '/poor/application/list',
    method: 'get',
    params: query
  })
}

// 查询贫困申请详细
export function getApplication(id) {
  return request({
    url: '/poor/application/' + id,
    method: 'get'
  })
}

// 新增贫困申请
export function addApplication(data) {
  return request({
    url: '/poor/application',
    method: 'post',
    data: data
  })
}

// 修改贫困申请
export function updateApplication(data) {
  return request({
    url: '/poor/application',
    method: 'put',
    data: data
  })
}

// 删除贫困申请
export function delApplication(id) {
  return request({
    url: '/poor/application/' + id,
    method: 'delete'
  })
}

export function reviewApplication(data) {
  return request({
    url: '/poor/application/review',
    method: 'put',
    data: data
  })
}
