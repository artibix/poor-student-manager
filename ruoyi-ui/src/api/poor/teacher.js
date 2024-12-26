import request from '@/utils/request'

// 查询教师信息列表
export function listTeacher(query) {
  return request({
    url: '/poor/teacher/list',
    method: 'get',
    params: query
  })
}

// 查询教师信息详细
export function getTeacher(userId) {
  return request({
    url: '/poor/teacher/' + userId,
    method: 'get'
  })
}

// 新增教师信息
export function addTeacher(data) {
  return request({
    url: '/poor/teacher',
    method: 'post',
    data: data
  })
}

// 修改教师信息
export function updateTeacher(data) {
  return request({
    url: '/poor/teacher',
    method: 'put',
    data: data
  })
}

// 删除教师信息
export function delTeacher(userId) {
  return request({
    url: '/poor/teacher/' + userId,
    method: 'delete'
  })
}
