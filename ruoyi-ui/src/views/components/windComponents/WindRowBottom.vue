<template>
  <div class="wind-row-bottom">
    <el-row style="margin-top:20px">
      <slot />
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
    // goTop() {
    //   this.topKey = !this.topKey
    //   this.topValue = !this.topValue
    // },
    // goDown() {
    //   this.topKey = !this.topKey
    //   this.topValue = !this.topValue
    // }
  }
}
</script>
<style scoped lang="less">
</style>
