export default {
    binarySearch: function (arr,data) {
        if (arr==null)return false
        var upperBound = arr.length-1;
        var lowerBound = 0;
        while(lowerBound<=upperBound){
            var mid = Math.floor((upperBound+lowerBound)/2);
            if(arr[mid]<data){
                lowerBound=mid+1;
            }else if(arr[mid]>data){
                upperBound=mid-1;
            }else{
                return true
            }
        }
        return false;
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