package com.example.demo.services.implementations;


import com.example.demo.dtos.CheckInDTO;
import com.example.demo.dtos.CheckOutDTO;
import com.example.demo.models.Tickets;
import com.example.demo.repository.POSRepository;
import com.example.demo.services.POSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class POSServiceImpl implements POSService {
    POSRepository posRepository;

    @Autowired
    public POSServiceImpl(POSRepository posRepository){
        this.posRepository = posRepository;
    }

    @Override
    public ResponseEntity<?> checkIn (CheckInDTO checkInDTO){
        if (!posRepository.findById(checkInDTO.getPlat().replaceAll(" ","").toUpperCase()).isEmpty()){
            System.out.println("BOO");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
        posRepository.checkIn(checkInDTO.getPlat().replaceAll(" ","").toUpperCase(),checkInDTO.getTipe_kendaraan(), LocalDateTime.now());
        return new ResponseEntity<>(HttpStatus.OK);}
    }

    @Override
    public ResponseEntity<?> checkOut (CheckOutDTO checkOutDTO){
        int rate;
        Tickets ticket = posRepository.queryTicketByPlate(checkOutDTO.getPlat().replaceAll(" ","").toUpperCase());
        if (ticket == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            posRepository.deleteTicket(checkOutDTO.getPlat().replaceAll(" ","").toUpperCase());
            if (ticket.getKendaraan().toUpperCase().equals("MOBIL")){
                rate = 3000;
            } else if (ticket.getKendaraan().toUpperCase().equals("MOTOR")) {
                rate = 1500;
            }else{
                rate = 3000;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime entry = ticket.getEntry();
            LocalDateTime currentTime = LocalDateTime.now();
            Duration parkingDuration = Duration.between(entry,currentTime);
            double parkingHours = parkingDuration.toMinutes()/60;
            double actualMinutes = parkingDuration.toMinutes()%60;
            Integer chargedHours = (int)Math.ceil(parkingDuration.toMinutes()/60.0);
            Integer bill = chargedHours * rate;

            TreeMap<String, Object> response = new TreeMap<>();
            response.put("Plate No", ticket.getPlat());
            response.put("Entry Time",entry.format(formatter));
            response.put("Exit Time", currentTime.format(formatter));
            response.put("Duration", String.valueOf((int)parkingHours)+ " Jam " + String.valueOf((int)actualMinutes)+ " Menit ");
            response.put("Bill","Rp."+bill.toString());

            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }

}
