package top.miyamoto.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @description: 处理上传文件
 * @author: ZAG
 * @time: 2021/3/9 0009 19:26
 */
@Service
@Log4j2
public class CommonService {
    public String handleUploadFile(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        System.out.println("filename:" + fileName);
        System.out.println("path: "+path);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        File filePathIF = new File(path, String.valueOf(now.getYear()));
        System.out.println(filePathIF.getParent());
        System.out.println(filePathIF.getAbsolutePath());
        if (!filePathIF.exists()) {
            try{
                System.out.println(filePathIF.mkdir());
            }catch (Exception e){
                System.out.println(e.toString());
            }
            filePathIF = new File(filePathIF.getAbsolutePath(), String.valueOf(now.getMonthValue()));
            if (!filePathIF.exists()) {
                filePathIF.mkdir();
                filePathIF = new File(filePathIF.getAbsolutePath(), String.valueOf(now.getDayOfMonth()));
                if (!filePathIF.exists()) {
                    filePathIF.mkdir();
                }
            }
        }
        String[] split = fileName.split("\\.");
        String newFileName = UUID.randomUUID().toString().toLowerCase() + "." + split[split.length - 1];
        File dest = new File(filePathIF, newFileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.error("文件处理失败：" + e.getMessage());
            throw new RuntimeException("文件处理失败");
        }
        return dest.getAbsolutePath();
    }
}
