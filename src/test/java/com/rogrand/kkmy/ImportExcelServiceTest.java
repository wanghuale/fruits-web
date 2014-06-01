package com.rogrand.kkmy;

import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rogrand.kkmy.common.BaseTest;
import com.rogrand.kkmy.common.api.KkmyDrugsNameService;
import com.rogrand.kkmy.common.model.KkmyDrugsName;

/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：huaxing.wang@rogrand.com <br/>
 * 生成日期：2014-4-23 <br/>
 * 描述：〈exl导入数据库〉
 */
public class ImportExcelServiceTest extends BaseTest {
	
    @Autowired
    private KkmyDrugsNameService KkmyDrugsNameService;
    
     @Test
    public void importExl() throws Throwable{
    	ImportExcel ei = new ImportExcel("E:/工作备份/企业备份/融贯通讯/常见药品标准名称.xlsx", 1);
		
		//List list = new ArrayList();
		for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			KkmyDrugsName kkmyDrugsName = new KkmyDrugsName();
			for (int j = 0; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				if(j==1){
					kkmyDrugsName.setName(val.toString());
				}
				if(j==2){
					kkmyDrugsName.setSymptom(val.toString());
				}
				//System.out.print(val+", ");
			}
			//list.add(map);
			//System.out.print("\n");
			KkmyDrugsNameService.save(kkmyDrugsName);
		}
		//System.out.println("size : "+list.size());
    }
}
