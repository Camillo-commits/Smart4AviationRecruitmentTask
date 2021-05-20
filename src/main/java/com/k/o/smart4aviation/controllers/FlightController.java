package com.k.o.smart4aviation.controllers;

import com.k.o.smart4aviation.models.Flight;
import com.k.o.smart4aviation.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/flight")
@ResponseBody
public class FlightController {
    @PostMapping
    public String create(@RequestBody List<Flight> flights){
        String ids = "";

        try {
            for (Flight f : flights) {
                flightRepository.save(f);
                ids = ids.concat(f.getFlightId() + ", ");
            }
        }
        catch (Exception e){
            return "Error while adding flights";
        }
        return "Flights successfully added with ids: " + ids;
    }

    @Autowired
    private FlightRepository flightRepository;
}
