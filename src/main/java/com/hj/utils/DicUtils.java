package com.hj.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
* @ClassName: DicUtils 
* @Description: TODO(数据字典的处理) 
* @author weilesi
* @date 2014年10月14日 下午2:33:11 
*
 */
public class DicUtils {

	/**
	 * 以后需要填写在配置文件中
	* @Title: certTypeList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param  @return    设定文件 
	* @return List<Map<String,String>>    返回类型 
	* @author jun.hai  
	* @date 2014年10月14日 下午2:39:00 
	* @throws
	 */
	public static List<Map<String,String>> certTypeList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("0", "身份证");
		map.put("1", "护照");
		map.put("2", "军人证");
		map.put("3", "港澳通行证");
		map.put("4", "回乡证");
		map.put("5", "台胞证");
		map.put("6", "国际海员证");
		map.put("7", "外国人永久居留证");
		map.put("9", "其他");
		
		list.add(map);
		
		return list;
	}
	
	public static String getCertTypeName(String value){
		String name ="其他";
		List<Map<String,String>> list = certTypeList();
		for (Map<String, String> map : list) {
			Set<String> set = map.keySet();
			for (String key : set) {
				if(key.equals(value)){
					name = map.get(value);
					break;
				}
			}
		}
		return name;
	}
	
	/**
	 *  01  三星/经济 
		02  四星/舒适 
		03  五星/豪华 
		04  特色 
		05  准四星级 
		06  准五星级 
		07  三星级以下
	* @Title: getHotelStarName 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param  @param code
	* @param  @return    设定文件 
	* @return String    返回类型 
	* @author jun.hai  
	* @date 2014年10月23日 下午1:45:47 
	* @throws
	 */
	public static String getHotelStarName(String code){
		String name = "三星级以下";
		
		if("01".equals(code)){
			name="三星/经济";
		}
		else if("02".equals(code)){
			name="四星/舒适";
		}
		else if("03".equals(code)){
			name="五星/豪华";
		}
		else if("04".equals(code)){
			name="特色";
		}
		else if("05".equals(code)){
			name="准四星级";
		}
		else if("06".equals(code)){
			name="准五星级";
		}
		
		return name;
	}
}
