<template>

  <div id="app">
    <div class="logindata">
      <div class="logintext">
        <h2>注册</h2>
      </div>
<!--      <div style="color: #606266;font-size: 12px">仅商家注册</div>-->
      <div class="formdata">
        <el-form :model="admin" :rules="rules" ref="adminForm">
          <el-form-item prop="adminId">
            <el-input
              prefix-icon="el-icon-user"
              v-model="admin.adminId"
              clearable
              placeholder="请输入账号"
            ></el-input>
          </el-form-item>
          <el-form-item prop="AdminPwd">
            <el-input
              prefix-icon="el-icon-lock"
              v-model="admin.adminPwd"
              clearable
              placeholder="请输入密码"
              show-password
            ></el-input>
          </el-form-item>
          <el-form-item prop="confirmAdminPwd">
            <el-input
              prefix-icon="el-icon-lock"
              v-model="admin.confirmAdminPwd"
              clearable
              placeholder="请确认密码"
              show-password
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="tool">
<!--        <el-radio v-model="radio" label="1">学生</el-radio>-->
<!--        <el-radio v-model="radio" label="2">教师</el-radio>-->
<!--        <el-radio v-model="radio" label="3">商家</el-radio>-->
<!--        <el-radio v-model="radio" label="4">管理员</el-radio>-->
      </div>
      <div class="butt">
        <el-button type="primary"
                   @click="register">注册
        </el-button>
        <el-button class="shou">
          <span @click="returnToLogin">返回登录</span>
          </el-button>
      </div>

    </div>
    <vue-particles
      color="#dedede"
      :particleOpacity="0.7"
      :particlesNumber="80"
      shapeType="circle"
      :particleSize="4"
      linesColor="#dedede"
      :linesWidth="1"
      :lineLinked="true"
      :lineOpacity="0.4"
      :linesDistance="150"
      :moveSpeed="3"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="push"
      style="width: 100%"
    />


  </div>
</template>

<script>
export default {
  name: "register",
  data() {
    return {
      admin: {
        adminId: "",
        adminPwd: "",
        confirmAdminPwd:"",
        // avatarUrl:''
      },
      radio: '1',
      rules: {
        //userId和userPwd跟el-input的prop属性对应
        adminId: [
          {required: true, message: "请输入用户名", trigger: "blur"},
          {min: 3, max: 10, message: "用户名长度在3到10之间", trigger: "blur"},
        ],
        adminPwd: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {min: 6, max: 12, message: "长度在6到12之间", trigger: "blur"},
        ],
        confirmAdminPwd: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {min: 6, max: 12, message: "长度在6到12之间", trigger: "blur"},
        ],
      },
    };
  },
  methods: {
    register() {
      /*登录前校验，不提交数据到后台
      * valid是个回调函数，校验表单。如果valid是true，则合法，提交表单到后台
      *     本身校验表单写法this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
            * 这里面使用后台接口，写了登录方法
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      * */
      this.$refs["adminForm"].validate((valid) => {
        if (valid) {
          if (this.admin.password!==this.admin.confirmAdminPwd){
            this.$message.error("两次输入的密码不一致")
          }
          this.request.post("/admin/register", this.admin).then(res => {
            console.log(this.admin)
            if (res.code == '200') {
              this.$message.success("注册成功！")
            } else {
              this.$message.error(res.msg)
            }
          })
        } else {
          return false;
        }
      });


    },
    returnToLogin(){
      this.$router.push("/login")
    }
  },
}


</script>

<style>


#app {
  width: 100%;
  height: 699px;
  overflow: hidden;
}

.logindata {
  width: 400px;
  height: 200px;
  transform: translate(-50%);
  margin-left: 50%;
  position: absolute;
  top: 150px;

}

.logintext {
  margin-bottom: 20px;
  line-height: 50px;
  text-align: center;
  font-size: 30px;
  font-weight: bolder;
  color: white;
  text-shadow: 2px 2px 4px #000000;
}


.tool {
  display: flex;
  justify-content: space-between;
  color: #606266;
}

.butt {
  margin-top: 10px;
  text-align: center;

}

.shou {
  cursor: pointer;
  color: #606266;
}
</style>
