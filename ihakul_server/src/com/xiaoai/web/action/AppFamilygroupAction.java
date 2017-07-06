package com.xiaoai.web.action;



import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiaoai.entity.FamilyUser;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Users;
import com.xiaoai.service.IFamilyUserService;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.service.IUsersService;
import com.xiaoai.util.MyRequest;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage;
import com.xiaoai.util.XiaoiResult;
/**
 * 家庭组操作实现类
 * 供安卓调用
 * @author Administrator
 *
 */
@Controller("appFamilyAction")
@Scope("prototype")
public class AppFamilygroupAction extends XiaoaiMessage{
	@Resource(name="familyService")
	private IFamilygroupService familyService;
	@Resource(name="usersService")
	private IUsersService usersService;
	@Resource(name="fauserService")
	private IFamilyUserService fauserService;

	private boolean success ;         //成功、失败标记
	private  String message ;        //信息
	private int code ;				 //标记
	private static Logger logger = Logger.getLogger(AppFamilygroupAction.class);
	
	/**
	 * 添加家庭组
	 * @param userId 用户Id
	 * @param groupName 家庭组名字
	 * @return success=true(添加成功)或者success=false(添加失败)
	 * @throws IOException 
	 */
	public String insert() throws IOException{
		String groupName=null,userId=null;
		XiaoiResult xiaoiResult = new XiaoiResult();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("添加家庭组的入参");
		JSONObject json=new JSONObject();
		JSONObject json2=new JSONObject();
		JSONArray array=new JSONArray();
		groupName=request.getParameter("groupName");
		userId=request.getParameter("userId");
		if(XATools.isNull(groupName)){
			xiaoiResult=XiaoiResult.build("家庭组名称不能为空", false, FamilyCode.emptyName);
		}
		if(XATools.isNull(userId)){
			xiaoiResult=XiaoiResult.build("用户Id不能为空", false, UserCode.emptyId);
		}else {
			if(!XATools.isInteger(userId)){
				xiaoiResult=XiaoiResult.build("用户ID格式不符 ", false, UserCode.formatisInconsistent);
			}
		}

	  if(xiaoiResult.isSuccess()){	
		try {
			Familygroup family=new Familygroup();
			family.setGroupName(groupName);
			family.setCreationTime(XATools.getTNowTime());
			family.setManagerId(Integer.parseInt(userId));
			family.setVersionNumber(0);
			
			xiaoiResult.setSuccess(familyService.insertFamilygroup(family));
			//所产生的家庭组编号
			int number=10000+family.getGroupId();
			String groupPassword=XATools.random(10); //生成10位随机数的验证码
			family.setGroupPassword(groupPassword);
			family.setGroupNumber(number);
			xiaoiResult.setSuccess(familyService.updateFamily(family));
			if(xiaoiResult.isSuccess()){
				Users users=null;
				users=usersService.selectUsersByid(Integer.parseInt(userId));
				if(users!=null){
				FamilyUser fu=new FamilyUser();
				fu.getUsers();
				fu.setFamilygroup(family);
				fu.setUsers(users);
				fu.setDna(userId);
				xiaoiResult.setSuccess(fauserService.insertFamilyUser(fu));		
				if(xiaoiResult.isSuccess()){ //用户绑定保存成功就正确返回
					json2.put("groupNumber", number);
					json2.put("groupPassword", groupPassword);
					array.add(json2);
				}else{
					xiaoiResult = XiaoiResult.build("用户绑定家庭组失败!", FamilyCode.conflictAbortBind);
				}
			 }else{
					xiaoiResult = XiaoiResult.build("没有该用户!",UserCode.noExistBean);
				}
			}else{
				xiaoiResult = XiaoiResult.build("家庭组添加失败!",FamilyCode.insertFalse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	  	json.put("code", xiaoiResult.getCode());
		json.put("message", xiaoiResult.getMessage());
		json.put("result", array);    //家庭组信息
		logger.info("添加家庭组的出参:"+json);
		out.print(json);
		return null;
	}
	
	
	/**
	 * 修改家庭组信息
	 * @param groupId 家庭组Id
	 * @param groupName 家庭组名字
	 * @param state 国家
	 * @param city 城市
	 * @param district 街道
	 * @param userId 用户Id
	 * @return success=true(修改成功)或者success=false(修改失败)
	 * @throws Exception 
	 */
	public String update() throws Exception{
		success =true;
		message =null;
		code=OK;
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("修改家庭组的入参");
		String groupNumber=request.getParameter("groupNumber");
		String groupName=request.getParameter("groupName");
		String userId=request.getParameter("userId");  
		Users users=null;
		Familygroup family=null;
		FamilyUser fu1 =null;
		JSONObject json=new JSONObject();
		if(XATools.isNull(groupNumber)){
			code=FamilyCode.emptyId;
			success=false;
			message="家庭组编号不能为空!";
	    }
		if(XATools.isNull(userId)){
			code=UserCode.emptyId;
			success=false;
			message="用户id不能为空";
		}	
		
		if(XATools.isNull(groupName)){
			code=FamilyCode.emptyName;
			success=false;
			message="家庭组名称不能为空";
		}	
		
		if(!XATools.isInteger(userId)){
			code=UserCode.formatisInconsistent;
			success=false;
			message="用户ID格式不符 ";
		}
		if(!XATools.isInteger(groupNumber)){
			code=FamilyCode.formatisInconsistent;
			success=false;
			message="家庭组编号格式不符 ";
		}
		if(success){
		try {
			users=usersService.selectUsersByid(Integer.parseInt(userId));
			if(users !=null){
					family=familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
					if(family !=null){
						fu1=fauserService.selectFamilyUserByGNU(users, family);
						if(fu1!=null){
					  if(userId.equals(fu1.getDna())){ //判断该家庭组是不是该用户创建					  
						family.setGroupName(groupName);
						success=familyService.updateFamily(family);
						if(success==false){
							code=FamilyCode.updateFalse;
							success=false;
							message="家庭组修改失败!";	
						}
					}else{
						code=FamilyCode.privilegeMaster;
						success=false;
						message="您不是群主，没有权限修改!";
					}
			      }else{
			    	  code=FamilyCode.noExistUser;
						success=false;
						message="该家庭组中不存在该用户";
					}			
				} else{
					code=FamilyCode.noExistBean;
					success=false;
					message="没有该家庭组";
				}		
			}else{
				code=UserCode.noExistBean;
				success=false;
				message="没有该用户!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		json.put("code", code);
		json.put("message", message);
		logger.info("修改家庭组出参:"+json);
		out.print(json);
		return null;
	}
	
	
	/**
	 * 查询当前家庭组的信息
	 * @param groupId 家庭组Id
	 * @return family 家庭组信息
	 * @throws IOException 
	 */
	public String getFamilygroupByid() throws IOException{
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("查询家庭组的入参");
		String groupNumber=request.getParameter("groupNumber");
		//json=getFamilyGroup(groupNumber);
		com.alibaba.fastjson.JSONObject familyGroup = familyService.getFamilyGroup(groupNumber);
		out.print(familyGroup);
		logger.info("查询家庭组的出参:"+familyGroup);	
		return null;
	}
	
	
	/**
	 * 删除家庭组
	 * @param userId 用户Id
	 * @param groupId 家庭组Id
	 * @return success=true(删除成功)或者success=false(删除失败)
	 * @throws IOException 
	 */
	public String delete() throws IOException{
		success =true;
		message =null;
		code=OK;
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("删除家庭组的入参");
		String groupNumber=request.getParameter("groupNumber");
		String userId=request.getParameter("userId");	
		Users users=null;
		FamilyUser fu1 =null;
		JSONObject json=new JSONObject();
		if(XATools.isNull(userId)){
			code=UserCode.emptyId;
			success=false;
			message="用户id不能为空";
		}
		if(XATools.isNull(groupNumber)){
			code=FamilyCode.emptyId;
			success=false;
			message="家庭组编号不能为空!";
		}
		if(!XATools.isInteger(userId)){
			code=UserCode.formatisInconsistent;
			success=false;
			message="用户ID格式不符 ";
		}
		if(!XATools.isInteger(groupNumber)){
			code=FamilyCode.formatisInconsistent;
			success=false;
			message="家庭组编号格式不符 ";
		}
		if(success){
		try {
			users=usersService.selectUsersByid(Integer.parseInt(userId));
			if(users !=null){
					Familygroup family=null;
					family=familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
					if(family !=null){
						fu1=fauserService.selectFamilyUserByGNU(users, family);
						if(fu1!=null){
						 if(userId.equals(fu1.getDna())){ //判断该家庭组是不是该用户创建					  
						success=familyService.deleteFamilygroup(family);
						if(success==false){
							code=FamilyCode.deleteFalse;
							success=false;
							message="家庭组删除失败!";	
						}
					}else{
						code=FamilyCode.privilegeMaster;
						success=false;
						message="您不是群主，没有权限修改!";
					}
				 }else{
					code=FamilyCode.noExistUser;
					success=false;
					message="该家庭组中不存在该用户";
					}
				}else{
					code=FamilyCode.noExistBean;
					success=false;
					message="没有该家庭组";	
				}
			}else{
				code=UserCode.noExistBean;
				success=false;
				message="没有该用户!";
			}
		} catch (Exception e) {
			code=FamilyCode.deleteFalse;
			success=false;
			message="家庭组删除失败!";
			e.printStackTrace();
		}
		}
		json.put("code", code);
		json.put("message", message);
		logger.info("删除家庭组的出参:"+json);
		out.print(json);
		return null;
	}


	/**
	 * 得到所有家庭组（终端调用接口）
	 * @return
	 * @throws Exception 
	 */
	public String getAllToXiaoi() throws Exception{
		XiaoiResult xr = new XiaoiResult();
		HttpServletRequest request=MyRequest.getRequest();
		PrintWriter out=MyRequest.getResponse();	
		MyRequest.printParameterNames("获得所有家庭组的入参（终端接口）");
		//获得参数
		String groupNumber=request.getParameter("groupNumber");
		com.alibaba.fastjson.JSONObject jsonObject = null;
		try {
			//判断
			if(XATools.isNull(groupNumber)){
				xr = XiaoiResult.build("家庭组编号不能为空！", FamilyCode.emptyId);
			}
			if(xr.isSuccess()){
				jsonObject = familyService.getFamilyGroupByXiaoi(groupNumber);
			}
			
		} catch (Exception e) {
			xr = XiaoiResult.build("未知错误！", Other);
			e.printStackTrace();
		}
		
		logger.info("获得所有家庭组的出参（终端接口）:"+jsonObject);
		out.print(jsonObject);
		return null;
	}
	
}
