package com.springboot.run;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ibong-gi on 2016. 8. 10..
 */
public class ExcelMaker {

    private static Logger logger = Logger.getLogger(ExcelMaker.class);

    private Workbook workbook;
    private Sheet sheet;
    private Row row;
    private Row headerRow;

    private List<String> headerList = null;

    private boolean is_header;

    private int row_count;
    private int column_count;
    private int length;
    private int list_data_count;

    public ExcelMaker(){}

    public String setWorkbook(Object obj){
        initExcel();
        String result;
        try{
            JSONObject json_obj = (JSONObject) obj;
            listJson(json_obj);
            makeHeader();

            result = "success";
        }catch(Exception e){
            result = "fail";
        }
        return result;
    }

    public Workbook getWorkbook(){
        return workbook;
    }

    private void initExcel(){
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
        headerRow = sheet.createRow(0);
        headerList = new ArrayList<>();

        is_header = true;
        row_count = 1;
        column_count = 0;
        list_data_count = 0;
    }

    private void listJson(JSONObject json){
        row = sheet.createRow(row_count);
        listJSONObject("", json);
    }

    private void listObject(String parent, Object data){
        if(data instanceof JSONObject){
            column_count++;
            listJSONObject(parent,(JSONObject)data);
        }else if(data instanceof JSONArray){
            list_data_count++;
            listJSONArray(parent, (JSONArray)data);
            list_data_count--; // 마지막에도 - 되기 때문에 후행 컬럼 밀림 현상
        }else{
            column_count++;
            listPrimitive(parent, data);
        }
    }

    private void listJSONObject(String parent, JSONObject json){
        for(Map.Entry<String, Object> temp_obj : ((Map<String, Object>) json).entrySet()) {
            String key = temp_obj.getKey();
            Object child = temp_obj.getValue();
            String child_key = parent.isEmpty() ? key : parent + "." + key;

            if(is_header==true) {
                headerList.add(key);
            }

            listObject(child_key, child);
        }

        is_header = false;
    }

    private void listJSONArray(String parent, JSONArray json){
        length = 0;

        for(Object temp_json : json){
            column_count =  list_data_count - 3; //
            if(length > 0){
                row_count++;
                row = sheet.createRow(row_count);
            }
            listObject(parent, temp_json);
            length++;
        }
    }

    private void listPrimitive(String parent, Object obj){
        try{
            row.createCell(column_count + list_data_count).setCellValue(obj.toString());
        }catch(Exception e){
            logger.error("row_count : " + row_count +" , column_count : " + column_count + " , parent : " + parent);
        }
    }

    private void makeHeader(){
        for(int i=0; i<headerList.size(); i++) headerRow.createCell(i).setCellValue(headerList.get(i));
    }

}
