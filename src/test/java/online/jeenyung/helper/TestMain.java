package online.jeenyung.helper;


import online.jeenyung.helper.utils.UUIDTool;

public class TestMain
{
    public static void main(String[] args)
    {
        for(int i=0;i<200;i++) {
            System.out.println(UUIDTool.getLong());
        }
    }

}
