package com.tong;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Tong on 2018/10/20.
 *
 * @author Tong
 */
public class TxtReader {

    public ArrayList<ArrayList<String>> readTxt(String filePath){
        ArrayList<ArrayList<String>> returnList = new ArrayList<ArrayList<String>>();
        File file = new File(filePath);
        BufferedReader br = null;
        String data;
        try {
            br = new BufferedReader(new FileReader(file));
            while((data = br.readLine())!=null){
                String[] row = data.split(" ");
                ArrayList<String> rowList = new ArrayList<String>(Arrays.asList(row));
                returnList.add(rowList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnList;
    }
}
