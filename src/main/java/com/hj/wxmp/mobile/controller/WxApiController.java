package com.hj.wxmp.mobile.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.utils.JsonUtils;
import com.hj.utils.MD5Utils;
import com.hj.wxmp.core.WxUser;
import com.hj.wxmp.mobile.common.Configurations;
import com.hj.wxmp.mobile.common.ControllerBaseWx;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.common.Weixin;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.entity.AccessRecord01;
import com.hj.wxmp.mobile.entity.AccessRecord02;
import com.hj.wxmp.mobile.entity.AccessRecord03;
import com.hj.wxmp.mobile.entity.AuditRecord;
import com.hj.wxmp.mobile.entity.Customer;
import com.hj.wxmp.mobile.entity.ProjCustRef;
import com.hj.wxmp.mobile.entity.SysRole;
import com.hj.wxmp.mobile.entity.TabDictRef;
import com.hj.wxmp.mobile.entity.UserCustRef;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.entity.UserRole;
import com.hj.wxmp.mobile.services.AccessRecord01Service;
import com.hj.wxmp.mobile.services.AccessRecord02Service;
import com.hj.wxmp.mobile.services.AccessRecord03Service;
import com.hj.wxmp.mobile.services.AuditRecordService;
import com.hj.wxmp.mobile.services.CustomerService;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjCustRefService;
import com.hj.wxmp.mobile.services.ProjUserRoleService;
import com.hj.wxmp.mobile.services.ProjectService;
import com.hj.wxmp.mobile.services.SysRoleService;
import com.hj.wxmp.mobile.services.TabDictRefService;
import com.hj.wxmp.mobile.services.UserCustRefService;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.hj.wxmp.mobile.services.UserRoleService;
import com.hj.wxmp.mobile.services.WxLoginService;
import com.spirit.core.dict.model.DictDetail;
import com.spirit.core.dict.model.DictModel;
import com.spirit.core.dict.service.DictService;
import com.spiritdata.framework.core.model.tree.TreeNode;
import com.spiritdata.framework.core.model.tree.TreeNodeBean;
import com.spiritdata.framework.ui.tree.ZTree;
import com.spiritdata.framework.util.RequestUtils;

@RequestMapping("/wx/api")
@Controller
public class WxApiController extends ControllerBaseWx {

	private final static Logger logger = LoggerFactory
			.getLogger(WxApiController.class);

	private HashSessions hashSession = HashSessions.getInstance();
	@Autowired
	IKeyGen key;
	@Autowired
	WxLoginService wxLoginService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	UserRoleService sysUserRoleService;
	@Autowired
	ProjUserRoleService projUserRoleService;
	@Autowired
	ProjCustRefService projCustRefService;
	@Autowired
	UserCustRefService userCustRefService;
	@Autowired
	ProjectService projectService;
	@Autowired
	CustomerService customerService;
	@Autowired
	AccessRecord01Service accessRecord01Service;
	@Autowired
	AccessRecord02Service accessRecord02Service;
	@Autowired
	AccessRecord03Service accessRecord03Service;
	@Autowired
	SysRoleService roleService;
	@Autowired
	ProjectService projectServise;
	@Autowired
	TabDictRefService tabDictRefService;
	@Autowired
	AuditRecordService auditRecordService;
    @Autowired
	DictService dictService;

	public UserInfo getCurrentUser() {
		UserInfo user = hashSession.getCurrentSessionUser(request);
		return user;
	}

	// 根据openid去腾讯后台获取用户信息并更新本地userinfo
	private void updateUserInfo(String openid) {
		if (StringUtils.trimToNull(openid) != null) {
			WxUser wxUser = Weixin.getInstance().getUserInfo(openid);
			wxLoginService.bandingUser(wxUser, ObjectUtils.toString(
					request.getSession().getAttribute("weixin_current_url")));
		}
	}

	@RequestMapping(value = "/redirectUrl.az")
	public void redirectUrl(HttpServletResponse response, Model model,
			HttpServletRequest request) throws IOException {
		String openid = HashSessions.getInstance().getOpenId(request);
		updateUserInfo(openid);
		// 获取RUL地址
		String wx_url = StringUtils.trimToEmpty(getTrimParameter("wx_url"));
		response.sendRedirect(URLDecoder.decode(wx_url, "UTF-8"));
	}

	
	
