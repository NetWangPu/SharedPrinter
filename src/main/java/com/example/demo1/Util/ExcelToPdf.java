package com.example.demo1.Util;

//写一个excel转pdf的工具类
public class ExcelToPdf {
    public static void excelToPdf(String excelPath, String pdfPath) {
        //调用libreoffice的命令行工具
        String command = "D:\\Program Files\\LibreOffice 6\\program\\soffice.exe --headless --convert-to pdf --outdir " + pdfPath + " " + excelPath;
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
