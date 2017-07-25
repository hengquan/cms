package com.hj.wxmp.mobile.entity;
/**   
* @Title: ClFuncParaDesc.java
* @Package com.hj.wxmp.mobile.entity
* @Description: TODO
* @author Wangzhiyong   
* @date 2016年8月13日 上午11:05:26
* @version V1.0   
* 择时
*/
public class ClFuncParaDesc {
	
	private String FUNC_ID;
	
	private int PARAM_ID;
	
	private String PARA_NAMES;
	
	private String COMMENT;

	public String getFUNC_ID() {
		return FUNC_ID;
	}

	public void setFUNC_ID(String fUNC_ID) {
		FUNC_ID = fUNC_ID;
	}

	public int getPARAM_ID() {
		return PARAM_ID;
	}

	public void setPARAM_ID(int pARAM_ID) {
		PARAM_ID = pARAM_ID;
	}

	public String getPARA_NAMES() {
		return PARA_NAMES;
	}

	public void setPARA_NAMES(String pARA_NAMES) {
		PARA_NAMES = pARA_NAMES;
	}

	public String getCOMMENT() {
		return COMMENT;
	}

	public void setCOMMENT(String cOMMENT) {
		COMMENT = cOMMENT;
	}
	
	
	

}
