var index = {
    init : function() {
        
    },
    getAccessToken : function(){
        $.ajax({
            url : "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential",
            data : {
                appid:$("#appid").val(),
                secret:$("#appsecret").val()
            },
            success :function(data){
                // data 格式{"access_token":"ACCESS_TOKEN","expires_in":7200}，expires_in有效时间
                console.log(data);
                $("#accesstoken").val(data);
            }
        });
    }
}