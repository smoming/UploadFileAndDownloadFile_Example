package pers.mike.file.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.mike.file.bean.QueryViewModel;
import pers.mike.file.bean.ReqFileWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RestController
@RequestMapping("/file")
public class FileController {
  @PostMapping("/upload_file_1")
  public String uploadFile1(@ModelAttribute ReqFileWrapper model) throws IOException {
    System.out.println("################################");
    System.out.println(Arrays.toString(model.getFile().getBytes()));
    System.out.println("################################");
    return "upload success...";
  }

  @PostMapping("/upload_file_2")
  public String uploadFile2(@RequestParam("file") MultipartFile mf) throws IOException {
    System.out.println("################################");
    System.out.println(Arrays.toString(mf.getBytes()));
    System.out.println("################################");
    return "upload success...";
  }

  @GetMapping("/download_file_1")
  public ResponseEntity<byte[]> downloadGet(@RequestParam("param") String model) {
    return getResponseEntity(model);
  }

  @PostMapping("/download_file_2")
  public ResponseEntity<byte[]> downloadPost(@RequestBody QueryViewModel model) {
    return getResponseEntity(model.getId() + ":" + model.getName());
  }

  private ResponseEntity<byte[]> getResponseEntity(String content) {
    ResponseEntity<byte[]> result = null;
    String fileName = "download_test.txt";
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      baos.write(content.getBytes(StandardCharsets.UTF_8));
      baos.flush();
      baos.close();

      HttpHeaders headers = new HttpHeaders();
      headers.setContentDispositionFormData("attachment", fileName);
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      result = new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }
}
