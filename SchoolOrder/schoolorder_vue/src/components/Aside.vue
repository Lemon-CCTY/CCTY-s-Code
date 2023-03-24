<template>
  <!--  侧边栏代码-->
  <el-menu :unique-opened="true" style="min-height: 100vh;"
           background-color="rgb(48 65 86)" width="150px" text-color="#fff" active-text-color="#ffd04b"
           :collapse-transition="true"
           :collapse="isCollapse"
           router
           :default-active="$route.path"
  >
    <!--    上面router路由跳转-->
    <div style="height:60px;line-height:60px;text-align:center;position:relative;top: 5px;" class="mr-5">
      <img src="@/assets/logo.png" alt="" style="width: 20px;"/>
      <b style="color:white;" v-show="!isCollapse"><a>后台管理系统</a></b>
    </div>


    <el-menu-item index="/Home">
      <template><i class="el-icon-s-home"></i>
        <span slot="title">首页</span>
      </template>
    </el-menu-item>
    <el-menu-item index="/consumer_manage">
      <i class="el-icon-s-custom"></i>
      <span slot="title">消费者管理</span>
    </el-menu-item>
    <el-submenu index="1">
      <template slot="title"><i class="el-icon-user-solid"></i>
        <span slot="title">商家管理</span>
      </template>

      <el-menu-item index="seller_approved"><i class="el-icon-user-solid"></i>已审核</el-menu-item>
      <el-menu-item index="sellers_unaudited"><i class="el-icon-user"></i>未审核</el-menu-item>

    </el-submenu>
    <el-submenu index="2">
      <template slot="title"><i class="el-icon-dish"></i>
        <span slot="title">食堂管理</span>
      </template>

      <el-menu-item index="hongyi_goods"><i class="el-icon-tableware"></i>弘毅园</el-menu-item>
      <el-menu-item index="kaiwu_goods"><i class="el-icon-food"></i>开物园</el-menu-item>

    </el-submenu>
    <el-menu-item index="/order_manage">
      <i class="el-icon-s-order"></i>
      <span slot="title">订单管理</span>
    </el-menu-item>

    <el-menu-item index="/evaluations_manage">
      <i class="el-icon-chat-dot-square"></i>
      <span slot="title">评价管理</span>
    </el-menu-item>
  </el-menu>

</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean,
  },

}
</script>

<style scoped>

</style>
