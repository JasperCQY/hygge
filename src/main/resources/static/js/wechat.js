/**
 * 微信接口
 */
var wechat = {};
/**
 * 微信公众号处理接口
 */
wechat.public = {
    /**
     * 调用微信的方法
     * @param opts 主要参数{url:'', data:'', jsonData:''} <br/>
     *  url 请求url <br/>
     *  data 请求参数（如果有jsonData，data会被拼到url中） <br/>
     *  jsonData 请求参数（复杂参数）  <br/>
     * @return 调用结果
     */
    callwechat : function(opts){
        var resultData = null;
        var requestOpts = {
            type : "get",
            async: false,
            dataType  : "json",
            success   : function(data){
                resultData = data;
            },
            error     : function(data){
                resultData = data;
            }
        };
        
        // 参数和url处理
        // 除了jsonData，其他参数全部拼凑到url中
        if(opts.jsonData){
            if(opts.data) {
                // 拼凑URL
                if(opts.url.indexOf('?') == -1){
                    opts.url += '?';
                } else {
                    opts.url += '&';
                }
                // opts.data是否真的有属性
                var hasProps = false;
                for(var index in opts.data) {
                    hasProps = true;
                    opts.url += (index+'='+opts.data[index]+'&');
                }
                if(hasProps) {
                    // 去掉末尾的&符号
                    opts.url = opts.url.substring(0,opts.url.length-1)
                }
                
                // 删除opts的data属性
                delete opts.data;
                
                // 重新赋值opts.data
                opts.data = {jsonData : JSON.stringify(opts.jsonData)};
            }
            // 删除opts.jsonData
            delete opts.jsonData;
        }
        $.extend(requestOpts,opts);
        https.request(requestOpts);
        return resultData;
    },
    
    /**
     * 获取access_token
     * @param opts 调用时需要的参数{appid:'', secret''}
     * @return {access_token:'', expires_in:''}或者{"errcode":40013,"errmsg":"invalid appid"}
     */
    getAccessToken : function(opts){
        var params = {grant_type : "client_credential"};
        $.extend(params,opts);
        return wechat.public.callwechat({
            url : "https://api.weixin.qq.com/cgi-bin/token",
            data : params
        });
    },
    
    /**
     * 获取微信服务器IP地址
     * @params {access_token:''}或者access_token
     * @return {ip_list : ['127.0.0.1','0.0.0.0']}
     */
    getcallbackip : function(opts) {
        if("object" != typeof(opts)) {
            opts = {access_token:opts};
        }
        return wechat.public.callwechat({
            url  : "https://api.weixin.qq.com/cgi-bin/getcallbackip",
            data : opts,
        });
    },
    /**
     * 自定义菜单操作
     */
    menu : {
        opration : function(opts, jsonData, url, method){
            if("object" != typeof(opts)) {
                opts = {access_token:opts};
            }
            if(!method) method = "get";
            return wechat.public.callwechat({
                url  : "https://api.weixin.qq.com/cgi-bin/menu/get",
                type : method,
                data : opts,
                jsonData : jsonData
            });
        },
        /**
         * @param opts {access_token : ACCESS_TOKEN}  <br/>
         * @param jsonData <br/>
         * {                                  <br/>
         *     button       : [{              <br/>
         *         "type":"click",             <br/>
         *         "name":"今日歌曲",              <br/>
         *         "key":"V1001_TODAY_MUSIC"     <br/>
         *     }]                                 <br/>
         * }                                       <br/>
         * 
         * @return 正确{"errcode":0,"errmsg":"ok"}，错误{"errcode":40018,"errmsg":"invalid button name size"}
         */
        create : function(opts,jsonData){
            return wechat.public.menu.opration(opts, jsonData, "https://api.weixin.qq.com/cgi-bin/menu/create", "post");
        },
        get : function(opts, jsonData){
            return wechat.public.menu.opration(opts, jsonData, "https://api.weixin.qq.com/cgi-bin/menu/get");
        },
        delete : function() {
            return wechat.public.menu.opration(opts, jsonData, "https://api.weixin.qq.com/cgi-bin/menu/delete");
        }
    }
};