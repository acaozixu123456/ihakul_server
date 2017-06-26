package com.xiaoai.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;
import com.xiaoai.service.IUsersService;
import com.xiaoai.util.Page;

@Controller("usersAction")
public class UsersAction {
	@Resource(name="usersService")
	private IUsersService usersService;
	
	private Page page=new Page();
	private int pageNow;//当前页
	private int showPage;//每页显示记录数
	private int offset;//开始记录数
	private int total;//总记录数
	//private List<Users> list;
	private int totalPage;//总页数
	private boolean fals;//删除返回的状态标志位
	private Users users;
	/**
	 * 条件分页查询
	 * @param userName 用户名
	 * @param userPhoneNumber 用户手机号
	 * @return usersList 用户对象集合
	 * @throws IOException 
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public String findAll() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();	
		String userName=request.getParameter("userName");
		String userPhoneNumber=request.getParameter("userPhoneNumber");
		Users users=new Users();
		if(userName !=null && !"".equals(userName)){
			users.setUserName(userName);
		}
		if(userPhoneNumber !=null && !"".equals(userPhoneNumber)){
			users.setUserPhoneNumber(userPhoneNumber);
		}
		int total=usersService.getAllUsersCount(users);//得到总记录数
		
		page.setTotal(total);
		page.setShowPage(showPage);
		page.setPageNow(pageNow);
		offset=page.getOfferset();//得到开始记录数
		pageNow=page.getpageNow();//得到当前页数
		
		totalPage=page.gettotalPage();//得到总页数
		
		List<Users> usersList=usersService.queryUsers(offset, showPage,users);
		
		Map<String,List> map=new HashMap<String,List>();
		List<Familygroup> fglist=null;
		if(usersList.size()>0){
			/*for (Users users2 : usersList) {
				fglist=familyService.selectFamilygroupByusers(users2);
				
			}*/
			map.put("usersList", usersList);
			request.setAttribute("map", map);
			return "success";
		}else{
			out.print("<script>alert('对不起，您查询的用户不存在')</script>");
			
			out.flush();
			return "error";
		}
		
		
	}
	
	
	/**
	 * 删除
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String deleteUsers() throws UnsupportedEncodingException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		String userId=request.getParameter("userId");
		
		int id=Integer.parseInt(userId);
		Users user=new Users();
		user.setUserId(id);
		fals=usersService.deleteUsersByid(user);
		
		if(fals){
				return "success";
		}else{
				return "error";
		}
		
		
	}
	
	/**
	 * 添加用户
	 * @return
	 * @throws IOException 
	 */
	public String insertUsers() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String userName=request.getParameter("userName");
		String userPassword=request.getParameter("userPassword");
		String phoneNumber=request.getParameter("phoneNumber");
		String sex=request.getParameter("sex");
		//得到当前系统时间
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTimes=df.format(date);
		Users users=new Users();
		users.setCreateTime(Timestamp.valueOf(nowTimes));
		users.setUserName(userName);
		users.setUserPassword(userPassword);
		users.setUserPhoneNumber(phoneNumber);
		users.setUserSex(sex);
		//执行持久化操作
		fals=usersService.saveUsers(users);
		
		if(fals){
				int refreshNumber=1;
				request.setAttribute("refresh", refreshNumber);
				out.print("<script>alert('添加成功')</script>");
				out.flush(); 
				
				return "success";
		}else{
				out.print("<script>alert('添加失败')</script>");
				out.flush(); 
				return "error";
		}
		
		
		
		
	}
	/**
	 * 根据id查询
	 * @return
	 * @throws IOException 
	 */
	public String getUsers() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		
		String userId=request.getParameter("userId");
		
		users=usersService.selectUsersByid(Integer.parseInt(userId));
		
		if(users !=null){
				return "success";
		}else{
				return "error";
		}
		
		
		
	}
	/**
	 * 修改
	 * @return
	 * @throws IOException 
	 */
	public String updateUsers() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String userId=request.getParameter("userId");
		String userName=request.getParameter("userName");
		String userPassword=request.getParameter("userPassword");
		String userPhoneNumber=request.getParameter("userPhoneNumber");
		String userSex=request.getParameter("sex");
		String accessper=request.getParameter("accessper");
		
		//执行修改操作
		Users users=usersService.selectUsersByid(Integer.parseInt(userId));;
		users.setUserName(userName);
		users.setUserPassword(userPassword);
		users.setUserPhoneNumber(userPhoneNumber);
		users.setUserSex(userSex);
		users.setAccessper(Integer.parseInt(accessper));
		fals=usersService.updateUsersByid(users);
		
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


	public void setUsers(Users users) {
		this.users = users;
	}
	
}
