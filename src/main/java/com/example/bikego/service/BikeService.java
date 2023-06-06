package com.example.bikego.service;

import com.example.bikego.dto.*;
import com.example.bikego.exception.InvalidRequestForBike;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface BikeService {
    Page<BikeCreateDTO> getFilterPaging(BikePage bikePage, BikeFilterDTO bikeFilterDTO);

    boolean sortValidation(String sortDirection, String sortBy);
    ResponseEntity<ResponseObject> createBike(HttpSession httpSession, BikeCreateDTO bikeCreateDTO);

    ResponseEntity<ResponseObject> getAllBike();

    ResponseEntity<ResponseObject> softDeleteBike(Long id);

    ResponseEntity<ResponseObject> updateBike(Long id, BikeUpdateDTO bikeUpdateDTO);

    ResponseEntity<ResponseObject> filterBikeByStatus(int pageNumber, int pageSize, String status);
}
