var index = {
    init : function() {
        
    },
    getAccessToken : function(){
        $.ajax({
            url       : "httpGet",
            type      : "GET",
            data      : {
            	url        : "https://api.weixin.qq.com/cgi-bin/token",
            	grant_type : "client_credential",
                appid      : $("#appid").val(),
                secret     : $("#appsecret").val()
            },
            dataType  : "json",
            success   : function(data){
                // data 格式{"access_token":"ACCESS_TOKEN","expires_in":7200}，expires_in有效时间
                console.log(data);
                $("#accesstoken").val(data.access_token);
                $("#expiresin").val(data.expires_in);
            },
            error     : function(data){
            	console.log(data);
            }
        });
    }
}