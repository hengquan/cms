package com.hj.wxmp.mobile.entity;



public class KeywordRespEx {
	    private Integer id;

	    private Integer parent_id=0;
	 
	    private String title="";

	    private String img="";

	    private String url="";

	    private String desc="";
	   
	    public KeywordRespEx() {
	    }

	    
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        KeywordRespEx game = (KeywordRespEx) o;

	        return id.equals(game.id);
	    }


		public Integer getId() {
			return id;
		}


		public void setId(Integer id) {
			this.id = id;
		}




		public Integer getParent_id() {
			return parent_id;
		}


		public void setParent_id(Integer parent_id) {
			this.parent_id = parent_id;
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