package com.xiaoai.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;
import com.xiaoai.entity.Versions;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.service.IUsersService;
import com.xiaoai.service.IVersionService;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.Page;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage;

@Controller("appUserAction")
@Scope("prototype")
public class AppUsersAction extends XiaoaiMessage {
	@Resource(name="usersService")
	private IUsersService usersService;
	@Resource(name="familyService")
	private IFamilygroupService familyService;
	
	@Resource(name="versionService")
	private IVersionService versionService;
	
	private boolean success ;         //成功、失败标记
	private  String message ;       //信息
	private int code ;				//标记
	private static Logger logger = Logger.getLogger(AppUsersAction.class);
	/**
	 * 用户登入(使用手机号和密码登入)
	 * @param userPhoneNumber 用户手机号
	 * @param userPassword  用户密码
	 * @return success(判断登入成功(true)或者失败(false)的标志位) 如果成功即
	 * 			success=true时返回其所在家庭组的信息(fgList),当success=false时，返回登入用户信息(users)
	 * @throws IOException 
	 */
	public String login() throws IOException{
		message =null;      
		success =true;
		code=OK;
		JSONObject json=new JSONObject();
		JSONObject json2=new JSONObject();
		JSONArray array=new JSONArray();
		JSONArray array2=new JSONArray();
		Users users=null;
		List<Familygroup> fgList=null;
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("用户登录的入参");
		String userLogin=request.getParameter("userLogin");  //用户登录账号
		String userPassword=request.getParameter("userPassword");       //用户密码
		
		
		if(XATools.isNull(userLogin)){
			code=UserCode.emptyPhoneNum;
			success=false;
			message="手机号或用户名不能为空!";
		}	

		if(XATools.isNull(userPassword)){
			code=UserCode.emptyPass;
			success=false;
			message="密码不能为空!";
		}	
		if(success){
			users=usersService.usersByuserLogin(userLogin);
		if(users!=null){ //判断账号是否存在
		if(userPassword.equals(users.getUserPassword())){ //判断密码是否正确
			users.setLoginLastTimes(XATools.getTNowTime());
			success=usersService.updateUsersByid(users); //修改最后登录时间
			if(success){
				request.getSession().setAttribute("users", users); //存储users值
			}
			json2.put("userId", users.getUserId());
			json2.put("userName", users.getUserName());
			json2.put("userPassword", users.getUserPassword());
			json2.put("userPhoneNumber", users.getUserPhoneNumber());
			json2.put("userSex", users.getUserSex());   
//			json2.put("urid", users.getUsersrole().getUridId()); //用户角色id
			array2.add(json2);
			
			 // 根据用户信息查询到家庭组信息
			fgList=familyService.selectFamilygroupByusers(users);
			if(fgList.size()>0){//如果已有家庭组信息
				for (Familygroup familygroup : fgList) {
					JSONObject json1=new JSONObject();
					json1.put("groupName", familygroup.getGroupName());
					json1.put("groupNumber", familygroup.getGroupNumber());
					json1.put("groupPassword", familygroup.getGroupPassword());
					json1.put("managerId", familygroup.getManagerId());
					json1.put("userName", users.getUserName());
					array.add(json1);
				}
			}
		}else{
			code=UserCode.errorPass;
			success=false;
			message="用户登录密码错误!";
		}
		}else{
			code=UserCode.noExistBean;
			success=false;
			message="没有该用户!";
		}
		}
		
		json.put("code", code);
		json.put("message", message);
		json.put("result", array);   //家庭组信息
		json.put("result2", json2); //用户信息
		logger.info("用户登录的出参:"+json);
		out.print(json);
		return null;
	}
	/**
	 * 添加用户
	 * @param userName 用户名
	 * @param userPassword 用户密码
	 * @param phoneNumber 用户手机号
	 * @param sex  用户性别
	 * @return success=true(添加成功)或者success=false(添加失败)
	 * @throws IOException 
	 */
	public String insert() throws IOException{
		message =null;      
		success =true;
		code=OK;
		JSONObject json=new JSONObject();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("用户注册的入参");
		String userName=request.getParameter("userName");
		String userPassword=request.getParameter("userPassword");
		String userPhoneNumber=request.getParameter("userPhoneNumber");
		String sex=request.getParameter("userSex");
		Users users1=new Users();
		if(XATools.isNull(userName)){
			code=UserCode.emptyName;
			message="用户名不能为空  ";
			success =false;
		}
		if(XATools.isNull(userPassword)){
			code=UserCode.emptyPass;
			message="密码不能为空 ";
			success =false;
		}
		if(XATools.isNull(userPhoneNumber)){
			code=UserCode.emptyPhoneNum;
			message="手机号不能为空 ";
			success =false;
		}
		if(XATools.isNull(sex)){
			code=UserCode.emptyGender;
			message="性别不能为空 ";
			success =false;
		}
		if(success){
			users1=usersService.usersByuserLogin(userPhoneNumber);
			if(users1==null){
				Users users=new Users();
				users.setUserName(userName);      
				users.setCreateTime(XATools.getTNowTime());
				users.setUserPhoneNumber(userPhoneNumber);
				users.setUserPassword(userPassword);
				users.setUserSex(sex);
				users.setAccessper(1); //默认允许登录
				//执行持久化操作
				 success=usersService.saveUsers(users);
				 if(success==false){ //添加失败
					 code=UserCode.insertFalse;
					 message="添加用户失败";
				 }	
			}else{
				code=UserCode.conflictPhoneNum;
				message="该手机号已注册 ";
			}
		}
		json.put("code", code);
		json.put("message", message);
		logger.info("用户注册的出参:"+json);
		out.print(json.toString());
		return null;
	}
	
	
	
