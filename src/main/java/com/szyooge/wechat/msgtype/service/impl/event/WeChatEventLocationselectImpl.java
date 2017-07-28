package com.szyooge.wechat.msgtype.service.impl.event;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.szyooge.util.XmlUtil;
import com.szyooge.wechat.constant.WeChatXmlConst;
import com.szyooge.wechat.msgtype.service.WeChatMsgTypeService;

/**
 * 弹出地理位置选择器的事件
 * @ClassName: WeChatEventLocationselectImpl
 * @author quanyou.chen
 * @date: 2017年7月28日 上午11:17:10
 * @version  v 1.0
 */
@Service(WeChatXmlConst.IEvent.location_select)
public class WeChatEventLocationselectImpl implements WeChatMsgTypeService {
    
    private static Logger logger = LoggerFactory.getLogger(WeChatEventLocationselectImpl.class);

    @Override
    public String msg(Map<String, String> wxMap) {
        String replyXmlStr = null;
        Map<String, String> replyMap = new HashMap<String, String>(wxMap);
        Resource resource = new ClassPathResource("replyXml/text.xml");
        InputStream in = null;
        try {
            in = resource.getInputStream();
            
            replyMap.put(WeChatXmlConst.FromUserName, wxMap.get(WeChatXmlConst.ToUserName));
            replyMap.put(WeChatXmlConst.ToUserName, wxMap.get(WeChatXmlConst.FromUserName));
            replyMap.put(WeChatXmlConst.CreateTime, String.valueOf(new Date().getTime()));
            
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
