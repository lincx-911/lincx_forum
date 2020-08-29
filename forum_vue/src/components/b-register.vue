<template>
  <div class="login-div">
      <span class="title">注册</span>
      <form class="">
          <el-input
          class="inputdiv"
        placeholder="邮箱"
        v-model="loginForm.email"
        clearable>
        </el-input>
      <el-input
       class="inputdiv"
        placeholder="用户名"
        v-model="loginForm.username"
        clearable>
        </el-input>
        
        <el-input
         class="inputdiv"
        placeholder="密码"
        v-model="loginForm.password"
        clearable
        show-password
        ></el-input>
        
        <el-input
         class="inputdiv"
        placeholder="确认密码"
        v-model="password1"
        clearable
        show-password
        @blur="checkpassword"
        @change="showoff"
        >
        </el-input>
        <div class="login-button">
        <el-button type='primary' @click="submit" v-loading.fullscreen.lock="fullscreenLoading" style="width:100%;font-size:15px">注册</el-button>
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
    name:"bregist",
    data(){
        return{
            fullscreenLoading: false,
            password1:'',
            isshow:false,
            loginForm:{
                username:'',
                email:'',
                password:'',
            }
        }
    },
    methods: {
      // 注册
        submit: function () {
            if(this.loginForm.username==''||this.loginForm.email==''||this.loginForm.password==''){
                this.$message({
                showClose: true,
                message: '存在字段为空',
                type: 'error'
                });
                return
            }
            this.fullscreenLoading=true
            api.postRegist(Qs.stringify(this.loginForm)).then(res=>{
         
                var data = res.data
                if(data.code==0){
                    this.$emit('funcre',false)
                }else{
                    this.$message({
                    showClose: true,
                    message: data.msg,
                    type: 'error'
                    });
                }
                this.fullscreenLoading=false
            }).catch(err=>{
                    this.fullscreenLoading=false})
        },
        checkpassword:function () {

            if (this.password1!=''){
                if(this.password1!=this.loginForm.password){
                    this.isshow=true;
                    this.$message({
                    showClose: true,
                    message: '密码不一致',
                    type: 'error'
                    });
                }
            }
        },
        showoff:function () {
            if(this.isshow){
                this.isshow=false;
            }
        }
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
}
.inputdiv{
    margin-bottom: 20px;
}
</style>