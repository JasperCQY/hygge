var index = {
    init : function() {
        
    },
    getAccessToken : function(){
        var data = wechat.public.getAccessToken({
            appid      : $("#appid").val(),
            secret     : $("#appsecret").val()
        });
        $("#accesstoken").val(data.access_token);
        $("#expiresin").val(data.expires_in);
    },
    getcallbackip : function(){
        var data = wechat.public.getcallbackip();
        $("#console").val(JSON.stringify(data));
    },
    menu : {
        create : function(){
            var data = wechat.public.menu.create({
                button:[{  
                   "type":"click",
                   "name":"今日歌曲",
                   "key":"v1001_today_music",
                   url : "http://www.yooge.xin/hygger/wechatpush/menuclick"
                }]
            });
            $("#console").val(JSON.stringify(data));
        },
        get : function(){
            var data = wechat.public.menu.get();
            $("#console").val(JSON.stringify(data));
        },
        "delete" : function(){
            var data = wechat.public.menu.delete();
            $("#console").val(JSON.stringify(data));
        }
    }
}