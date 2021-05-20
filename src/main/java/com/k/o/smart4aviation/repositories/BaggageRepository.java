package com.k.o.smart4aviation.repositories;

import com.k.o.smart4aviation.models.Baggage;
import com.k.o.smart4aviation.models.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BaggageRepository extends CrudRepository<Baggage, Long> {
    public List<Baggage> findAllByFlight(Flight flight);
    public List<Baggage> findAllByFlight_FlightId(long flightId);
    public List<Baggage> findAllByFlight_FlightNumber(int flightNumber);
    public List<Baggage> findAllByFlight_FlightNumberAndAndFlight_DepartureDate(int flightNumber, String date);
    public List<Baggage> findAllByFlight_DepartureDate(String date);
}
