package com.hj.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.hj.utils.HashSessions;
import com.hj.web.dao.SysItemRoleDao;
import com.hj.web.entity.SysItemRole;
import com.hj.web.entity.UserRole;
import com.hj.web.services.UserRoleService;

public class ControllerBase {

	private HashSessions hashSession = HashSessions.getInstance();

	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	UserRoleService sysUserRoleService;

	/**
	 * @description 参数集合
	 */
	protected static Map<String, Object> paramMap = new HashMap<>();

	/**
	 * @description 视图
	 */
	protected static ModelAndView modelAndView = new ModelAndView();

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Locale locale;
	static JsonMapper jsonMapper = new JsonMapper();

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		this.request = request;
		this.response = response;
		this.locale = locale;
		// this.setAppLang();//设置多语言资源
	}

	/*
	 * @InitBinder public void initBinder(WebDataBinder binder) { SimpleDateFormat
	 * dateFormat = new SimpleDateFormat(Config.getDateFormatDate());
	 * dateFormat.setLenient(false); binder.registerCustomEditor(Date.class, new
	 * CustomDateEditor(dateFormat, true)); }
	 */

	public static String toJsonStr(Object obj) {
		if (obj == null)
			return null;
		String result = null;
		try {
			result = jsonMapper.toJsonStr(obj);
			result = escapeHtml(result);
		} catch (JsonGenerationException e) { //
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String toJsonStr(Object value, String[] properties) {
		String result = null;

		try {
			result = jsonMapper.toJsonStr(value, properties);
			result = escapeHtml(result);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	static String escapeHtml(String src) {
		String r = src.replace("<", "&lt;");

		r = r.replace(">", "&gt;");
		return r;
	}

	public String toJsonStrWithExcludeProperties(Object value, String[] properties2Exclude) {
		String result = null;
		try {
			result = jsonMapper.toJsonStrWithExcludeProperties(value, properties2Exclude);
			result = escapeHtml(result);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	public String toListJson(int totalCount, List<Object> lst, String[] listfields) {
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("{");
		jsonBuilder.append("rowcount:" + totalCount);
		if (lst != null) {
			jsonBuilder.append(",rows:");
			jsonBuilder.append("[");
			for (int i = 0; i < lst.size(); i++) {
				if (i > 0)
					jsonBuilder.append(",");
				if (null != listfields)
					jsonBuilder.append(toJsonStr(lst.get(i), listfields));
				else
					jsonBuilder.append(toJsonStr(lst.get(i)));

			}
			jsonBuilder.append("]");
		}
		jsonBuilder.append("}");
		return jsonBuilder.toString();
	}

	/**
	 * 
	 * @param success
	 * @param params
	 * @return
	 */
	public static String toJson(String... params) {

		if (null == params)
			return null;
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("{");

		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i += 2) {
				if (i > 0)
					jsonBuilder.append(",");
				jsonBuilder.append("\"" + params[i] + "\":");
				if (StringUtils.isNumeric(params[i + 1]) || params[i + 1].equalsIgnoreCase("true")
						|| params[i + 1].equalsIgnoreCase("false")) {
					jsonBuilder.append(params[i + 1]);
				} else {
					jsonBuilder.append("\"" + escapeHtml(params[i + 1]) + "\"");
				}
			}
		}
		jsonBuilder.append("}");
		return jsonBuilder.toString();
	}

	public static String toJson(Map<String, String> params) {
		if (null == params)
			return null;
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("{");
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		if (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			jsonBuilder.append("\"" + entry.getKey() + "\":");
			if (StringUtils.isNumeric(entry.getValue()) || entry.getValue().equalsIgnoreCase("true")
					|| entry.getValue().equalsIgnoreCase("false")) {
				jsonBuilder.append(entry.getValue());
			} else {
				jsonBuilder.append("\"" + escapeHtml(entry.getValue()) + "\"");
			}
		}
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			jsonBuilder.append(",\"" + entry.getKey() + "\":");
			if (StringUtils.isNumeric(entry.getValue()) || entry.getValue().equalsIgnoreCase("true")
					|| entry.getValue().equalsIgnoreCase("false")) {
				jsonBuilder.append(entry.getValue());
			} else {
				jsonBuilder.append("\"" + escapeHtml(entry.getValue()) + "\"");
			}
		}
		jsonBuilder.append("}");
		return jsonBuilder.toString();
	}

	public static String toJson(List<Map<String, String>> params) {
		if (null == params)
			return null;
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("[");
		Iterator<Map<String, String>> iterator = params.iterator();
		if (iterator.hasNext()) {
			Map<String, String> mapItem = (Map<String, String>) iterator.next();
			jsonBuilder.append(toJson(mapItem));
		}
		while (iterator.hasNext()) {
			Map<String, String> mapItem = (Map<String, String>) iterator.next();
			jsonBuilder.append("," + toJson(mapItem));
		}
		jsonBuilder.append("]");
		return jsonBuilder.toString();
	}

	public String getTrimParameter(String name) {
		String v = request.getParameter(name);
		if (null == v)
			return null;
		v = v.trim();
		return v;
	}

	public static Map<String, Map<String, Object>> json2Map(String json) {
		if (StringUtils.trimToEmpty(json).equals(""))
			return null;

		return jsonMapper.json2Map(json);
	}

	public static List<LinkedHashMap<String, Object>> json2List(String json) {
		if (StringUtils.trimToEmpty(json).equals(""))
			return null;

		return jsonMapper.json2List(json);
	}

	//统一返回权限列表菜单
	public String userIRoleItem(ModelMap model, String pageUrl) {
		try {
			UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
			List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
			List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
			model.addAttribute("itemNamesss", item);
			model.addAttribute("lst", lst);
			String itemId = this.getTrimParameter("itemId");
			String id = this.getTrimParameter("id");
			model.addAttribute("itemId", itemId);
			model.addAttribute("id", id);
		} catch (Exception e) {
			pageUrl = "login/new_login";
		}
		return pageUrl;
	}

}
