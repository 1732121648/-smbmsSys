package com.xinshi.smbms.util;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * 文件上传
 */

public class UploadFile {

    public static  void doFirst(MultipartFile uploadFile,Model model,HttpSession session){
        String path = session.getServletContext().getRealPath("/images");
        String originalFilename=null;
        String idPicPath=null;
        if(!uploadFile.isEmpty()){
            originalFilename = uploadFile.getOriginalFilename();     //原文件名
            String prefix = FilenameUtils.getExtension(originalFilename);//原文件后缀名
            int fileSize=500000;
            if(uploadFile.getSize()>fileSize){
                model.addAttribute("uploadFileError"," * 上传大小不得超过  500KB");
            }else if(prefix.equalsIgnoreCase("jpg")|| prefix.equalsIgnoreCase("png")){
                File file =new File(path,originalFilename);
                try {
                    uploadFile.transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                idPicPath=path+File.separator+originalFilename;
            }
            System.out.println(" 111  "+idPicPath);
        }else{
            model.addAttribute("uploadFileError","上传文件格式不正确");
        }

    }
}
