package com.hj.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.common.ControllerBase;
import com.hj.utils.HashSessions;
import com.hj.utils.JsonUtils;
import com.hj.web.dao.SysItemDao;
import com.hj.web.dao.SysItemRoleDao;
import com.hj.web.entity.SysItem;
import com.hj.web.entity.SysItemRole;
import com.hj.web.entity.UserInfo;
import com.hj.web.entity.UserRole;
import com.hj.web.mapping.SysItemMapper;
import com.hj.web.services.IKeyGen;
import com.hj.web.services.UserRoleService;

/**
 * @author deng.hemei
 * @date 创建时间：2016年6月17日 上午11:58:15
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@RequestMapping("/item")
@Controller
public class SysItemController extends ControllerBase {

	private final static Logger logger = LoggerFactory.getLogger(SysItemController.class);

	private HashSessions hashSession = HashSessions.getInstance();

	@Autowired
	SysItemDao sysItemDao;
	@Autowired
	IKeyGen key;
	@Autowired
	SysItemMapper sysItemMapper;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	UserRoleService sysUserRoleService;

	public String Userid() {
		try {
			Object obj = request.getSession().getAttribute("adminSession");
			if (null != obj) {
				UserInfo admin = (UserInfo) obj;
				return admin.getId();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	/**
	 * 进入菜单列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model) {
		List<SysItem> itemList = sysItemDao.findAllByPa("");
		// List<SysItem> itemList = sysItemDao.findAll();
		for (SysItem s : itemList) {
			List<SysItem> list = sysItemDao.findAllByPa(s.getId());
			s.setChildList(list);
		}
		model.addAttribute("itemList", itemList);
		// model.addAttribute("itemNamesss",hashSession.getItemRole("itemNamesss"));
		// model.addAttribute("lst",hashSession.getItemRole("lst"));
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		if (userRole != null) {
			List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
			List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
			model.addAttribute("itemNamesss", item);
			model.addAttribute("lst", lst);
			model.addAttribute("itemId", itemId);
			model.addAttribute("id", id);
			logger.info("查询所有菜单信息");
			return "item/list";
		} else {
			return "login/new_login";
		}
	}

	/**
	 * 添加菜单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addAndUpdate")
	public String addAndUpdate() {
		Map<String, Object> result = new HashMap<String, Object>();
		String editId = StringUtils.trimToEmpty(getTrimParameter("editId"));
		SysItem sysItem = new SysItem();
		String itemName = StringUtils.trim(getTrimParameter("itemName"));
		String itemUrl = StringUtils.trim(getTrimParameter("itemUrl"));
		String parentId = StringUtils.trim(getTrimParameter("parentId"));
		/* String isLeaf = StringUtils.trim(getTrimParameter("isLeaf")); */
		// String leafLevel= StringUtils.trim(getTrimParameter("leafLevel"));
		// String isGrade = StringUtils.trim(getTrimParameter("idGrade"));
		String iconImg = StringUtils.trim(getTrimParameter("iconImg"));
		String visibleFlag = StringUtils.trim(getTrimParameter("visibleFlag"));
		String seqNum = StringUtils.trim(getTrimParameter("seqNum"));
		String remark = StringUtils.trim(getTrimParameter("remark"));

		/* SysRole sysRole1 = sysRoleDao.getSysRoleById(editId); */

		boolean isSave = true;

		if (isSave) {
			if (editId == null || editId.equals("")) {
				// 根据菜单名称查询是否存在
				SysItem si = sysItemDao.selectByItemName(itemName);
				if ("".equals(si) || si == null) {
					sysItem.setId(key.getUUIDKey());
					sysItem.setItemName(itemName);
					sysItem.setItemUrl(itemUrl);
					sysItem.setParentId(parentId);
					sysItem.setIconImg(iconImg);
					sysItem.setVisibleFlag(Integer.parseInt(visibleFlag));
					sysItem.setSeqNum(Integer.parseInt(seqNum));
					sysItem.setRemark(remark);
					sysItemMapper.insertSelective(sysItem);
					logger.info("添加菜单成功");
					result.put("msg", "isc");
				} else {
					// 添加菜单失败，该菜单名称已存在
					result.put("msg", "iscz");
				}
			} else {
				sysItem.setId(editId);
				sysItem.setItemName(itemName);
				sysItem.setItemUrl(itemUrl);
				sysItem.setParentId(parentId);
				sysItem.setIconImg(iconImg);
				sysItem.setVisibleFlag(Integer.parseInt(visibleFlag));
				sysItem.setSeqNum(Integer.parseInt(seqNum));
				sysItem.setRemark(remark);
				sysItemMapper.updateByPrimaryKeySelective(sysItem);
				logger.info("修改菜单成功");
				result.put("msg", "isupdate");
			}
		} else {
			result.put("msg", "error");
		}

		logger.info("===========添加或修改菜单失败=============");

		return JsonUtils.map2json(result);
	}

	/**
	 * 删除管理员
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	public String del() {
		String byid = StringUtils.trim(getTrimParameter("byid"));
		String boxeditId = StringUtils.trim(getTrimParameter("boxeditId"));
		if (byid != null && !byid.equals("")) {
			sysItemDao.del(byid);
		} else {
			String[] str = boxeditId.trim().split(",");
			StringBuffer sb = new StringBuffer();
			String strs = "";
			for (String s : str) {
				sb.append("'" + s + "',");
			}
			strs = sb.toString().substring(0, sb.length() - 1);
			sysItemDao.dels(strs);
		}
		return "redirect:/item/list";
	}

}
