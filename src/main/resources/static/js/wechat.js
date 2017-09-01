/**
 * 微信接口
 */
var wechat = {
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
     * @param opts 可选
     * @param jsonData 必填
     * @param url 必填， 
     * @param method 可选
     */
    opration : function(opts, jsonData, url, method){
        // url为空或不是http开头，证明前面有参数缺失
        if((!url) || url.indexOf('http') == -1) {
            // opts没填的情况
            if(opts && "object" == typeof(opts)) {
                if(!opts.access_token) {
                    // 无opts
                    jsonData = opts;
                    opts = null;
                }
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
        params.type = (!!method) ? method : "get";
        return wechat.callwechat(params);
    }
};

/**
 * 微信公众号处理接口。（所有的access_token值都可以为空）
 */
wechat.public = {
    /**
     * 获取access_token
     * @param opts 调用时需要的参数{appid:'', secret''}
     * @return {access_token:'', expires_in:''}或者{"errcode":40013,"errmsg":"invalid appid"}
     */
    getAccessToken : function(opts){
        var params = {grant_type : "client_credential"};
        $.extend(params,opts);
        return wechat.callwechat({
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
        return wechat.callwechat(params);
    }
};
/**
 * 自定义菜单操作
 */
wechat.public.menu = {
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
    create : function(jsonData){
        return wechat.opration(null, jsonData, "https://api.weixin.qq.com/cgi-bin/menu/create", "post");
    },
    /**
     * 获取所有的菜单
     */
    get : function(){
        return wechat.opration(null, null, "https://api.weixin.qq.com/cgi-bin/menu/get");
    },
    /**
     * 删除所有的菜单
     */
    "delete" : function() {
        return wechat.opration(null,null,"https://api.weixin.qq.com/cgi-bin/menu/delete");
    }
};

/**
 * 消息发送
 */
wechat.public.message = {
    /**
     * 客服
     */
    custom : {
        /**
         * 客服消息发送
         */
        send : function(opts, jsonData){
            return wechat.opration(opts, jsonData, "https://api.weixin.qq.com/cgi-bin/message/custom/send", "post");
        }
    }
};

/**
 * 客服消息
 */
wechat.public.customservice = {
    kfaccount : {
        add : function(opts, jsonData){
            return wechat.opration(opts, jsonData, "https://api.weixin.qq.com/customservice/kfaccount/add", "post");
        },
        update : function(opts){
            return wechat.opration(opts, null, "https://api.weixin.qq.com/customservice/kfaccount/update", "post");
        },
        "delete" : function(opts, jsonData){
            return wechat.opration(opts, jsonData, "https://api.weixin.qq.com/customservice/kfaccount/del");
        },
        uploadheadimg : function(opts, jsonData){
            return wechat.opration(opts, jsonData, "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg", "post");
        },
        getkflist : function(){
            return wechat.opration(null, null, "https://api.weixin.qq.com/cgi-bin/customservice/getkflist");
        }
    }
};

/**
 * 发送消息-群发接口和原创校验
 */
wechat.public.media = {
    /**
     * 上传图文消息内的图片获取URL【订阅号与服务号认证后均可用】
     */
    uploadimg : function(opts, jsonData){
        
    },
    /**
     * 上传图文消息素材【订阅号与服务号认证后均可用】
     */
    uploadnews : function(opts, jsonData) {
        
    }
};

/**
 * 永久素材
 */
wechat.public.material = {
    /**
     * 批量获取素材（分页）
     * @param jsonData {"type":TYPE,   "offset":OFFSET,   "count":COUNT}
     */
    batchget : function(jsonData){
        return wechat.opration(null, jsonData, "https://api.weixin.qq.com/cgi-bin/material/batchget_material");
    },
    /**
     * 素材数量
     * @return { "voice_count":COUNT, "video_count":COUNT, "image_count":COUNT, "news_count":COUNT}
     */
    getCount : function(){
        return wechat.opration(null, null, "https://api.weixin.qq.com/cgi-bin/material/get_materialcount");
    }
}

/**
 * 发送消息-模板消息接口(用于发送重要通知)
 */
wechat.public.template = {
        
};

/**
 * 关注用户
 */
wechat.public.user = {
    info : function(opts){
        var params = {lang : "zh_CN"};
        $.extend(params,opts);
        return wechat.callwechat({
            url : "https://api.weixin.qq.com/cgi-bin/user/info",
            data : params
        });
    },
    infos : function(opts, jsonData){
        return wechat.opration(opts, jsonData, "https://api.weixin.qq.com/cgi-bin/user/info/batchget", "post");
    },
    getlist : function(opts){
        if('object' != typeof(opts)) {
            opts = { next_openid : opts };
        }
        return wechat.callwechat({
            url : "https://api.weixin.qq.com/cgi-bin/user/get",
            data : opts
        });
    }
};