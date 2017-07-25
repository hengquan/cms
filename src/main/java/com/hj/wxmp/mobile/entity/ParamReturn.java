package com.hj.wxmp.mobile.entity;

import java.util.List;
import java.util.Map;

/**   
* @Title: Send.java
* @Package com.wzy.domain
* @Description: TODO
* @author Wangzhiyong   
* @date 2016年8月1日 下午3:57:48
* @version V1.0   
*/
public class ParamReturn {
	
	private String CODE;
	private String FUNC_ID;
	private String PARANUM;
	private List<ParamValue> PARADETAILS;
	private Map<String,Object> ZY;
	private Map<String,Object> ZS;
	
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String cODE) {
		CODE = cODE;
	}
	public String getFUNC_ID() {
		return FUNC_ID;
	}
	public void setFUNC_ID(String fUNC_ID) {
		FUNC_ID = fUNC_ID;
	}
	public String getPARANUM() {
		return PARANUM;
	}
	public void setPARANUM(String pARANUM) {
		PARANUM = pARANUM;
	}
	
	public List<ParamValue> getPARADETAILS() {
		return PARADETAILS;
	}
	public void setPARADETAILS(List<ParamValue> pARADETAILS) {
		PARADETAILS = pARADETAILS;
	}
	public Map<String, Object> getZY() {
		return ZY;
	}
	public void setZY(Map<String, Object> zY) {
		ZY = zY;
	}
	public Map<String, Object> getZS() {
		return ZS;
	}
	public void setZS(Map<String, Object> zS) {
		ZS = zS;
	}
	
	
	

}
