package com.tong;

/**
 * Created by Tong on 2017/8/14.
 *
 * @author Tong
 */
public class MainApp {

    public static void main(String[] args) {
        String filePath = "src/main/resources/table.txt";
        String jsonFieldPath = "src/main/resources/报文.txt";
        int sheetIndex = 3;
        int rowNum = 40;

        CreateX create = new CreateX();
//        create.printMember(filePath, sheetIndex, rowNum);
//        create.printGetter(filePath, sheetIndex, rowNum);
//        create.printFromDTO(filePath, sheetIndex, rowNum);
//        create.printToDTO(filePath, sheetIndex, rowNum);
//        create.printHibernate(filePath, sheetIndex, rowNum);
        create.printEntityMember(filePath,jsonFieldPath);

    }
}
