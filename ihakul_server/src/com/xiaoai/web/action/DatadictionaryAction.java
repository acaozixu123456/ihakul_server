package com.xiaoai.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.xiaoai.entity.Datadictionary;
import com.xiaoai.service.IDatadictionaryService;
import com.xiaoai.util.Page;
@SuppressWarnings("serial")
@Controller("datadicAction")
@Scope("prototype")
public class DatadictionaryAction extends ActionSupport{
	@Resource(name="datadicService")
	private IDatadictionaryService datadicService;
	/**
	 * 分页查询所有数据
	 * @param pageNow 页面显示当前页数(不可为空)
	 * @param showPage 页面显示最大记录数(不可为空)
	 * @param ddName 数据字典数据名字(可为空)
	 * @return dataList(数据字典对象集合)，pageNow(页面显示页数)，totalPage(页面显示最大记录数)
	 * @throws IOException 
	 */
	public String pagingFind() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String pageNow=request.getParameter("pageNow");
		String showPage=request.getParameter("showPage");
		String ddName=request.getParameter("ddName");
		Datadictionary datadic=new Datadictionary();
		if(ddName !=null && ! ddName.equals("")){
			datadic.setDdName(ddName);
		}
		int count=datadicService.getCountDatadictionary(datadic);
		Page page=new Page();
		if(pageNow !=null && ! pageNow.equals("")){
			
			page.setPageNow(Integer.parseInt(pageNow));
		}
		if(showPage !=null && !showPage.equals("")){
			page.setShowPage(Integer.valueOf(showPage));
		}
		if(count !=0){
			page.setTotal(count);
		}
		
		int begin=page.getOfferset();
		int pageNow2=page.getpageNow();
		int totalPage=page.gettotalPage();
		if(Integer.parseInt(pageNow) >totalPage){
			pageNow2=totalPage;
		}
		List<Datadictionary> dataList=datadicService.paginFind(begin, Integer.valueOf(showPage), datadic);
		if(dataList.size()>0){
			request.setAttribute("dataList", dataList);
			request.setAttribute("pageNow", pageNow2);
			request.setAttribute("totalPage", totalPage);
			return "success";
		}else{
			out.print("<script>alert('对不起，您还没有添加数据字典数据')</script>");
			out.flush();
			return "error";
		}
		
	}
	
	
	/**
	 * 查询所有数据
	 * @return ddlist 数据字典对象集合
	 * @throws UnsupportedEncodingException
	 */
	public String find() throws UnsupportedEncodingException{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List<Datadictionary> ddlist=datadicService.findAll();
		if(ddlist.size()>0){
			request.setAttribute("ddlist", ddlist);
			return "success";
		}else{
			return "effor";
		}
		
	}
	
	/**
	 * 添加
	 * @param ddName 数据描述名
	 * @return fals=false(添加失败)或者fals=true(添加成功)
	 * @throws IOException 
	 */
	public String insert() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String ddName=request.getParameter("ddName");
		Datadictionary datadictionary=new Datadictionary();
		if(ddName !=null && ! ddName.equals("")){
			datadictionary.setDdName(ddName);
		}
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String nowTime=sdf.format(date);
		datadictionary.setCreateTime(Timestamp.valueOf(nowTime));
		boolean fals=datadicService.insert(datadictionary);
		if(fals){
			//返回页面的数据
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			request.setAttribute("fals", fals);
			out.print("<script>alert('添加成功')</script>");
			out.flush();
			return "success";
		}else{
			//返回页面的数据
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			
			out.print("<script>alert('添加失败')</script>");
			out.flush();
			return "error";
		}
		
	}
	/**
	 * 删除数据字典数据
	 * @param id 数据字典id
	 * @return fals=false(删除失败)或者fals=true(删除成功)
	 * @throws IOException 
	 */
	public String delete() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String id=request.getParameter("id");
		Datadictionary datadiction=null;
		if(id !=null && !id.equals("")){
			datadiction=datadicService.selectDatadictionaryByid(Integer.parseInt(id));
		}
		boolean fals=false;
		if(datadiction !=null){
			fals=datadicService.delete(datadiction);
		}
		if(fals){
			//返回页面的数据
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			out.print("<script>alert('删除成功')</script>");
			out.flush();
			return "success";
		}else{
			//返回页面的数据
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			out.print("<script>alert('删除失败')</script>");
			out.flush();
			return "error";
		}
		
	}
	
	
	
	
	
}
