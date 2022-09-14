<template>
  <div class="wind-menu">
    <el-dropdown class="button" @command="handleCommand">
      <span class="el-dropdown-link">
        <i class="el-icon-menu" @mouseover="goOver"/>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item v-if="!fixedName" icon="el-icon-lock">锁定表头</el-dropdown-item>
        <el-dropdown-item v-if="fixedName" icon="el-icon-unlock">取消锁定表头</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>
<script>
import TableHeight from '@/api/TableHeight'

export default {
  props: {
    thisName: {
      default: null,
      type: Object
    },
    fixedName: {
      default: null,
      type: Boolean
    }
  },
  data() {
    return {
      // fixed: this.thisName.fixed
    }
  },
  mounted() {
    // console.log('this.thisName.fixed', this.thisName.fixed)
  },
  destroyed() {
  },
  methods: {
    changeFixed() {
      // TableHeight.changeFixed(this.thisName)
    },
    handleCommand() {
      TableHeight.changeFixed(this.thisName)
    },
    goOver() {
      document.getElementsByClassName('el-icon-menu')[0].style.transition = 'all 0.2s ease-in-out'
    }
  }
}
</script>
<style scoped lang="less">
.wind-row-option {
  position: relative;
}

.button {
  position: absolute;
  right: 0;
  bottom: -20px;
  border: 0;
  height: 20px;
  line-height: 20px;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
  position: relative;
}

.el-icon-menu {
  position: absolute;
  top: 10px;
  left: -10px;
  font-size: 20px;
}

.el-icon-arrow-down {
  font-size: 12px;
}

.demonstration {
  display: block;
  color: #8492a6;
  font-size: 14px;
  margin-bottom: 20px;
}
</style>
