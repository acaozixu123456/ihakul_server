package com.xiaoai.web.action;



import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiaoai.entity.Administrate;
import com.xiaoai.entity.Privilege;
import com.xiaoai.entity.Role;
import com.xiaoai.service.IAdministraterService;
import com.xiaoai.service.IPrivilegeService;
import com.xiaoai.service.IRoleService;
import com.xiaoai.util.Page;
import com.xiaoai.util.XATools;
/**
 *      
 * 类名称：AdministraterAction    
 * 类描述：管理员控制器的实现 
 * 创建人：蓝道优    
 * 创建时间：2016-12-6 下午11:07:43    
 * 修改人：   
 * 修改时间：   
 * 修改备注：    
 */


@Controller("adminAction")//控制层 都要写 固定写法
@Scope("prototype")
public class AdministraterAction {
	
	private String aname;
	private String password;
	private int aid;
	
	private boolean fals;
	private List<Administrate> adminList;
	private Page page=new Page();
	private int pageNow;//当前页
	private int showPage;//每页显示记录数
	private int offset;//开始记录数
	private int total;//总记录数
	private int totalPage;//总页数
	@Resource(name="adminService")//注入类  
	private IAdministraterService adminService;
	@Resource(name="roleService")
	private IRoleService roleService;
	@Resource(name="privilegeService")
	private IPrivilegeService privilegeService;
	
