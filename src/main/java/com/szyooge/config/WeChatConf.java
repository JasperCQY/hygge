package com.szyooge.config;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.szyooge.constant.CharSet;
import com.szyooge.util.HttpUtil;

@Component
public class WeChatConf {
    
    @Value("${wechat.token.url}")
    private String url;
    
    @Value("${wechat.appid}")
    private String appId;
    
    @Value("${wechat.appsecret}")
    private String appSecret;
    
    @Value("${wechat.clientcredential}")
    private String clientCredential;
    
    private static String accessToken;
    
    private static String accessTokenJson;
    
    private static WeChatConf th;
    
    public WeChatConf(){
        th = this;
    }
    
    public static WeChatConf instance(){
        return th;
    }
    
    public synchronized String requestAccessToken() {
        Map<String, String> param = new HashMap<>();
        param.put("appid", appId);
        param.put("secret", appSecret);
        param.put("grant_type", clientCredential);
        accessTokenJson = HttpUtil.sendGet(url, param, CharSet.UTF8);
        Pattern pattern = Pattern
            .compile("^\\{\\s*\"access_token\"\\s*:\\s*\"([\\w\\-]+)\"\\s*,\\s*\"expires_in\"\\s*:\\s*\\d+\\s*\\}$");
        Matcher matcher = pattern.matcher(accessTokenJson);
        if (matcher.find()) {
            accessToken = matcher.group(1);
        }
        return accessToken;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getAppId() {
        return appId;
    }
    
    public String getAppSecret() {
        return appSecret;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public String getClientCredential() {
        return clientCredential;
    }

    public String getAccessTokenJson() {
        return accessTokenJson;
    }
}
