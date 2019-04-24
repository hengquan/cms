package com.hj.utils;

import java.math.BigDecimal;

/**
 * @author hj
 * @description 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精确的浮点数运算，包括加减乘除和四舍五入。
 * @date 2006-08-23
 */
public class ArithmeticUtils {

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	// 这个类不能实例化

	private ArithmeticUtils() {

	}

	/** */
	/**
	 * 
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * 
	 * @param v2
	 *            加数
	 * 
	 * @return 两个参数的和
	 * 
	 */

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();
	}

	public static float add(float v1, float v2) {
		BigDecimal b1 = new BigDecimal(Float.toString(v1));

		BigDecimal b2 = new BigDecimal(Float.toString(v2));

		return b1.add(b2).floatValue();
	}

	/** */
	/**
	 * 
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * 
	 * @param v2
	 *            减数
	 * 
	 * @return 两个参数的差
	 * 
	 */

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();
	}

	public static float sub(float v1, float v2) {
		BigDecimal b1 = new BigDecimal(Float.toString(v1));

		BigDecimal b2 = new BigDecimal(Float.toString(v2));

		return b1.subtract(b2).floatValue();
	}

	/** */
	/**
	 * 
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * 
	 * @param v2
	 *            乘数
	 * 
	 * @return 两个参数的积
	 * 
	 */

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();
	}

	public static float mul(float v1, float v2) {
		BigDecimal b1 = new BigDecimal(Float.toString(v1));

		BigDecimal b2 = new BigDecimal(Float.toString(v2));

		return b1.multiply(b2).floatValue();
	}

	/** */
	/**
	 * 
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 
	 * 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @return 两个参数的商
	 * 
	 */

	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	public static float div(float v1, float v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/** */
	/**
	 * 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * 
	 * @return 两个参数的商
	 * 
	 */

	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {

			throw new IllegalArgumentException(

					"The scale must be a positive integer or zero");

		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static float div(float v1, float v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(

					"The scale must be a positive integer or zero");
		}

		BigDecimal b1 = new BigDecimal(Float.toString(v1));

		BigDecimal b2 = new BigDecimal(Float.toString(v2));

		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/** */
	/**
	 * 
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @return 四舍五入后的结果
	 * 
	 */

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(

					"The scale must be a positive integer or zero");
		}

		BigDecimal b = new BigDecimal(Double.toString(v));

		BigDecimal one = new BigDecimal("1");

		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static float round(float v, int scale) {
		if (scale < 0) {
			return 0;
		}

		BigDecimal b = new BigDecimal(Float.toString(v));

		BigDecimal one = new BigDecimal("1");

		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}
	
	/**
	 * 
	 * @author deng.hemei
	 * @description 
	 * @return 
	 * @updateDate 2016年6月24日
	 */
	public static BigDecimal fen2yuan(BigDecimal fen){
 	   BigDecimal one = new BigDecimal("100");  
 	   
 	   return fen.divide(one, 2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal round2bit(BigDecimal fen){
		if(fen==null || "".equals(fen)){
			return  new BigDecimal("0");
		}else{
			 BigDecimal one = new BigDecimal("1");  
		 	 return fen.divide(one, 2, BigDecimal.ROUND_HALF_UP);
		}
	 	  
	}
	
	public static void main(String[] args) {
		double a = round(Double.valueOf("12.999"), 2);
		System.out.println(a);
		System.out.println(BigDecimal.valueOf(a));
	}
	
	/**
	 * 四舍五入
	 * @param d
	 * @param index
	 * @return
	 */
	public static Double roundUpNumber(Double d, Integer index) {
		d = new BigDecimal(Double.toString(d)).setScale(index.intValue(), BigDecimal.ROUND_HALF_UP).doubleValue();
		return d;
	}

}
