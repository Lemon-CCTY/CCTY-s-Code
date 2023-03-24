<template>
  <!--头部代码-->
  <!--  <el-header style=" font-size: 13px;line-height:60px ;display: flex;">-->


  <div style=" font-size: 13px;line-height:60px ;display: flex;">
    <div style="flex:1;font-size: 18px;">
      <!-- 左滑图标设计 -->
      <span :class="collapseBtnClass" style="cursor: pointer;" @click="collapse"></span>
      <!-- 面包屑 -->
      <el-breadcrumb separator-class="el-icon-arrow-right" style="display: inline-block;margin-left: 10px">
        <el-breadcrumb-item v-for="breadCrumbItem in breadCrumbList" :key="breadCrumbItem.path"
                            :to="{path: breadCrumbItem.path}">
          {{ breadCrumbItem.meta.title }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-dropdown style="width:100px;cursor: pointer;">
      <div style="display: inline-block">
        <img :src="admin.avatarUrl"
             style="display:inline-block;width: 25px;border-radius: 50%;position: relative;top: 8px;">
        <span>{{ admin.adminName }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>

      <el-dropdown-menu slot="dropdown" style="margin-right: 15px;">
        <el-dropdown-item>
          <span @click="dialogImgVisible = true">个人信息</span>
        </el-dropdown-item>
        <el-dropdown-item>
          <span @click="dialogFormVisible = true">修改密码</span>
        </el-dropdown-item>
        <!--        <el-dropdown-item>修改密码</el-dropdown-item>-->
        <el-dropdown-item>
          <span @click="logout">退出登录</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>


    <div class="updateuserPwd">
      <!--    个人信息表单-->
      <el-dialog title="更换头像" :visible.sync="dialogImgVisible" width="30%" size="small">
        <el-form :model="admin" ref="AdminInfo" :rules="rules">
          <el-form-item label="用户头像" :label-width="formLabelWidth">

            <el-upload
              :action="'http://'+serverIP+':8888/file/upload'"
              style="display: inline-block"
              :show-file-list="false"
              :on-success="handelAvatarSuccess"
            ><img v-if="admin.avatarUrl" :src="admin.avatarUrl" style="display: inline-block;
            width: 48px;
            border-radius: 50%;
            position: absolute;
            top: -8px;
            ">
              <!--              <el-button type="primary" class="ml-5">上传头像-->
              <!--                <i class="el-icon-upload"></i>-->
              <!--              </el-button>-->
            </el-upload>
          </el-form-item>
          <el-form-item label="用户名" :label-width="formLabelWidth" >
            <el-input v-model="admin.adminName"></el-input>
          </el-form-item>
          <el-form-item label="手机号" :label-width="formLabelWidth">
            <el-input v-model="admin.phone"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogImgVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
      <!--修改密码表单-->
      <el-dialog title="个人信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form" :rules="rules" ref="AdminForm">
<!--          <el-form-item label="用户id" :label-width="formLabelWidth" >-->
<!--            <el-input v-model="this.admin.adminId" style="width: 270px;" autocomplete="off"  disabled></el-input>-->
<!--          </el-form-item>-->
          <el-form-item label="原密码" :label-width="formLabelWidth" prop="adminPwd">
            <el-input v-model="form.adminPwd" style="width: 270px;" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="新密码" :label-width="formLabelWidth" prop="newAdminbPwd">
            <el-input v-model="form.newAdminPwd" style="width: 270px;" show-password
                      autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" :label-width="formLabelWidth" prop="confirmUserPwd">
            <el-input v-model="form.confirmAdminPwd" style="width: 270px;" show-password
                      autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateUserPwd">确 定</el-button>
        </div>
      </el-dialog>
    </div>


  </div>

</template>

<script>
import {serverIP} from "../assets/config";

export default {
  name: "Head",
  props: {
    collapse: '',
    collapseBtnClass: String,

  },
  data() {
    return {
      serverIP:serverIP,
      formLabelWidth: '80px',
      dialogImgVisible: false,
      dialogFormVisible: false,
      form: {},
      adminId:'',
      adminName:this.admin.adminName,
      //密码修改校验规则
      rules: {
        //prop属性要对应
        adminPwd: [
          {required: true, message: '请输入原密码', trigger: 'blur'},
          {min: 6, max: 12, message: "长度在6到12之间", trigger: "blur"},
        ],
        newAdminPwd: [
          {required: true, message: "新密码不能为空", trigger: "blur"},
          {min: 6, max: 12, message: "长度在6到12之间", trigger: "blur"},
        ],
        confirmAdminPwd: [
          {required: true, message: "请再次新密码", trigger: "blur"},
          {min: 6, max: 12, message: "长度在6到12之间", trigger: "blur"},
        ],
        phone: [
          {required: true, message: "请输入手机号", trigger: "blur"},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号", trigger: "blur"}
]
      },
      admin: localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {}//将浏览器中登录传来的admin对象赋值
    }
  }, computed: {
    breadCrumbList() {
      return this.$route.matched;
    }
  },
  created() {
    // this.getUser()
  },
  mounted() {

  },
  methods: {
    getUser(){
      this.request.get("/admin/{adminId}"+this.admin.adminId).then(res=>{
        if (res.code==='200'){}
        this.form.adminId=this.admin.adminId
      })
    },
    //修改个人信息
    save() {
      this.$refs["AdminInfo"].validate((valid) => {
        if (valid) {  //合法
          this.form.adminId=this.admin.adminId
          this.request.post("/admin/updateAdminInfo", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("修改成功")
              this.dialogFormVisible = false
            } else if (res.code === '500') {
              this.$message.error("头像大小不能超过1048576 bytes")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    },
    //修改密码
    updateUserPwd() {
      this.$refs["AdminForm"].validate((valid) => {
        if (valid) {  //合法
          if (this.form.newAdminPwd !== this.form.confirmAdminPwd) {
            this.$message.error("两次输入的新密码不一致")
            return false
          }
          this.form.adminId=this.admin.adminId
          this.request.post("/admin/updateAdminPwd", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("修改成功")
              this.dialogFormVisible = false
              this.$store.commit("/logout")//重新登录
            } else {
              console.log(this.admin.adminId)
              this.$message.error(res.msg)
            }
          })
        }
      });
    },
    //退出
    logout() {
      this.$router.push("/login")
      this.$message.success("退出成功！")
      localStorage.removeItem("admin")//用来清除本地数据
    },
    //个人信息修改
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {
        });
    },
    handelAvatarSuccess(res) {
      this.admin.avatarUrl = res
    }
  }
}
</script>

<style scoped>
.dialog-footer {
  /*position: relative;*/
  /*left: -460px;*/
}

.updateInfo {
  /*width: 500px;*/
}
</style>
