package com.hj.wxmp.mobile.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelReader {
	private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;
    

//    /**
//     * 读取Excel数据内容
//     * @param InputStream
//     * @return Map 包含单元格数据内容的Map对象
//     */
//    public Map<Integer, String> readExcelContent(InputStream is,int sheetIndex) {
//        Map<Integer, String> content = new HashMap<Integer, String>();
//        String str = "";
//        try {
//            fs = new POIFSFileSystem(is);
//            wb = new HSSFWorkbook(fs);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        sheet = wb.getSheetAt(sheetIndex);
//        // 得到总行数
//        int rowNum = sheet.getLastRowNum();
//        row = sheet.getRow(0);
//        int colNum = row.getPhysicalNumberOfCells();
//        // 正文内容应该从第二行开始,第一行为表头的标题
//        for (int i = 1; i <= rowNum; i++) {
//            row = sheet.getRow(i);
//            int j = 0;
//            while (j < colNum) {
//                str += getCellFormatValue(row.getCell((short) j)).trim() + "    ";
//                j++;
//            }
//            content.put(i, str);
//            str = "";
//        }
//        return content;
//    }

    
    public Map<Integer, String> readExcelContent(Sheet sheet) {  
    	Map<Integer, String> content = new HashMap<Integer,String>();  
    	String str = "";
    	//总行数
        int rowNum = sheet.getPhysicalNumberOfRows();  
        for (int i = 1; i < rowNum; i++) {  
            Row row = sheet.getRow(i);
            //某一行的总列数
            int lastCellNum = row.getLastCellNum ();
            int j = 0;
            while (j < lastCellNum) {
                str += getCellFormatValue(row.getCell((short) j)).trim() + "    ";
                j++;
            }
            content.put(i, str);
            str = "";
        }  
        return content;  
    } 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(Cell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     * 
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case HSSFCell.CELL_TYPE_NUMERIC:
            case HSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                }
                // 如果是纯数字
                else {
                    // 取得当前Cell的数值
                	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                	cellvalue = cell.toString();
                }
                break;
            }
            // 如果当前Cell的Type为STRIN
            case HSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    public static void main(String[] args) {
    	
    }
}
