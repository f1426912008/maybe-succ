<template>
  <div class="wind-row-option">
    <el-row style="margin:20px 0">
      <slot/>
    </el-row>
  </div>
</template>
<script>
export default {
  props: {},
  data() {
    return {}
  },
  mounted() {},
  destroyed() {},
  methods: {
    handleCommand() {
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
