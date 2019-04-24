package com.hj.utils;

/**
 * 
 * @ClassName: ApiUrls
 * @Description: TODO(所需调用接口的url常量)
 * @author jun.hai
 * @date 2014年10月2日 下午8:43:39
 *
 */
public class ApiUrls {
	// 获取openId
	public static final String WEIXIN_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";

	public static final String WEIXIN_OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

	// 获取access_token
	public static final String WEIXIN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

	// 获取jsapi_ticket
	public static final String WEIXIN_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

	// 获取用户基本信息
	public static final String WEIXIN_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

	// 微信统一支付
	public static final String WEIXIN_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	// 生成换取二维码的ticket（生成二维码）
	public static final String WEIXIN_QRCODE_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
	
	//通过ticket换取二维码
	public static final String WEIXIN_QRCODE_CREATE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode";

	// 微信普通红包
	public static final String WEIXIN_SENDREDPACK = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
}
