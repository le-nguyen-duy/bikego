package com.example.bikego.controller;

import com.example.bikego.dto.ResponseObject;
import com.example.bikego.entity.User;
import com.example.bikego.exception.AuthenticationFailedException;
import com.example.bikego.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "for admin get all user")
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllUser (HttpSession httpSession) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(httpSession);
            System.out.println(currentUser.getRole().getName());

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return userService.getAllUser();
            } else {
                // Người dùng không có quyền truy cập API getAllBikeBrand
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseObject("Error", "Unauthorized", null,null));
            }
        } catch (AuthenticationFailedException e) {
            // Xử lý nếu xảy ra lỗi xác thực người dùng
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseObject("Error", e.getMessage(), null,null));
        } catch (ValidationException e) {
            // Xử lý nếu xảy ra lỗi trong việc tìm kiếm người dùng
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Error", e.getMessage(), null,null));
        }
    }
    @Operation(summary = "for user get detail infor")
    @GetMapping("/detail")
    public ResponseEntity<ResponseObject> getUserDetail(HttpSession httpSession) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(httpSession);
            System.out.println(currentUser.getRole().getName());

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN") ||
                    currentUser.getRole().getName().equalsIgnoreCase("OWNER") ) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return userService.getUserDetail(httpSession);
            } else {
                // Người dùng không có quyền truy cập API getAllBikeBrand
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseObject("Error", "Unauthorized", null,null));
            }
        } catch (AuthenticationFailedException e) {
            // Xử lý nếu xảy ra lỗi xác thực người dùng
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseObject("Error", e.getMessage(), null,null));
        } catch (ValidationException e) {
            // Xử lý nếu xảy ra lỗi trong việc tìm kiếm người dùng
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Error", e.getMessage(), null,null));
        }
    }
}
