<template>
    <div>
        <b-header></b-header>
        <div class="p-div">
        <div class="p-contain">
            <span class="p-title">welcome to LINCX</span>
            <span style="margin-top: 15px;">LINCX学习社区</span>
            <span style="margin-top: 15px;">一个专注于计算机知识交流的社区</span>
        </div>
        </div>
        <el-container class="container">
            <el-aside class="el-con-size"><b-aside></b-aside></el-aside>
                <el-container class="el-con-main">
                    <el-main>
                        <div class="categories-tags">
                            <div class="catrgory-item" v-for="(cate,index) in msg" :key="index" :style="{background:cate.color}" @click="directto(cate.id)">
                                <div class="cate-title">{{cate.title}}</div>
                                <el-divider></el-divider>
                                <div class="cate-content">{{cate.content}}</div>
                            </div>
                        </div>
                        <div class="categories-tags"></div>
                    </el-main>
                </el-container>
        </el-container>
    </div>
</template>
<script>

export default {
    name:"categories",
    data(){
        return{
            background:'',
            msg:[
                {id:'/bkl',title:'知识讨论',content:'知识讨论区，可发表各种讨论',color:'#f35d06'},
                {id:'/baq',title:'难题提问',content:'难题解答区，提出你的问题，给出解答',color:'#fdb8ec'},
                {id:'/bsf',title:'分享发现',content:'分享你日常的脑洞和探索发现、可以是觉得不错的书籍、文章、网页、软件、课程、开源项目、学习资源等等，互相拓宽视野，激发灵感！',color:'#409EFF'},
                {id:'/bws',title:'就业升学',content:'就业升学区，关于就业升学的疑问与解答',color:'purple'},
            ],
        }
    },
    methods:{
        directto:function (id) {
            this.$router.push({path: '/'+id});
            location.reload();
        },

    }
    
}
</script>
<style scoped>
@import '../assets/css/style.css';
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
  .categories-tags{
      display: flex;
      flex-direction: row;
      flex-wrap: wrap;
      justify-content: start;
  }
  .catrgory-item{
      display:flex;
      flex-direction: column;
      justify-self:center;
      width: 50%;
      height: 300px;
      background-color: antiquewhite;
  }
  .cate-title{
      color: #fffafa;
      text-align: left;
      font-weight: bold;
      font-size: 25px;
      margin-left: 20px;
      margin-top: 20px;
  }
  .cate-content{
      text-align: left;
      font-size:15px;
      color: #ebe8e8;
      margin-left: 20px;
  }
</style>