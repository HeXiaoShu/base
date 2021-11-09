package com.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 文件处理工具类
 * @Author Hexiaoshu
 * @Date 2021/11/9
 * @modify
 */
public class FileUtil {

    /**
     * 按行读取文件文本内容
     * @param is 输入流
     * @param m  缓存 / M
     * @return 字符集合
     */
    public static List<String> readFileContent(InputStream is, int m) {
        BufferedReader reader;
        List<String> listContent = new ArrayList<>();
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            reader = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8), m==0 ? 5 * 1024 * 1024 : m * 1024 * 1024);
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                listContent.add(tempString);
            }
            is.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listContent;
    }

    /**
     * 按行写入文本内容
     * @param content 文本内容集合
     * @param os 输出流
     * @param m  缓存 / M
     */
    public static void writeFileContent(List<String> content,OutputStream os,int m){
        BufferedWriter bw;
        try {
            BufferedOutputStream bos = new BufferedOutputStream(os);
            bw = new BufferedWriter(new OutputStreamWriter(bos, StandardCharsets.UTF_8), m==0?5 * 1024 * 1024: m * 1024 * 1024);
            for(String str:content){
                bw.write(str);
                bw.newLine();
                bw.flush();
            }
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
