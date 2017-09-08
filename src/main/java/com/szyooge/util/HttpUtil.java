package com.szyooge.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.szyooge.constant.CharSet;

public class HttpUtil {
    
    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
    
    static {
        // 解决错误javax.net.ssl.SSLProtocolException: handshake alert: unrecognized_name
        System.setProperty ("jsse.enableSNIExtension", "false");
    }
    
    /**
     * 使用Get方式获取数据
     * 
     * @param url
     *            URL包括参数，http://HOST/XX?XX=XX&XXX=XXX
     * @param charset
     * @return
     */
    public static String sendGet(String url, String charset) {
        log.info("发起GET请求URL：" + url);
        log.info("发起GET应答字符编码：" + charset);
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！", e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                log.error("关闭GET请求输入流异常", e2);
            }
        }
        log.info("GET请求应答：" + result);
        return result;
    }
    
    public static String sendGet(String url, Map<String, String> param, String charset) {
        StringBuffer buffer = new StringBuffer(url);
        if (buffer.indexOf("?") > 0) {
            buffer.append('&');
        } else {
            buffer.append('?');
        }
        if (param != null && !param.isEmpty()) {
            try {
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    buffer.append(URLEncoder.encode(entry.getKey(), CharSet.UTF8))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), CharSet.UTF8))
                        .append("&");
                }
            } catch (UnsupportedEncodingException e) {
                log.error("发送 GET 请求出现异常", e);
            }
            
        }
        buffer.setLength(buffer.length() - 1);
        return sendGet(buffer.toString(), charset);
    }
    
    /**
     * POST请求，字符串形式数据
     * 
     * @param url
     *            请求地址
     * @param param
     *            请求数据
     * @param charset
     *            编码方式
     * @throws IOException 
     */
    public static String sendPostUrl(String url, String param, String charset) throws IOException {
        log.info("发起POST请求URL：" + url);
        log.info("发起POST请求参数：" + param);
        log.info("发起POST应答字符编码：" + charset);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoInput(true);
            if (StringUtil.isNotNEmpty(param)) {
                conn.setDoOutput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("关闭POST请求输入输出流异常", ex);
            }
        }
        log.info("POST请求应答：" + result);
        return result;
    }
    
    /**
     * POST请求，Map形式数据
     * 
     * @param url
     *            请求地址
     * @param param
     *            请求数据
     * @param charset
     *            编码方式
     */
    public static String sendPost(String url, Map<String, String> param, String charset) {
        log.info("发起POST请求URL：" + url);
        log.info("发起POST请求参数：" + param);
        log.info("发起POST应答字符编码：" + charset);
        StringBuffer buffer = new StringBuffer();
        if (param != null && !param.isEmpty()) {
            try {
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    buffer.append(URLEncoder.encode(entry.getKey(), CharSet.UTF8))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), CharSet.UTF8))
                        .append("&");
                }
            } catch (UnsupportedEncodingException e) {
                log.error("发送 POST 请求出现异常", e);
            }
            buffer.setLength(buffer.length() - 1);
        }
        
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
            // 发送POST请求必须设置如下两行
            conn.setDoInput(true);
            if (buffer != null && buffer.length() > 0) {
                conn.setDoOutput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(buffer);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送 POST 请求出现异常！", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        log.info("POST请求应答：" + result);
        return result;
    }
    
    /**
     * 获取客户端IP地址
     * 
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) throws UnknownHostException {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                inet = InetAddress.getLocalHost();
                ip = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        log.info("客户端IP=" + ip);
        return ip;
    }
    
    public static String wxSendGet(String url, String jsonStr, String charset) {
        log.info("发起GET请求URL：" + url);
        log.info("发起GET应答字符编码：" + charset);
        String result = "";
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            if (StringUtil.isNotEmpty(jsonStr)) {
                connection.setDoOutput(true);
                out = new PrintWriter(connection.getOutputStream());
                // 发送请求参数
                out.print(jsonStr);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！", e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                log.error("关闭GET请求输入流异常", e2);
            }
        }
        log.info("GET请求应答：" + result);
        return result;
    }
    
    public static String wxSendPost(String url, String jsonStr, String charset) {
        log.info("发起POST请求URL：" + url);
        log.info("发起POST请求参数：" + jsonStr);
        log.info("发起POST应答字符编码：" + charset);
        
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
            // 发送POST请求必须设置如下两行
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            if (StringUtil.isNotEmpty(jsonStr)) {
                conn.setDoOutput(true);
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(jsonStr);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送 POST 请求出现异常！", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        log.info("POST请求应答：" + result);
        return result;
    }
    
    public static String uploadFile(String url, String fileFullName, String charset) {
        log.info("发起POST请求参数：" + fileFullName);
        
        String result = "";
        File file = new File(fileFullName);
        String fileName = file.getName();
        InputStream fileIn = null;
		try {
			fileIn = new FileInputStream(file);
			result = uploadFile(url,fileName,fileIn,charset);
			log.info("POST请求应答：" + result);
		} catch (FileNotFoundException e) {
			log.error("文件不存在！", e);
		} finally {
			if(fileIn != null){
				try {
					fileIn.close();
				} catch (IOException e) {
					log.error("关闭文件流异常！", e);
				}
			}
		}
        
        return result;
    }
    
    public static String uploadFile(String url, String fileName, InputStream fileIn, String charset) {
    	log.info("发起POST请求URL：" + url);
    	log.info("发起POST请求参数：" + fileName);
    	log.info("发起POST应答字符编码：" + charset);
    	
    	OutputStream out = null;
    	BufferedReader in = null;
    	String result = "";
    	byte[] fileData = new byte[512];
    	int len = 0;
    	try {
    		URL realUrl = new URL(url);
    		// 打开和URL之间的连接
    		HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
    		// 设置通用的请求属性
    		conn.setRequestMethod("POST");
    		conn.addRequestProperty("FileName", fileName);
    		conn.setRequestProperty("content-type", "text/html");
    		// 发送POST请求必须设置如下两行
    		conn.setDoInput(true);
    		// 获取URLConnection对象对应的输出流
    		if (StringUtil.isNotEmpty(fileName)) {
    			conn.setDoOutput(true);
    			out = conn.getOutputStream();
    			while((len = fileIn.read(fileData)) > 0) {
    				// 发送请求参数
    				out.write(fileData, 0, len);
    			}
    			// flush输出流的缓冲
    			out.flush();
    		}
    		// 定义BufferedReader输入流来读取URL的响应
    		in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
    		String line;
    		while ((line = in.readLine()) != null) {
    			result += line;
    		}
    	} catch (Exception e) {
    		log.error("发送 POST 请求出现异常！", e);
    	}
    	// 使用finally块来关闭输出流、输入流
    	finally {
    		try {
    			if (out != null) {
    				out.close();
    			}
    			if (in != null) {
    				in.close();
    			}
    		} catch (IOException ex) {
    			ex.printStackTrace();
    		}
    	}
    	log.info("POST请求应答：" + result);
    	return result;
    }
    public static byte[] downloadFile(String url, String charset) {
    	log.info("发起POST请求URL：" + url);
    	log.info("发起POST应答字符编码：" + charset);
    	
    	OutputStream out = null;
    	InputStream in = null;
    	String result = "";
    	byte[] fileData = new byte[512];
    	int len = 0;
    	ByteBuilder bb = new ByteBuilder();
    	try {
    		URL realUrl = new URL(url);
    		// 打开和URL之间的连接
    		HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
    		// 设置通用的请求属性
    		conn.setRequestProperty("content-type", "text/html");
    		// 发送POST请求必须设置如下两行
    		conn.setDoInput(true);
    		
    		// 定义BufferedReader输入流来读取URL的响应
    		in = conn.getInputStream();
    		while ((len = in.read(fileData)) > 0) {
    			bb.append(fileData,len);
    		}
    		return bb.toBytes();
    	} catch (Exception e) {
    		log.error("发送 POST 请求出现异常！", e);
    	}
    	// 使用finally块来关闭输出流、输入流
    	finally {
    		try {
    			if (out != null) {
    				out.close();
    			}
    			if (in != null) {
    				in.close();
    			}
    		} catch (IOException ex) {
    			ex.printStackTrace();
    		}
    	}
    	log.info("POST请求应答：" + result);
    	return null;
    }
    public static void main(String[] args) {
        Map<String,String> param = new HashMap<String,String>();
        Map<String,String> data = new HashMap<String,String>();
        param.put("URL", "https://login.wx.qq.com/jslogin");
        data.put("appid", "wx782c26e4c19acffb");
        data.put("fun", "new");
        data.put("redirect", "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage");
        data.put("lang", "zh_CN");
        data.put("_", String.valueOf(new Date().getTime()));
        System.out.println(request(param,data,"UTF-8"));
    }
    /**
     * http请求
     * @param params {               <br/>
     *     URL     : URL,            <br/>
     *     Method  : METHOD,         <br/>
     *     Cookie  : COOKIE,         <br/>
     *     Payload : PAYLOAD,        <br/>
     *     ...                       <br/>
     * }                             <br/>
     * @param data 
     */
    public static Map<String,String> request(Map<String,String> params, Map<String,String> data, String charset){
        log.info("request开始");
        log.info(params.toString());
        log.info(data.toString());
        Map<String,String> resultMap = new HashMap<String,String>();
        StringBuilder url = new StringBuilder(params.remove("URL"));
        if(url.indexOf("?") > 0) {
            url.append("&");
        } else {
            url.append("?");
        }
        if(data != null && !data.isEmpty()) {
            for(Entry<String, String> item : data.entrySet()) {
                url.append(item.getKey()).append("=").append(item.getValue()).append("&");
            }
        }
        url.setLength(url.length()-1);
        
        String payload = params.remove("Payload");
        String cookie = params.remove("Cookie");
        String method = params.remove("Method");
        method = (StringUtil.isNotEmpty(method) && method.toUpperCase().equals("POST")) ? "POST" : "GET";
        
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url.toString());
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod(method);
            conn.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
            if(StringUtil.isNotEmpty(cookie)) {
                conn.setRequestProperty("Cookie",cookie);
            }
            if(!params.isEmpty()) {
                for(Entry<String, String> item : params.entrySet()) {
                    conn.setRequestProperty(item.getKey(),item.getValue());
                }
            }
            // 发送POST请求必须设置如下两行
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            if (StringUtil.isNotEmpty(payload)) {
                conn.setDoOutput(true);
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(payload);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String line = null;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            resultMap.put("Response", result);
            
            Map<String, List<String>> responseHead = conn.getHeaderFields();
            if(responseHead != null) {
                List<String> cookies = responseHead.get("Set-Cookie");
                if(cookies != null) {
                    StringBuilder setCookie = new StringBuilder();
                    for(String co : cookies) {
                        setCookie.append(co).append(";");
                    }
                    if(setCookie.length() > 0) {
                        setCookie.setLength(setCookie.length()-1);
                    }
                    resultMap.put("Set-Cookie", setCookie.toString());
                }
            }
        } catch (Exception e) {
            log.error("发送 request 请求出现异常！", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        log.info("request请求应答：" + resultMap);
        
        return resultMap;
    }
    
}
