<template>
  
  <div class="edi-dv">

      <span v-if="msgVal==0||artVal!=null"><h2>发布文章</h2></span>
      <span v-else><h2>撰写评论</h2></span>
      <el-input placeholder="文章标题" v-model="title" v-if="msgVal==0"></el-input>
      <div>
        <mavon-editor 
            size=60%
            v-model="code" 
            ref="md" 
            @change="change" 
            style="min-height: 600px"
        />
    </div>
      <div class="eid-down" v-if="msgVal==0">
          <span class="tagspan">标签：</span>
          <el-tag size='middle' type="success" closable v-for="(t,index) in rtag" :key="index" style="margin-left:8px" @close="delTag(index)">{{t.atagname}}</el-tag>
                       <el-popover
                        placement="bottom"
                        title="添加标签"
                        width="200"
                        trigger="click">
                        <div class="popover-div">
                            <el-input v-model='newtag.atagname' placeholder='输入标签' style="width:150px"></el-input>
                            <el-button icon="el-icon-plus" circle size='mini' @click="addTag"></el-button>
                        </div>
                     <el-button icon="el-icon-plus" circle size='mini'  slot="reference"></el-button>
                       </el-popover><br>
        <el-select v-model="value" placeholder="分类" class="b-button">
        <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
        >
        </el-option>
        </el-select>
      <el-button v-on:click="submit" type='primary' size='medium' v-loading.fullscreen.lock="fullscreenLoading" v-if="artVal==null">发布</el-button>
      <el-button v-on:click="update" type='primary' size='medium' v-loading.fullscreen.lock="fullscreenLoading" v-else>更新</el-button>
      </div>
      <div class="eid-down" v-else>
          <el-button v-on:click="submitcomment" type='primary' size='medium' v-loading.fullscreen.lock="fullscreenLoading" >发布</el-button>
      </div>
  </div>
  
</template>
<script>
import api from '@/api/api'
import axios from 'axios'
import { Message} from 'element-ui';
export default {
    props: {
        msgVal:{
            type:Number,
            default:0,
        },
        paVal:{
            type:Number,
            default:0,
        },
        userVal:{
            type:Number,
            default:0,
        },
        artVal:{
            type:Object,
            default:null,
        }
    },
    name:"bEditor",
    data(){
        return {
            article:{
                title:'',
                content:'',
                user_id:"",
                category_id:'',
                posttime:'',
                updatetime:'',
                tag:[]
            },
            comment:{
                raid:'',
                paid:'',
                content:'',
                user_id:'',
                touser_id:'',
                post_time:'',
            },
           fullscreenLoading:false,
           title:'',
           code:'',
           html:'',
           value:'',
           label:'',
           newtag:{id:'',
                   atagname:''},
           rtag:[
           ],
           options:[
                {
                    value:1,
                    label:'知识讨论',
                },
                 {
                    value:2,
                    label:'难题提问',
                },
                 {
                    value:3,
                    label:'分享发现',
                },
                {
                    value:4,
                    label:'就业升学',
                }
            ],
        }
    },
    watch:{
    },
    created(){
        if(this.artVal!=null){
            this.article=JSON.parse(JSON.stringify(this.artVal))
            this.rtag = this.article.tag
            this.value = this.article.category_id
            this.code = this.article.content
            this.title = this.article.title
        }     
    },
     mounted(){
        
     },
        methods: {
        // 所有操作都会被解析重新渲染
        change:function(value, render){
            // render 为 markdown 解析后的结果[html]
            this.html = render;
        },
        // 提交
        submit:function(){
            if(this.title==''){
                Message.warning("标题不能为空")
                return
            }
            if(this.code==''){
                Message.warning("内容不能为空")
                return
            }
            if(this.value==''){
                Message.warning("未选择分类")
                return
            }
            this.fullscreenLoading=true
            this.article.title=this.title
            this.article.content=this.code
            this.article.category_id=this.value
            this.article.post_time = new Date().Format("yyyy-MM-dd HH:mm:ss");
            this.article.tag=this.rtag
         
            api.addArticle(this.article).then(res=>{
               
                var data=res.data
                if(data.code==0){
                    
                    Message.success(data.msg)
                    this.title=''
                    this.code=''
                    this.value=''
                    this.rtag=[]  
                }else{
                    Message.warning(data.msg)
                }
                this.fullscreenLoading=false
                
            }).catch(err=>{
                    this.fullscreenLoading=false
                   })
        },
        update:function(){
            if(this.title==''){
                Message.warning("标题不能为空")
                return
            }
            if(this.code==''){
                Message.warning("内容不能为空")
                return
            }
            if(this.value==''){
                Message.warning("未选择分类")
                return
            }
            this.fullscreenLoading=true
            this.article.title=this.title
            this.article.content=this.code
            this.article.category_id=this.category_id
            this.article.tag=this.rtag
            this.article.updatetime=new Date().Format("yyyy-MM-dd HH:mm:ss");
            api.updateArticle(this.article).then(res=>{
                var data=res.data
                if(data.code==0){
                    Message.success(data.msg)
                    this.title=''
                    this.code=''
                    this.value=''
                    this.rtag=[] 
                    this.$emit('func',JSON.parse(JSON.stringify(this.article))) 
                }else{
                    Message.warning(data.msg)
                }
                this.fullscreenLoading=false
            }).catch(err=>{
                    this.fullscreenLoading=false
                    })
        },
        addTag:function(){
            var tag = JSON.parse(JSON.stringify(this.newtag))
            this.rtag.push(tag)
        },
        delTag:function(index) {
            this.rtag.splice(this.rtag.indexOf(index),1);
        },
        submitcomment:function(){
           
            if(this.code==''){
                Message.warning("内容不能为空")
                return
            }
            this.comment.raid=this.msgVal
            if(this.paVal!=0){
                this.comment.paid=this.paVal
            }
            this.comment.touser_id=this.userVal
            this.comment.content=this.code
            this.comment.posttime = new Date().Format("yyyy-MM-dd HH:mm:ss");
           
            api.postComment(this.comment).then(res=>{
                var data=res.data
                if(data.code==0){
                    this.comment=data.data
                  
                    Message.success(data.msg)
                    this.code=''
                }else{
                    Message.warning(data.msg)
                }
            }).catch(err=>{
               
                Message.error(err)
            })
        }
    }
}
</script>
<style lang="scss">
@import '../assets/css/style.css';
span{
  margin-top: 10px;
}
.b-button{
  width: 120px;
  
}
 .el-drawer.rtl{
 
    overflow: scroll;
 }
 :focus{
 
        outline:0;
}
.eid-down{
    margin-top: 10px;
}
.tagspan{
    color: rgb(181, 184, 185);
}
</style>