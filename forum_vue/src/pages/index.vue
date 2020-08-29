<template>
<div><b-header></b-header>
    <div class="p-div" :style="{background:nowmsg.color}">
      <div class="p-contain" v-if="nowmsg.id">
          <span class="p-title" style="color:white">{{nowmsg.title}}</span>
          <span style="color:white">{{nowmsg.content}}</span>
      </div>
      <div class="p-contain" v-else>
          <span class="p-title">welcome to LINCX</span>
            <span style="margin-top: 15px;">LINCX学习社区</span>
            <span style="margin-top: 15px;">一个专注于计算机知识交流的社区</span>
      </div>
    </div>
    <el-container class="container">
    <el-aside class="el-con-size"><b-aside></b-aside></el-aside>
    <el-container class="el-con-main">
    <el-main v-if="allnum>0">
    <div class="b-div">
      <el-select v-model="value" placeholder="排序方式" class="b-button">
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value"
      >
    </el-option>
  </el-select>
  </div>
    <ul class="index-div" style="color:black">
      <li class="msg-list-div"  v-for="(msg,index) in msgList" :key="index" v-show="index<limitnum" v-on:click="directto(msg.id)">
        <el-avatar :size='50' :src="'/goIp'+msg.author.avatar" class="avatar-div"></el-avatar>

        <div class="msg-div" >
          <b>{{msg.title}}</b>
          <span class="msg-posttime"><i class="el-icon-paperclip" style="font-weight: bold; font-size:16px;">{{msg.author.username}}</i>
          <time>发布于{{msg.posttime}}</time>
          </span>
        </div>
        <div class="class-tag-div">
          <el-tag  effect='dark' size='small'>{{msg.category.category_name}}</el-tag>
          <el-tag type="danger" effect='dark' size='small' v-for="(tag,tindex) in msg.tag" :key=tindex>{{tag.atagname}}</el-tag>
          <i class="el-icon-chat-dot-round">{{msg.commentnum}}</i>
          
        </div>
    </li>
   </ul>
   <el-button @click="moreShow"  v-loading.fullscreen.lock="fullscreenLoading" v-if="allnum>limitnum">
     加载更多
   </el-button>
   <span class="tobuttom" v-else>╮(๑•́ ₃•̀๑)╭已经到底了哦</span>
   </el-main>
   <el-main v-else>
     <span>列表为空</span>
   </el-main>
    </el-container>
    </el-container>
  </div>
</template>

<script>
import api from '@/api/api'
import { Message} from 'element-ui';
import Cookie from'@/utils/cookie'
export default {
  
  name: 'index',
  data () {
    return {
      nowmsg:{id:'',title:'',content:'',color:''},
      msgList:[],
      value: 0,
      options:[
         {
              value:0,
              label:'默认排列',
          },
          {
              value:1,
              label:'最新发布',
          },
            {
              value:2,
              label:'最多评论',
          },
            {
              value:3,
              label:'最多点赞',
          },
      ],
     
      fullscreenLoading: false,
      allnum:0,
      page:1,
      limitnum:10,
      nowurl:'',
      url:'getlist/',
      cateurl:'',
      cateid:0,
      msg:[
          {id:'/bkl',title:'知识讨论',content:'知识讨论区，可发表各种讨论',color:'#f35d06'},
          {id:'/baq',title:'难题提问',content:'难题解答区，提出你的问题，给出解答',color:'#fdb8ec'},
          {id:'/bsf',title:'分享发现',content:'分享发现区，将你学习工作中的发现分享给大家吧',color:'#409EFF'},
          {id:'/bws',title:'就业升学',content:'就业升学区，关于就业升学的疑问与解答',color:'purple'},
        ],
    }
  },
  watch:{
    value(newNal,oldNal){
      switch (newNal) {
        case 0:
          this.url='getlist/'
          break;
        case 1:
            this.url='getlists/time/'
            break;
        case 2:
            this.url='getlists/com/'
            break;
        case 3:
            this.url='getlists/like/'
            break;
        default:
          break;
      }
      this.getArticle()
    },

  },
  created(){
    
  },
  mounted(){
    this.nowurl = this.$route.path;

    if(this.nowurl=='/focus'){
      this.url = 'getfocus/own/'
    }else{
      for(var i=0,len=this.msg.length;i<len;i++){
          if(this.nowurl==this.msg[i].id){
            this.nowmsg=this.msg[i]
            this.cateurl = this.nowmsg.id
            this.cateid = i+1
            break
          }
    }
    
    }
    this.getArticle()
    this.getLikeArticleList()
    this.getLikeCommentList()
  },
  methods:{
    moreShow(){
       this.fullscreenLoading = true;
       let ul = this.url+''+this.page
       if(this.cateurl!='')ul = 'bycateid/'+this.url+'/'+this.cateid+'/'+this.page
        api.getArticleByOther(ul).then(res=>{
        var data=res.data
        if(data.code==0){
          this.limitnum +=10;
          this.page +=1;
          this.msgList.push.apply(this.msgList,data.data.articles)
          this.allnum = data.data.totalnum
      
        }else{
          Message.error(data.msg)
        }
      }).catch(err=>{
        Message.error(err)
      })
        this.fullscreenLoading = false;
   
    },
    directto(id){
    
      this.$router.push({path: '/article/'+id});
    },
    getArticle:function(){
      this.fullscreenLoading = true;
      this.allnum=0,
      this.page=1
      this.limitnum=10
      this.msgList=[]
      let ul = this.url+''+this.page

      if(this.cateurl!='')ul = 'bycateid/'+this.url+'/'+this.cateid+'/'+this.page
       api.getArticleByOther(ul).then(res=>{
        var data=res.data
        if(data.code==0){
          this.page +=1;
          this.msgList.push.apply(this.msgList,data.data.articles)
          this.allnum = data.data.totalnum
        }else{
          Message.error(data.msg)
        }
      }).catch(err=>{
        Message.error(err)
      })
      this.fullscreenLoading = false;
    },
    getLikeArticleList:function(){
      api.getLikeArticlelist().then(res=>{
      var data=res.data
      if(data.code==0){
        var likelist = data.data
        localStorage.setItem('likelist',JSON.stringify(likelist));
      }else{
        localStorage.setItem('likelist',[]);
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
        localStorage.setItem('clikelist',[]);
      }
      }).catch(err=>{
        Message.error(err.Message)
      })
      
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import '../assets/css/style.css';
.tobuttom{
  font-size: 15px;
}
.b-button{
  width: 120px;

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
