package com.hj.utils;

import java.awt.GraphicsEnvironment;

/**
* @author WangZhiYong
* @date 创建时间：2016年4月25日 上午9:53:37
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class FontUtils {
	
	public static String[] getDefaultFonts(){
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fonts = environment.getAvailableFontFamilyNames();//获得系统字体
		for(int i = 0;i<fonts.length;i++){
			System.out.println(fonts[i]);
		}
		return fonts;
	}
}
