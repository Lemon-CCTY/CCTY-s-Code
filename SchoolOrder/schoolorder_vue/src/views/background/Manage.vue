<template>
  <!--该页面用于管理所有页面-->
  <div class="container" >
    <el-container style="min-height: 100%">
      <el-aside :width="sidewidth+'px'" style="background-color:rgb(48 65 86);">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"/>
      </el-aside>

      <el-container>
        <!--       头部代码 -->
        <el-header>
          <Head :collapseBtnClass="collapseBtnClass" :collapse="collapse"/>
        </el-header>

        <!--中心代码-->
        <el-main>
          <!--  router-view：其他页面放置其中-->
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from "@/components/Aside";
import Head from "@/components/Head";

export default {
  name: "Manage",
  components: {
    Aside,
    Head,
  },
  data() {

    return {
      // sideWidth:200,
      collapseBtnClass: "el-icon-s-fold",
      isCollapse: false,
      logoTextShow: true,
    }
  },
  methods: {
    // 折叠框触发收缩按钮
    collapse() {
      this.isCollapse = !this.isCollapse
      if (this.collapse) {
        this.sideWidth = 150,
          this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {
        // this.sideWidth = 100,
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },

  }
}
</script>

<style scoped>
.el-header {
  background-color: rgb(255, 255, 255);
  color: rgb(101, 101, 101);
  line-height: 60px;
}

.el-aside {
  color: white;
}

el-submenu el-menu-item {
  min-width: 0;
}

body {
  margin: 0;
  padding: 0;
}

/* 加过渡给侧边导航*/
.el-aside {
  transition: width 0.25s;
  -webkit-transition: width 0.25s;
  -moz-transition: width 0.25s;
  -webkit-transition: width 0.25s;
  -o-transition: width 0.25s;
}

/*加快侧边栏文字消失的速度*/
.el-menu {
  transition: all 5ms;
}
</style>
