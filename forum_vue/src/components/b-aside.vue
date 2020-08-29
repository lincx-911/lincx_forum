<template>
  <el-row >
  <el-col :span="12">
    
    <!-- <router-link to='/post'><el-button  type="primary" size="medium" style="width:150px">发布帖子</el-button></router-link> -->
    <el-button @click="drawer = true" type="primary" size="medium" style="width:150px">发布帖子</el-button>
    <el-drawer
      size="40%"
      :visible.sync="drawer"
      :direction="direction">
      <b-editor></b-editor>
    </el-drawer>
    <el-menu
      :default-active="nowurl"
      class="el-menu-vertical-demo"
      router
      >
     <el-menu-item index="/">
        <i class="el-icon-news"></i>
        <span slot="title">全部帖子</span>
      </el-menu-item>
      <el-menu-item index="/cgies">
        <i class="el-icon-menu"></i>
        <span slot="title">帖子分类</span>
      </el-menu-item>
     
      <el-menu-item index="/focus">
        <i class="el-icon-star-on"></i>
        <span slot="title">我的关注</span>
      </el-menu-item>
      <el-menu-item index="#">
        <i class=""></i>
        <span slot="title" class="rank-title">帖子分区</span>
      </el-menu-item>
      <el-menu-item index="/bkl">
        <i class="el-icon-s-comment"></i>
        <span slot="title">知识讨论</span>
      </el-menu-item>
      <el-menu-item index="/baq">
        <i class="el-icon-s-opportunity"></i>
        <span slot="title">难题提问</span>
      </el-menu-item>
      <el-menu-item index="/bsf">
        <i class="el-icon-share"></i>
        <span slot="title">分享发现</span>
      </el-menu-item>
            <el-menu-item index="/bws">
        <i class="el-icon-s-marketing"></i>
        <span slot="title">就业升学</span>
      </el-menu-item>
    </el-menu>
  </el-col>
</el-row>
</template>
<script>
export default {
    data(){
      return{
        nowurl:'',
        content: "",
        drawer:false,
        direction:'rtl'
      }
    },
    created(){
      this.nowurl = this.$route.path;
     
    },
    methods: {
      
    }
}
</script>
<style scoped>
@import '../assets/css/style.css';
 a {text-decoration: none}
</style>