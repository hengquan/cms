package com.hj.wxmp.mobile.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.common.UploadUtils;
import com.hj.wxmp.mobile.dao.ArticleDao;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.entity.SysAdmin;
import com.hj.wxmp.mobile.entity.SysItemRole;
import com.hj.wxmp.mobile.entity.SysUserRole;
import com.hj.wxmp.mobile.entity.TblArticle;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.SysUserRoleService;
import com.hj.wxmp.mobile.services.impl.ArticleService;

/**
* @author WangZhiYong
* @date 创建时间：2016年5月9日 下午14:38:42
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
@Controller
@RequestMapping("/article")
public class ArticleController extends ControllerBase {
	
	private final static Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	private HashSessions hashSession = HashSessions.getInstance();
	
	
	
	@Autowired
	IKeyGen key;
	@Autowired
	ArticleDao articleDao;
	@Autowired
	ArticleService articleService;
	@Autowired
	SysUserRoleService sysUserRoleService;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	
	public String Userid(){
		try {
			Object obj = request.getSession().getAttribute("adminSession");
			if(null!=obj){
				SysAdmin admin = (SysAdmin)obj;
				return admin.getId();
			}
			}catch(Exception e){
				return null;
			}
		return null;
	}
	
	
	@RequestMapping("/list")
	public String list(@RequestParam(value="nowPage",defaultValue="1") int nowPage,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize,Model model) {
		String articleType = StringUtils.trim(getTrimParameter("articleType"));
		String title = StringUtils.trim(getTrimParameter("title"));
		String itemId = StringUtils.trim(getTrimParameter("itemId"));
	    String id = StringUtils.trim(getTrimParameter("id"));
		List<TblArticle> list = articleService.getArticles(title,articleType,nowPage-1,pageSize);
		model.addAttribute("list", list);
		Map<String, Object> map = articleService.getArticleCount(title,articleType,pageSize);
		model.addAttribute("count",map.get("count"));
		model.addAttribute("nowPage",nowPage);
		model.addAttribute("totalPageNum",map.get("totalPageNum"));
	    model.addAttribute("articleType",articleType);
	    
		SysUserRole userRole=sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleId());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleId());
	    
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		model.addAttribute("itemId",itemId);
		model.addAttribute("id",id);
		return "article/list";
	}	
	
	@RequestMapping("/addOrUpdate")
	public String addOrUpdate(Model model,@RequestParam(value="imgUrl") MultipartFile file){
		String src = UploadUtils.upload(file, request);
		String editid = StringUtils.trim(getTrimParameter("editid"));
		String id = key.getUUIDKey();
		String articleType = StringUtils.trim(getTrimParameter("articleType"));
		String title = StringUtils.trim(getTrimParameter("title"));
		//String contextType = StringUtils.trim(getTrimParameter("contextType"));
		String content = StringUtils.trim(getTrimParameter("content"));
		String remark = StringUtils.trim(getTrimParameter("remark")); //备注
		String articleUrl = StringUtils.trim(getTrimParameter("articleUrl")); //链接地址
		String itemId = StringUtils.trim(getTrimParameter("itemId"));
		String idss = StringUtils.trim(getTrimParameter("id"));
		TblArticle t= new TblArticle();
		if(editid == null || editid.equals("")){
			t.setId(id);
			t.setArticleType(articleType);
			t.setTitle(title);
			t.setContent(content);
			t.setRemark(remark);
			t.setArticleUrl(articleUrl);
			t.setCreateUser(Userid());
			t.setUpdateUser(Userid());
			t.setImgUrl(src);
			articleDao.add(t);
			logger.info("添加成功!");
		}else{
			if(file != null && file.getSize() > 0){
				t.setImgUrl(src);
			}
			t.setId(editid);
			t.setArticleType(articleType);
			t.setTitle(title);
			t.setContent(content);
			t.setRemark(remark);
			t.setArticleUrl(articleUrl);
			t.setUpdateUser(Userid());
			articleDao.update(t);
			logger.info("修改成功!");
		}

		SysUserRole userRole=sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleId());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleId());
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
	    model.addAttribute("itemId",itemId);
	    model.addAttribute("id",idss);
		return "redirect:/article/list";
	}
	
	/**
	 * 删除一个或多个文章信息
	 * @return
	 */
	@RequestMapping("/del")
	public String del(Model model){
		String boxeditId = StringUtils.trim(getTrimParameter("boxeditId"));
		String byid = StringUtils.trim(getTrimParameter("byid"));
		String itemId = StringUtils.trim(getTrimParameter("itemId"));
	    String id = StringUtils.trim(getTrimParameter("id"));
		if(!byid.equals("")){
			articleDao.del(byid);
		}else{
			String[] str = boxeditId.trim().split(",");
			StringBuffer sb = new StringBuffer();
			String strs = "";
			for(String s: str){
				sb.append("'"+s+"',");
			}
			strs = sb.toString().substring(0,sb.length()-1);
			articleDao.dels(strs);
		}
		
	    SysUserRole userRole=sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleId());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleId());
	    
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		model.addAttribute("itemId",itemId);
		model.addAttribute("id",id);
		return "redirect:/article/list";
		
	}
	
	@RequestMapping("/intoAddOrUpdate")
	public String intoAddOrUpdate(Model model){
		String editValue = StringUtils.trim(getTrimParameter("editValue"));
		String itemId = StringUtils.trim(getTrimParameter("itemId"));
	    String id = StringUtils.trim(getTrimParameter("id"));
		if(editValue != null && !editValue.equals("")){
			TblArticle  tblArticle = articleDao.getArticleById(editValue);
			model.addAttribute("tblArticle", tblArticle);
		}
		//model.addAttribute("activeFlag","article");
		
	    model.addAttribute("itemId",itemId);
	    model.addAttribute("id",id);
	    SysUserRole userRole=sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
	    List<SysItemRole> lst =sysItemRoleDao.selectItemByRoleId(userRole.getRoleId());
	    List<SysItemRole> item=sysItemRoleDao.selectItemByPId(userRole.getRoleId());
	    model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		return "article/addOrUpdate";	
	}
}
