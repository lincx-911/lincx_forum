<template>
  <div class="login-div">
      <template v-if="!issuccess">
      <span class="title">找回密码</span>
      <el-alert
            title="输入邮箱，我们将发送密码重置链接至该邮箱。"
            type="info">
        </el-alert>
        <el-input
        class="inputdiv"
        placeholder="邮箱"
        v-model="user.email"
        clearable>
        </el-input>
        <div class="valid inputdiv">
        <el-input  placeholder="验证码" v-model="validcode1" clearable></el-input><span style="width:40%">
            <el-button @click="submit" type='primary' v-if="codeShow">发送验证码</el-button>
            <el-button type='primary' v-else>{{count}}秒后重试</el-button>
            </span>
        </div>
        <div class="login-button">
        <el-button @click="checkCode"  type='primary' style="width:100%;font-size:15px">重置密码</el-button>
        </div>
        </template>
        <template v-else>
            <span class="title">重置密码</span>
            <el-input
            class="inputdiv"
            placeholder="新密码"
            v-model="user.password"
            clearable
            show-password
            ></el-input>
            
            <el-input
            class="inputdiv"
            placeholder="确认密码"
            v-model="password1"
            clearable
            show-password
            
            >
            </el-input>
            <div class="login-button">
            <el-button @click="changePassword"  type='primary' style="width:100%;font-size:15px">确认重置</el-button>
            </div>
        </template>
         
  </div>
</template>
<script>
const Base64 = require('js-base64').Base64
import api from '@/api/api'
import Qs from 'qs'
export default {
    name:"blogin",
    data(){
        return{
            user:{
                email:'',
                password:'',
            },
            validcode:'',
            validcode1:'',
            codeShow:true,
            count:'',
            timer: null,
            issuccess:false,
            password1:'',
            
        }
    },
    created () {
          
    },
    methods: {
      // 登录
        submit: function () {
            const TIME_COUNT = 60;
                 if (!this.timer) {
                     this.count = TIME_COUNT;
                     this.codeShow = false;
                     this.timer = setInterval(() => {
                         if (this.count > 0 && this.count <= TIME_COUNT) {
                             this.count--;
                         } else {
                             this.codeShow = true;
                             clearInterval(this.timer);
                             this.timer = null;
                         }
                     }, 1000)
                 }
            api.getEmailCode(this.user).then(res=>{
                var data= res.data
                if(data.code==0){
                    this.validcode=data.data
                    this.$message({
                    showClose: true,
                    message: '发送成功',
                    type: 'success'
                    });
                }else{
                    this.$message({
                    showClose: true,
                    message: data.msg,
                    type: 'error'
                    });
                }
            }).catch(err=>{
                this.$message({
                showClose: true,
                message: '网络错误',
                type: 'error'
                });
            })
        },
        checkCode:function(){
            if(this.validcode1==''||this.validcode!=this.validcode1){
                this.$message({
                showClose: true,
                message: '验证码错误',
                type: 'error'
                });

            }else{
                this.issuccess=true
            }
        },
        changePassword:function(){
            if(this.isEmpty(this.user.password)){
             
                this.$message({
                showClose: true,
                message: '密码不能为空',
                type: 'error'
                });
                return
            }
            if(!this.checkpassword()){
                return
            }
            api.postPsw(Qs.stringify(this.user)).then(res=>{
                var data= res.data
                if(data.code==0){
                    this.$message({
                    showClose: true,
                    message: '修改成功',
                    type: 'success'
                    });
                }else{
                    this.$message({
                    showClose: true,
                    message: data.msg,
                    type: 'error'
                    });
                }
            }).catch(err=>{
                this.$message({
                showClose: true,
                message: '网络错误',
                type: 'error'
                });
            })
        },
        checkpassword:function () {
         
            if (this.password1!=''){
                if(this.password1==this.user.password){
                    return true
                }
            }
             this.$message({
            showClose: true,
            message: '密码不一致',
            type: 'error'
            });
            return false
        },
        isEmpty:function(obj){
            var regu = "^[ ]+$";
            var re = new RegExp(regu);
            if(typeof obj == "undefined" || obj == null || obj == "" || re.test(obj)){
                return true;
            }else{
                return false;
            }
        },
       
    }
}
</script>
<style scoped>
.valid{
    display: flex;
    flex-direction: row;
    justify-content: start;

}
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