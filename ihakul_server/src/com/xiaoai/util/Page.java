package com.xiaoai.util;
/**
 * 分页工具类
 * @author Administrator
 *
 */
public class Page {
	private int pageNow;//当前页
	private int showPage;//每页显示记录数
	
	private int total;//总记录数
	private int totalPage;//总页数
	
	public Page(){
		
	}
	public Page(int pageNow,int showPage,int total,int totalPage){
		
		this.pageNow=pageNow;
		this.showPage=showPage;
		this.total=total;
		this.totalPage=totalPage;
	}
	/**
	 * 得到每页开始记录数
	 * @return
	 */
	public int getOfferset(){
		int offset=(this.pageNow-1)*this.showPage;
		return offset;
	}
	/**
	 * 得到当前页
	 * @return
	 */
	public int getpageNow(){
		if(this.pageNow ==0){
			this.pageNow=this.pageNow +1;
			return this.pageNow;	
		}else{
			this.pageNow=this.pageNow;
			return this.pageNow;
			}
	
	}
	/**
	 * 得到总页数
	 * @return
	 */
	public int gettotalPage(){
		
		if(this.total%this.showPage ==0){
			int totalPage=this.total/this.showPage;
			return totalPage;
		}else{
			int totalPage=this.total/this.showPage+1;
			return totalPage;
			}
	}
	public int getPageNow() {
		return pageNow;
	}
	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}
	public int getShowPage() {
		return showPage;
	}
	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	

}

