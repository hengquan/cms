package com.hj.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.sf.json.JSONObject;

/**   
* @Title: Message.java
* @Description: TODO
* @author Wangzhiyong   
* @date 2016年6月22日 下午4:26:03
* @version V1.0   
*/
public class Message {
//	public static void main(String[] args) throws HttpException, IOException {
//
//		SimpleDateFormat df=new SimpleDateFormat("MMddHHmmss");		
//		String Stamp = df.format(new Date());
//		String password="123456";
//		String Secret=MD5.GetMD5Code(password+Stamp).toUpperCase();
//		
//		try {
//			JSONObject j=new JSONObject();
//			j.put("UserName", "dxcsh");
//			j.put("Stamp", Stamp);
//			j.put("Secret", Secret);
//			j.put("Moblie", "18611735639");
//			j.put("Text", "您的验证码是：8859【华信】");
//			j.put("Ext", "");
//			j.put("SendTime", "");
//			//获取json字符串
//			String json=j.toString();
//			byte[] data=json.getBytes("utf-8");
//			byte[] key=password.getBytes();
//			//获取加密的key
//			byte[] nkey=new byte[8];
//			System.arraycopy(key, 0, nkey, 0, key.length > 8 ? 8 : key.length);
//			//Des加密，base64转码
//			String str=new BASE64Encoder().encode(DesHelper.encrypt(data, nkey)); 
//			
//			System.out.println(str);
//			//url编码
//			//str=URLEncoder.encode(str, "utf-8");
//			
//			//发送http请求
//			String Url="http://114.113.154.5/ensms.ashx";
//			HttpClient client=new HttpClient();
//			PostMethod post=new PostMethod(Url);
//			post.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//			NameValuePair UserId=new NameValuePair("UserId","49156");
//			NameValuePair Text64=new NameValuePair("Text64",str);
//			post.setRequestBody(new NameValuePair[]{UserId,Text64});
//			int statu=client.executeMethod(post);
//			System.out.println("statu="+statu);
//			//返回结果
//			String result=post.getResponseBodyAsString();
//			System.out.println("result="+result);
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	public static String getCode(String tel) throws HttpException, IOException{
		long code = (long) (Math.random()*9000+1000);
		String Text="【华大智宝】校验码:"+code+",您正在使用华大智宝微信平台短信校验功能，（请勿向任何人提供您收到的短信校验码）";
		String Url="http://114.113.154.5/sms.aspx?action=send";
		HttpClient client=new HttpClient();
		PostMethod post=new PostMethod(Url);
		post.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		NameValuePair userid=new NameValuePair("userid","49156");
		NameValuePair account=new NameValuePair("account","dxcsh");
		NameValuePair password=new NameValuePair("password","123456");
		NameValuePair mobile=new NameValuePair("mobile",tel);
		NameValuePair content=new NameValuePair("content",Text);
		NameValuePair sendTime=new NameValuePair("sendTime","");
		NameValuePair extno=new NameValuePair("extno","");
		post.setRequestBody(new NameValuePair[]{userid,account,password,mobile,content,sendTime,extno});
		int statu=client.executeMethod(post);
		System.out.println("statu="+statu);
		String str=post.getResponseBodyAsString();
		System.out.println(str);
		try {
			//将字符转化为XML
			Document doc=DocumentHelper.parseText(str);
			//获取根节点
			Element rootElt=doc.getRootElement();
			//获取根节点下的子节点的值
			String returnstatus=rootElt.elementText("returnstatus").trim();
			String message=rootElt.elementText("message").trim();
			String remainpoint=rootElt.elementText("remainpoint").trim();
			String taskID=rootElt.elementText("taskID").trim();
			String successCounts=rootElt.elementText("successCounts").trim();
			
			System.out.println("返回状态为："+returnstatus);
			System.out.println("返回信息提示："+message);
			System.out.println("返回余额："+remainpoint);
			System.out.println("返回任务批次："+taskID);
			System.out.println("返回成功条数："+successCounts);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		return String.valueOf(code).toString();
	}
	
	public static void main(String[] args) throws HttpException, IOException {
		String code = getCode("15010919153");
		System.out.println(code);
	}
}