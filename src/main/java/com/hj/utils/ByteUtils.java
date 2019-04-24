package com.hj.utils;

/**
 * 
 * @author jun.hai
 * @Description: TODO(byte工具类)
 * @date 2016年11月21日 下午4:07:30
 */
public class ByteUtils {

	/**
	 * 
	 * @author jun.hai @Description: TODO(4为byte（－128～127）转int) @param @param
	 * map @return String 返回类型 @date 2016年11月21日 下午4:07:43 @throws
	 */
	public static int byte2Int(byte[] b, int index) {
		return (b[index + 0] & 0xff) << 24 | (b[index + 1] & 0xff) << 16 | (b[index + 2] & 0xff) << 8
				| b[index + 3] & 0xff;
	}

	public static int toInt(int b) {
		return b >= 0 ? (int) b : (int) (b + 256);
	}
}
