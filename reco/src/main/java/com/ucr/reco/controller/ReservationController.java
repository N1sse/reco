package com.ucr.reco.controller;

import com.ucr.reco.model.Reservation;
import com.ucr.reco.model.dto.ReservationDTO;
import com.ucr.reco.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ReservationDTO reservationDTO){
        return ResponseEntity.ok(reservationService.add(reservationDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(reservationService.findAll());
    }


}
