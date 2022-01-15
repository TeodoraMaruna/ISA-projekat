package com.application.bekend.controller;

import com.application.bekend.DTO.AddressDTO;
import com.application.bekend.model.Address;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class TestContoller {

    @GetMapping("/")
    public ResponseEntity getAddressByHouseId(){
       return new ResponseEntity<>( HttpStatus.OK);
    }
}
