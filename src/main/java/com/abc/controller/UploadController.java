package com.abc.controller;

import com.abc.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
}
