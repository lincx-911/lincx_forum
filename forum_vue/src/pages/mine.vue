<template>
<div><b-header></b-header>
<div class="page">
    <header>
        <div class="info-div">
            <div class="header-ul">
                
                
                    <el-upload
                    class="avatar-uploader"
                    :headers="header"
                    :action="PostUrl"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload" v-if="ismine">
                    
                   <el-avatar :size='70' :src="imageUrl"></el-avatar>
                    </el-upload>
               
                <span style="font-size:15px;margin-top:10px;">活跃天数{{age}}</span>
            </div>
            <div class="name-sign" v-if="noedit">
                <span style="font-size:30px;"><span>{{rootuser.username}}</span>
                    <el-tag size='mini' type="info" v-for="(t,index) in rootuser.utag" :key="index" style="margin-left:8px">{{t.utagname}}</el-tag>
                </span>
                <span style="margin-top:10px;">{{rootuser.email}}</span>
                <span style="margin-top:15px;" v-if="rootuser.sign==''">这个人很懒，啥都没写</span>
                <span style="margin-top:10px;" v-else>{{rootuser.sign}}</span>
            </div>
            <div class="name-sign" v-else>
                <span style="font-size:30px;"><span>
                    <input v-model="user.username" rows=1 style="background-color:transparent;border:none;color:aliceblue;font-size:30px" />
                </span><br>
                    <el-tag size='mini' type="info" closable v-for="(t,index) in user.utag" :key="index" style="margin-left:8px" @close="delTag(index)">{{t.utagname}}</el-tag>
                       <el-popover
                        placement="bottom"
                        title="添加标签"
                        width="200"
                        trigger="click">
                        <div class="popover-div">
                            <el-input v-model='newtag.utagname' placeholder='输入标签' style="width:150px"></el-input>
                            <el-button icon="el-icon-plus" circle size='mini' @click="addTag"></el-button>
                        </div>
                     <el-button icon="el-icon-plus" circle size='mini'  slot="reference"></el-button>
                       </el-popover>
                </span>
                
                <textarea
                    type="textarea"
                    placeholder="请输入内容"
                    v-model="user.sign"  
                    style="background-color:transparent;border:none;margin-top:30px;color:aliceblue;font-size:17px"
                    @change="editSign"
                />
            </div>
            <div class="edituser" v-if="ismine">
                <el-button type="info" plain icon="el-icon-edit" @click="changeEdit" v-if="noedit">编辑资料</el-button>
                <el-button type="info" plain icon="el-icon-s-promotion" @click="uploadInfo" v-if="!noedit">提交信息</el-button>
                <el-button type="info" plain icon="el-icon-s-promotion" @click="changeEdit" v-if="!noedit">返回</el-button>
            </div>
        </div>    
    </header> 
    <div class="content"> 
        <div class="mine-asize">
            <el-card class="box-card" style="margin-left: 90px;">
                <div class="item"  @click="change(0)" :style="{'color':which==0?'rgb(70, 139, 218)':''}">
                    <span><i class="el-icon-s-unfold"></i><span style="margin-left:20px">主题</span></span>
                    <span style="margin-left:20px" v-if="which==0">{{totalnum}}</span>
                </div>
                
                <el-divider></el-divider>
                <div class="item" @click="change(1)" :style="{'color':which==1?'rgb(70, 139, 218)':''}" v-if="ismine">
                    <span><i class="el-icon-s-comment" ></i><span style="margin-left:20px">回复</span></span>
                    <span style="margin-left:20px" v-if="which==1">{{totalnum}}</span>
                    
                </div>
                <el-divider  v-if="ismine"></el-divider>
                <div class="item" @click="change(2)" :style="{'color':which==2?'rgb(70, 139, 218)':''}">
                    <span><i class="el-icon-star-on"></i><span style="margin-left:20px">关注</span></span>
                    <span style="margin-left:20px" v-if="which==2">{{totalnum}}</span>
                </div>
            </el-card>
        </div>
        <div class="content-body">
            
            <el-card class="box-card">
                <div style="margin-top:50px;margin-bottom:50px;" v-if="totalnum==0">尚未发表任何文章哦</div>
                <div class="article-div"  v-for="(article,index) in alist" :key="index" v-show="index<limitnum" >
        <span style="margin-left:20px;font-size:20px;font-weight:bold;" v-on:click="directto(article.id)"><a>{{article.title}}</a></span>
        <div class="header-ul1">
              <el-avatar fit="contain" :size='70' :src="'/goIp'+article.author.avatar" class="avatar-div avatar-user" @click="directouser(article.user_id)"></el-avatar>
              <div class="username-time">
                  <span style="font-size:20px">{{article.author.username}}</span><span style="font-size:13px">发表于</span>
                  <time pubdate="true"  title="星期六, 九月 7, 2019 2:16 AM" data-humantime="true" style="font-size:13px">{{article.posttime}}</time>
                  <time pubdate="true"  title="星期六, 九月 7, 2019 2:16 AM" data-humantime="true" style="font-size:13px" v-if="which==1">{{article.post_time}}</time>
              </div>
        </div>
        <div class="content-div">
            <div class="markdown-body" style="text-align:left;">
                <VueMarkdown :source="article.content"  v-highlight></VueMarkdown>
            </div>
            <div class="content-like">
                <div class="con-tag-cla" >
         
                    <el-tag v-for="(atag,i) in article.tag" :key="i" type="danger" >
                        <span>{{atag.atagname}}</span>
                    </el-tag>
                </div>
                <div class="like-comm">
                    <!-- <el-tag  v-if="article.islike" type='dark' @click="likechange(index)">
                        <i class="el-icon-caret-bottom"></i>
                        <span>已赞同{{article.like}}</span>
                    </el-tag> -->
                    <el-tag  type='plain' >
                        <i class="el-icon-caret-top"></i>
                        <span>赞同{{article.likenum}}</span>
                    </el-tag>
                    <div >
                        <i class="el-icon-chat-dot-round"></i>
                        <span>回复{{article.commentnum}}</span>
                    </div>
                    <div style="margin-left:10px;font-size:20px;">
                        <el-dropdown>
                            <span class="el-dropdown-link">
                                <i class="el-icon-arrow-down el-icon--right"></i>
                            </span>
                            <el-dropdown-menu slot="dropdown">
                                <div @click="changedrawer1(index)"><el-dropdown-item v-if="which==0" >编辑</el-dropdown-item></div>
                                <div @click=" deleteFunc(index,article.id)"><el-dropdown-item >删除</el-dropdown-item></div>
                            </el-dropdown-menu>
                        </el-dropdown>
                             
                            </div>
                        </div>
                    </div>
                </div>
                    <el-divider></el-divider>
                </div>
                <el-button @click="moreShow"  v-loading.fullscreen.lock="fullscreenLoading" v-if="totalnum>limitnum">
                    加载更多
                </el-button>
                <span class="tobuttom" v-else>╮(๑•́ ₃•̀๑)╭已经到底了哦</span>
                <el-drawer
                size="40%"
                :visible.sync="drawer"
                direction="ltr">
                <b-editor @func="afterUpdate" :art-val="this.article111"></b-editor>
                </el-drawer>
            </el-card>
        </div> 
    </div> 
    </div>
