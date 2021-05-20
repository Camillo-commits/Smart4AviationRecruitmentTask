package com.k.o.smart4aviation.controllers;

import com.k.o.smart4aviation.models.Baggage;
import com.k.o.smart4aviation.models.Flight;
import com.k.o.smart4aviation.repositories.BaggageRepository;
import com.k.o.smart4aviation.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/baggage")
@ResponseBody
public class BaggageController {
    @PostMapping("/{id}")
    public String create(@PathVariable(value = "id") Long flightId, @RequestBody List<Baggage> baggageList){
        String ids = "";

        Flight flight = flightRepository.findByFlightId(flightId);
        if(flight == null){
            return "Flight does not exist";
        }

        try{
            for(Baggage b : baggageList){
                b.setFlight(flight);
                baggageRepository.save(b);
                ids = ids.concat(b.getBaggageId() + ", ");
            }
        }
        catch (Exception e){
            return "Error while adding Baggage";
        }
        return "Baggage successfully added with ids: " + ids;
    }

    @Autowired
    private BaggageRepository baggageRepository;
    @Autowired
    private FlightRepository flightRepository;
}
