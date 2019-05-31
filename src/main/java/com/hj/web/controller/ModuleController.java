package com.hj.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.common.ControllerBase;
import com.hj.utils.Configurations;
import com.hj.web.entity.Module;
import com.hj.web.entity.UserInfo;
import com.hj.web.entity.UserRole;
import com.hj.web.services.ModuleService;
import com.hj.web.services.PageService;
import com.hj.web.services.UserRoleService;

//频道管理
@Controller
public class ModuleController extends ControllerBase {

	@Autowired
	ModuleService moduleService;
	@Autowired
	PageService pageService;
	@Autowired
	UserRoleService userRoleService;

	// 频道列表
	@RequestMapping(value = "/module/getDataList")
	public String userList(PageService page, ModelMap model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "module/list";
		String moduleName = getTrimParameter("moduleName");
		// 判断所属类型
		String roleId = getTrimParameter("roleId");
		// 判断当前用户是啥级别的
		UserInfo userInfo = super.getUserInfo();
		if (userInfo != null) {
			String parentId = userInfo.getParentId();
			if (StringUtils.isNotEmpty(parentId)) {
				if (!parentId.equals("0")) {
					userInfo = super.getParentUserData(userInfo);
					if (userInfo != null) {
						String userId = userInfo.getId();
						if (StringUtils.isNotEmpty(userId)) {
							UserRole userRole = userRoleService.selectByUserId(userId);
							if (userRole != null) {
								roleId = userRole.getRoleid();
							}
						}
					}
				}
			}
		}
		map.put("roleId", roleId);
		try {
			// 存页面起始位置信息
			pageService.getPageLocation(page, map);
			if (StringUtils.isNotEmpty(moduleName)) {
				map.put("moduleName", moduleName);
			} else {
				map.put("moduleName", "");
			}
			// 获取所有频道信息
			List<Module> moduleList = moduleService.getDataMessge(map);
			if (moduleList != null && moduleList.size() > 0) {
				for (Module module : moduleList) {
					urlManage(module);
				}
			}
			// 所有信息数量
			int listMessgeCount = moduleService.getDataMessgeCount(map);
			// 获取页面信息
			pageService.getPageData(listMessgeCount, model, page);
			model.put("moduleName", moduleName);
			model.put("roleId", roleId);
			model.addAttribute("moduleList", moduleList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageUrl = super.userIRoleItem(model, pageUrl);
		return pageUrl;
	}

	// 保存频道
	@RequestMapping(value = "/module/save")
	public String saveProject(ModelMap model, Module project) {
		String roleId = getTrimParameter("roleId");
		try {
			moduleService.save(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("roleId", roleId);
		String itemId = getTrimParameter("itemId");
		String positionId = getTrimParameter("positionId");
		model.addAttribute("itemId", itemId);
		model.addAttribute("positionId", positionId);
		return "redirect:getDataList";
	}

	// 删除频道
	@RequestMapping(value = "/module/del")
	public String deletePro(ModelMap model, Module project) {
		String roleId = getTrimParameter("roleId");
		try {
			String boxeditId = getTrimParameter("boxeditId");
			if (StringUtils.isNotEmpty(boxeditId)) {
				moduleService.deletes(boxeditId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("roleId", roleId);
		String itemId = getTrimParameter("itemId");
		String positionId = getTrimParameter("positionId");
		model.addAttribute("itemId", itemId);
		model.addAttribute("positionId", positionId);
		return "redirect:getDataList";
	}

	// 处理图片访问地址
	public Module urlManage(Module module) {
		if (module != null) {
			String picUrl = module.getPicUrl();
			if (StringUtils.isNotEmpty(picUrl)) {
				String path = Configurations.getAccessUrl();
				if (StringUtils.isNotEmpty(path)) {
					picUrl = path + picUrl;
					module.setPicUrl(picUrl);
				}
			}
		}
		return module;
	}

	// 获取站点下所有的模块
	@RequestMapping(value = "/module/getDataByRoleId")
	@ResponseBody
	public Map<String, Object> getDataByRoleId(ModelMap model) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String roleId = getTrimParameter("roleId");
		try {
			if (StringUtils.isNotEmpty(roleId)) {
				List<Module> moduleList = moduleService.getDataByRoleId(roleId);
				resultMap.put("code", "200");
				resultMap.put("moduleList", moduleList);
			} else {
				resultMap.put("code", "201");
				resultMap.put("msg", "站点信息为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", "500");
			resultMap.put("msg", "系统错误请联系管理员");
		}
		return resultMap;
	}
}
