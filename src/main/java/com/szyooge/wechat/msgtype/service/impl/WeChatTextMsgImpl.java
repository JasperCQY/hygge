package com.szyooge.wechat.msgtype.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.szyooge.tuling.Robot;
import com.szyooge.tuling.constant.RobotConst;
import com.szyooge.util.XmlUtil;
import com.szyooge.wechat.constant.CmdConst;
import com.szyooge.wechat.constant.WeChatXmlConst;
import com.szyooge.wechat.msgtype.service.WeChatMsgTypeService;

@Service(WeChatXmlConst.IMsgType.text)
public class WeChatTextMsgImpl implements WeChatMsgTypeService {
    
    private static Logger logger = LoggerFactory.getLogger(WeChatTextMsgImpl.class);
    
    @Autowired
    private Robot robot;
    
    @Override
    public String msg(Map<String, String> wxMap) {
        String replyXmlStr = null;
        Map<String, String> replyMap = new HashMap<String, String>(wxMap);
        InputStream in = null;
        try {
            
            replyMap.put(WeChatXmlConst.FromUserName, wxMap.get(WeChatXmlConst.ToUserName));
            replyMap.put(WeChatXmlConst.ToUserName, wxMap.get(WeChatXmlConst.FromUserName));
            replyMap.put(WeChatXmlConst.CreateTime, String.valueOf(new Date().getTime()));
            String context = wxMap.get(WeChatXmlConst.Content);
            // 发送给机器人
            String replyMsg = robot.sendMsg(wxMap.get(WeChatXmlConst.FromUserName), context);
            logger.info(replyMsg);
            try {
                JSONObject robotReply = new JSONObject(replyMsg);
                if (RobotConst.DataType.TEXT == robotReply.getInt("code")) {
                    context = robotReply.getString("text");
                    // 对指令的处理
                    if (context != null && context.length() > 4 && "cmd:".equals(context.substring(0, 4))) {
                        String cmd = context.substring(4);
                        if (CmdConst.QC.equals(cmd)) {
                            // 小程序二维码
                            context = "请识别二维码：https://mmbiz.qpic.cn/mmbiz_jpg/WwzAYfZ2z0nJ1CeTXicb8xd55ibWRvl4Hbms76lic9oiaeRkARD4b5yZAcwVx1ZgeaHv0VFN2764uNkOicCibqsanrGw/0?wx_fmt=jpeg";
                        }
                    }
                }
                
            } catch (JSONException e) {
                logger.error("图灵机器人回复JSON格式问题", e);
            }
            replyMap.put(WeChatXmlConst.Content, context);
            in = new ClassPathResource("replyXml/text.xml").getInputStream();
            replyXmlStr = XmlUtil.mapToXmlStr(replyMap, in);
        } catch (IOException e) {
            logger.error("获取资源文件流失败", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("关闭资源文件流失败", e);
                }
            }
        }
        
        return replyXmlStr;
    }
    
}
