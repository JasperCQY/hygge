var index = {
    init : function() {
        
    },
    clickMenu : function(url){
        $("#hygge_index").layout("panel","center").panel("refresh",url);
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
        var data = wechat.public.getcallbackip(index.access_token);
        $("#console").val(JSON.stringify(data));
    },
    menu : {
        create : function(){
            var jsonStr = $("#createMenuText").val();
            if(!jsonStr){
                return;
            }
            var menuObj = JSON.parse(jsonStr);
            var data = wechat.public.menu.create(menuObj);
            $("#wxmenuConsole").html(JSON.stringify(data));
        },
        get : function(){
            var data = wechat.public.menu.get();
            $("#menuGetText").html(JSON.stringify(data));
        },
        "delete" : function(){
            var data = wechat.public.menu.delete();
            $("#wxmenuConsole").html(JSON.stringify(data));
        }
    },
    material : {
        batchget : function(){
            $("#wxmediaText").html("");
            var data =  null;
            var param = null;
            param = {type:"image",offset:0,count:20};
            while(param.count == 20){
                data = wechat.public.material.batchget(param);
                param.count = data.item_count;
                $("#wxmediaText").append(JSON.stringify(data)+"<br/><br/>");
            }
            
            param = {type:"video",offset:0,count:20};
            while(param.count == 20){
                data = wechat.public.material.batchget(param);
                param.count = data.item_count;
                $("#wxmediaText").append(JSON.stringify(data)+"<br/><br/>");
            }
            param = {type:"voice",offset:0,count:20};
            while(param.count == 20){
                data = wechat.public.material.batchget(param);
                param.count = data.item_count;
                $("#wxmediaText").append(JSON.stringify(data)+"<br/><br/>");
            }
            
            param = {type:"news",offset:0,count:20};
            while(param.count == 20){
                data = wechat.public.material.batchget(param);
                param.count = data.item_count;
                $("#wxmediaText").append(JSON.stringify(data)+"<br/><br/>");
            }
        }
    }
}