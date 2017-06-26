package com.xiaoai.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.xiaoai.entity.Administrate;





/**
 * 登入过滤器
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class LoginFilter extends HttpServlet implements Filter {

	
	

	public boolean test(int node) {
		
		return false;
	}

	
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		HttpSession session=request.getSession();
		String url=request.getServletPath();
		String contextPath=request.getContextPath();
		if(url.equals("")){
			url+="/";
		}
		if((url.startsWith("/")&&!url.startsWith("/jsp/login.jsp"))){//若访问后台资源 过滤到login    
			Administrate admin=(Administrate)session.getAttribute("admin");
			System.out.println("你服饰风格");
			if(admin == null){//转入管理员登陆页面   
				 response.sendRedirect(contextPath+"/jsp/login.jsp");   
				 return ;
			}
		}
		arg2.doFilter(arg0, arg1);
	}

	
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}

}