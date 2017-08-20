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
	public String userList(ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "accessRecord01/list";
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
			List<Map<String,Object>> hisFirstRecordMsg = accessRecord01Service.selectMessage(map);
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
	
	
	
	
	//首访页面详情
	@RequestMapping(value = "/accessRecord/firstRecordDetails")
	public String firstRecordDetails(ModelMap model){
		String pageUrl = "accessRecord01/seeRecord01";
		try {
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
