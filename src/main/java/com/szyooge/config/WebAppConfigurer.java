package com.szyooge.config;

import java.io.File;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.szyooge.interceptor.WeChatUrlImterceptor;

/**
 * WEB配置
 * @ClassName: WebAppConfigurer
 * @author quanyou.chen
 * @date: 2017年7月27日 下午12:53:54
 * @version  v 1.0
 */
@Configuration
@EnableConfigurationProperties(WebAppConfigurer.TomcatSslConnectorProperties.class)
public class WebAppConfigurer extends WebMvcConfigurerAdapter {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new WeChatUrlImterceptor())
            .addPathPatterns("/wxHttpGet")
            .addPathPatterns("/wxHttpPost");
        super.addInterceptors(registry);
    }
    
    @Bean
    public EmbeddedServletContainerFactory servletContainer(TomcatSslConnectorProperties properties) {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addAdditionalTomcatConnectors(createSslConnector(properties));
        return tomcat;
    }
    
    private Connector createSslConnector(TomcatSslConnectorProperties properties) {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        properties.configureConnector(connector);
        return connector;
    }
    
    @ConfigurationProperties(prefix = "custom.tomcat.https")
    public static class TomcatSslConnectorProperties {
        private Integer port;
        
        private Boolean ssl = true;
        
        private Boolean secure = true;
        
        private String scheme = "https";
        
        private File keyStore;
        
        private String keyStorePassword;
        
        private String keyAlias;
        
        private String keyStoreType;
        
        public void configureConnector(Connector connector) {
            if (port != null) {
                connector.setPort(port);
            }
            if (secure != null) {
                connector.setSecure(secure);
            }
            if (scheme != null) {
                connector.setScheme(scheme);
            }
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            if (ssl != null && true == ssl) {
                protocol.setSSLEnabled(true);
            }
            if (keyStore != null && keyStore.exists()) {
                protocol.setKeystoreFile(keyStore.getAbsolutePath());
                protocol.setKeystorePass(keyStorePassword);
                if (keyStoreType != null && !"".equals(keyStoreType)) {
                    protocol.setKeystoreType(keyStoreType);
                }
                protocol.setKeyAlias(keyAlias);
                protocol.setKeyPass(keyStorePassword);
            }
        }
        
        public Integer getPort() {
            return port;
        }
        
        public void setPort(Integer port) {
            this.port = port;
        }
        
        public Boolean getSsl() {
            return ssl;
        }
        
        public void setSsl(Boolean ssl) {
            this.ssl = ssl;
        }
        
        public Boolean getSecure() {
            return secure;
        }
        
        public void setSecure(Boolean secure) {
            this.secure = secure;
        }
        
        public String getScheme() {
            return scheme;
        }
        
        public void setScheme(String scheme) {
            this.scheme = scheme;
        }
        
        public File getKeyStore() {
            return keyStore;
        }
        
        public void setKeyStore(File keyStore) {
            this.keyStore = keyStore;
        }
        
        public String getKeyStorePassword() {
            return keyStorePassword;
        }
        
        public void setKeyStorePassword(String keyStorePassword) {
            this.keyStorePassword = keyStorePassword;
        }
        
        public String getKeyAlias() {
            return keyAlias;
        }
        
        public void setKeyAlias(String keyAlias) {
            this.keyAlias = keyAlias;
        }
        
        public String getKeyStoreType() {
            return keyStoreType;
        }
        
        public void setKeyStoreType(String keyStoreType) {
            this.keyStoreType = keyStoreType;
        }
    }
    
}
