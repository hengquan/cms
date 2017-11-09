package com.hj.wxmp.mobile.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.hj.utils.JsonUtils;
import com.hj.web.core.mvc.ControllerBase;
import com.hj.wxmp.mobile.common.HashSessions;
import com.hj.wxmp.mobile.dao.SysItemRoleDao;
import com.hj.wxmp.mobile.entity.AccessRecord01;
import com.hj.wxmp.mobile.entity.Customer;
import com.hj.wxmp.mobile.entity.ImportMapUserCust;
import com.hj.wxmp.mobile.entity.ProjCustRef;
import com.hj.wxmp.mobile.entity.SysItemRole;
import com.hj.wxmp.mobile.entity.SysRole;
import com.hj.wxmp.mobile.entity.UserCustRef;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.entity.UserRole;
import com.hj.wxmp.mobile.services.AccessRecord01Service;
import com.hj.wxmp.mobile.services.AccessRecord02Service;
import com.hj.wxmp.mobile.services.AccessRecord03Service;
import com.hj.wxmp.mobile.services.CustomerService;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.ImportMapUserCustService;
import com.hj.wxmp.mobile.services.ProjCustRefService;
import com.hj.wxmp.mobile.services.ProjUserRoleService;
import com.hj.wxmp.mobile.services.ProjectService;
import com.hj.wxmp.mobile.services.SysRoleService;
import com.hj.wxmp.mobile.services.UserCustRefService;
import com.hj.wxmp.mobile.services.UserInfoService;
import com.hj.wxmp.mobile.services.UserRoleService;

//项目管理
@Controller
public class CustomerController extends ControllerBase {

