/**
 * 2013-03-23 
 * @author ecif-yanjingying
 * 分页的公共类
 * */
package com.hj.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

public class PageUtil {
	
		// 每页显示条数， 默认是每页10条，具体根据传入的参数决定 
		private Integer count; 
		
		// 总记录数  
		private Integer record; 
		
		//当前页
		private Integer currentPage; 
		
		// 总页数  
		private Integer totalPage; 
		
		 // 下一页
		private Integer nextPage;
		
		// 上一页
		private Integer prePage; 
		
		// 最后一页
		private Integer lastPage; 
		
		// 第一页
		private Integer firstPage; 
		
	
		public Integer getCount() {
			if(this.count == null){
				this.count = 10;
			}
			return this.count;
		}
	
		public void setCount(Integer count) {
			this.count = count;
		}
	
		public Integer getRecord() {
			return record;
		}
	
		public void setRecord(Integer record) {
			this.record = record;
		}
	
		public Integer getTotalPage() {
			if((this.record/this.count) >= 1){  //取整大于等于1
				if( (this.record%this.count) == 0){
					this.totalPage = (this.record/this.count);
				}
				if( (this.record%this.count) > 0){
					this.totalPage = (this.record/this.count)+1;
				}
			}
			if((this.record / this.count) < 1){ //取整小于1
				this.totalPage = 1;
			}
			return this.totalPage;
		}
	
		public void setTotalPage(Integer totalPage) {
			this.totalPage = totalPage;
		}
	
		public Integer getNextPage() {
			this.nextPage = this.currentPage +1;
			return this.nextPage;
		}
	
		public void setNextPage(Integer nextPage) {
			this.nextPage = nextPage;
		}
	
		public Integer getPrePage() {
			this.prePage = this.currentPage -1;
			return this.prePage;
		}
	
		public void setPrePage(Integer prePage) {
			this.prePage = prePage;
		}
	
		public Integer getLastPage() {
			return this.getTotalPage();
		}
	
		public void setLastPage(Integer lastPage) {
			this.lastPage = lastPage;
		}
	
		public Integer getFirstPage() {
			return 1;
		}
	
		public void setFirstPage(Integer firstPage) {
			this.firstPage = firstPage;
		}
		
		public Integer getCurrentPage() {
			if(this.currentPage == null){
				this.currentPage = 1;
			}
			return this.currentPage;
		}
	
		public void setCurrentPage(Integer currentPage) {
			this.currentPage = currentPage;
		}
	
		
		
		public  Map<String,Object> pageReturnModelMap(HttpServletRequest request,  Map<String,Object> map, ModelMap modelMap,Integer totalRecord){
			PageUtil pageUtil = new PageUtil();
			// step 1
			String count_str = request.getParameter("count");  
			String currentPage_str = request.getParameter("currentPage"); 
			// step 2
			Integer count = 0;
			if(count_str != null){ if("-1".equals(count_str)){count = pageUtil.getCount();}else{count  = Integer.parseInt(count_str);}}
			if(count_str == null || "".equals(count_str)){  count = pageUtil.getCount();}
			// step 3
			Integer currentPage = 1;
			if(currentPage_str != null){currentPage = Integer.parseInt(currentPage_str); }
			if(currentPage_str == null || "".equals(currentPage_str)){currentPage = 1;  }
			// step 5
			pageUtil.setCount(count);
			pageUtil.setRecord(totalRecord);
			pageUtil.setCurrentPage(currentPage);
			// step 6
			if(null != modelMap){
				modelMap.addAttribute("pageUtil", pageUtil);
			}else{
				map.put("pageUtil", pageUtil);
			}
			// step 7
			int start = (currentPage-1)*count + 1;  
			int end = (start-1)+count;  
			map.put("start", start);
			map.put("end", end);
			map.put("pagesize", count);
			map.put("pagestart", start-1);
			
			if(null != modelMap){
				modelMap.put("start", start);
				modelMap.put("end", end);
				modelMap.put("pagesize", count);
				modelMap.put("pagestart", start-1);
			}
			
			return map;			
		}
}
