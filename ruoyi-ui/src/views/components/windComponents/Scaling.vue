<template>
  <div id="content">
    <el-button v-if="topKey" class="upButton" type="mini" style="border:0" @click="goTop">
      <i class="el-icon-arrow-up" style="font-size:14px"/>
    </el-button>
    <el-button v-if="!topKey" class="downButton" type="mini" style="border:0" @click="goDown">
      <i class="el-icon-arrow-down" style="font-size:14px;"/>
    </el-button>
    <wind-menu class="wind-menu" :this-name="thisName" :fixed-name="fixedName"/>
  </div>
</template>
<script>
import TableHeight from '@/api/TableHeight'
import WindMenu from './WindMenu'

export default {
  components: {
    WindMenu
  },
  props: {
    thisName: {
      default: null,
      type: Object
    },
    domName: {
      default: null,
      type: HTMLDocument
    },
    fixedName: {
      default: true,
      type: Boolean
    }
  },
  data() {
    return {
      topKey: true,
      topValue: true
    }
  },
  mounted() {},
  destroyed() {},
  methods: {
    goTop() {
      this.topKey = false
      if (this.thisName.fixed) {
        this.thisName.tableHeight = TableHeight.goTop(this.domName)
      } else {
        TableHeight.domTop(this.domName)
      }
    },
    goDown() {
      this.topKey = true
      TableHeight.domDown(this.domName)
      if (this.thisName.fixed) {
        TableHeight.resizeTable(this.thisName)
      }
    }
  }
}
</script>
<style scoped lang="less">
.upButton {
  position: absolute;
  top: 3px;
  left: 50%-5px;
  height: 18px;
}

.upButton:hover {
  height: 25px;
  top: 0px;
  transition: ease-in-out 0.3s;
  // color:blue;
  // background-color:pink;
}

.downButton:hover {
  height: 25px;
  top: -2px;
  transition: ease-in-out 0.3s;
  // color:blue;
  // background-color:pink;
}

.downButton {
  position: absolute;
  top: -8px;
  left: 50%-5px;
  height: 18px;
}

#content {
  position: relative;
  height: 20px;
  margin-top: -20px
}

.wind-menu {
  position: absolute;
  bottom: 30px;
  right: 10px;
}
</style>
