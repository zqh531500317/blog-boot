package com.zqh.blogboot.utils;


import java.io.*;

/**
 * 文件操作相关的工具类
 */
public class FileUtil {
    private FileUtil() {
    }

    /**
     * 获取最后文件上传的路径
     */
    public static String getFinalPath(String fileDestStr,String fileName){
        return fileDestStr+'/'+fileName;
    }
    public static String getExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }
    public static boolean upload(String fileDestStr,String fileName, InputStream is){
       // DateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
        //按月区分图片文件夹
        //String pack = dateFormat.format(new Date());
        File file=new File(fileDestStr+'/'+fileName);
        BufferedOutputStream bos=null;
        byte[] bytes=new byte[1024];
        int len=0;
        try {
            bos=new BufferedOutputStream(new FileOutputStream(file));
            if((len = is.read(bytes))!=-1){
                bos.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            close(bos);
            close(is);
        }
        return true;
    }
    //关闭流
    private static void close(BufferedOutputStream bos){
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void close(InputStream is){
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
