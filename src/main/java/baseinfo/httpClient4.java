package baseinfo;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * created by chenminqing
 */
public class httpClient4 {

        private static String enc = "UTF-8";
        // 创建连接的最长时间
        private static int connectTimeout = 5000;
        // 连接池中获取到连接的最长时间
        private static int requestTineout = 4000;
        // 数据传输的最长时间
        private static int socketTimeout = 3000;

        /**
         * 发送HTTP Get请求
         *
         * @param url
         *            请求地址
         * @param headers
         *            请求头
         * @param params
         *			  请求参数
         * @param encode
         *            请求编码方式，默认UTF-8
         * @return
         */
        public HttpResponse httpGet(String url, Map<String, String> headers, Map<String, String> params, String encode) {
            HttpResponse response = new HttpResponse();
            String content = null;

            if (params != null) {
                if (url.indexOf("?") != -1) {
                    url = url;
                } else {// 未认证
                    url = url + getUrl(params);
                }
            }
            //	LogUtil.logger().info("GET请求URL：" + url);

            if (encode == null) {
                encode = "utf-8";
            }

            RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(requestTineout).build();
            // since 4.3 不再使用 DefaultHttpClient
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(defaultRequestConfig);
            // 设置header
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            CloseableHttpResponse httpResponse = null;
            try {
                httpResponse = closeableHttpClient.execute(httpGet);
                HttpEntity entity = httpResponse.getEntity();
                content = EntityUtils.toString(entity, encode);
                response.setBody(content);
                response.setHeaders(httpResponse.getAllHeaders());
                response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
                response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                // 关闭连接、释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        /**
         * 发送 HTTP Post 请求，参数以form表单键值对的形式提交。
         *
         * @param url
         *            请求地址
         * @param params
         *            请求参数
         * @param headers
         *            请求头信息
         * @param encode
         *            请求编码，默认UTF-8
         * @return
         */
        public HttpResponse httpPostForm(String url, Map<String, String> params, Map<String, String> headers,
                                         String encode) {
            HttpResponse response = new HttpResponse();
            if (encode == null) {
                encode = "utf-8";
            }
            RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(requestTineout).build();
            // HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
//		LogUtil.logger().info("请求URL："+url);
            httpPost.setConfig(defaultRequestConfig);
            // 设置header
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            // 组织请求参数
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            if (params != null && params.size() > 0) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    paramList.add(new BasicNameValuePair(key, params.get(key)));
                }
            }
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(paramList, encode));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            String content = null;
            CloseableHttpResponse httpResponse = null;
            try {
                httpResponse = closeableHttpClient.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();
                content = EntityUtils.toString(entity, encode);
                response.setBody(content);
                response.setHeaders(httpResponse.getAllHeaders());
                response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
                response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                // 关闭连接、释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        /**
         * 发送 HTTP Post 请求，参数以原生字符串进行提交
         *
         * @param url
         *            请求地址
         * @param stringJson
         *            请求参数
         * @param headers
         *            请求头
         * @param encode
         *            请求编码，默认UTF-8
         * @return
         */
        public HttpResponse httpPostRaw(String url, String stringJson, Map<String, String> headers, String encode) {
            HttpResponse response = new HttpResponse();
            if (encode == null) {
                encode = "utf-8";
            }
            RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(requestTineout).build();
            // HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(defaultRequestConfig);
            // 设置header
            httpPost.setHeader("Content-type", "application/json");
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            // 组织请求参数
            StringEntity stringEntity = new StringEntity(stringJson, encode);
            httpPost.setEntity(stringEntity);
            String content = null;
            CloseableHttpResponse httpResponse = null;
            try {
                // 响应信息
                httpResponse = closeableHttpClient.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();
                content = EntityUtils.toString(entity, encode);
                response.setBody(content);
                response.setHeaders(httpResponse.getAllHeaders());
                response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
                response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                // 关闭连接、释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        /**
         * 发送 HTTP Put 请求，参数以原生字符串进行提交
         *
         * @param url
         *            请求地址
         * @param stringJson
         *            请求参数
         * @param headers
         *            请求头
         * @param encode
         *            编码格式默认UTF-8
         * @return
         */
        public HttpResponse httpPutRaw(String url, String stringJson, Map<String, String> headers, String encode) {
            HttpResponse response = new HttpResponse();
            if (encode == null) {
                encode = "utf-8";
            }
            RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(requestTineout).build();
            // HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut(url);
            httpPut.setConfig(defaultRequestConfig);
            // 设置header
            httpPut.setHeader("Content-type", "application/json");
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPut.setHeader(entry.getKey(), entry.getValue());
                }
            }
            // 组织请求参数
            StringEntity stringEntity = new StringEntity(stringJson, encode);
            httpPut.setEntity(stringEntity);
            String content = null;
            CloseableHttpResponse httpResponse = null;
            try {
                // 响应信息
                httpResponse = closeableHttpClient.execute(httpPut);
                HttpEntity entity = httpResponse.getEntity();
                content = EntityUtils.toString(entity, encode);
                response.setBody(content);
                response.setHeaders(httpResponse.getAllHeaders());
                response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
                response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                // 关闭连接、释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        /**
         * 发送HTTP Delete请求
         *
         * @param url
         *            请求地址
         * @param headers
         *            请求头
         * @param encode
         *            请求编码方式，默认UTF-8
         * @return
         */
        public HttpResponse httpDelete(String url, Map<String, String> headers, String encode) {
            HttpResponse response = new HttpResponse();
            if (encode == null) {
                encode = "utf-8";
            }
            String content = null;
            RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(requestTineout).build();
            // since 4.3 不再使用 DefaultHttpClient
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
            HttpDelete httpDelete = new HttpDelete(url);
            httpDelete.setConfig(defaultRequestConfig);
            // 设置header
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpDelete.setHeader(entry.getKey(), entry.getValue());
                }
            }
            CloseableHttpResponse httpResponse = null;
            try {
                httpResponse = closeableHttpClient.execute(httpDelete);
                HttpEntity entity = httpResponse.getEntity();
                content = EntityUtils.toString(entity, encode);
                response.setBody(content);
                response.setHeaders(httpResponse.getAllHeaders());
                response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
                response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                // 关闭连接、释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        /**
         * 发送 HTTP Post 请求，支持文件上传
         *
         * @param url
         * @param params
         *            请求参数
         * @param files
         *            上传的文件列表
         * @param headers
         *            请求头
         * @param encode
         *            编码格式，默认UTF-8
         * @return
         */
        public static HttpResponse httpPostFormMultipart(String url, Map<String, String> params, List<File> files,
                                                         Map<String, String> headers, String encode) {
            HttpResponse response = new HttpResponse();

            if (encode == null) {
                encode = "utf-8";
            }
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            // 上传文件不需要设置超时时间
//		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
//				.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(requestTineout).build();
            HttpPost httpPost = new HttpPost(url);
            // httpPost.setConfig(defaultRequestConfig);
            // 设置header
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
            mEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            mEntityBuilder.setCharset(Charset.forName(encode));

            // 普通参数
            ContentType contentType = ContentType.create("text/plain", Charset.forName(encode));// 解决中文乱码
            if (params != null && params.size() > 0) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    mEntityBuilder.addTextBody(key, params.get(key), contentType);
                }
            }
            // 二进制参数
            if (files != null && files.size() > 0) {
                for (File file : files) {
                    mEntityBuilder.addBinaryBody("file", file);
                }
            }
            httpPost.setEntity(mEntityBuilder.build());
            String content = null;
            CloseableHttpResponse httpResponse = null;
            try {
                httpResponse = closeableHttpClient.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();
                content = EntityUtils.toString(entity, encode);
                response.setBody(content);
                response.setHeaders(httpResponse.getAllHeaders());
                response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
                response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                // 关闭连接、释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        /**
         * 据Map生成get请求URL中的参数字符串
         *
         * @param map
         *
         * @return String
         */
        public static String getUrl(Map<String, String> map) {
            if (null == map || map.size() == 0) {
                return "";
            }
            StringBuffer urlParams = new StringBuffer("?");
            for (Map.Entry<String, String> element : map.entrySet()) {
                String key = element.getKey();
                String value = element.getValue();
                if (!value.equals("")) {
                    // 对值进行编码
                    try {
                        value = URLEncoder.encode(value, enc);
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    urlParams.append(key).append("=").append(value).append("&");
                }
            }
            String paramStr = urlParams.toString();
            paramStr = paramStr.substring(0, paramStr.length() - 1);

            return paramStr;
        }

}
