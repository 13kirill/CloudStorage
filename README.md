# Облачное хранилище
## Принцип работы
### Вход в облачное хранилище
Чтобы войти в облачное хранилище, необходимо ввести логин и пароль в форе входа.
Эти данные отправятся на сервер и в случае неверных данных будет выброшено
BadCredentialsException("Invalid username or password")

### Загрузить файл
Обрабатыается в методе контроллера
```
@PostMapping("/file")
public void uploadFileToServer(@RequestHeader("auth-token") String authToken,
@RequestParam(name = "filename") String filename,
@RequestPart MultipartFile file) {
fileService.uploadFile(filename, file);
}
```

### Переименовать файл
Обрабатыается в методе контроллера
```
    @PutMapping("/file")
    public void editFileName(@RequestParam(name = "filename") String filename,
                             @RequestBody EditFilenameRequestDTO editFilenameRequestDTO) {
        fileService.editFilename(filename, editFilenameRequestDTO.getFilename());
    }
```

### Удалить файл
Обрабатыается в методе контроллера
```
    @DeleteMapping("/file")
    public void deleteFile(@RequestParam(name = "filename") String filename) {
        fileService.deleteFile(filename);
    }
```

### Скачать файл
Обрабатыается в методе контроллера
```
    @GetMapping(path = "/file", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MultiValueMap<String, Object> downloadFileFromCloud(@RequestParam(name = "filename") String filename) {
        return fileService.downloadFile(filename);
    }
```

### Список файлов
Обрабатыается в методе контроллера
```
    @GetMapping("/list")
    public List<StoredFileDto> getInfoAboutAllFiles(@RequestHeader("auth-token") String authToken,
                                                    @RequestParam("limit") @Min(1) Integer limit) {
        return fileService.getInfoAboutAllFiles(limit);
    }
```

## Демонстрация работы
