package com.abc.controller;

import com.abc.model.Upload;
import com.abc.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
public class UploadController {

//    @Qualifier("IFileService")
    @Autowired
    private IFileService iFileService;


    @PostMapping("/upload")
    public ResponseEntity<String> create(@RequestParam(name = "file") MultipartFile[] files) {

        String fileName = "", imageUrl = "", downloadUrl;
        for (MultipartFile file : files) {
            try {

                fileName = iFileService.save(file);

                imageUrl = iFileService.getImageUrl(fileName);

//                downloadUrl = "https://firebasestorage.googleapis.com/v0/b/medical-ecom-72c30.appspot.com/o/imageUrl?alt=media"

                // do whatever you want with that
//                return new ResponseEntity<String>(imageUrl, HttpStatus.OK);

            } catch (Exception e) {
                //  throw internal error;
                return new ResponseEntity<String>("Failed to upload file!", HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<String>(imageUrl, HttpStatus.OK);
    }

    @PostMapping("/upload/base")
    public ResponseEntity<String> createBase(@RequestBody Upload up) {
//        String base64Image = up.getBase().split(",")[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(up.getBase());

        String fileName = "", imageUrl = "", downloadUrl;
            try {
//                BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
                fileName = iFileService.save(imageBytes, up.getFileName());
                imageUrl = iFileService.getImageUrl(fileName);
            } catch (Exception e) {
                //  throw internal error;
                return new ResponseEntity<String>("Failed to upload file!", HttpStatus.BAD_REQUEST);
            }


        return new ResponseEntity<String>(imageUrl, HttpStatus.OK);
    }
}
