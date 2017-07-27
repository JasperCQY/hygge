/**
 * 微信接口
 */
var wechat = {};
/**
 * 微信公众号处理接口。（所有的access_token值都可以为空）
 */
wechat.public = {
    /**
     * 调用微信的方法
     * @param opts 主要参数{url:'', data:'', jsonData:''} <br/>
     *  url 请求url <br/>
     *  data 请求参数 <br/>
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
        // opts参数处理，把opts.jsonData放到opts.data.jsonData，删除opts.jsonData
        if(opts.jsonData){
            if(!opts.data) {
                opts.data = {};
            }
            opts.data.jsonData = JSON.stringify(opts.jsonData);
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
        var params = {url  : "https://api.weixin.qq.com/cgi-bin/getcallbackip"};
        if(opts){
            if("object" == typeof(opts)) {
                params.data = opts;
            } else {
                params.data = {access_token:opts};
            }
        }
        return wechat.public.callwechat(params);
    },
    /**
     * 自定义菜单操作
     */
    menu : {
        /**
         * @param opts 可选
         * @param jsonData 必填
         * @param url 必填， 
         * @param method 可选
         */
        opration : function(opts, jsonData, url, method){
            // opts没填的情况
            if(opts && "object" == typeof(opts)) {
                if(!opts.access_token) {
                    // 无opts
                    jsonData = opts;
                    opts = null;
                }
            }
            
            var params = {
                url  : url,
                jsonData : jsonData
            };
            if(opts){
                if("object" == typeof(opts)) {
                    params.data = opts;
                } else {
                    params.data = {access_token:opts};
                }
            }
            if(!method) params.type = "get";
            return wechat.public.callwechat(params);
        },
        /**
         * @param opts {access_token : ACCESS_TOKEN}  选填<br/>
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
        /**
         * 获取所有的菜单
         */
        get : function(opts, jsonData){
            return wechat.public.menu.opration(opts, jsonData, "https://api.weixin.qq.com/cgi-bin/menu/get");
        },
        /**
         * 删除所有的菜单
         */
        "delete" : function() {
            return wechat.public.menu.opration(opts, jsonData, "https://api.weixin.qq.com/cgi-bin/menu/delete");
        }
    }
};