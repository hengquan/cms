package com.hj.wxmp.mobile.entity;



public class FollowRespEx {
	    private Integer id;
	    
	    private String title="";

	    private String img="";

	    private String url="";

	    private String des="";
	   
	    public FollowRespEx() {
	    }

	    
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        FollowRespEx game = (FollowRespEx) o;

	        return id.equals(game.id);
	    }


		public Integer getId() {
			return id;
		}


		public void setId(Integer id) {
			this.id = id;
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