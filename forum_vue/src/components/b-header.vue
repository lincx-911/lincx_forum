<template>
<div class="header-root">
<div id="nav" class="header-div-list" :class="{'fixed-top': navBarFixed}">
      <div class="headerfirst">
          <div style="margin-top:5px;">
        <a href="/" ><b><i class="el-icon-eleme" /> LINCX</b></a>
        </div>
        </div>
        <div class="headlist">
              <a href="/article/13">
                <i class="el-icon-edit"></i>
            使用指南
              </a>
       <a href="">
              <i class="el-icon-user-solid"></i>
        <span>社区规范</span>
       </a>
        
  

        <a href="">
             <i class="el-icon-s-management"></i>
         学习资源
        </a>

           <a href="">
             <i class="el-icon-info"></i>
         关于本站
           </a>
        </div>
         <div class="headersearch">
           <div class="seachdiv">
            <el-input v-model="search" size="small" placeholder="搜索" clearable name="search"  @input="searchEvent" @click="changeVisible"></el-input>
            <el-popover
              placement="bottom"
              width="300"
              trigger="manual"
              v-model="visible">
              <span class="searchli" style="font-size:16px">共找到{{searchlen}}记录</span>
              <div v-if="searchlen!=0">
                <div v-for="(article,index) in searchlist" :key="index" class="searchli" style="text-alian:left">
                  <div v-on:click="directto(article.id)">{{article.title}}</div>
                </div>
              </div>
              <div v-else @click="changeVisible">无记录</div>
              </el-popover>
           </div>
        

  <el-dropdown trigger="click" style="margin-top:5px;margin-left: 10px;">
  <span class="el-dropdown-link">
    <i class="el-icon-message-solid"></i>
  </span>
  <el-dropdown-menu slot="dropdown">
    <el-dropdown-item class="clearfix">
      评论
      <el-badge class="mark" :value="12" />
    </el-dropdown-item>
    <el-dropdown-item class="clearfix">
      回复
      <el-badge class="mark" :value="3" />
    </el-dropdown-item>
  </el-dropdown-menu>
</el-dropdown>
         </div>

    <div class="headerloginavatar">

      <div v-if="hasnotoken">
      <el-button  type="primary" size="small" @click="login"><i class="el-icon-upload"></i>登录</el-button>
      <el-button size="small"  @click="register">
        <i class="el-icon-edit" ></i> 注册
      </el-button>
      <el-dialog
    :visible.sync="centerDialogVisible"
    width="20%"
    center>
    <b-login @func="getLoginMsg" v-if="loginis" ></b-login>
    <b-register @funcre="getRegistMsg" v-if="registeris"></b-register>
    <b-forget v-if="forgetis"></b-forget>
    <div class="foot-dialog" v-if="loginis">
      <span style="color:blue;margin-bottom:10px;" @click="forget">忘记密码？</span>
      <span>还没有账户？<span style="color:blue" @click="register">立即注册</span></span>
    </div>
    <div class="foot-dialog" v-if="registeris">
      <span>已有账户？<span style="color:blue" @click="login">登陆</span></span>
    </div>
    <div class="foot-dialog" v-if="forgetis">
      <span style="color:blue" @click="login">返回首页</span>
    </div>
  </el-dialog>
      </div>
      
      <div v-else >
        <el-popover
        width="80"
        trigger="click">
          <div class="popover-div">
            <span class="back"><i class="el-icon-user-solid"></i><a :href="userurl">个人主页</a></span>
            <span class="popover-span" @click="loadout"><i class="el-icon-error"></i>注销</span>
          </div>
        <div slot="reference" class="ava-name">
        
        <div class="img-div"><img :src="avatar"></div>
        <span style="margin-left:10px;">{{user.username}}</span>
        </div>
        </el-popover>
        
      </div>
    </div>
    

</div>
</div>
</template>

