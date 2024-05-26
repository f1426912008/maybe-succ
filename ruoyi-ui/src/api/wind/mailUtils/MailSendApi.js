import request from '@/utils/request'

export default class MailSendApi {

  static sendMail(data) {
    return request({
      url: '/mailUtil/sendMail',
      method: 'post',
      data: data
    })
  }
}
