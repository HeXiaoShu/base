package com.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Description 文件处理工具类
 * @Author Hexiaoshu
 * @Date 2021/11/9
 * @modify
 */
@Slf4j
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

    /**
     * 文件切割 / FileChannel
     * 将文件切割到，文件名的目录下
     * split(file, 1024 * 1024 * 200)
     * @param file      切割文件
     * @param splitSize 切割大小 byte
     */
    public static Boolean split(File file,long splitSize){
        boolean flag=true;
        long start = System.currentTimeMillis();
        String fileName = file.getName();
        String outDir = fileName.substring(0,fileName.indexOf("."));
        long length = file.length();
        System.out.println("当前文件大小:"+length);
        System.out.println("切割值:"+splitSize);
        BigDecimal a = new BigDecimal(length);
        BigDecimal b = new BigDecimal(splitSize);
        BigDecimal splitCount = a.divide(b).setScale(0, RoundingMode.HALF_DOWN);
        int splitNum = splitCount.intValue();
        System.out.println("切割块数:"+splitNum);
        long outByte = length - (splitCount.multiply(b).longValue());
        System.out.println("剩余大小："+outByte);
        String absolutePath = file.getAbsolutePath();
        System.out.println("开始切割文件:"+absolutePath);
        File parentFile = file.getParentFile();
        String outPath=parentFile.getAbsolutePath()+File.separator+outDir;
        File outPathFile = new File(outPath);
        boolean mkdirs = outPathFile.mkdirs();
        if (mkdirs){
            System.out.println("输出路径:"+outPath);
        }else {
            System.out.println("输出路径:"+outPath+"创建失败");
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            FileChannel inputChannel = fis.getChannel();
            //切割指针
            long startPoint = 0;
            for (int i = 1; i <= splitNum; i++) {
                startPoint = toSplit(splitSize, fileName, outPath, inputChannel, startPoint, i);
            }
            int lastCount=splitNum+1;
            toSplit(splitSize, fileName, outPath, inputChannel, startPoint, lastCount);
        }catch (Exception e){
            flag=false;
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start)/1000+" 秒");
        return flag;
    }

    private static long toSplit(long splitSize, String fileName, String outDir, FileChannel inputChannel, long startPoint, int i) throws IOException {
        FileOutputStream fos;
        FileChannel outputChannel;
        String splitFileName = outDir+File.separator+ fileName +"_"+ i;
        File splitFile = new File(splitFileName);
        boolean newFile = splitFile.createNewFile();
        if (newFile){
            System.out.println(splitFile.getAbsolutePath()+"已生成..");
        }
        fos = new FileOutputStream(splitFileName);
        outputChannel = fos.getChannel();
        inputChannel.transferTo(startPoint, splitSize, outputChannel);
        //移动指针
        startPoint += splitSize;
        outputChannel.close();
        fos.close();
        return startPoint;
    }

    /**
     * 文件合并
     * @param dir 切块文件名目录
     * @param out 输出文件
     */
    public static Boolean merge(File dir,File out){
        if (!dir.isDirectory()){
            return false;
        }
        System.out.println("合并文件目录："+dir);
        File[] files = dir.listFiles();
        if (files==null||files.length==0){
            return false;
        }
        String name = files[0].getName();
        String outDir = name.substring(0,name.indexOf("_")+1);
        System.out.println("合并文件前缀："+outDir);
        LinkedList<FileInputStream> splitFiles = new LinkedList<>();
        for(int i=1; i<files.length+1; i++){
            try {
                String curFile = dir + File.separator + outDir + i;
                System.out.println("处理文件："+curFile);
                splitFiles.add(new FileInputStream(curFile));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            Enumeration<FileInputStream> en = Collections.enumeration(splitFiles);
            SequenceInputStream sis = new SequenceInputStream(en);
            FileOutputStream fos = new FileOutputStream(out);
            byte[] buf = new byte[1024];
            int len;
            while ((len = sis.read(buf)) != -1) {
                fos.write(buf,0,len);
            }
            fos.close();
            sis.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("文件输出成功: "+out.getAbsolutePath());
        return true;
    }

    /**
     * 浏览器文件下载
     * @param targetFile  目标文件
     * @param response    response
     */
    public static void browserDownLoad(File targetFile, HttpServletResponse response){
        OutputStream out = null;
        InputStream in = null;
        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(targetFile.getName(), "UTF-8"));
            response.addHeader("Content-Length", "" + targetFile.length());
            response.setContentType("application/octet-stream");
            out = new BufferedOutputStream(response.getOutputStream());
            in = new BufferedInputStream(new FileInputStream(targetFile));
            IOUtils.copy(in, out);
            out.flush();
        } catch (Exception e) {
            log.error("客户端断开了链接");
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 通过资源路径下载文件
     * @param targetUrl  目标路径
     * @param targetFile 输出文件
     */
    public static Boolean urlDownLoad(String targetUrl,File targetFile){
        OutputStream out = null;
        InputStream in = null;
        boolean flag=true;
        try {
            URL url = new URL(targetUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            in =  new BufferedInputStream(urlConnection.getInputStream());
            out=  new BufferedOutputStream(new FileOutputStream(targetFile));
            IOUtils.copy(in, out);
            out.flush();
        }catch (Exception e){
            flag=false;
            log.error("下载异常",e);
        }finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        return flag;
    }


    public static void main(String[] args) throws Exception {
        /*File file = new File("E:\\鹰眼.mp4");
        Boolean split = split(file, 1024 * 1024 * 50);
        System.out.println(split);*/
        Boolean merge = merge(new File("E:\\鹰眼"), new File("E:\\BaiduNetdiskDownload\\鹰眼.mp4"));
        System.out.println("合并完成："+merge);
    }


}
