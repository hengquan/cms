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
	
	
	
	
	
	
	//通用添加字典对应关系表
	public String addAccessRecord(String dataMessage,String dmid,Integer type
			,String refname,String customerId,List<TabDictRef> dictRefList){
		String resultData = "";
		String tableName = "";
		if(type == 1){
			tableName = "ql_AccessRecord01";
		}else if(type == 2){
			tableName = "ql_AccessRecord02";
		}else if(type == 3){
			tableName = "ql_AccessRecord03";
		}else if(type == 4){
			tableName = "ql_Customer";
		}else if(type == 5){
			tableName = "ql_ProjCust_Ref";
		}
		try {
			String[] dataList = dataMessage.split(",");
			for(int i=0;i<dataList.length;i++){
				String[] split = dataList[i].split("-");
				Integer length = split.length;
				TabDictRef tabDictRef = new TabDictRef();
				if(length>2){
					String scopes = split[2];
					String[] scope = scopes.split("~");
					Integer scopeLength = scope.length;
					String scopeBegin = scope[0];
					tabDictRef.setSectionbegin(Float.parseFloat(scopeBegin));
					if(scopeLength>1){
						String scopeEnd = scope[1];
						tabDictRef.setSectionend(Float.parseFloat(scopeEnd));
					}
				}
				//添加关系
				tabDictRef.setId(key.getUUIDKey());
				tabDictRef.setDdid(split[0]);
				tabDictRef.setDmid(dmid);
				tabDictRef.setTabname(tableName);
				tabDictRef.setTabid(customerId);
				tabDictRef.setRefname(refname);
				//tabDictRefService.insert(tabDictRef);
				dictRefList.add(tabDictRef);
				resultData += ","+split[1];
			}
			resultData = resultData.substring(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultData;
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
	
	
	
	//首访添加修改通用属性
	public Boolean addRecord01(int type,AccessRecord01 record01,Customer customer
			,ProjCustRef projCustRef,UserCustRef userCustRef,List<TabDictRef> dictRefList){
		Boolean isok = false;
		try {
			//客户表ID
			String customerId = "";
			//客户项目关系表
			String proJcustId = "";
			//项目客户关系表
			String userCustRefId = "";
			if(type == 0){
				//客户ID
				customerId = key.getUUIDKey();
				customer.setId(customerId);
				//客户项目关系表ID
				proJcustId = key.getUUIDKey();
				projCustRef.setId(proJcustId);
				//项目客户关系表ID
				userCustRefId = key.getUUIDKey();
				userCustRef.setId(userCustRefId);
				userCustRef.setProjid(record01.getProjid());
		        userCustRef.setUserid(record01.getAuthorid());
		        userCustRef.setCustid(customer.getId());
			}else{
				//客户ID
				customerId = record01.getCustid();
				customer.setId(customerId);
				//客户项目关系表
				Map<String,Object>result=new HashMap<String,Object>();
				result.put("cusId", customerId);
				result.put("projId", record01.getProjid());
				projCustRef = projCustRefService.selectByCusIdAndProjId(result);
				proJcustId = projCustRef.getId();
				logger.debug("首访修改ID值 ：{}",proJcustId);
			}
			customer.setCustname(record01.getCustname());
			customer.setPhonenum(record01.getCustphonenum());
			customer.setTraffictypedesc(record01.getTraffictypedesc());
			customer.setWorkindustrydesc(record01.getWorkindustrydesc());
			customer.setEnterprisetypedesc(record01.getEnterprisetypedesc());
			customer.setRealtyproducttypedesc(record01.getRealtyproducttypedesc());
			customer.setCustdescn(record01.getCustdescn());
			projCustRef.setCompareprojs(record01.getCompareprojs());
			projCustRef.setAttentionpointdesc(record01.getAttentionpointdesc());
			projCustRef.setKnowwaydesc(record01.getKnowwaydesc());
			projCustRef.setFirstknowtime(record01.getFirstknowtime());
			//客户性别
			String custsex = record01.getCustsex();
			if(custsex!=null){
				String data = addAccessRecord(custsex,"002",1,"客户性别",record01.getId(),dictRefList);
				addAccessRecord(custsex,"002",4,"客户性别",customerId,dictRefList);
				record01.setCustsex(data);
				customer.setCustsex(data);
			}
			//年龄段
			String agegroup = record01.getAgegroup();
			if(agegroup != null){
				String data = addAccessRecord(agegroup,"003",1,"年龄段",record01.getId(),dictRefList);
				addAccessRecord(agegroup,"003",4,"年龄段",customerId,dictRefList);
				record01.setAgegroup(data);
				customer.setAgegroup(data);
			}
			//购房资格
			String buyqualify = record01.getBuyqualify();
			if(buyqualify != null){
				String data = addAccessRecord(buyqualify,"004",1,"购房资格",record01.getId(),dictRefList);
				addAccessRecord(buyqualify,"004",4,"购房资格",customerId,dictRefList);
				record01.setBuyqualify(data);
				customer.setBuyqualify(data);
			}
			//本地居住地
			String localresidence = record01.getLocalresidence();
			if(localresidence != null){
				String data = addAccessRecord(localresidence,"001",1,"本地居住地",record01.getId(),dictRefList);
				addAccessRecord(localresidence,"001",4,"本地居住",customerId,dictRefList);
				record01.setLocalresidence(data);
				customer.setLocalresidence(data);
			}
			//本地工作地
			String localworkarea = record01.getLocalworkarea();
			if(localworkarea != null){
				String data = addAccessRecord(localworkarea,"001",1,"本地工作地",record01.getId(),dictRefList);
				addAccessRecord(localworkarea,"001",4,"本地工作地",customerId,dictRefList);
				record01.setLocalworkarea(data);
				customer.setLocalworkarea(data);
			}
			//外阜居住地
			String outresidence = record01.getOutresidence();
			if(outresidence != null){
				String data = addAccessRecord(outresidence,"001",1,"外阜居住地",record01.getId(),dictRefList);
				addAccessRecord(outresidence,"001",4,"外阜居住地",customerId,dictRefList);
				record01.setOutresidence(data);
				customer.setOutresidence(data);
			}
			//外阜工作地
			String outworkarea = record01.getOutworkarea();
			if(outworkarea != null){
				String data = addAccessRecord(outworkarea,"001",1,"外阜工作地",record01.getId(),dictRefList);
				addAccessRecord(outworkarea,"001",4,"外阜工作地",customerId,dictRefList);
				record01.setOutworkarea(data);
				customer.setOutworkarea(data);
			}
			//家族状况
			String familystatus = record01.getFamilystatus();
			if(familystatus != null){
				String data = addAccessRecord(familystatus,"005",1,"家族状况",record01.getId(),dictRefList);
				addAccessRecord(familystatus,"005",4,"家族状况",customerId,dictRefList);
				record01.setFamilystatus(data);
				customer.setFamilystatus(data);
			}
			//出行方式
			String traffictype = record01.getTraffictype();
			if(traffictype != null){
				String data = addAccessRecord(traffictype,"006",1,"出行方式",record01.getId(),dictRefList);
				addAccessRecord(traffictype,"006",4,"出行方式",customerId,dictRefList);
				record01.setTraffictype(data);
				customer.setTraffictype(data);
			}
			//从事行业
			String workindustry = record01.getWorkindustry();
			if(workindustry != null){
				String data = addAccessRecord(workindustry,"007",1,"从事行业",record01.getId(),dictRefList);
				addAccessRecord(workindustry,"007",4,"从事行业",customerId,dictRefList);
				record01.setWorkindustry(data);
				customer.setWorkindustry(data);
			}
			//企业性质
			String enterprisetype = record01.getEnterprisetype();
			if(enterprisetype != null){
				String data = addAccessRecord(enterprisetype,"008",1,"企业性质",record01.getId(),dictRefList);
				addAccessRecord(enterprisetype,"008",4,"企业性质",customerId,dictRefList);
				record01.setEnterprisetype(data);
				customer.setEnterprisetype(data);
			}
			//关注产品类型
			String realtyproducttype = record01.getRealtyproducttype();
			if(realtyproducttype != null){
				String data = addAccessRecord(realtyproducttype,"009",1,"关注产品类型",record01.getId(),dictRefList);
				addAccessRecord(realtyproducttype,"009",4,"关注产品类型",customerId,dictRefList);
				record01.setRealtyproducttype(data);
				customer.setRealtyproducttype(data);
			}
			//关注面积
			String attentacreage = record01.getAttentacreage();
			if(attentacreage != null){
				String data = addAccessRecord(attentacreage,"010",1,"关注面积",record01.getId(),dictRefList);
				addAccessRecord(attentacreage,"010",4,"关注面积",customerId,dictRefList);
				record01.setAttentacreage(data);
				customer.setAttentacreage(data);
			}
			//接受价格区段
			String pricesection = record01.getPricesection();
			if(pricesection != null){
				String data = addAccessRecord(pricesection,"011",1,"接受价格区段",record01.getId(),dictRefList);
				addAccessRecord(pricesection,"011",4,"接受价格区段",customerId,dictRefList);
				record01.setPricesection(data);
				customer.setPricesection(data);
			}
			//购房目的
			String buypurpose = record01.getBuypurpose();
			if(buypurpose != null){
				String data = addAccessRecord(buypurpose,"012",1,"购房目的",record01.getId(),dictRefList);
				addAccessRecord(buypurpose,"012",4,"购房目的",customerId,dictRefList);
				record01.setBuypurpose(data);
				customer.setBuypurpose(data);
			}
			//认知本案渠道
			String knowway = record01.getKnowway();
			if(knowway != null){
				String data = addAccessRecord(knowway,"013",1,"认知本案渠道",record01.getId(),dictRefList);
				addAccessRecord(knowway,"013",5,"认知本案渠道",proJcustId,dictRefList);
				record01.setKnowway(data);
				projCustRef.setKnowway(data);
			}
			//本案关注点
			String attentionpoint = record01.getAttentionpoint();
			if(attentionpoint != null){
				String data = addAccessRecord(attentionpoint,"014",1,"本案关注点",record01.getId(),dictRefList);
				addAccessRecord(attentionpoint,"014",5,"本案关注点",proJcustId,dictRefList);
				record01.setAttentionpoint(data);
				projCustRef.setAttentionpoint(data);
			}
			//预估身价
			String estcustworth = record01.getEstcustworth();
			if(estcustworth != null){
				String data = addAccessRecord(estcustworth,"015",1,"预估身价",record01.getId(),dictRefList);
				addAccessRecord(estcustworth,"015",4,"预估身价",customerId,dictRefList);
				record01.setEstcustworth(data);
				customer.setEstcustworth(data);
			}
			//重点投资
			String investtype = record01.getInvesttype();
			if(investtype != null){
				String data = addAccessRecord(investtype,"016",1,"重点投资",record01.getId(),dictRefList);
				addAccessRecord(investtype,"016",4,"重点投资",customerId,dictRefList);
				record01.setInvesttype(data);
				customer.setInvesttype(data);
			}
			//资金筹备期
			String captilprepsection = record01.getCapitalprepsection();
			if(captilprepsection != null){
				String data = addAccessRecord(captilprepsection,"017",1,"资金筹备期",record01.getId(),dictRefList);
				addAccessRecord(captilprepsection,"017",4,"资金筹备期",customerId,dictRefList);
				record01.setCapitalprepsection(data);
				customer.setCaptilprepsection(data);
			}
			//本次接待时间
			String receptimesection = record01.getReceptimesection();
			if(receptimesection != null){
				String data = addAccessRecord(receptimesection,"018",1,"本次接待时间",record01.getId(),dictRefList);
				record01.setReceptimesection(data);
			}
			//客户评级
			String custscore = record01.getCustscore();
			if(custscore != null){
				String data = addAccessRecord(custscore,"019",1,"客户评级",record01.getId(),dictRefList);
				addAccessRecord(custscore,"019",4,"客户评级",customerId,dictRefList);
				record01.setCustscore(data);
				customer.setCustscore(data);
			}
			//添加客户项目关系
			projCustRef.setProjid(record01.getProjid());
			projCustRef.setCustid(customerId);
			logger.debug("项目客户关系表数据1：{}",JsonUtils.object2json(projCustRef));
			//添加首访表信息
			record01.setCustid(customerId);
			record01.setStatus(1);
			isok = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isok;
		
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

		//若是新增，设置关联对象胡Id
		if (type==0) {
			projCustRef.setId(key.getUUIDKey());
			userCustRef.setId(key.getUUIDKey());
		}
		//首访Id
		if (type==0) id=key.getUUIDKey();
		else id=record01.getId();
		_retR01.setId(id);
		//项目Id
		if (type==0) projId=key.getUUIDKey();
		else projId=record01.getProjid();
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
			List<TabDictRef> sexO1=transToDictRefList(dictList, "003", "年龄段", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "003", "年龄段", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//购房资格
		tempStr=record01.getBuyqualify();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setBuyqualify(tempStr);
			cust.setBuyqualify(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "004", "购房资格", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "004", "购房资格", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
		}
		//本地居住地
		tempStr=record01.getLocalresidence();
		parseResult=parseDictsStr(tempStr);
		if (parseResult!=null) {
			tempStr=parseResult.get("storeStr")+"";
			_retR01.setLocalresidence(tempStr);
			cust.setLocalresidence(tempStr);
			List<Map<String, Object>> dictList=(List<Map<String, Object>>)parseResult.get("dictList");
			List<TabDictRef> sexO1=transToDictRefList(dictList, "032", "本地居住地", "ql_AccessRecord01", id);
			if (sexO1!=null) dictRefList.addAll(sexO1);
			List<TabDictRef> sexCust=transToDictRefList(dictList, "032", "本地居住地", "ql_Customer", customerId);
			if (sexCust!=null) dictRefList.addAll(sexCust);
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
		if(StringUtils.isNotEmpty(dictsStr)) return null;
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
			tdr.setDdid(dictMid);
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
	
	
	
	//复访添加修改通用属性
	public Boolean addRecord02(int type,AccessRecord02 record02,Customer customer
			,ProjCustRef projCustRef,UserCustRef userCustRef,List<TabDictRef> dictRefList){
		Boolean isok = false;
		try {
			//客户表ID
			String customerId = "";
			//客户项目关系表
			String proJcustId = "";
			//项目客户关系表
			String userCustRefId = "";
			if(type == 0){
				//客户ID
				customerId = key.getUUIDKey();
				customer.setId(customerId);
				//客户项目关系表ID
				proJcustId = key.getUUIDKey();
				projCustRef.setId(proJcustId);
				//项目客户关系表ID
				userCustRefId = key.getUUIDKey();
				userCustRef.setId(userCustRefId);
				userCustRef.setProjid(record02.getProjid());
		        userCustRef.setUserid(record02.getAuthorid());
		        userCustRef.setCustid(customer.getId());
			}else{
				//客户ID
				customerId = record02.getCustid();
				customer.setId(customerId);
				//客户项目关系表
				Map<String,Object>result=new HashMap<String,Object>();
				result.put("cusId", customerId);
				result.put("projId", record02.getProjid());
				projCustRef = projCustRefService.selectByCusIdAndProjId(result);
				proJcustId = projCustRef.getId();
			}
			//客户表
			customer.setChildrennum(record02.getChildrennum());
			customer.setChildrennum(record02.getChildrennum());
			customer.setChildavocations(record02.getChildavocations());
			customer.setSchoolname(record02.getSchoolname());
			customer.setCommunityname(record02.getCommunityname());
			customer.setFulltimewifeflag(record02.getFulltimewifeflag());
			customer.setOuteduwill(record02.getOuteduwill());
			customer.setNannyflag(record02.getNannyflag());
			customer.setOutexperflag(record02.getOutexperflag());
			customer.setOutexpercity(record02.getOutexpercity());
			customer.setChildoutexperflag(record02.getChildoutexperflag());
			customer.setChildoutexpercity(record02.getChildoutexpercity());
			customer.setPetflag(record02.getPetflag());
			customer.setCarfamilycount(record02.getCarfamilycount());
			customer.setCarbrand(record02.getCarbrand());
			customer.setHousecount(record02.getHousecount());
			customer.setAttentwx(record02.getAttentwx());
			customer.setAppnames(record02.getAppnames());
			customer.setAvocationsdesc(record02.getAvocationsdesc());
			customer.setLoveactdesc(record02.getLoveactdesc());
			customer.setFreetimesection(record02.getFreetimesection());
			customer.setTraffictypedesc(record02.getTraffictypedesc());
			customer.setWorkindustrydesc(record02.getWorkindustrydesc());
			customer.setEnterprisetypedesc(record02.getEnterprisetypedesc());
			customer.setRealtyproducttypedesc(record02.getRealtyproducttypedesc());
			customer.setBuypurposedesc(record02.getBuypurposedesc());
			//客户项目关系表
			projCustRef.setResistpointdesc(record02.getResistpointdesc());
			projCustRef.setKnowwaydesc(record02.getKnowwaydesc());
			//小孩年龄段
			String childagegroup = record02.getChildagegroup();
			if(childagegroup != null){
				String data = addAccessRecord(childagegroup,"020",2,"小孩年龄段",record02.getId(),dictRefList);
				addAccessRecord(childagegroup,"020",4,"小孩年龄段",customerId,dictRefList);
				record02.setChildagegroup(data);
				customer.setChildagegroup(data);
			}
			//孩子学校类型
			String schooltype = record02.getSchooltype();
			if(schooltype != null){
				String data = addAccessRecord(schooltype,"021",2,"孩子学校类型",record02.getId(),dictRefList);
				addAccessRecord(childagegroup,"020",4,"孩子学校类型",customerId,dictRefList);
				record02.setSchooltype(data);
				customer.setSchooltype(data);
			}
			//生活半径
			String livingradius = record02.getLivingradius();
			if(livingradius != null){
				String data = addAccessRecord(livingradius,"022",2,"生活半径",record02.getId(),dictRefList);
				addAccessRecord(livingradius,"022",4,"生活半径",customerId,dictRefList);
				record02.setLivingradius(data);
				customer.setLivingradius(data);
			}
			//居住面积
			String liveacreage = record02.getLiveacreage();
			if(liveacreage != null){
				String data = addAccessRecord(liveacreage,"023",2,"居住面积",record02.getId(),dictRefList);
				addAccessRecord(liveacreage,"023",4,"居住面积",customerId,dictRefList);
				record02.setLiveacreage(data);
				customer.setLiveacreage(data);
			}
			//小孩业余爱好
			String childavocations = record02.getChildavocations();
			if(childavocations != null){
				String data = addAccessRecord(childavocations,"033",2,"小孩业余爱好",record02.getId(),dictRefList);
				addAccessRecord(childavocations,"033",4,"小孩业余爱好",customerId,dictRefList);
				record02.setChildavocations(data);
				customer.setChildavocations(data);
			}
			//贷款记录
			String loanstatus = record02.getLoanstatus();
			if(loanstatus != null){
				String data = addAccessRecord(loanstatus,"024",2,"贷款记录",record02.getId(),dictRefList);
				addAccessRecord(loanstatus,"024",4,"贷款记录",customerId,dictRefList);
				record02.setLoanstatus(data);
				customer.setLoanstatus(data);
			}
			//可参加业主活动时间
			String freetimesection = record02.getFreetimesection();
			if(freetimesection != null){
				String data = addAccessRecord(freetimesection,"109",2,"可参加业主活动时间",record02.getId(),dictRefList);
				addAccessRecord(freetimesection,"109",4,"可参加业主活动时间",customerId,dictRefList);
				record02.setFreetimesection(data);
				customer.setFreetimesection(data);
			}
			//汽车总价款
			String cartotalpricce = record02.getCartotalprice();
			if(cartotalpricce != null){
				String data = addAccessRecord(cartotalpricce,"025",2,"汽车总价款",record02.getId(),dictRefList);
				addAccessRecord(cartotalpricce,"025",4,"汽车总价款",customerId,dictRefList);
				record02.setCartotalprice(data);
				customer.setCartotalprice(data);
			}
			//业余爱好
			String avocations = record02.getAvocations();
			if(avocations != null){
				String data = addAccessRecord(avocations,"026",2,"业余爱好",record02.getId(),dictRefList);
				addAccessRecord(avocations,"026",4,"业余爱好",customerId,dictRefList);
				record02.setAvocations(data);
				customer.setAvocations(data);
			}
			//本案抗拒点
			String resistpoint = record02.getResistpoint();
			if(resistpoint != null){
				String data = addAccessRecord(resistpoint,"014",2,"本案抗拒点",record02.getId(),dictRefList);
				addAccessRecord(resistpoint,"014",5,"本案抗拒点",projCustRef.getId(),dictRefList);
				record02.setResistpoint(data);
				projCustRef.setResistpoint(data);
			}
			//喜欢活动
			String loveactivation = record02.getLoveactivation();
			if(loveactivation != null){
				String data = addAccessRecord(loveactivation,"027",2,"喜欢活动",record02.getId(),dictRefList);
				addAccessRecord(loveactivation,"027",4,"喜欢活动",customerId,dictRefList);
				record02.setLoveactivation(data);
				customer.setLoveactivation(data);
			}
			//来访人关系
			String visitorrefs = record02.getVisitorrefs();
			if(visitorrefs != null){
				String data = addAccessRecord(visitorrefs,"028",2,"来访人关系",record02.getId(),dictRefList);
				record02.setVisitorrefs(data);
			}
			//本次接待时间
			String receptimesection = record02.getReceptimesection();
			if(receptimesection != null){
				String data = addAccessRecord(receptimesection,"018",2,"本次接待时间",record02.getId(),dictRefList);
				record02.setReceptimesection(data);
			}
			//客户评级
			String custscore = record02.getCustscore();
			if(custscore != null){
				String data = addAccessRecord(custscore,"019",2,"客户评级",record02.getId(),dictRefList);
				addAccessRecord(custscore,"019",4,"客户评级",customerId,dictRefList);
				record02.setCustscore(data);
				customer.setCustscore(data);
			}
			//年龄段
			String agegroup = record02.getAgegroup();
			if(agegroup != null){
				String data = addAccessRecord(agegroup,"003",2,"年龄段",record02.getId(),dictRefList);
				addAccessRecord(agegroup,"003",4,"年龄段",customerId,dictRefList);
				record02.setAgegroup(data);
				customer.setAgegroup(data);
			}
			//家庭状况
			String familystatus = record02.getFamilystatus();
			if(familystatus != null){
				String data = addAccessRecord(familystatus,"005",2,"家庭状况",record02.getId(),dictRefList);
				addAccessRecord(familystatus,"005",4,"家庭状况",customerId,dictRefList);
				record02.setFamilystatus(data);
				customer.setFamilystatus(data);
			}
			//出行方式
			String traffictype = record02.getTraffictype();
			if(traffictype != null){
				String data = addAccessRecord(traffictype,"006",2,"出行方式",record02.getId(),dictRefList);
				addAccessRecord(traffictype,"006",4,"出行方式",customerId,dictRefList);
				record02.setTraffictype(data);
				customer.setTraffictype(data);
			}
			//购房资格
			String buyqualify = record02.getBuyqualify();
			if(buyqualify != null){
				String data = addAccessRecord(buyqualify,"004",2,"购房资格",record02.getId(),dictRefList);
				addAccessRecord(buyqualify,"004",4,"购房资格",customerId,dictRefList);
				record02.setBuyqualify(data);
				customer.setBuyqualify(data);
			}
			//从事行业
			String workindustry = record02.getWorkindustry();
			if(workindustry != null){
				String data = addAccessRecord(workindustry,"007",2,"从事行业",record02.getId(),dictRefList);
				addAccessRecord(workindustry,"007",4,"从事行业",customerId,dictRefList);
				record02.setWorkindustry(data);
				customer.setWorkindustry(data);
			}
			//企业性质
			String enterprisetype = record02.getEnterprisetype();
			if(enterprisetype != null){
				String data = addAccessRecord(enterprisetype,"008",2,"企业性质",record02.getId(),dictRefList);
				addAccessRecord(enterprisetype,"008",4,"企业性质",customerId,dictRefList);
				record02.setEnterprisetype(data);
				customer.setEnterprisetype(data);
			}
			//认知本案渠道
			String knowway = record02.getKnowway();
			if(knowway != null){
				String data = addAccessRecord(knowway,"013",2,"认知本案渠道",record02.getId(),dictRefList);
				addAccessRecord(knowway,"013",5,"认知本案渠道",projCustRef.getId(),dictRefList);
				record02.setKnowway(data);
				projCustRef.setKnowway(data);
			}
			//资金筹备期
			String capitalprepsection = record02.getCapitalprepsection();
			if(capitalprepsection != null){
				String data = addAccessRecord(capitalprepsection,"017",2,"资金筹备期",record02.getId(),dictRefList);
				addAccessRecord(capitalprepsection,"017",5,"资金筹备期",projCustRef.getId(),dictRefList);
				record02.setCapitalprepsection(data);
				projCustRef.setCapitalprepsection(data);
			}
			//本案关注点
			String attentionpoint = record02.getAttentionpoint();
			if(attentionpoint != null){
				String data = addAccessRecord(attentionpoint,"014",1,"本案关注点",record02.getId(),dictRefList);
				addAccessRecord(attentionpoint,"014",5,"本案关注点",projCustRef.getId(),dictRefList);
				record02.setAttentionpoint(data);
				projCustRef.setAttentionpoint(data);
			}
			//预估身价
			String estcustworth = record02.getEstcustworth();
			if(estcustworth != null){
				String data = addAccessRecord(estcustworth,"015",2,"预估身价",record02.getId(),dictRefList);
				addAccessRecord(estcustworth,"015",4,"预估身价",customerId,dictRefList);
				record02.setEstcustworth(data);
				customer.setEstcustworth(data);
			}
			//重点投资
			String investType = record02.getInvesttype();
			if(investType != null){
				String data = addAccessRecord(investType,"016",2,"重点投资",record02.getId(),dictRefList);
				addAccessRecord(investType,"016",4,"重点投资",customerId,dictRefList);
				record02.setInvesttype(data);
				customer.setInvesttype(data);
			}
			//关注产品类型
			String realtyproducttype = record02.getRealtyproducttype();
			if(realtyproducttype != null){
				String data = addAccessRecord(realtyproducttype,"009",2,"关注产品类型",record02.getId(),dictRefList);
				addAccessRecord(realtyproducttype,"009",4,"关注产品类型",customerId,dictRefList);
				record02.setRealtyproducttype(data);
				customer.setRealtyproducttype(data);
			}
			//关注面积
			String attentacreage = record02.getAttentacreage();
			if(attentacreage != null){
				String data = addAccessRecord(attentacreage,"010",1,"关注面积",record02.getId(),dictRefList);
				addAccessRecord(attentacreage,"010",4,"关注面积",customerId,dictRefList);
				record02.setAttentacreage(data);
				customer.setAttentacreage(data);
			}
			//接受价格区段
			String pricesection = record02.getPricesection();
			if(pricesection != null){
				String data = addAccessRecord(pricesection,"011",1,"接受价格区段",record02.getId(),dictRefList);
				addAccessRecord(pricesection,"011",4,"接受价格区段",customerId,dictRefList);
				record02.setPricesection(data);
				customer.setPricesection(data);
			}
			//购房目的
			String buypurpose = record02.getBuypurpose();
			if(buypurpose != null){
				String data = addAccessRecord(buypurpose,"012",1,"购房目的",record02.getId(),dictRefList);
				addAccessRecord(buypurpose,"012",4,"购房目的",customerId,dictRefList);
				record02.setBuypurpose(data);
				customer.setBuypurpose(data);
			}
			record02.setCustid(customerId);
			record02.setStatus(1);
			//添加项目客户关系表
			projCustRef.setProjid(record02.getProjid());
			projCustRef.setCustid(customerId);
			isok = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return isok;
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
		//AccessRecord01 retR01=new AccessRecord01();//可以直接插入数据库的01数据
		Customer cust=new Customer(); //从01中汇出的客户信息，可直接参与数据库操作
		ProjCustRef projCustRef=new ProjCustRef(); //从01中汇出的客户项目关系，可直接参与数据库操作
		UserCustRef userCustRef=new UserCustRef(); //
		List<TabDictRef> dictRefList=new ArrayList<TabDictRef>(); //可以直接参与处理字典项与表关系处理的对象列表
		//获取所有对象的属性
		addRecord02(type,record02, cust,projCustRef,userCustRef,dictRefList);
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
			AccessRecord02 record02,Customer customer) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//Boolean isok = addRecord02(record02,customer,record02.getId());
			record02.setStatus(1);
			accessRecord02Service.update(record02);
			map.put("msg", "100");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}	
	
	
	
	
	//成交添加修改通用属性
	public Boolean addRecord03(int type,AccessRecord03 record03,Customer customer,ProjCustRef projCustRef,UserCustRef userCustRef,List<TabDictRef> dictRefList){
		Boolean isok = false;
		try {
			//客户表ID
			String customerId = "";
			//客户项目关系表
			String proJcustId = "";
			//项目客户关系表
			String userCustRefId = "";
			if(type == 0){
				//客户ID
				customerId = key.getUUIDKey();
				customer.setId(customerId);
				//客户项目关系表ID
				proJcustId = key.getUUIDKey();
				projCustRef.setId(proJcustId);
				//项目客户关系表ID
				userCustRefId = key.getUUIDKey();
				userCustRef.setId(userCustRefId);
				userCustRef.setProjid(record03.getProjid());
		        userCustRef.setUserid(record03.getAuthorid());
		        userCustRef.setCustid(customer.getId());
			}else{
				//客户ID
				customerId = record03.getCustid();
				customer.setId(customerId);
				//客户项目关系表
				Map<String,Object>result=new HashMap<String,Object>();
				result.put("cusId", customerId);
				result.put("projId", record03.getProjid());
				projCustRef = projCustRefService.selectByCusIdAndProjId(result);
				proJcustId = projCustRef.getId();
			}
			//客户性别
			String custsex = record03.getCustsex();
			//客户表
			customer.setAddressmail(record03.getAddressmail());
			if(custsex != null){
				String data = addAccessRecord(custsex,"002",3,"客户性别",record03.getId(),dictRefList);
				addAccessRecord(custsex,"002",4,"客户性别",customerId,dictRefList);
				record03.setCustsex(data);
				customer.setCustsex(data);
			}
			//户籍类型
			String houseregitype = record03.getHouseregitype();
			if(houseregitype != null){
				String data = addAccessRecord(houseregitype,"029",3,"户籍类型",record03.getId(),dictRefList);
				addAccessRecord(houseregitype,"029",4,"户籍类型",customerId,dictRefList);
				record03.setHouseregitype(data);
				customer.setHouseregitype(data);
			}
			//付款方式
			String paymenttype = record03.getPaymenttype();
			if(paymenttype != null){
				String data = addAccessRecord(paymenttype,"030",3,"付款方式",record03.getId(),dictRefList);
				record03.setPaymenttype(data);
			}
			//购买产品类型
			String realtyproducttype = record03.getRealtyproducttype();
			if(realtyproducttype != null){
				String data = addAccessRecord(realtyproducttype,"009",3,"购买产品类型",record03.getId(),dictRefList);
				record03.setRealtyproducttype(data);
			}
			//实际居住环境
			String livingstatus = record03.getLivingstatus();
			if(livingstatus != null){
				String data = addAccessRecord(livingstatus,"030",3,"实际居住环境",record03.getId(),dictRefList);
				addAccessRecord(livingstatus,"030",4,"实际居住环境",customer.getId(),dictRefList);
				record03.setLivingstatus(data);
				customer.setFamilystatus(data);
			}
			//实际使用人
			String realusemen = record03.getRealusemen();
			if(realusemen != null){
				String data = addAccessRecord(realusemen,"031",3,"实际使用人",record03.getId(),dictRefList);
				record03.setRealusemen(data);
			}
			//实际出资人
			String realpaymen = record03.getRealpaymen();
			if(realpaymen != null){
				String data = addAccessRecord(realpaymen,"031",3,"实际出资人",record03.getId(),dictRefList);
				record03.setRealpaymen(data);
			}
			record03.setCustid(customer.getId());
			record03.setStatus(1);
			isok = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return isok;
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
		Customer cust=new Customer(); //从01中汇出的客户信息，可直接参与数据库操作
		ProjCustRef projCustRef=new ProjCustRef(); //从01中汇出的客户项目关系，可直接参与数据库操作
		UserCustRef userCustRef=new UserCustRef(); //
		List<TabDictRef> dictRefList=new ArrayList<TabDictRef>(); //可以直接参与处理字典项与表关系处理的对象列表
		//获取所有对象的属性
		addRecord03(type,record03, cust,projCustRef,userCustRef,dictRefList);
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
			AccessRecord03 record03,Customer customer) {
		responseInfo(response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//Boolean isok = addRecord03(record03,customer,record03.getId(),request);
			accessRecord03Service.update(record03);
			map.put("msg", "100");
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
	@RequestMapping(value = "/getCusList")
	@ResponseBody
	public String getCusList(HttpServletRequest req){
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
	@RequestMapping(value = "/getCusMsg")
	@ResponseBody
	public String getCusMsg(HttpServletRequest req){
		Map<String, Object> m=RequestUtils.getDataFromRequest(req);
		responseInfo(response);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String cusId = m.get("cusId")==null?null:m.get("cusId").toString();
			//获取客户详细信息
			Customer customer = customerService.findById(cusId);
			map.put("customer", customer);
			map.put("msg", "100");
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	//获取客户项目关系表信息
	@RequestMapping(value = "/getProjCusList")
	@ResponseBody
	public String getProjCusList(HttpServletRequest req){
		Map<String, Object> m=RequestUtils.getDataFromRequest(req);
		responseInfo(response);
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String projId = m.get("projId")==null?null:m.get("projId").toString();
			String cusId = m.get("cusId")==null?null:m.get("cusId").toString();
			//获取用户列表
			result.put("projId", projId);
			result.put("cusId", cusId);
			List<Map<String,Object>> projCustRefList = projCustRefService.selectByProjIdAndCusId(result);
			map.put("projCustRefList", projCustRefList);
			map.put("msg", "100");
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
			    if (type==0) customerService.insert(cust);
			    else customerService.update(cust);
			    //处理用户客户
			    if (type==0) userCustRefService.insert(userCustRef);
			    else userCustRefService.update(userCustRef);
			    //处理用户项目
			    if (type==0) projCustRefService.insert(projCustRef);
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
