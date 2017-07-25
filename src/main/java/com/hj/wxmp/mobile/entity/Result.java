package com.hj.wxmp.mobile.entity;
import java.util.List;
import java.util.Map;
/**   
* @Title: Result.java
* @Package com.wzy.domain
* @Description: TODO
* @author Wangzhiyong   
* @date 2016年8月1日 下午4:36:10
* @version V1.0   
*/
public class Result {
	
	private String TASKID;
	private Map<String,Object> ALL;
	private Map<String,Object> BEST;
	private ParamReturn BESTPARA;
	private List<Map<String,Object>> EARNHIS;
	private List<Map<String,Object>> BSAction;
	
	
	public ParamReturn getBESTPARA() {
		return BESTPARA;
	}
	public void setBESTPARA(ParamReturn bESTPARA) {
		BESTPARA = bESTPARA;
	}
	public List<Map<String, Object>> getBSAction() {
		return BSAction;
	}
	public void setBSAction(List<Map<String, Object>> bSAction) {
		BSAction = bSAction;
	}
	public String getTASKID() {
		return TASKID;
	}
	public void setTASKID(String tASKID) {
		TASKID = tASKID;
	}
	public Map<String, Object> getALL() {
		return ALL;
	}
	public void setALL(Map<String, Object> aLL) {
		ALL = aLL;
	}
	public Map<String, Object> getBEST() {
		return BEST;
	}
	public void setBEST(Map<String, Object> bEST) {
		BEST = bEST;
	}

	public List<Map<String, Object>> getEARNHIS() {
		return EARNHIS;
	}
	public void setEARNHIS(List<Map<String, Object>> eARNHIS) {
		EARNHIS = eARNHIS;
	}


}
