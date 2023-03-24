<template>
  <div>
    <!-- 新增、批量删除、按钮 -->
    <div style="margin: 10px 0;">

      <!-- 搜索框 -->
      <!-- 样式在globle.css全局变量中已配置（globle.css在main.js文件已配置），只需在配置中写入所想要样式的class名 -->
      <div style="position: relative;top: -10px;left: 4px">
        <el-input style="width: 200px;" v-model="name" placeholder="请输入名称" suffix-icon="el-icon-search">
        </el-input>
        <!-- 搜索、重置按钮 -->
        <el-button class="ml-5" type="primary" @click="load()">搜索</el-button>
        <el-button class="ml-5" type="warning" @click="reset()">重置</el-button>

      </div>
      <div>
        <!--        文件上传-->
        <el-upload
          :action="'http://'+serverIP+':8888/file/upload'"
          style="display: inline-block"
          :show-file-list="false"
          :on-success="handelFileUploadSuccess"
        >
          <el-button type="primary" class="ml-5">上传文件
            <i class="el-icon-upload"></i>
          </el-button>
        </el-upload>
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
      </div>
    </div>
    <el-table :data="tableData" stripe border style="" @selection-change="handleSelectionChange">
      <!--      批量删除复选框 只需加个type属性 type="selection"-->
      <el-table-column type="selection" width="45">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="140">
      </el-table-column>
      <el-table-column prop="name" label="文件名称" width="140">
      </el-table-column>
      <el-table-column prop="type" label="文件类型" width="120">
      </el-table-column>
      <el-table-column prop="size" label="文件大小（kb）">
      </el-table-column>
      <el-table-column label="下载">
        <template slot-scope="scope">
          <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column label="启用">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc"
                     @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <!-- 操作栏 -->
      <el-table-column label="操作">
        <template slot-scope="scope">
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
  </div>
</template>

<script>
import {serverIP} from "../assets/config";

export default {
  name: "File",
  data() {
    return {
      serverIP:serverIP,
      tableData: [],
      multipleSelection: [],//批量删除存储的数组
      pageNum: 1,//初始页码
      pageSize: 10,//每页记录个数
      total: 0,
      name: '',
    }
  }, created() {
    this.load()

  }, methods: {
    //封装数据请求方法
    load() {
      //请求分页数据
      this.request.get("/file/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        // console.log(res);
        this.tableData = res.data.records
        this.total = res.data.total
      })
      this.dialogFormVisible = false
    },
    //重置方法
    reset() {
      this.name = ""
      this.load()
    },
    //删除
    del(id) {
      this.request.delete("/file/" + id).then(res => {
        if (res.code === '200') {
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
      this.multipleSelection = val
    },
    //批量删除
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)//map()把对象数组变成纯id数组 例、{}，{}，{} =>[1,2,3]
      this.request.delete("/file/del/batch", {data: ids}).then(res => {
        if (res.code === '200') {
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
    handelFileUploadSuccess() {
      this.load()
    },
    download(url) {
      window.open(url)
    },
    changeEnable(row) {
      this.request.post("/file/update", row).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功！！")
        }
      })
    },


  }
}
</script>


<style scoped>

</style>