</div>
</template>
<script>
import VueMarkdown from 'vue-markdown';
import api from '@/api/api'
import { Message} from 'element-ui';
import Cookie from'@/utils/cookie'
export default {
    name:"mine",
    components: {
        VueMarkdown // 注入组件
      },
    data(){
        return{
            fullscreenLoading: false,
            limitnum:10,
            isshow:true,
            drawer:false,
            noedit:true,
            ismine:false,
            article111:'',
            age:'',
            newtag:{
                id:'',
                utagname:''
            },
            rootuser:{
                id:'',
                username:'',
                password:'',
                email:'',
                avatar:'',
                sign:'',
                regdate:'',
                role:'',
                utag:[]
          },
            user:{},
            alist:[],
            totalnum:0,
            page:1,
            which:0,
            nowurl:'',
            updateindex:-1,
            header:'',
            PostUrl:'',
            imageUrl:''
        }
    },
    created(){
        let token = Cookie.getCookie('token')
        this.header = {
            'token':token,
          }
          this.PostUrl = "/goIp/user/updateimg"
       
        let url = this.$route.path
        let urllist = url.split('/')
        var user1 = JSON.parse(Cookie.getCookie('user'))
        if(user1.id==urllist[2]){
            this.ismine=true
        }
         api.getUserInfo(urllist[2]).then(res=>{
             let data = res.data
             if(data.code==0){
                 this.rootuser=data.data
                 this.imageUrl = '/goIp'+data.data.avatar
                 
                 if(this.ismine){
                     Cookie.setCookie('user','',-1)
                     Cookie.setCookie('user',JSON.stringify(this.rootuser),1)
                 }
                 
                 var date = new Date(this.rootuser.regdate)
                 this.age=(new Date).getDate()-date.getDate()
                 Message.success(data.msg)
             }
             else{
                 Message.error(data.msg)
             }
         }).catch(err=>{
         
             Message.error(err)
         })
        this.user = JSON.parse(JSON.stringify(this.rootuser))
       
    },
     mounted(){
         this.page=1
         this.totalnum=0
         this.limitnum=10
         let url1 = this.$route.path
         let urllist = url1.split('/')
         var url = urllist[2]+'/'+this.page
         api.getArticleByUser(url).then(res=>{
             var data = res.data
             if(data.code==0){
                 this.page+=1;
                 this.totalnum = data.data.totalnum
                 this.nowurl = 'article/getbyself/own/'
                 this.alist .push.apply(this.alist ,data.data.articles)
             }else{
                 Message.error(data.msg)
             }
         }).catch(err=>{
             Message.error(err.messgage)
         })
     },
    methods: {
      uploadInfo:function(){
          api.updateUserInfo(this.user).then(res=>{
              let data = res.data
                if(data.code==0){
                    this.rootuser=this.user
                    Cookie.setCookie('user','',-1)
                    Cookie.setCookie('user',JSON.stringify(this.rootuser),1)
                    Message.success(data.msg)
                    this.noedit=true
                }
                else{
                    Message.error(data.msg)
                }
            }).catch(err=>{
                Message.error(err)
            })
          
      },
      afterUpdate:function (params) {
          if(this.updateindex!=-1){
              this.alist[this.updateindex]=params
          }
          
      },
      changeEdit:function(){
          this.noedit=!this.noedit
          this.user = JSON.parse(JSON.stringify(this.rootuser))
      },
      focuschange:function (index) {
        var nowfocus=this.alist[index].focus
        this.alist[index].focus=!nowfocus
        },
        likechange:function (index) {
            var nowlike=this.alist[index].islike
            if(nowlike){
                this.alist[index].like -=1;
            }else{
                 this.alist[index].like +=1;
            }
            this.alist[index].islike=!nowlike
        },
        addTag:function(){
            var tag = JSON.parse(JSON.stringify(this.newtag))
            this.user.utag.push(tag)
        },
        delTag:function(index) {
            this.user.utag.splice(this.user.utag.indexOf(index),1);
        },
        changedrawer1:function (index) {
           
            this.drawer=true
            this.updateindex=index
            this.article111 = this.alist[index]
        },
        editSign:function(){
           
        },
        change:function(data){
            if(data==this.which)return
            this.which=data
            var url = ''
            if(data==0){
                url = 'article/getbyself/own/1'
                this.nowurl = 'article/getbyself/own/'
            }else if(data==1){
                url = 'comment/getown/own/1'
                this.nowurl = 'comment/getown/own/'
            }else{
                url = 'article/getfocus/own/1'
                this.nowurl = 'article/getfocus/own/'
            }
            api.getApi(url).then(res=>{
               var data = res.data
             if(data.code==0){
                 this.page=1
                 this.limitnum=10
                 this.page+=1;
                 this.totalnum = data.data.totalnum
                 this.alist = []
                 if(this.which==1){
                     this.alist.push.apply(this.alist ,data.data.comments)
                 }
                 else {
                     this.alist.push.apply(this.alist ,data.data.articles)
                 }
             }else{
                 Message.error(data.msg)
             }
         }).catch(err=>{
             Message.error(err.messgage)
         })
        },
        directto(id){
        this.$router.push({path: '/article/'+id});
        },
        directouser(id){
            this.$router.push({path: '/user/'+id});
        },
         moreShow(){
             this.fullscreenLoading = true;
             let url = this.nowurl+''+this.page
             api.getApi(url).then(res=>{
               var data = res.data
             if(data.code==0){
                 this.limitnum+=10
                 this.page+=1;
                 this.totalnum = data.data.totalnum
                 if(this.which==1){
                    
                     this.alist.push.apply(this.alist ,data.data.comments)
                 }
                 else {
                     
                     this.alist.push.apply(this.alist ,data.data.articles)
                 }
             }else{
                 Message.error(data.msg)
             }
         }).catch(err=>{
             Message.error(err.messgage)
         })
         this.fullscreenLoading = false;
         },
         deleteFunc:function (index,id) {
             var url = 'article/delete/'+id
             if(this.which==1){
                 url = 'comment/delete/'+id
             }
             api.deleteApi(url).then(res=>{
                 var data = res.data
                if(data.code==0){
                    Message.success("删除成功")
                    this.alist.splice(this.alist.indexOf(index),1);
                    this.totalnum-=1
                }else{
                    Message.error(data.msg)
                }
             }).catch(err=>{
                    Message.error(data.msg)
             })
         },
      handleAvatarSuccess(res, file) {
        var data = res.data
        if(res.code==0){
            this.rootuser.avatar=res.data
            this.imageUrl = '/goIp'+res.data
            Message.success("上传成功")
        }else{
            Message.success(res.msg)
        }
        this.dialogVisible=false
      },
      beforeAvatarUpload(file) {
        this.dialogVisible=true
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      }
    }
}
</script>
<style scoped>

