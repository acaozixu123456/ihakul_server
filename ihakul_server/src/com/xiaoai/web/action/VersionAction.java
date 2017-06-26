package com.xiaoai.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.xiaoai.entity.Versions;
import com.xiaoai.service.IVersionService;
import com.xiaoai.util.Page;
import com.xiaoai.util.XATools;
@SuppressWarnings("serial")
@Controller("versionAction")
public class VersionAction extends ActionSupport {
	
	@Resource(name="versionService")
	private IVersionService versionService;
	
	private Page page=new Page();
	private int pageNow;//当前页
	private int showPage;//每页显示记录数
	private int offset;//开始记录数
	private int total;//总记录数
	private int totalPage;//总页数
	
	
	 private File file;//文件
	 private String fileFileName;//文件名称    
	 private String fileContentType; //文件类型   
	 private String filename;
	 private String path;
	/**
	 * 分页查询所有
	 * @param versionNumber 版本号 (可为空)
	 * @param upgradeClass 更新内容
	 * @param versionPackage 版本包名
	 * @return versionList(版本对象集合),pageNow(当前页),totalPage(总页数)
	 * @throws Exception 
	 */
	public String findAllVersion() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String versionNumber=request.getParameter("versionNumber");
	//	String upgradeClass=request.getParameter("upgradeClass");
		String versionPackage=request.getParameter("versionPackage");
		Versions version=new Versions();
		if(!XATools.isNull(versionNumber)){
			version.setVersionNumber(Integer.parseInt(versionNumber));
		}
		if(!XATools.isNull(versionPackage)){
			version.setVersionPackage(versionPackage);
		}
//		if(!XATools.isNull(upgradeClass)){
//			version.setUpgradeClass(upgradeClass);
//		}
		int total =versionService.getCountVersion(version);
		page.setTotal(total);
		page.setShowPage(showPage);
		page.setPageNow(pageNow);
		offset=page.getOfferset();//得到开始记录数
		pageNow=page.getpageNow();//得到当前页数
		totalPage=page.gettotalPage();//得到总页数
		List<Versions> versionList=versionService.findAllVersion(version, showPage, offset);
		if(!XATools.isNull(versionList)){
			request.setAttribute("versionList", versionList);
		}
		return "success";
		
	}
	
	/**
	 * 文件上传，添加版本
	 * @param versionNumber 版本号
	 * @param versionPackage 版本包名
	 * @param upgradeClass 更新内容
	 * @param versionName 版本名字
	 * @param versionType 版本类型
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	public String uploadVersion() throws Exception{
		boolean fals;
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter print=response.getWriter();
		String npath=request.getServerName();
		Versions version1=new Versions();
		String versionNumber=request.getParameter("versionNumber");
		String versionPackage=request.getParameter("versionPackage");
		String upgradeClass=request.getParameter("upgradeClass");
		String versionName=request.getParameter("versionName");
		String versionType=request.getParameter("versionType");
		List<Versions> verlist=null;
		version1.setVersionNumber(Integer.parseInt(versionNumber));
		version1.setVersionPackage(versionPackage);
		verlist=versionService.selectVersionByvr(version1);
		if(XATools.isNull(verlist)){ //如果该版本在数据库不存在
		fals=fileOut();
		if(fals){
	    //封装到对象当中
        Versions version=new Versions();
        version.setVersionUrl(path);
        version.setUpgradeClass(upgradeClass);
        version.setVersionNumber(Integer.parseInt(versionNumber));
        version.setVersionPackage(versionPackage);
        version.setVersionType(Integer.parseInt(versionType));
        version.setVersionName(versionName);
         fals=versionService.insertVersion(version);
        if(fals){
        	//控制页面加载时只刷新一次
        	int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
        	print.print("<script>alert('添加成功')</script>");
        	print.flush();
        	return "success";
        }else{
        	int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
        	print.print("<script>alert('添加失败')</script>");
        	print.flush();
        	return "error";
        }
		}else{
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
        	print.print("<script>alert('添加失败')</script>");
        	print.flush();
        	return "error";
        }
		}else{
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
        	print.print("<script>alert('添加失败,已有该版本')</script>");
        	print.flush();
        	return "error";
        }
	}
	
	
	/**
	 * 文件保存 
	 * @throws  IOException 
	 **/
	public  boolean fileOut() throws  IOException{
		String path1="";
		String url1="";
	//	String newfileName=XATools.getNowTime("yyyyMMddHHmmss");
		String url=ServletActionContext.getServletContext().getRealPath("");
	//	String url=XATools.getLocalHostIp()+":"+XATools.getLocalPort();
		
		path="/upload/" +fileFileName;
	//	path="/upload/" +newfileName;
		url1=url+"/upload/";
		File dir=new File(url1);  
	        //判断文件是否有该目录，如果没有的话将会创建该目录  
	        if(!dir.exists()){  
	            dir.mkdir(); //创建该目录  
	        }  
	        //声明文件输入流，为输入流指定文件路径  
	        FileInputStream in=null;  
	        //获取输出流，获取文件的文件地址及名称  
	        FileOutputStream out=null;
	        path1=url+path;
//	        path1=newsuffix+path;
	        try{  
	            in= new FileInputStream(file);    
	         //   out=new FileOutputStream(path);
	            out=new FileOutputStream(path1);
	        //  path=web+path;
	            byte[] b=new byte[1024*1024];//每次写入的大小  
	            int i=0;  
	            while((i=in.read(b))>0){  
	                out.write(b,0,i);  
	            } 
	            in.close();
	            out.close(); 
	        }catch(Exception e){  
	            e.printStackTrace(); 
	            return false;	
	        }
		return true;		
	}
	
	
	/**
	 * 根据id查询
	 * @param id 版本id
	 * @return version 版本对象
	 * @throws UnsupportedEncodingException 
	 */
	public String findVersionByid() throws UnsupportedEncodingException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		String id=request.getParameter("id");
		Versions version=versionService.selectVersionByid(Integer.parseInt(id));
		if(version !=null){
			request.setAttribute("version", version);
			return "success";
		}else{
			return "error";
		}
	}
	/**
	 * 修改
	 * @param versionNumber 版本号
	 * @param upgradeClass 更新类容
	 * @param versionName 版本名字
	 * @param versionType 版本类型
	 * @param versionUrl 最新版本链接地址
	 * @param id 版本id
	 * @return fals=true(修改成功)或者fals=false(修改失败)
	 * @throws Exception 
	 */
	public String updateVersion() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String versionNumber=request.getParameter("versionNumber");
		String upgradeClass=request.getParameter("upgradeClass");
		String versionName=request.getParameter("versionName");
		String versionType=request.getParameter("versionType");
		String versionUrl=request.getParameter("versionUrl");
		String id=request.getParameter("id");
		Versions version=versionService.selectVersionByid(Integer.parseInt(id));
		version.setVersionNumber(Integer.parseInt(versionNumber));
		version.setVersionName(versionName);
		version.setVersionType(Integer.parseInt(versionType));
		version.setVersionUrl(versionUrl);
		version.setUpgradeClass(upgradeClass);
		boolean fals=versionService.updateVersion(version);
		if(fals){
			int refreshNumber=1;
			request.setAttribute("refresh", refreshNumber);
			out.print("<script>alert('修改成功')</script>");
			out.flush();
			return "success";
		}else{
			out.print("<script>alert('修改失败')</script>");
			out.flush();
			return "error";
		}
		
	}
	/**
	 * 版本删除
	 * @param id 版本id
	 * @return fals=true(删除成功)或者fals=false(删除失败)
	 * @throws Exception 
	 */
	public String deleteVersion() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		String id=request.getParameter("id");
		Versions version=versionService.selectVersionByid(Integer.parseInt(id));
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		boolean fals=versionService.deleteVrtsion(version);
		JSONObject json=new JSONObject();
		int refreshNumber=1;
		request.setAttribute("refresh", refreshNumber);
		json.put("fals", fals);
		out.print(json.toString());
		out.flush();
		return "success";
		
		
	}
	
	/**
	 * 提供给安卓客户端查询，并返回最新的版本提供给终端更新的接口
	 * @return version 版本对象
	 * @throws IOException 
	 */
	public String appQuary() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		List<Versions> verlist=versionService.selectVersion();
		JSONObject json=new JSONObject();
		if(verlist.size()>0){
			String versionNumber="";
			for(int i=0;i<verlist.size();i++){
				String number1=verlist.get(i).getVersionNumber().toString();
				String number2=verlist.get(i+1).getVersionNumber().toString();
				if(Double.parseDouble(number1)>Double.parseDouble(number2)){
					versionNumber=number1;
				}else{
					versionNumber=number2;
				}
			}
			Versions versions=new Versions();
			if(versionNumber !=null && !versionNumber.equals("")){
				versions.setVersionNumber(Integer.parseInt(versionNumber));
			}
			List<Versions> list=versionService.selectVersionByverNumber(versions);
			if(list.size()>0){
				json.put("version", list.get(0));
			}
		}
		out.print(json.toString());
		return null;
	}
	
	/**
	 * 判断版本号是否有唯一，不唯一这返回false,唯一返回true
	 * @return
	 * @throws IOException
	 */
	public String queryNumber() throws IOException{
		JSONObject json=new JSONObject();
		boolean fals=false;
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String versionNumber=request.getParameter("versionNumber");
		List<Versions> verList=null;
		Versions version=new Versions();
		if(versionNumber !=null && !versionNumber.equals("")){
			version.setVersionNumber(Integer.parseInt(versionNumber));
			verList=versionService.selectVersionByverNumber(version);
			if(!XATools.isNull(verList)){
				fals=true;
			}
		}
		json.put("fals", fals);
		out.print(json.toString());
		return null;
	}
	
	
	/**
	 * 判断版本号在该包名下是否有唯一，不唯一这返回false,唯一返回true
	 * @return
	 * @throws IOException
	 */
	public String queryPackageNumber() throws IOException{
		JSONObject json=new JSONObject();
		boolean fals=false;
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String versionNumber=request.getParameter("versionNumber");
		String versionPackage=request.getParameter("versionPackage");
		List<Versions> verList=null;
		Versions version=new Versions();
		if(!XATools.isNull(versionNumber)&&!XATools.isNull(versionPackage)){
			version.setVersionNumber(Integer.parseInt(versionNumber));
			version.setVersionPackage(versionPackage);
			verList=versionService.selectVersionByvr(version);
			if(XATools.isNull(verList)){
				fals=true;
			}
		}
		json.put("fals", fals);
		out.print(json.toString());
		out.flush();
		return null;
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
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

	
	
	
}
