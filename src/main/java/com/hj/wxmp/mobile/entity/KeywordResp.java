package com.hj.wxmp.mobile.entity;

import java.util.ArrayList;
import java.util.List;


public class KeywordResp {
	
	public static String SINGLE_TEXT="文字回复";
	public static String SINGLE_TEXT_IMG="单图文回复";
	public static String MULTI_TEXT_IMG="多图文回复";
	
	    private Integer id;

	    private String keyword="";
	 
	    private Integer resptype=0;
	    
	   

	    private String content="";
	    
	    private String title = "";

	    private String img="";

	    private String url="";

	    private String desc="";
	    
	    private String displayType="";
	    
	    
	    private String displayDesc="";
	    
	    private List<KeywordRespEx> exlist = new ArrayList<KeywordRespEx>();
	   
	    
	    public String getDisplayType() {
	    	if(0==resptype){
	    		return SINGLE_TEXT;
	    	}else if(1==resptype){
	    		return SINGLE_TEXT_IMG;
	    	}else{
	    		return MULTI_TEXT_IMG;
	    	}
		}


		public void setDisplayType(String displayType) {
			this.displayType = displayType;
		}


		public String getDisplayDesc() {
			if((null != desc)&&(!desc.isEmpty())){
	    		return desc;
	    	}else if((null != content)&&(!content.isEmpty())){
	    		return content;
	    	}else if(exlist.size()>0){
	    		return exlist.get(0).getTitle();
	    	}
			return "";
		}


		public void setDisplayDesc(String displayDesc) {
			this.displayDesc = displayDesc;
		}


		
	    public KeywordResp() {
	    }

	    
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        KeywordResp game = (KeywordResp) o;

	        return id.equals(game.id);
	    }


		public Integer getId() {
			return id;
		}


		public void setId(Integer id) {
			this.id = id;
		}
		


		public List<KeywordRespEx> getExlist() {
			return exlist;
		}


		public void setExlist(List<KeywordRespEx> exlist) {
			this.exlist = exlist;
		}


		public String getKeyword() {
			return keyword;
		}


		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}


		public Integer getResptype() {
			return resptype;
		}


		public void setResptype(Integer type) {
			this.resptype = type;
		}


		public String getContent() {
			return content;
		}


		public void setContent(String content) {
			this.content = content;
		}


		public String getTitle() {
			return title;
		}


		public void setTitle(String title) {
			this.title = title;
		}


		public String getImg() {
			return img;
		}


		public void setImg(String img) {
			this.img = img;
		}


		public String getUrl() {
			return url;
		}


		public void setUrl(String url) {
			this.url = url;
		}


		public String getDesc() {
			return desc;
		}


		public void setDesc(String desc) {
			this.desc = desc;
		}


}
