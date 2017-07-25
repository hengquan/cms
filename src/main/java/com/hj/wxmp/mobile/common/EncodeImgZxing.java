package com.hj.wxmp.mobile.common;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hj.wxmp.mobile.entity.SysAdmin;

public final class EncodeImgZxing { 
	//二维码颜色
	private static final int BLACK = 0xFF000000;//0xFFFF0000，红色
	//二维码背景色
	private static final int WHITE = 0xFFFFFFFF;//0xFF0000FF，蓝色
	//注：二维码颜色色差大，扫描快，但如果"BLACK'设置为黑色外其他颜色，可能无法扫描
	//二维码图片宽度
	private static final int width = 300;
	//二维码图片高度
	private static final int height = 300;
	//二维码格式参数
	private static final EnumMap<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
	static{


		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 二维码边界空白大小 1,2,3,4 (4为默认,最大)
		hints.put(EncodeHintType.MARGIN, 1);
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MAX_SIZE, 350);
		hints.put(EncodeHintType.MIN_SIZE, 150);
	}
	/**
	 * 绘制二维码
	 * @param contents 二维码内容  
	 * @return image 二维码图片
	 * */
	public static BufferedImage encodeImg(String contents){
		BufferedImage image = null;
		try{
			BitMatrix matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			int width = matrix.getWidth();
			int height = matrix.getHeight();
			for(int x = 0; x < width; x++){
				for(int y =0;y < height; y++){
					image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
				}
			}
		}catch(Exception e){
			System.out.println("生成二维码失败"+e.getMessage());
		}
		return image;
	}
	
	/**
	 * 二维码输出到文件
	 * 	@param contents 二维码内容
	 * @param format 图片格式
	 * @param file 输出文件
	 * */
	public static void writeToFile(String contents,String format,File file){
		BufferedImage image = encodeImg(contents);
		try {
			ImageIO.write(image, format, file);
		} catch (IOException e) {
			System.out.println("二维码写入文件失败"+e.getMessage());
		}
	}
	/**
	 * 二维码流式输出
	 * 	@param contents 二维码内容
	 * @param format 图片格式
	 * @param stream 输出流
	 * */
	public static void writeToStream(String contents,String format,OutputStream stream){
		BufferedImage image = encodeImg(contents);
		try {
			ImageIO.write(image, format, stream);
		} catch (IOException e) {
			System.out.println("二维码写入流失败"+e.getMessage());
		}
	}
	
	/**
	 * 获取二维码中间信息
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage getBufferedImage(String diningtableNum) throws IOException{
		  int imageWidth = 39;// 图片的宽度
		  int imageHeight = 39;// 图片的高度

		  BufferedImage image = new BufferedImage(imageWidth, imageHeight,
		  BufferedImage.TYPE_INT_RGB);
		  Graphics graphics = image.getGraphics();
		  graphics.setColor(Color.white);
		  graphics.fillRect(0, 0, imageWidth, imageHeight);
		  graphics.setColor(Color.black);
		  graphics.setFont(new Font("宋体",Font.BOLD,15));
		  graphics.drawString(diningtableNum, 12, 25);
		  
		  return image;
	}
	
	
	public static BufferedImage getFinishQrCode(String code,HttpServletRequest request){
		BufferedImage twodimensioncode = null;
		try {
			Object obj = request.getSession().getAttribute("adminSession");
			if (null != obj) {
				 SysAdmin admin = (SysAdmin) obj;
				 twodimensioncode = EncodeImgZxing.encodeImg(Configurations.getQrcodeUrl()+"?"+"code="+code+"&"+"wxmBusinessId="+admin.getRelationTableId());
				 Graphics2D g = twodimensioncode.createGraphics();
				 BufferedImage logo = getBufferedImage(code);
		         int logoWidth = logo.getWidth(null) > twodimensioncode.getWidth()*2 /10 ? (twodimensioncode.getWidth()*2 /10) : logo.getWidth(null);  
		         int logoHeight = logo.getHeight(null) > twodimensioncode.getHeight()*2 /10 ? (twodimensioncode.getHeight()*2 /10) : logo.getHeight(null);  
		         int x = (twodimensioncode.getWidth() - logoWidth) / 2;  
		         int y = (twodimensioncode.getHeight() - logoHeight) / 2;  
		         g.drawImage(logo, x, y, logoWidth, logoHeight, null);  
		         g.drawRoundRect(x, y, logoWidth, logoHeight, 15 ,15);  
		         g.setStroke(new BasicStroke(2));  
		         g.setColor(Color.WHITE);  
		         g.drawRect(x, y, logoWidth, logoHeight);  
		         g.dispose();  
		         logo.flush();  
		         twodimensioncode.flush();  
			}
		}catch(Exception e){
			System.out.println("生成二维码失败");
		}
		 
         return twodimensioncode;  
 
	}
}