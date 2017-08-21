package com.hj.wxmp.mobile.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.utils.JsonUtils;
import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.entity.AccessRecord01;
import com.hj.wxmp.mobile.entity.AccessRecord02;
import com.hj.wxmp.mobile.entity.AccessRecord03;
import com.hj.wxmp.mobile.entity.AuditRecord;
import com.hj.wxmp.mobile.entity.SysItemRole;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.entity.UserRole;
import com.hj.wxmp.mobile.services.AccessRecord01Service;
import com.hj.wxmp.mobile.services.AccessRecord02Service;
import com.hj.wxmp.mobile.services.AccessRecord03Service;
import com.hj.wxmp.mobile.services.AuditRecordService;
import com.hj.wxmp.mobile.services.CustomerService;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ProjUserRoleService;
import com.hj.wxmp.mobile.services.ProjectService;
import com.hj.wxmp.mobile.services.UserCustRefService;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.hj.wxmp.mobile.services.UserRoleService;

//客户访问记录审核
@Controller
public class AccessRecordController extends ControllerBase {

	private final static Logger logger = LoggerFactory.getLogger(AccessRecordController.class);
	private HashSessions hashSession = HashSessions.getInstance();

	@Resource
	private IKeyGen keyGen;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	UserRoleService sysUserRoleService;
	@Autowired
	ProjUserRoleService projUserRoleService;
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
	AuditRecordService auditRecordService;

