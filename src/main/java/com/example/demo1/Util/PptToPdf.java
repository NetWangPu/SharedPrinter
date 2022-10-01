package com.example.demo1.Util;

//写一个ppt转pdf的工具类
public class PptToPdf {
    public static void pptToPdf(String pptPath, String pdfPath) {
        //调用libreoffice的命令行工具
        String command = "D:\\Program Files\\LibreOffice 6\\program\\soffice.exe --headless --convert-to pdf --outdir " + pdfPath + " " + pptPath;
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
