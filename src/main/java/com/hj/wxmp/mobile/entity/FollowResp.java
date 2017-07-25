package com.hj.wxmp.mobile.entity;


import java.util.ArrayList;
import java.util.List;

public class FollowResp {
	    private Integer id;

	    private Integer resptype=0;

	    private String content="";
	    
	    private String title="";

	    private String img="";

	    private String url="";

	    private String des="";
	   
	    private List<FollowRespEx> exlist = new ArrayList<FollowRespEx>();
	    
	    
	    public FollowResp() {
	    }

	    
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        FollowResp game = (FollowResp) o;

	        return id.equals(game.id);
	    }


		public Integer getId() {
			return id;
		}


		public void setId(Integer id) {
			this.id = id;
		}


		public List<FollowRespEx> getExlist() {
			return exlist;
		}


		public void setExlist(List<FollowRespEx> exlist) {
			this.exlist = exlist;
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


		public String getDes() {
			return des;
		}


		public void setDes(String des) {
			this.des = des;
		}


}
