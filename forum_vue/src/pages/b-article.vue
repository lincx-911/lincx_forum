<template>
    <div>
        <b-header></b-header>
    <div class="p-div" :style="{background:color}">
      <div class="p-contain">
          <span style="margin-top:20px"><el-tag effect="plain" type='danger'>{{catagory}}</el-tag></span>
          <span class="p-title" style="color:white">{{title}}</span>
      </div>
    </div>
    <el-container class="container">
    <el-aside class="el-con-size"><b-aside></b-aside></el-aside>
    <el-container class="el-con-main">
    <el-main>
        
    <div class="article-div"  v-for="(article,index) in alist" :key="index" v-show="index<limitnum" >
        <div class="header-ul">
              <el-avatar :size='70' :src="'/goIp'+article.author.avatar" class="avatar-div avatar-user" ></el-avatar>
              <div class="tag-name-time">
              <div class="username-time" @click="directouser(article.user_id)">
                  <span style="font-size:20px">{{article.author.username}}</span><span style="font-size:13px">发表于</span>
                  <time pubdate="true"  title="星期六, 九月 7, 2019 2:16 AM" data-humantime="true" style="font-size:13px" v-if="index==0">{{article.posttime}}</time>
                  <time pubdate="true"  title="星期六, 九月 7, 2019 2:16 AM" data-humantime="true" style="font-size:13px">{{article.post_time}}</time>
              </div>
              <el-tag size='mini' v-if="article.paid" type='info'><span style="font-size:15px;"><i class="el-icon-caret-right"></i>回复{{article.author.username}}</span></el-tag>
              </div>
        </div>
        <div class="content-div">
            
            <div class="markdown-body" style="text-align:left;">
                <VueMarkdown :source="article.content"  v-highlight></VueMarkdown>
            </div>
            <div class="content-like">
                <div class="con-tag-cla">
                    <!-- <el-tag  type="success">
                        <span>{{article.catagory.category_name}}</span>
                    </el-tag> -->
                    <el-tag  type="danger" v-for="(tag,index) in article.tag" :key="index">
                        <span>{{tag.atagname}}</span>
                    </el-tag>
                </div>
                <div class="like-comm">
                    <el-tag  v-if="article.islike" type='dark' @click="likechange(index,article.id)">
                        <i class="el-icon-caret-bottom"></i>
                        <span>已赞同{{article.likenum}}</span>
                    </el-tag>
                    <el-tag  v-else type='plain' @click="likechange(index,article.id)">
                        <i class="el-icon-caret-top"></i>
                        <span>赞同{{article.likenum}}</span>
                    </el-tag>
                    <div v-if="index==0" @click="changedrawer">
                        <i class="el-icon-chat-dot-round"></i>
                        <span>回复{{article.commentnum}}</span>
                    </div>
                    <div v-else @click="changedrawer">
                        <i class="el-icon-edit"></i>
                        <span>写回复</span>
                    </div>
                    <el-drawer
                    size="40%"
                    :visible.sync="drawer"
                    direction="rtl">
                    <b-editor :user-val="article.user_id" :msg-val="rid" v-if="index==0"></b-editor>
                    <b-editor :user-val="article.user_id" :msg-val="rid" :paVal="article.id" v-else></b-editor>
                    </el-drawer>
                    <div @click="focuschange(article.id)" v-if="index==0">
                    <template v-if="isfocus">
                        <i class="el-icon-star-on"></i>
                         <span>已关注</span>
                    </template>
                    <template v-else >
                         <i class="el-icon-star-off"></i>
                         <span>关注</span>
                    </template>
                    </div>
                </div>
            </div>
        </div>
        
        <div v-if="index==0" style="margin-top:50px;margin-bottom:50px">
            <el-divider content-position='left'><span style="font-weight:bold;font-size:20px">评论{{totalnum-1}}</span></el-divider>
        </div>
        <div v-else><el-divider></el-divider></div>
        </div>
        <el-button @click="showmore"  v-loading.fullscreen.lock="fullscreenLoading" v-if="totalnum>limitnum">
            加载更多
        </el-button>
        <span v-else>╮(๑•́ ₃•̀๑)╭已经到底了哦</span>
    </el-main>
    </el-container>
    </el-container>
   </div>
    <!-- <div  v-html="html"></div> -->
    <!-- <article v-html="html"></article> -->