.page{
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 100%;
    margin: 0 auto;
}
header{
    flex: 0 0 auto;
    background: rgb(133, 133, 133);
}
.content{
    flex: 1 0 auto;
    width: 70%;
    margin: 0 auto;
    display: flex;
    flex-direction: row;
    justify-content: start;
}
footer{
    background: #333;
    flex: 0 0 auto;
}
.info-div{
    margin: 0 auto;
    margin-top: 40px;
    margin-bottom: 10px;
    display: flex;
    flex-direction: row;
    justify-content: start;
    width: 60%;
    color: aliceblue;
}
.header-ul {
    display: flex;
    flex-direction: column;
    justify-content: start;
    margin-left: 30px;
  } 
  .name-sign{
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    margin-left: 40px;
    text-align: left;
  }
  .edituser{
      flex:1; 
      justify-content: flex-end;
      width: 10px;
      text-align: right;
  }
.box-card {
    margin:40px ;
    
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
  }
  .item{
    display: flex;
    flex-direction: row;
    justify-content: center;
    color:rgb(51, 51, 51);
  }
  .item :hover{
      display: flex;
    flex-direction: row;
    justify-content: center;
    color:rgb(160, 151, 151);
  }
  .header-ul1{
    display: flex;
    flex: row;
    justify-content: start;
    height: 30px;
    margin-top: 15px;
  }

.content-div{
    width: 75%;
    margin: 0 auto;
    margin-top: 10px;
    display: flex;
    flex-direction: column;
    justify-self: start;
}
.content-like{
    display: flex;
    margin-top: 10px;
    
}
.con-tag-cla{
display: flex;
flex: row;
justify-content: start;
}
.like-comm{
    flex: 1;
    justify-content: flex-end;
    display: flex;
    align-items: center;
}
.mine-asize{
    width: 25%;
}
.article-div{
    text-align: left;
}
.img-div{
  border-radius:50%;
  width: 80px;
  height: 80px;
  transition: opacity .5s; opacity: 1;
}
.img-div :hover{
    opacity: 0.4;
}
img{
  border-radius:50%;
  width: 100%;
  height: 100%;
}
.avatar-uploader{
     width: 80px;
  height: 80px;
}
.popover-div{
    display: flex;
    flex-direction: row;
}
.content-body{
    width: 75%;
}
</style>