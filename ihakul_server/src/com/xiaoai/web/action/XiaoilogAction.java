package com.xiaoai.web.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.xiaoai.entity.Xiaoi;
import com.xiaoai.entity.Xiaoilog;
import com.xiaoai.service.IXiaoiService;
import com.xiaoai.service.IXiaoilogService;
import com.xiaoai.util.Page;
@SuppressWarnings("serial")
@Controller("xiaoilogAction")
@Scope("prototype")
public class XiaoilogAction extends ActionSupport{
	
	@Resource(name="xiaoilogService")
	private IXiaoilogService xiaoilogService;
	
	@Resource
	private IXiaoiService xiaoiService;
	
	private List<Xiaoilog> xiaoilogList;
	
	private Xiaoi xiaoi;
	private String xiaoiNumber;
	private Page page=new Page();
	private int pageNow;//当前页
	private int showPage;//每页显示记录数
	private int offset;//开始记录数
	private int total;//总记录数
	private int totalPage;//总页数
	
	private boolean fals;
	/**
	 * 查询工作日志
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	public String select() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
	    String xiaoiNumber=request.getParameter("xiaoiNumber");
		
		xiaoi=xiaoiService.selectXiaoiByNumber(xiaoiNumber);
		Xiaoilog xiaoilog=new Xiaoilog();
		if(xiaoi !=null){
			xiaoilog.setXiaoi(xiaoi);
		}
		//得到总记录数
		int total=xiaoilogService.getCountXiaoilog(xiaoilog);
		page.setTotal(total);
		page.setShowPage(showPage);
		page.setPageNow(pageNow);
		offset=page.getOfferset();//得到开始记录数
		pageNow=page.getpageNow();//得到当前页数
		totalPage=page.gettotalPage();//得到总页数
		xiaoilogList=xiaoilogService.selectXiaoilog(xiaoilog, showPage, offset);
		request.setAttribute("xiaoilogList",xiaoilogList);
		request.setAttribute("xiaoi",xiaoi);
		return "success";
		
	}
	/**
	 * 导出报表
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unused" })
	public void exportExcel() throws Exception{
		boolean fals=true;
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
		response.setCharacterEncoding("utf-8");
		PrintWriter out1 = response.getWriter();
		String xiaoNumber=request.getParameter("xiaoNumber");
		xiaoi=xiaoiService.selectXiaoiByNumber(xiaoNumber);
		if(xiaoi!=null){
		Xiaoilog xiaoilog=new Xiaoilog();
		xiaoilog.setXiaoi(xiaoi);
		HSSFWorkbook workbook =xiaoilogService.exportExecl(xiaoilog);
		//创建一个HttpServletResponse对象 
		response.reset();
		ServletOutputStream  out = response.getOutputStream();//创建一个输出流对象 
		//filename是下载的xls的名
		response.setHeader("Content-disposition","attachment; filename="+"xiaoilog.xls");
		response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
		response.setHeader("Pragma","No-cache");//设置头 
		response.setHeader("Cache-Control","no-cache");//设置头 
		response.setDateHeader("Expires", 0);//设置日期头 
		workbook.write(out); 
		out.flush(); 
		workbook.write(out); 
		if(out !=null){
			out.close();
		}
		}else{
			out1.print("<script>alert('没有选择小艾报表信息,无法导出报表')</script>");
			out1.print("<script>window.location.href='showXiaolog.action?=1pageNow&showPage=5'</script>");
			out1.flush(); 
		}
	}	
	/**
	 * 添加
	 * @return
	 * @throws IOException
	 */
	public String insert() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		
		String xiaoId=request.getParameter("xiaoId");
		String usingDetails=request.getParameter("usingDetails");
		
		Xiaoilog xiaoilog=new Xiaoilog();
		if(xiaoId !=null && ! xiaoId.equals("")){
			Xiaoi xiaoi=xiaoiService.getXiaoiByid(Integer.parseInt(xiaoId));
			xiaoilog.setXiaoi(xiaoi);
		}
		if(usingDetails !=null && ! usingDetails.equals("")){
			xiaoilog.setUsingDetails(usingDetails);
		}
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		
		xiaoilog.setUserTime(Timestamp.valueOf(sdf.format(date)));
		boolean fals=xiaoilogService.insertXiaoilog(xiaoilog);
		JSONObject json=new JSONObject();
		json.put("fals", fals);
		out.print(json.toString());
		return null;
	}
		
		
	
	public String getXiaoiNumber() {
		return xiaoiNumber;
	}
	public void setXiaoiNumber(String xiaoiNumber) {
		this.xiaoiNumber = xiaoiNumber;
	}
	public List<Xiaoilog> getXiaoilogList() {
		return xiaoilogList;
	}
	public void setXiaoilogList(List<Xiaoilog> xiaoilogList) {
		this.xiaoilogList = xiaoilogList;
	}
	public Xiaoi getXiaoi() {
		return xiaoi;
	}
	public void setXiaoi(Xiaoi xiaoi) {
		this.xiaoi = xiaoi;
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
	
}
