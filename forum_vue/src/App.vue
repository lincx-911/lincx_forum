<template>
  <div id="app">
    
    <router-view v-if="isRouterActive"  :key="$route.fullPath"/>
    <footer style="height:10px;"><span class="foot-span">©2019-2020 lincx</span></footer>
  </div>
</template>

<script>
export default {
  name: 'App',
   provide () {
    return {
      reload: this.reload
    }
  },
  data(){
    return{
      isRouterActive:true
    }
  },
  method:{
    async reload () {
      this.isRouterActive = false
      await this.$nextTick()
      this.isRouterActive = true
    }
  }
}
</script>

<style>
#app {
  
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c5885;
  margin: 0 auto;
  font-family: 'Microsoft YaHei';
}
</style>