	/**
	 * 管理员登入
	 * @param aname 管理员用户名
	 * @param password 管理员用户密码
	 * @return fals=true(登入成功)或者fals=false(登入失败)
	 * @throws IOException 
	*/ 
	@SuppressWarnings("unused")
	public String adminLogin() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		Administrate admin= adminService.selectAdmiByad(aname);
		if(admin!=null){ //如果有该用户
		if(password.equals(admin.getPassword())){ //和密码正确
			//保存登入后的管理员信息
			session.setAttribute("admin", admin);
			int loginTimes=admin.getLoginNumber();//得到之前登入次数
			loginTimes++;
			admin.setLoginNumber(loginTimes);
			admin.setLoginLastTime(XATools.getNowTime());
			boolean fals=adminService.updateAdmin(admin);//修改最后登入时间和登入次数
			int id=admin.getRole().getRid();
			List<Privilege> listPri=privilegeService.findPrivilegeByid(id);	
			session.setAttribute("privilege", listPri);
			out.print("<html><head><meta charset='UTF-8'></head>"+"<script>alert('登入成功')</script>");  
			out.flush();   
			return "success";
		}else{
			out.print("<html><head><meta charset='UTF-8'></head>"+"<script>alert('密码不正确')</script>");
			out.flush();   
			return "error";
		}
		}else{
			out.print("<html><head><meta charset='UTF-8'></head>"+"<script>alert('没有该用户')</script>");
			out.flush();   	
			return "error";
		}
		
	}
	
	
	/**
	 * 分页查询所有管理员
	 * @param pageNow 页面显示当前页
	 * @param showPage 页面显示最大记录数
	 * @return adminList(管理员集合),pageNow页面显示当前页,totalPage总页数
	 * @throws IOException 
	 */
	
	@SuppressWarnings("unused")
	public String selectAlladmin() throws IOException {
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		int total=adminService.getCountAdmin();//得到总记录数
		page.setTotal(total);
		page.setShowPage(showPage);
		page.setPageNow(pageNow);
		offset=page.getOfferset();//得到开始记录数
		pageNow=page.getpageNow();//得到当前页数
		totalPage=page.gettotalPage();//得到总页数
		adminList=adminService.selectAll(offset, showPage);
		request.setAttribute("adminList", adminList);
		return "success";
	}
	
	/**
	 * 根据id查询管理员
	 * @param aid 管理员id
	 * @return admin 管理员对象
	 * @throws UnsupportedEncodingException 
	 */
	
	public String selectAdminByid() throws UnsupportedEncodingException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		request.getAttribute("aid");
		Administrate admin=adminService.selectAdminByid(aid);
		request.setAttribute("admin", admin);
		return "success"; 
	}
	
	
	/**
	 * 根据用户名查询管理员
	 * @param aname 管理员名称
	 * @return admin 管理员对象
	 * @throws IOException 
	 */
	
	public String selectAdminByan() throws IOException{
		JSONObject json=new JSONObject();
		boolean fals=false;
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
	    String aname=request.getParameter("aname");
		Administrate admin=adminService.selectAdmiByad(aname);
		if(admin!=null){
			fals=true;
		}
		json.put("fals", fals);
		out.print(json.toString());
		return null; 
	}
	
	/**
	 * 修改
	 * @param aid 管理员id
	 * @param realName 管理员真实姓名
	 * @param phoneNumber 管理员手机号
	 * @param roleName 管理员权限名
	 * @param sex 管理员性别
	 * @param aname 用户名
	 * @param password 密码
	 * @return fals=true(修改成功)或者fals=false(修改失败)
	 * @throws IOException 
	 */
	public String updateAdminByid() throws IOException{
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String realName=request.getParameter("realName");
		String phoneNumber=request.getParameter("phoneNumber");
		String roleName=request.getParameter("roleName");
		String sex=request.getParameter("sex");
		Role role=roleService.selectRoleByname(roleName);
		Administrate admin=adminService.selectAdminByid(aid);
		admin.setAname(aname);
		admin.setPassword(password);
		admin.setRole(role);
		admin.setRealName(realName);
		admin.setPhoneNumber(phoneNumber);
		admin.setSex(sex);
		fals=adminService.updateAdmin(admin);//执行持久化操作返回状态标志位 
		if(fals){
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			out.print("<html><head><meta charset='UTF-8'></head>"+"<script>alert('修改成功')</script>"); 
			out.flush();
			return "success";
		}else{
			out.print("<html><head><meta charset='UTF-8'></head>"+"<script>alert('修改失败')</script>"); 
			out.flush();
			return "error";
		}
		
		
	}
	
	
	/**
	 * 添加
	 * @param realName 管理员真实姓名
	 * @param phoneNumber 管理员手机号
	 * @param roleName 管理员权限名
	 * @param sex 管理员性别
	 * @param aname 用户名
	 * @param password 密码
	 * @return fals=true(添加成功)或者fals=false(添加失败)
	 * @throws IOException 
	 */
	public String insertAdmin() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String aname=request.getParameter("aname");
		String password=request.getParameter("password");
		String realName=request.getParameter("realName");
		String phoneNumber=request.getParameter("phoneNumber");
		String sex=request.getParameter("sex");
		Role role=roleService.selectRoleByid(2); //默认设置为普通管理员
		if(role==null){
			//第一次启动服务器，还未设置角色
			
		}
		Administrate admin=new Administrate();
		admin.setAname(aname);
		admin.setPassword(password);
		admin.setPhoneNumber(phoneNumber);
		admin.setSex(sex);
		admin.setRealName(realName);
		admin.setLoginNumber(0);
		admin.setRole(role);
		boolean fals=adminService.insertAdmin(admin);
		if(fals){//添加持久化操作返回真
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			out.print("<html><head><meta charset='UTF-8'></head>"+"<script>alert('添加成功')</script>"); 
			out.flush();
			return "success";
		}else{//添加持久化操作返回假
			out.print("<html><head><meta charset='UTF-8'></head>"+"<script>alert('添加失败')</script>");  
			out.flush();
			return "error";
		}
		
	}
	/**
	 * 删除
	 * @param aid 管理员id
	 * @return fals=true(删除成功)或者fals=false(删除失败)
	 * @throws IOException 
	 * 
	 */
	public String deleteAdmin() throws IOException {
		JSONObject json=new JSONObject();
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String aid=request.getParameter("aid");
		//根据id查找管理员
		Administrate admin=adminService.selectAdminByid(Integer.parseInt(aid));
		//执行删除持久化
		fals=adminService.deleteAdminByid(admin);
		int refreshNumber=1;
		request.setAttribute("refresh", refreshNumber);
		json.put("fals", fals);
		out.print(json.toString()); 
		out.flush();
		return "success";
		
	
		
	}

	public List<Administrate> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Administrate> adminList) {
		this.adminList = adminList;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	
	public boolean isFals() {
		return fals;
	}
	
	public void setFals(boolean fals) {
		this.fals = fals;
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


	
}
