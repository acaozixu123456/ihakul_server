package com.xiaoai.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.xiaoai.entity.Channel;
import com.xiaoai.entity.Familygroup;
import com.xiaoai.service.IChannelService;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.util.Page;
import com.xiaoai.util.XATools;
import com.xiaoai.util.XiaoaiMessage.ChannelCode;
import com.xiaoai.util.XiaoaiMessage.FamilyCode;
import com.xiaoai.util.XiaoaiMessage.UserCode;
import com.xiaoai.util.XiaoaiMessage.XiaoiCode;
import com.xiaoai.util.XiaoiResult;
/**
 * 频道管理操作实现类
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@Controller("channelAction")
@Scope("prototype")
public class AppChannelAction extends ActionSupport{
	
	@Resource(name="channelService")
	private IChannelService channelService;
	@Resource(name="familyService")
	private IFamilygroupService familyService;
	
	private boolean fals ;         //成功、失败标记
	private String message ;       //信息
	/**
	 * 添加频道信息
	 * @param groupId 家庭组id
	 * @param chanName 频道名
	 * @param chanNumber 频道号
	 * @return fals=true(频道添加成功)或者fals=false(频道添加失败)
	 * @throws IOException 
	 */
	public String insertOne() throws IOException{
		fals=true;
		message=null;
		JSONObject json=new JSONObject();
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String chanName=request.getParameter("chanName");
		String chanNumber=request.getParameter("chanNumber");
		String groupId=request.getParameter("groupId");
		Familygroup family=null;
		if(XATools.isNull(groupId)){
			fals=false;
			message="家庭组Id不能为空";
		}
		if(XATools.isNull(chanName)){
			fals=false;
			message="频道名称不能为空";
		}
		if(XATools.isNull(chanNumber)){
			fals=false;
			message="频道编号不能为空";
		}
		if(fals){
			family=familyService.getFamilygroupByid(Integer.parseInt(groupId));
			Channel channel=new Channel();
			if(family !=null){ //判断是否有该家庭组
				channel.setFamilygroup(family);
				channel.setChanName(chanName);
				channel.setChanNumber(chanNumber);
				fals=channelService.insertChannel(channel);
				if(fals==false){
					message="保存频道信息失败";	
				}
			}else{
				fals=false;
				message="没有该家庭组id";
			}		
		}
		json.put("fals", fals);
		json.put("Message", message);
		out.print(json.toString());
		out.flush();
		return null;
	}
	
	/**
	 * 添加所有频道信息（集合）
	 * @param groupId 家庭组id
	 * @param chanName 频道名
	 * @param chanNumber 频道号
	 * @return fals=true(频道添加成功)或者fals=false(频道添加失败)
	 * @throws IOException 
	 */
	public String insertAll() throws IOException{
		JSONObject json=new JSONObject();
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String channels=request.getParameter("channels");
		String groupNumber=request.getParameter("groupNumber");
		String userId=request.getParameter("userId");
		String xiaoNumber=request.getParameter("xiaoNumber");
		Familygroup family=null;
		XiaoiResult xr = new XiaoiResult();
		
		//参数验证
		if(XATools.isNull(groupNumber)){
			xr = XiaoiResult.build("家庭组编号不能为空", FamilyCode.emptyId);
		}else{
			if(!XATools.isInteger(groupNumber)){
				xr = XiaoiResult.build("家庭组编号格式不符", FamilyCode.formatisInconsistent);
			}
		}
		if(XATools.isNull(channels)){
			xr = XiaoiResult.build("频道集合不能为空",ChannelCode.emptyContent);
		}
		if(XATools.isNull(userId)){
			xr = XiaoiResult.build("用户id不能为空",UserCode.emptyId);
		}
		if(XATools.isNull(xiaoNumber)){
			xr = XiaoiResult.build("终端编号不能为空",XiaoiCode.emptyId);
		}
		if(xr.isSuccess()){
			family=familyService.getFamilygroupByNumber(Integer.parseInt(groupNumber));
			Channel channel=new Channel();
			if(family !=null){ //判断是否有该家庭组
				//解析json
				try {
					com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
					com.alibaba.fastjson.JSONObject channel_ = jsonObject.getJSONObject(channels);
					
					String chanName =channel_.getString("chanName");
					String chanNumber =channel_.getString("chanNumber");
					
					channel.setChanName(chanName);
					channel.setChanNumber(chanNumber);
					channel.setFamilygroup(family);
					
					xr.setSuccess(channelService.insertChannel(channel));
				} catch (Exception e) {
					xr = XiaoiResult.build("新增频道失败", ChannelCode.insertFail);
					e.printStackTrace();
				}
				
				if(xr.isSuccess()){
					xr = XiaoiResult.build("没有该家庭组", FamilyCode.noExistBean);
				}
			}else{
				xr = XiaoiResult.build("没有该家庭组", FamilyCode.noExistBean);
			}		
		}
		json.put("code", xr.getCode());
		json.put("message", xr.getMessage());
		out.print(json.toString());
		out.flush();
		return null;
	}
	
	/**
	 * 删除频道信息
	 * @param id 频道id
	 * @return fals=true(频道删除成功)或者fals=false(频道删除失败)
	 * @throws IOException 
	 */
	public String delete() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String id=request.getParameter("id");
		Channel channel=null;
		if(id !=null && ! id.equals("") ){
			
			channel=channelService.getChannelByid(Integer.parseInt(id));
		}
		boolean fals=false;
		if(channel !=null){
			fals=channelService.delectChannel(channel);
		}
		JSONObject json=new JSONObject();
		json.put("fals", fals);
		out.print(json.toString());
		out.flush();
		return null;
	}
	/**
	 * 修改频道信息
	 * @param chanName 频道名
	 * @param chanNumber 频道号
	 * @param id 频道id
	 * @return fals=true(频道修改成功)或者fals=false(频道修改失败)
	 * @throws IOException 
	 */
	public String update() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String chanName=request.getParameter("chanName");
		String chanNumber=request.getParameter("chanNumber");
		String id=request.getParameter("id");
		Channel channel=null;
		if(id !=null && ! id.equals("")){
			channel=channelService.getChannelByid(Integer.parseInt(id));
		}
		if(chanName !=null && ! chanName.equals("")){
			channel.setChanName(chanName);
		}
		if(chanNumber !=null && ! chanNumber.equals("")){
			channel.setChanNumber(chanNumber);
		}
		boolean fals=false;
		if(channel !=null ){
			fals=channelService.updateChannel(channel);
		}
		JSONObject json=new JSONObject();
		json.put("fals", fals);
		out.print(json.toString());
		out.flush();
		return null;
	}
	/**
	 * 查询家庭组下的频道信息
	 * @param groupId 家庭组id
	 * @param pageNow 页面显示当前页
	 * @param showPage 页面显示最大记录数
	 * @return chlist(频道对象集合),pageNow(当前页),totalPage(最大页数)
	 * @throws IOException 
	 */
	public String query() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String pageNow=request.getParameter("pageNow");
		String showPage=request.getParameter("showPage");
		String id=request.getParameter("id");
		Familygroup family=null;
		Channel channel =new Channel();
		if(id !=null && ! id.equals("")){
			family=familyService.getFamilygroupByid(Integer.parseInt(id));
		}
		if(family !=null){
			channel.setFamilygroup(family);
		}
		int count=channelService.getCountChannels(channel);
		Page page =new Page();
		page.setTotal(count);
		if(pageNow !=null && ! pageNow.equals("")){
			page.setPageNow(Integer.parseInt(pageNow));
		}
		if(showPage !=null && ! showPage.equals("")){
			page.setShowPage(Integer.parseInt(showPage));
		}
		
		int begin=page.getOfferset();
		int totalPage=page.gettotalPage();
		int pages=page.getpageNow();
		List<Channel> chlist=channelService.selectChannels(begin, Integer.parseInt(showPage), channel);
		JSONArray array=new JSONArray();
		if(chlist.size()>0){
			for (Channel channel2 : chlist) {
				JSONObject json=new JSONObject();
				json.put("cid", channel2.getCid());
				json.put("chanName", channel2.getChanName());
				json.put("chanNumber", channel2.getChanNumber());
				json.put("pageNow", pages);
				json.put("totalPage", totalPage);
				array.add(json);
			}
		}
		out.print(array.toString());
		out.flush();
		return null;
	}
	
	
}
