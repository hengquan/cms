package com.hj.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
* @ClassName: MsgUtils
* @Description: TODO(消息管理类)
* @author jun.hai
* @date 2015年1月24日 下午9:09:18
*
 */
public class MsgUtils {
	/**
	 * 
	* @Title: getResultMsg
	* @Description: TODO(转json前map的处理，操作成功则resultCode为空，否则有对应的错误编码)
	* @param  @param resultCode
	* @param  @param message
	* @param  @param params
	* @param  @return    设定文件
	* @return Map<String,Object>    返回类型
	* @author jun.hai 
	* @date 2015年1月24日 下午9:14:43
	* @throws
	 */
	public static Map<String,Object> getResultMsg(String resultCode,String message,Object...params){
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		
		/*Map<String,Object> result = new LinkedHashMap<String, Object>();
		result.put("resultCode", resultCode);
		result.put("message", message);*/
		
		map.put("resultCode", resultCode);
		map.put("message", message);
		
		for (int i = 0; i < params.length; i++) {
			map.put(params[i].toString(), params[++i]);
		}
		
		return map;
	}
	
	/**
	 * 
	 * @author jun.hai 
	 * @Description: TODO(json数据的返回,格式为：{status:"1";data:"你好"error:{"msg":"dds" "type":"1"}}) 
	 * @param @param map
	 * @return String    返回类型 
	 * @date 2016年5月7日 下午3:29:57
	 * @throws
	 */
	public static Map<String,Object> getJsonResult(int status,String errMsg,String errType,Object...params){
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		map.put("status", status);
		
		Map<String,Object> error = new LinkedHashMap<String, Object>();
		error.put("msg", errMsg);
		error.put("type", errType);
		map.put("error", error);
		
		for (int i = 0; i < params.length; i++) {
			map.put(params[i].toString(), params[++i]);
		}
		
		return map;
	}
}
