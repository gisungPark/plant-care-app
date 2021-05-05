package com.ssafy.green.controller;

import com.ssafy.green.service.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class UploadController {
    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    @ResponseBody
    public List<String> upload(@RequestParam("data") MultipartFile[] multipartFile) throws IOException {

        List<String> filenames = new ArrayList<>();

        for(MultipartFile m :multipartFile){
            String filename = s3Uploader.upload(m);
            filenames.add(filename);
        }
        return filenames;
    }

}
