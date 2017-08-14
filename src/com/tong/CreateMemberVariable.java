package com.tong;

import java.util.ArrayList;

/**
 * Created by Tong on 2017/8/14.
 */
public class CreateMemberVariable {
    private static final int col = 1;
    private static final int type = 2;
    private static final int comment = 4;
    private static final int length = 2;
    private static final int name = 7;

    public void printMember(String filePath,int sheetIndex,int rowNum){
        ExcelReader er = new ExcelReader();
        ArrayList<ArrayList<String>> dataList = er.readExcel(filePath, sheetIndex,rowNum);
        for (int i = 0; i < dataList.size(); i++) {
            ArrayList<String> cellList = dataList.get(i);
            ExcelReader.println("/**");
            ExcelReader.println(" * "+cellList.get(comment));
            ExcelReader.println(" * ");
            ExcelReader.println(" * @Column(Name = \""+cellList.get(col)+"\"");
            ExcelReader.println(" * @generated");
            ExcelReader.println(" */ ");
            String typeStr = cellList.get(type);
            if("V".equals(typeStr.substring(0,1))){
                typeStr = "String";
            }else{
                int comma = cellList.get(type).lastIndexOf(",");
                if(comma == -1){
                    typeStr = "Long";
                }else{
                    typeStr = "Double";
                }
            }
            ExcelReader.println("private "+typeStr+" "+cellList.get(name)+";");
        }
    }
}
