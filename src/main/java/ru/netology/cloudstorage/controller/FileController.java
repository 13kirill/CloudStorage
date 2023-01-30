package ru.netology.cloudstorage.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.model.DTO.EditFilenameRequestDTO;
import ru.netology.cloudstorage.model.DTO.StoredFileDto;
import ru.netology.cloudstorage.service.FileService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
public class FileController {

    private FileService fileService;

    @PostMapping("/file")
    public void uploadFileToServer(@RequestHeader("auth-token") String authToken,
                                   @RequestParam(name = "filename") String filename,
                                   @RequestPart MultipartFile file) {
        fileService.uploadFile(filename, file);
    }

    @DeleteMapping("/file")
    public void deleteFile(@RequestParam(name = "filename") String filename) {
        fileService.deleteFile(filename);
    }

    @GetMapping(path = "/file", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MultiValueMap<String, Object> downloadFileFromCloud(@RequestParam(name = "filename") String filename) {
        return fileService.downloadFile(filename);
    }

    @PutMapping("/file")
    public void editFileName(@RequestParam(name = "filename") String filename,
                             @RequestBody EditFilenameRequestDTO editFilenameRequestDTO) {
        fileService.editFilename(filename, editFilenameRequestDTO.getFilename());
    }

    @GetMapping("/list")
    public List<StoredFileDto> getInfoAboutAllFiles(@RequestHeader("auth-token") String authToken,
                                                    @RequestParam("limit") @Min(1) Integer limit) {
        return fileService.getInfoAboutAllFiles(limit);
    }
}