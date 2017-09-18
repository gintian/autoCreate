package com.tong;

import java.util.ArrayList;

public class CreateX {
    private static final int col = 1;
    private static final int type = 2;
    private static final int nullable = 3;
    private static final int comment = 4;
    private static final int name = 7;

    /**
     * 生成成员变量
     */
    public void printMember(String filePath,int sheetIndex,int rowNum){
        ExcelReader er = new ExcelReader();
        ArrayList<ArrayList<String>> dataList = er.readExcel(filePath, sheetIndex,rowNum);
        for (int i = 0; i < dataList.size(); i++) {
            ArrayList<String> cellList = dataList.get(i);
            ExcelReader.println("/**");
            ExcelReader.println(" * "+cellList.get(comment).trim());
            ExcelReader.println(" * ");
            ExcelReader.println(" * @Column(Name = \""+cellList.get(col).trim()+"\")");
            ExcelReader.println(" */ ");
            String typeStr = cellList.get(type).trim();
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
            ExcelReader.println("private "+typeStr+" "+cellList.get(name).trim()+";");
        }
    }

    /**
     * 生成get方法
     * */
    public void printGetter(String filePath,int sheetIndex,int rowNum){
        ExcelReader er = new ExcelReader();
        ArrayList<ArrayList<String>> dataList = er.readExcel(filePath, sheetIndex,rowNum);
        for (int i = 0; i < dataList.size(); i++) {
            ArrayList<String> cellList = dataList.get(i);
            ExcelReader.println("@Label(value = \""+cellList.get(comment).trim()+"\")");
            int left = cellList.get(type).lastIndexOf("(");
            int right = cellList.get(type).lastIndexOf(")");
            int comma = cellList.get(type).lastIndexOf(",");
            String lengthStr = "";
            if(comma != -1){
                lengthStr = cellList.get(type).trim().substring(left+1,comma);
            }else{
                lengthStr = cellList.get(type).trim().substring(left+1,right);
            }
            ExcelReader.println("@Length(max = "+lengthStr+")");
            String nameStr = cellList.get(name).trim().substring(0,1).toUpperCase()+cellList.get(name).substring(1,cellList.get(name).length());
            String typeStr = cellList.get(type).trim();
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
            ExcelReader.println("   return this."+cellList.get(name).trim()+";");
            ExcelReader.println("}");
        }
    }

    /**
     * 生成转换类toDTO方法
     * */
    public void printToDTO(String filePath,int sheetIndex,int rowNum){
        ExcelReader er = new ExcelReader();
        ArrayList<ArrayList<String>> dataList = er.readExcel(filePath, sheetIndex,rowNum);
        for (int i = 0; i < dataList.size(); i++) {
            ArrayList<String> cellList = dataList.get(i);
            String nameStr = cellList.get(name).trim().substring(0,1).toUpperCase()+cellList.get(name).substring(1,cellList.get(name).length());
            ExcelReader.println("dto.set"+nameStr+"(entity.get"+nameStr+"());");
        }
    }

    /**
     * 生成转换类fromDTO方法
     * */
    public void printFromDTO(String filePath,int sheetIndex,int rowNum){
        ExcelReader er = new ExcelReader();
        ArrayList<ArrayList<String>> dataList = er.readExcel(filePath, sheetIndex,rowNum);
        for (int i = 0; i < dataList.size(); i++) {
            ArrayList<String> cellList = dataList.get(i);
            String nameStr = cellList.get(name).trim().substring(0,1).toUpperCase()+cellList.get(name).substring(1,cellList.get(name).length());
            ExcelReader.println("entity.set"+nameStr+"(dto.get"+nameStr+"());");
        }
    }

    /**
     * 生成hibernate配置文件
     * <property name="srcUnitName" type="string">
     <column length="100" name="AAB004" not-null="false" />
     </property>
     * */
    public void printHibernate(String filePath,int sheetIndex,int rowNum){
        ExcelReader er = new ExcelReader();
        ArrayList<ArrayList<String>> dataList = er.readExcel(filePath, sheetIndex,rowNum);
        for (int i = 0; i < dataList.size(); i++) {
            ArrayList<String> cellList = dataList.get(i);
            int left = cellList.get(type).lastIndexOf("(");
            int right = cellList.get(type).lastIndexOf(")");
            int comma = cellList.get(type).lastIndexOf(",");
            String typeStr = cellList.get(type).trim();
            if("V".equals(typeStr.substring(0,1))){
                typeStr = "string";
            }else{
                if(comma == -1){
                    typeStr = "long";
                }else{
                    typeStr = "double";
                }
            }
            ExcelReader.println("<property name=\""+cellList.get(name).trim()+"\" type=\""+typeStr+"\">");
            String lengthStr = "";
            if(comma != -1){
                lengthStr = cellList.get(type).trim().substring(left+1,comma);
            }else{
                lengthStr = cellList.get(type).trim().substring(left+1,right);
            }
            String nullableStr = cellList.get(nullable).trim();
            if("Y".equals(nullableStr)){
                nullableStr = "false";
            }else{
                nullableStr = "true";
            }
            ExcelReader.println("   <column length=\""+lengthStr+"\" name=\""+cellList.get(col).trim()+"\" not-null=\""+nullableStr+"\" />");
            ExcelReader.println("</property>");
        }
    }
}