	@RequestMapping(value = "/verifyMsg")
	public void verifyMsg(HttpServletResponse res,String requestURL,
			HttpServletRequest req) throws IOException {
		 try {
	            String openid = HashSessions.getInstance().getOpenId(req);
	            //openid = "oaBNt0xKNjXvStRlbKqMnk7QQ2Pw";
	            logger.debug("this---openid:{}",openid);
	            String siteName = Configurations.getSiteName();
	            if (openid == null || "".equals(openid)) {
	            	String[] split = requestURL.split("/wxfront");
	            	requestURL = siteName + "/wxmp.ql/wxpage/wxfront" + split[1];
	                getOpenid(res,requestURL,req);
	            }else{
	                Integer resultData = userMsg(openid);
	                if(resultData==100){
	                	siteName = requestURL;
	                }else if(resultData==103){
	                    siteName += "/wxmp.ql/wxfront/user/register.html";
	                }else if(resultData==104){
	                	String url = siteName + "/wxmp.ql/wxfront/user/info.html";
	                	if(!requestURL.equals(url)){
	                		siteName += "/wxmp.ql/wxfront/err.html?0002";
	                	}else{
	                		siteName = requestURL;
	                	}
	                }else if(resultData==105){
	                    siteName += "/wxmp.ql/wxfront/err.html?0003";
	                }else if(resultData==106){
	                    siteName += "/wxmp.ql/wxfront/err.html?0001";
	                }
	                res.sendRedirect(siteName);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	
	//是否有openid
    public void getOpenid(HttpServletResponse response,String requetUrl, HttpServletRequest request) throws Exception {
        String openid = HashSessions.getInstance().getOpenId(request);
        if(openid != null && !openid.equals("")){
            updateUserInfo(openid);
        }else{
        	String siteName = Configurations.getSiteName();
            String url = siteName+"/wxmp.ql/wx/api/redirectUrl.az?wx_url="+requetUrl;
            logger.debug("messageURL----asdfa{}",url);
            response.sendRedirect(URLDecoder.decode(url, "UTF-8"));
        }
    }

    //通用接口(所有的接口都会来访问该接口看用户信息是否完整和审核是否通过)
    private Integer userMsg(String openid) throws Exception{
        Integer result = 0;
        UserInfo userInfo = userInfoService.findByOpenid(openid);
        String realname = userInfo.getRealname();
        String phone = userInfo.getMainphonenum();
        String loginname = userInfo.getLoginname();
        String password = userInfo.getPassword();
        Integer isvalidate = userInfo.getIsvalidate();
        if(realname!=null && phone !=null && loginname!=null && password!=null){
            if(isvalidate==0){
                result = 104;
            }else if(isvalidate==2){
                result = 105;
            }else if(isvalidate==3){
                result = 106;
            }else{
                result = 100;
            }
        }else{
            result = 103;
        }
        return result;
    }
	
    
    //通用接口获取访问链接和参数并且验证用户信息
    public void visiitURL(HttpServletRequest req,HttpServletResponse res){
    	String requestURL = req.getRequestURL().toString();
		if(StringUtils.isNotBlank(req.getQueryString())){
			requestURL = req.getRequestURL() + "?" + req.getQueryString();
		}
		try {
            String openid = HashSessions.getInstance().getOpenId(req);
            //openid = "oaBNt0xKNjXvStRlbKqMnk7QQ2Pw";
            if (openid == null || "".equals(openid)) {
                getOpenid(res,requestURL,req);
            }else{
                Integer resultData = userMsg(openid);
                String siteName = Configurations.getSiteName();
                if(resultData==103){
                    siteName += "/wxmp.ql/wxfront/user/register.html";
                    res.sendRedirect(siteName);
                }else if(resultData==104){
                	String url = siteName + "/wxmp.ql/wxfront/user/info.html";
                	if(!requestURL.equals(url)){
                		siteName += "/wxmp.ql/wxfront/err.html?0002";
                		res.sendRedirect(siteName);
                	}
                }else if(resultData==105){
                    siteName += "/wxmp.ql/wxfront/err.html?0003";
                    res.sendRedirect(siteName);
                }else if(resultData==106){
                    siteName += "/wxmp.ql/wxfront/err.html?0001";
                    res.sendRedirect(siteName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	

	public void responseInfo(HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8;");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS,DELETE,PUT");
	}
	
	
	//用户微信登入补全信息页面(获取角色列表)
	@RequestMapping(value = "/getRoleList")
	@ResponseBody
	public String getRoleList(HttpServletResponse response,HttpServletRequest requet) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//获取所有的用户权限信息
			List<SysRole> roles = roleService.selectAllMsg();
			map.put("roles", roles);
			map.put("msg", "100");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}
	
	
	
	//用户微信登入补全信息(提交用户注册信息)
	@RequestMapping(value = "/updateUserMsg")
	@ResponseBody
	public String updateUserMsg(HttpServletRequest requet,HttpServletResponse response,UserInfo userInfo,
			@RequestParam(value = "roleId", defaultValue = "")String roleId,
			@RequestParam(value = "prjectNames", defaultValue = "")String prjectNames) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(userInfo.getLoginname() == null || "".equals(userInfo.getLoginname())){
				map.put("msg", "200");
				return JsonUtils.map2json(map);
			}
			if(userInfo.getPassword() == null || "".equals(userInfo.getPassword())){
				map.put("msg", "201");
				return JsonUtils.map2json(map);
			}
			if(userInfo.getMainphonenum() == null || "".equals(userInfo.getMainphonenum())){
				map.put("msg", "202");
				return JsonUtils.map2json(map);
			}
			if(userInfo.getRealname() == null || "".equals(userInfo.getRealname())){
				map.put("msg", "203");
				return JsonUtils.map2json(map);
			}
			if(roleId == null || "".equals(roleId)){
				map.put("msg", "204");
				return JsonUtils.map2json(map);
			}
			if(prjectNames == null || "".equals(prjectNames)){
				map.put("msg", "205");
				return JsonUtils.map2json(map);
			}
			String openid = HashSessions.getInstance().getOpenId(request);
			//用户信息
			UserInfo userinfo = userInfoService.findByOpenid(openid);
			String userId = userinfo.getId();
			//用户填写的信息
			userInfo.setId(userId);
			userInfo.setOpenid(openid);
			userInfo.setPassword(MD5Utils.MD5(userInfo.getPassword()));
			userInfo.setSelfprojauth(prjectNames);
			//更新用户数据
			userInfoService.update(userInfo);
			//给用户赋权限
			UserRole userRole = new UserRole();
			userRole.setId(key.getUUIDKey());
			userRole.setUserid(userId);
			userRole.setRoleid(roleId);
			//添加
			sysUserRoleService.insert(userRole);
			map.put("msg", "100");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}
	
	
	
	
	
	//查看首访记录是否有该客户信息
	@RequestMapping(value = "/existRecord01")
	@ResponseBody
	public String existRecord01(HttpServletRequest requet,HttpServletResponse response,
			@RequestParam(value = "custName", defaultValue = "")String name,
			@RequestParam(value = "custPhone", defaultValue = "")String phone,
			@RequestParam(value = "projId", defaultValue = "")String project) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			if("".equals(name)){
				map.put("msg", "200");
			}
			if("".equals(phone)){
				map.put("msg", "201");
			}
			if("".equals(project)){
				map.put("msg", "202");
			}
			if(map.get("msg")==null){
				String openid = HashSessions.getInstance().getOpenId(request);
				msg.put("name", name);
				msg.put("phone", phone);
				msg.put("project", project);
				//查询客户信息
				List<Map<String, Object>> accessRecord01 = accessRecord01Service.selectUserMsy(msg);
				if(accessRecord01.size()>0){
					//权限人ID
					String authorId = accessRecord01.get(0).get("authorId").toString();
					UserInfo authorUser = userInfoService.findById(authorId);
					//权限人姓名
					String authorName ="";
					if(authorUser==null){
						throw new Exception("找到的首访记录中记录的权限人在系统中不存在");
					}else{
						authorName = authorUser.getRealname();
					}
					//客户ID
					String custId = accessRecord01.get(0).get("custId").toString();
					if(authorUser.getOpenid().equals(openid)){
						map.put("msg", "100");
					}else{
						map.put("msg", "101");
					}
					map.put("authorName", authorName);
					map.put("authorId", authorId);
					map.put("custId", custId);
				}else{
					map.put("msg", "104");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}
	
	
	
	
	
	
	
	
	
	//获取用户权限项目信息
	@RequestMapping(value = "/getUserRoleProj")
	@ResponseBody
	public String getUserRoleProj(HttpServletRequest requet,HttpServletResponse response) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String openid = HashSessions.getInstance().getOpenId(request);
			//用户信息
			UserInfo userInfo = userInfoService.findByOpenid(openid);
			String userId = userInfo.getId();
			//查询用户和所属项目的所有信息
			List<Map<String, Object>> datas = projUserRoleService.selectByUserId(userId);
			map.put("datas", datas);
			map.put("msg", "100");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}
	
	
	

	//添加首访记录
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addHisFirstRecord")
	@ResponseBody
	public String addHisFirstRecord(HttpServletRequest requet,HttpServletResponse response,
			AccessRecord01 record01,String userId,
			String firstknowtime1,String receptime1) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String openid = HashSessions.getInstance().getOpenId(request);
			UserInfo userInfo=null;
			if (StringUtils.isNotEmpty(openid)) userInfo=userInfoService.findByOpenid(openid);
			else if (StringUtils.isNotEmpty(userId)) userInfo=userInfoService.findById(userId);
			if (userInfo==null) throw new Exception("未获得用户，无法处理");
			String _userId=userInfo.getId();
			userId=userInfo.getId();
			//补全首访信息-并更新
			record01.setId(key.getUUIDKey());
			//第一次获知本案的时间
			if(firstknowtime1!=null){
				firstknowtime1+=" 00:00:00";
				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = formata.parse(firstknowtime1);				
				record01.setFirstknowtime(parse);
			}
			//本次到访时间
			if(receptime1!=null){
				receptime1+=" 00:00:00";
				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = formata.parse(receptime1);				
				record01.setReceptime(parse);
			}
			//录入人权限人
			record01.setAuthorid(userId);
			record01.setCreatorid(userId);
			//扫描一次，处理本表，处理客户表，处理客户项目关系表，处理字典表；
			Object[] resultObjs=scan(record01,0);
			if (accessRecord01Service.insert((AccessRecord01)resultObjs[0])) {
				Deal01OtherTable d01=new Deal01OtherTable((Customer)resultObjs[1], (ProjCustRef)resultObjs[2], (UserCustRef)resultObjs[3], (List<TabDictRef>)resultObjs[4], 0);
				d01.start();
				map.put("msg", "100");
			} else {
				map.put("msg", "104");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}
	//首访处理表字段信息
	/**
	 * 
	 * @param record01
	 * @param type =0是添加
	 * @return
	 */
	private Object[] scan(AccessRecord01 record01,int type) {
		Object[] ret=new Object[5];
		AccessRecord01 _retR01=new AccessRecord01();//可以直接插入数据库的01数据
		Customer cust=new Customer(); //从01中汇出的客户信息，可直接参与数据库操作
		ProjCustRef projCustRef=new ProjCustRef(); //从01中汇出的客户项目关系，可直接参与数据库操作
		UserCustRef userCustRef=new UserCustRef(); //
		List<TabDictRef> dictRefList=new ArrayList<TabDictRef>(); //可以直接参与处理字典项与表关系处理的对象列表

		//获取所有对象的属性
		//本表Id
		String id = "";
		//项目Id
		String projId = "";
		//客户表ID
		String customerId = "";
		//临时字符串
		String tempStr = "";
		//返回结果
		Map<String, Object> parseResult = null;

		//若是新增，设置关联对象的Id
		if (type==0) {
			projCustRef.setId(key.getUUIDKey());
			userCustRef.setId(key.getUUIDKey());
		}else{
			//客户项目关系表
			Map<String,Object>result=new HashMap<String,Object>();
			result.put("cusId", record01.getCustid());
			result.put("projId", record01.getProjid());
			projCustRef = projCustRefService.selectByCusIdAndProjId(result);
		}
		//首访Id
		if (type==0) id=key.getUUIDKey();
		else id=record01.getId();
		_retR01.setId(id);
		//项目Id
		projId=record01.getProjid();
		_retR01.setProjid(projId);
		projCustRef.setProjid(projId);
		userCustRef.setProjid(projId);
		//用户Id
		if (type==0) customerId=key.getUUIDKey();
		else customerId=record01.getCustid();
		_retR01.setCustid(customerId);
		cust.setId(customerId);
		projCustRef.setCustid(customerId);
		userCustRef.setCustid(customerId);
		//客户名称
		_retR01.setCustname(record01.getCustname());
		cust.setCustname(record01.getCustname());
		//客户手机
		_retR01.setCustphonenum(record01.getCustphonenum());
		cust.setPhonenum(record01.getCustphonenum());
		//获知时间
		_retR01.setFirstknowtime(record01.getFirstknowtime());
		projCustRef.setFirstknowtime(record01.getFirstknowtime());
		//客户性别
		tempStr=record01.getCustsex();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setCustsex(tempStr);
			cust.setCustsex(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "002", "客户性别", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "002", "客户性别", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//年龄段
		tempStr=record01.getAgegroup();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setAgegroup(tempStr);
			cust.setAgegroup(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> ageO1=transToDictRefList(dictList, "003", "年龄段", "ql_AccessRecord01", id);
			if (ageO1!=null) dictRefList.addAll(ageO1);
			List<TabDictRef> ageCust=transToDictRefList(dictList, "003", "年龄段", "ql_Customer", customerId);
			if (ageCust!=null) dictRefList.addAll(ageCust);
		}
		//购房资格
		tempStr=record01.getBuyqualify();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setBuyqualify(tempStr);
			cust.setBuyqualify(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> buyqualifyO1=transToDictRefList(dictList, "004", "购房资格", "ql_AccessRecord01", id);
			if (buyqualifyO1!=null) dictRefList.addAll(buyqualifyO1);
			List<TabDictRef> buyqualifyCust=transToDictRefList(dictList, "004", "购房资格", "ql_Customer", customerId);
			if (buyqualifyCust!=null) dictRefList.addAll(buyqualifyCust);
		}
		//本地居住地
		tempStr=record01.getLocalresidence();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setLocalresidence(tempStr);
			cust.setLocalresidence(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> localresidenceO1=transToDictRefList(dictList, "032", "本地居住地", "ql_AccessRecord01", id);
			if (localresidenceO1!=null) dictRefList.addAll(localresidenceO1);
			List<TabDictRef> localresidenceCust=transToDictRefList(dictList, "032", "本地居住地", "ql_Customer", customerId);
			if (localresidenceCust!=null) dictRefList.addAll(localresidenceCust);
		}
		//本地工作区
		tempStr=record01.getLocalworkarea();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setLocalworkarea(tempStr);
			cust.setLocalworkarea(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "032", "本地工作地", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "032", "本地工作地", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//外阜居住地
		tempStr=record01.getOutresidence();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setOutresidence(tempStr);
			cust.setOutresidence(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "001", "外阜居住地", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "001", "外阜居住地", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//外阜工作地
		tempStr=record01.getOutworkarea();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setOutworkarea(tempStr);
			cust.setOutworkarea(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "001", "外阜工作地", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "001", "外阜工作地", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//家庭状况
		tempStr=record01.getFamilystatus();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setFamilystatus(tempStr);
			cust.setFamilystatus(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "005", "家庭状况", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "005", "家庭状况", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//出行方式
		tempStr=record01.getTraffictype();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setTraffictype(tempStr);
			cust.setTraffictype(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "006", "出行方式", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "006", "出行方式", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//出行方式描述
		tempStr=record01.getTraffictypedesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR01.setTraffictypedesc(tempStr);
			cust.setTraffictypedesc(tempStr);
		}
		//从事行业
		tempStr=record01.getWorkindustry();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setWorkindustry(tempStr);
			cust.setWorkindustry(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "007", "从事行业", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "007", "从事行业", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//从事行业描述
		tempStr=record01.getWorkindustrydesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR01.setWorkindustrydesc(tempStr);
			cust.setWorkindustrydesc(tempStr);
		}
		//企业性质
		tempStr=record01.getEnterprisetype();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setEnterprisetype(tempStr);
			cust.setEnterprisetype(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "008", "企业性质", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "008", "企业性质", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//企业性质描述
		tempStr=record01.getEnterprisetypedesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR01.setEnterprisetypedesc(tempStr);
			cust.setEnterprisetypedesc(tempStr);
		}
		//关注产品类型
		tempStr=record01.getRealtyproducttype();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setRealtyproducttype(tempStr);
			cust.setRealtyproducttype(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "009", "关注产品类型", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "009", "关注产品类型", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//关注产品类型描述
		tempStr=record01.getRealtyproducttypedesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR01.setRealtyproducttypedesc(tempStr);
			cust.setRealtyproducttypedesc(tempStr);
		}
		//关注面积
		tempStr=record01.getAttentacreage();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setAttentacreage(tempStr);
			cust.setAttentacreage(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "010", "关注面积", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "010", "关注面积", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//接受价格区段
		tempStr=record01.getPricesection();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setPricesection(tempStr);
			cust.setPricesection(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "011", "接受价格区段", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "011", "接受价格区段", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//购房目的
		tempStr=record01.getBuypurpose();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setBuypurpose(tempStr);
			cust.setBuypurpose(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "012", "购房目的", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "012", "购房目的", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//购房目的描述
		tempStr=record01.getBuypurposedesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR01.setBuypurposedesc(tempStr);
			cust.setBuypurposedesc(tempStr);
		}
		//认知本案渠道
		tempStr=record01.getKnowway();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setKnowway(tempStr);
			projCustRef.setKnowway(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "013", "认知本案渠道", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "013", "认知本案渠道", "ql_ProjCust_Ref", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//认知本案渠道描述
		tempStr=record01.getKnowwaydesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR01.setKnowwaydesc(tempStr);
			projCustRef.setKnowwaydesc(tempStr);
		}
		//本案关注点
		tempStr=record01.getAttentionpoint();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setAttentionpoint(tempStr);
			projCustRef.setAttentionpoint(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "014", "本案关注点", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "014", "本案关注点", "ql_ProjCust_Ref", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//本案关注点描述
		tempStr=record01.getAttentionpointdesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR01.setAttentionpointdesc(tempStr);
			projCustRef.setAttentionpointdesc(tempStr);
		}
		//预估身价
		tempStr=record01.getEstcustworth();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setEstcustworth(tempStr);
			cust.setEstcustworth(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "015", "预估身价", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "015", "预估身价", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//重点投资
		tempStr=record01.getInvesttype();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setInvesttype(tempStr);
			cust.setInvesttype(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "016", "重点投资", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "016", "重点投资", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//重点投资描述
		tempStr=record01.getInvesttypedesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR01.setInvesttypedesc(tempStr);
			cust.setInvesttypedesc(tempStr);
		}
		//资金筹备期
		tempStr=record01.getCapitalprepsection();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setCapitalprepsection(tempStr);
			projCustRef.setCapitalprepsection(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "017", "资金筹备期", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "017", "资金筹备期", "ql_ProjCust_Ref", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//比较项目
		tempStr=record01.getCompareprojs();
		if (parseResult!=null) {
			_retR01.setCompareprojs(tempStr);
			projCustRef.setCompareprojs(tempStr);
		}
		//本次接待时间
		tempStr=record01.getReceptimesection();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setReceptimesection(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "018", "本次接待时间", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
		}
		//本次接待时间
		_retR01.setReceptime(record01.getReceptime());
		//客户评级
		tempStr=record01.getCustscore();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setCustscore(tempStr);
			cust.setCustscore(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "019", "客户评级", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "019", "客户评级", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//客户描述
		_retR01.setCustdescn(record01.getCustdescn());
		//权限用户ID
		_retR01.setAuthorid(record01.getAuthorid());
		userCustRef.setUserid(record01.getAuthorid());
		//记录状态
		_retR01.setStatus(1);
		//记录状态
		_retR01.setCreatorid(record01.getCreatorid());
		
		ret[0]=_retR01;
		ret[1]=cust;
		ret[2]=projCustRef;
		ret[3]=userCustRef;
		ret[4]=dictRefList;
		
		logger.debug("项目客户关系表数据2：{}",JsonUtils.object2json(projCustRef));
		
		return ret;
	}
	/**
	 * 解析字典值内容
	 * @param dictsStr 被解析的字符串
	 * @return Map，key="storeStr",value=存储的字符串,key="dictList",vlue=字典值的列表，没个元素为一个Map
	 */
	private Map<String, Object> parseDictsStr(String dictsStr) {
		if(StringUtils.isEmpty(dictsStr)) return null;
		if(StringUtils.isEmpty(dictsStr)) return null;
		String[] oneDictStrList=dictsStr.split(",");

		Map<String, Object> ret=new HashMap<String, Object>();
		String storeStr="";
		List<Map<String, Object>> dictList=new ArrayList<Map<String, Object>>();
		for(int i=0;i<oneDictStrList.length;i++){
			String oneDictStr=oneDictStrList[i].trim();
			String[] scopeArray=oneDictStr.split("-");
			if (scopeArray.length<2||scopeArray.length>3) continue;

			Map<String, Object> oneDictInfo=new HashMap<String, Object>();
			if (scopeArray.length==2) oneDictInfo.put("type", 1);
			else if (scopeArray.length==3) oneDictInfo.put("type", 2);
			oneDictInfo.put("dictdid", scopeArray[0]);
			storeStr+=","+scopeArray[1];
			if (scopeArray.length==3) {
				int pos=scopeArray[2].indexOf("~");
				if (pos!=-1) {
					oneDictInfo.put("begin", scopeArray[2].substring(0, pos));
					oneDictInfo.put("end", scopeArray[2].substring(pos+1));
				} else continue;
			}
			dictList.add(oneDictInfo);
		}
		ret.put("storeStr", storeStr.substring(1));
		ret.put("dictList", dictList);
		return ret;
	}
	/*
	 * 
	 * @param dictList
	 * @param dictMid
	 * @param refName
	 * @param tabName
	 * @param tabId
	 * @return
	 */
	private List<TabDictRef> transToDictRefList(List<Map<String, Object>> dictList, String dictMid, String refName, String tabName, String tabId) {
		if (dictList.size()==0||dictList==null) return null;
		List<TabDictRef> ret=new ArrayList<TabDictRef>();
		for (Map<String, Object> oneDict: dictList) {
			TabDictRef tdr=new TabDictRef();
			tdr.setId(key.getUUIDKey());
			tdr.setDmid(dictMid);
			tdr.setDdid(oneDict.get("dictdid")+"");
			tdr.setTabname(tabName);
			tdr.setTabid(tabId);
			tdr.setRefname(refName);
			if (((Integer)oneDict.get("type"))==2) {
				tdr.setSectionbegin(Float.parseFloat(oneDict.get("begin")+""));
				tdr.setSectionend(Float.parseFloat(oneDict.get("end")+""));
			}
			ret.add(tdr);
		}
		return ret;
	}

	//修改首访记录
	@RequestMapping(value = "/updateRecord01")
	@ResponseBody
	public String updateRecord01(HttpServletRequest requet,HttpServletResponse response
			,AccessRecord01 record01,Customer customer,String userId
			,String firstknowtime1,String receptime1) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String openid = HashSessions.getInstance().getOpenId(request);
			UserInfo userInfo=null;
			if (StringUtils.isNotEmpty(openid)) userInfo=userInfoService.findByOpenid(openid);
			else if (StringUtils.isNotEmpty(userId)) userInfo=userInfoService.findById(userId);
			if (userInfo==null) throw new Exception("未获得用户，无法处理");
			String _userId=userInfo.getId();
			userId=userInfo.getId();
			//第一次获知本案的时间
			if(firstknowtime1!=null){
				firstknowtime1+=" 00:00:00";
				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = formata.parse(firstknowtime1);				
				record01.setFirstknowtime(parse);
			}
			//本次到访时间
			if(receptime1!=null){
				receptime1+=" 00:00:00";
				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = formata.parse(receptime1);				
				record01.setReceptime(parse);
			}
			//录入人权限人
			record01.setAuthorid(userId);
			record01.setCreatorid(userId);
			//扫描一次，处理本表，处理客户表，处理客户项目关系表，处理字典表；
			Object[] resultObjs=scan(record01,1);
			if (accessRecord01Service.update((AccessRecord01)resultObjs[0])) {
				Deal01OtherTable d01=new Deal01OtherTable((Customer)resultObjs[1], (ProjCustRef)resultObjs[2], (UserCustRef)resultObjs[3], (List<TabDictRef>)resultObjs[4], 1);
				d01.start();
				map.put("msg", "100");
			} else {
				map.put("msg", "104");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}	
	
	
	
	//添加复访记录
	@RequestMapping(value = "/addAfterVisit")
	@ResponseBody
	public String addAfterVisit(HttpServletRequest requet,HttpServletResponse response,
			AccessRecord02 record02,Customer customer,String userId,String receptime1) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String openid = HashSessions.getInstance().getOpenId(request);
			UserInfo userInfo=null;
			if (StringUtils.isNotEmpty(openid)) userInfo=userInfoService.findByOpenid(openid);
			else if (StringUtils.isNotEmpty(userId)) userInfo=userInfoService.findById(userId);
			if (userInfo==null) throw new Exception("未获得用户，无法处理");
			String _userId=userInfo.getId();
			userId=userInfo.getId();
			//补全首访信息-并更新
			record02.setId(key.getUUIDKey());
			//本次到访时间
			if(receptime1!=null){
				receptime1+=" 00:00:00";
				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = formata.parse(receptime1);				
				record02.setReceptime(parse);
			}
			//录入人权限人
			record02.setAuthorid(userId);
			record02.setCreatorid(userId);
			//扫描一次，处理本表，处理客户表，处理客户项目关系表，处理字典表；
			Object[] resultObjs=scan2(record02,0);
			if (accessRecord02Service.insert((AccessRecord02)resultObjs[0])) {
				Deal01OtherTable d01=new Deal01OtherTable((Customer)resultObjs[1], (ProjCustRef)resultObjs[2], (UserCustRef)resultObjs[3], (List<TabDictRef>)resultObjs[4], 0);
				d01.start();
				map.put("msg", "100");
			} else {
				map.put("msg", "104");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}	
	//复访处理表字段信息
	private Object[] scan2(AccessRecord02 record02,int type) {
		Object[] ret=new Object[5];
		AccessRecord02 _retR02=new AccessRecord02();//可以直接插入数据库的01数据
		Customer cust=new Customer(); //从01中汇出的客户信息，可直接参与数据库操作
		ProjCustRef projCustRef=new ProjCustRef(); //从01中汇出的客户项目关系，可直接参与数据库操作
		UserCustRef userCustRef=new UserCustRef(); //
		List<TabDictRef> dictRefList=new ArrayList<TabDictRef>(); //可以直接参与处理字典项与表关系处理的对象列表
		//获取所有对象的属性
		//本表Id
		String id = "";
		//项目id
		String projId = "";
		//客户表ID
		String customerId = "";
		//临时字符串
		String tempStr = "";
		//返回结果
		Map<String, Object> parseResult = null;
		//客户项目关系表
		Map<String,Object>result=new HashMap<String,Object>();
		result.put("cusId", record02.getCustid());
		result.put("projId", record02.getProjid());
		projCustRef = projCustRefService.selectByCusIdAndProjId(result);
		//复访Id
		if (type==0) id=key.getUUIDKey();
		else id=record02.getId();
		_retR02.setId(id);
		//项目Id
		projId=record02.getProjid();
		_retR02.setProjid(projId);
		projCustRef.setProjid(projId);
		//客户Id
		customerId=record02.getCustid();
		_retR02.setCustid(customerId);
		cust.setId(customerId);
		projCustRef.setCustid(customerId);
		userCustRef.setCustid(customerId);
		//客户名称
		_retR02.setCustname(record02.getCustname());
		cust.setCustname(record02.getCustname());
		//客户手机
		_retR02.setCustphonenum(record02.getCustphonenum());
		cust.setPhonenum(record02.getCustphonenum());
		//决策人是否到场
		_retR02.setDecisionerin(record02.getDecisionerin());
		//小孩个数
		_retR02.setChildrennum(record02.getChildrennum());
		cust.setChildrennum(record02.getChildrennum());
		//小孩年龄段
		tempStr=record02.getChildagegroup();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setChildagegroup(tempStr);
			cust.setChildagegroup(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> childagegroupO1=transToDictRefList(dictList, "020", "小孩年龄段", "ql_AccessRecord02", id);
			if (childagegroupO1!=null) dictRefList.addAll(childagegroupO1);
			List<TabDictRef> childagegroupCust=transToDictRefList(dictList, "020", "小孩年龄段", "ql_Customer", customerId);
			if (childagegroupCust!=null) dictRefList.addAll(childagegroupCust);
		}
		//小孩业余爱好
		tempStr=record02.getChildavocations();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setChildavocations(tempStr);
			cust.setChildavocations(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> childavocationsO1=transToDictRefList(dictList, "033", "小孩业余爱好", "ql_AccessRecord02", id);
			if (childavocationsO1!=null) dictRefList.addAll(childavocationsO1);
			List<TabDictRef> childavocationsCust=transToDictRefList(dictList, "033", "小孩业余爱好", "ql_Customer", customerId);
			if (childavocationsCust!=null) dictRefList.addAll(childavocationsCust);
		}
		//小孩业余爱好描述
		tempStr=record02.getChildavocationsdesc();
		_retR02.setChildavocationsdesc(tempStr);
		cust.setChildavocationsdesc(tempStr);
		//孩子学校类型
		tempStr=record02.getSchooltype();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setSchooltype(tempStr);
			cust.setSchooltype(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> schooltypeO1=transToDictRefList(dictList, "021", "孩子学校类型", "ql_AccessRecord02", id);
			if (schooltypeO1!=null) dictRefList.addAll(schooltypeO1);
			List<TabDictRef> schooltypeCust=transToDictRefList(dictList, "021", "孩子学校类型", "ql_Customer", customerId);
			if (schooltypeCust!=null) dictRefList.addAll(schooltypeCust);
		}
		//学校名称
		tempStr=record02.getSchoolname();
		_retR02.setSchoolname(tempStr);
		cust.setSchoolname(tempStr);
		//生活半径
		tempStr=record02.getLivingradius();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setLivingradius(tempStr);
			cust.setLivingradius(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> livingradiusO1=transToDictRefList(dictList, "022", "生活半径", "ql_AccessRecord02", id);
			if (livingradiusO1!=null) dictRefList.addAll(livingradiusO1);
			List<TabDictRef> livingradiusCust=transToDictRefList(dictList, "022", "生活半径", "ql_Customer", customerId);
			if (livingradiusCust!=null) dictRefList.addAll(livingradiusCust);
		}
		//社区名称
		tempStr=record02.getCommunityname();
		_retR02.setCommunityname(tempStr);
		cust.setCommunityname(tempStr);
		//住房性质
		tempStr=record02.getHousetype();
		_retR02.setHousetype(tempStr);
		cust.setHouseregitype(tempStr);
		//居住面积
		tempStr=record02.getLiveacreage();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setLiveacreage(tempStr);
			cust.setLiveacreage(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> livingradiusO1=transToDictRefList(dictList, "023", "居住面积", "ql_AccessRecord02", id);
			if (livingradiusO1!=null) dictRefList.addAll(livingradiusO1);
			List<TabDictRef> livingradiusCust=transToDictRefList(dictList, "023", "居住面积", "ql_Customer", customerId);
			if (livingradiusCust!=null) dictRefList.addAll(livingradiusCust);
		}
		//公司名称
		tempStr=record02.getEnterprisename();
		_retR02.setEnterprisename(tempStr);
		cust.setEnterprisename(tempStr);
		//公司地址
		tempStr=record02.getEnterpriseaddress();
		_retR02.setEnterpriseaddress(tempStr);
		cust.setEnterpriseaddress(tempStr);
		//公司职务
		tempStr=record02.getEnterprisepost();
		_retR02.setEnterprisepost(tempStr);
		cust.setEnterprisepost(tempStr);
		//贷款记录
		tempStr=record02.getLoanstatus();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setLoanstatus(tempStr);
			cust.setLoanstatus(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> loanstatusO1=transToDictRefList(dictList, "024", "贷款记录", "ql_AccessRecord02", id);
			if (loanstatusO1!=null) dictRefList.addAll(loanstatusO1);
			List<TabDictRef> loanstatusCust=transToDictRefList(dictList, "024", "贷款记录", "ql_Customer", customerId);
			if (loanstatusCust!=null) dictRefList.addAll(loanstatusCust);
		}
		//全职太太
		_retR02.setFulltimewifeflag(record02.getFulltimewifeflag());
		cust.setFulltimewifeflag(record02.getFulltimewifeflag());
		//国际教育意愿
		_retR02.setOuteduwill(record02.getOuteduwill());
		cust.setOuteduwill(record02.getOuteduwill());
		//是否有保姆
		_retR02.setNannyflag(record02.getNannyflag());
		cust.setNannyflag(record02.getNannyflag());
		//业主海外经历
		_retR02.setOutexperflag(record02.getOutexperflag());
		cust.setOutexperflag(record02.getOutexperflag());
		//业主海外经历城市
		_retR02.setOutexpercity(record02.getOutexpercity());
		cust.setOutexpercity(record02.getOutexpercity());
		//子女海外经历
		_retR02.setChildoutexperflag(record02.getChildoutexperflag());
		cust.setChildoutexperflag(record02.getChildoutexperflag());
		//子女海外经历城市
		_retR02.setChildoutexpercity(record02.getChildoutexpercity());
		cust.setChildoutexpercity(record02.getChildoutexpercity());
		//是否有宠物
		_retR02.setPetflag(record02.getPetflag());
		cust.setPetflag(record02.getPetflag());
		//家庭汽车数据量
		_retR02.setCarfamilycount(record02.getCarfamilycount());
		cust.setCarfamilycount(record02.getCarfamilycount());
		//汽车品牌
		_retR02.setCarbrand(record02.getCarbrand());
		cust.setCarbrand(record02.getCarbrand());
		//汽车总价款
		tempStr=record02.getCartotalprice();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setCartotalprice(tempStr);
			cust.setCartotalprice(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "025", "汽车总价款", "ql_AccessRecord02", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
			List<TabDictRef> cartotalpriceCust=transToDictRefList(dictList, "025", "汽车总价款", "ql_Customer", customerId);
			if (cartotalpriceCust!=null) dictRefList.addAll(cartotalpriceCust);
		}
		//汽车品牌
		_retR02.setHousecount(record02.getHousecount());
		cust.setHousecount(record02.getHousecount());
		//关注微信号
		_retR02.setAttentwx(record02.getAttentwx());
		cust.setAttentwx(record02.getAttentwx());
		//常用APP
		_retR02.setAppnames(record02.getAppnames());
		cust.setAppnames(record02.getAppnames());
		//业余爱好
		tempStr=record02.getAvocations();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setAvocations(tempStr);
			cust.setAvocations(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "026", "业余爱好", "ql_AccessRecord02", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
			List<TabDictRef> cartotalpriceCust=transToDictRefList(dictList, "026", "业余爱好", "ql_Customer", customerId);
			if (cartotalpriceCust!=null) dictRefList.addAll(cartotalpriceCust);
		}
//		//业余爱好描述
//		_retR02.setAppnamesdesc(record02.getAppnamesdesc());
//		cust.setAppnamesdesc(record02.getAppnamesdesc());
		//本案抗拒点
		tempStr=record02.getResistpoint();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setResistpoint(tempStr);
			projCustRef.setResistpoint(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "014", "本案抗拒点", "ql_AccessRecord02", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
			List<TabDictRef> cartotalpriceCust=transToDictRefList(dictList, "014", "本案抗拒点", "ql_ProjCust_Ref", customerId);
			if (cartotalpriceCust!=null) dictRefList.addAll(cartotalpriceCust);
		}
		//本案抗拒点描述
		_retR02.setResistpointdesc(record02.getResistpointdesc());
		projCustRef.setResistpointdesc(record02.getResistpointdesc());
		//本案关注点
		tempStr=record02.getAttentionpoint();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setAttentionpoint(tempStr);
			projCustRef.setAttentionpoint(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "014", "本案关注点", "ql_AccessRecord02", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
			List<TabDictRef> cartotalpriceCust=transToDictRefList(dictList, "014", "本案关注点", "ql_ProjCust_Ref", customerId);
			if (cartotalpriceCust!=null) dictRefList.addAll(cartotalpriceCust);
		}
		//本案关注点描述
		_retR02.setAttentionpointdesc(record02.getAttentionpointdesc());
		projCustRef.setAttentionpointdesc(record02.getAttentionpointdesc());
		//喜欢活动
		tempStr=record02.getLoveactivation();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setLoveactivation(tempStr);
			cust.setLoveactivation(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "027", "喜欢活动", "ql_AccessRecord02", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
			List<TabDictRef> cartotalpriceCust=transToDictRefList(dictList, "027", "喜欢活动", "ql_Customer", customerId);
			if (cartotalpriceCust!=null) dictRefList.addAll(cartotalpriceCust);
		}
		//喜欢活动描述
		_retR02.setLoveactdesc(record02.getLoveactdesc());
		cust.setLoveactdesc(record02.getLoveactdesc());
		//可参加活动时间
		_retR02.setFreetimesection(record02.getFreetimesection());
		cust.setFreetimesection(record02.getFreetimesection());
		//来访人数
		_retR02.setVisitorcount(record02.getVisitorcount());
		//来访人关系
		tempStr=record02.getVisitorrefs();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setVisitorrefs(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "028", "来访人数关系", "ql_AccessRecord02", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
		}
		//来访人数关系描述
		_retR02.setVisitorrefsdesc(record02.getVisitorrefsdesc());
		//本次接待时间
		tempStr=record02.getReceptimesection();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setReceptimesection(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "018", "本次接待时间", "ql_AccessRecord02", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
		}
		//本次接待时间
		_retR02.setReceptime(record02.getReceptime());
		//客户评级
		tempStr=record02.getCustscore();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setCustscore(tempStr);
			cust.setCustscore(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "019", "客户评级", "ql_AccessRecord02", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
			List<TabDictRef> cartotalpriceCust=transToDictRefList(dictList, "019", "客户评级", "ql_Customer", customerId);
			if (cartotalpriceCust!=null) dictRefList.addAll(cartotalpriceCust);
		}
		//访问描述
		_retR02.setDescn(record02.getDescn());
		//未成交描述
		_retR02.setFaultdescn(record02.getFaultdescn());
		//权限用户ID
		_retR02.setAuthorid(record02.getAuthorid());
		//状态
		_retR02.setStatus(1);
		//创建者ID
		_retR02.setCreatorid(record02.getCreatorid());
		//以下是首访导入数据
		//年龄段
		tempStr=record02.getAgegroup();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setAgegroup(tempStr);
			cust.setAgegroup(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> ageO1=transToDictRefList(dictList, "003", "年龄段", "ql_AccessRecord02", id);
			if (ageO1!=null) dictRefList.addAll(ageO1);
			List<TabDictRef> ageCust=transToDictRefList(dictList, "003", "年龄段", "ql_Customer", customerId);
			if (ageCust!=null) dictRefList.addAll(ageCust);
		}
		//家庭状况
		tempStr=record02.getFamilystatus();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setFamilystatus(tempStr);
			cust.setFamilystatus(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "005", "家庭状况", "ql_AccessRecord02", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "005", "家庭状况", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//出行方式
		tempStr=record02.getTraffictype();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setTraffictype(tempStr);
			cust.setTraffictype(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "006", "出行方式", "ql_AccessRecord02", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "006", "出行方式", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//出行方式描述
		tempStr=record02.getTraffictypedesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR02.setTraffictypedesc(tempStr);
			cust.setTraffictypedesc(tempStr);
		}
		//购房资格
		tempStr=record02.getBuyqualify();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setBuyqualify(tempStr);
			cust.setBuyqualify(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> buyqualifyO1=transToDictRefList(dictList, "004", "购房资格", "ql_AccessRecord02", id);
			if (buyqualifyO1!=null) dictRefList.addAll(buyqualifyO1);
			List<TabDictRef> buyqualifyCust=transToDictRefList(dictList, "004", "购房资格", "ql_Customer", customerId);
			if (buyqualifyCust!=null) dictRefList.addAll(buyqualifyCust);
		}
		//从事行业
		tempStr=record02.getWorkindustry();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setWorkindustry(tempStr);
			cust.setWorkindustry(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "007", "从事行业", "ql_AccessRecord012", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "007", "从事行业", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//从事行业描述
		tempStr=record02.getWorkindustrydesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR02.setWorkindustrydesc(tempStr);
			cust.setWorkindustrydesc(tempStr);
		}
		//企业性质
		tempStr=record02.getEnterprisetype();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setEnterprisetype(tempStr);
			cust.setEnterprisetype(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "008", "企业性质", "ql_AccessRecord02", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "008", "企业性质", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//企业性质描述
		tempStr=record02.getEnterprisetypedesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR02.setEnterprisetypedesc(tempStr);
			cust.setEnterprisetypedesc(tempStr);
		}
		//认知本案渠道
		tempStr=record02.getKnowway();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setKnowway(tempStr);
			projCustRef.setKnowway(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "013", "认知本案渠道", "ql_AccessRecord02", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "013", "认知本案渠道", "ql_ProjCust_Ref", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//认知本案渠道描述
		tempStr=record02.getKnowwaydesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR02.setKnowwaydesc(tempStr);
			projCustRef.setKnowwaydesc(tempStr);
		}
		//预估身价
		tempStr=record02.getEstcustworth();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setEstcustworth(tempStr);
			cust.setEstcustworth(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "015", "预估身价", "ql_AccessRecord02", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "015", "预估身价", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//重点投资
		tempStr=record02.getInvesttype();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setInvesttype(tempStr);
			cust.setInvesttype(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "016", "重点投资", "ql_AccessRecord02", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "016", "重点投资", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//重点投资描述
		tempStr=record02.getInvesttypedesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR02.setInvesttypedesc(tempStr);
			cust.setInvesttypedesc(tempStr);
		}
		//资金筹备期
		tempStr=record02.getCapitalprepsection();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setCapitalprepsection(tempStr);
			projCustRef.setCapitalprepsection(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "017", "资金筹备期", "ql_AccessRecord02", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "017", "资金筹备期", "ql_ProjCust_Ref", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//关注产品类型描述
		tempStr=record02.getRealtyproducttypedesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR02.setRealtyproducttypedesc(tempStr);
			cust.setRealtyproducttypedesc(tempStr);
		}
		//关注面积
		tempStr=record02.getAttentacreage();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setAttentacreage(tempStr);
			cust.setAttentacreage(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "010", "关注面积", "ql_AccessRecord02", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "010", "关注面积", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//接受价格区段
		tempStr=record02.getPricesection();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setPricesection(tempStr);
			cust.setPricesection(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "011", "接受价格区段", "ql_AccessRecord02", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "011", "接受价格区段", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//购房目的
		tempStr=record02.getBuypurpose();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR02.setBuypurpose(tempStr);
			cust.setBuypurpose(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "012", "购房目的", "ql_AccessRecord02", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "012", "购房目的", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//购房目的描述
		tempStr=record02.getBuypurposedesc();
		if(StringUtils.isNotEmpty(tempStr)){
			_retR02.setBuypurposedesc(tempStr);
			cust.setBuypurposedesc(tempStr);
		}
		ret[0]=record02;
		ret[1]=cust;
		ret[2]=projCustRef;
		ret[3]=userCustRef;
		ret[4]=dictRefList;
		return ret;
	}
	//修改复访记录
	@RequestMapping(value = "/updateRecord02")
	@ResponseBody
	public String updateRecord02(HttpServletRequest requet,HttpServletResponse response,
			AccessRecord02 record02,Customer customer,String userId,String receptime1) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String openid = HashSessions.getInstance().getOpenId(request);
			UserInfo userInfo=null;
			if (StringUtils.isNotEmpty(openid)) userInfo=userInfoService.findByOpenid(openid);
			else if (StringUtils.isNotEmpty(userId)) userInfo=userInfoService.findById(userId);
			if (userInfo==null) throw new Exception("未获得用户，无法处理");
			String _userId=userInfo.getId();
			userId=userInfo.getId();
			//本次到访时间
			if(receptime1!=null){
				receptime1+=" 00:00:00";
				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = formata.parse(receptime1);				
				record02.setReceptime(parse);
			}
			//录入人权限人
			record02.setAuthorid(userId);
			record02.setCreatorid(userId);
			//扫描一次，处理本表，处理客户表，处理客户项目关系表，处理字典表；
			Object[] resultObjs=scan2(record02,1);
			if (accessRecord02Service.update((AccessRecord02)resultObjs[0])) {
				Deal01OtherTable d01=new Deal01OtherTable((Customer)resultObjs[1], (ProjCustRef)resultObjs[2], (UserCustRef)resultObjs[3], (List<TabDictRef>)resultObjs[4], 1);
				d01.start();
				map.put("msg", "100");
			} else {
				map.put("msg", "104");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}	
	
	
	
	//添加成交记录
	@RequestMapping(value = "/addTradeVisit")
	@ResponseBody
	public String addTradeVisit(HttpServletRequest requet,HttpServletResponse response,
			AccessRecord03 record03,Customer customer,
			String receptime1,String userId,String signdate1,String purchasedate1) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String openid = HashSessions.getInstance().getOpenId(request);
			UserInfo userInfo=null;
			if (StringUtils.isNotEmpty(openid)) userInfo=userInfoService.findByOpenid(openid);
			else if (StringUtils.isNotEmpty(userId)) userInfo=userInfoService.findById(userId);
			if (userInfo==null) throw new Exception("未获得用户，无法处理");
			String _userId=userInfo.getId();
			userId=userInfo.getId();
			//补全首访信息-并更新
			record03.setId(key.getUUIDKey());
			//本次到访时间
			if(receptime1!=null){
				receptime1+=" 00:00:00";
				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = formata.parse(receptime1);				
				record03.setReceptime(parse);
			}
			//购买日期
			if(purchasedate1!=null){
				purchasedate1+=" 00:00:00";
				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = formata.parse(purchasedate1);				
				record03.setPurchasedate(parse);
			}
			//签约日期
			if(signdate1!=null){
				signdate1+=" 00:00:00";
				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = formata.parse(signdate1);				
				record03.setSigndate(parse);
			}
			//录入人权限人
			record03.setAuthorid(userId);
			record03.setCreatorid(userId);
			//扫描一次，处理本表，处理客户表，处理客户项目关系表，处理字典表；
			Object[] resultObjs=scan3(record03,0);
			if (accessRecord03Service.insert((AccessRecord03)resultObjs[0])) {
				Deal01OtherTable d01=new Deal01OtherTable((Customer)resultObjs[1], (ProjCustRef)resultObjs[2], (UserCustRef)resultObjs[3], (List<TabDictRef>)resultObjs[4], 0);
				d01.start();
				map.put("msg", "100");
			} else {
				map.put("msg", "104");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}	
	//成交处理表字段信息
	private Object[] scan3(AccessRecord03 record03,int type) {
		Object[] ret=new Object[5];
		AccessRecord03 _retR03 = new AccessRecord03();
		Customer cust=new Customer(); //从01中汇出的客户信息，可直接参与数据库操作
		ProjCustRef projCustRef=new ProjCustRef(); //从01中汇出的客户项目关系，可直接参与数据库操作
		UserCustRef userCustRef=new UserCustRef(); //
		List<TabDictRef> dictRefList=new ArrayList<TabDictRef>(); //可以直接参与处理字典项与表关系处理的对象列表
		//获取所有对象的属性
		//本表Id
		String id = "";
		//项目Id
		String projId = "";
		//客户表ID
		String customerId = "";
		//临时字符串
		String tempStr = "";
		//返回结果
		Map<String, Object> parseResult = null;
		//若是新增，设置关联对象的Id
		if (type==0) {
			String phone=record03.getCustphonenum();
			String[] phones = phone.split(",");
			if(phones.length>1) phone=phones[0];
			//是否以有该客户信息
			Customer customer = customerService.findByPhone(phone);
			if(customer==null){
				projCustRef.setId(key.getUUIDKey());
				userCustRef.setId(key.getUUIDKey());
			}
		}
		//成交Id
		if (type==0) id=key.getUUIDKey();
		else id=record03.getId();
		_retR03.setId(id);
		//项目Id
		projId=record03.getProjid();
		_retR03.setProjid(projId);
		projCustRef.setProjid(projId);
		userCustRef.setProjid(projId);
		//用户Id
		if (type==0) customerId=key.getUUIDKey();
		else customerId=record03.getCustid();
		_retR03.setCustid(customerId);
		cust.setId(customerId);
		projCustRef.setCustid(customerId);
		userCustRef.setCustid(customerId);
		//买房人姓名
		_retR03.setCustname(record03.getCustname());
		//联系方式
		_retR03.setCustphonenum(record03.getCustphonenum());
		cust.setPhonenum(record03.getCustphonenum());
		//客户性别
		tempStr=record03.getCustsex();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR03.setCustsex(tempStr);
			cust.setCustsex(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "002", "客户性别", "ql_AccessRecord03", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
			List<TabDictRef> cartotalpriceCust=transToDictRefList(dictList, "002", "客户性别", "ql_Customer", customerId);
			if (cartotalpriceCust!=null) dictRefList.addAll(cartotalpriceCust);
		}
		//成交周期到访
		_retR03.setVisitcycle(record03.getVisitcycle());
		//成交周期认购
		_retR03.setPurchasecycle(record03.getPurchasecycle());
		//成交周期签约
		_retR03.setSigncycle(record03.getSigncycle());
		//认购日期
		_retR03.setPurchasedate(record03.getPurchasedate());
		//签约日期
		_retR03.setSigndate(record03.getSigndate());
		//购买房号
		_retR03.setHousenum(record03.getHousenum());
		//户籍类型
		tempStr=record03.getHouseregitype();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR03.setHouseregitype(tempStr);
			cust.setHouseregitype(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "029", "户籍类型", "ql_AccessRecord03", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
			List<TabDictRef> cartotalpriceCust=transToDictRefList(dictList, "029", "户籍类型", "ql_Customer", customerId);
			if (cartotalpriceCust!=null) dictRefList.addAll(cartotalpriceCust);
		}
		//成交面积
		_retR03.setHouseacreage(record03.getHouseacreage());
		//成交单价
		_retR03.setUnitprice(record03.getUnitprice());
		//成交总价
		_retR03.setTotalprice(record03.getTotalprice());
		//付款方式
		tempStr=record03.getPaymenttype();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR03.setHouseregitype(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "030", "付款方式", "ql_AccessRecord03", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
		}
		//贷款银行
		tempStr=record03.getLoanbank();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR03.setLoanbank(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "030", "贷款银行", "ql_AccessRecord03", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
		}
		//购买产品类型
		tempStr=record03.getRealtyproducttype();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR03.setRealtyproducttype(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "009", "购买产品类型", "ql_AccessRecord03", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
		}
		//通邮地址
		_retR03.setAddressmail(record03.getAddressmail());
		//实际居住人情况
		tempStr=record03.getLivingstatus();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR03.setLivingstatus(tempStr);
			cust.setLivingstatus(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> cartotalpriceO1=transToDictRefList(dictList, "005", "实际居住人情况 ", "ql_AccessRecord03", id);
			if (cartotalpriceO1!=null) dictRefList.addAll(cartotalpriceO1);
			List<TabDictRef> cartotalpriceCust=transToDictRefList(dictList, "005", "实际居住人情况", "ql_Customer", customerId);
			if (cartotalpriceCust!=null) dictRefList.addAll(cartotalpriceCust);
		}
		//实际使用人
		_retR03.setRealusemen(record03.getRealusemen());
		//实际出资人
		_retR03.setRealpaymen(record03.getRealpaymen());
		//意见建议
		_retR03.setSuggestion(record03.getSuggestion());
		//谈判问题与解决
		_retR03.setTalkqands(record03.getTalkqands());
		//谈判问题与解决
		_retR03.setSignqands(record03.getSignqands());
		//本次接待时间
		_retR03.setReceptime(record03.getReceptime());
		//综合描述
		_retR03.setSumdescn(record03.getSumdescn());
		//权限用户ID
		_retR03.setAuthorid(record03.getAuthorid());
		//记录状态
		_retR03.setStatus(1);
		//创建人ID
		_retR03.setCreatorid(record03.getCreatorid());
		ret[0]=record03;
		ret[1]=cust;
		ret[2]=projCustRef;
		ret[3]=userCustRef;
		ret[4]=dictRefList;
		return ret;
	}
	
	//修改成交记录
	@RequestMapping(value = "/updateRecord03")
	@ResponseBody
	public String updateRecord03(HttpServletRequest requet,HttpServletResponse response,
			AccessRecord03 record03,Customer customer,
			String receptime1,String userId,String signdate1,String purchasedate1) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String openid = HashSessions.getInstance().getOpenId(request);
			UserInfo userInfo=null;
			if (StringUtils.isNotEmpty(openid)) userInfo=userInfoService.findByOpenid(openid);
			else if (StringUtils.isNotEmpty(userId)) userInfo=userInfoService.findById(userId);
			if (userInfo==null) throw new Exception("未获得用户，无法处理");
			String _userId=userInfo.getId();
			userId=userInfo.getId();
			//本次到访时间
			if(receptime1!=null){
				receptime1+=" 00:00:00";
				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = formata.parse(receptime1);				
				record03.setReceptime(parse);
			}
			//购买日期
			if(purchasedate1!=null){
				purchasedate1+=" 00:00:00";
				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = formata.parse(purchasedate1);				
				record03.setPurchasedate(parse);
			}
			//签约日期
			if(signdate1!=null){
				signdate1+=" 00:00:00";
				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = formata.parse(signdate1);				
				record03.setSigndate(parse);
			}
			//录入人权限人
			record03.setAuthorid(userId);
			record03.setCreatorid(userId);
			//扫描一次，处理本表，处理客户表，处理客户项目关系表，处理字典表；
			Object[] resultObjs=scan3(record03,0);
			if (accessRecord03Service.insert((AccessRecord03)resultObjs[0])) {
				Deal01OtherTable d01=new Deal01OtherTable((Customer)resultObjs[1], (ProjCustRef)resultObjs[2], (UserCustRef)resultObjs[3], (List<TabDictRef>)resultObjs[4], 0);
				d01.start();
				map.put("msg", "100");
			} else {
				map.put("msg", "104");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}	

	//个人中心
	@RequestMapping(value = "/personalCenter")
	@ResponseBody
    public String personalCenter(HttpServletRequest requet,HttpServletResponse response){
    	responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> datamsg = new HashMap<String, Object>();
		try {
			String openid = HashSessions.getInstance().getOpenId(request);
			UserInfo userInfo = userInfoService.findByOpenid(openid);
			String userId = userInfo.getId();
			String loginname = userInfo.getLoginname();
			String headimgurl = userInfo.getHeadimgurl();
			Integer isvalidate = userInfo.getIsvalidate();
			String realname = userInfo.getRealname();
			String selfprojauth = userInfo.getSelfprojauth();
			String mainphonenum = userInfo.getMainphonenum();
			Map<String, Object> findByUserId = sysUserRoleService.findByUserId(userId);
			String roleName = findByUserId.get("role_name").toString();
			if (isvalidate == 1) {
				String message = "";
				List<Map<String, Object>> projData = projUserRoleService.selectByUserId(userId);
				if (projData!=null&&!projData.isEmpty()) {
	                for(Map<String, Object> proj : projData){
	                    String projName = proj.get("projName").toString();
	                    String id = proj.get("id").toString();
	                    message += ","+id+"-"+projName;
	                }
	                message=message.substring(1);
				}
				map.put("checkProj", message);
			}else{
				map.put("checkProj", "");
			}
			//添加信息
            map.put("userid", userInfo.getId());
            map.put("loginname", loginname);
			map.put("realname", realname);
			map.put("mainphonenum", mainphonenum);
			map.put("roleName", roleName);
			map.put("selfprojauth", selfprojauth);
			map.put("isvalidate", isvalidate);
			map.put("headimgurl", headimgurl);
			datamsg.put("msg", "100");
			datamsg.put("userInfo", map);
		}catch(Exception e){
		    datamsg.put("msg", "103");
			e.printStackTrace();
		}
		System.out.println(JsonUtils.map2json(datamsg));
		return JsonUtils.map2json(datamsg);
    }

	//修改密码
	@RequestMapping("/updateUserPwd")
	@ResponseBody
	public String updateUserPwd(Model model,HttpServletResponse response,String newPwd){
		responseInfo(response);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//用户信息
			String openid = HashSessions.getInstance().getOpenId(request);
			if(openid!=null){
				UserInfo user = userInfoService.selectByOpenId(openid);
				user.setPassword(MD5Utils.MD5(newPwd));
				userInfoService.update(user);
				map.put("msg", "100");
			}else{
				map.put("msg", "103");
			}
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}

	//获取首访记录
	@RequestMapping(value = "/getRecord01")
	@ResponseBody
	public String getRecord01(HttpServletRequest req){
		responseInfo(response);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
		    Map<String, Object> m=RequestUtils.getDataFromRequest(req);
			String recordId=m.get("recordId")==null?null:(String)m.get("recordId");
			if(recordId != null&&!"".equals(recordId)){
				AccessRecord01 accessRecord01 = accessRecord01Service.findById(recordId);
				map.put("msg", "100");
				map.put("data", accessRecord01);
			}else{
				map.put("msg", "103");
			}
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}

	//获取复访记录
	@RequestMapping(value = "/getRecord02")
	@ResponseBody
	public String getRecord02(HttpServletRequest req){
		Map<String, Object> m=RequestUtils.getDataFromRequest(req);
		responseInfo(response);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String recordId = m.get("recordId")==null?null:m.get("recordId").toString();
			if(recordId != null && !"".equals(recordId)){
				AccessRecord02 accessRecord02 = accessRecord02Service.findById(recordId);
				map.put("msg", "100");
				map.put("data", accessRecord02);
			}else{
				map.put("msg", "103");
			}
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}

	
	//获取成交记录
	@RequestMapping(value = "/getRecord03")
	@ResponseBody
	public String getRecord03(HttpServletRequest req){
		Map<String, Object> m=RequestUtils.getDataFromRequest(req);
		responseInfo(response);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String recordId = m.get("recordId")==null?null:m.get("recordId").toString();
			if(recordId != null && !"".equals(recordId)){
				AccessRecord03 accessRecord03 = accessRecord03Service.findById(recordId);
				map.put("msg", "100");
				map.put("data", accessRecord03);
			}else{
				map.put("msg", "103");
			}
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	//获取首访记录列表
	@RequestMapping(value = "/getRecord01List")
	@ResponseBody
	public String getRecord01List(HttpServletRequest req,
			@RequestParam(value="page",defaultValue="1") int nowPage,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize,
			@RequestParam(value="searchStr") String searchStr){
		responseInfo(response);
		String openid = HashSessions.getInstance().getOpenId(request);
		UserInfo userInfo = userInfoService.selectByOpenId(openid);
		String userId = userInfo.getId();
		Map<String,Object> retMap = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			int page=((nowPage - 1) * pageSize);
			param.put("page", page);
			param.put("pageSize", pageSize);
			param.put("userId", userId);
			//权限信息
			String roleName="";
			Map<String,Object> userRole = sysUserRoleService.findByUserId(userId);
			if(userRole!=null){
				Object object = userRole.get("role_name");
				if(object!=null)  {
					List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
					roleName = object.toString();
					//用户相关项目信息
					List<Map<String, Object>> projs = projUserRoleService.selectByUserId(userId);
					String projid = "";
					for(Map<String,Object> proj : projs){
						String projId = proj.get("id").toString();
						projid += projId+",";
					}
					//处理查询字符串
					if (StringUtils.isNotEmpty(searchStr)) {
						searchStr = searchStr.replaceAll(" ", ",");
						String searchSql="";
						String[] l=searchStr.split(",");
						for (int i=0; i<l.length; i++) {
							if (StringUtils.isNotEmpty(l[i])) {
								searchSql+=" or instr(m.custName, '"+l[i].trim()+"') or instr(m.custPhoneNum, '"+l[i]+"')";
							}
						}
						searchSql=searchSql.substring(4);
						param.put("searchSql", searchSql);
					}
					//判断
					if(roleName.equals("顾问")){
						resultList = accessRecord01Service.getRecord01ListGuWen(param);
					}else if(roleName.equals("项目管理人")){
						param.put("projId", projid);
						resultList = accessRecord01Service.getRecord01ListGuanLi(param);
					}else if(roleName.equals("项目负责人")){
						param.put("projId", projid);
						resultList = accessRecord01Service.getRecord01ListFuZe(param);
					}else if(roleName.equals("管理员")){
						resultList = accessRecord01Service.getRecord01ListAdmin(param);
					}
					retMap.put("msg", "100");
					retMap.put("data", resultList);
				}else{
					retMap.put("msg", "103");
				}
			}else{
				retMap.put("msg", "103");
			}
		} catch (Exception e) {
			retMap.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(retMap);
	}
	//获取复访记录列表
	@RequestMapping(value = "/getRecord02List")
	@ResponseBody
	public String getRecord02List(HttpServletRequest req,
			@RequestParam(value="page",defaultValue="1") int nowPage,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize,
			@RequestParam(value="searchStr") String searchStr){
		responseInfo(response);
		String openid = HashSessions.getInstance().getOpenId(request);
		UserInfo userInfo = userInfoService.selectByOpenId(openid);
		String userId = userInfo.getId();
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			Integer page = ((nowPage - 1) * pageSize);
			result.put("page", page);
			result.put("pageSize", pageSize);
			result.put("userId", userId);
			//权限信息
			String roleName="";
			Map<String,Object> userRole = sysUserRoleService.findByUserId(userId);
			if(userRole!=null){
				Object object = userRole.get("role_name");
				if(object!=null)  {
					List<Map<String,Object>> message = new ArrayList<Map<String,Object>>();
					roleName = object.toString();
					//用户相关项目信息
					List<Map<String, Object>> projs = projUserRoleService.selectByUserId(userId);
					String projid = "";
					for(Map<String,Object> proj : projs){
						String projId = proj.get("id").toString();
						projid += projId+",";
					}
					//处理查询字符串
					if (StringUtils.isNotEmpty(searchStr)) {
						searchStr = searchStr.replaceAll(" ", ",");
						String searchSql="";
						String[] l=searchStr.split(",");
						for (int i=0; i<l.length; i++) {
							if (StringUtils.isNotEmpty(l[i])) {
								searchSql+=" or instr(m.custName, '"+l[i].trim()+"') or instr(m.custPhoneNum, '"+l[i]+"')";
							}
						}
						searchSql=searchSql.substring(4);
						result.put("searchSql", searchSql);
					}
					//判断
					if(roleName.equals("顾问")){
						message = accessRecord02Service.getRecord02ListGuWen(result);
					}else if(roleName.equals("项目管理人")){
						result.put("projId", projid);
						message = accessRecord02Service.getRecord02ListGuanLi(result);
					}else if(roleName.equals("项目负责人")){
						result.put("projId", projid);
						message = accessRecord02Service.getRecord02ListFuZe(result);
					}else if(roleName.equals("管理员")){
						message = accessRecord02Service.getRecord02ListAdmin(result);
					}
					map.put("msg", "100");
					map.put("data", message);
				}else{
					map.put("msg", "103");
				}
			}else{
				map.put("msg", "103");
			}
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	//获取成交记录列表
	@RequestMapping(value = "/getRecord03List")
	@ResponseBody
	public String getRecord03List(HttpServletRequest req,
			@RequestParam(value="page",defaultValue="1") int nowPage,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize,
			@RequestParam(value="searchStr") String searchStr){
		responseInfo(response);
		String openid = HashSessions.getInstance().getOpenId(request);
		UserInfo userInfo = userInfoService.selectByOpenId(openid);
		String userId = userInfo.getId();
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			Integer page = ((nowPage - 1) * pageSize);
			result.put("page", page);
			result.put("pageSize", pageSize);
			result.put("userId", userId);
			//权限信息
			String roleName="";
			Map<String,Object> userRole = sysUserRoleService.findByUserId(userId);
			if(userRole!=null){
				Object object = userRole.get("role_name");
				if(object!=null)  {
					List<Map<String,Object>> message = new ArrayList<Map<String,Object>>();
					roleName = object.toString();
					//用户相关项目信息
					List<Map<String, Object>> projs = projUserRoleService.selectByUserId(userId);
					String projid = "";
					for(Map<String,Object> proj : projs){
						String projId = proj.get("id").toString();
						projid += projId+",";
					}
					//处理查询字符串
					if (StringUtils.isNotEmpty(searchStr)) {
						searchStr = searchStr.replaceAll(" ", ",");
						String searchSql="";
						String[] l=searchStr.split(",");
						for (int i=0; i<l.length; i++) {
							if (StringUtils.isNotEmpty(l[i])) {
								searchSql+=" or instr(m.custName, '"+l[i].trim()+"') or instr(m.custPhoneNum, '"+l[i]+"')";
							}
						}
						searchSql=searchSql.substring(4);
						result.put("searchSql", searchSql);
					}
					//判断
					if(roleName.equals("顾问")){
						message = accessRecord03Service.getRecord03ListGuWen(result);
					}else if(roleName.equals("项目管理人")){
						result.put("projId", projid);
						message = accessRecord03Service.getRecord03ListGuanLi(result);
					}else if(roleName.equals("项目负责人")){
						result.put("projId", projid);
						message = accessRecord03Service.getRecord03ListFuZe(result);
					}else if(roleName.equals("管理员")){
						message = accessRecord03Service.getRecord03ListAdmin(result);
					}
					map.put("msg", "100");
					map.put("data", message);
				}else{
					map.put("msg", "103");
				}
			}else{
				map.put("msg", "103");
			}
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	
	//获取审核内容
	@RequestMapping(value = "/getCheckReason")
	@ResponseBody
	public String getCheckReason(HttpServletRequest req){
		Map<String, Object> m=RequestUtils.getDataFromRequest(req);
		Map<String, Object> data=new HashMap<String,Object>();
		Map<String, Object> map=new HashMap<String,Object>();
		try {
			String recordType = m.get("recordType")==null?null:m.get("recordType").toString();
			String arId = m.get("recordId")==null?null:m.get("recordId").toString();
			data.put("recordType", recordType);
			data.put("arId", arId);
			AuditRecord auditRecord = auditRecordService.findByArId(data);
			if(auditRecord!=null){
				String reason = auditRecord.getReason();
				if(reason!=null) map.put("checkReason",reason);
				if(reason==null) map.put("checkReason","");
			}else{
				map.put("checkReason","");
			}
			map.put("msg", "100");
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	//获取记录审核信息列表
	@RequestMapping(value = "/getAuditList")
	@ResponseBody
	public String getAuditList(HttpServletRequest req,
			@RequestParam(value="page",defaultValue="1") int nowPage,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize){
		Map<String, Object> m=RequestUtils.getDataFromRequest(req);
		responseInfo(response);
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String recordId = m.get("recordId")==null?null:m.get("recordId").toString();
			String recordType = m.get("recordType")==null?null:m.get("recordType").toString();
//			String userId = m.get("userId")==null?null:m.get("userId").toString();
			result.put("recordId", recordId);
			result.put("recordType", recordType);
			Integer page = ((nowPage - 1) * pageSize);
			result.put("page", page);
			result.put("pageSize", pageSize);
			List<Map<String,Object>> message = auditRecordService.selectByRecordIdAndType(result);
			map.put("msg", "100");
			map.put("auditList", message);
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	
	//录入记录审核信息
	@RequestMapping(value = "/addAudit")
	@ResponseBody
	public String addAudit(HttpServletRequest req){
		Map<String, Object> m=RequestUtils.getDataFromRequest(req);
		responseInfo(response);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String recordId = m.get("recordId")==null?null:m.get("recordId").toString();
			String recordType = m.get("recordType")==null?null:m.get("recordType").toString();
			String auditMsg = m.get("auditMsg")==null?null:m.get("auditMsg").toString();
			String auditType = m.get("auditType")==null?null:m.get("auditType").toString();
			AuditRecord auditRecord = new AuditRecord();
			auditRecord.setId(key.getUUIDKey());
			auditRecord.setRecordtype(Integer.parseInt(recordType));
			auditRecord.setAudittype(Integer.parseInt(auditType));
			auditRecord.setReason(auditMsg);
			auditRecord.setarid(recordId);
			auditRecord.setAudittype(2);
			auditRecordService.insert(auditRecord);
			//修改状态
			if(recordType.equals("1")){
				AccessRecord01 accessRecord01 = accessRecord01Service.findById(recordId);
				if(Integer.parseInt(auditType)==1) accessRecord01.setStatus(2);
				if(Integer.parseInt(auditType)==2) accessRecord01.setStatus(4);
				accessRecord01Service.update(accessRecord01);
			}else if(recordType.equals("2")){
				AccessRecord02 accessRecord02 = accessRecord02Service.findById(recordId);
				if(Integer.parseInt(auditType)==1) accessRecord02.setStatus(2);
				if(Integer.parseInt(auditType)==2) accessRecord02.setStatus(4);
				accessRecord02Service.update(accessRecord02);
			}else if(recordType.equals("3")){
				AccessRecord03 accessRecord03 = accessRecord03Service.findById(recordId);
				if(Integer.parseInt(auditType)==1) accessRecord03.setStatus(2);
				if(Integer.parseInt(auditType)==2) accessRecord03.setStatus(4);
				accessRecord03Service.update(accessRecord03);
			}
			map.put("msg", "100");
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}

    @RequestMapping(value = "/getLocalArea")
    @ResponseBody
    public String getLocalArea(HttpServletRequest req){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            DictModel dm=dictService.getDictModelById("032");
            TreeNode<? extends TreeNodeBean> root=dm.dictTree;
            map.put("data", (new ZTree<DictDetail>(root)).toTreeMap());
            map.put("msg", "100");
        } catch (Exception e) {
            map.put("msg", "103");
            e.printStackTrace();
        }
        return JsonUtils.map2json(map);
    }
    
    
    
	//获取用户列表
	@RequestMapping(value = "/getUserList")
	@ResponseBody
	public String getUserList(HttpServletRequest req){
		Map<String, Object> m=RequestUtils.getDataFromRequest(req);
		responseInfo(response);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String projId = m.get("projId")==null?null:m.get("projId").toString();
			//获取用户列表
			List<Map<String,Object>> users = projUserRoleService.selectByProjId(projId);
			map.put("users", users);
			map.put("msg", "100");
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	//获取客户列表
	@RequestMapping(value = "/getCustList")
	@ResponseBody
	public String getCustList(HttpServletRequest req){
		Map<String, Object> m=RequestUtils.getDataFromRequest(req);
		responseInfo(response);
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String projId = m.get("projId")==null?null:m.get("projId").toString();
			String userId = m.get("userId")==null?null:m.get("userId").toString();
			//获取用户列表
			result.put("projId", projId);
			result.put("userId", userId);
			List<Map<String,Object>> customers = projUserRoleService.selectByProjIdAndUserId(result);
			map.put("customers", customers);
			map.put("msg", "100");
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getOutArea")
    @ResponseBody
    public String getOutArea(HttpServletRequest req){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            DictModel dm=dictService.getDictModelById("001");
            TreeNode<? extends TreeNodeBean> root=dm.dictTree;
            Map<String, Object> tM=(new ZTree<DictDetail>(root)).toTreeMap();

			List<Object> l=(List<Object>)tM.get("children");
            int i=0;
            for (; i<l.size(); i++) {
                Map<String, Object> nM=(Map<String, Object>)l.get(i);
                if (nM.get("name").equals("北京市")) break;
            }
            if (i<l.size()) l.remove(i);
            map.put("data", tM);
            map.put("msg", "100");
        } catch (Exception e) {
            map.put("msg", "103");
            e.printStackTrace();
        }
        return JsonUtils.map2json(map);
    }

    @RequestMapping(value = "/testGet01List")
    @ResponseBody
    public String testGet01List(HttpServletRequest req,
    		@RequestParam(value="page",defaultValue="1") int page,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize) {
    	int begin=((page<1?1:page)-1)*pageSize;
    	int end=begin+pageSize;
        Map<String,Object> map = new HashMap<String,Object>();
    	if (end-begin==0) map.put("msg", "103");
    	else {
    		List<Map<String, Object>> listData=new ArrayList<Map<String, Object>>();
        	for (int i=begin; i<end; i++) {
        		Map<String, Object> oneData=new HashMap<String, Object>();

        		oneData.put("id", i);
        		oneData.put("custName", "测试客户"+i);
        		oneData.put("custSex", Math.random()-0.5>0?"男":"女");
        		Random r=new Random(); 
        		oneData.put("custPhoneNum", "139876543"+r.nextInt(100));
        		Map<String, Object> recepTime=new HashMap<String, Object>();
        		recepTime.put("time", System.currentTimeMillis());
        		oneData.put("recepTime", recepTime);
        		oneData.put("status", r.nextInt(3)+1);
        		oneData.put("authorName", "顾问"+r.nextInt(100));
        		oneData.put("isKnockdown", Math.random()-0.5>0?"1":"2");
        		listData.add(oneData);
        	}
			map.put("msg", "100");
			map.put("data", listData);
    	}
        return JsonUtils.map2json(map);
    }
    
    
    
    //获取客户详细信息
	@RequestMapping(value = "/getCustMsg")
	@ResponseBody
	public String getCusMsg(HttpServletRequest req){
		Map<String, Object> m=RequestUtils.getDataFromRequest(req);
		responseInfo(response);
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> parmeterMap = new HashMap<String,Object>();
		try {
			String custId = m.get("custId")==null?null:m.get("custId").toString();
			String projId = m.get("projId")==null?null:m.get("projId").toString();
			if(custId != null){
				if(projId!=null){
					parmeterMap.put("custId", custId);
					parmeterMap.put("custId", custId);
					//获取客户详细信息
					Customer customer = customerService.findById(custId);
					//首次获取时间
					List<AccessRecord01> accessRecord01s = accessRecord01Service.selectByUserId(parmeterMap);
					Date firstknowtime = accessRecord01s.get(0).getFirstknowtime();
					//复访总次数
					Integer totalRecord02 = accessRecord01Service.selectByCustIdAndProjId(parmeterMap);
					customer.setFirstvisittime(firstknowtime);
					customer.setVisitcount(totalRecord02);
					map.put("customer", customer);
					map.put("msg", "100");
				}else{
					map.put("msg", "201");
				}
			}else{
				map.put("msg", "200");
			}
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	//获取客户项目关系表信息
	@RequestMapping(value = "/getProjCustList")
	@ResponseBody
	public String getProjCusList(HttpServletRequest req){
		Map<String, Object> m=RequestUtils.getDataFromRequest(req);
		responseInfo(response);
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String projId = m.get("projId")==null?null:m.get("projId").toString();
			String custId = m.get("custId")==null?null:m.get("custId").toString();
			if(projId !=null){
				if(custId!=null){
					//获取用户列表
					result.put("projId", projId);
					result.put("cusId", custId);
					List<Map<String,Object>> projCustRefList = projCustRefService.selectByProjIdAndCusId(result);
					map.put("projCustRefList", projCustRefList);
					map.put("msg", "100");
				}else{
					map.put("msg", "201");
				}
			}else{
				map.put("msg", "200");
			}
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}

	class Deal01OtherTable extends Thread {
		int type=0;//0=insert;1=update
		Customer cust=null;
		ProjCustRef projCustRef=null;
		UserCustRef userCustRef=null;
		List<TabDictRef> tabDictRefList=null;
		public Deal01OtherTable(Customer cust,ProjCustRef projCustRef, UserCustRef userCustRef, List<TabDictRef> tabDictRefList, int type) {
			this.cust=cust;
			this.projCustRef=projCustRef;
			this.tabDictRefList=tabDictRefList;
			this.userCustRef=userCustRef;
			this.type=type;
		}
		@Override
        public void run() {
			try {
				//处理客户
			    if (type==0) {
			    	String id = cust.getId();
			    	if(StringUtils.isNotEmpty(id)){
			    		Customer custData = customerService.findById(id);
			    		if(custData!=null) customerService.update(cust);
			    		else customerService.insert(cust);
			    	}
			    }
			    else customerService.update(cust);
			    //处理用户客户
			    if (type==0) {
			    	String id = userCustRef.getId();
			    	if(StringUtils.isNotEmpty(id)) {
			    		UserCustRef usercusData = userCustRefService.findById(id);
			    		if(usercusData!=null) userCustRefService.update(userCustRef);
			    		else userCustRefService.insert(userCustRef);
			    	}
			    }
			    //else userCustRefService.update(userCustRef);
			    //处理用户项目
			    if (type==0) {
			    	String id = projCustRef.getId();
			    	if(StringUtils.isNotEmpty(id)) {
			    		ProjCustRef projData = projCustRefService.findById(id);
			    		if(projData!=null) projCustRefService.update(projCustRef);
			    		else projCustRefService.insert(projCustRef);
			    	}
			    }
			    else projCustRefService.update(projCustRef);
			    //处理字典
			    String _tabName=null, _tabId=null, _refName=null;
			    if (tabDictRefList!=null) {
			    	for (TabDictRef tdr: tabDictRefList) {
			    		if (type!=0) {
				    		if (!(tdr.getTabname().equals(_tabName)&&!tdr.getTabid().equals(_tabId)&&!tdr.getRefname().equals(_refName))) {
				    			tabDictRefService.delete4TabColum(tdr);
				    			_tabName=tdr.getTabname();
				    			_tabId=tdr.getTabid();
				    			_refName=tdr.getRefname();
				    		}
			    		}
			    		tabDictRefService.insert(tdr);
			    	}
			    }
			} catch(Exception e) {
			}
        }
	}
}
