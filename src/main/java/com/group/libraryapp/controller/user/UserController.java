package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    @RestController의 기능
    1. 설정한 클래스를 Controller로 등록 (api 진입 지점으로 해주는 것)
    2. 설정한 클래스를 스프링 빈으로 등록 !!
 */
@RestController

public class UserController {

    private final UserService userService;


    // jdbc 탬플릿을 생성자에 직접 넣어주지 않아도 스프링이 알아서 JDBC 템플릿을 넣어줌
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public void saveUser(@RequestBody UserCreateRequest request) {
        userService.saveUser(request);
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/user")
    // @RequestBody : 컨트롤러의 역할을 수행하기 위함
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userService.updateUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userService.deleteUser(name);
    }
}
