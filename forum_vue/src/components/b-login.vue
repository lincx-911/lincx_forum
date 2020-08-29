<template>
  <div class="login-div">
      <span class="title">登录</span>
      <form class="">
        <div class="inputdiv">
      <el-input
        placeholder="用户名或邮箱"
        v-model="loginForm.account"
        clearable>
        
        </el-input>
          </div>
        <el-input
        class="inputdiv"
        placeholder="密码"
        v-model="loginForm.password"
        clearable
        show-password
        >
        </el-input>
        <input type="checkbox"  class="inputdiv" v-model="remember"/><span>记住密码</span>
        <div class="login-button" >
        <el-button type='primary' style="width:100%;font-size:15px" @click="submit" v-loading.fullscreen.lock="fullscreenLoading">登录</el-button>
        </div>
      </form>
  </div>
</template>
<script>
import api from '@/api/api'
import Qs from 'qs'
import Cookie from'@/utils/cookie'
let Base64 = require('js-base64').Base64
export default {
    inject: ['reload'],
    name:"blogin",
    data(){
        return{
            fullscreenLoading: false,
            loginForm:{
                account:'',
                password:'',
            },
            remember:false
        }
    },
    created () {
        // 在页面加载时从cookie获取登录信息
        let account = Cookie.getCookie("account")
        let password = Base64.decode(Cookie.getCookie("password"))
        // 如果存在赋值给表单，并且将记住密码勾选
        if(account){
            this.loginForm.account = account
            this.loginForm.password = password
            this.remember = true
        }
    },
    methods: {
      // 登录
      
        submit: function () {
            // // 点击登陆向后台提交登陆信息
            this.fullscreenLoading=true
        
            api.postLogin(Qs.stringify(this.loginForm)).then(res=>{
                this.fullscreenLoading=false
                var data = res.data
                if(data.code==0){
                     Cookie.setCookie('token',data.token,1)
                    // 储存登录信息
                    this.setUserInfo()
                    this.$message({
                        showClose: true,
                        message: '登录成功',
                        type: 'success'
                        });
                    this.$emit('func',false)
                    Cookie.setCookie('user',JSON.stringify(data.user),1)
                }else{
                    this.$message({
                    showClose: true,
                    message: data.msg,
                    type: 'error'
                    });
                }
                
            }).catch(
                err=>{
                    this.fullscreenLoading=false
                    this.$message({
                    showClose: true,
                    message: '网络错误',
                    type: 'error'
                    });
                })

        },
        // 储存表单信息
        setUserInfo: function () {
            // 判断用户是否勾选记住密码，如果勾选，向cookie中储存登录信息，
            // 如果没有勾选，储存的信息为空
            if(this.loginForm.remember){
                Cookie.setCookie("account",this.loginForm.account,1)
                // base64加密密码
                let passWord = Base64.encode(this.loginForm.password)
                 Cookie.setCookie('password',passWord,1)
                 Cookie.setCookie("remember",this.remember,1)    
            }else{
                 Cookie.setCookie("account","",-1)
                 Cookie.setCookie("password","",-1) 
            } 
        },
       

    }
}
</script>
<style scoped>
.login-div{
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
}
.login-button{
    margin: 0 auto;
    width: 80%;
    margin-bottom: 20px;
}
.title{
    margin: 0 auto;
    font-size: 25px;
    margin-bottom: 20px;
}
.inputdiv{
    margin-bottom: 20px;
}
</style>