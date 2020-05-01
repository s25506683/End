package com.example.demo.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ExcelDownload;

@Repository
public class ExcelUtil {
    public void write(List<ExcelDownload> exceldownload, String[] classinfo, String function, HttpServletResponse response) throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet1;
        sheet1 = workbook.createSheet("點名紀錄");

        // 設定開始欄位為第一欄
        sheet1.setColumnWidth(0, 3000);
        sheet1.setColumnWidth(1, 4000);
        sheet1.setColumnWidth(2, 3000);
        sheet1.setColumnWidth(3, 3000);
        sheet1.setColumnWidth(4, 5000);
        sheet1.setColumnWidth(5, 3000);
      
        int rowIndex = 0;
        // 設定並寫入表頭，欄位+1
        HSSFRow titlerow = sheet1.createRow(rowIndex);
        
        
        final String Title = "課程ID";  //classinfo[0]
        final String Title2 = "課程名稱";   //classinfo[1]
        final String Title3 = "點名開啟時間";   //classinfo[2]
        final String Title4 = "點名方式";   //classinfo[3]
        
        //寫入基本資料
        int titleindex = classinfo.length;
        titlerow.createCell(rowIndex).setCellValue(Title);
        titlerow.createCell(rowIndex+1).setCellValue(classinfo[0]);
        //跳column
        titlerow = sheet1.createRow(sheet1.getLastRowNum() + 1);
        titlerow.createCell(rowIndex).setCellValue(Title2);
        titlerow.createCell(rowIndex+1).setCellValue(classinfo[1]);
        //跳column
        titlerow = sheet1.createRow(sheet1.getLastRowNum() + 1);
        titlerow.createCell(rowIndex).setCellValue(Title3);
        titlerow.createCell(rowIndex+1).setCellValue(classinfo[2]);
        //跳column
        titlerow = sheet1.createRow(sheet1.getLastRowNum() + 1);
        titlerow.createCell(rowIndex).setCellValue(Title4);
        titlerow.createCell(rowIndex+1).setCellValue(classinfo[3]);



        // 跳column
        rowIndex += titleindex+1;

        // 次要表頭
        final String[] head = { "序列", "學號", "姓名", "系所", "點名日期時間", "出缺席狀況" };
      
        titlerow = sheet1.createRow(rowIndex);
        for (int i = 0; i < head.length; i++) {
         titlerow.createCell(i).setCellValue(head[i]);
        }
        rowIndex++;
        int count = 0;
        for (ExcelDownload ed : exceldownload) {
         count++;
         //if (r.getCancelRegistrationString() != null)
          //continue;
      
         // 根據行指定列座標j,然後在單元格中寫入資料
         HSSFRow rowDate = sheet1.createRow(sheet1.getLastRowNum() + 1);
      
         rowDate.createCell(0).setCellValue(count);
      
         rowDate.createCell(1).setCellValue(ed.getStd_id());
      
         rowDate.createCell(2).setCellValue(ed.getStd_name());
      
         rowDate.createCell(3).setCellValue(ed.getStd_department());
      
         rowDate.createCell(4).setCellValue(ed.getRecord_time());
      
         rowDate.createCell(5).setCellValue(ed.getTl_type_name());
      
      
      
        }
        

        
        String fileName = classinfo[1] + classinfo[2] + "Rollcall.xls";

        
        //String home = System.getProperty("user.home");
        //下載路徑到 "下載項目"
        //String filePath = home + "/Downloads/" + classinfo[1] + classinfo[2] + "Rollcall.xls";
        //下載.xls檔案到下載項目中（不透過網頁瀏覽器，所以此方法不可行）
        //FileOutputStream fos = new FileOutputStream(filePath);
        //workbook.write(fos);
        //fos.close();
        //workbook.close();


        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
        OutputStream out = response.getOutputStream();
        try {
            workbook.write(out);// output .xls file
            //inputStream = new ByteArrayInputStream(out.toByteArray());
            String str = "download" + fileName + "sccessful";
            System.out.println(str);
         } catch (Exception e) {
            e.printStackTrace();
            String str1 = "download" + fileName + "failed！";
            System.out.println(str1);
         } finally {
            out.flush();
            out.close();
         }

        
    }
      
      
}