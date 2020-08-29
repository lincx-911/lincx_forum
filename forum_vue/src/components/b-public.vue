<template>
<div>
  <div class="p-div" :style="{background:nowmsg.color}">
      <div class="p-contain">
        <template v-if="which==1">
          <span class="p-title" style="color:white">{{nowmsg.title}}</span>
          <span style="color:white">{{nowmsg.content}}</span>
        </template>
        <template v-else-if="which==2">
          <span><el-tag effect="plain" type='danger'>{{ nowmsg.title }}</el-tag></span>
          <span class="p-title" style="color:white">{{nowmsg.content}}</span>
        </template>
        <template v-else >
           <span class="p-title">welcome to LINCX</span>
            <span>LINCX学习社区</span>
            <span>一个专注于计算机知识交流的社区</span>
        </template>
      </div>
      
  </div>
  </div>
</template>
<script>
export default {
    name:"bPulic",
    props: ['msgVal'],
    data(){
      return{
        nowurl:'',
        nowmsg:{id:'',title:'',content:'',color:''},
        amsg:this.msgVal,
        which:0,
        msg:[
          {id:'/bkl',title:'知识讨论',content:'知识讨论区，可发表各种讨论',color:'#f35d06'},
          {id:'/baq',title:'难题提问',content:'难题解答区，提出你的问题，给出解答',color:'#fdb8ec'},
          {id:'/bsf',title:'分享发现',content:'分享发现区，将你学习工作中的发现分享给大家吧',color:'#409EFF'},
          {id:'/bws',title:'就业升学',content:'就业升学区，关于就业升学的疑问与解答',color:'purple'},
        ],
        
      }
    },
    create(){


      if(this.amsg===0){
        this.nowurl = this.$route.path;

        for(var i=0,len=this.msg.length;i<len;i++){
          if(this.nowurl==this.msg[i].id){
            this.nowmsg=this.msg[i]
            this.which=1
            break
          }
        }
      }
      else{
            for(var i=0,len=this.msg.length;i<len;i++){
          if(this.amsg.classic==this.msg[i].title){
            this.nowmsg=this.msg[i]
            this.nowmsg.content=this.amsg.title
            this.which=2
            break
          }
        }
      }

    },
    
    methods: {
      handleOpen(key, keyPath) {
     
      },
      handleClose(key, keyPath) {
      
      }
    }
}
</script>
<style scoped>
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
span{
  margin-top: 15px;

}
</style>