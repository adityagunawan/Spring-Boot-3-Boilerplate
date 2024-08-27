package com.user.management.controller;

import com.user.management.config.BusinessException;
import com.user.management.dto.*;
import com.user.management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/hello/{name}")
    @ResponseBody
    public String helloUser(@PathVariable String name) {
        return userService.helloUser(name);
    }

    @GetMapping("/response-entity")
    public ResponseEntity<Map<String, Object>> responseEntity() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "this is response entity");
        return ResponseEntity.ok(map);
    }

    @PostMapping("/request-body")
    public ResponseEntity<ExampleDTO> requestBody(@Valid @RequestBody ExampleDTO dto) {
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/create")
    public GeneralResponse<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) throws BusinessException {
        return userService.saveUser(request);
    }

    @PutMapping("/update/{id}")
    public GeneralResponse<CreateUserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody CreateUserRequest request) throws BusinessException {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public GeneralResponse deleteUser(@PathVariable Long id) throws BusinessException {
        return userService.deleteUser(id);
    }

    @GetMapping("/detail/{id}")
    public GeneralResponse<DetailUserResponse> detailUser(@PathVariable Long id) throws BusinessException {
        return userService.detailUser(id);
    }

    @PostMapping("/list")
    public PaginationResponse<List<DetailUserResponse>> listUser(@RequestBody PaginationRequest<ListUserFilterRequest> request) throws BusinessException {
        return userService.listUser(request);
    }

}
