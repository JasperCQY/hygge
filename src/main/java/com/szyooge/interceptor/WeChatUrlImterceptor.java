package com.szyooge.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.szyooge.config.WeChatConf;
import com.szyooge.util.MDC_LOG;

/**
 * 请求微信的URL处理，在URL上添加access_token参数
 * @ClassName: WeChatUrlImterceptor
 * @author quanyou.chen
 * @date: 2017年7月27日 上午9:50:45
 * @version  v 1.0
 */
public class WeChatUrlImterceptor implements HandlerInterceptor {
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）<br/>
     * 只有返回true才会继续向下执行，返回false取消当前请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        MDC_LOG.add();
        String url = request.getParameter("url");
        
        WeChatConf weChatConf = WeChatConf.instance();
        if (url.startsWith(weChatConf.getUrl())) {
            // 微信access_token请求。
            if (weChatConf.getAppId().equals(request.getParameter("appid"))
                && weChatConf.getAppSecret().equals(request.getParameter("secret"))
                && weChatConf.getClientCredential().equals(request.getParameter("grant_type"))) {
                request.setAttribute("access_token_json", weChatConf.getAccessTokenJson());
            }
            return true;
        }
        // 微信请求
        url = request.getRequestURL().toString();
        // 添加access_token参数
        request.setAttribute("access_token", weChatConf.getAccessToken());
        return true;
    }
    
    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
        throws Exception {
        // 删除preHandle设置的attribute
        request.removeAttribute("access_token_json");
        request.removeAttribute("access_token");
        MDC_LOG.remove();
    }
    
    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     * 
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
        throws Exception {
        
    }
}
