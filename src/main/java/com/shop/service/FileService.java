package com.shop.service;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log4j2
public class FileService {

    // c:/shop 파일 저장
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {

        UUID uuid = UUID.randomUUID();

        //test.jpg 확장자 읽기
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        log.info("extension --->"+extension);
        //.png

        String savedFileName = uuid.toString() + extension;
        log.info("savedFileName --->"+savedFileName);
        //d09f2f56-ef42-4a3e-a7cf-ed6cc7ed227a.png

        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        log.info("fileUploadFullUrl --->"+fileUploadFullUrl);
        //C:/shop/item/d09f2f56-ef42-4a3e-a7cf-ed6cc7ed227a.png

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        }else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