	/**
	 * 删除 
	 * @param userid 用户id
	 * @return  success=true或者success=false(删除成功或失败)
	 * @throws IOException 
	 */
	public String delete() throws IOException{
		code=OK;
		message =null;      
		success =true;
		JSONObject json=new JSONObject();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("用户删除的入参");
		String userId=request.getParameter("userId");
		if(XATools.isNull(userId)){
			code=UserCode.emptyId;
			message="用户id不能为空!";
			success=false;
		}
		if(success){
		Users users=usersService.selectUsersByid(Integer.parseInt(userId));
		if(users !=null){
			success=usersService.deleteUsersByid(users);
			if(success==false){
				code=UserCode.deleteFalse;
				message="用户删除失败！";
				success=false;
			}
		}else{
			code=UserCode.noExistBean;
			message="没有该用户!";
			success=false;
		}
		}
		json.put("code", code);
		json.put("message", message);
		logger.info("用户删除的出参:"+json);
		out.print(json.toString());
		return null;
	}

	/**
	 * 修改用户
	 * @param userPassword 用户密码
	 * @param userPhoneNumber 用户手机号
	 * @return  success=true或者success=false(修改成功或失败)
	 * @throws IOException 
	 */
	public String update() throws IOException{
		code=OK;
		message =null;      
		success =true;
		JSONObject json=new JSONObject();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("用户修改的入参");
		String userPassword=request.getParameter("userPassword");
		String userPhoneNumber=request.getParameter("userPhoneNumber");
		Users users=null;
		if(XATools.isNull(userPassword)){
			code=UserCode.emptyPass;
			message="密码不能为空!";
			success =false;
		} 	
		if(XATools.isNull(userPhoneNumber)){
			code=UserCode.emptyPhoneNum;
			message="手机号不能为空 !";
			success =false;
		} 
	   if(success){
		users=usersService.selectUsersByPh(userPhoneNumber);
		if(users!=null){
		users.setUserPassword(userPassword);
		users.setUserPhoneNumber(userPhoneNumber);
		success=usersService.updateUsersByid(users);
			if(success==false){
				code=UserCode.updateFalse;
				message="用户修改失败 !";
			}
		}else{
			code=UserCode.noExistBean;
			message="没有该用户!";
			success=false;
		}
	   }		
		json.put("code", code);
		json.put("message", message);
		out.print(json.toString());
		logger.info("用户修改的出参:"+json);
		return null;
	}
	
	/**
	 * 根据id查询
	 * @param userId 用户Id
	 * @return users 用户对象
	 * @throws IOException 
	 */
	
