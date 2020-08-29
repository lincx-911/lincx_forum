import http from '@/utils/http'
// 
/**
 *  @parms resquest 请求地址 例如：http://197.82.15.15:8088/request/...
 *  @param '/javaIp'代表vue-cil中config，index.js中配置的代理
 */
let resquest = "/javaIp/user"
let res = "/goIp/"
let pyres = "/pyIp/"

// post注册请求

export default{
    //search搜索
    postSearch(params){
        return http.post1(`${pyres}getsearch`,params)
    },
    // post注册请求
    postRegist(params){
        return http.post1(`${resquest}/regist`,params)
    },
    // post登录请求
    postLogin(params){
        return http.post1(`${resquest}/login`,params)
    },
    postImg(params){
        return http.post1(`${res}user/updateimg`,params)
    },
    postPsw(params){
        return http.put1(`${resquest}/changepsw`,params)
    },
    getEmailCode(params){
        return http.get(`${res}user/regist/sendemail`,params)
    },
    //post添加文章
    addArticle(params){
        return http.post(`${res}article/add`,params)
    },
    updateArticle(params){
        return http.put(`${res}article/update`,params)
    },
    //get获取文章通过id
    getArticleById(params){
        let id = params
        let url = "article/get/"+id
        return http.get(`${res}${url}`,null)
    },
    //默认方式获取全部文章
    getAllArticle(params){
        let url="article/getlist/"+params
        return http.get(`${res}${url}`,null)
    },
    //按其他方式排序
    getArticleByOther(params){
        let url="article/"+params
        return http.get(`${res}${url}`,null)
    },
    getArticleByUser(params){
        let url="article/getbyuser/"+params
        return http.get(`${res}${url}`,null)
    },
    //get获取用户信息
    getUserInfo(params){
        let url = "user/get/"+params
        return http.get(`${res}${url}`,null)
    },
    //get用户个人信息
    getSelfInfo(){
        let url = "user/getself"
        return http.get(`${res}${url}`,null)
    },
    //update更新个人信息
    updateUserInfo(params){
        let url = "user/update"
        return http.put(`${res}${url}`,params)
    },
    //getCommentByRid获取评论
    getCommentByRid(page){
        let url = 'comment/get/'+page
        return http.get(`${res}${url}`,null)
    },
    //postComment发表评论
    postComment(params){
        return http.post(`${res}comment/add`,params)
    },
    //judgeFocus判断是否关注
    judgeFocus(params){
        let url = 'article/judge/'+params
        return http.get(`${res}${url}`,null)
    },
    //addFocus添加关注
    addFocus(params){
        let url = 'article/addfocus/'+params
        return http.put(`${res}${url}`,null)
    },
    //delFocus取消关注
    delFocus(params){
        let url = 'article/delfocus/'+params
        return http.delete(`${res}${url}`,null)
    },
    //文章点赞增加
    articleLikeUp(params){
        let url = params
        return http.put(`${res}${url}`,null)
    },
    //评论点赞数减少    
    commentLikeUp(params){
        let url='article/likeup/'+params
        return http.put(`${res}${url}`,null)
    },
    articleLikeDown(params){
        let url = params
        return http.put(`${res}${url}`,null)
    },
    commentLikeDown(params){
        let url='article/likedown/'+params
        return http.put(`${res}${url}`,null)
    },
    getLikeArticlelist(){
        return http.get(`${res}article/getlikes`,null)
    },
    getLikeCommentlist(){
        return http.get(`${res}comment/getlikes`,null)
    },
    //按分类返回ArticleList
    getArticlesByCateid(params){
        return http.get(`${res}article/getbycateid/${params}`,null)
    },
    //返回用户个人文章
    getOwnArticle(params){
        let url='article/getbyself/own/'+params
        return http.get(`${res}${url}`,null)
    },
    //public公共api
    getApi(params){
        return http.get(`${res}${params}`,null)
    },
    //公共删除
    deleteApi(params){
        return http.delete(`${res}${params}`,null)
    }
}
// // put 请求
// export function putSomeAPI(params){
//     return http.put(`${resquest}/putSome.json`,params)
// }
// // delete 请求
// export function deleteListAPI(params){
//     return http.delete(`${resquest}/deleteList.json`,params)
// }
