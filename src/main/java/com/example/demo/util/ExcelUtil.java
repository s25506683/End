package com.example.demo.util;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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
        HSSFFont font_absent = workbook.createFont();
        HSSFFont font_farRollcall = workbook.createFont();
        HSSFFont font_takeleave = workbook.createFont();
        HSSFCellStyle style_absent = workbook.createCellStyle();
        HSSFCellStyle style_farRollcall = workbook.createCellStyle();
        HSSFCellStyle style_takeleave = workbook.createCellStyle();

        //設定請假類別為 "缺席" 的字型(顏色為紅色).
        font_absent.setColor(IndexedColors.RED.index);
        //設定 "缺席" 的style.
        style_absent.setFont(font_absent);

        //設定請假類別為 "遠距簽到" 的字型(顏色為藍色).
        font_farRollcall.setColor(IndexedColors.BLUE.index);
        //設定 "遠距簽到" 的style.
        style_farRollcall.setFont(font_farRollcall);

        //設定請假類別為 "請假狀態" 的字型(顏色為橘色).
        font_takeleave.setColor(IndexedColors.LIGHT_ORANGE.index);
        //設定 "請假狀態" 的style.
        style_takeleave.setFont(font_takeleave);




        HSSFSheet sheet1;
        sheet1 = workbook.createSheet("點名紀錄");

        // 設定開始欄位為第一欄
        sheet1.setColumnWidth(0, 2500);
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
        String[] allrollcallstarttime = dao.findAllRollcallStartTime(classinfo[0]).replace("-", "/").split(",");   //classinfo[0] = cs_id

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


        
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        //將head的資料寫入excel中行程點名表的title.
        titlerow = sheet1.createRow(rowIndex);
        for (int i = 0; i < head.length; i++) {
           //如果head中已經沒有資料, break.
           if(head[i] == null){
              break;
           }
           //設定每個點名的column寬度.
           if(i>studentinfotitle.length-1){
              //如果studentinfotitle輸入完畢,先設定時間的格式、columnSize後再行輸入到Excel.
              head[i] = head[i].substring(0, head[i].length()-3);
              titlerow.createCell(i).setCellValue(head[i]);
              sheet1.autoSizeColumn(i);
           }else{
              //輸入studentinfotitle的title到Excel.
              titlerow.createCell(i).setCellValue(head[i]);
           }
        }

        rowIndex++;
        int count = 0;

        //將所有學生的學號輸入到陣列中，以便之後跟點名做比對.
        String[] allstudent = new String[100];
        int allstudent_index = 0;
        for (ExcelDownload ed : studentList) {

            count++;
            int cellindex = 0;
            
            // 根據行指定列座標j,然後在單元格中寫入資料
            HSSFRow rowDate = sheet1.createRow(sheet1.getLastRowNum() + 1);
         
            rowDate.createCell(cellindex).setCellValue(count);
            cellindex++;
         
            rowDate.createCell(cellindex).setCellValue(ed.getStd_id());
            allstudent[allstudent_index] = Integer.toString(ed.getStd_id());
            allstudent_index++;
            cellindex++;
         
            rowDate.createCell(cellindex).setCellValue(ed.getStd_name());
            cellindex++;
         
            rowDate.createCell(cellindex).setCellValue(ed.getStd_department());
            cellindex++;

      
        }
        

        int column_index = studentinfotitle.length;
        //利用迴圈取出每個點名的starttime.
        for(String starttime : allrollcallstarttime){

           //取得單一點名所有學生的點名紀錄.
           List<ExcelDownload> rollcallrecord = dao.findRollcallRecord(classinfo[0], starttime);
           //將allstudent_index歸0.
           allstudent_index = 0;
           //利用迴圈取出rollcallrecord的List中每一筆學生的點名紀錄.
           for(ExcelDownload personalrecord : rollcallrecord){
              //跑迴圈讓rollcallrecord跟allstudent去比對學號，以防資料錯誤.
              for(; allstudent_index < allstudent.length ;){
                 if(allstudent[allstudent_index] == null){
                    break;
                 }
                 //如果allstudent中的學號與personalrecord的object中的std_id一樣，則將資料寫入至excel中.
                 if( allstudent[allstudent_index].equals(Integer.toString(personalrecord.getStd_id())) ){
                    titlerow = sheet1.getRow(allstudent_index + rowIndex);
                    titlerow.createCell(column_index).setCellValue(personalrecord.getTl_type_name());

                    if(personalrecord.getTl_type_name().equals("缺席")){
                       //if 狀態為 "缺席".
                       titlerow.getCell(column_index).setCellStyle(style_absent);

                    }else if(personalrecord.getTl_type_name().equals("遠距簽到")){
                       //if 狀態為 "遠距簽到".
                       titlerow.getCell(column_index).setCellStyle(style_farRollcall);

                    }else if(
                       personalrecord.getTl_type_name().equals("病假") || 
                       personalrecord.getTl_type_name().equals("事假") || 
                       personalrecord.getTl_type_name().equals("喪假") || 
                       personalrecord.getTl_type_name().equals("公假")){
                          //if 狀態為 "請假狀態".
                          titlerow.getCell(column_index).setCellStyle(style_takeleave);

                     }

                    allstudent_index++;
                    break;
                 }else{
                    allstudent_index++;
                 }
              }
              
           }
           column_index++;
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