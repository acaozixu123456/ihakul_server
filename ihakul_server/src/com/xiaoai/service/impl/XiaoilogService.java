package com.xiaoai.service.impl;




import java.util.List;

import javax.annotation.Resource;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoai.dao.IXiaoilogDao;
import com.xiaoai.entity.Xiaoilog;
import com.xiaoai.service.IXiaoilogService;
/**
 * 工作日志业务逻辑实现类
 * @author Administrator
 *
 */
@Service("xiaoilogService")
public class XiaoilogService implements IXiaoilogService {
	@Resource(name="xiaoilogDao")
	private IXiaoilogDao xiaoilogDao;
	//删除
	@Transactional
	public boolean deleteXiaoilogByid(Xiaoilog xiaoilog) {
		boolean fals=true;
		try {
			xiaoilogDao.deleteXiaoilogByid(xiaoilog);
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}
	

	//按条件分页查询
	public List<Xiaoilog> selectXiaoilog(Xiaoilog xiaoilog,int showPage,int effor) {
		StringBuffer sb=new StringBuffer("from Xiaoilog as xi where 1=1");
		
		if(xiaoilog.getXiaoi() !=null){
			sb.append(" and xi.xiaoi=:xiaoi");
		}
		if(xiaoilog.getHousehold() !=null){
			sb.append(" and xi.household=:household");
		}
		String hql=sb.toString();
		List<Xiaoilog> list=xiaoilogDao.selectXiaoilog(xiaoilog, hql,showPage,effor);
		
		return list;
	}


	//根据id查询
	public Xiaoilog selectXiaoilogByid(int id) {
	
		return xiaoilogDao.selectXiaoilogByid(id);
	}


	//得到总记录数
	public int getCountXiaoilog(Xiaoilog xiaoilog) {
		StringBuffer sb=new StringBuffer("select count(*) from Xiaoilog as xl where 1=1");
		
		if(xiaoilog.getXiaoi() !=null){
			sb.append(" and xl.xiaoi=:xiaoi");
		};
		String sql=sb.toString();
		return xiaoilogDao.getCountXiaolog(xiaoilog, sql);
	}


	//查询导出报表
	
	public HSSFWorkbook  exportExecl(Xiaoilog xiaoilog) {
		StringBuffer sb=new StringBuffer("from Xiaoilog as xi where 1=1");
		if(xiaoilog.getXiaoi() !=null){
			sb.append(" and xiaoi=:xiaoi");
		}
		String hql=sb.toString();
		List<Xiaoilog> xiaoiList=xiaoilogDao.exportExecl(xiaoilog, hql);
		 /*设置表头：对Excel每列取名 
         *(必须根据你取的数据编写) 
         */ 
		String []tableHeader={"序号","时间","家庭组名","小艾名","详情"}; 
		/* 
        *下面的都可以拷贝不用编写 
        */ 
		short cellNumber=(short)tableHeader.length;//表的列数 
		HSSFWorkbook workbook = new HSSFWorkbook(); //创建一个excel 
		HSSFCell cell = null; //Excel的列 
		HSSFRow row = null; //Excel的行 
		HSSFCellStyle style = workbook.createCellStyle(); //设置表头的类型 
		style.setAlignment(HorizontalAlignment.CENTER); 
		HSSFCellStyle style1 = workbook.createCellStyle(); //设置数据类型 
		style1.setAlignment(HorizontalAlignment.CENTER); 
		HSSFFont font = workbook.createFont(); //设置字体 
		HSSFSheet sheet = workbook.createSheet("sheet1"); //创建一个sheet 
		HSSFHeader header = sheet.getHeader();//设置sheet的头 
		try {
			/** 
             *根据是否取出数据，设置header信息 
             * 
             */ 
			if(xiaoiList.size()<1){
				header.setCenter("查无资料"); 
			}else{
				header.setCenter("小艾工作日志表"); 
				row = sheet.createRow(0); 
				row.setHeight((short)400); 
				for(int k = 0;k < cellNumber;k++){ 
					cell = row.createCell(k);//创建第0行第k列 
					cell.setCellValue(tableHeader[k]);//设置第0行第k列的值 
					sheet.setColumnWidth(k,8000);//设置列的宽度 
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色. 
					font.setFontHeight((short)350); //设置单元字体高度 
					style1.setFont(font);//设置字体风格 
					cell.setCellStyle(style1);
				}

                /* 
                 *  给excel填充数据这里需要编写 
                 *     
                 */ 
				for (int i = 0; i < xiaoiList.size(); i++) {
					Xiaoilog xiaol=xiaoiList.get(i);//获取工作日志表
					  row = sheet.createRow((short) (i + 1));//创建第i+1行 
					  row.setHeight((short)400);//设置行高 
					  if(xiaol.getLid() !=null){
						  cell = row.createCell(0);//创建第i+1行第0列 
						  cell.setCellValue(xiaol.getLid());//设置第i+1行第0列的值 
						  cell.setCellStyle(style);//设置风格 
						  
					  }
					  if(xiaol.getUserTime() !=null){
						  cell = row.createCell(1); //创建第i+1行第1列 
						  cell.setCellValue(xiaol.getUserTime());
						  cell.setCellStyle(style);//设置风格 
					  }
					  if(xiaol.getXiaoi() !=null){
						  cell =row.createCell(2);//创建第i+1行第2列 
						  cell.setCellValue(xiaol.getXiaoi().getFamilygroup().getGroupName());
						  cell.setCellStyle(style);
					  }
					  if(xiaol.getXiaoi() !=null){
						  cell =row.createCell(3);//创建第i+1行第3列
						  cell.setCellValue(xiaol.getXiaoi().getXname());
						  cell.setCellStyle(style); 
					  }
					  if(xiaol.getUsingDetails() !=null){
						  cell =row.createCell(4);//创建第i+1行第4列
						  cell.setCellValue(xiaol.getUsingDetails());
						  cell.setCellStyle(style);
					  }
					  
					  
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook ;
	}


	//添加
	@Transactional
	public boolean insertXiaoilog(Xiaoilog xiaoilog) {
		boolean fals=true;
		try {
			xiaoilogDao.insertXiaoilog(xiaoilog);
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}


	//修改
	@Transactional
	public boolean updateXiaoilog(Xiaoilog xiaoilog) {
		boolean fals=true;
		try {
			xiaoilogDao.updateXiaoilog(xiaoilog);
		} catch (Exception e) {
			fals=false;
			e.printStackTrace();
		}
		return fals;
	}


	//根据小艾id查询
	public List<Xiaoilog> selectXiaoilogByxid(int xid) {
		List<Xiaoilog> list=xiaoilogDao.selectXiaoilogByxid(xid);
		return list;
	}


	

}
