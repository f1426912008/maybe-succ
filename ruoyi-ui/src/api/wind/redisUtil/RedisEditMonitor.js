import request from '@/utils/request'

export default class RedisEditMonitor {

  static queryPage(data) {
    return request({
      url: '/redisUtil/queryData',
      method: 'post',
      data: data
    })
  }

  static flushDb(data) {
    return request({
      url: '/redisUtil/flushDb',
      method: 'post',
      data: data
    })
  }

}
