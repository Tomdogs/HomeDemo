package com.example.homedemo.utils;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by lijingru on 2017/2/27.
 */

public class FileUtil {

    /**
     * 获取文件夹大小
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file){

        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++)
            {
                if (fileList[i].isDirectory())
                {
                    size = size + getFolderSize(fileList[i]);
                }else{
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return size/1048576;
        return size;
    }

    /**
     * 格式化单位
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size/1024;
        if(kiloByte < 1) {
            return size + "Byte(s)";
        }

        double megaByte = kiloByte/1024;
        if(megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte/1024;
        if(gigaByte < 1) {
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte/1024;
        if(teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


    /**
     * 删除一个目录中的所有文件
     * @param directory	所要删除的目录
     * @return
     */
    public static boolean deleteFileOfDirectory(File directory){
        //如果是目录则不操作
        try {
            if (directory == null || directory.isFile()) {
                return false;
            }
            if (directory.isDirectory()) {
                File[] arrFiles = directory.listFiles();
                if (arrFiles != null && arrFiles.length > 0) {
                    for (int i = 0; i < arrFiles.length; i++) {
                        if (arrFiles[i].isFile()) {
                            arrFiles[i].delete();
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 删除文件，可以是单个文件或文件夹
     * @param   fileName    待删除的文件名
     * @return 文件删除成功返回true,否则返回false
     */
    public static boolean delete(String fileName){
        if (fileName == null) {
            return false;
        }
        File file = new File(fileName);
        if(!file.exists()){
            return false;
        }else{
            if(file.isFile()){

                return deleteFile(fileName);
            }else{
                boolean isDeleteSuccess = false;
                try {
                    isDeleteSuccess = deleteDirectory(fileName);
                } catch (Throwable e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

                return isDeleteSuccess;
            }
        }
    }

    /**
     * 删除单个文件
     * @param   fileName    被删除文件的文件名
     * @return 单个文件删除成功返回true,否则返回false
     */
    public static boolean deleteFile(String fileName){
        File file = new File(fileName);
        if(file.isFile() && file.exists()){
            file.delete();
            return true;
        }else{
            return false;
        }
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   dir 被删除目录的文件路径
     * @return  目录删除成功返回true,否则返回false
     */
    public static boolean deleteDirectory(String dir){
        //如果dir不以文件分隔符结尾，自动添加文件分隔符
        if(!dir.endsWith(File.separator)){
            dir = dir+ File.separator;
        }
        File dirFile = new File(dir);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if(!dirFile.exists() || !dirFile.isDirectory()){
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        if (files != null) {
            for(int i=0;i<files.length;i++){
                //删除子文件
                if(files[i].isFile()){
                    flag = deleteFile(files[i].getAbsolutePath());
                    if(!flag){
                        break;
                    }
                }
                //删除子目录
                else{
                    flag = deleteDirectory(files[i].getAbsolutePath());
                    if(!flag){
                        break;
                    }
                }
            }
        }
        if(!flag){
            return false;
        }

        //删除当前目录
        if(dirFile.delete()){
            return true;
        }else{
            return false;
        }
    }


    /**
     *
     * @param dir 存放bitmap的文件夹的完整路径
     * @param bitmap 需要保存的bitmap
     * @return bitmap文件的完整路径
     */
    public static String saveBitmap(String dir, Bitmap bitmap){
        long dataTake = System.currentTimeMillis();
        String jpegName = dir + File.separator + "picture_" + dataTake + ".jpg";
        File file = new File(jpegName);
        File parentFile = file.getParentFile();//创建父文件夹
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return jpegName;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


}
