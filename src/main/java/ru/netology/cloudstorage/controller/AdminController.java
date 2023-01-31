//package ru.netology.cloudstorage.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import ru.netology.cloudstorage.model.DTO.AdminUserDTO;
//import ru.netology.cloudstorage.model.entity.User.User;
//import ru.netology.cloudstorage.service.UserService;
//
//@RestController
//@RequestMapping(value = "/admin/")
//public class AdminController {
//
//    private final UserService userService;
//
//    @Autowired
//    public AdminController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping(value = "users/{id}")
//    public ResponseEntity<AdminUserDTO> getUserById(@PathVariable(name = "id") Long id) {
//        User user = userService.findById(id);
//
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        AdminUserDTO result = AdminUserDTO.fromUser(user);
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//}