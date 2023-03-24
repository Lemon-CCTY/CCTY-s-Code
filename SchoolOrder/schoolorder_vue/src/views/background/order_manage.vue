<template>
  <div>
    <!--    订单管理-->
    <div>
      <!-- 搜索框 -->
      <!-- 样式在globle.css全局变量中已配置（globle.css在main.js文件已配置），只需在配置中写入所想要样式的class名 -->
      <div>
<!--        <el-input style="width: 200px;" v-model="consumerName" placeholder="请输入顾客名称"-->
<!--                  suffix-icon="el-icon-search">-->
<!--        </el-input>-->
        <el-input style="width: 200px;" v-model="orderId" placeholder="请输入订单号" suffix-icon="el-icon-phone">
        </el-input>


        <!-- 搜索、重置按钮 -->
        <el-button class="ml-5" type="primary" @click="load()">搜索</el-button>
        <el-button class="ml-5" type="warning" @click="reset()">重置</el-button>
      </div>
      <!-- 新增、批量删除、按钮 -->
      <div style="margin: 10px 0;">
        <!--批量删除-->
        <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='取消'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些用户吗？"
          @confirm="delBatch"
        >
          <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove"></i></el-button>
        </el-popconfirm>

        <!-- 导入
        accept  接受上传的文件类型
        show-file-list	是否显示已上传文件列表
        on-success	文件上传成功时的钩子-->
        <el-upload
          :action="'http://'+serverIP+':8888/consumer/import'"
          style="display: inline-block"
          :show-file-list="false"
          accept="xlsx"
          :on-success="handelExcelImportSuccess"
        >
          <el-button type="primary" class="ml-5">导入
            <i class="el-icon-bottom"></i>
          </el-button>
        </el-upload>

        <el-button type="primary" @click="exp">导出<i class="el-icon-upload2"></i></el-button>
      </div>
      <el-table :data="tableData" stripe border style="" @selection-change="handleSelectionChange">
        //批量删除复选框 只需加个type属性 type="selection"
        <el-table-column type="selection" width="45">
        </el-table-column>
        <el-table-column prop="orderId" label="订单号" width="80">
        </el-table-column>
        <el-table-column prop="address" label="收获地址" :show-overflow-tooltip="true">
          <!--          able表格内容超出隐藏显示省略号  :show-overflow-tooltip="true"-->
        </el-table-column>
        <el-table-column prop="phone" label="下单手机号" width="86">
        </el-table-column>
        <el-table-column prop="paidTime" label="下单时间" width="150">
        </el-table-column>
        <el-table-column prop="arrivedTime" label="到达时间" width="150">
        </el-table-column>
        <el-table-column prop="consumerName" label="下单用户" width="80" :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column prop="sellerName" label="商家名" width="65" :show-overflow-tooltip="true">
        </el-table-column>
        <!-- 操作栏 -->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary">查看评价</el-button>
            <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
            <!--                删除提示框-->
            <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除该用户信息吗？"
              @confirm="del(scope.row.id)"
            >
              <el-button type="danger" slot="reference">删除<i class="el-icon-remove"></i></el-button>
            </el-popconfirm>

          </template>
        </el-table-column>
      </el-table>
      <!-- 分页功能 -->
      <div style="padding:10px 0;">
        <el-pagination @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="pageNum"
                       :page-sizes="[5, 10, 15, 20]"
                       :page-size="pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="total">
        </el-pagination>
      </div>
      <!--          新增、修改对应弹窗窗口-->
      <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" size="small">
        <el-form :model="form" label-width="80px" :rules="rules" ref="orderForm">
          <el-form-item label="学号" prop="consumerId">
            <el-input v-model="form.consumerId" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户名" prop="consumerName">
            <el-input v-model="form.consumerName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="form.phone" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="地址" prop="address">
            <el-input v-model="form.address" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="load">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>

</template>

<script>
import {serverIP} from "../../assets/config";

export default {
  name: "order_manage",
  data() {

    return {
      serverIP: serverIP,
      tableData: [],
      total: 0,
      orderId: '',
      dialogFormVisible: false,//弹窗关闭
      form: {},//新增表单form对象
      multipleSelection: [],//批量删除存储的数组
      pageNum: 1,//初始页码
      pageSize: 10,//每页记录个数
      rules: {
        //userId和userPwd跟el-input的prop属性对应
        consumerId: [
          {required: true, message: "请输入学号/工号", trigger: "blur"},
          {min: 3, max: 10, message: "学号/工号长度在3到10之间", trigger: "blur"},
        ],
        consumerName: [
          {required: true, message: "请输入名称", trigger: "blur"},
          {min: 2, max: 10, message: "用户名长度在2到10之间", trigger: "blur"},
        ],
        phone: [
          {required: true, message: "请输入手机号", trigger: "blur"},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号", trigger: "blur"},

        ], address: [
          {required: true, message: "请输入地址", trigger: "blur"},
          // {min: 6, max: 12, message: "长度在6到12之间", trigger: "blur"},

        ],
      },
    }
  },
  created() {
    this.load();
  },

  methods: {
    // 折叠框触发收缩按钮
    collapse() {
      this.isCollapse = !this.isCollapse
      if (this.collapse) {
        this.sideWidth = 64,
          this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {
        // this.sideWidth = 100,
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },

    //封装数据请求方法
    load() {
      //请求分页数据
      this.request.get("/gorder/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          orderId: this.orderId,
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.records
        this.total = res.total
      })
      this.dialogFormVisible = false
    },
    //重置方法
    reset() {
      this.consumerName = ""
      this.phone = ""
      this.address = ""
      this.load()
    },
    //新增弹窗方法
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    //新增保存
    save() {
      this.$refs["orderForm"].validate((valid) => {
        if (valid) {
          this.request.post("/gorder", this.form).then(res => {
            if (res) {
              this.$message.success("保存成功！")
              this.dialogFormVisible = false
              this.load()
            } else {
              this.$message.error(res.msg)
            }
          })
        } else {
          return false;
        }
      });

    },
    //编辑
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));//scope.row获取数据
      this.dialogFormVisible = true
    },
    //删除
    del(id) {
      this.request.delete("/gorder/" + id).then(res => {
        if (res) {
          this.$message.success("删除成功！！")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("删除失败！")
        }
      })
    },
    //批量选择框
    handleSelectionChange(val) {
      // console.log(val)
      this.multipleSelection = val
    },
    //批量删除
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)//map()把对象数组变成纯id数组 例、{}，{}，{} =>[1,2,3]
      this.request.delete("/gorder/del/batch", {data: ids}).then(res => {
        if (res) {
          this.$message.success("删除成功！！")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("删除失败！")
        }
      })

    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize,
        this.load();
    }, handleCurrentChange(pageNum) {
      this.pageNum = pageNum,
        this.load();
    },
    //导出
    exp() {
      window.open(`http://${serverIP}:8888/order/export`)
    },
    //导入
    handelExcelImportSuccess() {
      this.$message.success("导入成功！")
      this.load();
    }
  }
}

</script>

<style scoped>

</style>
