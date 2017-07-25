package com.hj.wxmp.mobile.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hj.utils.DateUtils;
import com.hj.utils.HttpUtils;
import com.hj.utils.MD5Utils;
import com.hj.utils.SHA1Utils;
import com.hj.utils.UUIDUtils;
import com.hj.utils.WeixinUtils;
import com.hj.utils.XmlConverUtils;
import com.hj.utils.XmlUtils;
import com.hj.web.core.common.JsonMapper;
import com.hj.wxmp.core.WxPay;
import com.hj.wxmp.core.WxUser;
import com.hj.wxmp.mobile.entity.SendRedPack;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;
import net.sf.json.JSONObject;

/**
 * 
* @ClassName: Weixin 
* @Description: TODO(与微信相关的工具类) 
* @author weilesi
* @date 2015年2月17日 上午9:52:42 
*
 */
public class Weixin {
	
	private static Weixin uniqueInstance = null;
	
	private final static Logger logger = LoggerFactory.getLogger(Weixin.class);
	
	private HashSessions hashSessions = HashSessions.getInstance();

	public String token="";
	public String appId="";
	public String appSecret="";

	private Weixin() {
		token= Configurations.getToken();
		appId=Configurations.getAppId();
		appSecret=Configurations.getAppSecret();
	}

	public static Weixin getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Weixin();
		}
		return uniqueInstance;
	}
	
	/**
	 * 
	* @Title: getAccessToken
	* @Description: TODO(获取access_token,目前腾讯规定access_token在2个小时内有效，具体以返回的expires_in为准)
	* @param  @return    设定文件
	* @return String    返回类型
	* @author jun.hai 
	* @date 2015年2月17日 下午1:43:36
	* @throws
	 */
	public String getAccessToken(){
		long currentTime = DateUtils.getCurrentTimestamp();
		long accessTokenExpires = hashSessions.getAccessTokenExpires();
		if(currentTime < accessTokenExpires){
			return hashSessions.getAccessToken();
		}
		
		String params = "grant_type=client_credential&appid="+Configurations.getAppId()+"&secret="+Configurations.getAppSecret();
		
		String jsonStr = "";
		try {
			jsonStr = HttpUtils.sendByPost(ApiUrls.WEIXIN_ACCESS_TOKEN_URL, params);
		} catch (Exception e) {
			logger.error("getAccessToken:" + e.toString());
		}
		
		Map<String, Map<String, Object>> resultMap = new JsonMapper().json2Map(jsonStr);
		
		String access_token = ObjectUtils.toString(resultMap.get("access_token"));
		String expires_in = ObjectUtils.toString(resultMap.get("expires_in"));
		currentTime = currentTime + NumberUtils.toLong(expires_in)*1000 - 30*1000;
		hashSessions.setAccessToken(access_token, String.valueOf(currentTime));
		
		return access_token;
	}
	
	/**
	 * 
	* @Title: getJsApiTicket
	* @Description: TODO(获取jsapi_ticket,为生成签名算法-signature)
	* @param  @return    设定文件
	* @return String    返回类型
	* @author jun.hai 
	* @date 2015年2月17日 下午2:08:22
	* @throws
	 */
	public String getJsApiTicket(){
		long currentTime = DateUtils.getCurrentTimestamp();
		long jsapiTicketExpires = hashSessions.getJsapiTicketExpires();
		if(currentTime < jsapiTicketExpires){
			return hashSessions.getJsapiTicket();
		}
		
		String params = "access_token="+getAccessToken()+"&type=jsapi";
		
		String jsonStr = "";
		try {
			jsonStr = HttpUtils.sendByPost(ApiUrls.WEIXIN_JSAPI_TICKET_URL, params);
		} catch (Exception e) {
			logger.error("getJsApiTicket:" + e.toString());
		}
		
		Map<String, Map<String, Object>> resultMap = new JsonMapper().json2Map(jsonStr);
		
		String ticket = ObjectUtils.toString(resultMap.get("ticket"));
		String expires_in = ObjectUtils.toString(resultMap.get("expires_in"));
		currentTime = currentTime + NumberUtils.toLong(expires_in)*1000 - 30*1000;
		hashSessions.setJsapiTicket(ticket, String.valueOf(currentTime));
		
		return ticket;
	}
	
	/**
	 * 
	 * @Title: createSignature
	 * @Description: TODO(JS-SDK使用权限签名算法,签名生成规则如下：参与签名的字段包括noncestr（随机字符串）,
	 *               有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分）
	 *               。对所有待签名参数按照字段名的ASCII
	 *               码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&
	 *               key2=value2…）拼接成字符串string1
	 *               。这里需要注意的是所有参数名均为小写字符。对string1作sha1加密，字段名和字段值都采用原始值，不进行URL
	 *               转义。)
	 * @param @param noncestr
	 * @param @param jsapi_ticket
	 * @param @param timestamp
	 * @param @param currentPageUrl
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @author jun.hai
	 * @date 2015年2月17日 下午2:22:12
	 * @throws
	 */
	public String createSignature(String noncestr,String jsapi_ticket,long timestamp,String currentPageUrl){
		String param = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+currentPageUrl;
		
		return SHA1Utils.encode(param);
	}
	
	/**
	 * 
	 * 从微信服务器下载图片到自己的服务器
	 * @param mediaId 文件的id
	 * @throws Exception
	 */
	public void downloadImageToDisk(String mediaId,String fileName) throws Exception {
		InputStream inputStream = getInputStream(mediaId);
		byte[] data = new byte[1024];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		try {
			String pathAndName = Configurations.getFileRepository()+File.separator+fileName;
			
			fileOutputStream = new FileOutputStream(pathAndName);
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void downloadVideoToDisk(String mediaId,String fileName) throws Exception {
		InputStream inputStream = getInputStream(mediaId);
		byte[] data = new byte[1024];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		try {
			String pathAndName = Configurations.getFileRepository()+File.separator+fileName;
			String sourceName = Configurations.getFileRepository()+File.separator+"source.amr";
			
			fileOutputStream = new FileOutputStream(sourceName);
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}
			
			changeToMp3(sourceName,pathAndName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * amr转换mp3
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年10月24日
	 */
	 public static void changeToMp3(String sourcePath, String targetPath) {  
	        File source = new File(sourcePath);  
	        File target = new File(targetPath);  
	        AudioAttributes audio = new AudioAttributes();  
	        Encoder encoder = new Encoder();  
	  
	        audio.setCodec("libmp3lame");  
	        EncodingAttributes attrs = new EncodingAttributes();  
	        attrs.setFormat("mp3");  
	        attrs.setAudioAttributes(audio);  
	  
	        try {  
	            encoder.encode(source, target, attrs);
	            if(source.exists()){  
	            	  
	            	source.delete();  
	            }
	        } catch (IllegalArgumentException e) {  
	        } catch (InputFormatException e) {  
	        } catch (EncoderException e) {  
	        }  
	   }  
	
	/**
	 * 根据文件id下载文件
	 * @param mediaId 媒体id
	 * @throws Exception 
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	private InputStream getInputStream(String mediaId) throws Exception {
		String accessToken = getAccessToken();
		InputStream is = null;
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="
				+ accessToken + "&media_id="+mediaId;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);

			http.connect();
			// 获取文件转化为byte流
			is = http.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return is;
	}
	
	/**
	 * 
	* @Title: getUserInfo
	* @Description: TODO(获取微信用户基本信息)
	* @param  @return    设定文件
	* @return WxUser    返回类型
	* @author jun.hai 
	* @date 2016年3月22日 下午1:43:36
	* @throws
	 */
	public WxUser getUserInfo(String openId){
		String accessToken = this.getAccessToken();
		String params = "access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
		
		String jsonStr = "";
		try {
			jsonStr = HttpUtils.sendByGet(ApiUrls.WEIXIN_USER_INFO_URL, params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getUserInfo:" + e.toString());
		}
		
		try {
			Map<String, Map<String, Object>> resultMap = new JsonMapper().json2Map(jsonStr);
			String errcode = ObjectUtils.toString(resultMap.get("errcode"));
			if(StringUtils.isBlank(errcode)){
				String subscribe = ObjectUtils.toString(resultMap.get("subscribe"));
				if("0".equals(subscribe)){
					WxUser wxUser = new WxUser(false);
					wxUser.setSubscribe(subscribe);
					wxUser.setOpenid(openId);
					
					return wxUser;
				}else{
					WxUser wxUser = new WxUser(false);
					wxUser.setOpenid(openId);
					wxUser.setSubscribe(subscribe);
					wxUser.setCity(ObjectUtils.toString(resultMap.get("city")));
					wxUser.setNickname(ObjectUtils.toString(resultMap.get("nickname")));
					wxUser.setSex(ObjectUtils.toString(resultMap.get("sex")));
					wxUser.setLanguage(ObjectUtils.toString(resultMap.get("language")));
					wxUser.setProvince(ObjectUtils.toString(resultMap.get("province")));
					wxUser.setCountry(ObjectUtils.toString(resultMap.get("country")));
					wxUser.setHeadimgurl(ObjectUtils.toString(resultMap.get("headimgurl")));
					wxUser.setSubscribe_time(ObjectUtils.toString(resultMap.get("subscribe_time")));
					wxUser.setUnionid(ObjectUtils.toString(resultMap.get("unionid")));
					wxUser.setRemark(ObjectUtils.toString(resultMap.get("remark")));
					wxUser.setGroupid(ObjectUtils.toString(resultMap.get("groupid")));
					
					return wxUser;
				}
			}else{
				logger.error("getUserInfo:" + resultMap.get("errmsg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public String createPaySign(String urlParam,String key){
		//获取sign，第一步:ASCII 码从小到大排序(字典序)后,使用 URL 键值对的 格式(即 key1=value1&key2=value2...)拼接成字符串 string1,注意:值为空的参数丌参不 签名;
		//第二步,在 string1 最后拼接上 key=Key(商户支付密钥)得到 stringSignTemp 字符串,幵对 stringSignTemp 进行 md5 运算,再将得到的字符串所有字符转换为大写
		
		String signTemp = urlParam + "&key="+key;
		
		return MD5Utils.MD5(signTemp).toUpperCase();
	}
	
	/**
	 * @description 统一支付接口
	 * @return
	 */
	public Map<String,Object> wxPay(WxPay wxPay){
		/*官方样例xml
		 * <xml>
			<appid>wx2421b1c4370ec43b</appid> 
			<attach><![CDATA[att1]]></attach>
			<body><![CDATA[JSAPI 支付测试]]></body> 
			<device_info>1000</device_info>
			<mch_id>10000100</mch_id> 
			<nonce_str>b927722419c52622651a871d1d9ed8b2</nonce_str> 
			<notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.php</notify_url> 
			<out_trade_no>1405713376</out_trade_no> 
			<spbill_create_ip>127.0.0.1</spbill_create_ip>
			<total_fee>1</total_fee>
			<trade_type>JSAPI</trade_type> 
			<sign><![CDATA[3CA89B5870F944736C657979192E1CF4]]></sign> 
		</xml>*/
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		String nonce_str = UUIDUtils.getUUIDKey();
		//获取sign，第一步:ASCII 码从小到大排序(字典序)后,使用 URL 键值对的 格式(即 key1=value1&key2=value2...)拼接成字符串 string1,注意:值为空的参数丌参不 签名;
		StringBuffer string1 = new StringBuffer();
		string1.append("appid=").append(Configurations.getAppId());
		string1.append("&attach=").append(wxPay.attach);
		string1.append("&body=").append(wxPay.body);
		string1.append("&mch_id=").append(Configurations.getMchId());
		string1.append("&nonce_str=").append(nonce_str);
		string1.append("&notify_url=").append(Configurations.getNotifyUrl());
		string1.append("&openid=").append(wxPay.openid);
		string1.append("&out_trade_no=").append(wxPay.out_trade_no);
		string1.append("&spbill_create_ip=").append(wxPay.spbill_create_ip);
		string1.append("&total_fee=").append(wxPay.total_fee);
		string1.append("&trade_type=").append("JSAPI");
		
		logger.error("string1:"+string1);
		
		//第二步,在 string1 最后拼接上 key=Key(商户支付密钥)得到 stringSignTemp 字符串,幵对 stringSignTemp 进行 md5 运算,再将得到的字符串所有字符转换为大写
		String sign = createPaySign(string1.toString(),Configurations.getPayKey());
		
		logger.error("sign={}",sign);
		
		
		StringBuffer paramXml = new StringBuffer("<xml>");
		paramXml.append("<appid>").append(Configurations.getAppId()).append("</appid>");
		paramXml.append("<attach><![CDATA[").append(wxPay.attach).append("]]></attach>");
		paramXml.append("<body><![CDATA[").append(wxPay.body).append("]]></body>");
		paramXml.append("<mch_id>").append(Configurations.getMchId()).append("</mch_id>");
		paramXml.append("<nonce_str>").append(nonce_str).append("</nonce_str>");
		paramXml.append("<notify_url>").append(Configurations.getNotifyUrl()).append("</notify_url>");
		paramXml.append("<out_trade_no>").append(wxPay.out_trade_no).append("</out_trade_no>");
		paramXml.append("<openid>").append(wxPay.openid).append("</openid>");
		paramXml.append("<spbill_create_ip>").append(wxPay.spbill_create_ip).append("</spbill_create_ip>");
		paramXml.append("<total_fee>").append(wxPay.total_fee).append("</total_fee>");
		paramXml.append("<trade_type>JSAPI</trade_type> ");
		paramXml.append("<sign><![CDATA[").append(sign).append("]]></sign> ");
		paramXml.append("</xml>");
		
		try {
			//String xml = HttpUtils.sendByPost(ApiUrls.WEIXIN_UNIFIEDORDER, paramXml.toString());
			String xml = HttpUtils.httpsRequest(ApiUrls.WEIXIN_UNIFIEDORDER, "POST", paramXml.toString());
			//String xml = PayCommonUtil.getPayNo(ApiUrls.WEIXIN_UNIFIEDORDER, paramXml.toString());
			
			result = XmlConverUtils.xml2Map(xml);
			logger.debug("resultXML{}",xml);
			System.out.println("result:"+result);
		} catch (Exception e) {
			logger.error("wxPay--≥"+e.toString());
		}
		
		return result;
	}
	
	 /**
	  * 微信红包接口-拼参数
	  * @param redPack   (微信红包参数类)
	  * @author zhang.hengquan 
	  * @date 2016年10月24日 上午14:19:36
	  * @return
	  */
	private String createSendRedPackOrderSign(SendRedPack redPack){
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		String nonce_str = UUIDUtils.getUUIDKey();
		//
        StringBuffer sign = new StringBuffer();
        sign.append("act_name=").append(redPack.getAct_name());
        sign.append("&client_ip=").append(redPack.getClient_ip());
        sign.append("&mch_billno=").append(redPack.getMch_billno());
        sign.append("&mch_id=").append(redPack.getMch_id());
        sign.append("&nonce_str=").append(nonce_str);
        sign.append("&re_openid=").append(redPack.getRe_openid());
        sign.append("&remark=").append(redPack.getRemark());
        sign.append("&send_name=").append(redPack.getSend_name());
        sign.append("&total_amount=").append(redPack.getTotal_amount());
        sign.append("&total_num=").append(redPack.getTotal_num());
        sign.append("&wishing=").append(redPack.getWishing());
        sign.append("&wxappid=").append(redPack.getWxappid());
		
		//第二步,在 sign 最后拼接上 key=Key(商户支付密钥)得到 stringSignTemp 字符串,幵对 stringSignTemp 进行 md5 运算,再将得到的字符串所有字符转换为大写
		String stringSignTemp = createPaySign(sign.toString(),Configurations.getPayKey());
		
		logger.error("sign:"+sign);
		
		StringBuffer paramXml = new StringBuffer("<xml>");
		paramXml.append("<sign><![CDATA[").append(stringSignTemp).append("]]></sign>");
		paramXml.append("<mch_billno><![CDATA[").append(redPack.getMch_billno()).append("]]></mch_billno>");
		paramXml.append("<mch_id><![CDATA[").append(Configurations.getMchId()).append("]]></mch_id>");
		paramXml.append("<wxappid><![CDATA[").append(Configurations.getAppId()).append("]]></wxappid>");
		paramXml.append("<send_name><![CDATA[").append(redPack.getSend_name()).append("]]></send_name>");
		paramXml.append("<re_openid><![CDATA[").append(redPack.getRe_openid()).append("]]></re_openid>");
		paramXml.append("<total_amount><![CDATA[").append(redPack.getTotal_amount()).append("]]></total_amount>");
		paramXml.append("<total_num><![CDATA[").append(redPack.getTotal_num()).append("]]></total_num>");
		paramXml.append("<wishing><![CDATA[").append(redPack.getWishing()).append("]]></wishing>");
		paramXml.append("<client_ip><![CDATA[").append(redPack.getClient_ip()).append("]]></client_ip>");
		paramXml.append("<act_name><![CDATA[").append(redPack.getAct_name()).append("]]></act_name> ");
		paramXml.append("<remark><![CDATA[").append(redPack.getRemark()).append("]]></remark>");
		paramXml.append("<nonce_str><![CDATA[").append(nonce_str).append("]]></nonce_str>");
		paramXml.append("</xml>");
		
        return paramXml.toString();
    }
	
	
	/**
	 * 获取微信商户平台证书文件
	 * @author zhang.hengquan 
	 * @date 2016年10月24日 上午14:19:36
	 * @return
	 * @throws Exception
	 */
	private CloseableHttpClient checkCertificate() throws Exception{
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(Configurations.getConfig("keyStore_filepath")));
        //FileInputStream instream = new FileInputStream(new File("../static/zhengshu/apiclient_cert.p12"));
        try {
            keyStore.load(instream, Configurations.getMchId().toCharArray());
        } finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, Configurations.getMchId().toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,"TLSv1".split(" ") , null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        return httpclient;
	}
	
	
	/**
	 * 微信红包接口-发送请求
	 * @param httpclient  (微信证书文件)
	 * @param entitys   (请求微信红包的参数)
	 * @param url  (请求微信红包的URL地址)
	 * @author zhang.hengquan 
	 * @date 2016年10月24日 上午14:19:36
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> sendByPost(CloseableHttpClient httpclient,String entitys,String url) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		String text = "";
		StringBuffer sbf = new StringBuffer();
		try {
			HttpPost httpPost = new HttpPost(url);//创建HttpPost对象
			if(entitys != null){
				httpPost.setEntity(new StringEntity(entitys, "utf-8"));
			}
			
			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
			// 用逗号分隔显示可以同时接受多种编码
			httpPost.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
			httpPost.setHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
			CloseableHttpResponse response = httpclient.execute(httpPost);
//			RequestEntity entity = new StringRequestEntity(content, contentType,DEFAULT_ENCODING);
			try {
				HttpEntity entity = response.getEntity();
				System.out.println(entity);
				if (entity != null) {
					System.out.println("----------------------------------------");
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"utf-8"));
					while ((text = bufferedReader.readLine()) != null) {
						sbf.append(text);
						System.out.println(text);
					}
					System.out.println("----------------------------------------");
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		String string = sbf.toString();
		result = XmlConverUtils.xml2Map(sbf.toString());
		return result;
	}
	
	/**
	 * 发送红包
	 * @param openid  (接收红包的openid)
	 * @param totalAmount  (红包金额)
	 * @author zhang.hengquan 
	 * @date 2016年10月24日 上午14:19:36
	 * @return
	 */
	public Map<String, Object> toSendRedPack(String openid,int totalAmount){
		//获取参数
		SendRedPack sendRedPack = new SendRedPack();
		String mchId = Configurations.getMchId();
		String mchBillno = getMchBillno(mchId);
		String appId = Configurations.getAppId();
		Integer total_num = 1;
		String ip = Configurations.getConfig("wx_redpack_ip");
		String sendName = Configurations.getConfig("wx_redpack_sendName");
		String wishing = Configurations.getConfig("wx_redpack_wishing");
		String act_name =Configurations.getConfig("wx_redpack_act_name");
		String remark=Configurations.getConfig("wx_redpack_remark");
		//设置参数
		sendRedPack.setMch_id(mchId);
		sendRedPack.setMch_billno(mchBillno);
		sendRedPack.setWxappid(appId);
		sendRedPack.setSend_name(sendName);
		sendRedPack.setRe_openid(openid);
		sendRedPack.setTotal_amount(totalAmount);
		sendRedPack.setTotal_num(total_num);
		sendRedPack.setWishing(wishing);
		sendRedPack.setAct_name(act_name);
		sendRedPack.setRemark(remark);
		sendRedPack.setClient_ip(ip);
		
		Map<String, Object> msg = new HashMap<String,Object>();
		try {
			//调用证书
			CloseableHttpClient checkCertificate = checkCertificate();
			//设置签名及拼参数
			String xmlMsg = createSendRedPackOrderSign(sendRedPack);
			//发送红包请求
			msg = sendByPost(checkCertificate,xmlMsg,ApiUrls.WEIXIN_SENDREDPACK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return msg;
	}
	
	
	/**
	 * 生成MchBillno
	 * @param mch_id  
	 * @author zhang.hengquan 
	 * @date 2016年10月24日 上午14:19:36
	 * @return
	 */
    private String getMchBillno(String mch_id){
        //商户订单号（每个订单号必须唯一）
        //组成： mch_id+yyyymmdd+10位一天内不能重复的数字。
        //接口根据商户订单号支持重入， 如出现超时可再调用。
        Date time = new Date();
        SimpleDateFormat format =  new SimpleDateFormat("yyyyMMdd");
        String d = format.format(time);  
        StringBuffer count = new StringBuffer();
        
        while(count.length() < 10){
        	int r = (int)(Math.random()*10);
        	count = count.append(r);
        }
        System.out.println(mch_id+d+count);
        return mch_id+d+count;
    }
	
	
	
	
	
	/**
	 * 
	 * @author jun.hai 
	 * @Description: TODO(发送text类型的客服消息) 
	 * @param @param map
	 * @return String    返回类型 
	 * @date 2016年5月23日 下午4:27:52
	 * @throws
	 */
	public String sendCustomMsg(String openid,String msg){
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+getAccessToken();
		
		String json = "{\"touser\":\""+openid+"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+msg+"\"}}";
		 
		try {
			String result = HttpUtils.sendByPost(url, json);
			
			logger.error("custom-send:"+result);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @author jun.hai 
	 * @Description: TODO(发送模版消息) 
	 * @param @param map
	 * @return String    返回类型 
	 * @date 2016年5月24日 上午11:45:21
	 * @throws
	 */
	public String sendTemplateMsg(String openid,String template_id,String linkUrl,String data){
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+getAccessToken();
		
		String json = "{\"touser\":\""+openid+"\",\"template_id\":\""+template_id+"\",\"url\":\""+linkUrl+"\",\"data\":{"+data+"}}";
		
		logger.error("template-send-json:"+json);
		
		try {
			String result = HttpUtils.sendByPost(url, json);
			
			logger.error("template-send:"+result);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	* 通过access_token获取ticket(ticket可换取二维码)
	* @Title: getTicket
	* @Description: TODO(通过access_token获取ticket,二维码的有效时间，以秒为单位。最大不超过1800。 目前腾讯规定ticket在30分钟内有效，具体以返回的expire_seconds为准)
	* @param  @return    设定文件
	* @return String    返回类型
	* @author deng.hemei 
	* @date 2016年7月2日 上午11:43:36
	* @throws
	 */
	public String getTicket(String openid){
		String accessToken = this.getAccessToken();
		long currentTime = DateUtils.getCurrentTimestamp();
		long ticketExpires = hashSessions.getTicketExpires();
		if(currentTime < ticketExpires){
			return hashSessions.getTicket();
		}
		String jsonStr = "";
		try {
			String jsonMsg = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+openid+"\"}}}";
			
			String requestUrl = ApiUrls.WEIXIN_QRCODE_TICKET_URL + "?access_token="+accessToken;
			JSONObject jsonObject = WeixinUtils.httpRequest(requestUrl, "POST", jsonMsg,null);
			if(null != jsonObject){
				System.out.println(jsonObject);
				jsonStr = jsonObject+"";
			}
		} catch (Exception e) {
			logger.error("getTicket:" + e.toString());
		}
		
		Map<String, Map<String, Object>> resultMap = new JsonMapper().json2Map(jsonStr);
		
		String ticket = ObjectUtils.toString(resultMap.get("ticket"));
		String expires_seconds = ObjectUtils.toString(resultMap.get("expire_seconds"));
		currentTime = currentTime + NumberUtils.toLong(expires_seconds)*1000 - 30*1000;
		hashSessions.setTicket(ticket, String.valueOf(currentTime));
		
		return ticket;
	}
	
	/**
	* 通过ticke换取二维码
	* @Title: getTicket
	* @Description: TODO(通过ticke换取二维码,正确应返回是一张图片，可下载或直接展示)
	* @param  @return    设定文件
	* @return String    返回类型
	* @author deng.hemei 
	* @date 2016年7月2日 上午11:43:36
	* @throws
	 */
	public String getQrcode(String openid){
		String ticket = "";
		
		long currentTime = DateUtils.getCurrentTimestamp();
		long ticketExpires = hashSessions.getTicketExpires();
		if(currentTime < ticketExpires){
			ticket =  hashSessions.getTicket();
		}else{
			ticket = this.getTicket(openid);
		}
		
		//图片名称
		String filename = System.currentTimeMillis()+".jpg";
		try {
			ticket = URLEncoder.encode(ticket, "UTF-8");
			String requestUrl = ApiUrls.WEIXIN_QRCODE_CREATE_URL +"?ticket="+ticket;
			//图片保存地址
			String fileAddress = Configurations.getFileRepository();
			FileOutputStream output = new FileOutputStream(fileAddress+File.separator + filename);
			JSONObject jsonObject = WeixinUtils.httpRequest(requestUrl, "GET", null,output);
			if(null != jsonObject){
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.out.println(jsonObject+"");
			}
			//jsonStr = HttpUtils.sendByGet(ApiUrls.WEIXIN_QRCODE_CREATE_URL, params);
		} catch (Exception e) {
			logger.error("getQrcode:" + e.toString());
		}
		
		return filename;
	}
	
	
	//关注时功能－－－－－－－start
	//验证
	public boolean valid(String signature,String timestamp,String nonce)
    {
        return checkSignature(signature,timestamp,nonce);	
    }

	//分析数据
	@SuppressWarnings("unchecked")
	public Map<String, Object>  analysis(String postStr)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		//取post数据
		//分析数据
		if (null != postStr){		
			Document doc = null;
			try {
				doc = DocumentHelper.parseText(postStr);
			} catch (DocumentException e) {
				logger.error("Weixin", "analysis  xml error:"+postStr);
				
				e.printStackTrace();				
			}

			map = XmlUtils.Dom2Map(doc);
			if(null != map){
				Object obj = map.get("postObj");
				if(null == obj){
					obj = map.get("PostObj");
				}
				Map<String, Object> postObj = null;
				if(null != obj){
					postObj = (Map<String, Object>)obj;
					
					map.put("postObjEvent", postObj.get("Event"));
					map.put("postObjEventKey", postObj.get("EventKey"));
					map.put("postObjContent", postObj.get("Content"));					
				}				
			}else{
				logger.error("Weixin", "analysis  xml error:"+postStr);
			}
		}

		return map;
	}

	public String responseMsg()
	{
		return "";
	}
	
	
	//创建菜单json
	public String  createMenu(String json)
	{
		//发送自定义菜单
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

		return this.sendMsg(url,json);
	}
	
	public String  sendMsg(String url,String data)
	{
		String accessToken = this.getAccessToken();
		url += accessToken;
		logger.info("accessToken:"+accessToken);
		String jsonStr = "";
		try {
			jsonStr = HttpUtils.sendByPost(url, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Weixin", "sendMsg:"+e.getMessage());
		}
		
		return jsonStr;
	}
	
	//验证sign
	private boolean checkSignature(String signature,String timestamp,String nonce)
	{
		String tmpStr = signature;
				
		String[] tmpArr={token,timestamp,nonce};  
        Arrays.sort(tmpArr);  
        tmpStr=this.ArrayToString(tmpArr);  
        tmpStr=this.SHA1Encode(tmpStr);  
        
		if( tmpStr.equalsIgnoreCase(signature) ){
			return true;
		}else{
			logger.error("Weixin", "signature:"+signature +"  != "+" tmpStr:"+tmpStr);
			return false;
		}
	}
	
	
	//sha1加密  
    public String SHA1Encode(String sourceString) {  
        String resultString = null;  
        try {  
           resultString = new String(sourceString);  
           MessageDigest md = MessageDigest.getInstance("SHA-1");  
           resultString = byte2hexString(md.digest(resultString.getBytes()));  
        } catch (Exception ex) {  
        }  
        return resultString;  
    }  
    
    public final String byte2hexString(byte[] bytes) {  
        StringBuffer buf = new StringBuffer(bytes.length * 2);  
        for (int i = 0; i < bytes.length; i++) {  
            if (((int) bytes[i] & 0xff) < 0x10) {  
                buf.append("0");  
            }  
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));  
        }  
        return buf.toString().toUpperCase();  
    }  
    
    //数组转字符串  
    public String ArrayToString(String [] arr){  
        StringBuffer bf = new StringBuffer();  
        for(int i = 0; i < arr.length; i++){  
         bf.append(arr[i]);  
        }  
        return bf.toString();  
    }  

	
	
    //关注时功能－－－－－－end
	
	
	
	//获取证书
//	private String ssl(String url,String data){
//        StringBuffer message = new StringBuffer();
//        try {
//            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
//            FileInputStream instream = new FileInputStream(new File(Configurations.getConfig("keyStore_filepath")));
//            keyStore.load(instream, Configurations.getMchId().toCharArray());
//         // Trust own CA and all self-signed certs
//            SSLContext sslcontext = SSLContexts.custom()
//                    .loadKeyMaterial(keyStore, Configurations.getMchId().toCharArray())
//                    .build();
//            // Allow TLSv1 protocol only
//            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//                    sslcontext,
//                    new String[] { "TLSv1" },
//                    null,
//                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//            CloseableHttpClient httpclient = HttpClients.custom()
//                    .setSSLSocketFactory(sslsf)
//                    .build();
//            HttpPost httpost = new HttpPost(url);
//
//            httpost.addHeader("Connection", "keep-alive");
//            httpost.addHeader("Accept", "*/*");
//            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//            httpost.addHeader("Host", "api.mch.weixin.qq.com");
//            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
//            httpost.addHeader("Cache-Control", "max-age=0");
//            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
//            httpost.setEntity(new StringEntity(data, "UTF-8"));
//            System.out.println("executing request" + httpost.getRequestLine());
//
//            CloseableHttpResponse response = httpclient.execute(httpost);
//            try {
//                HttpEntity entity = response.getEntity();
//
//                System.out.println("----------------------------------------");
//                System.out.println(response.getStatusLine());
//                if (entity != null) {
//                    System.out.println("Response content length: " + entity.getContentLength());
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
//                    String text;
//                    while ((text = bufferedReader.readLine()) != null) {
//                        message.append(text);
//                    }
//
//                }
//                EntityUtils.consume(entity);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                response.close();
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        } 
//
//        return message.toString();
//    }
	
	
	public static void main(String[] args) {
		
		System.out.println("date:"+new Date());
		
		System.out.println("timestamp:"+new Date().getTime());
		
		System.out.println("callander:"+ Calendar.getInstance().getTimeInMillis());
		
		System.out.println("DateUtils:"+DateUtils.getCurrentTimestamp());
	}
	
	
	
}
