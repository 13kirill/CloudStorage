package ru.netology.cloudstorage.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.model.DTO.EditFileNameRequestDTO;
import ru.netology.cloudstorage.model.DTO.FileDTO;
import ru.netology.cloudstorage.service.FileService;

import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/cloud/file")
public class FileController {

    private FileService fileService;

    @PostMapping
    public void uploadFileToServer(@RequestHeader("auth-token") String authToken,
                                   @RequestParam(name = "fileName") String fileName,
                                   @RequestPart MultipartFile file) {
        fileService.uploadFile(fileName, file);
    }

    @DeleteMapping
    public void deleteFile(@RequestParam(name = "fileName") String fileName) {
        fileService.deleteFile(fileName);
    }

    @GetMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MultiValueMap<String, Object> downloadFileFromCloud(@RequestParam(name = "fileName") String fileName) {
        return fileService.downloadFile(fileName);
    }

    @PutMapping
    public void editFileName(@RequestParam(name = "fileName") String fileName,
                             @RequestBody EditFileNameRequestDTO editFileNameRequestDTO) {
        fileService.editFileName(fileName, editFileNameRequestDTO.getName());
    }

    @GetMapping("/list")
    public List<FileDTO> getInfoAboutAllFiles(@RequestHeader("auth-token") String authToken,
                                              @RequestParam("limit") @Min(1) Integer limit) {
        return fileService.getInfoAboutAllFiles(limit);
    }
}