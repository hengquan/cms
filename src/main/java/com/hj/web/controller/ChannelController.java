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
import com.hj.utils.JsonUtils;
import com.hj.web.entity.Channel;
import com.hj.web.services.ChannelService;
import com.hj.web.services.PageService;

//频道管理
@Controller
public class ChannelController extends ControllerBase {

	@Autowired
	ChannelService channelService;
	@Autowired
	PageService pageService;

	// 频道列表
	@RequestMapping(value = "/channel/getDataList")
	public String userList(PageService page, ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "channel/list";
		String channelname = getTrimParameter("channelname");
		// 判断所属类型
		String channeltype = getTrimParameter("channelType");
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
			// 所有信息数量
			int listMessgeCount = channelService.getProjectMessgeCount(map);
			// 获取页面信息
			pageService.getPageData(listMessgeCount, model, page);
			model.put("channelname", channelname);
			model.put("channeltype", channeltype);
			model.addAttribute("selectList", selectList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageUrl = super.userIRoleItem(model, pageUrl);
		return pageUrl;
	}

	// 添加频道
	@RequestMapping(value = "/channel/add")
	public String addProject(ModelMap model, Channel project) {
		try {
			channelService.insert(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("channelType", project.getChanneltype());
		return "redirect:getDataList";
	}

	// 修改频道
	@RequestMapping(value = "/channel/edit")
	public String editProject(ModelMap model, Channel project) {
		String editId = getTrimParameter("editId");
		try {
			project.setId(editId);
			channelService.update(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("channelType", project.getChanneltype());
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

	// 获取所有频道根据渠道类型
	@RequestMapping(value = "/channel/getDataByType")
	@ResponseBody
	public String getDataByType() {
		Map<String, Object> map = new HashMap<String, Object>();
		String channelType = getTrimParameter("channelType");
		int channeltype = 0;
		try {
			if (StringUtils.isNotEmpty(channelType)) {
				channeltype = Integer.parseInt(channelType);
			}
			List<Channel> channelList = channelService.getDataByType(channeltype);
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
}
