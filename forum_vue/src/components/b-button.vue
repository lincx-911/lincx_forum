<template>
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
</template>
<script>
export default {
    name:"bButton",
    data(){
        return {
            value: [],
            options:[
                {
                    value:'最新发布',
                    lable:'lastest',
                },
                 {
                    value:'最多评论',
                    lable:'mostcom',
                },
                 {
                    value:'最多关注',
                    lable:'mostfou',
                },
            ]
        }
    },
    methods: {
      handleOpen(key, keyPath) {
       
      },
      handleClose(key, keyPath) {
        
      }
    },
    handleChange(value){
        
    }
}
</script>
<style scoped>
@import '../assets/css/style.css';
span{
  margin-top: 10px;
}
.b-button{
  width: 120px;

}
</style>