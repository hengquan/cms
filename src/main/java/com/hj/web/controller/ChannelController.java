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
import com.hj.utils.JsonUtils;
import com.hj.web.entity.Channel;
import com.hj.web.entity.SysRole;
import com.hj.web.entity.UserInfo;
import com.hj.web.entity.UserRole;
import com.hj.web.services.ChannelService;
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

	// 频道列表
	@RequestMapping(value = "/channel/getDataList")
	public String userList(PageService page, ModelMap model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "channel/list";
		String channelname = getTrimParameter("channelname");
		// 判断所属类型
		String channeltype = getTrimParameter("channelType");
		SysRole role = super.getUserRole();
		String roleId = role.getId();
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
				} else {
					roleId = "";
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
