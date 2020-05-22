package com.seeyon.oversea;


import com.seeyon.oversea.utils.UUIDTool;

public class TestMain
{
    public static void main(String[] args)
    {
        for(int i=0;i<200;i++) {
            System.out.println(UUIDTool.getLong());
        }
    }

}