	public String getUsersByid() throws IOException{
		code=OK;
		message =null;      
		success =true;
		JSONObject json=new JSONObject();
		JSONObject json2=new JSONObject();
		JSONArray array2=new JSONArray();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("用户查询的入参");
		
//		HttpServletRequest request=ServletActionContext.getRequest();
//		request=MyRequest.getRequest(request);
//		HttpServletResponse response=ServletActionContext.getResponse();
//		PrintWriter out=MyRequest.getResponse(response);	
//		MyRequest.printParameterNames("用户查询的入参",request);
		
		String userId=request.getParameter("userId");
		Users users=null;
		if(XATools.isNull(userId)){
			code=UserCode.emptyId;
			message="用户Id不能为空!";
			success =false;
		}
		if(!XATools.isInteger(userId)){
			code=UserCode.formatisInconsistent;
			success=false;
			message="用户ID格式不符 ";
		}
		if(success){
			users=usersService.selectUsersByid(Integer.parseInt(userId));
			if(users!=null){
				request.getSession().setAttribute("users", users);
				json2.put("userId", users.getUserId());
				json2.put("userName", users.getUserName());
				json2.put("userPassword", users.getUserPassword());
				json2.put("userPhoneNumber", users.getUserPhoneNumber());
				json2.put("userSex", users.getUserSex()); 
				array2.add(json2);
			}else{
				code=UserCode.noExistBean;
				message="没有该用户!";
			}
			
		}
		json.put("code", code);
		json.put("message", message);
		json.put("result", array2);
		out.print(json.toString());
		logger.info("用户查询的出参:"+json);
		return null;
		
	}
	
	/**
	 * 分页查询
	 * @param groupId 家庭组id
	 * @param pageNow 当前页面数
	 * @param showPage 每页显示最大页数
	 * @return usersList 用户对象集合
	 * @throws IOException 
	 */
	public String findUsers() throws IOException{
		message =null;      
		success =true;
		code=OK;
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		String groupId=request.getParameter("groupId");
		String pages=request.getParameter("pageNow");
		String showPage=request.getParameter("showPage");
		Familygroup fg=null;
		if(groupId !=null && !"".equals(groupId)){
			fg=familyService.getFamilygroupByid(Integer.parseInt(groupId));
			
		}
		int count=usersService.getUsersCountByfamily(fg);
		JSONArray array=new JSONArray();
		if(count !=0){
			Page page=new Page();
			page.setPageNow(Integer.parseInt(pages));
			page.setTotal(count);
			page.setShowPage(Integer.parseInt(showPage));
			int offer=page.getOfferset();
			int totalPage=page.gettotalPage();
			int pageNow=page.getpageNow();
			List<Users> usersList=usersService.queryUsersByfamily(fg, Integer.parseInt(showPage), offer);
			if(usersList.size()>0){
				for (Users users : usersList) {
					JSONObject json=new JSONObject();
					json.put("userId", users.getUserId());
					json.put("userName", users.getUserName());
					json.put("userPassword", users.getUserPassword());
					json.put("accessper", users.getAccessper());
					json.put("userPhoneNumber", users.getUserPhoneNumber());
					json.put("userSex", users.getUserSex());
					json.put("createTime", users.getCreateTime());
					json.put("loginLastTimes", users.getLoginLastTimes());
					json.put("totalPage", totalPage);
					json.put("pageNow", pageNow);
					array.add(json);
				}
			}
			out.print(array.toString());
		}
		return null;
	}
	
	

	/**
	 * 版本号检查
	 * @param versionNumber 版本号
	 * @param versionPackage 版本包
	 * @return users 用户对象
	 * @throws IOException 
	 */
	
	public String checkVersion() throws IOException{
		code=OK;
		message =null;      
		success =true;
		String path="";
		JSONObject json=new JSONObject();
		JSONObject json2=new JSONObject();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("版本号检查的入参");
	//	String versionNumber=request.getParameter("versionNumber");
		String versionPackage=request.getParameter("versionPackage");
//		if(XATools.isNull(versionNumber)){
//			code=VersionCode.emptyVersion;
//			message="版本号不能为空!";
//			success =false;
//		}
		if(XATools.isNull(versionPackage)){
			code=VersionCode.emptyPackage;
			message="版本包名不能为空!";
			success =false;
		}
		if(success){
		Versions version =versionService.selectMaxVersions(versionPackage);
		if(version!=null){
			path=request.getContextPath()+version.getVersionUrl();
				json2.put("url", path);
				json2.put("versionNumber", version.getVersionNumber());
				json2.put("upgradeClass", version.getUpgradeClass());
		}else{
			code=VersionCode.noExistBean;
			message="没有该包名!";	
		}
		
		}
		json.put("code", code);
		json.put("message", message);
		json.put("result", json2);
		out.print(json.toString());
		logger.info("版本号检查的出参:"+json);
		return null;
		
	}
	
	
	
	
}
