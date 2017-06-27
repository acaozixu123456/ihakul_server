package com.xiaoai.TaskJob;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xiaoai.entity.Xiaoitask;
import com.xiaoai.service.IXiaoitaskService;
import com.xiaoai.web.action.AppFamilygroupAction;

/**
 * @author ZERO
 * @Data 2017-6-26 下午5:45:55
 * @Description 计划任务定时出清器
 */
@Service
public class XiaoiTaskTimer {
	
	private static Logger logger = Logger.getLogger(AppFamilygroupAction.class);
	
	@Resource(name="xiaoitaskService")
	private IXiaoitaskService xiaoitaskService;
	/**
	 * 定时清除器
	 */
	public void job(){
		//logger.info("计划任务定时清除器被触发!");
		//取出所有计划任务
		List<Xiaoitask> list = xiaoitaskService.findAllXiaoitasks();
		//判断
		long currentTimeMillis = System.currentTimeMillis();
		for (Xiaoitask xiaoitask : list) {
			if(currentTimeMillis>xiaoitask.getTriggerTime()){
				//清除
				xiaoitaskService.deleteXiaoitask(xiaoitask);
				logger.info("计划任务定时清除器被触发清除了:"+xiaoitask.getId());
			}
		}
	}
}
