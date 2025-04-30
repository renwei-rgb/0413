<template>
  <div class="pro-container">
    <div>
      <el-table :data="tableData" border fit highlight-current-row style="width: 100%">
        <!-- <el-table-column prop="number" label="任务编号"></el-table-column> -->
        <el-table-column prop="name" label="任务名称"></el-table-column>
        <el-table-column prop="applyName" label="申请人姓名"></el-table-column>
        <el-table-column prop="applyTime" label="申请时间"></el-table-column>
        <el-table-column prop="statusName" label="审批状态"
          :filters="[{ text: '待审批', value: '待审批' }, { text: '已通过', value: '已通过' }, { text: '已驳回', value: '已驳回' }]"
          :filter-method="filterTag" filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag :type="scope.row.statusName === '待审批' ? 'danger' : 'success'"
              disable-transitions>{{ scope.row.statusName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="190" fixed="right">
          <template slot-scope="scope">
            <el-button type="warning" @click="handleUpdate(scope.row)"
              v-if="scope.row.statusName === '待审批' ? true : false"> 审批任务</el-button>
            <el-button type="success" @click="handleUpdate(scope.row)" v-else> 任务详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
      <el-form ref="tempData" :model="form" label-position="left" label-width="120px"
        style="width: 420px; margin-left:50px;">
        <el-form-item label="申请人">
          <el-input v-model="form.applyName" readonly />
        </el-form-item>
        <el-form-item label="申请时间">
          <el-input v-model="form.applyTime" readonly />
        </el-form-item>
        <el-form-item label="申请类型">
          <el-input v-model="form.type" readonly />
        </el-form-item>
        <el-form-item label="开始时间" v-if="isLeave">
          <el-input v-model="form.beginTime" readonly />
        </el-form-item>
        <el-form-item label="结束时间" v-if="isLeave">
          <el-input v-model="form.endTime" readonly />
        </el-form-item>
        <el-form-item label="时长" v-if="isLeave">
          <el-input v-model="form.duration" readonly />
        </el-form-item>
        <el-form-item label="请假事由" v-if="isLeave">
          <el-input type="textarea" v-model="form.reason" readonly />
        </el-form-item>
        <el-form-item label="固定资产编号" v-if="isFixed">
          <el-input v-model="form.number" readonly />
        </el-form-item>
        <el-form-item label="固定资产名称" v-if="isFixed">
          <el-input v-model="form.name" readonly />
        </el-form-item>
        <el-form-item label="价格" v-if="isFixed">
          <el-input v-model="form.price" readonly />
        </el-form-item>
        <el-form-item label="审批人" v-if="isApproval">
          <el-input v-model="form.approvalName" readonly />
        </el-form-item>
        <el-form-item label="审批时间" v-if="isApproval">
          <el-input v-model="form.approvalTime" readonly />
        </el-form-item>
        <el-form-item label="审批结果">
          <el-input v-model="form.status" readonly />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="approval('yes')" v-if="isShow"> 同 意</el-button>
        <el-button type="danger" @click="approval('no')" v-if="isShow"> 驳 回</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      tableData: [],
      dialogFormVisible: false,
      dialogTitle: '',
      form: {},
      form1: {},
      isLeave: false,
      isFixed: false,
      isShow: false,
      isApproval: true,
    }
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      axios.post("/task/list", {
        receiveNumber: sessionStorage.getItem("username")
      }).then(res => {
        if(!document.cookie.split(';').some(c => c.trim().startsWith('LOGIN='))) {
          
          this.$router.push("/");
          this.$message({ message: "未登录或登录信息过期", type: "error" });
          return
        } 
        this.tableData = res.data;
      })
    },
    handleUpdate(row) {
      if (row.statusName == '待审批') {
        this.isShow = true
        this.isApproval = false
      } else {
        this.isShow = false
        this.isApproval = true
      }
      this.form1 = Object.assign({}, row)
      axios.post("/task/findByApplyID", this.form1).then(res => {

        this.form = res.data;
        if (this.form.status == "1") {
          this.form.status = "已通过"
        } else if (this.form.status == "2") {
          this.form.status = "已驳回"
        }
        this.dialogTitle = "审批任务"
        this.dialogFormVisible = true
        if (res.data.taskTypeID == 1) {
          this.isFixed = false;
          this.isLeave = true;
        } else if (res.data.taskTypeID == 2) {
          this.isLeave = false;
          this.isFixed = true;
        }
      })
    },
    approval(advice) {
      this.form1.advice = advice;
      this.form1.approvalTime = this.getTime();
      this.form1.approvalNumber = sessionStorage.getItem("username");
      //this.form1 = Object.assign({advice: advice}, this.form1)
      axios.post("/task/approval", this.form1).then(res => {
        if (res.data.code == 200) {
          this.dialogFormVisible = false
          this.$message({ message: "审批成功", type: "success" });
          this.getData();
        }
      })
    },
    getTime() {
      var date = new Date();
      var seperator1 = "-";
      var seperator2 = ":";
      //以下代码依次是获取当前时间的年月日时分秒
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      var minute = date.getMinutes();
      var hour = date.getHours();
      var second = date.getSeconds();
      //固定时间格式
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
      }
      if (minute >= 0 && minute <= 9) {
        minute = "0" + minute;
      }
      if (second >= 0 && second <= 9) {
        second = "0" + second;
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate
        + " " + hour + seperator2 + minute + seperator2 + second;
      return currentdate;
    },
    filterTag(value, row) {
      return row.statusName === value;
    },
  }
}
</script>

<style>
.pro-container {
  padding: 15px 32px;
  margin: 4px 2px;
}
</style>
