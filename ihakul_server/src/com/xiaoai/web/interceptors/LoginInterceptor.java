package com.xiaoai.web.interceptors;

import java.io.PrintWriter;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;


import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xiaoai.entity.Administrate;
/**
 * 登入拦截器
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class LoginInterceptor extends AbstractInterceptor {

	public String intercept(ActionInvocation invocation) throws Exception {
	     //取得请求相关的ActionContext实例    
          
        HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		HttpSession session= request.getSession();
        //取出名为admin的Session属性    
        Administrate admin=(Administrate) session.getAttribute("admin");
        //如果没有登陆，都返回重新登陆    
        if (admin != null)    
        {    
            return invocation.invoke();    
        }else{
        	 
			  out.print("<script>alert(window.location.href('login.jsp'))</script>");
			  out.flush(); 
			  return "error";
        }    
       
            
       
       

		
	}

}
