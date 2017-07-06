package com.xiaoai.web.action;

import java.io.IOException;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.struts2.ServletActionContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;




import com.opensymphony.xwork2.ActionSupport;

import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.service.IXiaoiService;
import com.xiaoai.util.JavaDemo10;
import com.xiaoai.util.Page;
import com.xiaoai.util.XATools;
@SuppressWarnings("serial")
@Controller("xiaoiAction")//控制层 都要写 固定写法
@Scope("prototype")
public class XiaoiAction extends ActionSupport{
	
	private String familynumber;//家庭组编号
	private int id;
	private Page page=new Page();
	private int pageNow;//当前页
	private int showPage;//每页显示记录数
	private int offset;//开始记录数
	private int total;//总记录数
	
	private int totalPage;//总页数
	private boolean fals;//删除返回的状态标志位
	@Resource(name="xiaoiService")
	private IXiaoiService xiaoiService;
	
	
	@Resource(name="familyService")
	private IFamilygroupService familyService;
	/**
	 * 根据编号查询
	 * @param xiaoiNumber 终端编号
	 * @return 
	 * @throws IOException 
	 */
	
	@SuppressWarnings("unused")
	public String getXiaoi() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String xiaoiNumber=request.getParameter("xiaoiNumber");
		Xiaoi xi=xiaoiService.selectXiaoiByNumber(xiaoiNumber);
		int num=100;
		boolean fals=true;
		if(xi !=null){//数据库中存在此编号
			if(xi.getState()==0){//如果未使用
				for(int i=0;i<num;i++){
					familynumber=JavaDemo10.getRandomNumber();//得到家庭组编号
					try {
						Familygroup fg=familyService.getFamilygroupByNumber(Integer.parseInt(xiaoiNumber));
					} catch (Exception e) {
						fals=false;	
					}
					if(fals){//验证该编号是否已使用
						continue;
					}else{//未使用
						break;
					}
				}
				return "success";
			}
			else{//该小艾已使用
				return "error";
			}
		}else{//该编号不存在
			
			return "error";
		}
	}
	
	/**
	 * 按条件查询
	 * @param xiaoNumber 终端小艾编号、
	 * @param groupName 家庭组名字
	 * @return list(终端对象集合)，pageNow(页面显示当前页),totalPage(总页数)
	 * @throws IOException 
	 */
	public String findAll() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		PrintWriter out=response.getWriter();
		String xiaoNumber=request.getParameter("xiaoNumber");	
		String groupName=request.getParameter("groupName");
		Xiaoi xiao=new Xiaoi();
		if(xiaoNumber !=null && ! xiaoNumber.equals("")){
			xiao.setXiaoNumber(xiaoNumber); 
		}
		
		if(groupName !=null &&! "".equals(groupName)){
			Familygroup fg=familyService.getFamilygroupByName(groupName);
			xiao.setFamilygroup(fg);
			
		}
		int total=xiaoiService.getXiaoiCount(xiao);//得到总记录数
		page.setTotal(total);
		page.setShowPage(showPage);
		page.setPageNow(pageNow);
		offset=page.getOfferset();//得到开始记录数
		pageNow=page.getpageNow();//得到当前页数
		totalPage=page.gettotalPage();//得到总页数
		List<Xiaoi> xiaoList=xiaoiService.findAll(offset, showPage, xiao);
		if(xiaoList.size()>0){
			request.setAttribute("list", xiaoList);
			return "success";
		}else{
		//	out.print("<script>alert('对不起，您查询的小艾不存在')</script>");
		//	out.print("<script>window.location.href='findAllXiaoi.action?pageNow=1&showPage=5'</script>");
			out.flush();
			return "error";
		}
		
	}
	
	
	
	
	/**
	 * 删除
	 * @param id
	 * @return 
	 * @throws IOException
	 */
	public String deleteXiaoi() throws IOException{
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Xiaoi xi=new Xiaoi();
		xi.setXid(id);
		boolean fals=xiaoiService.delete(xi);
		if(fals){
			out.print("<script>alert('删除成功')</script>");
			out.flush(); 
			return "success";
		}else{
			out.print("<script>alert('删除失败')</script>");
			out.flush();
			return "error";
		}
		
	}
	/**
	 * 根据id查询
	 * @param id 终端小艾ID
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getXiaoiByid() throws UnsupportedEncodingException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		Xiaoi xiao=xiaoiService.getXiaoiByid(id);
		request.setAttribute("xiao", xiao);
		return "success";
	}
	
	/**
	 * 修改
	 * @param id 终端小艾ID
	 * @param xiaoName 终端名字
	 * @param xiaoType 终端类型
	 * @param groupName 家庭组名字
	 * @param useType 使用状态
	 * @return
	 * @throws IOException 
	 */
	public String updateXiaoi() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String xiaoName=request.getParameter("xiaoName");
		String xiaoType=request.getParameter("xiaoType");
		String groupName=request.getParameter("groupName");
		Familygroup family=familyService.getFamilygroupByName(groupName);
		String usesType=request.getParameter("useType");
		int useType=Integer.parseInt(usesType);	
		Xiaoi xiao=xiaoiService.getXiaoiByid(id);
		xiao.setXname(xiaoName);
		xiao.setXiaoType(Integer.parseInt(xiaoType));
		xiao.setFamilygroup(family);
		xiao.setState(useType);
		boolean fals=xiaoiService.updateXiaoi(xiao);
		if(fals){
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			out.print("<script>alert('修改成功')</script>");
			out.flush();
			return "success";
		}else{
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			out.print("<script>alert('修改失败')</script>");
			out.flush();
			return "error";
		}
		
	}
	/**
	 * 添加
	 * 
	 * @param xiaoName 终端名字
	 * @param xiaoType 终端类型
	 * @param groupName 家庭组名字
	 * @param useType 使用状态
	 * @return
	 * @throws IOException 
	 */
	public String addXiaoai() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String xiaoName=request.getParameter("xiaoName");
		String xiaoNumber=request.getParameter("xiaoNumber");
		String xiaoType=request.getParameter("xiaoType");
		Xiaoi xiao=new Xiaoi();
		xiao.setXname(xiaoName);
		xiao.setXiaoNumber(xiaoNumber);
		if(!XATools.isNull(xiaoType)){
		xiao.setXiaoType(Integer.parseInt(xiaoType));
		}
		xiao.setState(0);
		fals=xiaoiService.insertXiaoi(xiao);
		if(fals){
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			out.print("<script>alert('添加成功')</script>");
			out.flush();
			return "success";
		}else{
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			out.print("<script>alert('添加失败')</script>");
			out.flush();
			return "error";
		}
		
	}
	
	
	public String getFamilynumber() {
		return familynumber;
	}
	public void setFamilynumber(String familynumber) {
		this.familynumber = familynumber;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
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

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
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

	public boolean isFals() {
		return fals;
	}

	public void setFals(boolean fals) {
		this.fals = fals;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
