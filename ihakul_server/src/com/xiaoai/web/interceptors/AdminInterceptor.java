package com.xiaoai.web.interceptors;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.xiaoai.entity.Privilege;
import com.xiaoai.util.XATools;
/**
 * 管理员权限拦截器
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class AdminInterceptor extends MethodFilterInterceptor{

	
	@SuppressWarnings("unchecked")
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		  HttpServletRequest request=ServletActionContext.getRequest();
		  HttpServletResponse response=ServletActionContext.getResponse();
		  request.setCharacterEncoding("UTF-8");
		  response.setCharacterEncoding("UTF-8");
		  PrintWriter out=response.getWriter();
		  HttpSession session= request.getSession();
		  String result="";
		  List<Privilege> list=(List<Privilege>) session.getAttribute("privilege");
		  if(XATools.isNull(list)){ //判断是否为空
			  result=invocation.invoke();  //执行Action方法  
			  return result;
		  }else{
			  out.print("<script>alert('你是普通管理员，你没有此权限')</script>");
			  out.print("<script>alert(window.location.href('main.jsp'))</script>");
			  out.flush(); 
			return "error";
		  }
		  
		
	}
	
}
