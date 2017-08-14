package com.tong;

import java.util.ArrayList;

/**
 * Created by Tong on 2017/8/14.
 */
public class CreateGetter {

    private static final int lable = 4;
    private static final int length = 2;
    private static final int name = 7;
    private static final int type = 2;

    public void printGetter(String filePath,int sheetIndex,int rowNum){
        ExcelReader er = new ExcelReader();
        ArrayList<ArrayList<String>> dataList = er.readExcel(filePath, sheetIndex,rowNum);
        for (int i = 0; i < dataList.size(); i++) {
            ArrayList<String> cellList = dataList.get(i);
            ExcelReader.println("@Label(value = \""+cellList.get(lable)+"\")");
            int left = cellList.get(length).lastIndexOf("(");
            int right = cellList.get(length).lastIndexOf(")");
            int comma = cellList.get(length).lastIndexOf(",");
            String lengthStr = "";
            if(comma != -1){
                lengthStr = cellList.get(length).substring(left+1,comma);
            }else{
                lengthStr = cellList.get(length).substring(left+1,right);
            }
            ExcelReader.println("@Length(max = "+lengthStr+")");
            String nameStr = cellList.get(name).substring(0,1).toUpperCase()+cellList.get(name).substring(1,cellList.get(name).length());
            String typeStr = cellList.get(type);
            if("V".equals(typeStr.substring(0,1))){
                typeStr = "String";
            }else{
                if(comma == -1){
                    typeStr = "Long";
                }else{
                    typeStr = "Double";
                }
            }
            ExcelReader.println("public "+typeStr+" get"+nameStr+"() {");
            ExcelReader.println("   return this."+cellList.get(name)+";");
            ExcelReader.println("}");
        }
    }

}
