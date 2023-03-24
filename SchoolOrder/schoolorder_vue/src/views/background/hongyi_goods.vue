<template>
  <div>
    这是弘毅园食堂
  </div>
</template>

<script>
import {serverIP} from "../../assets/config";

export default {
  name: "hongyi_goods",
  data() {

    return {
      serverIP: serverIP,
      tableData: [],
      total: 0,
      consumerId: '',
      consumerName: '',
      phone: '',
      address: '',
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
      this.request.get("/consumer/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          consumerName: this.consumerName,
          phone: this.phone,
          address: this.address
        }
      }).then(res => {
        // console.log(res);
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
      this.$refs["consumersForm"].validate((valid) => {
        if (valid) {
          this.request.post("/consumer", this.form).then(res => {
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
      this.request.delete("/consumer/" + id).then(res => {
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
      this.request.delete("/consumer/del/batch", {data: ids}).then(res => {
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
      window.open(`http://${serverIP}:8888/consumer/export`)
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
