package com.autowaterworks.core.controller;

import com.autowaterworks.core.service.FileService;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/image")
public class FileRestApi {

  @Autowired
  private FileService fileService;


  @PostMapping("upload")
  public ResponseEntity<Map<String, String>> handleFileUpload(@RequestParam("file") MultipartFile file) {
    Map<String, String> results = new HashMap<>();
    String message;
    try {
      fileService.createUploadFolder();
      fileService.store(file);
      message = "You successfully uploaded " + file.getOriginalFilename() + "!";
      results.put("urlImage", file.getOriginalFilename());
      results.put("message", message);
      results.put("status", HttpStatus.OK.toString());
    } catch (Exception e) {
      message = "FAIL to upload " + file.getOriginalFilename() + "!";
      results.put("urlImage", null);
      results.put("message", message);
      results.put("status", HttpStatus.EXPECTATION_FAILED.toString());
    } finally {
      return ResponseEntity.ok().body(results);
    }
  }

  @PostMapping("uploadByUrl")
  public ResponseEntity<Map<String, String>> handleFileUpload(@RequestParam("base64Image") String base64Image) {
    Map<String, String> results = new HashMap<>();
    String message;
    try {
      fileService.createUploadFolder();
      fileService.storeByBase64Image(base64Image);
      message = "You successfully uploaded !";
      results.put("message", message);
      results.put("status", HttpStatus.OK.toString());
    }catch (Exception e) {
      message = "FAIL to upload! Errors: " + e.getMessage();
      results.put("message", message);
      results.put("status", HttpStatus.EXPECTATION_FAILED.toString());
    } finally {
      return ResponseEntity.ok().body(results);
    }
  }

}
