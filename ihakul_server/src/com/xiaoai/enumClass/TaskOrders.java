package com.xiaoai.enumClass;
/**
 * @author ZERO
 * @Data 2017-6-22 下午5:08:32
 * @Description 该类用于对{@code AiTask}的意图进行简单描述
 */
public enum TaskOrders {

	/** 定时执行闹钟 */
	ALARM_SET,

	/** 定时播报计划 */
	REMINDER_SET,

	/** 房间电器定时开启 */
	DEVICE_ON,

	/** 房间电器定时关闭 */
	DEVICE_OFF,

	/** 播放歌手音乐 */
	SONG_SET;

	/** 操作房间电器 */
	public boolean isOperatingEa() {
		return equals(DEVICE_ON)||equals(DEVICE_OFF);
	}

	/**
	 * 获得该任务说明
	 * 
	 * @return String
	 */
	public String show() {
		switch (this) {
		case ALARM_SET:
			return "闹钟设定为%1$s";
		case REMINDER_SET:
			return "提醒时间为%1$s";
		case SONG_SET:
			return "将在%1$s播放音乐";
		case DEVICE_ON:
		case DEVICE_OFF:
			return "将在%1$s操作电器";
		default:
			return "Unknown";
		}
	}
}
