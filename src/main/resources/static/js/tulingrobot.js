/**
 * 图灵机器人http://www.tuling123.com/help/h_cent_webapi.jhtml?nav=doc
 */
var tulingrobot = {
    apiUrl : "http://www.tuling123.com/openapi/api",
    /**
     * @param                        <br/>
     * opts {                        <br/>
     *     key    : KEY,      // 必填 <br/>
     *     info   : INFO,     // 必填 <br/>
     *     userid : USERID,   // 必填 <br/>
     *     loc    : LOC       // 选填 <br/>
     * }                             <br/>
     * @return                       <br/>
     * {                             <br/>
     *     code : CODE,       // 必填 <br/>
     *     text : TEXT,       // 必填 <br/>
     *     url  : URL,        // 选填（链接类） <br/>
     *     list : LIST        // 选填（新闻类） <br/>
     * }                             <br/>
     */
    sendMsg : function(opts) {
        var resultData = null;
        https.request({
            url  : tulingrobot.apiUrl,
            type : "post",
            data : opts,
            async: false,
            dataType  : "json",
            success   : function(data){
                resultData = data;
            },
            error     : function(data){
                resultData = data;
            }
        });
        return resultData;
    }
}