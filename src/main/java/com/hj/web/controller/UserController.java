package com.hj.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hj.common.ControllerBase;
import com.hj.utils.JsonUtils;
import com.hj.utils.MD5Utils;
import com.hj.utils.UploadUtils;
import com.hj.web.dao.SysItemRoleDao;
import com.hj.web.entity.UserInfo;
import com.hj.web.services.IKeyGen;
import com.hj.web.services.SysRoleService;
import com.hj.web.services.UserInfoService;
import com.hj.web.services.UserRoleService;

/**
 * 
 * @ClassName: UserController
 * @Description: TODO(用户管理)
 * @author weilesi
 * @date 2016年2月26日 上午16:38:30
 *
 */
@Controller
public class UserController extends ControllerBase {

	@Resource
	private IKeyGen keyGen;
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	SysRoleService roleService;

	/**
	 * 主页
	 */
	@RequestMapping("/user/index")
	public String index(ModelMap map) {
		String pageUrl = "user/index";

		try {
			Object obj = request.getSession().getAttribute("adminSession");
			if (null != obj) {
				UserInfo admin = (UserInfo) obj;
				map.put("currentUser", admin);
			}

			map.put("activeFlag", "user");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pageUrl;
	}

	/**
	 * 修改用户密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/updatePwd")
	@ResponseBody
	public String updatePwd() throws Exception {
		String oldPwd = getTrimParameter("oldpwd");
		String newpwd = getTrimParameter("newpwd");
		Object obj = request.getSession().getAttribute("adminSession");
		if (null != obj) {
			UserInfo userInfo = (UserInfo) obj;
			if (userInfo.getPassword().equals(MD5Utils.MD5(oldPwd))) {
				userInfo.setPassword(MD5Utils.MD5(newpwd));
				userInfoService.update(userInfo);
			} else {
				return "{\"code\":\"error\"}";
			}

		}
		return "{\"code\":\"ok\"}";

	}

	@RequestMapping("/user/wpPwd")
	public String upPwd() {
		return "admin/update_pwd";
	}

	// 用户列表
	@RequestMapping(value = "/user/userList")
	public String userList(@RequestParam(value = "nowPage", defaultValue = "1") int nowPage,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize, ModelMap model) throws Exception {
		String pageUrl = "user/list";
		// 纪录总数
		Integer listMessgeCount = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		String state = getTrimParameter("selectState");
		String userName = getTrimParameter("userName");
		state = "1";
		map.put("state", state);
		map.put("userName", userName);
		Integer start = ((nowPage - 1) * pageSize);
		map.put("page", start);
		map.put("pageSize", pageSize);
		try {
			// 获取用户所有的信息
			List<UserInfo> selectList = userInfoService.getDataList(map);
			// 获取用户所对应项目的信息和角色信息
			for (UserInfo userinfo : selectList) {
				String userId = userinfo.getId();
				// 用户所对应的角色
				Map<String, Object> userRole = userRoleService.findByUserId(userId);
				userinfo.setUserRole(userRole);
			}
			listMessgeCount = userInfoService.getDataListCount(map);
			Integer totalCount = listMessgeCount % pageSize;
			Integer totalPageNum = 0;
			if (totalCount == 0) {
				totalPageNum = listMessgeCount / pageSize;
			} else {
				totalPageNum = (listMessgeCount / pageSize) + 1;
			}
			model.put("userName", userName);
			model.put("nowPage", nowPage);
			model.put("totalPageNum", totalPageNum);
			model.addAttribute("userList", selectList);
			model.put("pageSize", pageSize);
			model.put("state", state);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageUrl = super.userIRoleItem(model, pageUrl);
		return pageUrl;
	}

	// 添加项目
	@RequestMapping(value = "/user/save")
	public String addUser(ModelMap model, UserInfo userInfo,
			@RequestParam(value = "headFile", required = false) MultipartFile headFile) {
		try {
			if (headFile != null && headFile.getSize() > 0) {
				String src = UploadUtils.upload(headFile, request);
				userInfo.setHeadimgurl(src);
			}
			userInfoService.save(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:userList";
	}

	// 删除项目
	@RequestMapping(value = "/user/del")
	public String deleteUser(ModelMap model) {
		try {
			String byid = getTrimParameter("byid");
			UserInfo userInfo = userInfoService.get(byid);
			String boxeditId = getTrimParameter("boxeditId");
			if (StringUtils.isNotEmpty(byid)) {
				userInfoService.del(userInfo);
			} else {
				userInfoService.deletes(boxeditId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:userList";
	}

	// 根据ID获取用户信息
	@RequestMapping(value = "/user/getData")
	@ResponseBody
	public String getData(ModelMap model, UserInfo userInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userInfo = userInfoService.get(userInfo);
			if (userInfo != null) {
				map.put("msg", "0");
				map.put("Data", userInfo);
			}
		} catch (Exception e) {
			map.put("msg", "1");
		}
		return JsonUtils.map2json(map);
	}

}
