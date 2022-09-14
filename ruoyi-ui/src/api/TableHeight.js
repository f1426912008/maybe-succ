import elementResizeDetectorMaker from 'element-resize-detector'

export default class TableHeight {
  static getTableHeight(document) {
    const all = document.body.clientHeight
    let query = 0
    let button = 0
    let page = 0
    if (document.getElementsByClassName('wind-row-query')[0]) {
      query = document.getElementsByClassName('wind-row-query')[0].offsetHeight
    }
    if (document.getElementsByClassName('wind-row-option')[0]) {
      button = document.getElementsByClassName('wind-row-option')[0].offsetHeight
    }
    if (document.getElementsByClassName('wind-row-bottom')[0]) {
      page = document.getElementsByClassName('wind-row-bottom')[0].offsetHeight
    }
    // const query = document.getElementsByClassName('wind-row-query') ? document.getElementsByClassName('wind-row-query')[0].offsetHeight : 0
    // const button = document.getElementsByClassName('wind-row-option') ? document.getElementsByClassName('wind-row-option')[0].offsetHeight : 0
    // const page = document.getElementsByClassName('wind-row-bottom') ? document.getElementsByClassName('wind-row-bottom')[0].offsetHeight : 0
    const other = 50 + 30 + 20 * 5 + 5
    if (TableHeight.isIframe()) {
      return all - query - button - page - other
    } else {
      if (String(localStorage.breadStatus) === 'true') {
        return all - query - button - page - other + 55
      } else {
        return all - query - button - page - other + 80
      }
    }
  }
  static goTop(document) { // 点击拉起
    this.domTop(document)
    return this.getTableHeight(document) + 40
  }
  static goOther(document) { // 点击释放
    this.domDown(document)
    return this.getTableHeight(document)
  }
  static domTop(document) { // 查询和按钮部分隐藏
    if (document.getElementsByClassName('wind-row-query')[0]) {
      document.getElementsByClassName('wind-row-query')[0].style.display = 'none'
    }
    if (document.getElementsByClassName('wind-row-option')[0]) {
      document.getElementsByClassName('wind-row-option')[0].style.display = 'none'
    }
  }
  static domDown(document) { // 查询和按钮部分显示
    if (document.getElementsByClassName('wind-row-query')[0]) {
      document.getElementsByClassName('wind-row-query')[0].style.display = 'block'
    }
    if (document.getElementsByClassName('wind-row-option')[0]) {
      document.getElementsByClassName('wind-row-option')[0].style.display = 'block'
    }
  }
  static init(_this) { // 初始化
    _this.fixed = true
    _this.operateFix = true
    this.watchSize(_this)
    this.keyupF4(_this)
  }
  static watchSize(_this) { // 监听头部展开收缩高度
    var erd = elementResizeDetectorMaker()
    if (document.getElementsByClassName('wind-row-query')[0]) {
      erd.listenTo(document.getElementsByClassName('wind-row-query'), (element) => {
        _this.$nextTick(() => {
          this.resizeTable(_this)
        })
      })
    } else if (document.getElementsByClassName('wind-row-option')[0]) {
      erd.listenTo(document.getElementsByClassName('wind-row-option'), (element) => {
        _this.$nextTick(() => {
          this.resizeTable(_this)
        })
      })
    }
  }
  static keyupF4(_this) { // f4快捷键
    document.onkeydown = function(e) {
      const key = window.event.keyCode;
      if (key === 115) {
        TableHeight.changeFixed(_this)
      }
    }
  }
  static changeFixed(_this) {
    if (!_this.fixed) {
      this.fixedTable(_this)
    } else {
      this.unfixedTable(_this)
    }
  }
  static fixedTable(_this) { //  固定表头
    _this.fixed = true
    this.resizeTable(_this)
  }
  static unfixedTable(_this) { //  取消固定表头
    _this.fixed = false
    _this.tableHeight = ''
  }
  static resizeTable(_this) { // 固定表格对表格进行的操作
    if (_this.fixed && document.getElementsByClassName('wind-row-query')[0].style.display === 'block') {
      _this.tableHeight = this.goOther(document)// 显示按钮+固定表头
    } else if (_this.fixed && document.getElementsByClassName('wind-row-query')[0].style.display === 'none') {
      _this.tableHeight = this.goTop(document)// 显示按钮+固定表头
    } else {
      this.domDown(document)// 显示按钮
    }
  }
  static isIframe() {
    // false 被嵌套; true 未被嵌套
    return window.self === window.top;
  }
}
