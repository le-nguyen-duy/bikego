package com.example.bikego.controller;

import com.example.bikego.dto.*;
import com.example.bikego.entity.User;
import com.example.bikego.exception.AuthenticationFailedException;
import com.example.bikego.service.BikeService;
import com.example.bikego.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;


@RestController
@RequestMapping(value = "/api/v1/bike")
public class BikeController {
    private final BikeService bikeService;
    private final UserService userService;

    public BikeController(BikeService bikeService, UserService userService) {
        this.bikeService = bikeService;
        this.userService = userService;
    }

    @Operation(summary = "for admin get all bike")
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllBike (HttpSession httpSession) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(httpSession);
            System.out.println(currentUser.getRole().getName());

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return bikeService.getAllBike();
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

    @Operation(summary = "for admin create bike")
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createBike (HttpSession httpSession,@RequestBody BikeCreateDTO bikeCreateDTO) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(httpSession);
            System.out.println(currentUser.getRole().getName());

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return bikeService.createBike(httpSession,bikeCreateDTO);
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

    @Operation(summary = "for delete bike")
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseObject> deleteBike (HttpSession httpSession,@RequestParam Long id) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(httpSession);
            System.out.println(currentUser.getRole().getName());

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN") ||
                    currentUser.getRole().getName().equalsIgnoreCase("OWNER")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return bikeService.softDeleteBike(id);
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

    @Operation(summary = "for update bike")
    @PutMapping("/update{id}")
    public ResponseEntity<ResponseObject> updateBike (HttpSession httpSession,
                                                      @PathVariable Long id,
                                                      @RequestBody BikeUpdateDTO bikeUpdateDTO) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(httpSession);
            System.out.println(currentUser.getRole().getName());

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN") ||
                    currentUser.getRole().getName().equalsIgnoreCase("OWNER")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return bikeService.updateBike(id, bikeUpdateDTO);
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
    @Operation(summary = "for filter bike by status")
    @GetMapping("/filter")
    public ResponseEntity<ResponseObject> filterBike(HttpSession httpSession,
                                                     @RequestParam(required = false) String status,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "3") int size) {
        try {
            // Lấy người dùng hiện tại từ session
            User currentUser = userService.getCurrentUser(httpSession);
            System.out.println(currentUser.getRole().getName());

            // Kiểm tra vai trò của người dùng
            if (currentUser.getRole().getName().equalsIgnoreCase("ADMIN") ||
                    currentUser.getRole().getName().equalsIgnoreCase("OWNER")) {
                // Người dùng có vai trò "ADMIN", cho phép truy cập API getAllBikeBrand
                return bikeService.filterBikeByStatus(page,size,status);
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

