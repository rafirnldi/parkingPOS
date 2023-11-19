package com.example.demo.services;


import com.example.demo.dtos.CheckInDTO;
import com.example.demo.dtos.CheckOutDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface POSService {
    ResponseEntity<?> checkIn(CheckInDTO checkInDTO);

    ResponseEntity<?> checkOut(CheckOutDTO checkOutDTO);

}
