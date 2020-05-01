package com.example.demo.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ExcelDownload;

@Repository
public class ExcelUtil {
    public void write(List<ExcelDownload> exceldownload, String filename, String function, HttpServletResponse res) throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet1;
        
        sheet1 = workbook.createSheet("TEST");

        // 設定開始欄位為第一欄
      
        sheet1.setColumnWidth(0, 4000);
        sheet1.setColumnWidth(1, 7000);
        sheet1.setColumnWidth(2, 4000);
        sheet1.setColumnWidth(3, 4000);
        sheet1.setColumnWidth(4, 4000);
        sheet1.setColumnWidth(5, 4000);
        sheet1.setColumnWidth(6, 4000);
        sheet1.setColumnWidth(7, 4000);
        sheet1.setColumnWidth(8, 4000);
        sheet1.setColumnWidth(9, 4000);
        sheet1.setColumnWidth(10, 4000);
      
        int rowIndex = 0;
        // 設定並寫入表頭，欄位+1
        HSSFRow titlerow = sheet1.createRow(rowIndex);
        // 第一列第一欄為
        final String Title = "報名名單";
        titlerow.createCell(rowIndex).setCellValue(Title);
        // 跳列
        rowIndex++;
      
        // 名字 電話 緊急連絡人 電話 *mail 備註 供餐 性別
      
        // 次要表頭
      
        final String[] head = { "名字", "Email", "性別", "電話", "緊急聯絡人姓名", "緊急連絡人電話", "緊急聯絡人關係" };
      
        titlerow = sheet1.createRow(rowIndex);
        for (int i = 0; i < head.length; i++) {
         titlerow.createCell(i).setCellValue(head[i]);
        }
        rowIndex++;
        for (ExcelDownload ed : exceldownload) {
      
         //if (r.getCancelRegistrationString() != null)
          //continue;
      
         // 根據行指定列座標j,然後在單元格中寫入資料
         HSSFRow rowDate = sheet1.createRow(sheet1.getLastRowNum() + 1);
      
         rowDate.createCell(0).setCellValue(ed.getCs_id());
      
         rowDate.createCell(1).setCellValue(ed.getRc_inputsource());
      
         rowDate.createCell(2).setCellValue(ed.getStd_id());
      
         rowDate.createCell(3).setCellValue(ed.getStd_name());
      
         rowDate.createCell(4).setCellValue(ed.getStd_department());
      
         rowDate.createCell(5).setCellValue(ed.getRecord_time());
      
         rowDate.createCell(6).setCellValue(ed.getTl_type_name());
      
      
      
        }
        

        // String filePath = "C:/Users/jack1/Desktop/somthing/upload/" + filename +
        // ".xls";
        String home = System.getProperty("user.home");
        //下載路徑到 "下載項目"
        String filePath = home + "/Downloads/" + filename + ".xls";
        

        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        workbook.close();


        OutputStream outputStream = res.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        bis = new BufferedInputStream(new FileInputStream(new File(filePath)));
        //new BufferedInputStream(new FileInputStream(new File("./file/" + filename)));
        int i = bis.read(buff);
        while (i != -1) {
            outputStream.write(buff, 0, buff.length);
            outputStream.flush();
            i = bis.read(buff);
        }
        
    }
      
      
}