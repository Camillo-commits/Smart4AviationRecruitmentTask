package com.k.o.smart4aviation.controllers;

import com.k.o.smart4aviation.models.Baggage;
import com.k.o.smart4aviation.models.Cargo;
import com.k.o.smart4aviation.models.Flight;
import com.k.o.smart4aviation.repositories.CargoRepository;
import com.k.o.smart4aviation.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cargo")
@ResponseBody
public class CargoController {
    @PostMapping("/{id}")
    public String create(@PathVariable(value = "id") Long flightId, @RequestBody List<Cargo> cargoList){
        String ids = "";

        Flight flight = flightRepository.findByFlightId(flightId);
        if(flight == null){
            return "Flight does not exist";
        }
        try{
            for(Cargo c : cargoList){
                c.setFlight(flight);
                cargoRepository.save(c);
                ids = ids.concat(c.getCargoId() + ", ");
            }
        }
        catch (Exception e){
            return "Error while adding cargo";
        }
        return "Cargo successfully added with ids: " + ids;
    }

    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private FlightRepository flightRepository;

}
