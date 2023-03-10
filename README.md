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
![Screenshot_136](https://user-images.githubusercontent.com/91612871/224330228-c20a430c-928d-4c4a-b2e4-e0a4df1f818c.png)
![Screenshot_137](https://user-images.githubusercontent.com/91612871/224330232-7a37cc60-f453-43b5-9e93-33b723e87e2a.png)
![Screenshot_138](https://user-images.githubusercontent.com/91612871/224330236-910e615f-a520-4ecd-9699-841266a22928.png)
![Screenshot_139](https://user-images.githubusercontent.com/91612871/224330239-3ef0aba2-7b10-4cc8-942f-105f1e5fc37d.png)
![Screenshot_140](https://user-images.githubusercontent.com/91612871/224330244-04e98b79-57a2-4486-a702-a1fdbde3a663.png)
![Screenshot_141](https://user-images.githubusercontent.com/91612871/224330246-440ce7c5-7da0-437f-ba09-2c3c70e6e119.png)
