package com.tong;

/**
 * Created by Tong on 2017/8/14.
 */
public class MainApp {

    public static void main(String[] args) {
        String filePath = "F://javaTools/table.xlsx";
        int sheetIndex = 3;
        int rowNum = 40;

        CreateX create = new CreateX();
//        create.printMember(filePath, sheetIndex, rowNum);
        create.printGetter(filePath, sheetIndex, rowNum);
//        create.printFromDTO(filePath, sheetIndex, rowNum);
//        create.printToDTO(filePath, sheetIndex, rowNum);
//        create.printHibernate(filePath, sheetIndex, rowNum);

    }
}
