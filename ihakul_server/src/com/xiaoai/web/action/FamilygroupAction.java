package com.xiaoai.web.action;



import java.io.IOException;


import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.sql.Timestamp;
import java.util.List;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;


import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.service.IXiaoiService;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.Page;
@SuppressWarnings("serial")
@Controller("familygroupAction")
public class FamilygroupAction extends ActionSupport{
	@Resource(name="familyService")
	private IFamilygroupService familyService;
	@Resource(name="xiaoiService")
	private IXiaoiService xiaoiService;
	private int id;
	List<Familygroup> familyList;
	
	
	private boolean fals=true;//删除返回的状态标志位
	
	/**
	 * 条件查询所有家庭组
	 * @param startTime 开始时间
	 * @param showPage 页面当前显示最大记录数
 	 * @param pageNow  页面当前显示页数
	 * @param groupName 家庭组名字
	 * @param city      城市
	 * @return familyList(家庭组对象集合),pageNow(页面当前显示页数),totalPage(总页数)
	 * @throws IOException 
	 */
	public String selectAllFamily() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String startTime=request.getParameter("startTime");  //开始时间
	//	String endTime=request.getParameter("endTime");       //结束时间
		
		String showPage=request.getParameter("showPage");
		String pageNow=request.getParameter("pageNow");
		Familygroup fg=new Familygroup();
		
		String groupName=request.getParameter("groupName");
		String city=request.getParameter("city");
		if(null !=groupName && !groupName.equals("")){
			fg.setGroupName(groupName);
		}
		if(null !=city && ! city.equals("")){
			fg.setCity(city);
		}
		if(null !=startTime && ! startTime.equals("")){
			fg.setCreationTime(Timestamp.valueOf(startTime));
		}
		int total=familyService.getAllRowCount(fg);//得到总记录数
		Page page=new Page();
		page.setTotal(total);
		page.setShowPage(Integer.parseInt(showPage));
		page.setPageNow(Integer.parseInt(pageNow));
		int offset=page.getOfferset();//得到开始记录数
		int pageNows=page.getpageNow();//得到当前页数
		System.out.println(pageNow);
		int totalPage=page.gettotalPage();//得到总页数
		familyList=familyService.queryFamilygroup(offset, Integer.parseInt(showPage),fg);
		if(familyList.size()>0){
			request.setAttribute("list", familyList);
			request.setAttribute("pageNow", pageNows);
			request.setAttribute("totalPage", totalPage);
			return "success";
		}else{
		//	out.print("<script>alert('对不起，您查询的家庭组不存在')</script>");
		//	out.print("<script>window.location.href='showAllFanily.action?pageNow=1&showPage=5'</script>");
			out.flush(); 
			return "error";
		}
		
		
	}
	/**
	 * 删除
	 * @param id 家庭组id
	 * @return fals=false(删除失败)或者fals=true(删除成功)
	 * @throws IOException 
	 */
	public String delete() throws IOException{
		JSONObject json=new JSONObject();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out = MyRequest.getResponse();
		String id=request.getParameter("id");
		Familygroup fg=new Familygroup();
		fg.setGroupId(Integer.parseInt(id));
		fals=familyService.deleteFamilygroup(fg);
		json.put("fals", fals);
		out.println(json.toString());
		out.flush();
		return "success";
		
	}
	/**
	 * 添加
	 * @param xiaoNumber 终端编号
	 * @param gnumber 家庭组编号
	 * @param groupName 家庭组名字
	 * @param state 国家
	 * @param city  城市
	 * @param district  街道
	 * @return fals=false(添加失败)或者fals=true(添加成功)
	 * @throws IOException 
	 */
	public String insertFamily() throws IOException{
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpServletRequest request=ServletActionContext.getRequest();
		String xiaoNumber=request.getParameter("xiaoNumber");
		Xiaoi xi=xiaoiService.selectXiaoiByNumber(xiaoNumber);
		String gnumber =request.getParameter("gnumber");
		String  groupName=request.getParameter("groupName");
		String state =request.getParameter("state");
		String city =request.getParameter("city");
		String district =request.getParameter("district");
		Familygroup fg=new Familygroup();
		fg.setCity(city);
		fg.setDistrict(district);
		fg.setGroupName(groupName);
		fg.setState(state);
		fg.setGroupNumber(Integer.parseInt(gnumber));
		fals=familyService.insertFamilygroup(fg);
		if(fals){
			int refreshNumber=1;
			xi.setFamilygroup(fg);
			request.setAttribute("refresh", refreshNumber);
			xi.setOnlineState(1);
			xiaoiService.updateXiaoi(xi);
		
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
	
	
	/**
	 * 修改 
	 * @param id 家庭组id
	 * @param xiaoNumber 终端编号
	 * @param gnumber 家庭组编号
	 * @param groupName 家庭组名字
	 * @param state 国家
	 * @param city  城市
	 * @param district  街道
	 * @return fals=false(修改失败)或者fals=true(修改成功)
	 * @throws IOException 
	 */
	public String updateFamily() throws IOException{
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		String gnumber =request.getParameter("gnumber");
		String  groupName=request.getParameter("groupName");
		String state =request.getParameter("state");
		String city =request.getParameter("city");
		String district =request.getParameter("district");
		Familygroup fg=familyService.getFamilygroupByid(id);
		fg.setCity(city);
		fg.setDistrict(district);
		fg.setGroupName(groupName);
		fg.setState(state);
		fg.setGroupNumber(Integer.parseInt(gnumber));
		
		fals=familyService.updateFamily(fg);
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
	 * 根据id查询
	 * @param id 家庭组id
	 * @return family 家庭组对象
	 */
	public String selectFgByid(){
		HttpServletRequest request=ServletActionContext.getRequest();
		Familygroup fg=familyService.getFamilygroupByid(id);
		if(fg !=null){
			request.setAttribute("family", fg);
			return "success";
		}else{
			return "error";
		}	
	}
	
	/**
	 * 根据家庭组名字判断是否存在该家庭组
	 * @param groupName 家庭组名字
	 * @return fals=false(该家庭组不存在)或者fals=true(该家庭组存在)
	 * @throws UnsupportedEncodingException 
	 */
	public String selectFamilyByname() throws UnsupportedEncodingException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		String groupName=request.getParameter("groupName");
		
		Familygroup familygroup=familyService.getFamilygroupByName(groupName);
		if(familygroup !=null){
			fals=true;
			return "success";
		}else{
			fals=false;
			return "error";
		}
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	


	public boolean isFals() {
		return fals;
	}
	public void setFals(boolean fals) {
		this.fals = fals;
	}
	public List<Familygroup> getFamilyList() {
		return familyList;
	}
	public void setFamilyList(List<Familygroup> familyList) {
		this.familyList = familyList;
	}
	
	
	
}