	private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);
	private HashSessions hashSession = HashSessions.getInstance();

	@Resource
	private IKeyGen keyGen;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	SysItemRoleDao sysItemRoleDao;
	@Autowired
	UserRoleService sysUserRoleService;
	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	ProjUserRoleService projUserRoleService;
	@Autowired
	UserCustRefService userCustRefService;
	@Autowired
	ProjectService projectService;
	@Autowired
	CustomerService customerService;
	@Autowired
	AccessRecord01Service accessRecord01Service;
	@Autowired
	AccessRecord02Service accessRecord02Service;
	@Autowired
	AccessRecord03Service accessRecord03Service;
	@Autowired
	ProjCustRefService projCustRefService;
	@Autowired
	ImportMapUserCustService importMapUserCustService;

	// 客户列表
	@RequestMapping(value = "/customer/customerList")
	public String userList(@RequestParam(value="nowPage",defaultValue="1") int nowPage,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize,
			@RequestParam(value="isValidate",defaultValue="") String isValidate,ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		//纪录总数
		Integer listMessgeCount = 0;
		String name = getTrimParameter("custName");
		Integer start = ((nowPage - 1) * pageSize);
		map.put("page", start);
		map.put("pageSize", pageSize);
		map.put("isValidate", isValidate);
		String pageUrl = "customer/list";
		try {
			//用户角色权限信息
			String id = hashSession.getCurrentAdmin(request).getId();
			UserRole userRole = sysUserRoleService.selectByUserId(id);
			String roleId = userRole.getRoleid();
			SysRole role = sysRoleService.findById(roleId);
			String roleName = role.getRoleName();
			if(name == null){
				map.put("name", "");
			}else{
				map.put("name", name);
			}
			if(roleName.equals("管理员")) {
				map.put("userType", "1");
			}
			if(roleName.equals("项目负责人")) {
				String projIds = "";
				List<Map<String, Object>> projs = projUserRoleService.selectByUserId(id);
				for(Map<String, Object> proj : projs){
					projIds+=proj.get("id").toString()+",";
				}
				map.put("projIds", projIds);
				map.put("userType", "2");
			}
			if(roleName.equals("项目管理人")) {
				String projIds = "";
				List<Map<String, Object>> projs = projUserRoleService.selectByUserId(id);
				for(Map<String, Object> proj : projs){
					projIds+=proj.get("id").toString()+",";
				}
				map.put("projIds", projIds);
				map.put("userType", "3");
			}
			if(roleName.equals("顾问")) {
				map.put("userType", "4");
				map.put("userId", id);
			}
			//最终返回的信息值
			List<Map<String,Object>> userMsg = new ArrayList<Map<String,Object>>();
			//是否是正常状态3为特殊状态其他为正常状态
			if(isValidate.equals("")){
				// 获取所有用户信息
				userMsg = userCustRefService.selectByUserMessge(map);
				//所有信息数量
				listMessgeCount = userCustRefService.selectByUserMessgeCount(map);
			}else{
				//查看所有已作废顾问和客户未有顾问的所有信息
				userMsg = userCustRefService.selectBySpecialUserMessge(map);
				//所有信息数量
				listMessgeCount = userCustRefService.selectBySpecialUserMessgeCount(map);
			}
		 	Integer totalCount = listMessgeCount%pageSize;
			Integer totalPageNum = 0;
			if(totalCount==0){
				totalPageNum = listMessgeCount/pageSize;
			}else{
				totalPageNum = (listMessgeCount/pageSize)+1;
			}
			model.put("nowPage", nowPage);
			model.put("totalPageNum", totalPageNum);
			model.addAttribute("userMsg", userMsg);
			model.addAttribute("role", role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		model.addAttribute("itemId", itemId);
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("isValidate", isValidate);
		return pageUrl;
	}

	
	
	
	//查询该项目里面的所有用户信息
	@ResponseBody
	@RequestMapping(value = "/customer/getUserList")
	public String getUserList(ModelMap model) {
		Map<String,Object>map=new HashMap<String,Object>();
		String projId = getTrimParameter("projId");
		try {
			//用户角色权限信息
			UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
			String roleId = userRole.getRoleid();
			SysRole role = sysRoleService.findById(roleId);
			List<UserInfo> userData = null;
			if(role.getRoleName().equals("管理员")) userData = projUserRoleService.selectProjUserDataByProjIdGLY(projId);
			if(role.getRoleName().equals("项目负责人")) userData = projUserRoleService.selectProjUserDataByProjIdFZR(projId);
			map.put("msg", "100");
			map.put("userData", userData);
			map.put("projId", projId);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}
	
	
	//转移客户信息
	@RequestMapping(value = "/customer/transferCustMsg")
	public String transferCustMsg(ModelMap model) {
		try {
			Map<String,Object>parmeterMap=new HashMap<String,Object>();
			String projId = getTrimParameter("projId");
			String custId = getTrimParameter("custId");
			//转移到该用户名下
			String userId = getTrimParameter("userId");
			parmeterMap.put("projId", projId);
			parmeterMap.put("custId", custId);
			parmeterMap.put("userId", userId);
			//用户客户表
			List<UserCustRef> userCustRefs = userCustRefService.selectByProjIdAndCustId(parmeterMap);
			if(userCustRefs.size()>0){
				userCustRefService.updateByProjIdAndCustId(parmeterMap);
			}else{
				String[] splits = custId.split(",");
				for(String split : splits){
					if(StringUtils.isNotEmpty(split)){
						UserCustRef userCustRef = new UserCustRef();
						userCustRef.setId(keyGen.getUUIDKey());
						userCustRef.setProjid(projId);
						userCustRef.setUserid(userId);
						userCustRef.setCustid(split);
						userCustRef.setIsvalidate(1);
						userCustRefService.insert(userCustRef);
					}
				}
			}
			//首访
			accessRecord01Service.updateByProjIdAndCustId(parmeterMap);
			//复访
			accessRecord02Service.updateByProjIdAndCustId(parmeterMap);
			//成交
			accessRecord03Service.updateByProjIdAndCustId(parmeterMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:customerList";
	}




	//查询该项目里面的所有用户信息(多选)
	@ResponseBody
	@RequestMapping(value = "/customer/getUserLists")
	public String getUserLists(ModelMap model) {
		Map<String,Object>map=new HashMap<String,Object>();
		String userCustIds = getTrimParameter("userCustIds");
		try {
			if(userCustIds!=null){
				//项目id
				String projId = null;
				//原权限人id
				String custId = "";
				String[] userCustIdList = userCustIds.split(",");
				for(String userCustId : userCustIdList){
					AccessRecord01 accessRecord01 = accessRecord01Service.findById(userCustId);
					String projid = accessRecord01.getProjid();
					custId += accessRecord01.getCustid()+",";
					//对比是否为同一个项目
					if(projId==null){
						projId = projid;
					}else{
						if(!projId.equals(projid)){
							map.put("msg", "105");
							return JsonUtils.map2json(map);
						}
					}
				}
				//用户角色权限信息
				UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
				String roleId = userRole.getRoleid();
				SysRole role = sysRoleService.findById(roleId);
				List<UserInfo> userData = null;
				if(role.getRoleName().equals("管理员")) userData = projUserRoleService.selectProjUserDataByProjIdGLY(projId);
				if(role.getRoleName().equals("项目负责人")) userData = projUserRoleService.selectProjUserDataByProjIdFZR(projId);
				map.put("msg", "100");
				map.put("userData", userData);
				map.put("projId", projId);
				map.put("custId", custId);
			}else{
				map.put("msg", "200");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "103");
		}
		System.out.println(JsonUtils.map2json(map));
		return JsonUtils.map2json(map);
	}
	
	
	
	
	
	
	
	
	
	//查询结果导出excel
	@RequestMapping(value = "/customer/downLoadExcel")
	public void  downLoadExcel(@RequestParam(value="path",defaultValue="") String path) {
		 // path是指欲下载的文件的路径。
         File file = new File(path);
         // 取得文件名。
         String filename = file.getName();
    	 //下载
         response.setContentType("multipart/form-data");
         response.setContentType("application/force-download");
         response.setHeader("Content-Disposition", "attachment;fileName="+filename);
         ServletOutputStream out = null;
         try {
             FileInputStream inputStream = new FileInputStream(file);
             //3.通过response获取ServletOutputStream对象(out)
             out = response.getOutputStream();
             int b = 0;
             byte[] buffer = new byte[1024];
             while (b != -1){
                 b = inputStream.read(buffer);
                 //4.写到输出流(out)中 
                 out.write(buffer,0,b);
             }
             inputStream.close();
             out.close();
             out.flush();
         } catch (IOException e) {
         } finally {
         	if (out!=null) {
         		try {
             		out.close();
         		} catch(Exception e1) {
         		}
         		out=null;
         	}
         }
	}
	
	
	//查询结果导出excel
	@ResponseBody
	@RequestMapping(value = "/customer/customerMsgExcel")
	public String customerMsgExcel() throws Exception{
		String path = "";
		//总条数
		Integer sumTotal = 0;
		//导出时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date());
		//导出人
		String id = hashSession.getCurrentAdmin(request).getId();
		UserRole userRole = sysUserRoleService.selectByuserId(id);
		String roleid = userRole.getRoleid();
		SysRole role = sysRoleService.findById(roleid);
		String roleName = role.getRoleName();
		//用户
		UserInfo userInfo = userInfoService.findById(id);
		String realname = userInfo.getRealname();
		//
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> json = new HashMap<String, Object>();
		//所有条件
		String isValidate = getTrimParameter("isValidate");
		//首访接待开始时间
		String record01Begin = getTrimParameter("record01Begin");
		//首访接待结束时间
		String record01End = getTrimParameter("record01End");
		//复访接待开始时间
		String record02Begin = getTrimParameter("record02Begin");
		//复访接待结束时间
		String record02End = getTrimParameter("record02End");
		//成交接待开始时间
		String record03Begin = getTrimParameter("record03Begin");
		//成交接待结束时间
		String record03End = getTrimParameter("record03End");
		//年龄段
		String agegroup = getTrimParameter("agegroups");
		//认知渠道
		String knowway = getTrimParameter("knowways");
		//房屋面积
		String liveacreage = getTrimParameter("liveacreages");
		//接受价格区段
		String pricesection = getTrimParameter("pricesections");
		//客户评价
		String custscore = getTrimParameter("custscores");
		//名字
		String name = getTrimParameter("custName");
		map.put("isValidate", isValidate);
		try {
			if(name == null){
				map.put("name", "");
			}else{
				map.put("name", name);
			}
			map.put("record01Begin", record01Begin);
			map.put("record01End", record01End);
			map.put("record02Begin", record02Begin);
			map.put("record02End", record02End);
			map.put("record03Begin", record03Begin);
			map.put("record03End", record03End);
			map.put("agegroup", agegroup);
			map.put("knowway", knowway);
			map.put("liveacreage", liveacreage);
			map.put("pricesection", pricesection);
			map.put("custscore", custscore);
			// 获取所有用户信息
			List<Map<String,Object>> userMsg = new ArrayList<Map<String,Object>>();
			if(roleName.equals("管理员")) {
				map.put("userType", "1");
			}
			if(roleName.equals("项目负责人")) {
				String projIds = "";
				List<Map<String, Object>> projs = projUserRoleService.selectByUserId(id);
				for(Map<String, Object> proj : projs){
					projIds+=proj.get("id").toString()+",";
				}
				map.put("projIds", projIds);
				map.put("userType", "2");
			}
			if(roleName.equals("项目管理人")) {
				String projIds = "";
				List<Map<String, Object>> projs = projUserRoleService.selectByUserId(id);
				for(Map<String, Object> proj : projs){
					projIds+=proj.get("id").toString()+",";
				}
				map.put("projIds", projIds);
				map.put("userType", "3");
			}
			if(roleName.equals("顾问")) {
				map.put("userType", "4");
				map.put("userId", id);
			}
			userMsg=userCustRefService.downloadExcel(map);
			sumTotal=userMsg.size();
			// 第一步，创建一个webbook，对应一个Excel文件  
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        //样式
	        //设置表格样式
			HSSFCellStyle style = wb.createCellStyle();  
			//生成一个字体
			HSSFFont font=wb.createFont();
			font.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
			font.setFontHeightInPoints((short)12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
			//把字体应用到当前的样式
			style.setFont(font);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("客户查询结果");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        //第一行
	        HSSFRow row0 = sheet.createRow((int) 0);  
	        HSSFCell createCell = row0.createCell((short) 0);
	        createCell.setCellValue("客户查询结果");  
	        createCell.setCellStyle(style); 
	        sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
	        //第二行
	        HSSFRow row1 = sheet.createRow((int) 1);  
	        HSSFCell createCell1 = row1.createCell((short) 0);
	        createCell1.setCellValue("导出人:"+realname);
	        createCell1.setCellStyle(style); 
	        sheet.addMergedRegion(new CellRangeAddress(1,1,0,1));
	        //第二行第一列
	        HSSFCell createCell2 = row1.createCell((short) 4);
	        createCell2.setCellValue("导出时间:"+date);
	        createCell2.setCellStyle(style); 
	        //合并单元格
	        sheet.addMergedRegion(new CellRangeAddress(1,1,4,5));
	        //第四列
	        HSSFCell createCell3 = row1.createCell((short) 6);
	        createCell3.setCellValue("总条数:"+sumTotal);
	        createCell3.setCellStyle(style); 
	        //合并单元格
	        sheet.addMergedRegion(new CellRangeAddress(1,1,6,8));
	        //五列及以后
	        HSSFRow row = sheet.createRow((int) 2);
	        HSSFCell cell = row.createCell((short) 0);  
	        cell.setCellValue("客户姓名");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 1);  
	        cell.setCellValue("客户电话");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("性别");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("项目名称");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 4);  
	        cell.setCellValue("关注时间");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("顾问名称");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 6);  
	        cell.setCellValue("访问总次数");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 7);  
	        cell.setCellValue("年龄段");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 8);  
	        cell.setCellValue("是购房资格");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 9);  
	        cell.setCellValue("本地居住区");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 10);  
	        cell.setCellValue("本地工作区");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 11);  
	        cell.setCellValue("外阜居住区");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 12);  
	        cell.setCellValue("外阜工作区");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 13);  
	        cell.setCellValue("居住详细地址");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 14);  
	        cell.setCellValue("家庭状况");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 15);  
	        cell.setCellValue("实际居住情况");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 16);  
	        cell.setCellValue("出行方式");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 17);  
	        cell.setCellValue("出行方式描述");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 18);  
	        cell.setCellValue("从事行业");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 19);  
	        cell.setCellValue("从事行业描述");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 20);  
	        cell.setCellValue("公司名称");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 21);  
	        cell.setCellValue("公司地址");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 22);  
	        cell.setCellValue("公司职务");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 23);  
	        cell.setCellValue("企业性质");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 24);  
	        cell.setCellValue("企业性质描述");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 25);  
	        cell.setCellValue("关注产品类型");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 26);  
	        cell.setCellValue("关注产品类型描述");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 27);  
	        cell.setCellValue("关注面积");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 28);  
	        cell.setCellValue("接受价格区段");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 29);  
	        cell.setCellValue("购房目的");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 30);  
	        cell.setCellValue("购房目的描述");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 31);  
	        cell.setCellValue("预估身价");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 32);  
	        cell.setCellValue("重点投资");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 33);  
	        cell.setCellValue("重点投资描述");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 34);  
	        cell.setCellValue("小孩个数");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 35);  
	        cell.setCellValue("小孩年龄段");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 36);  
	        cell.setCellValue("小孩业余爱好");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 37);  
	        cell.setCellValue("小孩业余爱好描述");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 38);  
	        cell.setCellValue("孩子学校类型");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 39);  
	        cell.setCellValue("学校名称");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 40);  
	        cell.setCellValue("生活半径");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 41);  
	        cell.setCellValue("社区名称");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 42);  
	        cell.setCellValue("住房性质");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 43);  
	        cell.setCellValue("居住面积");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 44);  
	        cell.setCellValue("贷款记录");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 45);  
	        cell.setCellValue("全职太太");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 46);  
	        cell.setCellValue("国际教育意愿");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 47);  
	        cell.setCellValue("是否有保姆");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 48);  
	        cell.setCellValue("业主海外经历");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 49);  
	        cell.setCellValue("业主海外经历城市");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 50);  
	        cell.setCellValue("子女海外经历");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 51);  
	        cell.setCellValue("子女海外经历城市");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 52);  
	        cell.setCellValue("是否有宠物");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 53);  
	        cell.setCellValue("家庭汽车数量");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 54);  
	        cell.setCellValue("汽车品牌");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 55);  
	        cell.setCellValue("汽车总价");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 56);  
	        cell.setCellValue("名下房产数量");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 57);  
	        cell.setCellValue("关注微信号");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 58);  
	        cell.setCellValue("常用APP");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 59);  
	        cell.setCellValue("业余爱好");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 60);  
	        cell.setCellValue("业余爱好描述");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 61);  
	        cell.setCellValue("喜欢活动");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 62);  
	        cell.setCellValue("喜欢活动描述");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 63);  
	        cell.setCellValue("可参加活动时间");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 64);  
	        cell.setCellValue("户籍类型");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 65);  
	        cell.setCellValue("通邮地址");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 66);  
	        cell.setCellValue("客户评级");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 67);  
	        cell.setCellValue("客户描述");  
	        cell.setCellStyle(style);
	        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
	        //List list = CreateSimpleExcelToDisk.getStudent();  
	        for (int i = 0; i < userMsg.size(); i++)  
	        {  
	            row = sheet.createRow((int) i + 3);  
	            Map<String, Object> map2 = userMsg.get(i);  
	            Integer number = Integer.parseInt(map2.get("total").toString());
	            sumTotal += number;
	            
	            
	            // 第四步，创建单元格，并设置值  
	            row.createCell((short) 0).setCellValue(map2.get("custName").toString());  
	            row.createCell((short) 1).setCellValue(map2.get("phoneNum").toString());  
	            row.createCell((short) 2).setCellValue(map2.get("custSex").toString());  
	            row.createCell((short) 3).setCellValue(map2.get("projName").toString());  
	            cell = row.createCell((short) 4);  
	            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map2.get("cTime")));  
	            row.createCell((short) 5).setCellValue(map2.get("realName").toString());  
	            //总次数
	            row.createCell((short) 6).setCellValue(map2.get("total")==null?"":map2.get("total").toString()); 
	            //年龄段
	            row.createCell((short) 7).setCellValue(map2.get("ageGroup")==null?"":map2.get("ageGroup").toString()); 
	            //购房资格
	            row.createCell((short) 8).setCellValue(map2.get("buyQualify")==null?"":map2.get("buyQualify").toString()); 
	            //本地居住地
	            row.createCell((short) 9).setCellValue(map2.get("localResidence")==null?"":map2.get("localResidence").toString()); 
	            //本地工作地
	            row.createCell((short) 10).setCellValue(map2.get("localWorkArea")==null?"":map2.get("localWorkArea").toString()); 
	            //外阜居住地
	            row.createCell((short) 11).setCellValue(map2.get("outResidence")==null?"":map2.get("outResidence").toString()); 
	            //外阜工作地
	            row.createCell((short) 12).setCellValue(map2.get("outWorkArea")==null?"":map2.get("outWorkArea").toString()); 
	            //居住详细地址
	            row.createCell((short) 13).setCellValue(map2.get("custAddress")==null?"":map2.get("custAddress").toString()); 
	            //家庭状况
	            row.createCell((short) 14).setCellValue(map2.get("familyStatus")==null?"":map2.get("familyStatus").toString()); 
	            //实际居住情况
	            row.createCell((short) 15).setCellValue(map2.get("livingStatus")==null?"":map2.get("livingStatus").toString()); 
	            //出行方式
	            row.createCell((short) 16).setCellValue(map2.get("trafficType")==null?"":map2.get("trafficType").toString()); 
	            //出行方式描述
	            row.createCell((short) 17).setCellValue(map2.get("trafficTypeDesc")==null?"":map2.get("trafficTypeDesc").toString()); 
	            //从事行业
	            row.createCell((short) 18).setCellValue(map2.get("workIndustry")==null?"":map2.get("workIndustry").toString()); 
	            //从事行业描述
	            row.createCell((short) 19).setCellValue(map2.get("workIndustryDesc")==null?"":map2.get("workIndustryDesc").toString()); 
	            //公司名称
	            row.createCell((short) 20).setCellValue(map2.get("enterpriseName")==null?"":map2.get("enterpriseName").toString()); 
	            //公司地址
	            row.createCell((short) 21).setCellValue(map2.get("enterpriseAddress")==null?"":map2.get("enterpriseAddress").toString()); 
	            //公司职务
	            row.createCell((short) 22).setCellValue(map2.get("enterprisePost")==null?"":map2.get("enterprisePost").toString()); 
	            //企业性质
	            row.createCell((short) 23).setCellValue(map2.get("enterpriseType")==null?"":map2.get("enterpriseType").toString()); 
	            //企业性质描述
	            row.createCell((short) 24).setCellValue(map2.get("enterpriseTypeDesc")==null?"":map2.get("enterpriseTypeDesc").toString()); 
	            //关注产品类型
	            row.createCell((short) 25).setCellValue(map2.get("realtyProductType")==null?"":map2.get("realtyProductType").toString()); 
	            //关注产品类型描述
	            row.createCell((short) 26).setCellValue(map2.get("realtyProductTypeDesc")==null?"":map2.get("realtyProductTypeDesc").toString()); 
	            //关注产面积
	            row.createCell((short) 27).setCellValue(map2.get("attentAcreage")==null?"":map2.get("attentAcreage").toString()); 
	            //接受价格区段
	            row.createCell((short) 28).setCellValue(map2.get("priceSection")==null?"":map2.get("priceSection").toString()); 
	            //购房目的
	            row.createCell((short) 29).setCellValue(map2.get("buyPurpose")==null?"":map2.get("buyPurpose").toString()); 
	            //购房目的描述
	            row.createCell((short) 30).setCellValue(map2.get("buyPurposeDesc")==null?"":map2.get("buyPurposeDesc").toString()); 
	            //预估身价
	            row.createCell((short) 31).setCellValue(map2.get("estCustWorth")==null?"":map2.get("estCustWorth").toString()); 
	            //重点投资
	            row.createCell((short) 32).setCellValue(map2.get("investType")==null?"":map2.get("investType").toString()); 
	            //重点投资描述
	            row.createCell((short) 33).setCellValue(map2.get("investTypeDesc")==null?"":map2.get("investTypeDesc").toString()); 
	            //小孩个数
	            row.createCell((short) 34).setCellValue(map2.get("childrenNum")==null?"":map2.get("childrenNum").toString()); 
	            //小孩年龄段
	            row.createCell((short) 35).setCellValue(map2.get("childAgeGroup")==null?"":map2.get("childAgeGroup").toString()); 
	            //小孩业余爱好
	            row.createCell((short) 36).setCellValue(map2.get("childAvocations")==null?"":map2.get("childAvocations").toString()); 
	            //小孩业余爱好描述
	            row.createCell((short) 37).setCellValue(map2.get("childAvocationsDesc")==null?"":map2.get("childAvocationsDesc").toString()); 
	            //孩子学校类型
	            row.createCell((short) 38).setCellValue(map2.get("schoolType")==null?"":map2.get("schoolType").toString()); 
	            //学校名称
	            row.createCell((short) 39).setCellValue(map2.get("schoolName")==null?"":map2.get("schoolName").toString()); 
	            //生活半径
	            row.createCell((short) 40).setCellValue(map2.get("livingRadius")==null?"":map2.get("livingRadius").toString()); 
	            //社区名称
	            row.createCell((short) 41).setCellValue(map2.get("communityName")==null?"":map2.get("communityName").toString()); 
	            //住房性质
	            row.createCell((short) 42).setCellValue(map2.get("houseType")==null?"":map2.get("houseType").toString()); 
	            //居住面积
	            row.createCell((short) 43).setCellValue(map2.get("liveAcreage")==null?"":map2.get("liveAcreage").toString()); 
	            //贷款记录
	            row.createCell((short) 44).setCellValue(map2.get("loanStatus")==null?"":map2.get("loanStatus").toString()); 
	            //全职太太
	            row.createCell((short) 45).setCellValue(map2.get("fulltimeWifeFlag")==null?"":map2.get("fulltimeWifeFlag").toString()); 
	            //国际教育意愿
	            row.createCell((short) 46).setCellValue(map2.get("outEduWill")==null?"":map2.get("outEduWill").toString()); 
	            //是否有保姆
	            row.createCell((short) 47).setCellValue(map2.get("nannyFlag")==null?"":map2.get("nannyFlag").toString()); 
	            //业主海外经历
	            row.createCell((short) 48).setCellValue(map2.get("outExperFlag")==null?"":map2.get("outExperFlag").toString()); 
	            //业主海外经历城市
	            row.createCell((short) 49).setCellValue(map2.get("outExperCity")==null?"":map2.get("outExperCity").toString()); 
	            //子女外省经历
	            row.createCell((short) 50).setCellValue(map2.get("childOutExperFlag")==null?"":map2.get("childOutExperFlag").toString()); 
	            //子女外省经历城市
	            row.createCell((short) 51).setCellValue(map2.get("childOutExperCity")==null?"":map2.get("childOutExperCity").toString()); 
	            //是否有宠物
	            row.createCell((short) 52).setCellValue(map2.get("petFlag")==null?"":map2.get("petFlag").toString()); 
	            //家庭汽车数据量
	            row.createCell((short) 53).setCellValue(map2.get("carFamilyCount")==null?"":map2.get("carFamilyCount").toString()); 
	            //汽车品牌
	            row.createCell((short) 54).setCellValue(map2.get("carBrand")==null?"":map2.get("carBrand").toString()); 
	            //汽车总价
	            row.createCell((short) 55).setCellValue(map2.get("carTotalPrice")==null?"":map2.get("carTotalPrice").toString()); 
	            //名下房产数量
	            row.createCell((short) 56).setCellValue(map2.get("houseCount")==null?"":map2.get("houseCount").toString()); 
	            //关注微信号
	            row.createCell((short) 57).setCellValue(map2.get("attentWX")==null?"":map2.get("attentWX").toString()); 
	            //常用APP
	            row.createCell((short) 58).setCellValue(map2.get("appNames")==null?"":map2.get("appNames").toString()); 
	            //业余爱好
	            row.createCell((short) 59).setCellValue(map2.get("avocations")==null?"":map2.get("avocations").toString()); 
	            //业余爱好描述
	            row.createCell((short) 60).setCellValue(map2.get("avocationsDesc")==null?"":map2.get("avocationsDesc").toString()); 
	            //喜欢活动
	            row.createCell((short) 61).setCellValue(map2.get("loveActivation")==null?"":map2.get("loveActivation").toString()); 
	            //喜欢活动描述
	            row.createCell((short) 62).setCellValue(map2.get("lovActDesc")==null?"":map2.get("lovActDesc").toString()); 
	            //可参加活动时间
	            row.createCell((short) 63).setCellValue(map2.get("freeTimeSection")==null?"":map2.get("freeTimeSection").toString()); 
	            //户籍类型
	            row.createCell((short) 64).setCellValue(map2.get("houseRegiType")==null?"":map2.get("houseRegiType").toString()); 
	            //通邮地址
	            row.createCell((short) 65).setCellValue(map2.get("addressMail")==null?"":map2.get("addressMail").toString()); 
	            //客户评级
	            row.createCell((short) 66).setCellValue(map2.get("custScore")==null?"":map2.get("custScore").toString()); 
	            //客户描述
	            row.createCell((short) 67).setCellValue(map2.get("custDescn")==null?"":map2.get("custDescn").toString()); 
	        
	        }  
	         sheet.autoSizeColumn((short)0); //调整第一列宽度
	         sheet.autoSizeColumn((short)1); //调整第二列宽度
	         sheet.autoSizeColumn((short)2); //调整第三列宽度
	         sheet.autoSizeColumn((short)3); //调整第四列宽度
	         sheet.autoSizeColumn((short)4); //调整第四列宽度
	         sheet.autoSizeColumn((short)5); //调整第四列宽度
	         
	         // 第六步，将文件存到指定位置  
	         long time = new Date().getTime();
	         String fileName = time+"cust.xls";
	         //path="D:\\excels\\"+fileName;
	         path="/opt/tomcat/webapps/ROOT/excels/"+fileName;
	         FileOutputStream fout = new FileOutputStream(path);
	         wb.write(fout);  
	         fout.close();  
	         json.put("msg", "100");
	         json.put("path", path);
		} catch (Exception e) {
			json.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(json);
	}
	
	
	
	//导入客户数据
	@RequestMapping(value = "/customer/toLead")
    public String  fileUpload(@RequestParam("file") CommonsMultipartFile file,String userProjId) throws Exception{
        Map<String,Object>map=new HashMap<String,Object>();
        try {
        	//用来检测程序运行时间
            long  startTime=System.currentTimeMillis();
            String originalFilename = file.getOriginalFilename();
            System.out.println("fileName："+originalFilename);
            //文件流
            InputStream is=file.getInputStream();
            //工作薄
            Sheet sheet=null;
            //判断是2003还是2007
            if (originalFilename != null) {  
                int index = originalFilename.indexOf(".");  
                String suffex = originalFilename.substring(index);  
                if (".xls".equals(suffex)) {  
                	POIFSFileSystem fs = new POIFSFileSystem(is);  
                    HSSFWorkbook workbook = new HSSFWorkbook(fs);  
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
                    	//获取工作溥
                    	sheet = workbook.getSheetAt(i);
                    	String sheetName = sheet.getSheetName();
                    	if(sheetName.equals("来电")){
                    		//来电
                    		incomingTelegram(userProjId,sheet);
                    	}else if(sheetName.equals("来访")){
                    		//来访
                    		comeRound(userProjId,sheet);
                    	}
                    }
                } else if (".xlsx".equals(suffex)) {  
                	XSSFWorkbook xwb = new XSSFWorkbook(is);  
                	for (int i = 0; i < xwb.getNumberOfSheets(); i++) {//获取每个Sheet表
                    	//获取工作溥
                    	sheet = xwb.getSheetAt(i);
                    	String sheetName = sheet.getSheetName();
                    	if(sheetName.equals("来电")){
                    		//来电
                    		incomingTelegram(userProjId,sheet);
                    	}else if(sheetName.equals("来访")){
                    		//来访
                    		comeRound(userProjId,sheet);
                    	}
                    }
                }  
            }  
        }catch(Exception e){
        	e.printStackTrace();
        }
        return "redirect:customerList";
    }


	//来电
	public Boolean incomingTelegram(String userProjId,Sheet sheet) {
		Boolean isok = false;
		// 对读取Excel表格标题测试
        ExcelReader excelReader = new ExcelReader();
		try{
            Map<Integer, String> map = excelReader.readExcelContent(sheet);
            System.out.println("获得Excel表格的内容:");
            for (int i = 1; i <= map.size(); i++) {
            	Customer customer = new Customer();
            	AccessRecord01 accessRecord01 = new AccessRecord01();
            	//Map<String,Object> parameterMap = new HashMap<String,Object>();
                System.out.println(map.get(i).length());
                System.out.println(map.get(i));
                if(i>1){
                	String msgs = map.get(i);
                	String[] split = msgs.split("    ");
                	int length = split.length;
                	//顾问姓名
        			String userName ="";
                	for(int index=0;index<length;index++){
                		//年
                		String year = split[1];
                		//时间
                		String time = split[2];
                		//月
                		String month = split[3];
                		//日
                		String day = split[4];
                		//最终时间
                		String dateTime = year+"-"+month+"-"+day+" 00:00:00";
        				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        				Date parse = formata.parse(dateTime);				
                		accessRecord01.setReceptime(parse);
                		if(index==5){
                			//客户姓名
                			String custName = split[5];
                			if(StringUtils.isEmpty(custName)){
                				customer.setCustname("");
                				accessRecord01.setCustname("");
                			}else{
                				customer.setCustname(custName);
                				accessRecord01.setCustname(custName);
                			}
                		}
                		if(index==6){
                			//客户电话
                			String phoneNum = split[6];
                			if(StringUtils.isEmpty(phoneNum)){
                				customer.setPhonenum("");
                				accessRecord01.setCustphonenum("");
                			}else {
                				//查询客户表是否已有该客户
                    			Integer number = customerService.selectByCustPhoneNum(phoneNum);
                    			if(number > 0) break;
                    			customer.setPhonenum(phoneNum);
                				accessRecord01.setCustphonenum(phoneNum);
                				//设置主键
                				String custId = keyGen.getUUIDKey();
                            	accessRecord01.setId(keyGen.getUUIDKey());
                            	customer.setId(custId);
                            	accessRecord01.setProjid(userProjId);
                            	accessRecord01.setCustid(custId);
                			}
                		}
                		if(index==7){
                			//顾问姓名
                			userName = split[7];
            				if(StringUtils.isEmpty(userName)){
                				accessRecord01.setAuthorid("");
                				accessRecord01.setCreatorid("");
            				}else{
            					//查询用户表是否有该顾问
            					List<UserInfo> userInfos = userInfoService.selectByName(userName);
            					int size = userInfos.size();
            					//获取顾问ID
            					if(size==1){
            						String id = userInfos.get(0).getId();
            						accessRecord01.setAuthorid(id);
            						accessRecord01.setCreatorid(id);
            					}else{
            						accessRecord01.setAuthorid("");
            						accessRecord01.setCreatorid("");
            					}
            				}
                		}
                		if(index==10){
                			//客户级别
                			String custScore = split[10];
            				if(StringUtils.isEmpty(custScore)){
                				accessRecord01.setCustscore("");
                				customer.setCustscore("");
            				}else{
            					accessRecord01.setCustscore(custScore);
            					customer.setCustscore(custScore);
            				}
                		}
                		if(index==11){
                			//户籍
                			String huji = split[11];
            				if(StringUtils.isEmpty(huji)){
            					
            				}else{
            					if(huji.equals("本市")){
            						if(length>=17){
            							accessRecord01.setLocalresidence(split[17]);
                						customer.setLocalresidence(split[17]);
            						}
            						if(length>=18){
            							accessRecord01.setLocalworkarea(split[18]);
                						customer.setLocalworkarea(split[18]);
            						}
            					}else{
            						if(length>=17){
            							accessRecord01.setOutresidence(split[17]);
                						customer.setOutresidence(split[17]);
            						}
            						if(length>=18){
            							accessRecord01.setOutworkarea(split[18]);
                						customer.setOutworkarea(split[18]);
            						}
            					}
            				}
                		}
                		if(index==13){
                			//意向面积
                			String attentAcreage = split[13];
            				if(StringUtils.isEmpty(attentAcreage)){
                				accessRecord01.setAttentacreage("");
                				customer.setAttentacreage("");
            				}else{
            					accessRecord01.setAttentacreage(attentAcreage);
                				customer.setAttentacreage(attentAcreage);
            				}
                		}
                		if(index==16){
                			//意向总价
                			String priceSection = split[16];
            				if(StringUtils.isEmpty(priceSection)){
                				accessRecord01.setPricesection("");
                				customer.setPricesection("");
            				}else{
            					accessRecord01.setPricesection(priceSection);
                				customer.setPricesection(priceSection);
            				}
                		}
                		if(index==20){
                			//渠道来源
                			String knowWay = split[20];
            				if(StringUtils.isEmpty(knowWay)){
                				accessRecord01.setKnowway("");
            				}else{
            					accessRecord01.setKnowway(knowWay);
            				}
                		}
                	}
                	//状态
                	accessRecord01.setStatus(2);
                	//首访客户性别
                	accessRecord01.setCustsex("");
                	//客户表客户性别
                	customer.setCustsex("");
                	addRelationTbl(customer,accessRecord01,userProjId,userName);
                }
            }
            isok=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return isok;
	}
	
	//来访
	public Boolean comeRound(String userProjId,Sheet sheet) {
		Boolean isok = false;
		// 对读取Excel表格标题测试
        ExcelReader excelReader = new ExcelReader();
		try{
            Map<Integer, String> map = excelReader.readExcelContent(sheet);
            System.out.println("获得Excel表格的内容:");
            for (int i = 1; i <= map.size(); i++) {
            	Customer customer = new Customer();
            	AccessRecord01 accessRecord01 = new AccessRecord01();
            	//Map<String,Object> parameterMap = new HashMap<String,Object>();
                System.out.println(map.get(i).length());
                System.out.println(map.get(i));
                if(i>1){
                	String msgs = map.get(i);
                	String[] split = msgs.split("    ");
                	int length = split.length;
                	//顾问姓名
            		String userName="";
                	for(int index=0;index<length;index++){
                		//年
                		String year = split[1];
                		//时间
                		String time = split[2];
                		//月
                		String month = split[3];
                		//日
                		String day = split[4];
                		//最终时间
                		String dateTime = year+"-"+month+"-"+day+" 00:00:00";
        				SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        				Date parse = formata.parse(dateTime);				
                		accessRecord01.setReceptime(parse);
                		if(index==5){
                			//客户姓名
                			String custName = split[5];
                			if(StringUtils.isEmpty(custName)){
                				customer.setCustname("");
                				accessRecord01.setCustname("");
                			}else{
                				customer.setCustname(custName);
                				accessRecord01.setCustname(custName);
                			}
                		}
                		if(index==6){
                			//客户电话
                			String phoneNum = split[6];
                			if(StringUtils.isEmpty(phoneNum)){
                				customer.setPhonenum("");
                				accessRecord01.setCustphonenum("");
                			}else {
                				//查询客户表是否已有该客户
                    			Integer number = customerService.selectByCustPhoneNum(phoneNum);
                    			if(number > 0) break;
                    			customer.setPhonenum(phoneNum);
                				accessRecord01.setCustphonenum(phoneNum);
                				//设置主键
                				String custId = keyGen.getUUIDKey();
                            	accessRecord01.setId(keyGen.getUUIDKey());
                            	customer.setId(custId);
                            	accessRecord01.setProjid(userProjId);
                            	accessRecord01.setCustid(custId);
                			}
                		}
                		if(index==7){
                			//顾问姓名
                			userName = split[7];
            				if(StringUtils.isEmpty(userName)){
                				accessRecord01.setAuthorid("");
                				accessRecord01.setCreatorid("");
            				}else{
            					//查询用户表是否有该顾问
            					List<UserInfo> userInfos = userInfoService.selectByName(userName);
            					int size = userInfos.size();
            					//获取顾问ID
            					if(size==1){
            						String id = userInfos.get(0).getId();
            						accessRecord01.setAuthorid(id);
            						accessRecord01.setCreatorid(id);
            					}else{
            						accessRecord01.setAuthorid("");
            						accessRecord01.setCreatorid("");
            					}
            				}
                		}
                		if(index==10){
                			//客户级别
                			String custScore = split[10];
            				if(StringUtils.isEmpty(custScore)){
                				accessRecord01.setCustscore("");
                				customer.setCustscore("");
            				}else{
            					accessRecord01.setCustscore(custScore);
            					customer.setCustscore(custScore);
            				}
                		}
                		if(index==12){
                			//户籍
                			String huji = split[12];
            				if(StringUtils.isEmpty(huji)){
            					
            				}else{
            					if(huji.equals("本市")){
            						if(length>=29){
            							accessRecord01.setLocalresidence(split[29]);
                						customer.setLocalresidence(split[29]);
            						}
            						if(length>=30){
            							accessRecord01.setLocalworkarea(split[30]);
                						customer.setLocalworkarea(split[30]);
            						}
            					}else{
            						if(length>=29){
            							accessRecord01.setOutresidence(split[29]);
                						customer.setOutresidence(split[29]);
            						}
            						if(length>=30){
            							accessRecord01.setOutworkarea(split[30]);
                						customer.setOutworkarea(split[30]);
            						}
            					}
            				}
                		}
                		if(index==14){
                			//置业目的
                			String buyPurpose = split[14];
            				if(StringUtils.isEmpty(buyPurpose)){
                				accessRecord01.setBuypurpose("");
                				customer.setBuypurpose("");
            				}else{
            					accessRecord01.setBuypurpose(buyPurpose);
                				customer.setBuypurpose(buyPurpose);
            				}
                		}
                		if(index==19){
                			//意向面积
                			String attentAcreage = split[19];
            				if(StringUtils.isEmpty(attentAcreage)){
                				accessRecord01.setAttentacreage("");
                				customer.setAttentacreage("");
            				}else{
            					accessRecord01.setAttentacreage(attentAcreage);
                				customer.setAttentacreage(attentAcreage);
            				}
                		}
                		if(index==22){
                			//意向总价
                			String priceSection = split[22];
            				if(StringUtils.isEmpty(priceSection)){
                				accessRecord01.setPricesection("");
                				customer.setPricesection("");
            				}else{
            					accessRecord01.setPricesection(priceSection);
                				customer.setPricesection(priceSection);
            				}
                		}
                		if(index==27){
                			//渠道来源
                			String knowWay = split[27];
            				if(StringUtils.isEmpty(knowWay)){
                				accessRecord01.setKnowway("");
            				}else{
            					accessRecord01.setKnowway(knowWay);
            				}
                		}
                		if(index==32){
                			//从事行业
                			String workIndustry = split[32];
            				if(StringUtils.isEmpty(workIndustry)){
                				accessRecord01.setWorkindustry("");
                				customer.setWorkindustry("");
            				}else{
            					accessRecord01.setWorkindustry(workIndustry);
                				customer.setWorkindustry(workIndustry);
            				}
                		}
                		if(index==33){
                			//行业性质
                			String enterpriseType = split[33];
            				if(StringUtils.isEmpty(enterpriseType)){
                				accessRecord01.setEnterprisetype("");
                				customer.setEnterprisetype("");
            				}else{
            					accessRecord01.setEnterprisetype(enterpriseType);
                				customer.setEnterprisetype(enterpriseType);
            				}
                		}
                		if(index==35){
                			//年龄段
                			String ageGroup = split[35];
            				if(StringUtils.isEmpty(ageGroup)){
                				accessRecord01.setAgegroup("");
                				customer.setAgegroup("");
            				}else{
            					accessRecord01.setAgegroup(ageGroup);
                				customer.setAgegroup(ageGroup);
            				}
                		}
                		if(index==36){
                			//家族状况
                			String familyStatus = split[36];
            				if(StringUtils.isEmpty(familyStatus)){
                				accessRecord01.setFamilystatus("");
                				customer.setFamilystatus("");
            				}else{
            					accessRecord01.setFamilystatus(familyStatus);
                				customer.setFamilystatus(familyStatus);
            				}
                		}
                	}
                	//状态
                	accessRecord01.setStatus(2);
                	//首访客户性别
                	accessRecord01.setCustsex("");
                	//客户表客户性别
                	customer.setCustsex("");
                	addRelationTbl(customer,accessRecord01,userProjId,userName);
                }
            }
            isok=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return isok;
	}
	
	
	
	
	
	//通用添加关系表
	public Boolean addRelationTbl(Customer customer, AccessRecord01 accessRecord01, String userProjId, String userName){
		Boolean isok = false;
		try {
			//添加
			if(StringUtils.isNotEmpty(customer.getId())) customerService.insert(customer);
			if(StringUtils.isNotEmpty(accessRecord01.getId())) accessRecord01Service.insert(accessRecord01);
			//添加项目客户关系表
			if(StringUtils.isNotEmpty(customer.getId())){
				Map<String,Object> data = new HashMap<String,Object>();
				data.put("projId", userProjId);
				data.put("custId", customer.getId());
				ProjCustRef projCustRefMsg = projCustRefService.selectByCusIdAndProjId(data);
				if(projCustRefMsg==null){
					ProjCustRef projCustRef = new ProjCustRef();
					projCustRef.setId(keyGen.getUUIDKey());
					projCustRef.setProjid(userProjId);
					projCustRef.setCustid(customer.getId());
					projCustRef.setKnowway(accessRecord01.getKnowway());
					projCustRefService.insert(projCustRef);
				}
				
			}
			//添加客户用户关系表
			if(StringUtils.isNotEmpty(customer.getId()) && 
					StringUtils.isNotEmpty(accessRecord01.getAuthorid())){
				Map<String,Object> data = new HashMap<String,Object>();
				data.put("projId", userProjId);
				data.put("custId", customer.getId());
				data.put("userId", accessRecord01.getAuthorid());
				UserCustRef userCustRefMsg = userCustRefService.selectByData(data);
				if(userCustRefMsg==null){
					UserCustRef userCustRef = new UserCustRef();
					userCustRef.setId(keyGen.getUUIDKey());
					userCustRef.setProjid(userProjId);
					userCustRef.setUserid(accessRecord01.getAuthorid());
					userCustRef.setCustid(customer.getId());
					userCustRefService.insert(userCustRef);
				}
			}
			//添加特殊表（未来的客户和顾问关系表）
			if(StringUtils.isEmpty(accessRecord01.getAuthorid())&&
					StringUtils.isNotEmpty(accessRecord01.getCustphonenum())&&
					StringUtils.isNotEmpty(accessRecord01.getCustname())&&
					StringUtils.isNotEmpty(customer.getPhonenum())){
				ImportMapUserCust importMapUserCust = new ImportMapUserCust();
				importMapUserCust.setId(keyGen.getUUIDKey());
				importMapUserCust.setUsername(userName);
				importMapUserCust.setUserphonenum(accessRecord01.getCustphonenum());
				importMapUserCust.setCustname(customer.getCustname());
				importMapUserCust.setCustphonenum(customer.getPhonenum());
				importMapUserCust.setReporttype(1);
				importMapUserCust.setReportid(accessRecord01.getId());
				importMapUserCustService.insert(importMapUserCust);
			}
			isok = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isok;
	}
	
	
	
	
	
	//获取登入用户所对应的项目
	@ResponseBody
	@RequestMapping(value = "/customer/getProj")
	public String getProj() {
		Map<String, Object> map = new HashMap<String, Object>();
		//用户角色权限信息
		try {
			//返回数据结果
			List<Map<String, Object>> resultMsg = new ArrayList<Map<String, Object>>();
			String id = hashSession.getCurrentAdmin(request).getId();
			UserRole userRole = sysUserRoleService.selectByUserId(id);
			String roleId = userRole.getRoleid();
			SysRole role;
			role = sysRoleService.findById(roleId);
			String roleName = role.getRoleName();
			if(roleName.equals("管理员")) {
				resultMsg = projectService.selectAll();
			}
			if(roleName.equals("项目负责人")) {
				resultMsg = projUserRoleService.selectByUserId(id);
			}
			map.put("msg", "100");
			map.put("resultMsg", resultMsg);
		} catch (Exception e) {
			map.put("msg", "103");
			e.printStackTrace();
		}
		return JsonUtils.map2json(map);
	}
	
	
	
	
	
	
	
	// 综合查询
	@RequestMapping(value = "/customer/integratedQuery")
	public String integratedQuery(@RequestParam(value="nowPage",defaultValue="1") int nowPage,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize,ModelMap model) {
		Map<String, Object> map = new HashMap<String, Object>();
		//所有条件
		//首访接待开始时间
		String record01Begin = getTrimParameter("record01Begin");
		//首访接待结束时间
		String record01End = getTrimParameter("record01End");
		//复访接待开始时间
		String record02Begin = getTrimParameter("record02Begin");
		//复访接待结束时间
		String record02End = getTrimParameter("record02End");
		//成交接待开始时间
		String record03Begin = getTrimParameter("record03Begin");
		//成交接待结束时间
		String record03End = getTrimParameter("record03End");
		//年龄段
		String agegroup = getTrimParameter("agegroups");
		//认知渠道
		String knowway = getTrimParameter("knowways");
		//房屋面积
		String liveacreage = getTrimParameter("liveacreages");
		//接受价格区段
		String pricesection = getTrimParameter("pricesections");
		//客户评价
		String custscore = getTrimParameter("custscores");
		//纪录总数
		Integer listMessgeCount = 0;
		String name = getTrimParameter("custName");
		Integer start = ((nowPage - 1) * pageSize);
		map.put("page", start);
		map.put("pageSize", pageSize);
		String pageUrl = "inquiryStatistics/synthesize/list";
		try {
			//用户角色权限信息
			String id = hashSession.getCurrentAdmin(request).getId();
			UserRole userRole = sysUserRoleService.selectByUserId(id);
			String roleId = userRole.getRoleid();
			SysRole role = sysRoleService.findById(roleId);
			String roleName = role.getRoleName();
			if(name == null){
				map.put("name", "");
			}else{
				map.put("name", name);
			}
			map.put("record01Begin", record01Begin);
			map.put("record01End", record01End);
			map.put("record02Begin", record02Begin);
			map.put("record02End", record02End);
			map.put("record03Begin", record03Begin);
			map.put("record03End", record03End);
			map.put("agegroup", agegroup);
			map.put("knowway", knowway);
			map.put("liveacreage", liveacreage);
			map.put("pricesection", pricesection);
			map.put("custscore", custscore);
			if(roleName.equals("管理员")) {
				map.put("userType", "1");
			}
			if(roleName.equals("项目负责人")) {
				String projIds = "";
				List<Map<String, Object>> projs = projUserRoleService.selectByUserId(id);
				for(Map<String, Object> proj : projs){
					projIds+=proj.get("id").toString()+",";
				}
				map.put("projIds", projIds);
				map.put("userType", "2");
			}
			if(roleName.equals("项目管理人")) {
				String projIds = "";
				List<Map<String, Object>> projs = projUserRoleService.selectByUserId(id);
				for(Map<String, Object> proj : projs){
					projIds+=proj.get("id").toString()+",";
				}
				map.put("projIds", projIds);
				map.put("userType", "3");
			}
			if(roleName.equals("顾问")) {
				map.put("userType", "4");
				map.put("userId", id);
			}
			// 获取所有用户信息
			List<Map<String,Object>> userMsg = userCustRefService.selectZongHe(map);
			//所有信息数量
			listMessgeCount = userCustRefService.selectZongHeCount(map);
		 	Integer totalCount = listMessgeCount%pageSize;
			Integer totalPageNum = 0;
			if(totalCount==0){
				totalPageNum = listMessgeCount/pageSize;
			}else{
				totalPageNum = (listMessgeCount/pageSize)+1;
			}
			model.put("nowPage", nowPage);
			model.put("totalPageNum", totalPageNum);
			model.addAttribute("userMsg", userMsg);
			model.addAttribute("role", role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserRole userRole = sysUserRoleService.selectByUserId(hashSession.getCurrentAdmin(request).getId());
		List<SysItemRole> lst = sysItemRoleDao.selectItemByRoleId(userRole.getRoleid());
		List<SysItemRole> item = sysItemRoleDao.selectItemByPId(userRole.getRoleid());
		model.addAttribute("itemNamesss", item);
		model.addAttribute("lst", lst);
		String itemId = super.getTrimParameter("itemId");
		String id = super.getTrimParameter("id");
		model.addAttribute("itemId", itemId);
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		//查询条件
		model.addAttribute("record01Begin", record01Begin);
		model.addAttribute("record01End", record01End);
		model.addAttribute("record02Begin", record02Begin);
		model.addAttribute("record02End", record02End);
		model.addAttribute("record03Begin", record03Begin);
		model.addAttribute("record03End", record03End);
		model.addAttribute("agegroup", agegroup);
		model.addAttribute("knowway", knowway);
		model.addAttribute("liveacreage", liveacreage);
		model.addAttribute("pricesection", pricesection);
		model.addAttribute("custscore", custscore);
		return pageUrl;
	}
	
	
}