	//首访审核列表
	@RequestMapping(value = "/accessRecord/hisFirstRecord")
	public String userList(@RequestParam(value="nowPage",defaultValue="1") int nowPage,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize,ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "accessRecord01/list";
		//纪录总数
		Integer listMessgeCount = 0;
		String userName = getTrimParameter("userName");
		String state = getTrimParameter("state");
		map.put("state", state);
		Integer start = ((nowPage - 1) * pageSize);
		map.put("page", start);
		map.put("pageSize", pageSize);
		try {
			if(userName == null){
				map.put("userName", "");
			}else{
				map.put("userName", userName);
			}
			// 获取所有未审核首访记录
			List<Map<String,Object>> hisFirstRecordMsg = accessRecord01Service.selectMessage(map);
			//所有信息数量
			listMessgeCount = accessRecord01Service.selectMessageCount(map);
		 	Integer totalCount = listMessgeCount%pageSize;
			Integer totalPageNum = 0;
			if(totalCount==0){
				totalPageNum = listMessgeCount/pageSize;
			}else{
				totalPageNum = (listMessgeCount/pageSize)+1;
			}
			model.put("nowPage", nowPage);
			model.put("totalPageNum", totalPageNum);
			model.addAttribute("hisFirstRecordMsg", hisFirstRecordMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		model.addAttribute("itemId", itemId);
		model.addAttribute("id", id);
		return pageUrl;
	}
	
	//修改首访信息
	@RequestMapping(value = "/updateFirstRecord")
	public String updateFirstRecord(ModelMap model,HttpServletRequest req){
		String pageUrl = "accessRecord01/editRecord01";
		String userId = "";
		try {
			Object obj = req.getSession().getAttribute("adminSession");
			if (null != obj)  {
				UserInfo userInfo = (UserInfo) obj;
				userId = userInfo.getId();
				System.out.println(userId);
			}
			String id = getTrimParameter("id");
			AccessRecord01 accessRecord01 = accessRecord01Service.findById(id);
			model.addAttribute("accessRecord01", accessRecord01);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//菜单
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		model.addAttribute("itemId", itemId);
		model.addAttribute("lst", lst);
		model.addAttribute("id", id);
		model.addAttribute("userId", userId);
		return pageUrl;
		
	}
	
	//首访审核
	@ResponseBody
	@RequestMapping(value = "/accessRecord/firstRecord")
	public String firstRecord(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String state = getTrimParameter("state");
			String id = getTrimParameter("checkedId");
			String checkContent = getTrimParameter("checkContent");
			//获取首访记录
			AccessRecord01 record01 = accessRecord01Service.findById(id);
			record01.setStatus(Integer.parseInt(state));
			accessRecord01Service.update(record01);
			//添加审核信息
			AuditRecord auditRecord = new AuditRecord();
			auditRecord.setId(keyGen.getUUIDKey());
			auditRecord.setRecordtype(1);
			auditRecord.setarid(id);
			if(Integer.parseInt(state)==2){
				auditRecord.setAudittype(1);
				auditRecord.setReason("");
				auditRecordService.insert(auditRecord);
			}else if(Integer.parseInt(state)==3){
				auditRecord.setAudittype(2);
				auditRecord.setReason(checkContent);
				auditRecordService.insert(auditRecord);
			}
			map.put("msg", "100");
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	
	//改变详情和审核共用信息
	public AccessRecord01 setAccessRecord01Data(AccessRecord01 accessRecord01){
		int index=0;
		//出行方式
		String traffictype = accessRecord01.getTraffictype();
		if(traffictype!=null){
			String traffictypeDesc = accessRecord01.getTraffictypedesc();
			if(traffictypeDesc!=null){
				index=traffictype.indexOf("其他");
				if(index>=0){
					traffictype=traffictype.replace("其他", "其他("+ traffictypeDesc +")");
					accessRecord01.setTraffictype(traffictype);
				}
			}
		}
		 //从事行业
		 String workindustry = accessRecord01.getWorkindustry();
		 if(workindustry!=null){
			 String workindustrydesc = accessRecord01.getWorkindustrydesc();
			 if(workindustrydesc!=null){
				 index=workindustry.indexOf("其他");
				 if(index>=0){
					 workindustry=workindustry.replace("其他", "其他("+ workindustrydesc +")");
					 accessRecord01.setWorkindustry(workindustry);
				 }
			 }
		 }
		 //企业性质
		 String enterprisetype = accessRecord01.getEnterprisetype();
		 if(enterprisetype!=null){
			 String enterprisetypedesc = accessRecord01.getEnterprisetypedesc();
			 if(enterprisetypedesc!=null){
				 index=enterprisetype.indexOf("其他");
				 if(index>=0){
					 enterprisetype=enterprisetype.replace("其他", "其他("+ enterprisetypedesc +")");
					 accessRecord01.setEnterprisetype(enterprisetype);
				 }
			 }
		 }
		 //产品类型
		 String realtyproducttype = accessRecord01.getRealtyproducttype();
		 if(realtyproducttype!=null){
			 String realtyproducttypedesc = accessRecord01.getRealtyproducttypedesc();
			 if(realtyproducttypedesc!=null){
				 index=realtyproducttype.indexOf("其他");
				 if(index>=0){
					 realtyproducttype=realtyproducttype.replace("其他", "其他("+ realtyproducttypedesc +")");
					 accessRecord01.setRealtyproducttype(realtyproducttype);
				 }
			 }
		 }
		 //购房目的
		 String buypurpose = accessRecord01.getBuypurpose();
		 if(buypurpose!=null){
			 String buypurposedesc = accessRecord01.getBuypurposedesc();
			 if(buypurposedesc!=null){
				 index=buypurpose.indexOf("其他");
				 if(index>=0){
					 buypurpose=buypurpose.replace("其他", "其他("+ buypurposedesc +")");
					 accessRecord01.setBuypurpose(buypurpose);
				 }
			 }
		 }
		 //认知渠道
		 String knowway = accessRecord01.getKnowway();
		 if(knowway!=null){
			 String knowwaydesc = accessRecord01.getKnowwaydesc();
			 if(knowwaydesc!=null){
				 index=knowway.indexOf("其他");
				 if(index>=0){
					 knowway=knowway.replace("其他", "其他("+ knowwaydesc +")");
					 accessRecord01.setKnowway(knowway);
				 }
			 }
		 }
		 //本案关注点
		 String attentionpoint = accessRecord01.getAttentionpoint();
		 if(attentionpoint!=null){
			 String attentionpointdesc = accessRecord01.getAttentionpointdesc();
			 if(attentionpointdesc!=null){
				 index=attentionpoint.indexOf("其他");
				 if(index>=0){
					 attentionpoint=attentionpoint.replace("其他", "其他("+ attentionpointdesc +")");
					 accessRecord01.setAttentionpoint(attentionpoint);
				 }
			 }
		 }
		 //重点投资
		 String investtype = accessRecord01.getInvesttype();
		 if(investtype!=null){
			 String investtypedesc = accessRecord01.getInvesttypedesc();
			 if(investtypedesc!=null){
				 index=investtype.indexOf("其他");
				 if(index>=0){
					 investtype=investtype.replace("其他", "其他("+ investtypedesc +")");
					 accessRecord01.setInvesttype(investtype);
				 }
			 }
		 }
		 return accessRecord01;
	}
	
	
	
	//首访页面详情
	@RequestMapping(value = "/accessRecord/firstRecordDetails")
	public String firstRecordDetails(ModelMap model){
		String pageUrl = "accessRecord01/seeRecord01";
		try {
			String id = getTrimParameter("id");
			AccessRecord01 accessRecord01 = accessRecord01Service.findById(id);
			setAccessRecord01Data(accessRecord01);
			model.addAttribute("accessRecord01", accessRecord01);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//菜单
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		model.addAttribute("itemId", itemId);
		model.addAttribute("lst", lst);
		model.addAttribute("id", id);
		return pageUrl;
	}
	
	
	//审核页面详情
	@RequestMapping(value = "/accessRecord/checkDetails")
	public String checkDetails(ModelMap model){
		String pageUrl = "accessRecord01/checkRecord01";
		try {
			String id = getTrimParameter("id");
			AccessRecord01 accessRecord01 = accessRecord01Service.findById(id);
			setAccessRecord01Data(accessRecord01);
			model.addAttribute("accessRecord01", accessRecord01);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//菜单
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		model.addAttribute("itemId", itemId);
		model.addAttribute("lst", lst);
		model.addAttribute("id", id);
		return pageUrl;
	}
	
	
	//删除首访审核
	@ResponseBody
	@RequestMapping(value = "/accessRecord/firstRecordDel")
	public String firstRecordDel(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String byid = getTrimParameter("byid");
			String boxeditId = getTrimParameter("boxeditId");
			//删除
			accessRecord01Service.dels(boxeditId);
			map.put("msg", "100");
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	
	
	
	
	
	//复访审核列表
	@RequestMapping(value = "/accessRecord/recheckRecord")
	public String recheckRecord(ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "accessRecord02/list";
		try {
			String userName = getTrimParameter("userName");
			if(userName == null){
				map.put("userName", "");
			}else{
				map.put("userName", userName);
			}
			String state = getTrimParameter("state");
			map.put("state", state);
			// 获取所有未审核首访记录
			List<Map<String,Object>> hisFirstRecordMsg = accessRecord02Service.selectMessage(map);
			model.addAttribute("hisFirstRecordMsg", hisFirstRecordMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		model.addAttribute("itemId", itemId);
		model.addAttribute("id", id);
		return pageUrl;
	}
	
	
	
	//复访审核
	@ResponseBody
	@RequestMapping(value = "/accessRecord/recheckRecordCheck")
	public String recheckRecordCheck(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String state = getTrimParameter("state");
			String id = getTrimParameter("checkedId");
			String checkContent = getTrimParameter("checkContent");
			//获取首访记录
			AccessRecord02 record02 = accessRecord02Service.findById(id);
			record02.setStatus(Integer.parseInt(state));
			accessRecord02Service.update(record02);
			//添加审核信息
			AuditRecord auditRecord = new AuditRecord();
			auditRecord.setId(keyGen.getUUIDKey());
			auditRecord.setRecordtype(2);
			auditRecord.setarid(id);
			if(Integer.parseInt(state)==2){
				auditRecord.setAudittype(1);
				auditRecord.setReason("");
				auditRecordService.insert(auditRecord);
			}else if(Integer.parseInt(state)==3){
				auditRecord.setAudittype(2);
				auditRecord.setReason(checkContent);
				auditRecordService.insert(auditRecord);
			}
			map.put("msg", "100");
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	//复访页面详情
	@RequestMapping(value = "/accessRecord/recheckRecordDetails")
	public String recheckRecordDetails(ModelMap model){
		String pageUrl = "accessRecord02/msg";
		try {
			String id = getTrimParameter("id");
			AccessRecord02 accessRecord02 = accessRecord02Service.findById(id);
			model.addAttribute("accessRecord02", accessRecord02);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//菜单
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		model.addAttribute("itemId", itemId);
		model.addAttribute("id", id);
		return pageUrl;
	}
	
	
	
	
	//删除复访审核
	@ResponseBody
	@RequestMapping(value = "/accessRecord/recheckRecordDel")
	public String recheckRecordDel(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String byid = getTrimParameter("byid");
			String boxeditId = getTrimParameter("boxeditId");
			//删除
			accessRecord02Service.dels(boxeditId);
			map.put("msg", "100");
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	
	
	//成交审核列表
	@RequestMapping(value = "/accessRecord/knockdownRecord")
	public String knockdownRecord(ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "accessRecord03/list";
		try {
			String userName = getTrimParameter("userName");
			if(userName == null){
				map.put("userName", "");
			}else{
				map.put("userName", userName);
			}
			String state = getTrimParameter("state");
			map.put("state", state);
			// 获取所有未审核首访记录
			List<Map<String,Object>> hisFirstRecordMsg = accessRecord03Service.selectMessage(map);
			model.addAttribute("hisFirstRecordMsg", hisFirstRecordMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		model.addAttribute("itemId", itemId);
		model.addAttribute("id", id);
		return pageUrl;
	}
	
	
	//成交审核
	@ResponseBody
	@RequestMapping(value = "/accessRecord/recheckCheck")
	public String recheckCheck(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String state = getTrimParameter("state");
			String id = getTrimParameter("checkedId");
			String checkContent = getTrimParameter("checkContent");
			//获取首访记录
			AccessRecord03 record03 = accessRecord03Service.findById(id);
			record03.setStatus(Integer.parseInt(state));
			accessRecord03Service.update(record03);
			//添加审核信息
			AuditRecord auditRecord = new AuditRecord();
			auditRecord.setId(keyGen.getUUIDKey());
			auditRecord.setRecordtype(3);
			auditRecord.setarid(id);
			if(Integer.parseInt(state)==2){
				auditRecord.setAudittype(1);
				auditRecord.setReason("");
				auditRecordService.insert(auditRecord);
			}else if(Integer.parseInt(state)==3){
				auditRecord.setAudittype(2);
				auditRecord.setReason(checkContent);
				auditRecordService.insert(auditRecord);
			}
			map.put("msg", "100");
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	
	//成交页面详情
	@RequestMapping(value = "/accessRecord/knockdownRecordDetails")
	public String knockdownRecordDetails(ModelMap model){
		String pageUrl = "accessRecord03/msg";
		try {
			String id = getTrimParameter("id");
			AccessRecord03 accessRecord03 = accessRecord03Service.findById(id);
			model.addAttribute("accessRecord03", accessRecord03);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//菜单
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		model.addAttribute("itemId", itemId);
		model.addAttribute("id", id);
		return pageUrl;
	}
	
	
	
	
	
	//删除复访审核
	@ResponseBody
	@RequestMapping(value = "/accessRecord/knockdownRecordDel")
	public String knockdownRecordDel(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String byid = getTrimParameter("byid");
			String boxeditId = getTrimParameter("boxeditId");
			//删除
			accessRecord03Service.dels(boxeditId);
			map.put("msg", "100");
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}

}
