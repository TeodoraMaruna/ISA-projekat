package com.application.bekend.controller;

import com.application.bekend.DTO.AdditionalServicesDTO;
import com.application.bekend.model.*;
import com.application.bekend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequestMapping("api/additionalServices")
@EnableTransactionManagement
public class AdditionalServicesContoller {

    private final AdditionalServicesService additionalServicesService;
    private final HouseService houseService;
    private final BoatService boatService;
    private final FishingAdventureService fishingAdventureService;
    private final HouseReservationService houseReservationService;

    @Autowired
    public AdditionalServicesContoller(AdditionalServicesService additionalServicesService, HouseService houseService, BoatService boatService, FishingAdventureService fishingAdventureService, HouseReservationService houseReservationService) {
        this.additionalServicesService = additionalServicesService;
        this.houseService = houseService;
        this.boatService = boatService;
        this.fishingAdventureService = fishingAdventureService;
        this.houseReservationService = houseReservationService;
    }

    @GetMapping("/getAllByHouseId/{id}")
    public ResponseEntity<List<AdditionalServicesDTO>> getAllByHouseId(@PathVariable("id") Long id){
        List<AdditionalServices> additionalServices = this.additionalServicesService.getAllByHouseId(id);
        List<AdditionalServicesDTO> additionalServicesDTOS = new ArrayList<>();

        for (AdditionalServices a: additionalServices) {
            AdditionalServicesDTO additionalServicesDTO = new AdditionalServicesDTO(a.getId(), a.getName(), a.getPrice());
            additionalServicesDTOS.add(additionalServicesDTO);
        }

        return new ResponseEntity<>(additionalServicesDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllByBoatId/{id}")
    public ResponseEntity<List<AdditionalServicesDTO>> getAllByBoatId(@PathVariable("id") Long id){
        List<AdditionalServices> additionalServices = this.additionalServicesService.getAllByBoatId(id);
        List<AdditionalServicesDTO> additionalServicesDTOS = new ArrayList<>();

        for (AdditionalServices a: additionalServices) {
            AdditionalServicesDTO additionalServicesDTO = new AdditionalServicesDTO(a.getId(), a.getName(), a.getPrice());
            additionalServicesDTOS.add(additionalServicesDTO);
        }

        return new ResponseEntity<>(additionalServicesDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllByHouseReservationId/{id}")
    public ResponseEntity<Set<AdditionalServicesDTO>> getAllByHouseReservationId(@PathVariable("id") Long id){
        Set<AdditionalServices> additionalServices = this.additionalServicesService.getAllByHouseReservationId(id);
        Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();

        for (AdditionalServices a: additionalServices) {
            // TODO: proveriti ovaj deo checked u kostruktoru
            AdditionalServicesDTO additionalServicesDTO = new AdditionalServicesDTO(a.getId(), a.getName(), a.getPrice(), true);
            additionalServicesDTOS.add(additionalServicesDTO);
        }

        return new ResponseEntity<>(additionalServicesDTOS, HttpStatus.OK);
    }
    
    @GetMapping("/getAllByFishingAdventureId/{id}")
    public ResponseEntity<List<AdditionalServicesDTO>> getAllByFishingAdventureId(@PathVariable("id") Long id){
        List<AdditionalServices> additionalServices = this.additionalServicesService.getAllByFishingAdventureId(id);
        List<AdditionalServicesDTO> additionalServicesDTOS = new ArrayList<>();

        for (AdditionalServices a: additionalServices) {
            AdditionalServicesDTO additionalServicesDTO = new AdditionalServicesDTO(a.getId(), a.getName(), a.getPrice());
            additionalServicesDTOS.add(additionalServicesDTO);
        }

        return new ResponseEntity<>(additionalServicesDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllByBoatReservationId/{id}")
    public ResponseEntity<Set<AdditionalServicesDTO>> getAllByBoatReservationId(@PathVariable("id") Long id){
        Set<AdditionalServices> additionalServices = this.additionalServicesService.getAllByBoatReservationId(id);
        Set<AdditionalServicesDTO> additionalServicesDTOS = new HashSet<>();

        for (AdditionalServices a: additionalServices) {
            AdditionalServicesDTO additionalServicesDTO = new AdditionalServicesDTO(a.getId(), a.getName(), a.getPrice(), true);
            additionalServicesDTOS.add(additionalServicesDTO);
        }

        return new ResponseEntity<>(additionalServicesDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        AdditionalServices additionalServices = this.additionalServicesService.getAdditionalServicesById(id);

//        for (HouseReservation h: this.houseReservationService.getHouseReservationsByAdditionalServicesId(id)) {
//            Long endDate = h.getEndDate().getTime();
//            Calendar date = Calendar.getInstance();
//            long millisecondsDate = date.getTimeInMillis();
//
//            if (h.isAvailable() == false && h.isAvailabilityPeriod() == false && endDate >= millisecondsDate) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }

        additionalServices.setHouses(null);
        additionalServices.setHouseReservationsServices(null);

        additionalServices.setBoats(null);
        additionalServices.setBoatReservationsServices(null);
        
        additionalServices.setFishingAdventures(null);
        additionalServices.setAdventureReservationsServices(null);
        this.additionalServicesService.deleteById(id);  // brisanje iz additional_services

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AdditionalServices> save(@RequestBody AdditionalServicesDTO dto) {
        AdditionalServices additionalServices = new AdditionalServices(dto.getId(), dto.getName(), dto.getPrice(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        House house = this.houseService.getHouseById(dto.getHouseId());
        Boat boat = this.boatService.getBoatById(dto.getBoatId());
        FishingAdventure adventure = this.fishingAdventureService.getFishingAdventureById(dto.getAdventureId());

        if (house != null) {
            for (HouseReservation h: house.getCourses()) {
                Long endDate = h.getEndDate().getTime();
                Calendar date = Calendar.getInstance();
                long millisecondsDate = date.getTimeInMillis();

                if (h.isAvailable() == false && h.isAvailabilityPeriod() == false && endDate >= millisecondsDate) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }

            Set<House> houses = additionalServices.getHouses();
            houses.add(house);
            additionalServices.setHouses(houses);
        }
        else if (boat != null) {
            for (BoatReservation h: boat.getCourses()) {
                Long endDate = h.getEndDate().getTime();
                Calendar date = Calendar.getInstance();
                long millisecondsDate = date.getTimeInMillis();

                if (h.isAvailable() == false && h.isAvailabilityPeriod() == false && endDate >= millisecondsDate) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }

            Set<Boat> boats = additionalServices.getBoats();
            boats.add(boat);
            additionalServices.setBoats(boats);
        }
        else if (adventure != null) {
            Set<FishingAdventure> adventures = additionalServices.getFishingAdventures();
            adventures.add(adventure);
            additionalServices.setFishingAdventures(adventures);
        }
        this.additionalServicesService.save(additionalServices);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
