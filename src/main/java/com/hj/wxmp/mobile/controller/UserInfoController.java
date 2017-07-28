package com.hj.wxmp.mobile.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.dao.UserDao;
import com.hj.wxmp.mobile.entity.SysItemRole;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.entity.UserRole;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.hj.wxmp.mobile.services.UserRoleService;
/**
 * @author deng.hemei
 * @date 创建时间：2016年7月4日 上午11:03:15
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@RequestMapping("/userinfo")
@Controller
public class UserInfoController extends ControllerBase {

	//private final static Logger logger = LoggerFactory.getLogger(UserInfoController.class);
	private HashSessions hashSession = HashSessions.getInstance();

	@Autowired
	IKeyGen key;
	@Autowired
	//SysUserService sysUserService;
	UserInfoService userInfoService;
	@Autowired
	UserDao userDao;
	@Autowired
	UserRoleService sysUserRoleService;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	
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
	 * 进入会员信息列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model) {
		String itemId = super.getTrimParameter("itemId");
	    String id = super.getTrimParameter("id");
	   
		List<UserInfo> userList = userInfoService.findAll();
		model.addAttribute("userList", userList);
		//System.out.println("-----"+id);
		//System.out.println("-----------"+itemId);
	    model.addAttribute("itemId",itemId);
	    model.addAttribute("id",id);
		//model.addAttribute("itemNamesss",hashSession.getItemRole("itemNamesss"));
	    //model.addAttribute("lst",hashSession.getItemRole("lst"));
	    UserRole userRole=sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleid());
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		return "user/list";
	}

	/**
	 * 删除会员
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	public String del() {
		String byid = StringUtils.trim(getTrimParameter("byid"));
		String boxeditId = StringUtils.trim(getTrimParameter("boxeditId"));
		if (byid != null && !byid.equals("")) {
			userDao.del(byid);
		} else {
			String[] str = boxeditId.trim().split(",");
			StringBuffer sb = new StringBuffer();
			String strs = "";
			for (String s : str) {
				sb.append("'" + s + "',");
			}
			strs = sb.toString().substring(0, sb.length() - 1);
			userDao.dels(strs);
		}
		return "redirect:list";
	}

}
