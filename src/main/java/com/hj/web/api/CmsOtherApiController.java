package com.hj.web.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.common.ControllerBase;
import com.hj.utils.HttpUtil;
import com.hj.utils.JsonUtils;
import com.hj.utils.MD5Utils;

@Controller
@RequestMapping("/api")
public class CmsOtherApiController extends ControllerBase {
	//访问的地址
	//测试
	//String domain = "https://appmltest.ccwb.cn";
	//正式
	String domain = "https://appml.ccwb.cn";
	String appkey = "d1cff959685e3093";
    String appsecret = "429c1474019b48442f62d75215abdcd3";
    
    /**
           * 获取新闻频道
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/getXinWenChannel")
    public Map<String, String> getXinWenChannel() throws Exception {
    	Map<String,String> param = new HashMap<String,String>();
    	qianMing(param);
    	//请求URL
    	String spenUrl = domain + "/api/ml/getChannelLists";
    	String doPost = HttpUtil.doPost(spenUrl, param);
    	System.out.println("----------------------------------");
    	System.out.println(doPost);
    	System.out.println("----------------------------------");
    	Map<String, String> jsonToMap = JsonUtils.jsonToMap(doPost);
		return jsonToMap;
    }
    
    /**
           * 获取新闻列表
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/getXinWenList")
    public Map<String, String> getXinWenList() throws Exception {
    	Map<String,String> param = new HashMap<String,String>();
    	qianMing(param);
    	//获取参数
    	String app_id = request.getParameter("app_id");//应用号ID
    	param.put("app_id", app_id);
    	String channel_id = request.getParameter("channel_id");//频道ID
    	param.put("channel_id", channel_id);
    	String title = request.getParameter("title");//关键字
    	param.put("title", title);
    	//请求URL
    	String spenUrl = domain + "/api/ml/getNewsLists";
    	String doPost = HttpUtil.doPost(spenUrl, param);
    	System.out.println("----------------------------------");
    	System.out.println(doPost);
    	System.out.println("----------------------------------");
    	Map<String, String> jsonToMap = JsonUtils.jsonToMap(doPost);
		return jsonToMap;
    }
    
    //拼签名参数
    public void qianMing(Map<String,String> param) {
    	//时间戳
		SimpleDateFormat formatter  = new SimpleDateFormat("yyyyMMddHHmmss");
		String  nonce = formatter.format(new Date());
		//待签名
		String content = "appkey="+appkey+"&appsecret="+appsecret+"&nonce="+nonce;
		//签名
		String signature = MD5Utils.MD5(content);
		signature = signature.toLowerCase();//使用toLowerCase()方法实现小写转换
    	param.put("appkey", appkey);
    	param.put("nonce", nonce);
    	param.put("signature", signature);
    }
}
