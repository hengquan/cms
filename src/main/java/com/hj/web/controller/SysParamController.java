package com.hj.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hj.common.ControllerBase;

@Controller
@RequestMapping("/sys_param")
public class SysParamController extends ControllerBase{

	/*@Autowired
	private SysParamService sysParamService;
	@Autowired
	private IKeyGen keyGen;
	
	*//**
	 * 查看所有系统参数
	 * @param model
	 * sysParamList
	 *//*
	@RequestMapping("/list")
	public String list(Model model){
		List<SysParam> sysParamList = sysParamService.findSysParamList();
		model.addAttribute("sysParamList", sysParamList);
		String itemId = super.getTrimParameter("itemId");
	    String id = super.getTrimParameter("id");
	    model.addAttribute("itemId",itemId);
	    model.addAttribute("id",id);
		return "sysparam/list";
	}
	
	
	*//**
	 * 添加系统参数
	 * @param sysParam
	 * @return json
	 *//*
	@RequestMapping("/add_update")
	@ResponseBody
	public String addAndUpdateSysParam(){
		String paramName = StringUtils.trimToEmpty(getTrimParameter("paramName"));
		String paramCode = StringUtils.trimToEmpty(getTrimParameter("paramCode"));
		String paramVal = StringUtils.trimToEmpty(getTrimParameter("paramVal"));
		String remark = StringUtils.trimToEmpty(getTrimParameter("remark"));
		String id = StringUtils.trimToEmpty(getTrimParameter("id"));
		
		SysParam sysParam =getSysParam(paramName,paramCode,paramVal,remark,id);
		if(sysParam.getId() != null && !sysParam.getId().equals("")){
			boolean flag = validateParamCode(sysParam.getParamCode(),sysParam.getId());
			if(flag){				
				sysParamService.updateSysParam(sysParam);
				return "{\"code\":1}";
			}else{
				return "{\"code\":2,\"message\":\"操作失败,编码重复\"}";
			}
		}else{
			boolean flag = validateParamCode(sysParam.getParamCode());
			if(flag){
				sysParam.setId(keyGen.getUUIDKey());
				sysParamService.addSysParam(sysParam);
				return "{\"code\":1}";
			}else{
				return "{\"code\":2,\"message\":\"操作失败,编码重复\"}";
			}
		}
	
	}
	

	*//**
	 * 
	 * @param id
	 * @return 根据id删除系统参数
	 *//*
	@RequestMapping("/remove_sys_param")
	@ResponseBody
	public String removeSysParam(){
		String id = StringUtils.trimToEmpty(getTrimParameter("id"));
		try {
			sysParamService.removeParams(id);
		} catch (Exception e) {
			return "{\"code\":2,\"message\":\"删除失败\"}";
		}
		return "{\"code\":1}";
	}
	
	
	@RequestMapping("/add_and_update")
	public String sysParamAddAndUpdate(@RequestParam(value="editId",defaultValue="") String id,Model model){
		if(id != null && !id.equals("")){
			SysParam sysParam = sysParamService.findSysParamById(id);
			model.addAttribute("sysParam", sysParam);
		}		
		return "sysparam/add_update";		
	}
	
	
	 * 验证参数编码是否重复
	 
	public boolean validateParamCode(String paramCode,String id){
		List<SysParam> sysParamList = sysParamService.findSysParamList();
		SysParam sysParam = sysParamService.findSysParamById(id);
		for(SysParam str : sysParamList){
			if((sysParam == null ? "":sysParam.getParamCode()).equals(paramCode)){
				return true;
			}else{
				if(str.getParamCode().equals(paramCode)){
					return false;
				}
			}
			
		}
		return true;
	}
	
	public boolean validateParamCode(String paramCode){
		List<SysParam> sysParamList = sysParamService.findSysParamList();
		for(SysParam str : sysParamList){
				if(str.getParamCode().equals(paramCode)){
					return false;
				}
			
		}
		return true;
	}
	
	public SysParam getSysParam(String paramName,String paramCode,String paramVal,String remark,String id){
		SysParam sysParam = new SysParam();
		sysParam.setParamCode(paramCode);
		sysParam.setParamName(paramName);
		sysParam.setParamVal(paramVal);
		sysParam.setRemark(remark);
		sysParam.setId(id);
		return sysParam;
	}*/
	
}
