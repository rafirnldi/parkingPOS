package com.example.demo.controllers;


import com.example.demo.dtos.CheckInDTO;
import com.example.demo.dtos.CheckOutDTO;
import com.example.demo.repository.POSRepository;
import com.example.demo.services.POSService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class POSController {

    @Autowired
    private POSService posService;

    @PostMapping(
            value = "/checkin",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> checkin(@RequestBody @Valid CheckInDTO checkInDTO){

        return posService.checkIn(checkInDTO);
    }

    @PostMapping(
            value = "/checkout",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> checkout(@RequestBody @Valid CheckOutDTO checkOutDTO){

        return posService.checkOut(checkOutDTO);
    }
}
