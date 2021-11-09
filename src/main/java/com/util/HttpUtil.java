package com.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author heyonghao
 * @Description: http请求常用工具类
 */
@Slf4j
public class HttpUtil {

    /**
     * doPost请求方式
     * @param url String类型
     * @param 'json String类型
     * @return 字符串
     * **/
    public static String doPostWithJsonStr(String url, String jsonStr) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(5000).setSocketTimeout(5000).setConnectTimeout(5000).build();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            httpPost.setConfig(config);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * doPost请求方式
     * @param url String类型
     * @param 'json String类型
     * @return 字符串
     * **/
    public static String doPostWithJsonStrToken(String url, String jsonStr,String token) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Access-Token",token);
            // 创建请求内容
            StringEntity entity = new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultString;
    }


    /**
     * doPost请求方式,表单提交
     * @param url String类型
     * @param param  Map类型
     * @return 字符串
     * **/
    public static String doPostForm(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    /**
     * post 提交文件
     * @param url         提交路径
     * @param filePath    文件全路径
     * @param content     文件MIME类型,额外表单内容
     * @param accessToken  token,没有为空
     * @return result
     */
    public static String doPostFileWithToken(String url, String filePath, String content,String accessToken)  {
        String result="";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Access_token", accessToken);
        // 把文件转换成流对象FileBody
        FileBody bin = new FileBody(new File(filePath));
        StringBody fileType = new StringBody(content, ContentType.create(
                "text/plain", Consts.UTF_8));
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                // 相当于<input type="file" name="file"/>
                .addPart("file", bin)
                .addPart("type", fileType)
                // 相当于<input type="text" name="userName" value=userName>
                .build();
        httpPost.setEntity(reqEntity);
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                result = EntityUtils.toString(entity, "UTF-8");
            }
           return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        throw new RuntimeException("服务端异常");
    }


    /**
     * @param   url  get请求。地址,携带请求参数
     * @throws IOException
     * @Description:
     */
    public static String sendGetRequest(String url) {
        String lines = "";
        try {
            // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
            URL getUrl = new URL(url);
            // 根据拼凑的URL，打开连接，URL.openConnection()函数会根据
            // URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            // 建立与服务器的连接，并未发送数据
            connection.connect();
            // 发送数据到服务器并使用Reader读取返回的数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String temp = "";
            while ((temp = reader.readLine()) != null) {
                lines = lines + temp;
            }
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * post提交,上传字节
     * @param url
     * @param content
     * @return
     */
    public static String sendPostRequest(String url, String content) {
        String line = "";
        try {
            URL postUrl = new URL(url);
            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            // 打开读写属性，默认均为false
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 设置请求方式，默认为GET
            connection.setRequestMethod("POST");
            // Post 请求不能使用缓存
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-type","text/html");
            connection.setInstanceFollowRedirects(true);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(content.getBytes("utf-8"));
            out.flush();
            out.close(); // flush and close
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println("read from server....");
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                line = line + tmp;
            }
            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    /**
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doGet(String url, Map<String, String> param) {
		String content =null;
		StringBuilder paramsb = new StringBuilder(200);
		if (param != null && !param.isEmpty()) {
			Iterator<String> names = param.keySet().iterator();
			String name = null, value = null;
			while (names.hasNext()) {
				name = names.next();
				value = param.get(name);
				paramsb.append(name + "=" + ((value == null) ? "" : value)+ "&");
			}
		}
		String paramstr = paramsb.toString();
		 //截取基本url和session参数
        String[] urlTemp = url.split("\\?JSESSIONID=");
		String httpurl = urlTemp[0];
		
		if (paramstr.length() > 0) {
			paramstr = paramstr.substring(0, paramstr.length() - 1);
			httpurl += ((httpurl.indexOf("?") == -1) ? "?" : "&") + paramstr;
		}
		log.info("HttpGet.1 request url:\n" + httpurl);
		HttpGet httpget = new HttpGet(httpurl);
		if (urlTemp.length > 1 && url.contains("/requestForPhone")) {// 如果是锁定接口，setcookie模拟登陆
			httpget.addHeader("Cookie", "JSESSIONID=" + urlTemp[1]);
		}
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			 response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			 content = EntityUtils.toString(entity,"UTF-8");
			log.info("HttpGet.2 response text:\n" + content);
			EntityUtils.consume(entity);
			
		} catch (SocketTimeoutException e) {
			httpget.abort();
			log.error("HttpGet.21 SocketTimeoutException, timeout=30000", e);
			return null;
		} catch (Exception e) {
			httpget.abort();
			log.error("HttpGet.22 Exception", e);
			return null;
		} finally {
			log.info("HttpGet.3 finished.");
			 //关闭所有资源连接
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
		return content;
	}

}
