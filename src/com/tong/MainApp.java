package com.tong;

import java.util.ArrayList;

/**
 * Created by Tong on 2017/8/14.
 */
public class MainApp {

    public static void main(String[] args) {
        CreateGetter cg = new CreateGetter();
        CreateMemberVariable cv = new CreateMemberVariable();
//        cg.printGetter("F:\\javaTools\\table.xlsx",2,25);
        cv.printMember("F:\\javaTools\\table.xlsx",0,36);

    }
}
