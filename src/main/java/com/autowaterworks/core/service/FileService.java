package com.autowaterworks.core.service;


import com.google.common.primitives.Bytes;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

@Service
public class FileService {
  private final Path rootLocation = Paths.get("upload-dir");

  public void store(MultipartFile file) {
    try {
      Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
    } catch (Exception e) {
      throw new RuntimeException("FAIL!");
    }
  }

  public void storeByBase64Image(String base64Image) throws Exception {
    String[] datas = base64Image.split(",");
    String extension;
    switch (datas[0]) {
      case "data:image/jpeg;base64":
        extension = ".jpeg";
        break;
      case "data:image/png;base64":
        extension = ".png";
        break;
      default:
        extension = ".jpg";
        break;
    }

    byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(datas[1]);
    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
    File outputFile = new File(this.rootLocation.resolve("imageMap"+extension).toString());
    ImageIO.write(image, "png", outputFile);
  }

  public void createUploadFolder() {
    try {
      if (!rootLocation.toFile().exists()) {
        Files.createDirectory(rootLocation);
      }

    } catch (IOException e) {
      throw new RuntimeException("Could not initialize rootLocation! Errors is " + e.getMessage());
    }
  }

  public Path getRootLocationPath() {
    return this.rootLocation;
  }
}
