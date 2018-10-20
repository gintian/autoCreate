package com.tong;

import java.util.ArrayList;

/**
 * @author Tong
 */
public class CreateX {
    private static final int COL = 1;
    private static final int TYPE = 2;
    private static final int NULLABLE = 3;
    private static final int COMMENT = 4;
    private static final int NAME = 7;

    private ExcelReader er = new ExcelReader();
    private TxtReader tr = new TxtReader();

    /**
     * 生成成员变量
     */
    public void printMember(String filePath,int sheetIndex,int rowNum){
        ArrayList<ArrayList<String>> dataList = er.readExcel(filePath, sheetIndex,rowNum);
        for (ArrayList<String> cellList : dataList) {
            PrintTools.println("/**");
            PrintTools.println(" * "+cellList.get(COMMENT).trim());
            PrintTools.println(" * ");
            PrintTools.println(" * @Column(Name = \""+cellList.get(COL).trim()+"\")");
            PrintTools.println(" */ ");
            String typeStr = cellList.get(TYPE).trim();
            if("V".equals(typeStr.substring(0,1))){
                typeStr = "String";
            }else{
                int comma = cellList.get(TYPE).lastIndexOf(",");
                if(comma == -1){
                    typeStr = "Long";
                }else{
                    typeStr = "Double";
                }
            }
            PrintTools.println("private "+typeStr+" "+cellList.get(NAME).trim()+";");
        }
    }

    /**
     * 生成get方法
     * */
    public void printGetter(String filePath,int sheetIndex,int rowNum){
        ArrayList<ArrayList<String>> dataList = er.readExcel(filePath, sheetIndex,rowNum);
        for (ArrayList<String> cellList : dataList) {
            PrintTools.println("@Label(value = \""+cellList.get(COMMENT).trim()+"\")");
            int left = cellList.get(TYPE).lastIndexOf("(");
            int right = cellList.get(TYPE).lastIndexOf(")");
            int comma = cellList.get(TYPE).lastIndexOf(",");
            String lengthStr = "";
            if(comma != -1){
                lengthStr = cellList.get(TYPE).trim().substring(left+1,comma);
            }else{
                lengthStr = cellList.get(TYPE).trim().substring(left+1,right);
            }
            PrintTools.println("@Length(max = "+lengthStr+")");
            String nameStr = cellList.get(NAME).trim().substring(0,1).toUpperCase()+cellList.get(NAME).substring(1,cellList.get(NAME).length());
            String typeStr = cellList.get(TYPE).trim();
            if("V".equals(typeStr.substring(0,1))){
                typeStr = "String";
            }else{
                if(comma == -1){
                    typeStr = "Long";
                }else{
                    typeStr = "Double";
                }
            }
            PrintTools.println("public "+typeStr+" get"+nameStr+"() {");
            PrintTools.println("   return this."+cellList.get(NAME).trim()+";");
            PrintTools.println("}");
        }
    }

    /**
     * 生成转换类toDTO方法
     * */
    public void printToDTO(String filePath,int sheetIndex,int rowNum){
        ArrayList<ArrayList<String>> dataList = er.readExcel(filePath, sheetIndex,rowNum);
        for (ArrayList<String> cellList : dataList) {
            String nameStr = cellList.get(NAME).trim().substring(0, 1).toUpperCase() + cellList.get(NAME).substring(1, cellList.get(NAME).length());
            PrintTools.println("dto.set" + nameStr + "(entity.get" + nameStr + "());");
        }
    }

    /**
     * 生成转换类fromDTO方法
     * */
    public void printFromDTO(String filePath,int sheetIndex,int rowNum){
        ArrayList<ArrayList<String>> dataList = er.readExcel(filePath, sheetIndex,rowNum);
        for (ArrayList<String> cellList : dataList) {
            String nameStr = cellList.get(NAME).trim().substring(0, 1).toUpperCase() + cellList.get(NAME).substring(1, cellList.get(NAME).length());
            PrintTools.println("entity.set" + nameStr + "(dto.get" + nameStr + "());");
        }
    }

    /**
     * 生成hibernate配置文件
     * <property name="srcUnitName" type="string">
     <column length="100" name="AAB004" not-null="false" />
     </property>
     * */
    public void printHibernate(String filePath,int sheetIndex,int rowNum){
        ArrayList<ArrayList<String>> dataList = er.readExcel(filePath, sheetIndex,rowNum);
        for (ArrayList<String> cellList : dataList) {
            int left = cellList.get(TYPE).lastIndexOf("(");
            int right = cellList.get(TYPE).lastIndexOf(")");
            int comma = cellList.get(TYPE).lastIndexOf(",");
            String typeStr = cellList.get(TYPE).trim();
            if ("V".equals(typeStr.substring(0, 1))) {
                typeStr = "string";
            } else {
                if (comma == -1) {
                    typeStr = "long";
                } else {
                    typeStr = "double";
                }
            }
            PrintTools.println("<property name=\"" + cellList.get(NAME).trim() + "\" type=\"" + typeStr + "\">");
            String lengthStr;
            if (comma != -1) {
                lengthStr = cellList.get(TYPE).trim().substring(left + 1, comma);
            } else {
                lengthStr = cellList.get(TYPE).trim().substring(left + 1, right);
            }
            String nullableStr = cellList.get(NULLABLE).trim();
            if ("Y".equals(nullableStr)) {
                nullableStr = "false";
            } else {
                nullableStr = "true";
            }
            PrintTools.println("   <column length=\"" + lengthStr + "\" name=\"" + cellList.get(COL).trim() + "\" not-null=\"" + nullableStr + "\" />");
            PrintTools.println("</property>");
        }
    }


    /**
     * 根据txt文件拼写entity成员变量
     *
     * @param namePath table.txt路径
     * @param jsonFieldPath 报文.txt路径
     * */
    public void printEntityMember(String namePath,String jsonFieldPath){
        ArrayList<ArrayList<String>> dataList = tr.readTxt(namePath);
        ArrayList<ArrayList<String>> jsonFieldList = tr.readTxt(jsonFieldPath);
        for (ArrayList<String> cellList : dataList) {
            String typeStr = cellList.get(1).trim();
            if("V".equals(typeStr.substring(0,1))){
                typeStr = "String";
            }else if("D".equals(typeStr.substring(0,1))){
                typeStr = "Date";
            }else{
                int comma = typeStr.substring(0,typeStr.length()-1).lastIndexOf(",");
                if(comma == -1){
                    typeStr = "Long";
                }else{
                    typeStr = "Double";
                }
            }

            String annotation = "";
            for (ArrayList<String> list : jsonFieldList){
                if(list.get(2).toLowerCase().equals(cellList.get(0))){
                    annotation = list.get(0);
                }
            }
            PrintTools.println("@JSONField(\""+annotation+"\")");
            PrintTools.println("private "+typeStr+" "+cellList.get(0).trim()+";");
            PrintTools.println("");

        }
    }

}