<script>
import Cookie from '@/utils/cookie'
import Search from '@/utils/search'
import api from '@/api/api'
import Qs from 'qs'
// 节流函数
const delay = (function() {
  let timer = 0;
  return function(callback, ms) {
    clearTimeout(timer);
    timer = setTimeout(callback, ms);
  };
})();
export default {
     inject: ['reload'],
    name : 'bHeader',
    data(){
        return {
          centerDialogVisible: false,
          navBarFixed: false,
          search: '',
          loginis:true,
          forgetis:false,
          registeris:false,
          hasnotoken:true,
          user:{
                id:'',
                username:'',
                password:'',
                email:'',
                avatar:'',
                sign:'',
                regdate:'',
                role:''
          },
          userurl:'',
          avatar:'',
          postdata:{data:''},
          visible:false,
          searchlist:[],
          searchlen:0
      }
    },
    watch:{
      search(){
        if(Search.isEmpty(this.search)){
          this.visible=false
          this.searchlen=0
        }
        delay(() => {
        this.fetchData();
      }, 300);
      }
    },
    created(){
      let token = Cookie.getCookie('token');
      if(token){
        this.hasnotoken=false;
        let user = JSON.parse(Cookie.getCookie('user'));
        this.user = user
        this.avatar = '/goIp'+user.avatar
        this.userurl = '/user/'+user.id
      }
      
    },
    mounted() {
      // 事件监听滚动条
      window.addEventListener("scroll", this.watchScroll);
    },

    destroyed() {
      // 移除事件监听
      window.removeEventListener("scroll", this.watchScroll);
    },
    methods: {
      directto(id){
        this.visible=false
      this.$router.push({path: '/article/'+id});
    },
        async fetchData(val) {
            await this.fetch();
          },
          fetch:function () {
            if(this.hasnotoken){
              this.$message.error({
                    showClose: true,
                    message: '尚未登录',
                    type: 'error'
                    });
                    return
            }
            if(Search.isEmpty(this.search)){ 
             
              return}
            this.postdata.data = this.search
      
            api.postSearch(Qs.stringify(this.postdata)).then(res=>{
              var data = res.data
              console.log("res",res)
              if(data.code==0){
                this.visible=true
                this.searchlist=data.data
                this.searchlen=this.searchlist.length
              }else{
                this.$message.error({
                    showClose: true,
                    message: data.msg,
                    type: 'error'
                    });
              }
            }).catch(err=>{
               this.$message.error({
                    showClose: true,
                    message: '网络错误',
                    type: 'error'
                    });
            })
          },
          changeVisible(){
            if(this.visible==true){
              this.visible=false
            }
          },
          watchScroll() {
            // 滚动的距离
            var scrollTop =
              window.pageYOffset ||
              document.documentElement.scrollTop ||
              document.body.scrollTop;

            // 容器的高度
            var offsetTop = document.querySelector("#nav").offsetHeight;
            //  滚动的距离如果大于了元素到顶部的距离时，实现吸顶效果
            if (scrollTop > offsetTop) {
              this.navBarFixed = true;
            } else {
              this.navBarFixed = false;
            }
          },
          forget:function () {
            this.forgetis=true
            this.registeris=false
            this.loginis=false
          },
          register:function () {
            if(!this.centerDialogVisible){
              this.centerDialogVisible=true
            }
            this.forgetis=false
            this.registeris=true
            this.loginis=false
          },
          login:function(){
            if(!this.centerDialogVisible){
              this.centerDialogVisible=true
            }
            this.forgetis=false
            this.registeris=false
            this.loginis=true
          },
        loadout:function(){
           Cookie.setCookie('token','',-1);
           Cookie.setCookie('user','',-1);
           Cookie.setCookie('clikelist','',-1)
           Cookie.setCookie('likelist','',-1)
           location.reload()
        },
          getLoginMsg:function(data){
            this.centerDialogVisible=false;
            this.$message({
              showClose: true,
              message: '登陆成功！',
              type: 'success'
            });
            location.reload()
          },
          getRegistMsg:function (data) {
            this.$message({
              showClose: true,
              message: '注册成功！',
              type: 'success'
            });
            this.login()
          },
          searchEvent:function(){
          
          }
        },
        getForgetMsg:function(params) {
          this.$message({
              showClose: true,
              message: '注册成功！',
              type: 'success'
            });
            this.login()
        },
        
}
</script>

<style scoped>
@import '../assets/css/style.css';
a{ text-decoration:none;
  color: #2c5885;
  font-size: 15px;
}
li{
margin-top:10px;
list-style-type:none;
}
.ava-name{
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.fixed-top{
  position: fixed;
  top: 0;
  z-index: 999;
  margin:auto;
	left:0;
  right:0;
  
}
.foot-dialog{
  margin: 0 auto;
  text-align: center;
  display: flex;
  flex-direction: column;
}
.popover-div{
  display: flex;
  flex-direction: column;
  justify-content: start;
  font-size: 16px;
}
.popover-span{
  margin-top: 12px;
}
.hearder-ava{
  top:0;
  bottom: 0;
}
.searchli{
font-size:15px;font-weight:bold;margin-top:15px;
}
.searchli :hover{
  background-color: #bdbdc2;
}
.back :hover{
background-color: #e8ecf3;
}
.img-div{
  border-radius:50%;
}
img{
  border-radius:50%;
  width: 40px;
  height: 40px;
}
a{
  margin-left: 8px;
  margin-top:5px;
}
.headerfirst{
  width: 10%;
}
.headlist{
  margin-left: 10px;
  width: 30%;
  display: flex;
  flex-direction:row;
  justify-content:start;
  margin-bottom: 20px;
}
.headersearch{
  width: 15%;
  display: flex;
  flex-direction:row;
  justify-content:center;
}
.searchdiv{
  width: 20%;
}
.headerloginavatar{
  margin-left: 10px;
  width: 10%;
  display: flex;
  flex-direction:row-reverse;
  justify-content: right;
}
</style>