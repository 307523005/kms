package com.basewin.kms.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class FileDownUtil {
    /*
     * 通用下载方法
     */
    public static boolean downFile(HttpServletRequest request, HttpServletResponse response, String paths, String fname) {
        File file = new File(paths);
        if (file.exists()) { //判断文件父目录是否存在
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                //转码，免得文件名中文乱码
                fname = URLEncoder.encode(fname, "UTF-8");
                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment;fileName=" + fname);
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
    /**
     * springboot的方法
     *
     * @param filePath
     * @param fname
     * @return
     * @throws IOException
     */
    public static ResponseEntity<InputStreamResource> downloadFile(String filePath, String fname)
            throws IOException {
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        fname = URLEncoder.encode(fname, "UTF-8");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fname));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }
}
