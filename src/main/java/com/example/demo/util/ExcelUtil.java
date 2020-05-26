package com.example.demo.util;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ExcelDownload;
import com.example.demo.dao.ExcelDownloadDAO;

@Repository
public class ExcelUtil {

   @Autowired
   ExcelDownloadDAO dao;

    public void write(List<ExcelDownload> studentList, String[] classinfo, String function, HttpServletResponse response) throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet1;
        sheet1 = workbook.createSheet("點名紀錄");

        // 設定開始欄位為第一欄
        sheet1.setColumnWidth(0, 2000);
        sheet1.setColumnWidth(1, 3000);
        sheet1.setColumnWidth(2, 2000);
        sheet1.setColumnWidth(3, 3500);

      
        int rowIndex = 0;
        // 設定並寫入表頭，欄位+1
        HSSFRow titlerow = sheet1.createRow(rowIndex);
        
        
        String Title = "課程ID";  //classinfo[0]
        String Title2 = "課程名稱";   //classinfo[1]
        String Title3 = "授課教師";   //classinfo[2]
        String Title4 = "學生人數";   //classinfo[3]

        
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
        titlerow.createCell(rowIndex+1).setCellValue(classinfo[3]+" 人");



        // 跳column
        rowIndex += titleindex+1;

        //get所有的點名時間
        String[] allrollcallstarttime = dao.findAllRollcallStartTime(classinfo[0]).split(",");   //classinfo[0] = cs_id

        // 次要表頭
        String[] head = new String[100];
        //將學生資料加入head.
        String[] studentinfotitle = { "序列", "學號", "姓名", "系所" };
        for(int i=0; i<studentinfotitle.length ; i++){
           head[i] = studentinfotitle[i];
        }
        int starttime_index = 0;
        //將一筆一筆的點名寫到head.
        for(int i = studentinfotitle.length ; i <= allrollcallstarttime.length-1+studentinfotitle.length ; i++){
           head[i] = allrollcallstarttime[starttime_index];
           starttime_index++;
        }


        //將head的資料寫入excel中行程點名表的title.
        titlerow = sheet1.createRow(rowIndex);
        for (int i = 0; i < head.length; i++) {
           //設定每個點名的column寬度.
           if(i>3){
              sheet1.setColumnWidth(i, 4500);
           }
           titlerow.createCell(i).setCellValue(head[i]);
        }

        rowIndex++;
        int count = 0;

        //將所有學生的學號輸入到陣列中，以便之後跟點名做比對.
        String[] allstudent = new String[100];
        int allstudent_count = 0;
        for (ExcelDownload ed : studentList) {

            count++;
            int cellindex = 0;
            
            // 根據行指定列座標j,然後在單元格中寫入資料
            HSSFRow rowDate = sheet1.createRow(sheet1.getLastRowNum() + 1);
         
            rowDate.createCell(cellindex).setCellValue(count);
            cellindex++;
         
            rowDate.createCell(cellindex).setCellValue(ed.getStd_id());
            allstudent[allstudent_count] = Integer.toString(ed.getStd_id());
            allstudent_count++;
            cellindex++;
         
            rowDate.createCell(cellindex).setCellValue(ed.getStd_name());
            cellindex++;
         
            rowDate.createCell(cellindex).setCellValue(ed.getStd_department());
            cellindex++;
      
      
        }
        

        int column_count = studentinfotitle.length;
        //利用迴圈取出每個點名的starttime.
        for(String starttime : allrollcallstarttime){

           //取得單一點名所有學生的點名紀錄.
           List<ExcelDownload> rollcallrecord = dao.findRollcallRecord(classinfo[0], starttime);
           //將allstudent_count歸0.
           allstudent_count = 0;
           //利用迴圈取出rollcallrecord的List中每一筆學生的點名紀錄.
           for(ExcelDownload personalrecord : rollcallrecord){
              //跑迴圈讓rollcallrecord跟allstudent去比對學號，以防資料錯誤.
              for(; allstudent_count < allstudent.length ;){
                 if(allstudent[allstudent_count] == null){
                    break;
                 }
                 //如果allstudent中的學號與personalrecord的object中的std_id一樣，則將資料寫入至excel中.
                 if( allstudent[allstudent_count].equals(Integer.toString(personalrecord.getStd_id())) ){
                    titlerow = sheet1.getRow(allstudent_count + rowIndex);
                    titlerow.createCell(column_count).setCellValue(personalrecord.getTl_type_name());
                    allstudent_count++;
                    break;
                 }else{
                    allstudent_count++;
                 }
              }
              
           }
           column_count++;
        }
        

        
        String fileName = classinfo[1] + "Rollcalls.xls";

        
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
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            out.flush();
            out.close();
         }

        
    }
      
      
}