package com.xiaoai.util;


/**
 * 小艾报错信息集合
 * 
 * @author ZERO
 */
/**
 * code十位数  [1]入参缺失，[2]入参错误，[3]冲突检出，[4]查无此键，[5]格式不符，[6]权限不足，[7]操作失败 ， 剩余的8~9备用。
 * @Description 
 * @author Administrator
 * @Data 2017-5-22下午6:10:01
 */
public class XiaoaiMessage {
	/**成功 */
	public static final int OK = 0; 
	/** 操作失败，表示其它未知、始料未及的错误 */
	public static final int Other = 500; 
	/** 小艾已经被删除 */
	public static final int deletedXiaoi = 600; 
	/** 小艾已经被替换 */
	public static final int changedXiaoi = 700; 
	
	
	/**
	 * 用户报错信息集合311-400
	 */
	public static class UserCode {
		
		/**用户名不能为空*/
		public static final int emptyName=311;
		/**密码不能为空*/
		public static final int emptyPass=312;
		/**手机号不能为空*/
		public static final int emptyPhoneNum=313;
		/**性别不能为空*/
		public static final int emptyGender=314;
		/**用户ID不能为空*/
		public static final int emptyId=315;
		/**用户角色ID不能为空*/
		public static final int emptyRoleId=316;
		/**用户状态不能为空 */
		public static final int emptyStatus=317;

		/**用户密码错误 */
		public static final int errorPass=321;

		/**该用户名已注册 */
		public static final int conflictBean=331;
		/**该手机号已注册 */
		public static final int conflictPhoneNum=332;/*没有该用户角色参考Role#noExistBean */
		
		/**没有该用户 */
		public static final int noExistBean=341;
		
		/**用户ID格式不符 */
		public static final int formatisInconsistent=351;
		
		/**新增用户失败! */
		public static final int insertFalse=371;
		/**修改用户失败! */
		public static final int updateFalse=372;
		/**删除用户失败! */
		public static final int deleteFalse=373;
	}

	/**
	 * 家庭组报错信息集合111-200
	 */
	public static class FamilyCode {
		
		/**家庭组编号不能为空 */
		public static final int emptyId=111;
		/**家庭组名称不能为空 */
		public static final int emptyName=112;
		/**家庭组验证密码不能为空 */
		public static final int emptySecurityCode=113;
		/**家庭组版本号不能为空 */
		public static final int emptyVersion=114;
		
		/**家庭组验证密码错误! */
		public static final int errorSecurityCode=121;
	
		/**用户绑定家庭组失败 */
		public static final int conflictAbortBindfalse=131;
		/**该用户已绑定该家庭组 */
		public static final int conflictAbortBind = 132;
	
		/**没有该家庭组 */
		public static final int noExistBean=141;
		/**该家庭组中不存在该用户 */
		public static final int noExistUser=142;
		
		/**家庭组编号格式不符 */
		public static final int formatisInconsistent=151;
	
		
		
		/**您不是群主，没有权限操作! */
		public static final int privilegeMaster=161;
		
		/**新增家庭组失败! */
		public static final int insertFalse=171;
		/**修改家庭组失败! */
		public static final int updateFalse=172;
		/**删除家庭组失败! */
		public static final int deleteFalse=173;
		
	}

	/**
	 * 房间报错信息集合411-499
	 */
	public static class RoomCode {

		/**房间编号不能为空*/
		public static final int emptyId=411;
		/**房间名称不能为空*/
		public static final int emptyName=412;

		/**该房间已存在*/
		public static final int conflictBean=431;
       
		/**该房间已绑定终端 */
		public static final int conflictAbortBind = 432;
		 
		/**没有该房间*/
		public static final int noExistBean=441;
		
		/**房间楼层格式不符 */
		public static final int floorFormatIsfalse=452;
		
		/**新增房间失败! */
		public static final int insertFalse=471;
		/**修改房间失败! */
		public static final int updateFalse=472;
		/**删除房间失败! */
		public static final int deleteFalse=473;
		/**切换默认房间失败! */
		public static final int changeFalse=474;
	}

	/**
	 * 家电报错信息集合511-599
	 */
	public static class HouseholdCode {

		/**家电编号不能为空*/
		public static final int emptyId=511;
		/**家电昵称不能为空*/
		public static final int emptyName=512;
		/**家电类别ID不能为空*/
		public static final int emptyClassId=513;
		/**requestUri不能为空！*/
		public static final int emptyRequestUri=514;
		/**Keys不能为空！*/
		public static final int emptyKeys=515;
		/**通讯参数不能为空!*/
		public static final int emptyProp=516;
		/**功能键值不能为空!*/
		public static final int emptyCommand=517;
		/**执行参数不能为空!*/
		public static final int emptyValue=518;
		
