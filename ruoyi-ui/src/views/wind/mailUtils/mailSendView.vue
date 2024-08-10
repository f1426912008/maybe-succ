<template>
  <el-container>
    <el-main>
      <wind-row-query>
        <el-form
          ref="queryRef"
          :model="queryParam.data"
          inline
          style="border-bottom: 1px solid #dcdfe6"
        >
          <el-form-item label="模板名称" prop="tempName">
            <el-input
              v-model="queryParam.data.tempName"
              placeholder="请输入模板名称"
              clearable
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading="searchDisabled" @click="search('queryRef')">查 询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button @click="reset('queryRef')">重 置</el-button>
          </el-form-item>
        </el-form>
      </wind-row-query>
      <wind-row-option>
        <el-button type="primary">定时发送</el-button>
        <el-button type="primary" @click="sendByCustom">自定义发送</el-button>
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
          <el-table-column prop="tempName" label="模板名称" align="center" min-width="150" show-overflow-tooltip/>
          <el-table-column prop="subject" label="邮件主题" align="center" min-width="150" show-overflow-tooltip/>
          <el-table-column prop="content" label="正文内容" align="center" min-width="260" show-overflow-tooltip/>
          <el-table-column label="是否启用" align="center" min-width="100">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.isEnabled" @change="enabledChange(scope.row)"/>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" min-width="100">
            <template slot-scope="scope">
              <el-tooltip content="编 辑" placement="top">
                <el-button
                  size="mini"
                  type="primary"
                  icon="el-icon-edit"
                  @click="updateOne(scope.row)"
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
        title="写邮件"
        :visible.sync="customDialog"
        width="60%"
        :before-close="handleClose"
      >
        <el-form
          ref="mailInfo"
          style="margin-left: 2%;margin-right: 2%"
          label-position="right"
          label-width="auto"
          :model="mailInfo"
          :rules="mailInfoRules"
        >
          <el-form-item label="收件人" prop="sendTo">
            <el-input
              style="width: 80%"
              v-model="mailInfo.sendTo"
              placeholder="请输入收件人"
              clearable
            />
            <el-button style="width: 18%;margin-left: 2%" type="primary" @click="selectContact">选择收件人</el-button>
          </el-form-item>
          <el-form-item label="主 题" prop="subject">
            <el-input
              v-model="mailInfo.subject"
              placeholder="请输入主题"
              clearable
            />
          </el-form-item>
          <el-form-item label="正 文" prop="content">
            <editor
              :height="200"
              v-model="mailInfo.content"
            />
          </el-form-item>
          <el-form-item label="附 件" prop="attachment">
            <el-upload
              action=""
              :file-list="mailInfo.attachment"
              :limit="1"
            >
              <el-button size="small" type="primary">点击上传</el-button>
              <span style="margin-left: 2%" slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</span>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" style="text-align: center">
          <el-button @click="handleClose">取 消</el-button>
          <el-button type="primary" @click="send">发 送</el-button>
        </div>
      </el-dialog>
      <el-dialog
        title="联系人"
        :visible.sync="contactDialog"
        width="50%"
        style="margin-top: 5vh"
      >
        <el-table
          :data="contactData"
          v-loading="contactLoading"
          highlight-current-row
          :row-style="{height: '40px'}"
          max-height="400"
          height="400"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" align="center" width="55"/>
          <el-table-column prop="contactName" label="名称" align="center" min-width="80"/>
          <el-table-column prop="mailAddr" label="邮箱地址" align="center" min-width="100"/>
          <el-table-column prop="mobilePhone" label="手机号" align="center" min-width="100"/>
          <el-table-column prop="groupName" label="分组" align="center" min-width="80"/>
          <el-table-column prop="remark" label="备注" align="center" min-width="150" show-overflow-tooltip/>
          <el-table-column prop="isCollect" label="收藏" align="center" min-width="50"/>
        </el-table>
        <div slot="footer" style="text-align: center">
          <el-button @click="contactDialog = false">取 消</el-button>
          <el-button type="primary" @click="submitSelect">确 定</el-button>
        </div>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>
import TableHeight from "@/api/TableHeight";
import mailSendApi from "@/api/wind/mailUtils/MailSendApi";

export default {
  name: "mailSendView",
  mounted() {
    TableHeight.init(this)
  },
  data() {
    const regex = /[^0-9@()（）a-zA-Z\u4E00-\u9FA5]/g
    const validateSendName = (rule, value, callback) => {
      if (!value) {
        callback()
      } else {
        if (!regex.test(value)) {
          callback()
        } else {
          callback('只能输入中文、英文、括号、@号、数字')
        }
      }
    }
    return {
      fixed: false,
      document: document,
      tableHeight: null,
      customDialog: false,
      contactDialog: false,
      tableLoading: false,
      contactLoading: false,
      searchDisabled: false,
      multipleSelection: [],
      contactData: [],
      tableData: [
        {
          tempName: '测试',
          subject: '测试主题',
          content: '测试正文内容',
          isEnabled: true,
        },
        {
          tempName: '测试1',
          subject: '测试主题1',
          content: '测试正文内容1',
          isEnabled: false,
        }
      ],
      queryParam: {
        data: {
          tempName: '',
        },
        pageInfo: {
          pageNum: 1,
          pageSize: 10,
          total: 0,
          pageSizes: [10, 20, 30, 50, 100]
        }
      },
      mailInfo: {
        sendTo: '',   // 收件人
        subject: '',  // 主题
        content: '',   // 正文
        attachment: null    // 附件
      },
      mailInfoRules: {
        // sendTo: [{trigger: 'change', validator: validateSendName}]
      }
    }
  },
  methods: {
    search(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          this.searchDisabled = true
          this.queryParam.pageInfo.pageNum = 1
          await this.query(this.queryParam)
          this.searchDisabled = false
          this.tableLoading = false
          this.$message({type: "success", message: '查询成功', duration: 1000})
        } else {
          return false
        }
      })
    },
    query(param) {
      this.tableLoading = true
      this.tableData.push({
        tempName: '测试' + this.tableData.length,
        subject: '测试主题' + this.tableData.length,
        content: '测试正文内容' + this.tableData.length,
        isEnabled: this.tableData.length % 2 === 0,
      })
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
    updateOne(row) {
      this.$message({type: "success", message: '已更新', duration: 1000})
    },
    removeOne(row) {
    },
    enabledChange(row) {
      this.updateOne(row)
    },
    sendByCustom() {
      this.customDialog = true
    },
    handleClose() {
      this.reset('mailInfo')
      this.customDialog = false
    },
    send() {
      this.$refs['mailInfo'].validate((valid) => {
        if (valid) {
          console.log(this.mailInfo)
          mailSendApi.sendMail(this.mailInfo).then(res => {
            if (res.code === 200) {
              this.$message({type: "success", message: res.msg, duration: 2000})
            }
          }).catch()
          this.handleClose()
        } else {
          return false
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val.map(item => item.mailAddr)
    },
    submitSelect() {
      this.contactDialog = false
      this.mailInfo.sendTo = this.multipleSelection.join(";")
    },
    selectContact() {
      this.contactDialog = true
      mailSendApi.contactList().then(res => {
        if (res.code === 200) {
          this.contactData = res.data
        }
      }).catch()
    },
  }
}
</script>

<style scoped>

</style>
