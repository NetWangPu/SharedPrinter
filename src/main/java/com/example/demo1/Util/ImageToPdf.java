package com.example.demo1.Util;

//写一个图片转pdf的工具类
public class ImageToPdf {
    public static void imageToPdf(String imagePath, String pdfPath) {
        //调用libreoffice的命令行工具
        String command = "D:\\Program Files\\LibreOffice 6\\program\\soffice.exe --headless --convert-to pdf --outdir " + pdfPath + " " + imagePath;
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
