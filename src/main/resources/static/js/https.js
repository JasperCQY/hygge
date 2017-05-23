/**
 * HTTPS AJAX请求
 */
var https = {
    request : function(options){
        // 把url设置到data中
        if(!options.data) {
            options.data = {};
        }
        options.data.url = options.url;
        
        // 把url换成对应的Controller
        var url = "wxHttpGet";
        if("post" == options.type || "POST" == options.type){
            url = "wxHttpPost";
        }
        options.url = url;
        
        return $.ajax(options);
    }
};