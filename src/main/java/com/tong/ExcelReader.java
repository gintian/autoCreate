package com.tong;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {

    /** 读取Excel文件内容 */
    public ArrayList<ArrayList<String>> readExcel(String filePath, int sheetIndex,int rowNum) {
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>> ();
        try {
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filePath));
            XSSFSheet sheet = wb.getSheetAt(sheetIndex);
            dataList  = readExcelContent(sheet,rowNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
    /**
     * 读取Excel数据内容
     *
     * @param
     * @return Map 包含单元格数据内容的Map对象
     * @author litt
     */
    private static ArrayList<ArrayList<String>> readExcelContent(XSSFSheet sheet,int rowNum) throws Exception{
        Map<Integer, Map<Integer,Object>> content = new HashMap<Integer, Map<Integer,Object>>();
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>> ();

        // 得到总行数(不能得到正确的行数？？？)
//        int rowNum = sheet.getLastRowNum();
        Row row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            Map<Integer,Object> cellValue = new HashMap<Integer, Object>();
            ArrayList<String> cellList = new ArrayList<String>();
            while (j < colNum) {
                Object obj = getCellFormatValue(row.getCell(j));
                cellValue.put(j, obj);
                cellList.add(obj.toString());
                j++;
            }
            content.put(i, cellValue);
            dataList.add(cellList);
        }
        return dataList;
    }

    /**
     *
     * 根据Cell类型设置数据
     *
     * @param cell
     * @return
     * @author zengwendong
     */
    private static Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式
                        // data格式是带时分秒的：2013-7-10 0:00:00
                        // cellvalue = cell.getDateCellValue().toLocaleString();
                        // data格式是不带带时分秒的：2013-7-10
                        Date date = cell.getDateCellValue();
                        cellvalue = date;
                    } else {// 如果是纯数字

                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:// 默认的Cell值
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }
}