</template>
<script>
import VueMarkdown from 'vue-markdown';
import api from '@/api/api'
import { Message} from 'element-ui';
import Search from '@/utils/search'
import Cookie from'@/utils/cookie'
export default {
    
    name:'barticle',
    components: {
        VueMarkdown // 注入组件
      },
    data(){
        return{
            colorlist:[
            '#f35d06','#fdb8ec','#409EFF','purple'
        ],
            nowmsg:{id:'',title:'',content:'',color:''},
            fullscreenLoading: false,
            drawer:false,
            limitnum:10,
            totalnum:0,
            isshow:true,
            msg:{},
            rootarticle:{
                title:'',
                catagory:{
                    category_name:'',
                }
            },
            alist:[],
            page:1,
            color:'',
            catagory:'',
            title:'',
            rid:0,
            pid:0,
            isfocus:0,
            likelist:[],
            clikelist:[]
        }
    },
    created(){
      
    },
    mounted(){
        this.likelist= JSON.parse(localStorage.getItem('likelist'));
        this.clikelist = JSON.parse(localStorage.getItem('clikelist'));
        let url = this.$route.path
        let urllist = url.split('/')
            api.getArticleById(urllist[2]).then(res=>{
            var data = res.data
            if(data.code==0){
                this.rootarticle=data.data
                this.alist.push(data.data)
              
                if(Search.binarySearch(this.likelist,this.rootarticle.id)){
                    this.alist[0]['islike']=1
                }else{
                    this.alist[0]['islike']=0
                }
           
                this.color = this.colorlist[parseInt(this.rootarticle.category_id)-1]
                this.totalnum = 1
                this.catagory=this.rootarticle.category.category_name
                this.title=this.rootarticle.title
                let uli = ""+this.rootarticle.id+"/"+this.page
                this.rid = this.rootarticle.id
             
                api.judgeFocus(this.rootarticle.id).then(res=>{
                    var data = res.data
                    if(data.code==0){
                        this.isfocus=data.data
                    }else{
                        Message.error(data.msg)
                      
                    }
                }).catch(err=>{
                        Message.error(err)
                     
                    })
                this.getComment(uli)
            }else{
                Message.error(data.msg)
                return
            }
        }).catch(err=>{
            Message.error(err)
        })
    },
    beforeDestroy(){
        this.getLikeArticleList()
        this.getLikeCommentList()
    },
    methods: {
        focuschange:function (index) {
          
            if(this.isfocus==0){
                api.addFocus(index).then(res=>{
                        var data = res.data
                        if(data.code==0){
                            this.isfocus=1
                        }else{
                            Message.error(data.msg)
                        }
                    }).catch(err=>{
                        Message.error(err)
                    })
            }else{
                api.delFocus(index).then(res=>{
                        var data = res.data
                        if(data.code==0){
                            this.isfocus=0
                        }else{
                            Message.error(data.msg)
                        }
                    }).catch(err=>{
                        Message.error(err)
                    })
            }
        },
        likechange:function (index,id) {
            var nowlike=this.alist[index].islike
           
            if(nowlike){
                var url = 'comment/likedown/'+id
                 if(index==0){
                     url = 'article/likedown/'+id
                 }
                 api.articleLikeDown(url).then(res=>{
                     var data = res.data
                     if(data.code==0){
                         this.alist[index].likenum -=1;
                         this.alist[index].islike=!nowlike
                     }else{
                         Message.error(data.msg)
                     }
                 }).catch(err=>{
                     Message.error(err.message)
                 })
            }else{
                 var url = 'comment/likeup/'+id
                 if(index==0){
                     url = 'article/likeup/'+id
                 }
                 api.articleLikeUp(url).then(res=>{
                     var data = res.data
                     if(data.code==0){
                         this.alist[index].likenum +=1;
                         this.alist[index].islike=!nowlike
                     }else{
                         Message.error(data.msg)
                     }
                 }).catch(err=>{
                     Message.error(err.message)
                 })
                 
            }
            
        },
        editcomment:function(){

        },
         directouser(id){
            this.$router.push({path: '/user/'+id});
        },
        changedrawer:function () {
            this.drawer=true
        },
        showmore:function(){
            this.fullscreenLoading = true;
            let url = ""+this.rootarticle.id+"/"+this.page
            this.getComment(url)
            this.fullscreenLoading=false;
        },
        getComment:function(uli){
            api.getCommentByRid(uli).then(res=>{
            var data = res.data
            if(data.code==0){
                
                this.page+=1
                this.rootarticle=data.data
                this.alist.push.apply(this.alist,data.data.comments)
                for(var i=this.totalnum,len=data.data.totalnum;i<len+1;i++){
                if(Search.binarySearch(this.clikelist,this.alist[i].id)){
                    this.alist[i]['islike']=1
                    }else{
                        this.alist[i]['islike']=0
                    }
                }
                this.totalnum += data.data.totalnum
             
                this.limitnum +=10
            }else{
                Message.error(data.msg)
            }
        }).catch(err=>{
            Message.error(err)
        })
        },
    getLikeArticleList:function(){
      api.getLikeArticlelist().then(res=>{
      var data=res.data
      if(data.code==0){
    
        var likelist = data.data
 
         localStorage.setItem('likelist',JSON.stringify(likelist));
      }else{
        
      }
      }).catch(err=>{
        Message.error(err.Message)
      })
    },
      getLikeCommentList:function(){
      api.getLikeCommentlist().then(res=>{
      var data=res.data
      if(data.code==0){
  
        var likelist = data.data
   
         localStorage.setItem('clikelist',JSON.stringify(likelist));
      }else{
        
      }
      }).catch(err=>{
        Message.error(err.Message)
      })
      
    },
    
    }
}
</script>
<style scoped>
@import '../assets/css/style.css';

span{
margin-right:5px
}
.imgdiv{
    margin:10px auto;
}
.avatar-user{
    display: inline-block;
    
}
.tag-name-time{
    display: flex;
    flex-direction: row;
    justify-content: start;
    flex-wrap: wrap;
    width: 30%;
}
.username-time{
    margin-top: 0;
    display: inline-block;
    vertical-align: top;
}
.div-left{
    float: left;
}
.header-tag-name{
    display: flex;
    flex-direction: column;
    justify-content: start;
}
.header-ul{
    display: flex;
    flex: row;
    justify-content: start;
    flex-wrap: wrap;
  }

.content-div{
    width: 75%;
    margin: 0 auto;
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
.p-div{
    margin-top: -1px;
    background: #e8ecf3;
    text-align: center;
    color: #8c9299;
    width: 100%;
    height: 150px;
  }
  .p-contain{
    margin: 0 auto;
    text-align: center;
    display: flex;
    flex-direction: column;
    font-size: 15px;
  }
  .p-title{
    margin-top: 20px;
    font-size: 30px;
  }

</style>