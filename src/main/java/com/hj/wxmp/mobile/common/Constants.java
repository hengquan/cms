package com.hj.wxmp.mobile.common;

/**
 * 
 * @ClassName: Constants
 * @Description: TODO(存放系统中的常量)
 * @author jun.hai
 * @date 2014年10月5日 上午8:39:20
 *
 */
public class Constants {

	public static final String WEIXIN_ISOURCE_SOUORCE = "weixinclient";

	public static final int WEIXIN_ROWS_OF_PAGE = 5;

	public static final String WEIXIN_INTERFACE_ERROR_JSON = "{\"result\": {\"resultCode\": \"F0001\",\"message\": \"网络故障或网速慢，请重试\" }}";

	public static final String WEIXIN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	public static final String WEIXIN_DATE = "yyyy-MM-dd";
	
	public static final String WEIXIN_DATES = "yyyy-MM";

	public static final Integer COOKIE_EXPIRY = 365 * 24 * 60 * 60;

	public static final String COOKIE_WEIXIN_OPEN_ID = "_cookie_weixin_open_id";

	public static final String HASH_KEY_ACCESS_TOKEN = "_hask_key_access_token";
	
	public static final String HASH_KEY_TICKET = "_hask_key_ticket";
	
	public static final String HASH_KEY_TICKET_EXPIRE_SECONDS = "_hask_key_ticket_expire_seconds";

	public static final String HASH_KEY_ACCESS_TOKEN_EXPIRES_IN = "_hask_key_access_token_expires_in";

	public static final String HASH_KEY_JSAPI_TICKET = "_hask_key_jsapi_ticket";

	public static final String HASH_KEY_JSAPI_TICKET_EXPIRES_IN = "_hask_key_jsapi_ticket_expires_in";

	public static final String WEIXIN_SESSION_CURRENT_QUERYTRING = "session_weixin_current_query_string";

	public static final String WEIXIN_SESSION_USER_INFO = "_session_weixin_user_info";

	public final static String WXMP_NO_DATA_INFO = "暂无数据！";

	public final static String WXMP_ROLE_CUST = "1";

	public final static String WXMP_ROLE_MGR = "3";

	public final static String WXMP_ROLE_POS = "4";

	public final static String WXMP_NO_PERMISSION = "您不能使用该功能！";

	// 定义存在session值中的常量
	public static final String USER_CONTEXT = "user";

	public static final String HASH_KEY_APP_ROOT = "_hask_key_root_version";

	public static final String HASH_KEY_ADMINSESSION = "adminSession";

	public static final String HASH_KEY_LST = "lstSession";

	public static final String SMS_ACCOUNT = "zhizhaoke";

	public static final String SMS_PASSWORD = "123456";

	// 新建商户默认密码
	public static final String BUSINESS_DEFAULT_PASSWORD = "123456";
	// 管理员默认密码
	public static final String ADMIN_DEFAULT_PASSWORD = "123456";

	// 车队卡类型
	public final static String QP_CARD_TYPE = "I";
	
	//每周抽奖活动类型
	public final static String ACTIVITY_TYPE = "01";
	//推荐抽奖类型
	public final static String ACTIVITY_TYPE_TUIJIAN = "02";
	
	
	public static final String OPER_TYPE_ASSIGN_ROLE_FONT = "分配角色";
	public static final String OPER_TYPE_CREATE_FONT = "新增";
	public static final String OPER_TYPE_DELETE_FONT = "删除";
	public static final String OPER_TYPE_EDIT_FONT = "编辑";
	public static final String OPER_TYPE_FIND_FONT = "查询";
	public static final String OPER_TYPE_UPLOAD_FILE_FONT = "上传文件";
	public static final String OPER_TYPE_ORDER_BY_FONT = "排序";
	public static final String OPER_TYPE_ISVALID_N_FONT = "关闭";
	public static final String OPER_TYPE_ROLE_ASSIGN_ACL_FONT = "角色菜单资源分配";
	public static final String OPER_TYPE_USER_ASSIGN_ACL_FONT = "用户菜单资源分配";
	public static final String OPER_TYPE_MODULE_REOURCE_FIND_FONT = "菜单资源查询";
	public static final String OPER_TYPE_RESET_FONT = "复制";
	public static final String OPER_TYPE_BATCH_DELETE_FONT = "批量删除";
	public static final String OPER_TYPE_UPDATE_FONT = "修改";
	public static final String OPER_TYPE_ISVALID_Y_FONT = "启用";
	public static final String OPER_TYPE_ASSIGN_ZIYUAN_FONT = "分配资源";
	public static final String OPER_TYPE_PASSWORD_RESET_Y_FONT = "重置密码成功";
	public static final String OPER_TYPE_PASSWORD_RESET_N_FONT = "重置密码失败";
	
	// 定义操作类型
	public static final String OPER_TYPE_ASSIGN_ROLE = "A"; // 分配角色
	public static final String OPER_TYPE_CREATE = "C"; // 新增
	public static final String OPER_TYPE_DELETE = "D"; // 删除
	public static final String OPER_TYPE_EDIT = "E"; // 编辑
	public static final String OPER_TYPE_FIND = "F"; // 查询
	public static final String OPER_TYPE_UPLOAD_FILE = "M"; // 上传文件
	public static final String OPER_TYPE_ORDER_BY = "O"; // 排序
	public static final String OPER_TYPE_ISVALID_N = "P"; // 关闭
	public static final String OPER_TYPE_ROLE_ASSIGN_ACL = "Q"; // 角色菜单资源分配
	public static final String OPER_TYPE_USER_ASSIGN_ACL = "T"; // 用户菜单资源分配
	public static final String OPER_TYPE_MODULE_REOURCE_FIND = "W"; // 菜单资源查询
	public static final String OPER_TYPE_RESET = "R"; // 复制
	public static final String OPER_TYPE_BATCH_DELETE = "S"; // 批量删除
	public static final String OPER_TYPE_UPDATE = "U"; // 修改
	public static final String OPER_TYPE_ISVALID_Y = "V"; // 启用
	public static final String OPER_TYPE_ASSIGN_ZIYUAN = "Z"; // 分配资源
	public static final String OPER_TYPE_PASSWORD_RESET_Y = "Y"; // 重置密码成功
	public static final String OPER_TYPE_PASSWORD_RESET_N = "N"; // 重置密码失败
	public static final String OPER_TYPE_CHECK = "CK"; // 检查

}
