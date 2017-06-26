package com.xiaoai.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;
	


import com.xiaoai.entity.Familygroup;
import com.xiaoai.entity.Room;
import com.xiaoai.entity.Users;
import com.xiaoai.service.IFamilygroupService;
import com.xiaoai.service.IRoomService;
import com.xiaoai.service.IUsersService;

import com.xiaoai.util.Page;

@Controller("roomAction")
public class RoomAction {
	@Resource(name="roomService")
	private IRoomService roomService;
	@Resource(name="familyService")
	private IFamilygroupService familyService;
	@Resource(name="usersService")
	private IUsersService usersService;
	/**
	 * 分页查询房间信息
	 * @param page 页面显示当前页
	 * @param showPage 页面显示最大记录数
	 * @param groupId 家庭组id
	 * @return roomList(家庭组对象集合),pageNow(当前页面记录数),totalPage(总页数)
	 * @throws IOException 
	 */
	public String findAllRoom() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("uf-8");
		PrintWriter out=response.getWriter();
		String page=request.getParameter("page");
		String showPage=request.getParameter("showPage");
		String groupId=request.getParameter("groupId");
		Room room =new Room();
		if(groupId !=null &&! groupId.equals("")){
			Familygroup family=familyService.getFamilygroupByid(Integer.parseInt(groupId));
			room.setFamilygroup(family);
		}
		int count=roomService.getCountRoom(room);
		Page pages=new Page();
		pages.setPageNow(Integer.parseInt(page));
		pages.setShowPage(Integer.parseInt(showPage));
		pages.setTotal(count);
		int pageNow= pages.getpageNow();
		int begin=pages.getOfferset();
		int totalPage=pages.gettotalPage();
		List<Room> roomList=roomService.selectRoom(room, Integer.parseInt(showPage), begin);
		JSONArray array=new JSONArray();
		if(roomList.size()>0){
			for (Room rooms : roomList) {
				JSONObject json=new JSONObject();
				json.put("id", rooms.getId());
				json.put("roomName", rooms.getRoomName());
				json.put("roomNickName", rooms.getRoomNickName());
				json.put("familygroup", rooms.getFamilygroup());
				json.put("createTime", rooms.getCreateTime());
				json.put("totalPage", totalPage);
				json.put("pageNow", pageNow);
				array.add(json);
			}	
		}
		out.print(array.toString());
		return null;
	}
	/**
	 * 添加房间信息
	 * @param groupId 家庭组Id
	 * @param userid  用户Id
 	 * @param roomName 房间名字
	 * @return fals=true(添加成功)或者fals=false(添加失败)
	 * @throws IOException 
	 */
	public String insertRoom() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String id=request.getParameter("groupId");
		String userid=request.getParameter("userid");
		Users users=null;
		boolean	fals=false;
		if(userid !=null && ! userid.equals("")){
			users=usersService.selectUsersByid(Integer.parseInt(userid));
		}
		if(users !=null){
			//只有在用户角色未群主时才能添加房间
				String roomName=request.getParameter("roomName");
				
				Room room=new Room();
				
				if(id !=null && ! id.equals("")){
					Familygroup family=familyService.getFamilygroupByid(Integer.parseInt(id));
					room.setFamilygroup(family);
				}
				if(roomName !=null && ! roomName.equals("")){
					room.setRoomName(roomName);
				}
				Date date=new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String dateNow =sdf.format(date);
				Timestamp tim=Timestamp.valueOf(dateNow);
				room.setCreateTime(tim);
				fals=roomService.insertRoom(room);
			}
		JSONObject json=new JSONObject();
		json.put("fals", fals);
		out.print(json.toString());
		return null;
		
		
	}
	/**
	 * 删除房间信息
	 * @param roomId 房间id
	 * @param userid 用户id
	 * @return fals=true(删除成功)或者fals=false(删除失败)
	 * @throws IOException 
	 */
	public String deleteRoom() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String userid=request.getParameter("userid");
		Users users=null;
		boolean	fals=false;
		JSONObject json=new JSONObject();
		String roomId=request.getParameter("roomId");
		if(userid !=null && ! userid.equals("")){
			//根据用户id查到用户信息
			users=usersService.selectUsersByid(Integer.parseInt(userid));
		}
		if(users !=null){
			//只有当用户角色未群主时才能执行以下操作
				if(roomId !=null && ! roomId.equals("")){
					Room room=roomService.getRoomByid(Integer.parseInt(roomId));
					fals=roomService.deleteRoom(room);	
					
				}
			
		}
		json.put("fals", fals);
		out.print(json.toString());
		return null;
	}
	
	/**
	 * 根据id查询房间信息
	 * @param roomId 房间id
	 * @return room 房间对象
	 * @throws IOException 
	 */
	public String getRoomByid() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String roomId=request.getParameter("roomId");
		Room room=null;
		JSONObject json=new JSONObject();
		if(roomId !=null && ! roomId.equals("")){
			room=roomService.getRoomByid(Integer.parseInt(roomId));
			if(room !=null){	
				json.put("room", room);
				out.print(json.toString());
			}	
		}
		return null;
	}
	/**
	 * 修改房间信息
	 * @param roomId 房间id
	 * @param userid 用户id
	 * @param roomName 房间名字
	 * @return fals=true(修改成功)或者fals=false(修改失败)
	 * @throws IOException 
	 */
	public String updateRoom() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String roomId=request.getParameter("roomId");
		String roomName=request.getParameter("roomName");
		String userid=request.getParameter("userid");
		Users users=null;
		boolean fals=false;
		JSONObject json=new JSONObject();
		if(userid !=null && ! userid.equals("")){
			//根据用户id查到用户信息
			users=usersService.selectUsersByid(Integer.parseInt(userid));
		}
		if(users !=null){
				Room room=null;
				if(roomId !=null && ! roomId.equals("")){
					room=roomService.getRoomByid(Integer.parseInt(roomId));
					room.setRoomName(roomName);
				}
				if(room !=null){
					fals=roomService.updateRoom(room);
					
				}
		}
		json.put("fals", fals);
		out.print(json.toString());
		return null;
	}

	


	
	
	
	
}
