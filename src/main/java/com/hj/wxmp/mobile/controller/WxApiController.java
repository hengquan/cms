package com.hj.wxmp.mobile.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hj.wxmp.mobile.entity.Customer;
import com.hj.wxmp.mobile.entity.SysRole;
import com.hj.wxmp.mobile.entity.TabDictRef;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.entity.UserRole;
import com.hj.wxmp.mobile.services.AccessRecord01Service;
import com.hj.wxmp.mobile.services.AccessRecord02Service;
import com.hj.wxmp.mobile.services.AccessRecord03Service;
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

@RequestMapping("/wx/api")
@Controller
public class WxApiController extends ControllerBaseWx {

	private final static Logger logger = LoggerFactory
			.getLogger(WxApiController.class);

	private HashSessions hashSession = HashSessions.getInstance();
	private Weixin weixin = Weixin.getInstance();
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
	                	if(requestURL.equals(url)){
	                		siteName = url;
	                	}else{
	                		siteName += "/wxmp.ql/wxfront/err.html?0002";
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
                	if(requestURL.equals(url)){
                		siteName = url;
                	}else{
                		siteName += "/wxmp.ql/wxfront/err.html?0002";
                	}
                    res.sendRedirect(siteName);
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
	@RequestMapping(value = "/getUserClienteleMsg")
	@ResponseBody
	public String getUserClienteleMsg(HttpServletRequest requet,HttpServletResponse response,
			@RequestParam(value = "name", defaultValue = "")String name,
			@RequestParam(value = "phone", defaultValue = "")String phone,
			@RequestParam(value = "project", defaultValue = "")String project) {
		responseInfo(response);
		visiitURL(requet,response);
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
			if(!"".equals(name) && !"".equals(project) && !"".equals(phone)){
				String openid = HashSessions.getInstance().getOpenId(request);
				UserInfo userInfo = userInfoService.findByOpenid(openid);
				//用户ID
				String userId = userInfo.getId();
				msg.put("name", name);
				msg.put("phone", phone);
				msg.put("project", project);
				//查询客户信息
				List<Map<String, Object>> accessRecord01 = accessRecord01Service.selectUserMsy(msg);
				if(accessRecord01.size()>0){
					String authorName = userInfoService.findById(userId).getRealname();
					//权限人ID
					String authorId = accessRecord01.get(0).get("authorId").toString();
					//客户ID
					String custId = accessRecord01.get(0).get("custId").toString();
					if(userId.equals(authorId)){
						map.put("msg", "100");
					}else{
						map.put("msg", "101");
					}
					map.put("authorName", authorName);
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
	public String addAccessRecord(String dataMessage,String dmid,Integer type,String refname,String customerId){
		String resultData = "";
		try {
			String[] dataList = dataMessage.split(";");
			for(int i=0;i<dataList.length;i++){
				String[] split = dataList[i].split("-");
				//添加字典对应关系表
				TabDictRef tabDictRef = new TabDictRef();
				//添加关系
				tabDictRef.setId(key.getUUIDKey());
				tabDictRef.setDdid(split[0]);
				tabDictRef.setDmid(dmid);
				if(type == 1){
					tabDictRef.setTabname("ql_AccessRecord01");
				}else if(type == 2){
					tabDictRef.setTabname("ql_AccessRecord02");
				}else if(type == 3){
					tabDictRef.setTabname("ql_AccessRecord03");
				}
				tabDictRef.setTabid(customerId);
				tabDictRef.setRefname(refname);
				tabDictRefService.insert(tabDictRef);
				if(i==0){
					resultData += split[1];
				}else{
					resultData += ","+split[1];
				}
			}
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
		visiitURL(requet,response);
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
	@RequestMapping(value = "/addHisFirstRecord")
	@ResponseBody
	public String addHisFirstRecord(HttpServletRequest requet,HttpServletResponse response,AccessRecord01 record01) {
		responseInfo(response);
		visiitURL(requet,response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String openid = HashSessions.getInstance().getOpenId(request);
			//openid = "oaBNt0xKNjXvStRlbKqMnk7QQ2Pw";
			//首访记录表ID
			String record01Id = key.getUUIDKey();
			//客户表
			Customer customer = new Customer();
			String customerId = key.getUUIDKey();
			customer.setId(customerId);
			customer.setCustname(record01.getCustname());
			customer.setPhonenum(record01.getCustphonenum());
			//客户性别
			String custsex = record01.getCustsex();
			if(custsex!=null){
				String data = addAccessRecord(custsex,"002",1,"客户性别",record01Id);
				customer.setCustsex(data);
				record01.setCustsex(data);
			}
			//添加客户
			customerService.insert(customer);
			UserInfo userInfo = userInfoService.findByOpenid(openid);
			//获取用户ID
			String userId = userInfo.getId();
			//补全首访信息-并更新
			record01.setId(record01Id);
			//年龄段
			String agegroup = record01.getAgegroup();
			if(agegroup != null){
				String data = addAccessRecord(agegroup,"003",1,"年龄段",record01Id);
				record01.setAgegroup(data);
			}
			//购房资格
			String buyqualify = record01.getBuyqualify();
			if(buyqualify != null){
				String data = addAccessRecord(buyqualify,"004",1,"购房资格",record01Id);
				record01.setBuyqualify(data);
			}
			//本地居住地
			String localresidence = record01.getLocalresidence();
			if(localresidence != null){
				String data = addAccessRecord(localresidence,"001",1,"本地居住地",record01Id);
				record01.setLocalresidence(data);
			}
			//本地工作地
			String localworkarea = record01.getLocalworkarea();
			if(localworkarea != null){
				String data = addAccessRecord(localworkarea,"001",1,"本地工作地",record01Id);
				record01.setLocalworkarea(data);
			}
			//外阜居住地
			String outresidence = record01.getOutresidence();
			if(outresidence != null){
				String data = addAccessRecord(outresidence,"001",1,"外阜居住地",record01Id);
				record01.setOutresidence(data);
			}
			//外阜工作地
			String outworkarea = record01.getOutworkarea();
			if(outworkarea != null){
				String data = addAccessRecord(outworkarea,"001",1,"外阜工作地",record01Id);
				record01.setOutworkarea(data);
			}
			//家族状况
			String familystatus = record01.getFamilystatus();
			if(familystatus != null){
				String data = addAccessRecord(familystatus,"005",1,"家族状况",record01Id);
				record01.setFamilystatus(data);
			}
			//出行方式
			String traffictype = record01.getTraffictype();
			if(traffictype != null){
				String data = addAccessRecord(traffictype,"006",1,"出行方式",record01Id);
				record01.setTraffictype(data);
			}
			//从事行业
			String workindustry = record01.getWorkindustry();
			if(workindustry != null){
				String data = addAccessRecord(workindustry,"007",1,"从事行业",record01Id);
				record01.setWorkindustry(data);
			}
			//企业性质
			String enterprisetype = record01.getEnterprisetype();
			if(enterprisetype != null){
				String data = addAccessRecord(enterprisetype,"008",1,"企业性质",record01Id);
				record01.setEnterprisetype(data);
			}
			//关注产品类型
			String realtyproducttype = record01.getRealtyproducttype();
			if(realtyproducttype != null){
				String data = addAccessRecord(realtyproducttype,"009",1,"关注产品类型",record01Id);
				record01.setRealtyproducttype(data);
			}
			//关注面积
			String attentacreage = record01.getAttentacreage();
			if(attentacreage != null){
				String data = addAccessRecord(attentacreage,"010",1,"关注面积",record01Id);
				record01.setAttentacreage(data);
			}
			//接受价格区段
			String pricesection = record01.getPricesection();
			if(pricesection != null){
				String data = addAccessRecord(pricesection,"011",1,"接受价格区段",record01Id);
				record01.setPricesection(data);
			}
			//购房目的
			String buypurpose = record01.getBuypurpose();
			if(buypurpose != null){
				String data = addAccessRecord(buypurpose,"012",1,"购房目的",record01Id);
				record01.setBuypurpose(data);
			}
			//认知本案渠道
			String knowway = record01.getKnowway();
			if(knowway != null){
				String data = addAccessRecord(knowway,"013",1,"认知本案渠道",record01Id);
				record01.setKnowway(data);
			}
			//本案关注点
			String attentionpoint = record01.getAttentionpoint();
			if(attentionpoint != null){
				String data = addAccessRecord(attentionpoint,"014",1,"本案关注点",record01Id);
				record01.setAttentionpoint(data);
			}
			//预估身价
			String estcustworth = record01.getEstcustworth();
			if(estcustworth != null){
				String data = addAccessRecord(estcustworth,"015",1,"预估身价",record01Id);
				record01.setEstcustworth(data);
			}
			//重点投资
			String investtype = record01.getInvesttype();
			if(investtype != null){
				String data = addAccessRecord(investtype,"016",1,"重点投资",record01Id);
				record01.setInvesttype(data);
			}
			//资金筹备期
			String captilprepsection = record01.getCaptilprepsection();
			if(captilprepsection != null){
				String data = addAccessRecord(captilprepsection,"017",1,"资金筹备期",record01Id);
				record01.setCaptilprepsection(data);
			}
			//本次接待时间
			String receptimesection = record01.getReceptimesection();
			if(receptimesection != null){
				String data = addAccessRecord(receptimesection,"018",1,"本次接待时间",record01Id);
				record01.setReceptimesection(data);
			}
			//客户评级
			String custscore = record01.getCustscore();
			if(custscore != null){
				String data = addAccessRecord(custscore,"019",1,"客户评级",record01Id);
				record01.setCustscore(data);
			}
			record01.setCustid(customerId);
			record01.setAuthorid(userId);
			record01.setCreatorid(userId);
			record01.setStatus(1);
			//添加首访记录
			accessRecord01Service.insert(record01);
			map.put("msg", "100");
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
			AccessRecord02 record02,Customer customer) {
		responseInfo(response);
		visiitURL(requet,response);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String openid = HashSessions.getInstance().getOpenId(request);
			//openid = "oaBNt0xKNjXvStRlbKqMnk7QQ2Pw";
			//复访表ID
			String record02Id = key.getUUIDKey();
			//客户表
			customer.setCustname(record02.getCustname());
			customer.setPhonenum(record02.getCustphonenum());
			//更新客户信息
			customerService.update(customer);
			UserInfo userInfo = userInfoService.findByOpenid(openid);
			//获取用户ID
			String userId = userInfo.getId();
			//补全复访信息-并更新
			record02.setId(record02Id);
			//小孩年龄段
			String childagegroup = record02.getChildagegroup();
			if(childagegroup != null){
				String data = addAccessRecord(childagegroup,"020",2,"小孩年龄段",record02Id);
				record02.setChildagegroup(data);
			}
			//孩子学校类型
			String schooltype = record02.getSchooltype();
			if(schooltype != null){
				String data = addAccessRecord(schooltype,"021",2,"孩子学校类型",record02Id);
				record02.setSchooltype(data);
			}
			//生活半径
			String livingradius = record02.getLivingradius();
			if(livingradius != null){
				String data = addAccessRecord(livingradius,"022",2,"生活半径",record02Id);
				record02.setLivingradius(data);
			}
			//居住面积
			String liveacreage = record02.getLiveacreage();
			if(liveacreage != null){
				String data = addAccessRecord(liveacreage,"023",2,"居住面积",record02Id);
				record02.setLiveacreage(data);
			}
			//贷款记录
			String loanstatus = record02.getLoanstatus();
			if(loanstatus != null){
				String data = addAccessRecord(loanstatus,"024",2,"贷款记录",record02Id);
				record02.setLoanstatus(data);
			}
			//汽车总价款
			String cartotalpricce = record02.getCartotalpricce();
			if(cartotalpricce != null){
				String data = addAccessRecord(cartotalpricce,"025",2,"汽车总价款",record02Id);
				record02.setCartotalpricce(data);
			}
			//业余爱好
			String avocations = record02.getAvocations();
			if(avocations != null){
				String data = addAccessRecord(avocations,"026",2,"业余爱好",record02Id);
				record02.setAvocations(data);
			}
			//本案抗拒点
			String resistpoint = record02.getResistpoint();
			if(resistpoint != null){
				String data = addAccessRecord(resistpoint,"014",2,"本案抗拒点",record02Id);
				record02.setResistpoint(data);
			}
			//喜欢活动
			String loveactivation = record02.getLoveactivation();
			if(loveactivation != null){
				String data = addAccessRecord(loveactivation,"027",2,"喜欢活动",record02Id);
				record02.setLoveactivation(data);
			}
			//来访人关系
			String visitorrefs = record02.getVisitorrefs();
			if(visitorrefs != null){
				String data = addAccessRecord(visitorrefs,"028",2,"来访人关系",record02Id);
				record02.setVisitorrefs(data);
			}
			//本次接待时间
			String receptimesection = record02.getReceptimesection();
			if(receptimesection != null){
				String data = addAccessRecord(receptimesection,"018",2,"本次接待时间",record02Id);
				record02.setReceptimesection(data);
			}
			//客户评级
			String custscore = record02.getCustscore();
			if(custscore != null){
				String data = addAccessRecord(custscore,"019",2,"客户评级",record02Id);
				record02.setCustscore(data);
			}
			record02.setCustid(customer.getId());
			record02.setAuthorid(userId);
			record02.setCreatorid(userId);
			record02.setStatus(1);
			//添加首访记录
			accessRecord02Service.insert(record02);
			map.put("msg", "100");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}	
	
	
	
	
	//添加成交记录
	@RequestMapping(value = "/addKnockdown")
	@ResponseBody
	public String addKnockdown(HttpServletRequest requet,HttpServletResponse response,
			AccessRecord03 record03,Customer customer) {
		responseInfo(response);
		visiitURL(requet,response);
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String openid = HashSessions.getInstance().getOpenId(request);
			//openid = "oaBNt0xKNjXvStRlbKqMnk7QQ2Pw";
			//成交访问记录ID
			String record03Id = key.getUUIDKey();
			//客户信息ID
			String customerId = customer.getId();
			//客户性别
			String custsex = record03.getCustsex();
			if(customerId!=null){
				//客户表
				customer.setCustname(record03.getCustname());
				customer.setPhonenum(record03.getCustphonenum());
				if(custsex != null){
					String data = addAccessRecord(custsex,"002",3,"客户性别",record03Id);
					customer.setCustsex(data);
				}
				//更新客户信息
				customerService.update(customer);
			}else{
				if(custsex != null){
					String data = addAccessRecord(custsex,"002",3,"客户性别",record03Id);
					record03.setCustsex(data);
				}
			}
			UserInfo userInfo = userInfoService.findByOpenid(openid);
			//获取用户ID
			String userId = userInfo.getId();
			//补全首访信息-并更新
			record03.setId(record03Id);
			String purchasedate1 = request.getParameter("purchasedate1");
			//String purchasedate1 = "2017-02-01 12:12:12";
			String signdate1 = request.getParameter("signdate1");
			//String signdate1 = "2017-02-01 12:12:12";
			record03.setPurchasedate(format.parse(purchasedate1));
			record03.setSigndate(format.parse(signdate1));
			//户籍类型
			String houseregitype = record03.getHouseregitype();
			if(houseregitype != null){
				String data = addAccessRecord(houseregitype,"029",3,"户籍类型",record03Id);
				record03.setHouseregitype(data);
			}
			//付款方式
			String paymenttype = record03.getPaymenttype();
			if(paymenttype != null){
				String data = addAccessRecord(paymenttype,"030",3,"付款方式",record03Id);
				record03.setPaymenttype(data);
			}
			//购买产品类型
			String realtyproducttype = record03.getRealtyproducttype();
			if(realtyproducttype != null){
				String data = addAccessRecord(realtyproducttype,"009",3,"购买产品类型",record03Id);
				record03.setRealtyproducttype(data);
			}
			//实际居住环境
			String livingstatus = record03.getLivingstatus();
			if(livingstatus != null){
				String data = addAccessRecord(livingstatus,"030",3,"实际居住环境",record03Id);
				record03.setLivingstatus(data);
			}
			//实际使用人
			String realusemen = record03.getRealusemen();
			if(realusemen != null){
				String data = addAccessRecord(realusemen,"031",3,"实际使用人",record03Id);
				record03.setRealusemen(data);
			}
			//实际出资人
			String realpaymen = record03.getRealpaymen();
			if(realpaymen != null){
				String data = addAccessRecord(realpaymen,"031",3,"实际出资人",record03Id);
				record03.setRealpaymen(data);
			}
			record03.setCustid(customer.getId());
			record03.setAuthorid(userId);
			record03.setCreatorid(userId);
			record03.setStatus(1);
			//添加成交记录
			accessRecord03Service.insert(record03);
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
		visiitURL(requet,response);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> datamsg = new HashMap<String, Object>();
		try {
			String openid = HashSessions.getInstance().getOpenId(request);
			//openid = "oaBNt0xKNjXvStRlbKqMnk7QQ2Pw";
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
			if(isvalidate == 1){
				String message = "";
				List<Map<String, Object>> projData = projUserRoleService.selectByUserId(userId);
				for(Map<String, Object> proj : projData){
					String projName = proj.get("projName").toString();
					message += projName+",";
				}
				map.put("checkProj", message);
			}else{
				map.put("checkProj", "");
			}
			//添加信息
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
		System.out.println("++++++++++++"+JsonUtils.map2json(datamsg));
		return JsonUtils.map2json(datamsg);
    }
	
	
	
	
	
//	//用户中心-我的二维码
//	@RequestMapping("/myQRcode")
//	@ResponseBody
//	public String myQRcode(Model model,HttpServletResponse response){
//		responseInfo(response);
//		Map<String,Object> map = new HashMap<String,Object>();
//		//用户信息
//		String openid = HashSessions.getInstance().getOpenId(request);
//		openid = "oaBNt0xKNjXvStRlbKqMnk7QQ2Pw";
//		UserInfo user = userInfoService.selectByOpenId(openid);
//		String qrCodeAddress = user.getDescn();
//		try {
//			if(qrCodeAddress==null || qrCodeAddress.equals("")){
//				String fileName = weixin.getQrcode(openid);
//				user.setDescn(fileName);
//				userInfoService.update(user);
//			}
//			String urlpath=Configurations.getConfig("ACCESSURL");
//			map.put("img", urlpath + user.getDescn());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return JsonUtils.map2json(map);
//	}
	

}
