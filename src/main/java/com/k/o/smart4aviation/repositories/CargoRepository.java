package com.k.o.smart4aviation.repositories;

import com.k.o.smart4aviation.models.Cargo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends CrudRepository<Cargo,Long> {
    public List<Cargo> findAllByFlight_FlightId(int flightId);
    public List<Cargo> findAllByFlight_FlightNumberAndAndFlight_DepartureDate(int flightId, String date);
    public List<Cargo> findAllByFlight_DepartureDate(String date);
}
