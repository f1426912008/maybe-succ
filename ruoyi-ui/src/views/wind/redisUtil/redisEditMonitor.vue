<template>
  <el-container>
    <el-main>
      <wind-row-query>
        <el-form
          ref="searchRef"
          :model="queryParam.data"
          :rules="rules"
          inline
          style="border-bottom: 1px solid #dcdfe6"
        >
          <el-form-item label="选择库" prop="switchData">
            <el-select v-model="queryParam.data.switchData" placeholder="请选择" clearable>
              <el-option
                v-for="item in databases"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="键名" prop="redisKey">
            <el-input
              v-model="queryParam.data.redisKey"
              placeholder="请输入"
              maxlength="200"
              clearable
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :disabled="searchDisabled" @click="search('searchRef')">查 询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button @click="reset('searchRef')">重 置</el-button>
          </el-form-item>
        </el-form>
      </wind-row-query>
      <wind-row-option>
        <el-form inline>
          <el-form-item>
            <el-button type="primary" plain @click="addOne">新 增</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="danger" plain @click="flushDb(queryParam.data.switchData)">清 空</el-button>
          </el-form-item>
        </el-form>
      </wind-row-option>
      <div class="table-style">
        <scaling :this-name="this" :dom-name="document" :fixed-name="fixed"/>
        <el-table
          :data="tableData"
          v-loading="tableLoading"
          :max-height="tableHeight"
          highlight-current-row
          :row-style="{height: '40px'}"
          height="440"
          fit
          border
        >
          <el-table-column label="序号" type="index" align="center" width="55"/>
          <el-table-column prop="redisKey" label="键" align="center" min-width="300" show-overflow-tooltip/>
          <el-table-column prop="redisValue" label="值" align="center" min-width="300" show-overflow-tooltip/>
          <el-table-column prop="redisTTL" label="过期时间" align="center" min-width="100"/>
          <el-table-column label="操作" align="center" min-width="100">
            <template slot-scope="scope">
              <el-tooltip content="编 辑" placement="top">
                <el-button
                  size="mini"
                  type="primary"
                  icon="el-icon-edit"
                  @click="modifyOne(scope.row)"
                />
              </el-tooltip>
              &emsp;
              <el-tooltip content="删 除" placement="top">
                <el-popconfirm title="是否确定删除？">
                  <el-button
                    @click="removeOne(scope.row)"
                    size="mini"
                    type="danger"
                    slot="reference"
                    icon="el-icon-delete"
                  />
                </el-popconfirm>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
        <wind-row-bottom>
          <el-col style="height:20px;display:flex;justify-content:flex-end;align-items:center">
            <el-pagination
              :current-page="queryParam.pageInfo.pageNum"
              :page-size="queryParam.pageInfo.pageSize"
              :total="queryParam.pageInfo.total"
              :page-sizes="queryParam.pageInfo.pageSizes"
              layout="total, sizes, prev, pager, next, jumper"
              @current-change="handleCurrentChange"
              @size-change="handleSizeChange"
            />
          </el-col>
        </wind-row-bottom>
      </div>
      <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
        <span>这是一段信息</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>
import WindRowQuery from "@/views/components/windComponents/WindRowQuery";
import WindRowOption from "@/views/components/windComponents/WindRowOption";
import WindRowBottom from "@/views/components/windComponents/WindRowBottom";
import Scaling from "@/views/components/windComponents/Scaling";
import TableHeight from "@/api/TableHeight";
import redisEditMonitor from "@/api/wind/redisUtil/RedisEditMonitor";

export default {
  name: "redisEditMonitor",
  components: {
    WindRowQuery, Scaling, WindRowOption, WindRowBottom
  },
  data() {
    const regex = /[^0-9#()（）a-zA-Z\u4E00-\u9FA5]/g
    const validateUnitName = (rule, value, callback) => {
      if (!value) {
        callback()
      } else {
        if (!regex.test(value)) {
          callback()
        } else {
          callback('只能输入中文、英文、括号、#号、数字')
        }
      }
    }
    return {
      fixed: false,
      document: document,
      tableHeight: null,
      searchDisabled: false,
      tableLoading: false,
      dialogVisible: false,
      tableData: [],
      queryParam: {
        data: {
          switchData: 0,
          redisKey: '',
        },
        pageInfo: {
          pageNum: 1,
          pageSize: 10,
          total: 0,
          pageSizes: [10, 20, 30, 50, 100]
        }
      },
      rules: {
        // amtYm: [{required: true, message: '请选择年月', trigger: 'blur'}],
        redisKey: [{trigger: 'blur', validator: validateUnitName}]
      },
      databases: [],
    }
  },
  created() {
    for (let i = 0; i <= 15; i++) {
      this.databases.push({
        label: '数据库 - ' + i,
        value: i
      })
    }
  },
  mounted() {
    TableHeight.init(this)
    this.query(this.queryParam)
  },
  methods: {
    search(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.searchDisabled = true
          this.queryParam.pageInfo.pageNum = 1
          this.query(this.queryParam)
          this.searchDisabled = false
          this.$message({type: "success", message: '查询成功', duration: 1000})
        } else {
          return false
        }
      })
    },
    query(param) {
      this.tableLoading = true
      redisEditMonitor.queryPage(param).then(res => {
        if (res.code === 200) {
          this.tableData = res.data.list
          this.queryParam.pageInfo = res.data.list.length
          this.tableLoading = false
        } else {
          this.$message({type: "warning", message: res.msg, duration: 1000})
        }
      }).catch(() => {
      }).finally(() => this.tableLoading = false)
    },
    flushDb(database) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        debugger
        redisEditMonitor.flushDb({database: database}).then(res => {
          if (res.code === 200) {
            this.$message({type: "success", message: "删除成功", duration: 1000})
            this.query(this.queryParam)
          } else {
            this.$message({type: "warning", message: res.msg, duration: 1000})
          }
        }).catch(() => {
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      })
    },
    updateOne(row) {

    },
    removeOne(row) {

    },
    reset(formName) {
      this.$refs[formName].resetFields()
    },
    handleCurrentChange(val) {
      this.queryParam.pageInfo.pageNum = val
      this.query(this.queryParam)
    },
    handleSizeChange(val) {
      this.queryParam.pageInfo.pageSize = val
      this.query(this.queryParam)
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    }
  }
}
</script>

<style scoped>

</style>
