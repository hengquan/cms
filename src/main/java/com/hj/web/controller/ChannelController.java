package com.hj.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hj.common.ControllerBase;
import com.hj.web.dao.SysItemRoleDao;
import com.hj.web.entity.Channel;
import com.hj.web.services.ChannelService;
import com.hj.web.services.IKeyGen;
import com.hj.web.services.UserRoleService;

//项目管理
@Controller
public class ChannelController extends ControllerBase {

	@Resource
	private IKeyGen keyGen;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	UserRoleService sysUserRoleService;
	@Autowired
	ChannelService channelService;

	// 项目列表
	@RequestMapping(value = "/channel/getDataList")
	public String userList(@RequestParam(value = "nowPage", defaultValue = "1") int nowPage,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize, ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageUrl = "channel/list";
		// 纪录总数
		Integer listMessgeCount = 0;
		String channelname = getTrimParameter("channelname");
		Integer start = ((nowPage - 1) * pageSize);
		map.put("page", start);
		map.put("pageSize", pageSize);
		try {
			if (channelname == null) {
				map.put("channelname", "");
			} else {
				map.put("channelname", channelname);
			}
			// 获取所有项目信息
			List<Channel> selectList = channelService.getProjectMessge(map);
			for (Channel pro : selectList) {
				String proId = pro.getId();
				map.put("proId", proId);
			}
			// 所有信息数量
			listMessgeCount = channelService.getProjectMessgeCount(map);
			Integer totalCount = listMessgeCount % pageSize;
			Integer totalPageNum = 0;
			if (totalCount == 0) {
				totalPageNum = listMessgeCount / pageSize;
			} else {
				totalPageNum = (listMessgeCount / pageSize) + 1;
			}
			model.put("nowPage", nowPage);
			model.put("totalPageNum", totalPageNum);
			model.put("channelname", channelname);
			model.addAttribute("selectList", selectList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageUrl = super.userIRoleItem(model, pageUrl);
		return pageUrl;
	}

	// 添加项目
	@RequestMapping(value = "/channel/add")
	public String addProject(ModelMap model, Channel project) {
		try {
			project.setId(keyGen.getUUIDKey());
			channelService.insert(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:getDataList";
	}

	// 修改项目
	@RequestMapping(value = "/channel/edit")
	public String editProject(ModelMap model, Channel project) {
		String editId = getTrimParameter("editId");
		try {
			project.setId(editId);
			channelService.update(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:getDataList";
	}

	// 删除项目
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
		return "redirect:getDataList";
	}
}
