# �������� ���������
## ������� ������
### ���� � �������� ���������
����� ����� � �������� ���������, ���������� ������ ����� � ������ � ���� �����.
��� ������ ���������� �� ������ � � ������ �������� ������ ����� ���������
BadCredentialsException("Invalid username or password")

### ��������� ����
������������� � ������ �����������
```
@PostMapping("/file")
public void uploadFileToServer(@RequestHeader("auth-token") String authToken,
@RequestParam(name = "filename") String filename,
@RequestPart MultipartFile file) {
fileService.uploadFile(filename, file);
}
```

### ������������� ����
������������� � ������ �����������
```
    @PutMapping("/file")
    public void editFileName(@RequestParam(name = "filename") String filename,
                             @RequestBody EditFilenameRequestDTO editFilenameRequestDTO) {
        fileService.editFilename(filename, editFilenameRequestDTO.getFilename());
    }
```

### ������� ����
������������� � ������ �����������
```
    @DeleteMapping("/file")
    public void deleteFile(@RequestParam(name = "filename") String filename) {
        fileService.deleteFile(filename);
    }
```

### ������� ����
������������� � ������ �����������
```
    @GetMapping(path = "/file", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MultiValueMap<String, Object> downloadFileFromCloud(@RequestParam(name = "filename") String filename) {
        return fileService.downloadFile(filename);
    }
```

### ������ ������
������������� � ������ �����������
```
    @GetMapping("/list")
    public List<StoredFileDto> getInfoAboutAllFiles(@RequestHeader("auth-token") String authToken,
                                                    @RequestParam("limit") @Min(1) Integer limit) {
        return fileService.getInfoAboutAllFiles(limit);
    }
```

## ������������ ������
