package com.szyooge.wechat.media.service.impl;

import org.springframework.stereotype.Service;

import com.szyooge.config.WeChatConf;
import com.szyooge.constant.CharSet;
import com.szyooge.util.HttpUtil;
import com.szyooge.wechat.constant.MediaType;
import com.szyooge.wechat.media.service.WeChatMediaService;

@Service("weChatImageService")
public class WeChatImageService implements WeChatMediaService{
    
    @Override
    public String pushUrl(String... args) {
        return "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="+args[0]+"&type="+args[1];
    }
    @Override
    public String push(String content) {
        String url = pushUrl(WeChatConf.instance().getAccessToken(),MediaType.IMG);
        return HttpUtil.uploadFile(url, content, CharSet.UTF8);
    }

    @Override
    public String pullUrl(String... args) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public String pull(String id) {
        // TODO Auto-generated method stub
        return null;
    }
}