		/**该电器已存在 */
		public static final int conflictBean=531;
		
		/**没有该家电*/
		public static final int noExistBean=541;
		/**没有该家电类别*/
		public static final int noExistClassId=542;
		
		/**通讯参数格式不符*/
		public static final int propFormatisfalse=552;
		/**智能索引格式不符*/
		public static final int stubformatisfalse=553;
			
		
		/**新增家电失败! */
		public static final int insertFalse=571;
		/**修改家电失败! */
		public static final int updateFalse=572;
		/**删除家电失败! */
		public static final int deleteFalse=573;
		
	}
   
	
	/**
	 * 小艾报错信息集合611-699
	 */
	public static class XiaoiCode {
	
		/** 小艾编号不能为空*/
		public static final int emptyId=611;
		/** 小艾名称不能为空*/
		public static final int emptyName=612;
		/** 小艾Ip不能为空*/
		public static final int emptyIp=613;
		
		/** 该小艾已存在   */
		public static final int conflictBean=631;
		
	    /** 没有该小艾   */
		public static final int noExistBean=641;
		/** 没有在线小艾   */
		public static final int noExistOnlinBean=642;
         
		
		
		/**新增小艾失败! */
		public static final int insertFalse=671;
		/**修改小艾失败! */
		public static final int updateFalse=672;
		/**删除小艾失败! */
		public static final int deleteFalse=673;
		/**更换终端小艾失败! */
		public static final int changeFalse=674;
	}
	
	
	/**
	 * 版本报错信息集合711-799
	 */
	public static class VersionCode {
	
		/** 版本号不能为空*/
		public static final int emptyVersion=711;
		/** 版本包名不能为空*/
		public static final int emptyPackage=712;
		
		/** 服务器URL不能为空*/
		public static final int emptyUrl=713;
		
		/** 没有该包名	*/
		public static final int noExistBean=741;
	}
	
	
	
	/**
	 * 第三方报错信息集合811-899
	 */
	public static class OtherCode {
	   
	   /**群发消息ID不能为空*/
	   public static final int emptyId=811;
	
	
	   /** 调用短信平台接口失败   */
	   public static final int otherFalse = 871;  	
	}
	
	
	/**
	 * 其它报错信息集合951-999
	 */
	public static class UnknownCode {
		/**没有找到该实体类*/
	   public static int Code_951 = 951;  
	   
	    /**该名称已存在 */
	   public static int Code_971 = 971;  
	  
	   /** 未知错误   */
	   public static int Code_999 = 999;  	
	}
	
	
	public static class Plan {
		/**任务ID不能为空*/
		public static final int emptyId=1011;
		/**任务触发时间不能为空*/
		public static final int emptyTrigger=1012;
		/**任务意图不能为空*/
		public static final int emptyOrder=1013;
		/**任务对象不能为空*/
		public static final int emptyObject=1014;
		/**任务事项不能为空*/
		public static final int emptyThings=1015;

		/**非法的意图描述*/
		public static final int errorOrder=1021;

		/** 没有该任务数据*/
		public static final int noExistBean=1041;
		
		/**新增计划任务失败! */
		public static final int insertFalse=1071;
		/**修改计划任务失败! */
		public static final int updateFalse=1072;
		/**删除计划任务失败! */
		public static final int deleteFalse=1073;
	
		/**消息推送失败! */
		public static final int pushFalse=1074;
		
		
		
	}
	
	public static class XiaoiModeCode{
		/**id(场景模式)不能为空！ */
		public static final int emptyId = 1111;
		/**控制类型不能为空！ */
		public static final int emptyClassId = 1112;
		/**执行参数不能为空！ */
		public static final int emptyArgument = 1113;
		/**电器标识不能为空！ */
		public static final int emptyEaNumber = 1114;
		/**mode(场景模式)不能为空！ */
		public static final int emptyMode = 1115;
		
		/**没有该情景模式 */
		public static final int noExistBean = 1141;
		
		/**新增情景模式失败！ */
		public static final int insertFail = 1175;
		/**删除情景模式失败！ */
		public static final int deleteFail = 1176;
		
		
		
	}
	
	public static class InfoCode{
		/**公告内容不能为空！ */
		public static final int emptyContent = 1211;
		
		
		/**新增公告失败！ */
		public static final int insertFail = 1271;
	}
	
	public static class ChannelCode{
		/**频道集合不能为空！ */
		public static final int emptyContent = 1311;
		
		
		/**新增频道失败！ */
		public static final int insertFail = 1371;
	}
}
