package com.hj.web.controller;

import java.util.ArrayList;
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
import com.hj.utils.JsonUtils;
import com.hj.web.dao.SysItemDao;
import com.hj.web.dao.SysItemRoleDao;
import com.hj.web.entity.Channel;
import com.hj.web.entity.Module;
import com.hj.web.entity.SysItem;
import com.hj.web.entity.SysItemRole;
import com.hj.web.entity.UserInfo;
import com.hj.web.entity.UserRole;
import com.hj.web.services.ChannelService;
import com.hj.web.services.ModuleService;
import com.hj.web.services.PageService;
import com.hj.web.services.UserRoleService;

//频道管理
@Controller
public class ChannelController extends ControllerBase {

	@Autowired
	ChannelService channelService;
	@Autowired
	PageService pageService;
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	ModuleService moduleService;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	SysItemDao sysItemDao;

	// 频道列表
	@RequestMapping(value = "/channel/getDataList")
	public String userList(PageService page, ModelMap model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "channel/list";
		String channelname = getTrimParameter("channelname");
		// 判断所属类型
		String channeltype = getTrimParameter("channelType");
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
			if (StringUtils.isNotEmpty(channelname)) {
				map.put("channelname", channelname);
			} else {
				map.put("channelname", "");
			}
			if (StringUtils.isNotEmpty(channeltype)) {
				map.put("channeltype", Integer.parseInt(channeltype));
			} else {
				map.put("channeltype", 0);
			}
			// 获取所有频道信息
			List<Channel> selectList = channelService.getProjectMessge(map);
			if (selectList != null && selectList.size() > 0) {
				for (Channel channel : selectList) {
					String moduleIds = channel.getModuleId();
					if (StringUtils.isNotEmpty(moduleIds)) {
						String[] moduleIdList = moduleIds.split(",");
						if (moduleIdList.length > 0) {
							String moduleNames = "";
							for (String moduleId : moduleIdList) {
								Module module = moduleService.get(moduleId);
								if (module != null) {
									String moduleName = module.getModuleName();
									moduleNames += "," + moduleName;
								}
							}
							if (StringUtils.isNotEmpty(moduleNames)) {
								moduleNames = moduleNames.substring(1);
								channel.setModuleName(moduleNames);
							}
						}
					}
					urlManage(channel);
				}
			}
			// 所有信息数量
			int listMessgeCount = channelService.getProjectMessgeCount(map);
			// 获取页面信息
			pageService.getPageData(listMessgeCount, model, page);
			model.put("channelname", channelname);
			model.put("channeltype", channeltype);
			model.put("roleId", roleId);
			model.addAttribute("selectList", selectList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageUrl = super.userIRoleItem(model, pageUrl);
		return pageUrl;
	}

	// 保存频道
	@RequestMapping(value = "/channel/save")
	public String saveProject(ModelMap model, Channel project) {
		try {
			channelService.save(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("channelType", project.getChanneltype());
		String itemId = getTrimParameter("itemId");
		String positionId = getTrimParameter("positionId");
		model.addAttribute("itemId", itemId);
		model.addAttribute("positionId", positionId);
		return "redirect:getDataList";
	}

	// 删除频道
	@RequestMapping(value = "/channel/del")
	public String deletePro(ModelMap model, Channel project) {
		try {
			String boxeditId = getTrimParameter("boxeditId");
			if (StringUtils.isNotEmpty(boxeditId)) {
				channelService.deletes(boxeditId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("channelType", project.getChanneltype());
		String itemId = getTrimParameter("itemId");
		String positionId = getTrimParameter("positionId");
		model.addAttribute("itemId", itemId);
		model.addAttribute("positionId", positionId);
		return "redirect:getDataList";
	}

	// 获取所有频道
	@RequestMapping(value = "/channel/getAllData")
	@ResponseBody
	public String getAllData() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Channel> channelList = channelService.getAllData();
			if (channelList != null && channelList.size() > 0) {
				for (Channel channel : channelList) {
					urlManage(channel);
				}
				map.put("dataList", channelList);
				map.put("msg", "0");
			} else {
				map.put("msg", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}

	// 获取所有频道根据渠道类型根据相关权限
	@RequestMapping(value = "/channel/getDataByType")
	@ResponseBody
	public String getDataByType() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		String channelType = getTrimParameter("channelType");
		String roleId = getTrimParameter("roleId");
		int channeltype = 0;
		try {
			if (StringUtils.isNotEmpty(channelType)) {
				channeltype = Integer.parseInt(channelType);
			}
			param.put("channeltype", channeltype);
			param.put("roleId", roleId);
			List<Channel> channelList = channelService.getDataByType(param);
			if (channelList != null && channelList.size() > 0) {
				map.put("dataList", channelList);
				map.put("msg", "0");
			} else {
				map.put("msg", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}

	// 获取所有频道根据渠道类型根据相关权限
	@RequestMapping(value = "/channel/getDataByUrserRole")
	@ResponseBody
	public String getDataByUrserRole() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		String roleId = getTrimParameter("roleId");
		try {
			// 菜单ID组
			String itemIds = "";
			// 获取该权限的菜单列表
			List<SysItemRole> itemRoleList = sysItemRoleDao.selectItemByRoleId(roleId);
			if (itemRoleList != null && itemRoleList.size() > 0) {
				for (SysItemRole itemRole : itemRoleList) {
					String itemId = itemRole.getItemId();
					if (StringUtils.isNotEmpty(itemId)) {
						itemIds += "," + itemId;
					}
				}
			}
			if (StringUtils.isNotEmpty(itemIds))
				itemIds = itemIds.substring(1);
			List<SysItem> sysItemList = sysItemDao.selDataByIds(itemIds);
			String channelType = "";
			if (sysItemList != null && sysItemList.size() > 0) {
				for (SysItem item : sysItemList) {
					String moduleName = item.getItemName();
					if (StringUtils.isNotEmpty(moduleName)) {
						if (moduleName.equals("APP")) {
							channelType += "," + "1";
						} else if (moduleName.equals("H5")) {
							channelType += "," + "2";
						} else if (moduleName.equals("触摸板") || moduleName.equals("触摸版")) {
							channelType += "," + "3";
						} else if (moduleName.equals("APP视频")) {
							channelType += "," + "4";
						}
					}
				}
			}
			param.put("roleId", roleId);
			if (StringUtils.isNotEmpty(channelType))
				channelType = channelType.substring(1);
			param.put("channeltype", channelType);
			List<Channel> app = new ArrayList<Channel>();
			List<Channel> h5 = new ArrayList<Channel>();
			List<Channel> chumo = new ArrayList<Channel>();
			List<Channel> appVideo = new ArrayList<Channel>();
			List<Channel> channelList = channelService.getDataByType(param);
			if (channelList != null && channelList.size() > 0) {
				for (Channel channel : channelList) {
					Integer type = channel.getChanneltype();
					if (type == 1) {
						app.add(channel);
					} else if (type == 2) {
						h5.add(channel);
					} else if (type == 3) {
						chumo.add(channel);
					} else if (type == 4) {
						appVideo.add(channel);
					}
				}
			}
			if (app != null && app.size() > 0)
				map.put("app", app);
			if (h5 != null && app.size() > 0)
				map.put("h5", h5);
			if (chumo != null && app.size() > 0)
				map.put("chumo", chumo);
			if (appVideo != null && app.size() > 0)
				map.put("appVideo", appVideo);
			map.put("msg", "0");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "1");
		}
		return JsonUtils.map2json(map);
	}

	// 处理图片访问地址
	public Channel urlManage(Channel channel) {
		if (channel != null) {
			String picUrl = channel.getPicUrl();
			if (StringUtils.isNotEmpty(picUrl)) {
				String path = Configurations.getAccessUrl();
				if (StringUtils.isNotEmpty(path)) {
					picUrl = path + picUrl;
					channel.setPicUrl(picUrl);
				}
			}
		}
		return channel;
	}
}
